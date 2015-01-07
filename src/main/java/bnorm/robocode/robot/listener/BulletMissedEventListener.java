package bnorm.robocode.robot.listener;

import robocode.BulletMissedEvent;

public interface BulletMissedEventListener extends RobocodeEventListener {

   void onBulletMissedEvent(BulletMissedEvent event);
}
