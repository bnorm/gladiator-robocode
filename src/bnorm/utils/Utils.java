package bnorm.utils;

/**
 * A utility class for common Robocode functions.
 * 
 * @author Brian Norman
 * @version 1.1
 */
public final class Utils {

   /**
    * Don't let anyone instantiate this class.
    */
   private Utils() {
   }

   /**
    * Normalizes an angle to an absolute angle. The normalized angle will be in the range from
    * <code>0</code> to <code>2*PI</code>, where <code>2*PI</code> itself is not included.
    * 
    * @param angle
    *           the angle to normalize
    * @return the normalized angle that will be in the range of <code>[0, 2*PI)</code>
    */
   public static final double absolute(double angle) {
      return robocode.util.Utils.normalAbsoluteAngle(angle);
   }

   /**
    * Normalizes an angle to a relative angle. The normalized angle will be in the range from
    * <code>-PI</code> to <code>PI</code>, where <code>PI</code> itself is not included.
    * 
    * @param angle
    *           the angle to normalize
    * @return the normalized angle that will be in the range of <code>[-PI, PI)</code>
    */
   public static final double relative(double angle) {
      return robocode.util.Utils.normalRelativeAngle(angle);
   }

   /**
    * Returns the x coordinate of a point projected at a specified angle and distance with a
    * specified starting coordinate.
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
    * Returns the y coordinate of a point projected at a specified angle and distance with a
    * specified starting coordinate.
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

   /**
    * Returns the sign of the specified number. This simplifies to returning a -1 if the number is
    * strictly less than 0 and returning a 1 otherwise.
    * 
    * @param n
    *           the number in question.
    * @return a -1 if the number is negative or 1 otherwise.
    */
   public static int sign(double n) {
      return (n < 0.0 ? -1 : 1);
   }

   /**
    * Returns the sign of the specified number or zero if it is such. This simplifies to returning 0
    * if the number is such, -1 if the number is less than 0, or 1 otherwise.
    * 
    * @param n
    *           the number in question.
    * @return a 0 if the number is such, -1 if the number is negative, or 1 otherwise.
    */
   public static int signZ(double n) {
      return (n == 0.0 ? 0 : sign(n));
   }

}
