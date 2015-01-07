package bnorm.robocode.robot.listener;

import robocode.HitRobotEvent;

public interface HitRobotEventListener extends RobocodeEventListener {

   void onHitRobotEvent(HitRobotEvent event);
}
