package kid.base;

import kid.robots.IRobotSnapshot;
import robocode.Bullet;
import robocode.Robot;
import robocode.Rules;

/**
 * An abstract representation of the gun on a {@link Robot}. This class allows the the robot to
 * perform some basic turning and firing by utilizing some convince functions.
 * 
 * @author Brian Norman
 * @version 1.0
 */
public class Gun extends Base {

   /**
    * The turning rate of the gun measured in radians, which is 20 degrees/turn. Note, that if
    * setAdjustGunForRobotTurn(true) has been called, the gun turn is independent of the robot turn.
    * In this case the gun moves relatively to the screen. If setAdjustGunForRobotTurn(false) has
    * been called or setAdjustGunForRobotTurn() has not been called at all (this is the default),
    * then the gun turn is dependent on the robot turn, and in this case the gun moves relatively to
    * the robot body.
    * 
    * @see Robot#setAdjustGunForRobotTurn(boolean)
    */
   public static final double GUN_TURN_RATE = Rules.GUN_TURN_RATE_RADIANS;

   /**
    * Constructs a new {@link Gun} class that gives the {@link Robot} some convince functions for
    * turning and firing.
    * 
    * @param robot
    *           the {@link Robot} creating the class.
    */
   public Gun(Robot robot) {
      super(robot);
   }

   // --------------------
   // Information Commands
   // --------------------

   /**
    * Returns the direction that the robot's gun is facing, in radians. The value returned will be
    * between 0 and 2*PI (is excluded).
    * <p/>
    * Note that the heading in Robocode is like a compass, where 0 means North, PI/2 means East, PI
    * means South, and 2/3*PI means West.
    * 
    * @return the direction that the robot's gun is facing, in radians.
    */
   @Override
   public double getHeading() {
      return Math.toRadians(robot_.getGunHeading());
   }

   /**
    * Returns the current heat of the gun. The gun cannot fire unless this is 0. (Calls to fire will
    * succeed, but will not actually fire unless <code>getGunHeat() == 0</code>).
    * <p/>
    * The amount of gun heat generated when the gun is fired is <code>1 + (firePower / 5)</code>.
    * Each turn the gun heat drops by the amount returned by {@link Robot#getGunCoolingRate()},
    * which is a battle setup.
    * <p/>
    * Note that all guns are "hot" at the start of each round, where the gun heat is 3.
    * 
    * @return the current gun heat.
    * @see Robot#getGunCoolingRate()
    */
   public double getHeat() {
      return robot_.getGunHeat();
   }

   /**
    * Returns the rate at which the gun will cool down, i.e. the amount of heat the gun heat will
    * drop per turn.
    * <p/>
    * The gun cooling rate is default 0.1 / turn, but can be changed by the battle setup. So don't
    * count on the cooling rate being 0.1!
    * 
    * @return the gun cooling rate.
    * @see #getHeat()
    */
   public double getCoolingRate() {
      return robot_.getGunCoolingRate();
   }

   // -----------------
   // Movement Commands
   // -----------------

   /**
    * Makes the gun turn right a specified angle in radians.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param a
    *           the angle to turn.
    */
   public void turnRight(double a) {
      robot_.turnGunRight(Math.toDegrees(a));
   }

   /**
    * Makes the gun turn left a specified angle in radians.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param a
    *           the angle to turn.
    */
   public void turnLeft(double a) {
      robot_.turnGunLeft(Math.toDegrees(a));
   }

   /**
    * Makes the gun turn a specified angle in radians. A positive angle will cause the gun to turn
    * to the right, a negative angle will cause the gun to turn to the left, and if an angle of
    * <code>0</code> is specified, no action is taken.
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
    * Makes the gun of the {@link Robot} turn to a specified angle.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param angle
    *           the angle for the gun to turn to.
    */
   public final void turnTo(double angle) {
      turn(getBearing(angle));
   }

   /**
    * Makes the gun of the {@link Robot} turn to a specified coordinates, <code>(x, y)</code>.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param x
    *           the ordinate coordinate for the gun to turn to.
    * @param y
    *           the abscissa coordinate for the gun to turn to.
    */
   public final void turnTo(double x, double y) {
      turnTo(angle(x, y));
   }

   /**
    * Makes the gun of the {@link Robot} turn to a specified robot snapshot.
    * <p/>
    * This call executes immediately, and does not return until it is complete, i.e. when the
    * remaining angle to turn is 0.
    * 
    * @param robot
    *           the snapshot for the gun to turn to.
    */
   public final void turnTo(IRobotSnapshot robot) {
      if (robot != null && robot.getEnergy() >= 0.0) {
         turnTo(angle(robot));
      }
   }

   // --------------
   // Other Commands
   // --------------

   /**
    * Makes the gun of the {@link Robot} fire a bullet and then returns that bullet.
    * 
    * @param power
    *           the power at which the gun will fire.
    * @return the resulting bullet of the gun firing.
    */
   public final Bullet fire(double power) {
      return robot_.fireBullet(power);
   }

}
