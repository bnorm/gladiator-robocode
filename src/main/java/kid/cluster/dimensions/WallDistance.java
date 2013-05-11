package kid.cluster.dimensions;

import java.awt.geom.Rectangle2D;

import kid.cluster.Comparison;
import kid.cluster.Vector;
import kid.data.Data;
import kid.robot.RobotData;
import kid.segmentation.Segmentable;

public class WallDistance extends Comparison {

   private Rectangle2D battleFeild;

   public WallDistance(Rectangle2D battleFeild) {
      this.battleFeild = battleFeild;
   }

   @Override
   public <E extends Data, F extends Segmentable, G extends Segmentable> double value(Vector<E, F, G> vector) {
      if (battleFeild != null && vector.getView() instanceof RobotData) {
         RobotData v = (RobotData) vector.getView();
         return Math.min(Math.min(v.getX(), v.getY()), Math.min(battleFeild.getMaxX() - v.getX(), battleFeild.getMaxY()
               - v.getY()));
      }
      return Double.POSITIVE_INFINITY;
   }

   @Override
   public String toString() {
      return "Wall Distance";
   }
}
