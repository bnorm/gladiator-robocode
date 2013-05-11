package kid.twins;

import java.awt.Color;

import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.DeathEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.MessageEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;
import robocode.TeamRobot;
import robocode.WinEvent;

public class Gimli extends TeamRobot {

   @Override
   public void run() {

      setColors(new Color(9, 2, 2), new Color(9, 5, 2), new Color(64, 64, 64));
      // setAdjustRadarForGunTurn(true);
      setAdjustGunForRobotTurn(true);

      while (true) {
         execute();
      }
   }

   // public void onPaint(java.awt.Graphics2D g) {
   // }

   @Override
   public void onScannedRobot(ScannedRobotEvent e) {
      BotManager.onEvent(e, this);
   }

   @Override
   public void onRobotDeath(RobotDeathEvent e) {
      BotManager.onEvent(e, this);
   }

   @Override
   public void onWin(WinEvent e) {
   }

   @Override
   public void onDeath(DeathEvent e) {
   }

   @Override
   public void onHitRobot(HitRobotEvent e) {
   }

   @Override
   public void onBulletHit(BulletHitEvent e) {
   }

   @Override
   public void onBulletMissed(BulletMissedEvent e) {
   }

   @Override
   public void onBulletHitBullet(BulletHitBulletEvent e) {
   }

   @Override
   public void onHitByBullet(HitByBulletEvent e) {
   }

   @Override
   public void onHitWall(HitWallEvent e) {
   }

   @Override
   public void onSkippedTurn(SkippedTurnEvent e) {
   }

   @Override
   public void onMessageReceived(MessageEvent e) {
   }
}