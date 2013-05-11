package kid.cluster.dimensions;

import kid.cluster.Comparison;
import kid.cluster.Vector;
import kid.data.Data;
import kid.robot.RobotData;
import kid.segmentation.Segmentable;
import kid.utils.Utils;

public class LateralVelocity extends Comparison {

   @Override
   public <E extends Data, F extends Segmentable, G extends Segmentable> double value(Vector<E, F, G> v) {
      if (v.getView() instanceof RobotData && v.getReference() instanceof RobotData) {
         RobotData v1_view = (RobotData) v.getView();
         RobotData v1_reference = (RobotData) v.getReference();
         return Math.abs(v1_view.getVelocity() * Utils.sin(v1_view.getHeading() - Utils.angle(v1_reference, v1_view)));
      }
      return 0;
   }

   public String toString() {
      return "LateralVelocity";
   }
}
