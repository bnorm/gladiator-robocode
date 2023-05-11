package kid.movement.robot;

import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.io.PrintStream;

import kid.Utils;
import kid.data.RobotChooser;
import kid.data.factor.GuessFactor;
import kid.data.robot.*;
import kid.data.virtual.*;
import robocode.Event;
import robocode.RobocodeFileOutputStream;
import robocode.Robot;

// TODO documentation: class
// TODO code: method stubs

/**
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class MinimumRiskPoint extends Movement {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = -2185474595949395107L;

   private Point2D nextPosition;

   private RoundRectangle2D battleField;
   private double walldist = 20;
   private double cornerarc = 100;

   private int NUM_OF_GENERATED_POINTS = 144;
   private int CORNER_RISK = 2;
   private int BOT_RISK = 100;

   /**
    * @param myRobot
    */
   public MinimumRiskPoint(Robot myRobot) {
      super(myRobot);
      init(myRobot);
   }

   private void init(Robot myRobot) {
      nextPosition = new Point2D.Double(robot.getX(), robot.getY());
      battleField = new RoundRectangle2D.Double(walldist, walldist, robot.getBattleFieldWidth() - 2 * walldist, robot.getBattleFieldHeight() - 2
                                                                                                                * walldist, cornerarc, cornerarc);
   }

   @Override
   public void inEvent(Event event) {
   }

   @Override
   public void move(RobotData[] robots, VirtualBullet[] teammateBullets) {
      movement.setMoveToPoint(getPoint(robots, teammateBullets));
   }

   @Override
   public void move(RobotData[] robots, DataWave<GuessFactor, RobotData, RobotData>[] enemyWaves) {
   }

   @Override
   public Point2D.Double[] getMove(RobotData[] robots, VirtualBullet[] teammateBullets, long time) {
      return null;
   }

   public Point2D getPoint(RobotData[] robots, VirtualBullet[] teammateBullets) {
      double myX = info.getX();
      double myY = info.getY();
      long time = info.getTime();

      double minDist = info.distSq(RobotChooser.CLOSEST.getRobot(robot, robots));
      for (VirtualBullet b : teammateBullets)
         minDist = Math.min(2 * Utils.distSq(myX, myY, b.getX(time), b.getY(time)), minDist);
      minDist = Math.sqrt(minDist);

      double pointDist = Utils.distSq(nextPosition, myX, myY);
      double pointRisk = Double.POSITIVE_INFINITY;
      if (pointDist > Utils.sqr(20))
         pointRisk = risk(nextPosition, robots, teammateBullets);

      double dist = Utils.random(minDist / 2, minDist);

      for (double a = 0; a < Utils.CIRCLE; a += Utils.CIRCLE / NUM_OF_GENERATED_POINTS) {
         double angle = Utils.random(a, a + Utils.CIRCLE / NUM_OF_GENERATED_POINTS);
         Point2D point = Utils.getPoint(myX, myY, dist, angle);

         if (battleField.contains(point)) {
            double risk = risk(point, robots, teammateBullets);
            if (risk < pointRisk) {
               nextPosition = point;
               pointRisk = risk;
            }
         }

      }
      return nextPosition;
   }

   private double risk(Point2D point, RobotData[] robots, VirtualBullet[] teammateBullets) {
      double myX = info.getX();
      double myY = info.getY();
      long time = info.getTime();
      double angle = Utils.angle(myX, myY, point.getX(), point.getY());
      double pointRisk = 0.0D;
      for (RobotData r : robots) {
         if (!r.isDead()) {
            double robotRisk = r.getEnergy();
            if (!(r instanceof TeammateData))
               robotRisk *= (1 + Math.abs(Utils.cos(angle - Utils.angle(myX, myY, r.getX(), r.getY()))));
            else
               robotRisk = BOT_RISK / 2.0D;
            robotRisk /= Utils.distSq(point, r.getX(), r.getY());

            boolean amiclosest = true;
            double myDist = Utils.distSq(myX, myY, r.getX(), r.getY());
            for (int j = 0; j < robots.length && amiclosest; j++) {
               if (!r.getName().equals(robots[j].getName()) && Utils.distSq(r.getX(), r.getY(), robots[j].getX(), robots[j].getY()) < myDist)
                  amiclosest = false;
            }
            if (amiclosest)
               robotRisk *= Utils.sqr(1 + Math.abs(Utils.cos(angle - Utils.angle(myX, myY, r.getX(), r.getY()))));

            pointRisk += robotRisk;
         }
      }

      for (VirtualBullet b : teammateBullets) {
         double bulletRisk = 100.0D;
         double nowdist = Utils.distSq(myX, myY, b.getX(time), b.getY(time));
         double futuredist = Utils.distSq(myX, myY, b.getX(time + 1), b.getY(time + 1));
         if (futuredist < nowdist) {
            bulletRisk /= Utils.sqr(Utils.distSq(point, b.getX(time), b.getY(time)));
            pointRisk += bulletRisk;
         }
      }

      pointRisk += info.getOthers() / Utils.distSq(point, info.getBattleFieldWidth() / 2, info.getBattleFieldHeight() / 2);

      pointRisk += CORNER_RISK / Utils.distSq(point, info.getBattleFieldWidth(), info.getBattleFieldHeight());
      pointRisk += CORNER_RISK / Utils.distSq(point, 0.0D, info.getBattleFieldHeight());
      pointRisk += CORNER_RISK / Utils.distSq(point, 0.0D, 0.0D);
      pointRisk += CORNER_RISK / Utils.distSq(point, info.getBattleFieldWidth(), 0.0D);

      return pointRisk;
   }

   @Override
   public String getName() {
      return new String("Minimum Risk Point Movement");
   }

   @Override
   public String getType() {
      return new String("? Movement");
   }

   public void print(PrintStream console) {
   }

   public void print(RobocodeFileOutputStream output) {
   }

}
