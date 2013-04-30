package bnorm.base;

import bnorm.robots.IRobotSnapshot;
import bnorm.utils.Trig;
import bnorm.utils.Utils;
import robocode.AdvancedRobot;
import robocode.Robot;

/**
 * An abstract representation of a {@link AdvancedRobot}. This class allows the the robot to perform
 * some basic moving and turning by utilizing some convince functions.
 * 
 * @author Brian Norman
 * @version 1.0
 */
public class AdvTank extends Tank {

   /**
    * The {@link AdvancedRobot} that created the {@link AdvTank} instance.
    */
   protected AdvancedRobot advRobot_;

   /**
    * Constructs a new {@link AdvTank} class that gives the {@link AdvancedRobot} some convenience
    * functions for moving and turning.
    * 
    * @param robot
    *           the {@link AdvancedRobot} creating the class.
    */
   public AdvTank(AdvancedRobot robot) {
      super(robot);
      advRobot_ = robot;
   }

   // --------------------
   // Information Commands
   // --------------------

   /**
    * {@inheritDoc}
    */
   @Override
   public double getHeading() {
      return advRobot_.getHeadingRadians();
   }

   /**
    * Returns the angle in radians that the robot has yet to turn.
    * <p/>
    * This call returns both positive and negative values. Positive values means that the robot is
    * currently turning to the right. Negative values means that the robot is currently turning to
    * the left. If the returned value is <code>0</code>, the robot is currently not turning.
    * 
    * @return the turning angle remaining.
    */
   public double getTurnRemaining() {
      return advRobot_.getTurnRemainingRadians();
   }

   /**
    * Returns the sign of the angle that the robot has yet to turn. Will return <code>1</code> if
    * the robot is turning to the right, <code>-1</code> if the robot is turning to the left, and
    * <code>0</code> if the robot is not turning.
    * 
    * @return the sign of the turn remaining.
    */
   public int getTurningSign() {
      return Utils.signZ(getTurnRemaining());
   }

   /**
    * Returns the distance that the robot has yet to move.
    * <p/>
    * This call returns both positive and negative values. Positive values means that the robot is
    * currently moving forward. Negative values means that the robot is currently moving backward.
    * If the returned value is <code>0</code>, the robot is currently not moving.
    * 
    * @return the moving distance remaining.
    */
   public double getMoveRemaining() {
      return advRobot_.getDistanceRemaining();
   }

   /**
    * Returns the sign of the distance that the robot has yet to move. Will return <code>1</code> if
    * the robot is moving forward, <code>-1</code> if the robot is moving backward, and
    * <code>0</code> if the robot is not moving.
    * 
    * @return the sign of the move remaining.
    */
   public int getMovingSign() {
      return Utils.signZ(getTurnRemaining());
   }

   // ----------------
   // Turning Commands
   // ----------------

   /**
    * Sets the {@link AdvancedRobot} to turn right a specified angle in radians.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param a
    *           the angle to turn.
    */
   public void setTurnRight(double a) {
      advRobot_.setTurnRightRadians(a);
   }

   /**
    * Sets the {@link AdvancedRobot} to turn left a specified angle in radians.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param a
    *           the angle to turn.
    */
   public void setTurnLeft(double a) {
      advRobot_.setTurnLeftRadians(a);
   }

   /**
    * Sets the {@link AdvancedRobot} to turn a specified angle in radians. A positive angle will
    * cause the robot to turn to the right, a negative angle will cause the robot to turn to the
    * left, and if an angle of <code>0</code> is specified, no action is taken.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param a
    *           the angle to turn.
    */
   public void setTurn(double a) {
      if (a != 0.0) {
         turnRight(a);
      }
   }

   /**
    * Sets the {@link AdvancedRobot} to turn to a specified angle.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param angle
    *           the angle for the robot to turn to.
    */
   public void setTurnTo(double angle) {
      setTurn(getBearing(angle));
   }

   /**
    * Sets the {@link AdvancedRobot} to turn so it is in line with the specified angle and returns a
    * value corresponding to which direction the robot would need to move to follow the line.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param angle
    *           the line for the robot to turn to.
    * @return the direction the robot would need to move to follow the line.
    */
   public int setTurnToLine(double angle) {
      if (Math.abs(getBearing(angle)) <= Trig.QUARTER_CIRCLE) {
         setTurnTo(angle);
         return 1;
      } else {
         setTurnTo(angle + Trig.HALF_CIRCLE);
         return -1;
      }
   }

   /**
    * Sets the {@link AdvancedRobot} turn to a specified coordinates, <code>(x, y)</code>.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param x
    *           the ordinate coordinate for the robot to turn to.
    * @param y
    *           the abscissa coordinate for the robot to turn to.
    */
   public void setTurnTo(double x, double y) {
      setTurnTo(angle(x, y));
   }

   /**
    * Sets the {@link AdvancedRobot} to turn so it is in line with the specified coordinates,
    * <code>(x, y)</code>, and returns a value corresponding to which direction the robot would need
    * to move to follow the line.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param x
    *           the ordinate coordinate of the line for the robot to turn to.
    * @param y
    *           the abscissa coordinate of the line for the robot to turn to.
    * @return the direction the robot would need to move to follow the line.
    */
   public int setTurnToLine(double x, double y) {
      return setTurnToLine(angle(x, y));
   }

   /**
    * Sets the {@link AdvancedRobot} to turn to a specified robot snapshot.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param robot
    *           the snapshot for the robot to turn to.
    */
   public void setTurnTo(IRobotSnapshot robot) {
      if (robot != null && robot.getEnergy() >= 0.0) {
         setTurnTo(angle(robot));
      }
   }

   /**
    * Sets the {@link AdvancedRobot} turn so it is in line with the specified robot snapshot and
    * returns a value corresponding to which direction the robot would need to move to follow the
    * line.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param robot
    *           the snapshot of the line for the robot to turn to.
    * @return the direction the robot would need to move to follow the line.
    */
   public int setTurnToLine(IRobotSnapshot robot) {
      if (robot != null && robot.getEnergy() >= 0.0) {
         return setTurnToLine(angle(robot));
      }
      return 0;
   }

   // -----------------
   // Movement Commands
   // -----------------

   /**
    * Sets the {@link AdvancedRobot} to move a specified distance forward.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param d
    *           the distance to move forward.
    */
   public void setMoveForward(double d) {
      advRobot_.setAhead(d);
   }

   /**
    * Sets the {@link Robot} to move a specified distance backward.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param d
    *           the distance to move backward.
    */
   public void setMoveBackward(double d) {
      advRobot_.setBack(d);
   }

   /**
    * Sets the {@link Robot} to move a specified distance. A positive distance will cause the robot
    * to move forwards, a negative distance will cause the robot to move backwards, and if the
    * distance of <code>0</code> is specified, no action is taken.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param d
    *           the distance to move.
    */
   public void setMove(double d) {
      if (d != 0.0) {
         setMoveForward(d);
      }
   }

}
