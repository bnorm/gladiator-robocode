package kid.utils;

/**
 * A utility class for common Robocode functions.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
public final class Utils {

   /**
    * Don't let anyone instantiate this class.
    */
   private Utils() {
   }

   /**
    * Returns the x coordinate of a point projected at a specified angle and
    * distance with a specified starting coordinate.
    * 
    * @param x
    *           the starting x coordinate.
    * @param a
    *           the projecting angle.
    * @param d
    *           the projecting distance.
    * @return the projected x coordinate.
    */
   public static final double projectX(double x, double a, double d) {
      return x + vectorX(a, d);
   }

   /**
    * Returns the x coordinate of a vector with the specified angle and length.
    * 
    * @param a
    *           the angle of the vector.
    * @param l
    *           the length of the vector.
    * @return the x coordinate of the vector.
    */
   public static final double vectorX(double a, double l) {
      return l * Trig.sin(a);
   }

   /**
    * Returns the y coordinate of a point projected at a specified angle and
    * distance with a specified starting coordinate.
    * 
    * @param y
    *           the starting y coordinate.
    * @param a
    *           the projecting angle.
    * @param d
    *           the projecting distance.
    * @return the projected y coordinate.
    */
   public static final double projectY(double y, double a, double d) {
      return y + vectorY(a, d);
   }

   /**
    * Returns the y coordinate of a vector with the specified angle and length.
    * 
    * @param a
    *           the angle of the vector.
    * @param l
    *           the length of the vector.
    * @return the y coordinate of the vector.
    */
   public static final double vectorY(double a, double l) {
      return l * Trig.cos(a);
   }

}
