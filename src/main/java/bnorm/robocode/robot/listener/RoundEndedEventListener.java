package bnorm.robocode.robot.listener;

import robocode.RoundEndedEvent;

public interface RoundEndedEventListener extends RobocodeEventListener {

   void onRoundEndedEvent(RoundEndedEvent event);
}
