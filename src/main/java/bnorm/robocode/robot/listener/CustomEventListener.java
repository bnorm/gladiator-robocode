package bnorm.robocode.robot.listener;

import robocode.CustomEvent;

public interface CustomEventListener extends RobocodeEventListener {

   void onCustomEvent(CustomEvent event);
}
