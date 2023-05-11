package kid.movement.robot;

import java.awt.geom.Point2D;
import java.io.PrintStream;

import kid.Utils;
import kid.data.factor.GuessFactor;
import kid.data.robot.*;
import kid.data.virtual.DataWave;
import robocode.*;

// TODO documentation: class

/**
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class Perpendicular extends Movement {

   /**
    * Determines if a de-serialized file is compatible with this class.<BR>
    * <BR>
    * Maintainers must change this value if and only if the new version of this class is
    * not compatible with old versions.
    */
   private static final long serialVersionUID = 4332573743463836316L;

   private double safeDistance;

   public Perpendicular(Robot myRobot) {
      super(myRobot);
      init(myRobot);
   }

   private void init(Robot myRobot) {
      safeDistance = Math.min(info.getBattleFieldHeight(), info.getBattleFieldWidth()) * 0.75D;
   }

   /**
    * @see kid.upgrade.movement.Movement#move(kid.upgrade.data.robot.RobotData[])
    */
   @Override
   public void move(final RobotData[] robots) {
      double perpenAngle = 0;
      double totalWeight = 0;
      double minRobotDist = Double.POSITIVE_INFINITY;
      for (RobotData r : robots) {
         if (!r.isDead()) {
            minRobotDist = Math.min(info.distToSq(r), minRobotDist);
            double robotAngle = info.angleTo(r);
            double robotWeight = (r.getEnergy() / info.distToSq(r));
            perpenAngle += robotAngle * robotWeight;
            totalWeight += robotWeight;
         }
      }
      if (totalWeight != 0) {
         perpenAngle /= totalWeight;
         movement.setAhead(movement.setTurnPerpenToAnglewBFwDC(perpenAngle, Math.sqrt(minRobotDist),
                                                               safeDistance / Math.max(info.getOthers() / 3.0D, 1.0D))
                           * Double.POSITIVE_INFINITY);
         movement.setSmoothWalls();
      }
   }

   
   public RobotVector move(final RobotVector myRobot, final RobotData[] robots) {
      double perpenAngle = 0;
      double totalWeight = 0;
      double minRobotDist = Double.POSITIVE_INFINITY;
      for (RobotData r : robots) {
         if (!r.isDead()) {
            minRobotDist = Math.min(info.distToSq(r), minRobotDist);
            double robotAngle = Utils.getAngle(myRobot.getX(), myRobot.getY(), r.getX(), r.getY());
            double robotWeight = (r.getEnergy() / Utils.getDistSq(myRobot.getX(), myRobot.getY(), r.getX(), r.getY()));
            perpenAngle += robotAngle * robotWeight;
            totalWeight += robotWeight;
         }
      }
      if (totalWeight != 0) {
         perpenAngle /= totalWeight;
         

      }
      return null;
   }

   /**
    * @see kid.upgrade.movement.Movement#move(kid.upgrade.data.robot.RobotData[],
    *      kid.upgrade.movement.MovementProfiler)
    */
   @Override
   public void move(final RobotData[] robots, final DataWave<GuessFactor, RobotData, RobotData>[] enemyWaves) {
      if (robots.length > 1)
         move(robots);
      else {
         move(robots);
      }
   }

   /**
    * @see kid.upgrade.movement.Movement#getMove(kid.upgrade.data.robot.RobotData[], long)
    */
   @Override
   public Point2D[] getMove(final RobotData[] robots, final long time) {
      return null;
   }

   /**
    * @see kid.upgrade.movement.Movement#inEvent(robocode.Event)
    */
   @Override
   public void inEvent(final Event event) {
      // TODO method stub

   }


   /**
    * @see kid.upgrade.movement.Movement#getName()
    */
   @Override
   public String getName() {
      return new String("Perpendicular Movement");
   }

   /**
    * @see kid.upgrade.movement.Movement#getType()
    */
   @Override
   public String getType() {
      return new String("One-On-One Movement");
   }


   /**
    * @see kid.upgrade.data.Printable#print(java.io.PrintStream)
    */
   public void print(PrintStream console) {
      // TODO method stub

   }

   /**
    * @see kid.upgrade.data.Printable#print(robocode.RobocodeFileOutputStream)
    */
   public void print(RobocodeFileOutputStream output) {
      // TODO method stub

   }

   @Override
   protected void finalize() throws Throwable {
      super.finalize();
   }
}
