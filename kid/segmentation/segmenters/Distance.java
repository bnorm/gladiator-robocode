package kid.segmentation.segmenters;

import java.io.*;

import robocode.*;

import kid.Utils;
import kid.data.*;
import kid.data.robot.RobotData;
import kid.segmentation.*;

// TODO documentation: class (0% complete)

public class Distance<E extends Segmentable, F extends Data, G extends Data> extends Segmenter<E, F, G> implements Cloneable, Serializable,
                                                                                                       Printable {

   /**
    * Determines if a de-serialized file is compatible with this class.<BR>
    * <BR>
    * Maintainers must change this value if and only if the new version of this class is
    * not compatible with old versions.
    */
   private static final long serialVersionUID = -6799983340289927774L;

   public static final double MIN_DISTANCE_TO_SEGMENT = Utils.sqr(100.0D);

   public Distance(Robot robot) {
      init(0.0D, Utils.sqr(Math.max(robot.getBattleFieldHeight(), robot.getBattleFieldWidth())));
   }

   public Distance(double min, double max) {
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
            value = Utils.getDistSq(robot_view.getX(), robot_view.getY(), robot_reference.getX(), robot_reference.getY());
         } else {
            System.out.println("ERROR: Unsupported kid.upgrade.data.Data type: " + reference.getClass());
         }
      } else {
         System.out.println("ERROR: Unsupported kid.upgrade.data.Data type: " + view.getClass());
      }
      return value;
   }

   @Override
   public boolean canSplit(int numOfBranches) {
      return (minValue - maxValue) / numOfBranches > MIN_DISTANCE_TO_SEGMENT;
   }

   @SuppressWarnings("unchecked")
   @Override
   public Segmenter<E, F, G>[] getChildSegmenters() {
      Segmenter<E, F, G>[] children = new Distance[2];
      children[0] = new Distance<E, F, G>(minValue, midValue);
      children[1] = new Distance<E, F, G>(midValue, maxValue);
      return children;
   }

   public void print(PrintStream console) {
      // TODO Auto-generated method stub
   }

   public void print(RobocodeFileOutputStream output) {
      // TODO Auto-generated method stub
   }

}
