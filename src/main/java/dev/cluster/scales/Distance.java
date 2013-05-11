package dev.cluster.scales;

import dev.cluster.Scale;
import dev.robots.RobotData;
import dev.utils.Utils;

public class Distance extends Scale {

   @Override
   protected double getValue(RobotData view, RobotData reference) {
      return Utils.dist(reference.getX(), reference.getY(), view.getX(), view.getY());
   }

   @Override
   public String toString() {
      return "Distance";
   }
}
