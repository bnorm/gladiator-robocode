package dev;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import kid.movement.radar.RadarMovement;
import robocode.AdvancedRobot;
import robocode.BulletHitBulletEvent;
import robocode.DeathEvent;
import robocode.HitByBulletEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import dev.cluster.Scale;
import dev.cluster.Space;
import dev.cluster.Vector;
import dev.cluster.scales.Distance;
import dev.cluster.scales.LateralVelocity;
import dev.cluster.scales.Velocity;
import dev.draw.DrawMenu;
import dev.draw.RobotGraphics;
import dev.move.MovementProfiler;
import dev.robots.RobotData;
import dev.robots.RobotManager;

public class Test extends AdvancedRobot {

   private RadarMovement        radar;

   private RobotManager         robots;
   private static final Scale[] scales = { new Distance(), new Velocity(), new LateralVelocity() };

   private MovementProfiler     profile;

   private Space<Object>        space;

   @Override
   public void run() {
      // try {
      // LogManager.getLogManager().readConfiguration(new
      // FileInputStream(getDataFile("logging.properties")));
      // } catch (IOException e) {
      // e.printStackTrace();
      // }
      out.println("BLAHB LAKJFL:AKJF:LKSJDF:LKJ");

      radar = new RadarMovement(this);

      this.robots = RobotManager.getInstance(this);
      this.profile = new MovementProfiler(this, scales);

      space = new Space<Object>(scales);

      setTurnRadarRightRadians(Double.POSITIVE_INFINITY);

      do {
         this.execute();
      } while (true);
   }

   @Override
   public void onScannedRobot(ScannedRobotEvent event) {
      this.robots.inEvent(event);
      this.profile.inEvent(event);
      if (this.setFireBullet(2.0) != null) {
         Vector<Object> center = new Vector<Object>(Arrays.asList(Test.scales), robots.getRobot(event.getName()), new RobotData(this), null);
         System.out.println("CENTER: " + center);

         // LinkedList<Object> vectors =
         space.getClustor(robots.getRobot(event.getName()), new RobotData(this), 10);

         space.add(robots.getRobot(event.getName()), new RobotData(this), null);
      }
      if (getOthers() == 1) {
         RobotData enemy = robots.getRobot(event.getName());
         radar.setSweep(enemy.getX(), enemy.getY(), 20);
      }
   }

   @Override
   public void onRobotDeath(RobotDeathEvent event) {
      this.robots.inEvent(event);
      this.profile.inEvent(event);
   }

   @Override
   public void onDeath(DeathEvent event) {
      this.robots.inEvent(event);
      this.profile.inEvent(event);
   }

   @Override
   public void onWin(WinEvent event) {
      this.robots.inEvent(event);
      this.profile.inEvent(event);
   }

   @Override
   public void onHitByBullet(HitByBulletEvent event) {
      this.profile.inEvent(event);
   }

   @Override
   public void onBulletHitBullet(BulletHitBulletEvent event) {
      this.profile.inEvent(event);
   }

   @Override
   public void onPaint(Graphics2D g) {
      DrawMenu.draw(g);
      this.profile.draw(new RobotGraphics(g, this));
   }

   @Override
   public void onMouseClicked(MouseEvent e) {
      DrawMenu.inMouseEvent(e);
   }

}
