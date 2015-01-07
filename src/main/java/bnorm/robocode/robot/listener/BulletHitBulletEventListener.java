package bnorm.robocode.robot.listener;

import robocode.BulletHitBulletEvent;

public interface BulletHitBulletEventListener extends RobocodeEventListener {

   void onBulletHitBulletEvent(BulletHitBulletEvent event);
}
