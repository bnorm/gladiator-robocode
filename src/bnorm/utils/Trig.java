package bnorm.utils;

/**
 * A utility class for common trigonometric functions.
 * 
 * @author Brian Norman
 * @version 1.1
 */
public final class Trig {

   /**
    * The {@link Double} representation of a complete circle in radians, which is 2*PI.
    */
   public static final double CIRCLE         = 2.0 * Math.PI;

   /**
    * The {@link Double} representation of half of a complete circle in radians, which is PI.
    */
   public static final double HALF_CIRCLE    = Math.PI;

   /**
    * The {@link Double} representation of quarter of a complete circle in radians, which is PI/2.
    */
   public static final double QUARTER_CIRCLE = Math.PI / 2.0;

   /**
    * Don't let anyone instantiate this class.
    */
   private Trig() {
   }

   /**
    * Returns the angle from the origin to the specified coordinates, <code>(x, y)</code>.
    * 
    * @param x
    *           the ordinate coordinate.
    * @param y
    *           the abscissa coordinate.
    * @return the angle to the specified coordinates.
    */
   public static final double angle(double x, double y) {
      return StrictMath.atan2(x, y);
   }

   /**
    * Returns the sine of the specified angle in radians.
    * 
    * @param radians
    *           an angle in radians.
    * @return the sine of the argument.
    */
   public static final double sin(double radians) {
      return StrictMath.sin(radians);
   }

   /**
    * Returns the cosine of the specified angle in radians.
    * 
    * @param radians
    *           an angle in radians.
    * @return the cosine of the argument.
    */
   public static final double cos(double radians) {
      return StrictMath.cos(radians);
   }

   /**
    * Returns the tangent of the specified angle in radians.
    * 
    * @param radians
    *           an angle in radians.
    * @return the tangent of the argument.
    */
   public static final double tan(double radians) {
      return StrictMath.tan(radians);
   }

   /**
    * Returns the arc sine of the specified angle in radians.
    * 
    * @param ratio
    *           the ratio between sides.
    * @return the arc sine of the argument.
    */
   public static final double asin(double ratio) {
      return StrictMath.asin(ratio);
   }

   /**
    * Returns the arc cosine of the specified angle in radians.
    * 
    * @param ratio
    *           the ratio between sides.
    * @return the arc cosine of the argument.
    */
   public static final double acos(double ratio) {
      return StrictMath.acos(ratio);
   }

   /**
    * Returns the arc tangent of the specified angle in radians.
    * 
    * @param ratio
    *           the ratio between sides.
    * @return the arc tangent of the argument.
    */
   public static final double atan(double ratio) {
      return StrictMath.atan(ratio);
   }

   /**
    * Returns the angle <i>theta</i> from the conversion of rectangular coordinates (<code>x</code>
    * ,&nbsp;<code>y</code>) to Robocode polar coordinates (<i>r</i>,&nbsp;<i>theta</i>).
    * 
    * @param x
    *           the abscissa coordinate.
    * @param y
    *           the ordinate coordinate.
    * @return the <i>theta</i> component of the point (<i>r</i>,&nbsp;<i>theta</i>) in Robocode
    *         polar coordinates that corresponds to the point (<code>x</code>,&nbsp;<code>y</code>)
    *         in Cartesian coordinates.
    */
   public static final double atan2(double x, double y) {
      return StrictMath.atan2(x, y);
   }

}
