package dev.cluster.scales;

import dev.cluster.Scale;
import dev.robots.RobotData;

public class DeltaVelocity extends Scale {

   @Override
   protected double getValue(RobotData view, RobotData reference) {
      return view.getDeltaVelocity();
   }

   @Override
   public String toString() {
      return "Delta Velocity";
   }
}
