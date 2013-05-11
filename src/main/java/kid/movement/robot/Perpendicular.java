package kid.movement.robot;

import java.awt.geom.Point2D;
import java.io.PrintStream;
import java.util.ArrayList;

import kid.data.factor.GuessFactor;
import kid.graphics.Colors;
import kid.info.RobotInfo;
import kid.movement.MovementProfiler;
import kid.robot.RobotData;
import kid.robot.RobotVector;
import kid.utils.Utils;
import kid.virtual.DataWave;
import kid.virtual.VirtualBullet;
import kid.virtual.VirtualWave;
import robocode.Event;
import robocode.RobocodeFileOutputStream;
import robocode.Robot;
import robocode.Rules;

// TODO documentation: class

/**
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class Perpendicular extends Movement {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = 4332573743463836316L;

   private double            safeDistance;

   private Point2D[]         forwards         = new Point2D[0];
   private Point2D[]         stop             = new Point2D[0];
   private Point2D[]         backwards        = new Point2D[0];

   private static final int  NUM_BINS         = 101;

   private int               points           = 4;

   public Perpendicular(final Robot myRobot) {
      super(myRobot);
      init(myRobot);
   }

   private void init(final Robot myRobot) {
      safeDistance = Math.min(Rules.RADAR_SCAN_RADIUS, Math
            .min(info.getBattleFieldHeight(), info.getBattleFieldWidth())) * 0.7;
   }

   public void move(RobotData enemy, MovementProfiler profile) {
      ArrayList<DataWave<GuessFactor, RobotData, RobotData>> enemyWaves = profile.getWaves();
      double targetVelocity = -Utils.sign(info.getMoveRemaining()) * Rules.MAX_VELOCITY;

      double x = info.getX();
      double y = info.getY();
      long time = info.getTime();
      VirtualWave wave = null;
      if (enemyWaves.size() > 0) {
         wave = enemyWaves.get(0);
         for (VirtualWave w : enemyWaves) {
            if (w.getDistToImpact(x, y, time) < wave.getDistToImpact(x, y, time))
               wave = w;
         }
      }

      if (wave != null && enemy.distSq(x, y) > 40000) {

         robot.getGraphics().setColor(Colors.GREEN);
         forwards = getImpactPoints(Utils.sign(info.getMoveRemaining()) * Rules.MAX_VELOCITY, enemyWaves, points);
         robot.getGraphics().setColor(Colors.YELLOW);
         stop = getImpactPoints(0.0, enemyWaves, points);
         robot.getGraphics().setColor(Colors.RED);
         backwards = getImpactPoints(-Utils.sign(info.getMoveRemaining()) * Rules.MAX_VELOCITY, enemyWaves, points);

         double forwardsDanger = getDanger(forwards, enemyWaves, profile.getLatest(), profile.getLatestHits());
         double stopDanger = getDanger(stop, enemyWaves, profile.getLatest(), profile.getLatestHits());
         double backwardsDanger = getDanger(backwards, enemyWaves, profile.getLatest(), profile.getLatestHits());

         double minDanger = Math.min(forwardsDanger, Math.min(stopDanger, backwardsDanger));
         if (minDanger == forwardsDanger) {
            targetVelocity = Utils.sign(info.getMoveRemaining()) * Rules.MAX_VELOCITY;
         } else if (minDanger == stopDanger) {
            targetVelocity = 0.0;
         }

         movement.setAhead(targetVelocity * Double.POSITIVE_INFINITY);
         movement.setTurnPerpenToXYwBFwDCwRM(wave.getStartX(), wave.getStartY(), safeDistance);
      } else {
         movement.setAhead(movement.setTurnPerpenToRobotwBFwDC(enemy, safeDistance) * Double.POSITIVE_INFINITY);
      }
      movement.setSmoothWalls();
   }

   private double getDanger(Point2D[] position, ArrayList<DataWave<GuessFactor, RobotData, RobotData>> enemyWaves,
         GuessFactor[] latest, GuessFactor[] latestHits) {
      double danger = Double.NEGATIVE_INFINITY;
      if (position.length > 0) {
         danger = 0.0;
         long time = info.getTime();

         for (int i = 0; i < enemyWaves.size() && i < position.length; i++) {
            DataWave<GuessFactor, RobotData, RobotData> w = enemyWaves.get(i);
            if (w.getDistToImpact(position[0].getX(), position[0].getY(), time) > 0) {
               double pointDanger = 0.0;
               double x = position[i].getX();
               double y = position[i].getY();
               double dist = Utils.dist(x, y, w.getStartX(), w.getStartY());

               int index = Utils.getIndex(Utils.getGuessFactor(w, w.getView(), x, y), NUM_BINS);
               int indexWidth = Utils.getIndex(Utils.atan(RobotInfo.WIDTH / dist) / w.getMaxEscapeAngle(), NUM_BINS)
                     - Utils.getIndex(0, NUM_BINS);
               index -= indexWidth;
               for (int k = index; k <= index + 2 * indexWidth; k++) {
                  double indexGF = Utils.getGuessFactor(k, NUM_BINS);
                  for (GuessFactor gf : w.getData())
                     pointDanger += Utils.getGFDanger(gf, indexGF);
                  // for (GuessFactor gf : latest)
                  // if (gf != null)
                  // pointDanger += Utils.getGFDanger(gf, indexGF);
                  // for (GuessFactor gf : latestHits)
                  // if (gf != null)
                  // pointDanger += Utils.getGFDanger(gf, indexGF);
               }
               pointDanger /= Utils.sqr(w.getDistToImpact(x, y, time));
               pointDanger /= Utils.sqr(i + 1);
               danger += pointDanger;
            }
         }
      }
      return danger;
   }

   private Point2D[] getImpactPoints(double targetVelocity,
         ArrayList<DataWave<GuessFactor, RobotData, RobotData>> enemyWaves, int n) {
      Point2D[] points = new Point2D[Math.min(enemyWaves.size(), n)];
      if (enemyWaves.size() > 0) {
         VirtualWave[] waves = enemyWaves.toArray(new VirtualWave[0]);

         double x = info.getX();
         double y = info.getY();
         double h = info.getHeading();
         double v = info.getVelocity();
         if (targetVelocity < 0) {
            h = Utils.oppositeRelative(h);
            v = -v;
            targetVelocity = -targetVelocity;
         }
         long time = info.getTime() + 2;

         double bfw = info.getBattleFieldWidth(), bfh = info.getBattleFieldHeight();

         for (int i = 0; i < points.length; i++) {
            boolean hit = false;

            while (!hit) {
               for (int j = 0; j < waves.length - i && !hit; j++) {
                  VirtualWave w = waves[j];
                  if (w.getDistSq(time) > Utils.distSq(w.getStartX(), w.getStartY(), x, y)) {
                     points[i] = new Point2D.Double(x, y);

                     robot.getGraphics().fillOval((int) x - 1, (int) y - 1, 3, 3);
                     VirtualWave temp = waves[waves.length - (i + 1)];
                     waves[waves.length - (i + 1)] = w;
                     waves[j] = temp;

                     hit = true;
                  }
               }

               time++;
               h = movement.setSmoothWalls(x, y, h);
               v = RobotInfo.getFutureVelocity(v, targetVelocity);
               x += Utils.getDeltaX(v, h);
               y += Utils.getDeltaY(v, h);
               x = Utils.limit(RobotInfo.MIN_WALL_DIST, x, bfw - RobotInfo.MIN_WALL_DIST);
               y = Utils.limit(RobotInfo.MIN_WALL_DIST, y, bfh - RobotInfo.MIN_WALL_DIST);
            }
         }
      }
      return points;
   }

   @Override
   public void move(RobotData[] robots, VirtualBullet[] teammateBullets) {
      long time = robot.getTime();
      double perpenAngle = 0;
      double totalWeight = 0;
      double minDist = Double.POSITIVE_INFINITY;
      for (RobotData r : robots) {
         if (!r.isDead()) {
            minDist = Math.min(info.distSq(r), minDist);
            double robotAngle = Utils.absolute(info.angle(r)) % 180.0;
            double robotWeight = (r.getEnergy() / info.distSq(r));
            perpenAngle += robotAngle * robotWeight;
            totalWeight += robotWeight;
         }
      }
      for (VirtualBullet b : teammateBullets) {
         minDist = Math.min(info.distSq(b.getX(time), b.getY(time)), minDist);
         double bulletAngle = Utils.absolute(info.angle(b.getX(time), b.getY(time))) % 180.0;
         double bulletWeight = (1000 / Utils.sqr(info.distSq(b.getX(time), b.getY(time))));
         perpenAngle += bulletAngle * bulletWeight;
         totalWeight += bulletWeight;
      }
      if (totalWeight != 0) {
         perpenAngle /= totalWeight;
         double safedist = safeDistance / Math.max(info.getOthers() / 3.0D, 1.0D);
         movement.setAhead(Double.POSITIVE_INFINITY
               * movement.setTurnPerpenToAnglewBFwDC(perpenAngle, Utils.sqrt(minDist), safedist));
         movement.setSmoothWalls();
      }
   }

   public RobotVector move(RobotVector myRobot, RobotData[] robots) {
      double perpenAngle = 0;
      double totalWeight = 0;
      double minRobotDist = Double.POSITIVE_INFINITY;
      for (RobotData r : robots) {
         if (!r.isDead()) {
            minRobotDist = Math.min(info.distSq(r), minRobotDist);
            double robotAngle = Utils.absolute(Utils.angle(myRobot.getX(), myRobot.getY(), r.getX(), r.getY())) % 180.0;
            double robotWeight = (r.getEnergy() / Utils.distSq(myRobot.getX(), myRobot.getY(), r.getX(), r.getY()));
            perpenAngle += robotAngle * robotWeight;
            totalWeight += robotWeight;
         }
      }
      if (totalWeight != 0) {
         perpenAngle /= totalWeight;
      }
      return null;
   }

   @Override
   public Point2D[] getMove(RobotData[] robots, VirtualBullet[] teammateBullets, long time) {
      return null;
   }

   @Override
   public void inEvent(Event event) {
      // TODO method stub
   }

   @Override
   public String getName() {
      return new String("Perpendicular Movement");
   }

   @Override
   public String getType() {
      return new String("One-On-One Movement");
   }

   public void print(PrintStream console) {
      // TODO method stub
   }

   public void print(RobocodeFileOutputStream output) {
      // TODO method stub
   }

}
