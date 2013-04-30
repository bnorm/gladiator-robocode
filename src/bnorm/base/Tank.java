package bnorm.base;

import bnorm.robots.IRobotSnapshot;
import bnorm.utils.Trig;
import robocode.Robot;
import robocode.Rules;

/**
 * An abstract representation of a {@link Robot}. This class allows the the robot to perform some
 * basic moving and turning by utilizing some convince functions.
 * 
 * @author Brian Norman
 * @version 1.0
 */
public class Tank extends Base {

   /**
    * The maximum turning rate of the robot measured in radians which is 1/18 * PI radians/turn.
    * <p/>
    * Note, that the turn rate of the robot depends on it's velocity.
    * 
    * @see Rules#getTurnRate(double)
    */
   public static final double MAX_TURN_RATE = Rules.MAX_TURN_RATE_RADIANS;

   /**
    * The acceleration of a robot, i.e. the increase of velocity when the robot moves forward, which
    * is 1 pixel/turn.
    */
   public static final double ACCELERATION  = Rules.ACCELERATION;

   /**
    * The deceleration of a robot, i.e. the decrease of velocity when the robot moves backwards (or
    * brakes), which is 2 pixels/turn.
    */
   public static final double DECELERATION  = Rules.DECELERATION;

   /**
    * The maximum velocity of a robot which is 8 pixels/turn.
    */
   public static final double MAX_VELOCITY  = Rules.MAX_VELOCITY;

   /**
    * The {@link Integer} representation of forwards, which is 1.
    */
   public static final int    FORWARD       = 1;

   /**
    * The {@link Integer} representation of backwards, which is -1.
    */
   public static final int    BACKWARD      = -1;

   /**
    * Constructs a new {@link Tank} class that gives the {@link Robot} some convince functions for
    * moving and turning.
    * 
    * @param robot
    *           the {@link Robot} creating the class.
    */
   public Tank(Robot robot) {
      super(robot);
   }

   // --------------------
   // Information Commands
   // --------------------

   /**
    * Returns the direction that the robot is facing, in radians. The value returned will be between
    * 0 and 2*PI (is excluded).
    * <p/>
    * Note that the heading in Robocode is like a compass, where 0 means North, PI/2 means East, PI
    * means South, and 2/3*PI means West.
    * 
    * @return the direction that the robot is facing, in radians.
    */
   @Override
   public double getHeading() {
      return Math.toRadians(robot_.getHeading());
   }

   /**
    * Returns the velocity of the robot measured in pixels/turn.
    * <p/>
    * The maximum velocity of a robot is defined by {@link #MAX_VELOCITY} (8 pixels / turn).
    * 
    * @return the velocity of the robot measured in pixels/turn.
    * @see #MAX_VELOCITY
    */
   public double getVelocity() {
      return robot_.getVelocity();
   }

   /**
    * Returns the turn rate of a robot given a specific velocity measured in radians/turn.
    * 
    * @param velocity
    *           the velocity of the robot.
    * @return turn rate in radians/turn.
    */
   public static double getTurnRate(double velocity) {
      return Rules.getTurnRateRadians(velocity);
   }

   /**
    * Returns the turn rate of the robot measured in radians/turn.
    * 
    * @return turn rate in radians/turn.
    */
   public double getTurnRate() {
      return getTurnRate(robot_.getVelocity());
   }

   // ----------------
   // Turning Commands
   // ----------------

   /**
    * Makes the robot turn right a specified angle in radians.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param a
    *           the angle to turn.
    */
   public void turnRight(double a) {
      robot_.turnRight(Math.toDegrees(a));
   }

   /**
    * Makes the robot turn left a specified angle in radians.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param a
    *           the angle to turn.
    */
   public void turnLeft(double a) {
      robot_.turnLeft(Math.toDegrees(a));
   }

   /**
    * Makes the robot turn a specified angle in radians. A positive angle will cause the robot to
    * turn to the right, a negative angle will cause the robot to turn to the left, and if an angle
    * of <code>0</code> is specified, no action is taken.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param a
    *           the angle to turn.
    */
   public void turn(double a) {
      if (a != 0.0) {
         turnRight(a);
      }
   }

   /**
    * Makes the {@link Robot} turn to a specified angle.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param angle
    *           the angle for the robot to turn to.
    */
   public void turnTo(double angle) {
      turn(getBearing(angle));
   }

   /**
    * Makes the {@link Robot} turn so it is in line with the specified angle and returns a value
    * corresponding to which direction the robot would need to move to follow the line.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param angle
    *           the line for the robot to turn to.
    * @return the direction the robot would need to move to follow the line.
    */
   public int turnToLine(double angle) {
      if (Math.abs(getBearing(angle)) <= Trig.QUARTER_CIRCLE) {
         turnTo(angle);
         return 1;
      } else {
         turnTo(angle + Trig.HALF_CIRCLE);
         return -1;
      }
   }

   /**
    * Makes the {@link Robot} turn to a specified coordinates, <code>(x, y)</code>.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param x
    *           the ordinate coordinate for the robot to turn to.
    * @param y
    *           the abscissa coordinate for the robot to turn to.
    */
   public void turnTo(double x, double y) {
      turnTo(angle(x, y));
   }

   /**
    * Makes the {@link Robot} turn so it is in line with the specified coordinates,
    * <code>(x, y)</code>, and returns a value corresponding to which direction the robot would need
    * to move to follow the line.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param x
    *           the ordinate coordinate of the line for the robot to turn to.
    * @param y
    *           the abscissa coordinate of the line for the robot to turn to.
    * @return the direction the robot would need to move to follow the line.
    */
   public int turnToLine(double x, double y) {
      return turnToLine(angle(x, y));
   }

   /**
    * Makes the {@link Robot} turn to a specified robot snapshot.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param robot
    *           the snapshot for the robot to turn to.
    */
   public void turnTo(IRobotSnapshot robot) {
      if (robot != null && robot.getEnergy() >= 0.0) {
         turnTo(angle(robot));
      }
   }

   /**
    * Makes the {@link Robot} turn so it is in line with the specified robot snapshot and returns a
    * value corresponding to which direction the robot would need to move to follow the line.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param robot
    *           the snapshot of the line for the robot to turn to.
    * @return the direction the robot would need to move to follow the line.
    */
   public int turnToLine(IRobotSnapshot robot) {
      if (robot != null && robot.getEnergy() >= 0.0) {
         return turnToLine(angle(robot));
      }
      return 0;
   }

   // -----------------
   // Movement Commands
   // -----------------

   /**
    * Makes the {@link Robot} move a specified distance forward.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining distance to move is 0.
    * <p/>
    * If the robot collides with a wall, the move is complete, meaning that the robot will not move
    * any further. If the robot collides with another robot, the move is complete if you are heading
    * toward the other robot.
    * 
    * @param d
    *           the distance to move forward.
    */
   public void moveForward(double d) {
      robot_.ahead(d);
   }

   /**
    * Makes the {@link Robot} move a specified distance backward.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining distance to move is 0.
    * <p/>
    * If the robot collides with a wall, the move is complete, meaning that the robot will not move
    * any further. If the robot collides with another robot, the move is complete if you are heading
    * toward the other robot.
    * 
    * @param d
    *           the distance to move backward.
    */
   public void moveBackward(double d) {
      robot_.back(d);
   }

   /**
    * Makes the {@link Robot} move a specified distance. A positive distance will cause the robot to
    * move forwards, a negative distance will cause the robot to move backwards, and if the distance
    * of <code>0</code> is specified, no action is taken.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining distance to move is 0.
    * <p/>
    * If the robot collides with a wall, the move is complete, meaning that the robot will not move
    * any further. If the robot collides with another robot, the move is complete if you are heading
    * toward the other robot.
    * 
    * @param d
    *           the distance to move.
    */
   public void move(double d) {
      if (d != 0.0) {
         moveForward(d);
      }
   }

}
