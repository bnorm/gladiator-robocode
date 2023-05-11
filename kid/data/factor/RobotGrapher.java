package kid.data.factor;

import java.util.*;

import kid.*;
import kid.data.*;
import kid.data.robot.RobotData;
import kid.data.virtual.DataWave;
import kid.managers.RobotManager;
import kid.segmentation.*;
import robocode.*;

public class RobotGrapher implements Drawable {

   private AdvancedRobot robot;
   private RobotManager robots;

   private static HashMap<String, TreeNode<GuessFactor, RobotData, RobotData>> virtualTrees = null;
   private static HashMap<String, TreeNode<GuessFactor, RobotData, RobotData>> realTrees = null;
   private Segmenter<GuessFactor, RobotData, RobotData>[] segmenters;

   private List<DataWave<GuessFactor, RobotData, RobotData>> waves;

   public RobotGrapher(AdvancedRobot myRobot, Segmenter<GuessFactor, RobotData, RobotData>[] segmenters) {
      init(myRobot, segmenters);
   }

   @SuppressWarnings("unchecked")
   public RobotGrapher(AdvancedRobot myRobot, List<Segmenter<GuessFactor, RobotData, RobotData>> segmenters) {
      init(myRobot, segmenters.toArray(new Segmenter[0]));
   }

   private void init(AdvancedRobot myRobot, Segmenter<GuessFactor, RobotData, RobotData>[] segmenters) {
      this.robot = myRobot;
      this.robots = new RobotManager(myRobot);
      if (virtualTrees == null)
         virtualTrees = new HashMap<String, TreeNode<GuessFactor, RobotData, RobotData>>(robot.getOthers());
      if (realTrees == null)
         realTrees = new HashMap<String, TreeNode<GuessFactor, RobotData, RobotData>>(robot.getOthers());
      this.segmenters = segmenters;
      this.waves = new ArrayList<DataWave<GuessFactor, RobotData, RobotData>>();
      this.robot.addCustomEvent(new WaveTracker());
   }

   public void fire(RobotData target, double firePower) {
      RobotData myRobot = new RobotData(robot);
      GuessFactor[] data = virtualTrees.get(target.getName()).get(myRobot, target).toArray(new GuessFactor[0]);
      waves.add(new DataWave<GuessFactor, RobotData, RobotData>(robot.getX(), robot.getY(), Utils.getAngle(myRobot, target), firePower,
                                                                robot.getTime(), data, target, myRobot));
   }

   public void fire(Bullet bullet, RobotData target) {
      if (bullet != null) {
         RobotData myRobot = new RobotData(robot);
         GuessFactor[] data = realTrees.get(target.getName()).get(myRobot, target).toArray(new GuessFactor[0]);
         waves.add(new DataWave<GuessFactor, RobotData, RobotData>(bullet, robot, target, data, target, myRobot));
      }
   }

   public GuessFactor[] getData(RobotData view, RobotData reference) {
      return virtualTrees.get(view.getName()).get(view, reference).toArray(new GuessFactor[0]);
   }

   public void inEvent(Event event) {
      robots.inEvent(event);
      if (event instanceof ScannedRobotEvent)
         handleScannedRobot((ScannedRobotEvent) event);
      else if (event instanceof BulletHitEvent)
         handleBulletHit((BulletHitEvent) event);
      else if (event instanceof BulletHitBulletEvent)
         handleBulletHitBullet((BulletHitBulletEvent) event);
   }

   private final void handleScannedRobot(ScannedRobotEvent event) {
      String name = event.getName();
      if (!virtualTrees.containsKey(name))
         virtualTrees.put(name, new TreeNode<GuessFactor, RobotData, RobotData>(segmenters));
      if (!realTrees.containsKey(name))
         realTrees.put(name, new TreeNode<GuessFactor, RobotData, RobotData>(segmenters));
   }

   private final void handleBulletHit(BulletHitEvent event) {
      DataWave<GuessFactor, RobotData, RobotData> wave = Utils.findWaveMatch(waves, event.getBullet(), event.getTime());
      if (wave != null) {
         RobotData enemy = robots.getRobot(event.getName());
         GuessFactor gf = new GuessFactor(Utils.getGuessFactor(wave, enemy, event.getBullet()));
         TreeNode<GuessFactor, RobotData, RobotData> tree = realTrees.get(enemy.getName());
         if (tree != null)
            tree.add(gf, wave.getView(), wave.getReference());
         tree = virtualTrees.get(enemy.getName());
         if (tree != null)
            tree.add(gf, wave.getView(), wave.getReference());
         waves.remove(wave);
      }
   }

   private final void handleBulletHitBullet(BulletHitBulletEvent event) {
      waves.remove(Utils.findWaveMatch(waves, event.getBullet(), event.getTime()));
   }

   /**
    * Commands:
    * <ul>
    * <li><code>"-real"</code> - prints 3D graphs for the bullets that hit the robot.</li>
    * <li><code>"-virtual"</code> - draws 3D graphs for the virtual waves that hit the robot.</li>
    * <li><code>"-waves"</code> - draws all the waves that have been fired that are still active.</li>
    * <ul>
    */
   @Override
   public void draw(RobocodeGraphicsDrawer grid, String commandString) {
      if (commandString.contains("-real"))
         drawVirtual3DGraphs(grid, commandString, robots.getEnemy(RobotChooser.CLOSEST));
      if (commandString.contains("-virtual"))
         drawVirtual3DGraphs(grid, commandString, robots.getEnemy(RobotChooser.CLOSEST));
      if (commandString.contains("-waves"))
         drawWaves(grid, commandString);
   }

   public void drawWaves(RobocodeGraphicsDrawer grid, String commandString) {
      for (DataWave<GuessFactor, RobotData, RobotData> wave : waves)
         wave.draw(grid, "");
   }

   public void drawVirtual3DGraphs(RobocodeGraphicsDrawer grid, String commandString, RobotData enemy) {
      if (enemy != null && virtualTrees.get(enemy.getName()) != null)
         virtualTrees.get(enemy.getName()).draw(grid, "");
   }

   public void drawReal3DGraphics(RobocodeGraphicsDrawer grid, String commandString, RobotData enemy) {
      if (enemy != null && virtualTrees.get(enemy.getName()) != null)
         virtualTrees.get(enemy.getName()).draw(grid, "");
   }


   private class WaveTracker extends Condition {

      @Override
      public boolean test() {
         for (int i = 0; i < waves.size(); i++) {
            DataWave<GuessFactor, RobotData, RobotData> w = waves.get(i);
            long time = robot.getTime();
            RobotData enemy = w.getView();
            if (w.testHit(w.getView(), time)) {
               GuessFactor gf = new GuessFactor(Utils.getGuessFactor(w, enemy, robots.getRobot(enemy.getName())));
               TreeNode<GuessFactor, RobotData, RobotData> tree = virtualTrees.get(enemy.getName());
               if (tree != null)
                  tree.add(gf, w.getView(), w.getReference());
               waves.remove(w);
               i--;
            } else if (!w.active(time)) {
               waves.remove(w);
               i--;
            }
         }
         return false;
      }
   }

}
