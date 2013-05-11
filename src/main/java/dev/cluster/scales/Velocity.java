package dev.cluster.scales;

import dev.cluster.Scale;
import dev.robots.RobotData;

public class Velocity extends Scale {

   @Override
   protected double getValue(RobotData view, RobotData reference) {
      return Math.abs(view.getVelocity());
   }

   @Override
   public String toString() {
      return "Velocity";
   }

}
