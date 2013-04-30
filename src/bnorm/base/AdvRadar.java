package bnorm.base;

import bnorm.robots.IRobotSnapshot;
import bnorm.utils.Trig;
import bnorm.utils.Utils;
import robocode.AdvancedRobot;
import robocode.Robot;

/**
 * An abstract representation of the radar on an {@link AdvancedRobot}. This class allows the the
 * robot to perform some basic turning and firing by utilizing some convince functions.
 * 
 * @author Brian Norman
 * @version 1.0
 */
public class AdvRadar extends Radar {

   /**
    * The {@link AdvancedRobot} <code>class</code> that created the {@link AdvRadar} instance.
    */
   protected AdvancedRobot advRobot_;

   /**
    * Constructs a new {@link AdvRadar} class that gives the {@link AdvancedRobot} some convince
    * functions for turning and firing.
    * 
    * @param robot
    *           the {@link AdvancedRobot} creating the class.
    */
   public AdvRadar(AdvancedRobot robot) {
      super(robot);
      this.advRobot_ = robot;
   }

   // --------------------
   // Information Commands
   // --------------------

   /**
    * {@inheritDoc}
    */
   @Override
   public double getHeading() {
      return advRobot_.getRadarHeadingRadians();
   }

   /**
    * Returns the angle in radians that the radar has yet to turn.<br>
    * <br>
    * This call returns both positive and negative values. Positive values means that the radar is
    * currently turning to the right. Negative values means that the radar is currently turning to
    * the left. If the returned value is 0, the radar is currently not turning.
    * 
    * @return the turning angle remaining.
    */
   public double getTurnRemaining() {
      return advRobot_.getRadarTurnRemainingRadians();
   }

   /**
    * Returns the sign of the angle that the radar has yet to turn. Will return <code>1</code> if
    * the radar is turning to the right, <code>-1</code> if the radar is turning to the left, and
    * <code>0</code> if the radar is not turning.
    * 
    * @return the sign of the turn remaining.
    */
   public int getTurningSign() {
      return Utils.signZ(getTurnRemaining());
   }

   // -----------------
   // Movement Commands
   // -----------------

   /**
    * Sets the radar to turn right a specified angle in radians.<br>
    * <br>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param a
    *           the angle to turn.
    */
   public void setTurnRight(double a) {
      advRobot_.setTurnRadarRightRadians(a);
   }

   /**
    * Sets the radar to turn left a specified angle in radians.<br>
    * <br>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param a
    *           the angle to turn.
    */
   public void setTurnLeft(double a) {
      advRobot_.setTurnRadarLeftRadians(a);
   }

   /**
    * Sets the radar to turn a specified angle in radians. A positive angle will cause the radar to
    * turn to the right and a negative angle will cause the radar to turn to the left.<br>
    * <br>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param a
    *           the angle to turn.
    */
   public void setTurn(double a) {
      setTurnRight(a);
   }

   /**
    * Sets the radar of the {@link AdvancedRobot} to turn to a specified angle.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param angle
    *           the angle for the radar to turn to.
    */
   public final void setTurnTo(double angle) {
      setTurn(getBearing(angle));
   }

   /**
    * Sets the radar of the {@link Robot} to turn past a specified angle by a sweep angle.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param angle
    *           the angle for the radar to turn past.
    * @param sweep
    *           the angle past which to sweep.
    */
   public final void setSweep(double angle, double sweep) {
      double theta = getBearing(angle);
      setTurn(Utils.relative(theta + Utils.sign(theta) * Math.abs(sweep)));
   }

   /**
    * Sets the radar of the {@link AdvancedRobot} to turn to a specified coordinates,
    * <code>(x, y)</code>.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param x
    *           the ordinate coordinate for the radar to turn to.
    * @param y
    *           the abscissa coordinate for the radar to turn to.
    */
   public final void setTurnTo(double x, double y) {
      setTurnTo(angle(x, y));
   }

   /**
    * Sets the radar of the {@link Robot} to turn past a specified coordinate, <code>(x, y)</code>,
    * by a sweep angle.
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
   public final void setSweep(double x, double y, double sweep) {
      setSweep(angle(x, y), sweep);
   }

   /**
    * Sets the radar of the {@link AdvancedRobot} to turn past a specified coordinate,
    * <code>(x, y)</code>, by a distance.
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
   public final void setSweepDist(double x, double y, double dist) {
      setSweep(angle(x, y), Trig.atan(dist / dist(x, y)));
   }

   /**
    * Sets the radar of the {@link AdvancedRobot} to turn to a specified robot snapshot. If the
    * targeted robot snapshot is null or dead, the radar will be set to turn right for infinity.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param robot
    *           the snapshot for the radar to turn to.
    */
   public final void setTurnTo(IRobotSnapshot robot) {
      setTurnTo(angle(robot));
   }

   /**
    * Sets the radar of the {@link AdvancedRobot} turn past a specified robot snapshot by a
    * specified angle. If the targeted robot snapshot is null or dead, the radar will be set to turn
    * right for infinity.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param robot
    *           the target robot snapshot to sweep.
    * @param sweep
    *           the angle past the robot snapshot the radar will sweep.
    */
   public final void setSweep(IRobotSnapshot robot, double sweep) {
      setSweep(angle(robot), sweep);
   }

   /**
    * Sets the radar of the {@link AdvancedRobot} turn past a specified robot snapshot by a sweep
    * distance. If the targeted robot snapshot is null or dead, the radar will be set to turn right
    * for infinity.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param robot
    *           the snapshot for the radar to turn to.
    * @param dist
    *           the distance past which to sweep.
    */
   public final void setSweepDist(IRobotSnapshot robot, double dist) {
      if (robot != null && robot.getEnergy() >= 0.0) {
         setSweepDist(robot.getX(), robot.getY(), dist);
      } else {
         setTurn(Double.POSITIVE_INFINITY);
      }
   }

}
