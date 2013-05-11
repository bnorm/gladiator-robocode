package kid.movement;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import kid.cluster.Comparison;
import kid.cluster.Space;
import kid.data.Drawable;
import kid.data.Printable;
import kid.data.factor.GuessFactor;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.robot.EnemyData;
import kid.robot.RobotData;
import kid.utils.Utils;
import kid.virtual.DataWave;
import robocode.AdvancedRobot;
import robocode.Bullet;
import robocode.BulletHitBulletEvent;
import robocode.Condition;
import robocode.DeathEvent;
import robocode.Event;
import robocode.HitByBulletEvent;
import robocode.RobocodeFileOutputStream;
import robocode.RobotDeathEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

// TODO documentation: class

/**
 * A class that attempts to see a given robot in the same way that its enemies see it. The actual representation of the
 * enemy's view is most often, if not always, wrong, but it still provides a semi-accurate guess as to how the enemy see
 * it. <code>{@link package_class_member label}</code>
 * 
 * @author Brian Norman
 * @version 0.0.1 Beta
 */
public class MovementProfiler implements Drawable, Printable {

   public static final int                                                  PROFILE_VEIWS = 5;
   public static final int                                                  ENEMY_LAG     = -2;

   private AdvancedRobot                                                    robot;
   private static boolean                                                   flattener     = false;

   private RobotData[]                                                      profiles;
   private int                                                              profiles_position;

   private GuessFactor[]                                                    latest;
   private int                                                              latestPosition;

   private GuessFactor[]                                                    latestHits;
   private int                                                              latestHitsPosition;

   private RobotData                                                        enemy;
   private ArrayList<DataWave<GuessFactor, RobotData, RobotData>>           enemyWaves;
   private ArrayList<DataWave<GuessFactor, RobotData, RobotData>>           enemyWavesCF;

   private static HashMap<String, Space<GuessFactor, RobotData, RobotData>> hitSpace      = null;
   private static HashMap<String, Space<GuessFactor, RobotData, RobotData>> allSpace      = null;
   private Comparison[]                                                     comparisons   = null;
   private int                                                              dataPerWave   = 10;

   public MovementProfiler(AdvancedRobot myRobot, Comparison[] comparisons) {
      init(myRobot, comparisons, -1);
   }

   public MovementProfiler(AdvancedRobot myRobot, Comparison[] comparisons, int dataPerWave) {
      init(myRobot, comparisons, dataPerWave);
   }

   private void init(AdvancedRobot r, Comparison[] c, int d) {
      this.robot = r;

      this.profiles = new RobotData[PROFILE_VEIWS];
      this.profiles[0] = new RobotData(this.robot);
      this.profiles_position = 1;

      this.latest = new GuessFactor[2];
      this.latestPosition = 0;

      this.latestHits = new GuessFactor[1];
      this.latestHitsPosition = 0;

      this.enemy = null;
      this.enemyWaves = new ArrayList<DataWave<GuessFactor, RobotData, RobotData>>();
      this.enemyWavesCF = new ArrayList<DataWave<GuessFactor, RobotData, RobotData>>();

      this.robot.addCustomEvent(new WaveTracker());

      comparisons = c;
      if (d > 0)
         dataPerWave = d;
      if (hitSpace == null) {
         hitSpace = new HashMap<String, Space<GuessFactor, RobotData, RobotData>>();
         allSpace = new HashMap<String, Space<GuessFactor, RobotData, RobotData>>();
      }
   }

   public void inEvent(final Event event) {
      if (robot.getOthers() <= 1) {
         if (event instanceof ScannedRobotEvent)
            handleScannedRobot((ScannedRobotEvent) event);
         else if (event instanceof DeathEvent)
            handleDeathEvent((DeathEvent) event);
         else if (event instanceof WinEvent)
            handleWinEvent((WinEvent) event);
         else if (event instanceof RobotDeathEvent)
            handleRobotDeathEvent((RobotDeathEvent) event);
         else if (event instanceof HitByBulletEvent)
            handleHitByBullet((HitByBulletEvent) event);
         else if (event instanceof BulletHitBulletEvent)
            handleBulletHitBullet((BulletHitBulletEvent) event);
      }
   }

   public ArrayList<DataWave<GuessFactor, RobotData, RobotData>> getWaves() {
      if (flattener)
         return enemyWavesCF;
      else
         return enemyWaves;
   }

   public boolean isFlattening() {
      return flattener;
   }

   public GuessFactor[] getLatest() {
      if (flattener)
         return new GuessFactor[0];
      else
         return latest;
   }

   public GuessFactor[] getLatestHits() {
      if (flattener)
         return new GuessFactor[0];
      else
         return latestHits;
   }

   private final void handleDeathEvent(DeathEvent event) {
      enemy = null;
   }

   private final void handleWinEvent(WinEvent event) {
      enemy = null;
   }

   private final void handleRobotDeathEvent(RobotDeathEvent event) {
      enemy = null;
   }

   /**
    * A function that handles all onScannedRobot events that occur during the duration of the match. This function
    * should not be called directly but will be called when an event of this type is passed to <i>onEvent()</i>. <br>
    * <br>
    * A note on the code: Until the end of the function, the variable <i>enemy</i> refers to the previous turn. It is
    * updated at the very end of the function. This is done to make comparison of the current turn and the previous one.
    * 
    * @param event
    **/
   private final void handleScannedRobot(final ScannedRobotEvent event) {
      if (enemy == null) {
         this.enemy = new EnemyData(event, robot);
         if (!hitSpace.containsKey(enemy.getName())) {
            hitSpace.put(enemy.getName(), new Space<GuessFactor, RobotData, RobotData>(comparisons));
            allSpace.put(enemy.getName(), new Space<GuessFactor, RobotData, RobotData>(comparisons));
         }
      } else {
         double deltaEnergy = event.getEnergy() - enemy.getEnergy();
         double deltaVelocity = event.getVelocity() - enemy.getVelocity();

         if (enemy.getDeltaEnergy() == 0.1D && deltaEnergy == 0.1D) {
            enemyWaves.remove(enemyWaves.size() - 1);
            enemyWavesCF.remove(enemyWavesCF.size() - 1);

         } else if (Utils.limit(-Rules.MAX_BULLET_POWER - 0.000001, deltaEnergy, -Rules.MIN_BULLET_POWER + 0.000001) == deltaEnergy
               && Math.abs(deltaVelocity) <= Math.max(Rules.ACCELERATION, Rules.DECELERATION)
               && Math.abs(enemy.getDeltaVelocity()) <= Math.max(Rules.ACCELERATION, Rules.DECELERATION)
               && deltaEnergy != enemy.getDeltaEnergy()) {

            RobotData seenRobot = getProfile(ENEMY_LAG);
            GuessFactor[] hitData = hitSpace.get(enemy.getName()).getCluster(seenRobot, enemy, dataPerWave).toArray(
                  new GuessFactor[0]);
            GuessFactor[] allData = allSpace.get(enemy.getName()).getCluster(seenRobot, enemy, dataPerWave).toArray(
                  new GuessFactor[0]);
            DataWave<GuessFactor, RobotData, RobotData> wave = new DataWave<GuessFactor, RobotData, RobotData>(enemy,
                  seenRobot, Math.abs(deltaEnergy), hitData, seenRobot, enemy.copy());
            DataWave<GuessFactor, RobotData, RobotData> waveCF = new DataWave<GuessFactor, RobotData, RobotData>(enemy,
                  seenRobot, Math.abs(deltaEnergy), allData, seenRobot, enemy.copy());
            enemyWaves.add(wave);
            enemyWavesCF.add(waveCF);
         }

         enemy.update(event, robot);
      }
   }

   private final void handleHitByBullet(final HitByBulletEvent event) {
      handleBulletEvent(event.getBullet(), event.getTime());
   }

   private final void handleBulletHitBullet(final BulletHitBulletEvent event) {
      handleBulletEvent(event.getHitBullet(), event.getTime());
   }

   private final void handleBulletEvent(final Bullet b, long eventTime) {
      if (enemy != null && !enemy.isDummy()) {
         DataWave<GuessFactor, RobotData, RobotData> wave = Utils.findWaveMatch(enemyWaves, b, eventTime);
         if (wave != null) {
            enemyWaves.remove(wave);
            GuessFactor gf = new GuessFactor(Utils.getGuessFactor(wave, wave.getView(), b));
            hitSpace.get(enemy.getName()).add(gf, wave.getView(), wave.getReference());

            latestHits[latestHitsPosition % latestHits.length] = gf;
            latestHitsPosition++;

            double[] bins = getBins(wave, 51);
            double max = 1;
            for (double i : bins)
               max = Math.max(max, i);
            boolean temp = bins[Utils.getIndex(gf.getGuessFactor(), 51)] / max < .7;
            if (flattener != temp)
               System.out.println("Curve flattener is now " + (temp ? "ON" : "OFF"));
            flattener = temp;
         }
      }
   }

   private double[] getBins(DataWave<GuessFactor, RobotData, RobotData> wave, int numOfBins) {
      double[] bins = new double[numOfBins];

      for (int i = 0; i < numOfBins; i++) {
         double indexGF = Utils.getGuessFactor(i, numOfBins);
         for (GuessFactor gf : wave.getData()) {
            bins[i] += Utils.getGFDanger(gf, indexGF);
         }
         for (GuessFactor gf : getLatest()) {
            if (gf != null)
               bins[i] += Utils.getGFDanger(gf, indexGF);

         }
         for (GuessFactor gf : getLatestHits()) {
            if (gf != null)
               bins[i] += Utils.getGFDanger(gf, indexGF);
         }
      }

      return bins;
   }

   private int getProfilesPosition(final int difference) {
      return (profiles_position + difference) % profiles.length;
   }

   private RobotData getProfile(final int difference) {
      return profiles[getProfilesPosition(difference)];
   }

   @Override
   public void print(PrintStream console) {
      hitSpace.get(enemy.getName()).print(console);
   }

   @Override
   public void print(RobocodeFileOutputStream output) {
      hitSpace.get(enemy.getName()).print(output);
   }

   @Override
   public void draw(final RGraphics grid) {
      drawEnemyWaves(grid);
   }

   public void drawEnemyWaves(final RGraphics grid) {
      if (DrawMenu.getValue("Enemy Waves", "Profile", true)) {
         for (DataWave<GuessFactor, RobotData, RobotData> w : getWaves()) {
            double dist = w.getDist(grid.getTime()) - 5.0;
            double[] bins = getBins(w, 51);

            double max = 1;
            for (double i : bins)
               max = Math.max(max, i);

            grid.setStroke(new BasicStroke(1.5f));
            float hue = 0.0f; // RED

            double heading = w.getHeading();
            double x = w.getStartX();
            double y = w.getStartY();
            RobotData view = w.getView();
            double d = Utils.getDirection(view.getHeading(), view.getVelocity(), Utils.angle(x, y, view.getX(), view
                  .getY()));
            double angle = d * Utils.getMaxEscapeAngle(w.getFirePower());

            double angleGF = heading + angle * Utils.getGuessFactor(0, bins.length);

            double prevX = Utils.getX(x, dist, angleGF);
            double prevY = Utils.getY(y, dist, angleGF);
            for (int i = 1; i < bins.length; i++) {
               float brightness = (float) (0.1 + 0.9 * bins[i] / max);
               grid.setColor(Color.getHSBColor(hue, 1.0f, brightness));

               angleGF = heading + angle * Utils.getGuessFactor(i, bins.length);
               grid.drawLine(prevX, prevY, prevX = Utils.getX(x, dist, angleGF), prevY = Utils.getY(y, dist, angleGF));
            }

         }
      }
   }

   private class WaveTracker extends Condition {
      @Override
      public boolean test() {
         for (int i = 0; i < enemyWaves.size(); i++) {
            DataWave<GuessFactor, RobotData, RobotData> w = enemyWaves.get(i);
            if (w.testHit(robot.getX(), robot.getY(), robot.getTime() - 1) || !w.active(robot.getTime())) {
               enemyWaves.remove(i);
               i--;
            }
         }

         for (int i = 0; i < enemyWavesCF.size(); i++) {
            DataWave<GuessFactor, RobotData, RobotData> w = enemyWavesCF.get(i);
            if (w.testHit(robot.getX(), robot.getY(), robot.getTime() - 1) || !w.active(robot.getTime())) {
               RobotData newRobot = getProfile(-1).copy();
               GuessFactor gf = new GuessFactor(Utils.getGuessFactor(w, w.getView(), newRobot));
               if (enemy != null)
                  allSpace.get(enemy.getName()).add(gf, w.getView(), w.getReference());
               latest[latestPosition % latest.length] = gf;
               latestPosition++;
               enemyWavesCF.remove(i);
               i--;
            }
         }

         RobotData newRobot = getProfile(-1).copy();
         newRobot.update(robot);
         profiles[getProfilesPosition(0)] = newRobot;
         profiles_position++;
         return false;
      }
   }

}
