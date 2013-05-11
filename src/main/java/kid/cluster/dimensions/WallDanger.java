package kid.cluster.dimensions;

import java.awt.geom.Rectangle2D;

import kid.cluster.Comparison;
import kid.cluster.Vector;
import kid.data.Data;
import kid.robot.RobotData;
import kid.segmentation.Segmentable;
import kid.utils.Utils;

public class WallDanger extends Comparison {

   private Rectangle2D battleFeild;

   public WallDanger(Rectangle2D battleFeild) {
      this.battleFeild = battleFeild;
   }

   @Override
   public <E extends Data, F extends Segmentable, G extends Segmentable> double value(Vector<E, F, G> vector) {
      double danger = Double.POSITIVE_INFINITY;
      if (battleFeild != null && vector.getView() instanceof RobotData) {
         RobotData v = (RobotData) vector.getView();
         double min = Math.min(Math.min(v.getX(), v.getY()), Math.min(battleFeild.getMaxX() - v.getX(), battleFeild.getMaxY()
               - v.getY()));
         if (min == v.getX()) {
            danger = Math.abs(min / Utils.acos(v.getHeading() - 90)); 
         } else if (min == v.getY()) {
            danger = Math.abs(min / Utils.acos(v.getHeading())); 
         } else if (min == battleFeild.getMaxX() - v.getX()) {
            danger = Math.abs(min / Utils.acos(v.getHeading() - 90)); 
         } else {
            danger = Math.abs(min / Utils.acos(v.getHeading())); 
         }
      }
      return danger;
   }

   @Override
   public String toString() {
      return "Wall Danger";
   }
}
