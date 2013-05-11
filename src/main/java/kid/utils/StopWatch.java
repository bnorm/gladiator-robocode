package kid.utils;

import java.text.DecimalFormat;

public class StopWatch extends java.lang.Object {
   private long startTime = -1;
   private long endTime = -1;

   public StopWatch() {
      start();
   }

   public void start() {
      startTime = System.nanoTime();
      endTime = -1;
   }

   public void stop() {
      endTime = System.nanoTime();
   }

   public long nanoTime() {
      return ((endTime < 0 ? System.nanoTime() : endTime) - startTime);
   }

   public double micoTime() {
      return nanoTime() / 1000.0D;
   }

   public double milliTime() {
      return nanoTime() / 1000000.0D;
   }

   @Override
   public String toString() {
      DecimalFormat decimal = new DecimalFormat("#,###0.000 000 ms");
      return decimal.format(milliTime());
   }
}
