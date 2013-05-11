package dev.move;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import robocode.AdvancedRobot;
import robocode.Bullet;
import robocode.BulletHitBulletEvent;
import robocode.Condition;
import robocode.DeathEvent;
import robocode.Event;
import robocode.HitByBulletEvent;
import robocode.Robot;
import robocode.RobotDeathEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import dev.cluster.Scale;
import dev.cluster.Space;
import dev.data.EventHandler;
import dev.draw.RobotGraphics;
import dev.robots.EnemyData;
import dev.robots.RobotData;
import dev.utils.Utils;
import dev.virtual.DataWave;

public class MovementProfiler implements EventHandler {

   public static final int                       PROFILE_VEIWS = 5;
   public static final int                       ENEMY_LAG     = -2;


   private Robot                                 robot;
   private RobotData[]                           profiles;
   private int                                   profiles_position;

   private RobotData                             enemy;
   private LinkedList<DataWave<Double>>          enemyWaves;

   private Collection<Scale>                     scales;
   private static HashMap<String, Space<Double>> spaces;
   private int                                   clustorSize   = 10;


   public MovementProfiler(Robot robot, Collection<Scale> scales) {
      this.robot = robot;
      this.profiles = new RobotData[PROFILE_VEIWS];
      this.profiles[0] = new RobotData(this.robot);
      this.profiles_position = 1;

      enemy = null;
      this.enemyWaves = new LinkedList<DataWave<Double>>();

      if (spaces == null) {
         this.scales = scales;
         MovementProfiler.spaces = new HashMap<String, Space<Double>>(robot.getOthers());
      }
   }

   public MovementProfiler(Robot robot, Scale[] scales) {
      this(robot, Arrays.asList(scales));
   }

   public MovementProfiler(AdvancedRobot robot, Collection<Scale> scales) {
      this((Robot) robot, scales);
      robot.addCustomEvent(new Condition() {
         @Override
         public boolean test() {
            update();
            return false;
         }
      });
   }

   public MovementProfiler(AdvancedRobot robot, Scale[] scales) {
      this(robot, Arrays.asList(scales));
   }

   public void update() {
      updateWaves();
      updateProfile();
   }

   /**
    * The events that are currently handled are:<br>
    * - {@link ScannedRobotEvent}<br>
    * - {@link DeathEvent}<br>
    * - {@link WinEvent}<br>
    * - {@link RobotDeathEvent}<br>
    * - {@link HitByBulletEvent}<br>
    * - {@link BulletHitBulletEvent}<br>
    * <small>(Last updated 2009/12/26)</small>
    * 
    * @param e
    *           the current {@link Event} being processed.
    */
   @Override
   public void inEvent(Event e) {
      if (robot.getOthers() <= 1) {
         if (e instanceof ScannedRobotEvent)
            handleScannedRobot((ScannedRobotEvent) e);
         else if (e instanceof DeathEvent)
            handleDeathEvent((DeathEvent) e);
         else if (e instanceof WinEvent)
            handleWinEvent((WinEvent) e);
         else if (e instanceof RobotDeathEvent)
            handleRobotDeathEvent((RobotDeathEvent) e);
         else if (e instanceof HitByBulletEvent)
            handleHitByBullet((HitByBulletEvent) e);
         else if (e instanceof BulletHitBulletEvent)
            handleBulletHitBullet((BulletHitBulletEvent) e);
      }
   }

   @Override
   public void inEvents(Iterable<Event> events) {
      for (Event e : events)
         inEvent(e);
   }


   private void handleScannedRobot(ScannedRobotEvent event) {
      if (enemy == null) {
         this.enemy = new EnemyData(event, robot);
         if (!spaces.containsKey(enemy.getName())) {
            spaces.put(enemy.getName(), new Space<Double>(scales));
         }
      } else {
         double deltaEnergy = event.getEnergy() - enemy.getEnergy();
         double deltaVelocity = event.getVelocity() - enemy.getVelocity();

         if (Utils.isNear(enemy.getDeltaEnergy(), -0.1, .01) && Utils.isNear(deltaEnergy, -0.1, .01)) {
            if (enemyWaves.size() > 0)
               enemyWaves.remove(enemyWaves.size() - 1);
         } else if (firedBullet(enemy, deltaEnergy, deltaVelocity)) {
            RobotData seen = getProfile(ENEMY_LAG);

            double firePower = Math.abs(deltaEnergy);
            if (firePower > 3.0) {
               RobotData me = getProfile(-1);
               firePower = Math.abs(deltaEnergy - 3.0 * Utils.firePower(Math.abs(me.getDeltaEnergy())));
            }

            Collection<Double> data = spaces.get(enemy.getName()).getClustor(seen, enemy, clustorSize);
            DataWave<Double> wave = new DataWave<Double>(enemy.copy(), seen, firePower, data);
            enemyWaves.add(wave);
         }

         enemy.update(event, robot);
      }
   }

   private void handleHitByBullet(HitByBulletEvent event) {
      handleBulletEvent(event.getBullet(), event.getTime());
   }

   private void handleBulletHitBullet(BulletHitBulletEvent event) {
      handleBulletEvent(event.getHitBullet(), event.getTime());
   }

   private void handleDeathEvent(DeathEvent event) {
      enemy = null;
   }

   private void handleWinEvent(WinEvent event) {
      enemy = null;
   }

   private void handleRobotDeathEvent(RobotDeathEvent event) {
      enemy = null;
   }

   private boolean firedBullet(RobotData enemy, double deltaEnergy, double deltaVelocity) {
      // Determines whether the delta energy of the enemy robot is a possible
      // fire energy drop
      boolean fired = Utils.inRange(-Rules.MAX_BULLET_POWER, deltaEnergy, -Rules.MIN_BULLET_POWER, 0.01);

      // A check to see if one of the enemy's bullets hit us at the same time
      // they fired.
      deltaEnergy -= Utils.energyReturn_BulletDamage(Math.abs(getProfile(-1).getDeltaEnergy()));
      fired = fired || Utils.inRange(-Rules.MAX_BULLET_POWER, deltaEnergy, -Rules.MIN_BULLET_POWER, 0.01);

      // Makes sure the robot didn't hit a wall
      fired = fired && Math.abs(deltaVelocity) <= Rules.DECELERATION;

      // Makes sure of ?
      // fired = fired && Math.abs(enemy.getDeltaVelocity()) <=
      // Rules.DECELERATION;

      // Makes sure the inactivity time hasn't been reached and the robot is
      // just losing energy from that
      fired = fired && !Utils.isNear(deltaEnergy, enemy.getDeltaEnergy(), 0.01);

      return fired;
   }

   private void handleBulletEvent(Bullet b, long eventTime) {
      if (enemy == null || enemy.isDead()) {
         return;
      }
      DataWave<Double> wave = Utils.findWaveMatch(enemyWaves, b, eventTime);
      if (wave != null) {
         enemyWaves.remove(wave);
         Double gf = Utils.getGuessFactor(wave, wave.getView(), b);
         spaces.get(enemy.getName()).add(wave.getView(), wave.getReference(), gf);
      }
   }

   private RobotData getProfile(int difference) {
      return profiles[(profiles_position + difference) % profiles.length];
   }

   private void updateProfile() {
      RobotData newRobot = getProfile(-1).copy();
      newRobot.update(robot);
      profiles[profiles_position % profiles.length] = newRobot;
      profiles_position++;
   }

   private void updateWaves() {
      for (int i = 0; i < enemyWaves.size(); i++) {
         DataWave<Double> w = enemyWaves.get(i);
         if (w.testHit(getProfile(-2)) || !w.active(robot.getTime()))
            enemyWaves.remove(i--);
      }
   }

   public void draw(RobotGraphics grid) {
      for (DataWave<Double> w : enemyWaves)
         w.draw(grid);
   }

}
