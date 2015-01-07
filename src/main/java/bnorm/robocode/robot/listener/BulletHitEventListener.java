package bnorm.robocode.robot.listener;

import robocode.BulletHitEvent;

public interface BulletHitEventListener extends RobocodeEventListener {

   void onBulletHitEvent(BulletHitEvent event);
}
