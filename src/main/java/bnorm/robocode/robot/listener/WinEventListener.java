package bnorm.robocode.robot.listener;

import robocode.WinEvent;

public interface WinEventListener extends RobocodeEventListener {

   void onWinEvent(WinEvent event);
}
