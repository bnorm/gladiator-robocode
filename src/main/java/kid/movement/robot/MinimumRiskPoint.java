package kid.movement.robot;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.io.PrintStream;
import java.util.Arrays;

import kid.data.RobotChooser;
import kid.robot.RobotData;
import kid.robot.TeammateData;
import kid.utils.Utils;
import kid.virtual.VirtualBullet;
import robocode.Event;
import robocode.RobocodeFileOutputStream;
import robocode.Robot;

// TODO documentation: class - MinimumRiskPoint : Movement
// TODO code: methods - MinimumRiskPoint : Movement

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
   private final double walldist = 40;
   private final double cornerarc = 100;

   private final int NUM_OF_GENERATED_POINTS = 90;
   private final int CORNER_RISK = 2;
   private final int BOT_RISK = 100;

   /**
    * @param myRobot
    */
   public MinimumRiskPoint(final Robot myRobot) {
      super(myRobot);
      init(myRobot);
   }

   private void init(final Robot myRobot) {
      nextPosition = new Point2D.Double(robot.getX(), robot.getY());
      battleField = new RoundRectangle2D.Double(walldist, walldist, robot.getBattleFieldWidth() - 2 * walldist, robot.getBattleFieldHeight() - 2 * walldist, cornerarc, cornerarc);
   }

   @Override
   public void inEvent(final Event event) {
   }

   @Override
   public void move(final RobotData[] robots, final VirtualBullet[] teammateBullets) {
      movement.setMoveToPoint(getPoint(robots, teammateBullets));
   }

   @Override
   public Point2D.Double[] getMove(final RobotData[] robots, final VirtualBullet[] teammateBullets, final long time) {
      return null;
   }

   public Point2D getPoint(final RobotData[] robots, final VirtualBullet[] teammateBullets) {
      double myX = info.getX();
      double myY = info.getY();
      // long time = info.getTime();

      double minDist = info.distSq(RobotChooser.CLOSEST.getRobot(robot, Arrays.asList(robots)));
      // for (VirtualBullet b : teammateBullets)
      // minDist = Math.min(2 * Utils.distSq(myX, myY, b.getX(time),
      // b.getY(time)), minDist);
      minDist = Utils.sqrt(minDist);

      double pointDist = Utils.distSq(nextPosition, myX, myY);
      double pointRisk = Double.POSITIVE_INFINITY;
      if (pointDist > Utils.sqr(20))
         pointRisk = risk(nextPosition, robots, teammateBullets);

      double dist = Utils.random(minDist / 2, minDist);

      for (double a = 0; a < Utils.CIRCLE; a += Utils.CIRCLE / NUM_OF_GENERATED_POINTS) {
         double angle = Utils.random(a, a + Utils.CIRCLE / NUM_OF_GENERATED_POINTS);
         Point2D point = Utils.getPoint(myX, myY, dist, angle);

         if (battleField.contains(point)) {
            double risk = risk(point, angle, robots, teammateBullets);
            if (risk < pointRisk) {
               nextPosition = point;
               pointRisk = risk;
            }
         }

      }
      return nextPosition;
   }

   private double risk(final Point2D point, final RobotData[] robots, final VirtualBullet[] teammateBullets) {
      return risk(point, info.angle(point), robots, teammateBullets);
   }

   private double risk(final Point2D point, final double angle, final RobotData[] robots, final VirtualBullet[] teammateBullets) {
      double myX = info.getX();
      double myY = info.getY();
      long time = info.getTime();
      double pointRisk = 0.0D;
      Line2D path = new Line2D.Double(myX, myY, point.getX(), point.getY());
      for (RobotData r : robots) {
         if (!r.isDead()) {
            if (path.intersects(r.getRectangle())) {
               return Double.POSITIVE_INFINITY;
            } else {
               double robotRisk = BOT_RISK;
               if (!(r instanceof TeammateData)) {
                  robotRisk = r.getEnergy();
                  robotRisk *= (1 + Math.abs(Utils.cos(angle - Utils.angle(myX, myY, r.getX(), r.getY()))));

                  // boolean amiclosest = true;
                  // double myDist = Utils.distSq(myX, myY, r.getX(), r.getY());
                  // for (int i = 0; i < robots.length && amiclosest; i++) {
                  // if (!r.getName().equals(robots[i].getName()) &&
                  // Utils.distSq(r.getX(),
                  // r.getY(), robots[i].getX(),
                  // robots[i].getY()) < myDist)
                  // amiclosest = false;
                  // }
                  // if (amiclosest)
                  // robotRisk *= Utils.sqr(1 + Math.abs(Utils.cos(angle -
                  // Utils.angle(myX, myY,
                  // r.getX(), r.getY()))));
               } else
                  robotRisk /= 2.0D;

               robotRisk /= Utils.distSq(point, r.getX(), r.getY());
               pointRisk += robotRisk;
            }
         }
      }

      for (VirtualBullet b : teammateBullets) {
         double bulletRisk = 10.0D;
         double heading = b.getHeading();
         double angleBullet = Utils.angle(myX, myY, b.getX(time), b.getY(time));
         if (Math.abs(angleBullet - heading) < Utils.maxEscapeAngle(b.getVelocity())) {
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

   public void print(final PrintStream console) {
   }

   public void print(final RobocodeFileOutputStream output) {
   }

}
