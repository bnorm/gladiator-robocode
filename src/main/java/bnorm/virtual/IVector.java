package bnorm.virtual;

/**
 * Interface for a vector in two dimensional space. A vector in two dimensional
 * space is a point with a direction and a velocity.
 *
 * @author Brian Norman
 * @version 1.0
 */
public interface IVector extends IPoint {

   /**
    * Returns the delta change in the <code>x</code> coordinate direction of
    * the vector.
    *
    * @return the delta <code>x</code>.
    */
   double getDeltaX();

   /**
    * Returns the delta change in the <code>y</code> coordinate direction of
    * the vector.
    *
    * @return the delta <code>y</code>.
    */
   double getDeltaY();

   /**
    * Returns the heading of the vector.
    *
    * @return the heading.
    */
   double getHeading();

   /**
    * Returns the velocity of the vector.
    *
    * @return the velocity.
    */
   double getVelocity();

}
