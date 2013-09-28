package bnorm.virtual;

/**
 * Representation of a point in two dimensional space.
 *
 * @author Brian Norman
 * @version 1.0
 */
public class Point implements IPoint {

   /**
    * The <code>x</code> coordinate of this Point.
    */
   private final double x;

   /**
    * The <code>y</code> coordinate of this Point.
    */
   private final double y;

   /**
    * Creates a new Point with the specified <code>(x,y)</code> coordinates.
    *
    * @param x the <code>x</code> coordinate.
    * @param y the <code>y</code> coordinate.
    */
   public Point(double x, double y) {
      this.x = x;
      this.y = y;
   }

   @Override
   public double getX() {
      return x;
   }

   @Override
   public double getY() {
      return y;
   }

   /**
    * Returns a String that represents the value of this Point.
    *
    * @return a string representation of this Point.
    */
   @Override
   public String toString() {
      return "Point[" + getX() + ", " + getY() + "]";
   }

   /**
    * Determines whether or not two points are equal. Two instances of Point
    * are equal if the values of their <code>x</code> and <code>y</code> member
    * fields, representing their position in the coordinate space, are the
    * same.
    *
    * @param obj an object to be compared with this Point.
    * @return <code>true</code> if the object to be compared is an instance of
    * Point and has the same values; <code>false</code> otherwise.
    */
   @Override
   public boolean equals(Object obj) {
      if (obj instanceof IPoint) {
         IPoint p = (IPoint) obj;
         return (getX() == p.getX()) && (getY() == p.getY());
      }
      return super.equals(obj);
   }
}
