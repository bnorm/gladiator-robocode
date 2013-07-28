package bnorm.events;

import bnorm.robots.IRobot;

public class RobotFoundEvent {

   private final IRobot robot;

   public RobotFoundEvent(IRobot robot) {
      this.robot = robot;
   }

   public IRobot getRobot() {
      return robot;
   }
}
