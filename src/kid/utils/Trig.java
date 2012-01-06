package kid.utils;

/**
 * A utility class for common trigonometric functions.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
public final class Trig {

   /**
    * Don't let anyone instantiate this class.
    */
   private Trig() {
   }

   /**
    * Returns the sine of the specified angle in radians.
    * 
    * @param radians
    *           an angle.
    * @return the sine of the argument.
    */
   public static final double sin(double radians) {
      return StrictMath.sin(radians);
   }

   /**
    * Returns the cosecant of the specified angle in radians.
    * 
    * @param radians
    *           an angle.
    * @return the cosecant of the argument.
    */
   public static final double csc(double radians) {
      return 1.0 / StrictMath.sin(radians);
   }

   /**
    * Returns the cosine of the specified angle in radians.
    * 
    * @param radians
    *           an angle.
    * @return the cosine of the argument.
    */
   public static final double cos(double radians) {
      return StrictMath.cos(radians);
   }

   /**
    * Returns the secant of the specified angle in radians.
    * 
    * @param radians
    *           an angle.
    * @return the secant of the argument.
    */
   public static final double sec(double radians) {
      return 1.0 / StrictMath.cos(radians);
   }

   /**
    * Returns the tangent of the specified angle in radians.
    * 
    * @param radians
    *           an angle.
    * @return the tangent of the argument.
    */
   public static final double tan(double radians) {
      return StrictMath.tan(radians);
   }

   /**
    * Returns the cotangent of the specified angle in radians.
    * 
    * @param radians
    *           an angle.
    * @return the cotangent of the argument.
    */
   public static final double cot(double radians) {
      return 1.0 / StrictMath.tan(radians);
   }

   /**
    * Returns the arc sine of the specified angle in radians.
    * 
    * @param radians
    *           an angle.
    * @return the arc sine of the argument.
    */
   public static final double asin(double ratio) {
      return StrictMath.asin(ratio);
   }

   /**
    * Returns the arc cosine of the specified angle in radians.
    * 
    * @param radians
    *           an angle.
    * @return the arc cosine of the argument.
    */
   public static final double acos(double ratio) {
      return StrictMath.acos(ratio);
   }

   /**
    * Returns the arc tangent of the specified angle in radians.
    * 
    * @param radians
    *           an angle.
    * @return the arc tangent of the argument.
    */
   public static final double atan(double ratio) {
      return StrictMath.atan(ratio);
   }

   /**
    * Returns the angle <i>theta</i> from the conversion of rectangular
    * coordinates (<code>x</code>,&nbsp;<code>y</code>) to Robocode polar
    * coordinates (<i>r</i>,&nbsp;<i>theta</i>).
    * 
    * @param x
    *           the abscissa coordinate
    * @param y
    *           the ordinate coordinate
    * @return the <i>theta</i> component of the point
    *         (<i>r</i>,&nbsp;<i>theta</i>) in Robocode polar coordinates that
    *         corresponds to the point (<code>x</code>,&nbsp;<code>y</code>) in
    *         Cartesian coordinates.
    */
   public static final double atan2(double x, double y) {
      return StrictMath.atan2(x, y);
   }

}
