package kid.robot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;

import kid.cluster.Comparison;
import kid.cluster.Space;
import kid.data.Drawable;
import kid.data.factor.GuessFactor;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.utils.Utils;
import kid.virtual.DataWave;
import robocode.AdvancedRobot;
import robocode.Condition;
import robocode.Rules;

public class EnemyProfile implements Drawable {

   private AdvancedRobot robot;
   private RobotData reference;
   private EnemyData enemy;
   private ArrayList<DataWave<GuessFactor, RobotData, RobotData>> waves;

   private Space<GuessFactor, RobotData, RobotData> space = null;
   // private Comparison[] comparisons = null;
   private int dataPerWave = 10;

   public EnemyProfile(AdvancedRobot myRobot, EnemyData enemy, Comparison[] comparisons) {
      init(myRobot, enemy, comparisons, -1);
   }

   public EnemyProfile(AdvancedRobot myRobot, EnemyData enemy, Comparison[] comparisons, int dataPerWave) {
      init(myRobot, enemy, comparisons, dataPerWave);
   }

   private void init(AdvancedRobot r, EnemyData e, Comparison[] c, int d) {
      robot = r;
      reference = new RobotData(r);
      enemy = e;
      // comparisons = c;
      if (d > 0)
         dataPerWave = d;
      r.addCustomEvent(new WaveWatcher(r.getTime()));
      waves = new ArrayList<DataWave<GuessFactor, RobotData, RobotData>>();
      space = new Space<GuessFactor, RobotData, RobotData>(c);
   }

   public void update(AdvancedRobot r) {
      reference = new RobotData(r);
      r.addCustomEvent(new WaveWatcher(r.getTime()));
      waves = new ArrayList<DataWave<GuessFactor, RobotData, RobotData>>();
   }

   public void fire(double power) {
      power = Utils.limit(Rules.MIN_BULLET_POWER, power, Rules.MAX_BULLET_POWER);

      RobotData view = enemy.copy();
      RobotData reference = this.reference.copy();

      GuessFactor[] data = space.getCluster(view, reference, dataPerWave).toArray(new GuessFactor[0]);

      waves.add(new DataWave<GuessFactor, RobotData, RobotData>(reference, view, power, data, view, reference));
   }

   public GuessFactor[] getCluster(int size) {
      return space.getCluster(enemy, reference.copy(), size).toArray(new GuessFactor[0]);
   }

   public void print() {
      space.print(System.out);
   }

   public void draw(final RGraphics grid) {
      if (DrawMenu.getValue("Waves", "Targeting")) {
         for (DataWave<GuessFactor, RobotData, RobotData> w : waves) {
            // w.draw(grid);

            double dist = w.getDist(grid.getTime()) - 5.0;
            GuessFactor[] data = w.getData();
            double[] bins = new double[101];

            for (GuessFactor gf : data) {
               for (int i = 0; i < bins.length; i++) {
                  bins[i] += Utils.limit(0.0, 10.0 / (30.0 * Utils.sqr(Utils.getGuessFactor(i, bins.length)
                        - gf.getGuessFactor()) + 1.0) - 4.99, 5.0);
               }
            }

            double max = 1;
            for (double i : bins)
               max = Math.max(max, i);

            grid.setStroke(new BasicStroke(1.5f));
            float hue = 0.0f; // RED
            hue = (float) (75.0 / 240.0); // GREEN

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

   private class WaveWatcher extends Condition {

      private long time;

      public WaveWatcher(final long curTime) {
         this.time = curTime;
      }

      @Override
      public boolean test() {
         for (int i = 0; i < waves.size(); i++) {
            DataWave<GuessFactor, RobotData, RobotData> w = waves.get(i);
            if (w.testHit(enemy.getX(), enemy.getY(), reference.getTime())) {
               GuessFactor gf = new GuessFactor(Utils.getGuessFactor(w, w.getView(), enemy));
               space.add(gf, w.getView(), w.getReference());
               waves.remove(i--);
            }
            if (w.getCreationTime() == time) {
               w.getReference().update(robot);
            }
         }
         time++;
         reference.update(robot);
         return false;
      }
   }

}
