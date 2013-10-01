package bnorm.utils;

/**
 * A utility class for common Robocode functions.
 *
 * @author Brian Norman
 * @version 1.2
 */
public final class Utils {

   /**
    * Don't let anyone instantiate this class.
    */
   private Utils() {
   }

   /**
    * Returns the limited value specified.  The minimum and maximum values specified will be
    * returned if the specified value is less than or greater than the limits.
    *
    * @param min the minimum limit.
    * @param n the value that will be limited.
    * @param max the maximum limit.
    * @return the limited value.
    */
   public static double limit(double min, double n, double max) {
      return Math.max(Math.min(n, max), min);
   }

   /**
    * Normalizes an angle to an absolute angle. The normalized angle will be in the range from
    * <code>0</code> to <code>2*PI</code>, where <code>2*PI</code> itself is not included.
    *
    * @param angle
    *           the angle to normalize
    * @return the normalized angle that will be in the range of <code>[0, 2*PI)</code>
    */
   public static double absolute(double angle) {
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
   public static double relative(double angle) {
      return robocode.util.Utils.normalRelativeAngle(angle);
   }

   /**
    * Returns true if the two specified angles are equal.  The specified epsilon value will be used
    * as a maximum difference between the two angles for them to be considered equal.
    *
    * @param angle1 the first angle.
    * @param angle2 the second angle.
    * @param epsilon the maximum difference between the angles.
    * @return if the two angles are equal.
    */
   public static boolean equalAngle(double angle1, double angle2, double epsilon) {
      return Math.abs(absolute(angle1) - absolute(angle2)) <= epsilon;
   }

   /**
    * Returns true if the two specified angles are equal.
    *
    * @param angle1 the first angle.
    * @param angle2 the second angle.
    * @return if the two angles are equal.
    */
   public static boolean equalAngle(double angle1, double angle2) {
      return equalAngle(angle1, angle2, 1.0E-10);
   }

   /**
    * Returns true if the two specified doubles are equal.  The specified epsilon value will be used
    * as a maximum difference between the two doubles for them to be considered equal.
    *
    * @param n1 the first double.
    * @param n2 the second double.
    * @param epsilon the maximum difference between the angles.
    * @return if the two angles are equal.
    */
   public static boolean equal(double n1, double n2, double epsilon) {
      return Math.abs(n1 - n2) <= epsilon;
   }

   /**
    * Returns true if the two specified doubles are equal.
    *
    * @param n1 the first double.
    * @param n2 the second double.
    * @return if the two angles are equal.
    */
   public static boolean equal(double n1, double n2) {
      return equal(n1, n2, 1.0E-10);
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
   public static double projectX(double x, double a, double d) {
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
   public static double vectorX(double a, double l) {
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
   public static double projectY(double y, double a, double d) {
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
   public static double vectorY(double a, double l) {
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

   /**
    * Returns the square of the specified number.
    *
    * @param n the number to square.
    * @return the square of the specified number.
    */
   public static double sqr(double n) {
      return n * n;
   }
}
