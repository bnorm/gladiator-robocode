package kid.data.factor;

import kid.Utils;
import kid.segmentation.Segmentable;

// TODO documentation: class

/**
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class GuessFactor implements Segmentable {

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

}
