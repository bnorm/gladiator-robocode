package bnorm.base;

import bnorm.robots.IRobotSnapshot;
import bnorm.utils.Trig;
import bnorm.utils.Utils;
import robocode.Robot;
import robocode.Rules;

/**
 * An abstract representation of the radar on a {@link Robot}. This class allows the the robot to
 * perform some basic turning and firing by utilizing some convince functions.
 * 
 * @author Brian Norman
 * @version 1.0
 */
public class Radar extends Base {

   /**
    * The turning rate of the radar measured in radians, which is 45 degrees/turn. Note, that if
    * setAdjustRadarForRobotTurn(true) and/or setAdjustRadarForGunTurn(true) has been called, the
    * radar turn is independent of the robot and/or gun turn. If both methods has been set to true,
    * the radar moves relatively to the screen. If setAdjustRadarForRobotTurn(false) and/or
    * setAdjustRadarForGunTurn(false) has been called or not called at all (this is the default),
    * then the radar turn is dependent on the robot and/or gun turn, and in this case the radar
    * moves relatively to the gun and/or robot body.
    * 
    * @see Robot#setAdjustGunForRobotTurn(boolean)
    * @see Robot#setAdjustRadarForGunTurn(boolean)
    */
   public static final double RADAR_TURN_RATE   = Rules.RADAR_TURN_RATE_RADIANS;

   /**
    * The maximum distance in pixels that a robot can scan.
    */
   public static final double RADAR_SCAN_LENGTH = 1200.0D;

   /**
    * Constructs a new {@link Radar} class that gives the {@link Robot} some convince functions for
    * turning.
    * 
    * @param robot
    *           the {@link Robot} creating the class.
    */
   public Radar(Robot robot) {
      super(robot);
   }

   // --------------------
   // Information Commands
   // --------------------

   /**
    * Returns the direction that the robot's radar is facing, in radians. The value returned will be
    * between 0 and 2*PI (is excluded).
    * <p>
    * Note that the heading in Robocode is like a compass, where 0 means North, PI/2 means East, PI
    * means South, and 3/2*PI means West.
    * 
    * @return the direction that the robot's radar is facing, in radians.
    */
   @Override
   public double getHeading() {
      return Math.toRadians(robot_.getRadarHeading());
   }

   // -----------------
   // Movement Commands
   // -----------------

   /**
    * Makes the radar turn right a specified angle in radians before returning. If an angle of
    * <code>0</code> is specified, no action is taken.
    * 
    * @param a
    *           the angle to turn.
    */
   public void turnRight(double a) {
      if (a != 0.0 && Math.abs(a) != Double.POSITIVE_INFINITY) {
         robot_.turnRadarRight(Math.toDegrees(a));
      }
   }

   /**
    * Makes the radar turn left a specified angle in radians before returning. If an angle of
    * <code>0</code> is specified, no action is taken.
    * 
    * @param a
    *           the angle to turn.
    */
   public void turnLeft(double a) {
      turnRight(-a);
   }

   /**
    * Makes the radar turn a specified angle in radians before returning. A positive angle will
    * cause the radar to turn to the right, a negative angle will cause the radar to turn to the
    * left, and if an angle of <code>0</code> is specified, no action is taken.
    * 
    * @param a
    *           the angle to turn.
    */
   public void turn(double a) {
      turnRight(a);
   }

   /**
    * Makes the radar of the {@link Robot} turn to a specified angle.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param angle
    *           the angle for the radar to turn to.
    */
   public final void turnTo(double angle) {
      turn(getBearing(angle));
   }

   /**
    * Makes the radar of the {@link Robot} turn past a specified angle by a sweep angle.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param angle
    *           the angle for the radar to turn past.
    * @param sweep
    *           the angle past which to sweep.
    */
   public final void sweep(double angle, double sweep) {
      double theta = getBearing(angle);
      turn(theta + Utils.sign(theta) * Math.abs(sweep));
   }

   /**
    * Makes the radar of the {@link Robot} turn to a specified coordinate, <code>(x, y)</code>.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param x
    *           the ordinate coordinate for the radar to turn to.
    * @param y
    *           the abscissa coordinate for the radar to turn to.
    */
   public final void turnTo(double x, double y) {
      turnTo(angle(x, y));
   }

   /**
    * Makes the radar of the {@link Robot} turn past a specified coordinate, <code>(x, y)</code>, by
    * a sweep angle.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param x
    *           the ordinate coordinate for the radar to turn past.
    * @param y
    *           the abscissa coordinate for the radar to turn past.
    * @param sweep
    *           the angle past which to sweep.
    */
   public final void sweep(double x, double y, double sweep) {
      sweep(angle(x, y), sweep);
   }

   /**
    * Makes the radar of the {@link Robot} turn past a specified coordinate, <code>(x, y)</code>, by
    * a distance.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param x
    *           the ordinate coordinate for the radar to turn past.
    * @param y
    *           the abscissa coordinate for the radar to turn past.
    * @param dist
    *           the distance past which to sweep.
    */
   public final void sweepDist(double x, double y, double dist) {
      sweep(angle(x, y), Trig.atan(dist / dist(x, y)));
   }

   /**
    * Makes the radar of the {@link Robot} turn to a specified robot snapshot.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param robot
    *           the snapshot for the radar to turn to.
    */
   public final void turnTo(IRobotSnapshot robot) {
      if (robot != null && robot.getEnergy() >= 0.0) {
         turnTo(angle(robot));
      }
   }

   /**
    * Makes the radar of the {@link Robot} turn past a specified robot snapshot by a sweep angle.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param robot
    *           the snapshot for the radar to turn to.
    * @param sweep
    *           the angle past which to sweep.
    */
   public final void sweep(IRobotSnapshot robot, double sweep) {
      if (robot != null && robot.getEnergy() >= 0.0) {
         sweep(angle(robot), sweep);
      }
   }

   /**
    * Makes the radar of the {@link Robot} turn past a specified robot snapshot by a sweep distance.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param robot
    *           the snapshot for the gun to turn to.
    * @param dist
    *           the distance past which to sweep.
    */
   public final void sweepDist(IRobotSnapshot robot, double dist) {
      if (robot != null && robot.getEnergy() >= 0.0) {
         sweepDist(robot.getX(), robot.getY(), dist);
      }
   }

}
