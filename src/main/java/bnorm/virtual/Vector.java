package bnorm.virtual;

import bnorm.utils.Trig;
import bnorm.utils.Utils;

/**
 * Representation of a vector in two dimensional space. A vector in two
 * dimensional space is a point with a direction and a velocity.
 *
 * @author Brian Norman
 * @version 1.0
 */
public class Vector extends Point implements IVector {

   /**
    * The delta <code>x</code> of this Vector.
    */
   private Double deltaX;

   /**
    * The delta <code>y</code> of this Vector.
    */
   private Double deltaY;

   /**
    * The heading of this Vector.
    */
   private Double heading;

   /**
    * The velocity of this Vector.
    */
   private Double velocity;

   /**
    * Creates a new Vector with the specified <code>(x,y)</code> coordinates,
    * heading, and velocity.
    *
    * @param x the <code>x</code> coordinate.
    * @param y the <code>y</code> coordinate.
    * @param heading the heading.
    * @param velocity the velocity.
    */
   public Vector(double x, double y, double heading, double velocity) {
      super(x, y);

      this.heading = heading;
      this.velocity = velocity;
   }

   /**
    * Creates a new Vector with the specified delta coordinates.
    *
    * @param dx the delta <code>x</code> coordinate.
    * @param dy the delta <code>y</code> coordinate.
    */
   public Vector(double dx, double dy) {
      super(0, 0);

      this.deltaX = dx;
      this.deltaY = dy;
   }

   @Override
   public double getDeltaX() {
      if (deltaX == null) {
         deltaX = Utils.vectorX(heading, velocity);
      }
      return deltaX;
   }

   @Override
   public double getDeltaY() {
      if (deltaY == null) {
         deltaY = Utils.vectorY(heading, velocity);
      }
      return deltaY;
   }

   @Override
   public double getHeading() {
      if (heading == null) {
         heading = Trig.angle(deltaX, deltaY);
      }
      return heading;
   }

   @Override
   public double getVelocity() {
      if (velocity == null) {
         velocity = Math.sqrt(Utils.sqr(deltaX) + Utils.sqr(deltaY));
      }
      return velocity;
   }
}
