package bnorm.events;

import robocode.Event;

public class RobotFiredEvent extends Event {

   private String name;
   private double firepower;

   public RobotFiredEvent(String name, long time, double firepower) {
      this.name = name;
      setTime(time);
      this.firepower = firepower;
   }

   public String getName() {
      return name;
   }

   public double getFirepower() {
      return firepower;
   }
}
