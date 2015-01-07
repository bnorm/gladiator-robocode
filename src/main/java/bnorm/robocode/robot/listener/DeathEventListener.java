package bnorm.robocode.robot.listener;

import robocode.DeathEvent;

public interface DeathEventListener extends RobocodeEventListener {

   void onDeathEvent(DeathEvent event);
}
