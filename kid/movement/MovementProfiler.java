package kid.movement;

import java.util.*;

import robocode.*;

import kid.*;
import kid.data.*;
import kid.data.factor.GuessFactor;
import kid.data.info.RobotInfo;
import kid.data.robot.RobotData;
import kid.data.virtual.DataWave;
import kid.segmentation.*;

// TODO documentation: class

/**
 * A class that attempts to see a given robot in the same way that its enemies see it. The actual representation of the
 * enemy's view is most often, if not always, wrong, but it still provides a semi-accurate guess as to how the enemy see
 * it. <code>{@link package_class_member label}</code>
 * 
 * @author Brian Norman
 */
public class MovementProfiler implements Drawable {

   public static final int PROFILE_VEIWS = 5;
   public static final int ENEMY_LAG = -2;


   private AdvancedRobot robot;
   private RobotInfo info;

   private RobotData[] profiles;
   private int profiles_position;

   private RobotData enemy;
   private List<DataWave<GuessFactor, RobotData, RobotData>> enemyWaves;

   private static HashMap<String, TreeNode<GuessFactor, RobotData, RobotData>> dataTrees;
   private Segmenter<GuessFactor, RobotData, RobotData>[] segmenters;


   public MovementProfiler(AdvancedRobot myRobot, Segmenter<GuessFactor, RobotData, RobotData>[] segmenters) {
      init(myRobot, segmenters);
   }

   @SuppressWarnings("unchecked")
   public MovementProfiler(AdvancedRobot myRobot, List<Segmenter<GuessFactor, RobotData, RobotData>> segmenters) {
      init(myRobot, segmenters.toArray(new Segmenter[0]));
   }


   private void init(AdvancedRobot myRobot, Segmenter<GuessFactor, RobotData, RobotData>[] segmenters) {
      this.robot = myRobot;
      this.info = new RobotInfo(this.robot);
      this.profiles = new RobotData[PROFILE_VEIWS];
      this.profiles[profiles.length - 1] = new RobotData(this.robot);
      this.profiles_position = profiles.length;
      this.enemy = null;
      this.enemyWaves = new ArrayList<DataWave<GuessFactor, RobotData, RobotData>>();
      this.segmenters = segmenters;
      if (dataTrees == null)
         MovementProfiler.dataTrees = new HashMap<String, TreeNode<GuessFactor, RobotData, RobotData>>(info.getOthers());
      this.robot.addCustomEvent(new WaveTracker());
   }


   public void inEvent(Event event) {
      if (info.getOthers() == 1) {
         if (event instanceof ScannedRobotEvent)
            handleScannedRobot((ScannedRobotEvent) event);
         else if (event instanceof HitByBulletEvent)
            handleHitByBullet((HitByBulletEvent) event);
         else if (event instanceof BulletHitBulletEvent)
            handleBulletHitBullet((BulletHitBulletEvent) event);
      }
   }

   /**
    * A function that handles all onScannedRobot events that occur during the duration of the match. This function
    * should not be called directly but will be called when an event of this type is passed to <i>onEvent()</i>. <br>
    * <br>
    * A note on the code: Until the end of the function, the variable <i>enemy</i> refers to the previous turn. It is
    * updated at the very end of the function. This is done to make comparison of the current turn and the previous one.
    * 
    * @param event
    */
   private final void handleScannedRobot(ScannedRobotEvent event) {
      if (enemy == null) {
         this.enemy = new RobotData(event, robot);
         if (!dataTrees.containsKey(event.getName())) {
            dataTrees.put(event.getName(), new TreeNode<GuessFactor, RobotData, RobotData>(segmenters));
         }
      } else {
         double deltaEnergy = event.getEnergy() - enemy.getEnergy();
         double deltaVelocity = event.getVelocity() - enemy.getVelocity();
         if (enemy.getDeltaEnergy() == 0.1D && deltaEnergy == 0.1D) {
            enemyWaves.remove(enemyWaves.size() - 1);
         } else if (Utils.limit(-Rules.MAX_BULLET_POWER, deltaEnergy, -Rules.MIN_BULLET_POWER) == deltaEnergy
                    && Math.abs(deltaVelocity) <= Math.max(Math.abs(Rules.ACCELERATION), Math.abs(Rules.DECELERATION))
                    && Math.abs(enemy.getDeltaVelocity()) <= Math.max(Math.abs(Rules.ACCELERATION), Math.abs(Rules.DECELERATION))
                    && deltaEnergy != enemy.getDeltaEnergy()) {
            RobotData seenRobot = getProfile(ENEMY_LAG);
            double bulletHeading = Utils.getAngle(enemy, seenRobot);
            GuessFactor[] data = dataTrees.get(enemy.getName()).get(seenRobot, enemy).toArray(new GuessFactor[0]);
            enemyWaves.add(new DataWave<GuessFactor, RobotData, RobotData>(enemy.getX(), enemy.getY(), bulletHeading, Math.abs(deltaEnergy),
                                                                           enemy.getTime(), data, getProfile(ENEMY_LAG), (RobotData) enemy.clone()));
         }
         enemy.update(event, robot);
      }
   }

   private final void handleHitByBullet(HitByBulletEvent event) {
      handleBulletEvent(event.getBullet());
   }

   private final void handleBulletHitBullet(BulletHitBulletEvent event) {
      handleBulletEvent(event.getHitBullet());
   }

   private final void handleBulletEvent(Bullet b) {
      if (enemy != null && !enemy.isDead()) {
         DataWave<GuessFactor, RobotData, RobotData> wave = Utils.findWaveMatch(enemyWaves, b, robot.getTime());
         if (wave != null) {
            enemyWaves.remove(wave);
            GuessFactor gf = new GuessFactor(Utils.getGuessFactor(wave, wave.getView(), b));
            TreeNode<GuessFactor, RobotData, RobotData> tree = dataTrees.get(enemy.getName());
            if (tree != null) {
               tree.add(gf, wave.getView(), wave.getReference());
            }
         }
      }
   }

   private int getProfilesPosition(int difference) {
      return (profiles_position + difference) % profiles.length;
   }

   private RobotData getProfile(int difference) {
      return profiles[getProfilesPosition(difference)];
   }

   /**
    * Commands:
    * <ul>
    * <li><code>"-waves"</code> - draws all the waves that have been fired that are still active.</li>
    * <li><code>"-graph"</code> - draws the guess factor graph for the bullets that hit the robot.</li>
    * <li><code>"-3Dgraphs"</code> - prints 3D graphs for the bullets that hit the robot.</li>
    * <ul>
    */
   public void draw(RobocodeGraphicsDrawer grid, String commandString) {
      if (commandString.contains("-waves"))
         drawEnemyWaves(grid, commandString);
      if (commandString.contains("-graph"))
         drawGFGraph(grid, commandString);
      if (commandString.contains("-3Dgraphs"))
         drawLeaf3DGraphs(grid, commandString);
   }

   public void drawEnemyWaves(RobocodeGraphicsDrawer grid, String commandString) {
      for (DataWave<GuessFactor, RobotData, RobotData> w : enemyWaves)
         w.draw(grid, commandString);
   }

   public void drawLeaf3DGraphs(RobocodeGraphicsDrawer grid, String commandString) {
      if (dataTrees != null && enemy != null && dataTrees.get(enemy.getName()) != null)
         dataTrees.get(enemy.getName()).draw(grid, commandString);
   }

   public void drawGFGraph(RobocodeGraphicsDrawer grid, String commandString) {
      if (dataTrees != null && enemy != null && dataTrees.get(enemy.getName()) != null) {
         GuessFactor[] data = dataTrees.get(enemy.getName()).get(getProfile(0), enemy).toArray(new GuessFactor[0]);
         if (data != null) {
            double width = robot.getBattleFieldWidth() / 2.0D;
            double heigth = 10.0D;

            double py = heigth;

            for (double i = -width - 1; i <= width; i++) {
               if (i < RobotInfo.WIDTH / 2 && i > -RobotInfo.WIDTH / 2)
                  grid.setColor(Colors.YELLOW);
               else if (i > 0)
                  grid.setColor(Colors.GREEN);
               else
                  grid.setColor(Colors.RED);
               double pointHeigth = heigth;
               for (GuessFactor gf : data)
                  pointHeigth += 1000.0D / (Utils.sqr(i - width * gf.getGuessFactor()) + 100.0D);
               grid.drawLine(width + i - 1.0D, py, width + i, pointHeigth);
               py = pointHeigth;
            }
         }
      }
   }


   private class WaveTracker extends Condition {

      @Override
      public boolean test() {
         RobotData newRobot = new RobotData(getProfile(-1));
         newRobot.update(robot);
         profiles[getProfilesPosition(0)] = newRobot;
         profiles_position++;

         for (int i = 0; i < enemyWaves.size(); i++) {
            DataWave<GuessFactor, RobotData, RobotData> w = enemyWaves.get(i);
            if (w.testHit(robot.getX(), robot.getY(), robot.getTime() - 15) || !w.active(robot.getTime())) {
               enemyWaves.remove(w);
               i--;
            }
         }
         return false;
      }
   }

}
