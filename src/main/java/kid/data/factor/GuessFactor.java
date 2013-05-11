package kid.data.factor;

import kid.data.Data;
import kid.utils.Utils;

// TODO documentation: class

/**
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class GuessFactor implements Data {

   private double guessFactor;

   public GuessFactor() {
      init(-1.0D);
   }

   public GuessFactor(double guessFactor) {
      init(guessFactor);
   }

   private void init(double guessFactor) {
      this.guessFactor = guessFactor;
   }

   public double getValue() {
      return guessFactor;
   }

   public double getGuessFactor() {
      return getValue();
   }

   public final int getIndex(int numOfBins) {
      return getIndex(getGuessFactor(), numOfBins);
   }

   public static final int getIndex(double guessFactor, int numOfBins) {
      return Utils.round((numOfBins - 1.0D) / 2.0D * (guessFactor + 1.0D));
   }

   public final double getAngle(double maxEscapeAngle) {
      return getAngle(getGuessFactor(), maxEscapeAngle);
   }

   public static final double getAngle(double guessFactor, double maxEscapeAngle) {
      return (guessFactor * maxEscapeAngle);
   }

   public double compare(Data d) {
      if (d instanceof GuessFactor)
         return getGuessFactor() - ((GuessFactor) d).getGuessFactor();
      return Double.POSITIVE_INFINITY;
   }

   public double max() {
      return 2.0;
   }

   public boolean equals(Object obj) {
      if (obj instanceof GuessFactor)
         return ((GuessFactor) obj).getValue() == getValue();
      return false;

   }

}
