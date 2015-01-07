package bnorm.robocode.robot.listener;

import robocode.HitWallEvent;

public interface HitWallEventListener extends RobocodeEventListener {

   void onHitWallEvent(HitWallEvent event);
}
