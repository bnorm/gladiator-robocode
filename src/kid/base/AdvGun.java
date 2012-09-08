package kid.base;

import kid.robots.IRobotSnapshot;
import kid.utils.Utils;
import robocode.AdvancedRobot;
import robocode.Bullet;

/**
 * An abstract representation of the gun on an {@link AdvancedRobot}. This class allows the the
 * robot to perform some basic turning and firing by utilizing some convince functions.
 * 
 * @author Brian Norman
 * @version 1.0
 */
public class AdvGun extends Gun {

   /**
    * The {@link AdvancedRobot} <code>class</code> that created the {@link AdvGun} instance.
    */
   protected AdvancedRobot advRobot_;

   /**
    * Constructs a new {@link AdvGun} class that gives the {@link AdvancedRobot} some convince
    * functions for turning and firing.
    * 
    * @param robot
    *           the {@link AdvancedRobot} creating the class.
    */
   public AdvGun(AdvancedRobot robot) {
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
      return advRobot_.getGunHeadingRadians();
   }

   /**
    * Returns the angle in radians that the gun has yet to turn.
    * <p/>
    * This call returns both positive and negative values. Positive values means that the gun is
    * currently turning to the right. Negative values means that the gun is currently turning to the
    * left. If the returned value is 0, the gun is currently not turning.
    * 
    * @return the turning angle remaining.
    */
   public double getTurnRemaining() {
      return advRobot_.getGunTurnRemainingRadians();
   }

   /**
    * Returns the sign of the angle that the gun has yet to turn. Will return <code>1</code> if the
    * gun is turning to the right, <code>-1</code> if the gun is turning to the left, and
    * <code>0</code> if the gun is not turning.
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
    * Sets the gun to turn right a specified angle in radians.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param a
    *           the angle to turn.
    */
   public void setTurnRight(double a) {
      advRobot_.setTurnGunRightRadians(a);
   }

   /**
    * Sets the gun to turn left a specified angle in radians.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param a
    *           the angle to turn.
    */
   public void setTurnLeft(double a) {
      advRobot_.setTurnGunLeftRadians(a);
   }

   /**
    * Sets the gun to turn a specified angle in radians. A positive angle will cause the gun to turn
    * to the right and a negative angle will cause the gun to turn to the left.
    * <p/>
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
    * Sets the gun of the {@link AdvancedRobot} to turn to a specified angle.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param angle
    *           the angle for the gun to turn to.
    */
   public final void setTurnTo(double angle) {
      setTurn(getBearing(angle));
   }

   /**
    * Sets the gun of the {@link AdvancedRobot} to turn to a specified coordinates,
    * <code>(x, y)</code>.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param x
    *           the ordinate coordinate for the gun to turn to.
    * @param y
    *           the abscissa coordinate for the gun to turn to.
    */
   public final void setTurnTo(double x, double y) {
      setTurnTo(angle(x, y));
   }

   /**
    * Sets the gun of the {@link AdvancedRobot} to turn to a specified robot snapshot.
    * <p/>
    * This call returns immediately, and will not execute until you call
    * {@link AdvancedRobot#execute() execute()} or take an action that executes.
    * 
    * @param robot
    *           the snapshot for the gun to turn to.
    */
   public final void setTurnTo(IRobotSnapshot robot) {
      setTurnTo(angle(robot));
   }

   // --------------
   // Other Commands
   // --------------

   /**
    * Sets the gun of the {@link AdvancedRobot} to fire a bullet and returns that bullet.
    * 
    * @param power
    *           the power at which the gun will fire.
    * @return the resulting bullet of the gun firing.
    */
   public final Bullet setFire(double power) {
      return advRobot_.setFireBullet(power);
   }

}
