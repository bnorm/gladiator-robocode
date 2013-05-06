package bnorm.virtual;

/**
 * Utility class for {@link IPoint}s.
 *
 * @author Brian Norman
 * @version 1.0
 */
public final class Points {

   /**
    * Private constructor.
    */
   private Points() {
   }

   /**
    * Calculates and returns the square of the distance between two
    * <code>(x,y)</code> coordinates.
    *
    * @param x1 the <code>x</code> coordinate of the first coordinate.
    * @param y1 the <code>y</code> coordinate of the first coordinate.
    * @param x2 the <code>x</code> coordinate of the second coordinate.
    * @param y2 the <code>y</code> coordinate of the second coordinate.
    * @return the square of the distance between two coordinates.
    */
   public static double distSq(double x1, double y1, double x2, double y2) {
      x1 -= x2;
      y1 -= y2;
      return (x1 * x1 + y1 * y1);
   }

   /**
    * Calculates and returns the distance between two <code>(x,y)</code>
    * coordinates.
    *
    * @param x1 the <code>x</code> coordinate of the first coordinate.
    * @param y1 the <code>y</code> coordinate of the first coordinate.
    * @param x2 the <code>x</code> coordinate of the second coordinate.
    * @param y2 the <code>y</code> coordinate of the second coordinate.
    * @return the distance between two coordinates.
    */
   public static double dist(double x1, double y1, double x2, double y2) {
      return Math.sqrt(distSq(x1, y1, x2, y2));
   }

   /**
    * Calculates and returns the square of the distance between two
    * <code>(x,y)</code> coordinates.
    *
    * @param p the first coordinate.
    * @param x the <code>x</code> coordinate of the second coordinate.
    * @param y the <code>y</code> coordinate of the second coordinate.
    * @return the square of the distance between two coordinates.
    */
   public static double distSq(IPoint p, double x, double y) {
      return distSq(p.getX(), p.getY(), x, y);
   }

   /**
    * Calculates and returns the distance between two <code>(x,y)</code>
    * coordinates.
    *
    * @param p the first coordinate.
    * @param x the <code>x</code> coordinate of the second coordinate.
    * @param y the <code>y</code> coordinate of the second coordinate.
    * @return the distance between two coordinates.
    */
   public static double dist(IPoint p, double x, double y) {
      return dist(p.getX(), p.getY(), x, y);
   }

   /**
    * Calculates and returns the square of the distance between two
    * <code>(x,y)</code> coordinates.
    *
    * @param p1 the first coordinate.
    * @param p2 the second coordinate.
    * @return the square of the distance between two coordinates.
    */
   public static double distSq(IPoint p1, IPoint p2) {
      return distSq(p1.getX(), p1.getY(), p2.getX(), p2.getY());
   }

   /**
    * Calculates and returns the distance between two <code>(x,y)</code>
    * coordinates.
    *
    * @param p1 the first coordinate.
    * @param p2 the second coordinate.
    * @return the distance between two coordinates.
    */
   public static double dist(IPoint p1, IPoint p2) {
      return dist(p1.getX(), p1.getY(), p2.getX(), p2.getY());
   }
}
