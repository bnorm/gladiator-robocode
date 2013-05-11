package dev.cluster.scales;

import dev.cluster.Scale;
import dev.robots.RobotData;
import dev.utils.Trig;
import dev.utils.Utils;

public class LateralVelocity extends Scale {

   @Override
   protected double getValue(RobotData view, RobotData reference) {
      return Math.abs(view.getVelocity()
            * Trig.sin(view.getHeading()
                  - Utils.angle(reference.getX(), reference.getY(), view.getX(), view.getY())));
   }

   @Override
   public String toString() {
      return "Lateral Velocity";
   }
}
