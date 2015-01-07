package bnorm.robocode.robot.listener;

import robocode.BattleEndedEvent;

public interface BattleEndedEventListener extends RobocodeEventListener {

   void onBattleEndedEvent(BattleEndedEvent event);
}
