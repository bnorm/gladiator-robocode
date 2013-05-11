package kid.cluster.dimensions;

import kid.cluster.Comparison;
import kid.cluster.Vector;
import kid.data.Data;
import kid.robot.RobotData;
import kid.segmentation.Segmentable;

public class BulletFirePower extends Comparison {

   @Override
   public <E extends Data, F extends Segmentable, G extends Segmentable> double value(Vector<E, F, G> v) {
      if (v.getView() instanceof RobotData && v.getReference() instanceof RobotData) {
         RobotData reference = (RobotData) v.getReference();
         return Math.abs(reference.getDeltaEnergy());
      }
      return 0;
   }

   public String toString() {
      return "BulletFirePower";
   }
}
