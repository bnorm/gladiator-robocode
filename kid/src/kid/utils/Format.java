package kid.utils;

import java.text.DecimalFormat;

/**
 * A utility class that provides the coder with the ability to quickly produce
 * string representations of numeric values in a specific format.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
public class Format {

   /**
    * A DecimalFormat object that formats given values with no decimals.
    */
   public static final DecimalFormat NO_DEC    = new DecimalFormat("#0;-#0");

   /**
    * A DecimalFormat object that formats given values with one decimal.
    */
   public static final DecimalFormat ONE_DEC   = new DecimalFormat("#0.0;-#0.0");

   /**
    * A DecimalFormat object that formats given values with two decimals.
    */
   public static final DecimalFormat TWO_DEC   = new DecimalFormat("#0.00;-#0.00");

   /**
    * A DecimalFormat object that formats given values with three decimals.
    */
   public static final DecimalFormat THREE_DEC = new DecimalFormat("#0.000;-#0.000");

   /**
    * Don't let anyone instantiate this class.
    */
   private Format() {
   }

   /**
    * Returns the specified double value formatted with the no decimals.
    * 
    * @param n
    *           the double value to format.
    * @return the formatted string of the specified value.
    */
   public static final String dec0(double n) {
      return NO_DEC.format(n);
   }

   /**
    * Returns the specified double value formatted with the one decimal.
    * 
    * @param n
    *           the double value to format.
    * @return the formatted string of the specified value.
    */
   public static final String dec1(double n) {
      return ONE_DEC.format(n);
   }

   /**
    * Returns the specified double value formatted with the two decimals.
    * 
    * @param n
    *           the double value to format.
    * @return the formatted string of the specified value.
    */
   public static final String dec2(double n) {
      return TWO_DEC.format(n);
   }

   /**
    * Returns the specified double value formatted with the three decimals.
    * 
    * @param n
    *           the double value to format.
    * @return the formatted string of the specified value.
    */
   public static final String dec3(double n) {
      return THREE_DEC.format(n);
   }

   /**
    * Returns the specified double values formatted as a coordinate with no
    * decimals.
    * 
    * @param x
    *           the double value of the x coordinate.
    * @param y
    *           the double value of the y coordinate.
    * @return the formatted string of the specified coordinate.
    */
   public static final String coordinateDec0(double x, double y) {
      return "(" + dec0(x) + ", " + dec0(y) + ")";
   }

   /**
    * Returns the specified double values formatted as a coordinate with one
    * decimal.
    * 
    * @param x
    *           the double value of the x coordinate.
    * @param y
    *           the double value of the y coordinate.
    * @return the formatted string of the specified coordinate.
    */
   public static final String coordinateDec1(double x, double y) {
      return "(" + dec1(x) + ", " + dec1(y) + ")";
   }

   /**
    * Returns the specified double values formatted as a coordinate with two
    * decimals.
    * 
    * @param x
    *           the double value of the x coordinate.
    * @param y
    *           the double value of the y coordinate.
    * @return the formatted string of the specified coordinate.
    */
   public static final String coordinateDec2(double x, double y) {
      return "(" + dec2(x) + ", " + dec2(y) + ")";
   }

   /**
    * Returns the specified double values formatted as a coordinate with three
    * decimals.
    * 
    * @param x
    *           the double value of the x coordinate.
    * @param y
    *           the double value of the y coordinate.
    * @return the formatted string of the specified coordinate.
    */
   public static final String coordinateDec3(double x, double y) {
      return "(" + dec3(x) + ", " + dec3(y) + ")";
   }

}
