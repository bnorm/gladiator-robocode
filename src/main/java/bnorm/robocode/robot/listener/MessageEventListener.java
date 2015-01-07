package bnorm.robocode.robot.listener;

import robocode.MessageEvent;

public interface MessageEventListener extends RobocodeEventListener {

   void onMessageEvent(MessageEvent event);
}
