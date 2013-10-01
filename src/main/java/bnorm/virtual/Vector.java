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
    * The delta {@code x} of this Vector.
    */
   private Double deltaX;

   /**
    * The delta {@code y} of this Vector.
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
    * Creates a new Vector with the specified {@code (x, y)} coordinates,
    * heading, and velocity.
    *
    * @param x the {@code x} coordinate.
    * @param y the {@code y} coordinate.
    * @param heading the heading.
    * @param velocity the velocity.
    */
   public Vector(double x, double y, double heading, double velocity) {
      super(x, y);

      this.heading = heading;
      this.velocity = velocity;

      // Lazy initialize deltaX and deltaY
      this.deltaX = null;
      this.deltaY = null;
   }

   /**
    * Creates a new Vector with the specified delta coordinates.
    *
    * @param dx the delta {@code x} coordinate.
    * @param dy the delta {@code y} coordinate.
    */
   public Vector(double dx, double dy) {
      super(0.0, 0.0);

      this.deltaX = dx;
      this.deltaY = dy;

      // Lazy initialize heading and velocity
      this.heading = null;
      this.velocity = null;
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
         velocity = Points.dist(0.0, 0.0, deltaX, deltaY);
      }
      return velocity;
   }

   @Override
   public String toString() {
      if (heading == null || velocity == null) {
         return getClass().getSimpleName() + "[x=" + getX() + ", y=" + getY() + ", dx=" + getDeltaX() + ", dy="
                 + getDeltaY() + "]";
      } else {
         return getClass().getSimpleName() + "[x=" + getX() + ", y=" + getY() + ", h=" + getHeading() + ", v="
                 + getVelocity() + "]";
      }
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof IVector) {
         IVector v = (IVector) obj;
         if (heading == null || velocity == null) {
            return Utils.equal(getX(), v.getX()) && Utils.equal(getY(), v.getY()) && Utils
                    .equal(getDeltaX(), v.getDeltaX()) && Utils.equal(getDeltaY(), v.getDeltaY());
         } else {
            return Utils.equal(getX(), v.getX()) && Utils.equal(getY(), v.getY()) && Utils
                    .equalAngle(getHeading(), v.getHeading()) && Utils.equal(getVelocity(), v.getVelocity());
         }
      }
      return false;
   }
}
