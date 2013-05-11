package dev.cluster.scales;

import java.awt.geom.Rectangle2D;

import dev.cluster.Scale;
import dev.robots.RobotData;
import dev.utils.Trig;

public class WallDanger extends Scale {

   private Rectangle2D battleFeild;

   public WallDanger(Rectangle2D battleFeild) {
      this.battleFeild = battleFeild;
   }

   @Override
   protected double getValue(RobotData view, RobotData reference) {
      double danger = Double.POSITIVE_INFINITY;
      if (this.battleFeild != null) {
         double min = Math.min(Math.min(view.getX(), view.getY()), Math.min(this.battleFeild.getMaxX()
               - view.getX(), this.battleFeild.getMaxY() - view.getY()));
         if (min == view.getX()) {
            danger = Math.abs(min / Trig.acos(view.getHeading() - 90));
         } else if (min == view.getY()) {
            danger = Math.abs(min / Trig.acos(view.getHeading()));
         } else if (min == this.battleFeild.getMaxX() - view.getX()) {
            danger = Math.abs(min / Trig.acos(view.getHeading() - 90));
         } else {
            danger = Math.abs(min / Trig.acos(view.getHeading()));
         }
      }
      return danger;
   }

   @Override
   public String toString() {
      return "Wall Danger";
   }
}
