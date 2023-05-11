package kid.segmentation.segmenters;

import java.io.*;

import robocode.*;

import kid.Utils;
import kid.data.*;
import kid.data.info.RobotInfo;
import kid.data.robot.RobotData;
import kid.segmentation.*;

// TODO documentation: class (0% complete)

public class LateralVelocity<E extends Segmentable, F extends Data, G extends Data> extends Segmenter<E, F, G> implements Cloneable, Serializable,
                                                                                                              Printable {

   /**
    * Determines if a de-serialized file is compatible with this class.<BR>
    * <BR>
    * Maintainers must change this value if and only if the new version of this class is
    * not compatible with old versions.
    */
   private static final long serialVersionUID = 2571262597615043669L;

   public static final double MIN_LATERAL_VELOCITY_TO_SEGMENT = 1.0D;

   public LateralVelocity(Robot robot) {
      init(0.0D, RobotInfo.MAX_VELOCITY);
   }

   public LateralVelocity(double min, double max) {
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
         if (reference instanceof RobotData) {
            RobotData robot_view = (RobotData) view;
            RobotData robot_reference = (RobotData) reference;

            value = Math.abs(robot_view.getVelocity() * Utils.sin(robot_view.getHeading() - Utils.getAngle(robot_reference, robot_view)));
         } else {
            System.out.println("ERROR: Unsupported kid.upgrade.data.Data type: " + reference.getClass());
         }
      } else {
         System.out.println("ERROR: Unsupported kid.upgrade.data.Data type: " + view.getClass());
         if (!(reference instanceof RobotData))
            System.out.println("ERROR: Unsupported kid.upgrade.data.Data type: " + reference.getClass());
      }
      return value;
   }
   @Override
   public boolean canSplit(int numOfBranches) {
      return (minValue - maxValue) / numOfBranches > MIN_LATERAL_VELOCITY_TO_SEGMENT;
   }

   @SuppressWarnings("unchecked")
   @Override
   public Segmenter<E, F, G>[] getChildSegmenters() {
      Segmenter<E, F, G>[] children = new LateralVelocity[2];
      children[0] = new LateralVelocity<E, F, G>(minValue, midValue);
      children[1] = new LateralVelocity<E, F, G>(midValue, maxValue);
      return children;
   }

   public void print(PrintStream console) {
      // TODO Auto-generated method stub
   }

   public void print(RobocodeFileOutputStream output) {
      // TODO Auto-generated method stub
   }

}
