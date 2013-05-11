package kid.cluster.dimensions;

import kid.cluster.Comparison;
import kid.cluster.Vector;
import kid.data.Data;
import kid.robot.RobotData;
import kid.segmentation.Segmentable;
import kid.utils.Utils;

public class Distance extends Comparison {

   @Override
   public <E extends Data, F extends Segmentable, G extends Segmentable> double value(Vector<E, F, G> v) {
      if (v.getView() instanceof RobotData && v.getReference() instanceof RobotData) {
         RobotData v1_view = (RobotData) v.getView();
         RobotData v1_reference = (RobotData) v.getReference();
         return Utils.dist(v1_reference.getX(), v1_reference.getY(), v1_view.getX(), v1_view.getY());
      }
      return 0;
   }

   public String toString() {
      return "Distance";
   }
}
