package bnorm.robocode.robot.listener;

import robocode.SkippedTurnEvent;

public interface SkippedTurnListener extends RobocodeEventListener {

   void onSkippedTurnEvent(SkippedTurnEvent event);
}
