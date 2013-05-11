package bnorm.virtual;

import bnorm.events.RobotFiredEvent;
import bnorm.robots.IRobotSnapshot;
import robocode.Rules;

public class EnemyBulletWave extends Wave {

   private IRobotSnapshot snapshot;

   public EnemyBulletWave(IRobotSnapshot snapshot, double velocity, long time) {
      super(snapshot.getX(), snapshot.getY(), velocity, time);
      this.snapshot = snapshot;
   }

   public EnemyBulletWave(IRobotSnapshot snapshot, RobotFiredEvent event) {
      this(snapshot, Rules.getBulletSpeed(event.getFirepower()), event.getTime());
   }

   public IRobotSnapshot getEnemy() {
      return snapshot;
   }
}
