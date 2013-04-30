package bnorm.base;

import bnorm.robots.IRobotSnapshot;
import bnorm.utils.Trig;
import bnorm.utils.Utils;
import robocode.Robot;

/**
 * The {@link Base} class is a basic representation of a Robocode {@link Robot}. It defines many
 * convenience methods that each sub-class can use. Each of the different parts of a {@link Robot}
 * use this class as a base to define movement methods.
 * 
 * @author Brian Norman
 * @version 1.1
 */
public abstract class Base {

   /**
    * The {@link Robot} <code>class</code> that created the {@link Base} instance. This must be
    * provided by a sub-class and is never assumed to be null.
    */
   protected Robot robot_;

   /**
    * Constructs a new {@link Base} class that provides convenience methods to sub-classes.
    * 
    * @param robot
    *           the {@link Robot} that created this instance.
    */
   public Base(Robot robot) {
      this.robot_ = robot;
   }

   // --------------------
   // Information Commands
   // --------------------

   /**
    * Returns the specific heading of the part of the robot. Depending on the sub-class, this may be
    * the robot, gun, or radar heading. It will always be in radians and between 0 and 2 * PI.
    * 
    * @return a heading of the {@link Robot}.
    */
   public abstract double getHeading();

   /**
    * Returns the bearing to an angle, relative to the specific heading.
    * 
    * @param angle
    *           target angle.
    * @return the bearing to the angle.
    */
   public double getBearing(double angle) {
      return Utils.relative(angle - getHeading());
   }

   /**
    * Returns the angle to the specified coordinates <code>(x, y)</code>.
    * 
    * @param x
    *           the ordinate coordinate.
    * @param y
    *           the abscissa coordinate.
    * @return the angle to the specified coordinates.
    */
   public double angle(double x, double y) {
      return Trig.angle(x - robot_.getX(), y - robot_.getY());
   }

   /**
    * Returns the angle to the specified robot snapshot.
    * <p />
    * If the robot is <code>null</code> or dead, {@link Double#POSITIVE_INFINITY} will be returned.
    * 
    * @param robot
    *           the specified robot snapshot.
    * @return the angle to the robot snapshot.
    */
   public double angle(IRobotSnapshot robot) {
      if (robot == null || robot.getEnergy() < 0.0) {
         return Double.POSITIVE_INFINITY;
      }
      return angle(robot.getX(), robot.getY());
   }

   /**
    * Returns the distance squared to the specified coordinates, <code>(x, y)</code>.
    * 
    * @param x
    *           the ordinate coordinate.
    * @param y
    *           the abscissa coordinate.
    * @return the distance squared to the specified coordinates.
    */
   public double distSq(double x, double y) {
      x -= robot_.getX();
      y -= robot_.getY();
      return x * x + y * y;
   }

   /**
    * Returns the distance to the specified coordinates, <code>(x, y)</code>.
    * 
    * @param x
    *           the ordinate coordinate.
    * @param y
    *           the abscissa coordinate.
    * @return the distance to the specified coordinates.
    */
   public double dist(double x, double y) {
      return Math.sqrt(distSq(x, y));
   }

   /**
    * Returns the distance squared to the specified robot snapshot.
    * <p />
    * If the point is <code>null</code>, {@link Double#POSITIVE_INFINITY} will be returned.
    * 
    * @param robot
    *           the specified robot snapshot.
    * @return the distance squared to the specified robot snapshot.
    */
   public double distSq(IRobotSnapshot robot) {
      if (robot == null || robot.getEnergy() < 0.0) {
         return Double.POSITIVE_INFINITY;
      }
      return distSq(robot.getX(), robot.getY());
   }

   /**
    * Returns the distance to the specified robot snapshot.
    * <p />
    * If the point is <code>null</code>, {@link Double#POSITIVE_INFINITY} will be returned.
    * 
    * @param robot
    *           the specified robot snapshot.
    * @return the distance to the specified robot snapshot.
    */
   public double dist(IRobotSnapshot robot) {
      if (robot == null || robot.getEnergy() < 0.0) {
         return Double.POSITIVE_INFINITY;
      }
      return dist(robot.getX(), robot.getY());
   }

}
