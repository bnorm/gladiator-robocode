package dev.utils;

/**
 * @author Alexander Schultz (a.k.a. Rednaxela)
 * @author Julian Kent (a.k.a. Skilgannon)
 * @author Nat Pavasant
 */
public final class Trig {

   public static final double    TWO_PI              = 6.2831853071795864769252867665590D;
   public static final double    CIRCLE              = TWO_PI;
   // public static final double THREE_OVER_TWO_PI = 4.7123889803846898576939650749193D;
   public static final double    PI                  = 3.1415926535897932384626433832795D;
   public static final double    HALF_CIRCLE         = PI;
   public static final double    HALF_PI             = 1.5707963267948966192313216916398D;
   public static final double    QUARTER_CIRCLE      = HALF_PI;
   public static final double    QUARTER_PI          = 0.7853981633974483096156608458199D;
   public static final double    EIGHTIETH_CIRCLE    = QUARTER_PI;


   /* Settings for trig */
   private static final int      TRIG_DIVISIONS      = 8192;                              /* Must be power of 2 */
   private static final int      TRIG_HIGH_DIVISIONS = 131072;                            /* Must be power of 2 */
   private static final double   K                   = TRIG_DIVISIONS / TWO_PI;
   private static final double   ACOS_K              = (TRIG_HIGH_DIVISIONS - 1) / 2;
   private static final double   TAN_K               = TRIG_HIGH_DIVISIONS / PI;


   /* Lookup tables */
   private static final double[] sineTable           = new double[TRIG_DIVISIONS];
   private static final double[] tanTable            = new double[TRIG_HIGH_DIVISIONS];
   private static final double[] acosTable           = new double[TRIG_HIGH_DIVISIONS];


   static {
      // new Thread(new Runnable() {
      // @Override
      // public void run() {
      // for (int i = 0; i < TRIG_DIVISIONS; i++) {
      // sineTable[i] = Math.sin(i / K);
      // }
      // for (int i = 0; i < TRIG_HIGH_DIVISIONS; i++) {
      // tanTable[i] = Math.tan(i / TAN_K);
      // acosTable[i] = Math.acos(i / ACOS_K - 1);
      // }
      // }
      // }).start();
   }

   private Trig() {
   }


   /* --------------------- */
   /* Normal trig functions */
   /* --------------------- */

   public static final double sin(double radians) {
      return StrictMath.sin(radians);
   }

   public static final double csc(double radians) {
      return 1.0 / StrictMath.sin(radians);
   }

   public static final double cos(double radians) {
      return StrictMath.cos(radians);
   }

   public static final double sec(double radians) {
      return 1.0 / StrictMath.cos(radians);
   }

   public static final double tan(double radians) {
      return StrictMath.tan(radians);
   }

   public static final double cot(double radians) {
      return 1.0 / StrictMath.tan(radians);
   }

   public static final double asin(double ratio) {
      return StrictMath.asin(ratio);
   }

   public static final double acos(double ratio) {
      return StrictMath.acos(ratio);
   }

   public static final double atan(double ratio) {
      return StrictMath.atan(ratio);
   }

   public static final double atan2(double x, double y) {
      return StrictMath.atan2(x, y);
   }


   /* ------------------------------------ */
   /* Trig functions for angles in degrees */
   /* ------------------------------------ */

   public static final double d_sin(double degrees) {
      return StrictMath.sin(Math.toRadians(degrees));
   }

   public static final double d_csc(double degrees) {
      return 1.0 / StrictMath.sin(Math.toRadians(degrees));
   }

   public static final double d_cos(double degrees) {
      return StrictMath.cos(Math.toRadians(degrees));
   }

   public static final double d_sec(double degrees) {
      return 1.0 / StrictMath.cos(Math.toRadians(degrees));
   }

   public static final double d_tan(double degrees) {
      return StrictMath.tan(Math.toRadians(degrees));
   }

   public static final double d_cot(double degrees) {
      return 1.0 / StrictMath.tan(Math.toRadians(degrees));
   }

   public static final double d_asin(double ratio) {
      return Math.toDegrees(StrictMath.asin(ratio));
   }

   public static final double d_acos(double ratio) {
      return Math.toDegrees(StrictMath.acos(ratio));
   }

   public static final double d_atan(double ratio) {
      return Math.toDegrees(StrictMath.atan(ratio));
   }

   public static final double d_atan2(double x, double y) {
      return Math.toDegrees(StrictMath.atan2(x, y));
   }


   /* ------------------------------------------- */
   /* Fast and reasonable accurate trig functions */
   /* ------------------------------------------- */

   public static final double t_sin(double radians) {
      return sineTable[(int) (((radians * K + 0.5) % TRIG_DIVISIONS + TRIG_DIVISIONS)) & (TRIG_DIVISIONS - 1)];
   }

   public static final double t_cos(double radians) {
      return sineTable[(int) (((radians * K + 0.5) % TRIG_DIVISIONS + 1.25 * TRIG_DIVISIONS)) & (TRIG_DIVISIONS - 1)];
   }

   public static final double t_tan(double radians) {
      return tanTable[(int) (((radians * TAN_K + 0.5) % TRIG_HIGH_DIVISIONS + TRIG_HIGH_DIVISIONS))
            & (TRIG_HIGH_DIVISIONS - 1)];
   }

   public static final double t_asin(double ratio) {
      return HALF_PI - t_acos(ratio);
   }

   public static final double t_acos(double ratio) {
      return acosTable[(int) (ratio * ACOS_K + (ACOS_K + 0.5))];
   }

   public static final double t_atan(double ratio) {
      return (ratio >= 0 ? t_acos(1 / Math.sqrt(ratio * ratio + 1)) : -t_acos(1 / Math.sqrt(ratio * ratio + 1)));
   }

   public static final double t_atan2(double x, double y) {
      return (x >= 0 ? t_acos(y / Math.sqrt(x * x + y * y)) : -t_acos(y / Math.sqrt(x * x + y * y)));
   }

}
