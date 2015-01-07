package bnorm.robocode.robot.listener;

import robocode.StatusEvent;

public interface StatusEventListener extends RobocodeEventListener {

   void onStatusEvent(StatusEvent event);
}
