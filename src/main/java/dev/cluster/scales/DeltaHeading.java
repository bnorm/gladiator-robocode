package dev.cluster.scales;

import dev.cluster.Scale;
import dev.robots.RobotData;

public class DeltaHeading extends Scale {

   @Override
   protected double getValue(RobotData view, RobotData reference) {
      return view.getDeltaHeading();
   }

   @Override
   public String toString() {
      return "Delta Heading";
   }
}
