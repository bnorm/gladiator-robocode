package kid.segmentation.segmenters;

import java.io.*;

import kid.data.*;
import kid.data.robot.RobotData;
import kid.segmentation.*;
import robocode.*;

// TODO documentation: class (0% complete)

public class DeltaVelocity<E extends Segmentable, F extends Data, G extends Data> extends Segmenter<E, F, G> implements Cloneable, Serializable,
                                                                                                            Printable {

   /**
    * Determines if a de-serialized file is compatible with this class.<BR>
    * <BR>
    * Maintainers must change this value if and only if the new version of this class is
    * not compatible with old versions.
    */
   private static final long serialVersionUID = 380831924089533186L;

   public static final double MIN_DELTA_VELOCITY_TO_SEGMENT = 1.0D;

   public DeltaVelocity(Robot robot) {
      init(-Math.max(Rules.DECELERATION, Rules.ACCELERATION), Math.max(Rules.DECELERATION, Rules.ACCELERATION));
   }

   public DeltaVelocity(double min, double max) {
      init(min, max);
   }

   private void init(double min, double max) {
      super.minValue = min;
      super.midValue = (min + max) / 2.0D;
      super.maxValue = max;
   }


   @Override
   public double getSegmentValue(F view, G reference) {
      double value = 0.0D;
      if (view instanceof RobotData) {
         RobotData robot_view = (RobotData) view;
         value = robot_view.getDeltaVelocity();
      } else
         System.out.println("ERROR: Unsupported kid.upgrade.data.Data type: " + view.getClass());
      return value;
   }

   @Override
   public boolean canSplit(int numOfBranches) {
      return (minValue - maxValue) / numOfBranches > MIN_DELTA_VELOCITY_TO_SEGMENT;
   }

   @SuppressWarnings("unchecked")
   @Override
   public Segmenter<E, F, G>[] getChildSegmenters() {
      Segmenter<E, F, G>[] children = new DeltaVelocity[2];
      children[0] = new DeltaVelocity<E, F, G>(minValue, midValue);
      children[1] = new DeltaVelocity<E, F, G>(midValue, maxValue);
      return children;
   }

   public void print(PrintStream console) {
      // TODO Auto-generated method stub
   }

   public void print(RobocodeFileOutputStream output) {
      // TODO Auto-generated method stub
   }

}