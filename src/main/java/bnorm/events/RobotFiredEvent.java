package bnorm.events;

import bnorm.robots.IRobotSnapshot;
import robocode.Event;

public class RobotFiredEvent extends Event {

   private IRobotSnapshot snapshot;
   private double firepower;

   public RobotFiredEvent(IRobotSnapshot snapshot, double firepower) {
      this.snapshot = snapshot;
      setTime(snapshot.getTime());
      this.firepower = firepower;
   }

   public IRobotSnapshot getSnapshot() {
      return snapshot;
   }

   public double getFirepower() {
      return firepower;
   }
}
