package bnorm.robocode.robot.listener;

import robocode.RobotDeathEvent;

public interface RobotDeathEventListener extends RobocodeEventListener {

   void onRobotDeathEvent(RobotDeathEvent event);
}
