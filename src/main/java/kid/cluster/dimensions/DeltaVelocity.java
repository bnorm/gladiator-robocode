package kid.cluster.dimensions;

import kid.cluster.Comparison;
import kid.cluster.Vector;
import kid.data.Data;
import kid.robot.RobotData;
import kid.segmentation.Segmentable;

public class DeltaVelocity extends Comparison {

   @Override
   public <E extends Data, F extends Segmentable, G extends Segmentable> double value(Vector<E, F, G> v) {
      if (v.getView() instanceof RobotData && v.getReference() instanceof RobotData) {
         RobotData v1_view = (RobotData) v.getView();
         return v1_view.getDeltaVelocity();
      }
      return 0;
   }

   public String toString() {
      return "DeltaVelocity";
   }
}
