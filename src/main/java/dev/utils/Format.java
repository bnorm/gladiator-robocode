package dev.utils;

import java.text.DecimalFormat;

public class Format {

   public static final DecimalFormat NO_DEC    = new DecimalFormat("#0;-#0");
   public static final DecimalFormat ONE_DEC   = new DecimalFormat("#0.0;-#0.0");
   public static final DecimalFormat TWO_DEC   = new DecimalFormat("#0.00;-#0.00");
   public static final DecimalFormat THREE_DEC = new DecimalFormat("#0.000;-#0.000");


   private Format() {
   }


   public static final String noDec(double n) {
      return NO_DEC.format(n);
   }

   public static final String oneDec(double n) {
      return ONE_DEC.format(n);
   }

   public static final String twoDec(double n) {
      return TWO_DEC.format(n);
   }

   public static final String threeDec(double n) {
      return THREE_DEC.format(n);
   }


   public static final String corrdinateNoDec(double x, double y) {
      return "(" + noDec(x) + ". " + noDec(y) + ")";
   }

   public static final String corrdinateOneDec(double x, double y) {
      return "(" + oneDec(x) + ". " + oneDec(y) + ")";
   }

   public static final String corrdinateTwoDec(double x, double y) {
      return "(" + twoDec(x) + ". " + twoDec(y) + ")";
   }

   public static final String corrdinateThreeDec(double x, double y) {
      return "(" + threeDec(x) + ". " + threeDec(y) + ")";
   }

}
