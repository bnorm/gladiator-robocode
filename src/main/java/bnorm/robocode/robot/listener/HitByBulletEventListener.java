package bnorm.robocode.robot.listener;

import robocode.HitByBulletEvent;

public interface HitByBulletEventListener extends RobocodeEventListener {

   void onHitByBulletEvent(HitByBulletEvent event);
}
