package dev.cluster.scales;

import dev.cluster.Scale;
import dev.robots.RobotData;

public class BulletFirePower extends Scale {

   @Override
   protected double getValue(RobotData view, RobotData reference) {
      return Math.abs(reference.getDeltaEnergy());
   }

   @Override
   public String toString() {
      return "Bullet Fire Power";
   }
}
