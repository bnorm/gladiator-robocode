package dev.cluster.scales;

import java.awt.geom.Rectangle2D;

import dev.cluster.Scale;
import dev.robots.RobotData;
import dev.utils.Utils;

public class WallDistance extends Scale {

   private Rectangle2D battleFeild;

   public WallDistance(Rectangle2D battleFeild) {
      this.battleFeild = battleFeild;
   }

   @Override
   protected double getValue(RobotData view, RobotData reference) {
      return Utils.min(view.getX(), view.getY(), this.battleFeild.getMaxX() - view.getX(), this.battleFeild.getMaxY()
            - view.getY());
   }

   @Override
   public String toString() {
      return "Wall Distance";
   }

}
