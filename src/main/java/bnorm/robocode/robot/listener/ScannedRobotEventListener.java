package bnorm.robocode.robot.listener;

import robocode.ScannedRobotEvent;

public interface ScannedRobotEventListener extends RobocodeEventListener {

   void onScannedRobotEvent(ScannedRobotEvent event);
}
