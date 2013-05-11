package kid;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import kid.graphics.Colors;
import kid.utils.Utils;

import robocode.AdvancedRobot;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.DeathEvent;
import robocode.HitByBulletEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;
import robocode.WinEvent;

public class Temp extends AdvancedRobot {

   @Override
   public void run() {

      setColors(Colors.BLACK, Colors.SILVER, Colors.SILVER);
      setAdjustGunForRobotTurn(true);
      setAdjustRadarForGunTurn(true);

      setTurnRadarRight(Double.POSITIVE_INFINITY);
      while (true) {
         execute();
      }
   }

   @Override
   public void onPaint(final Graphics2D graphics) {
   }

   @Override
   public void onScannedRobot(final ScannedRobotEvent e) {
      double angle = getHeading() + e.getBearing();
      double theta = angle - getRadarHeading();

      theta += Utils.sign(theta) * Math.abs(15.0);
      setTurnRadarRight(theta);
      setTurnGunRight(angle - getGunHeading());
   }

   @Override
   public void onRobotDeath(final RobotDeathEvent e) {
   }

   @Override
   public void onHitByBullet(final HitByBulletEvent e) {
   }

   @Override
   public void onBulletHit(final BulletHitEvent e) {
   }

   @Override
   public void onBulletHitBullet(final BulletHitBulletEvent e) {
   }

   @Override
   public void onWin(final WinEvent e) {
   }

   @Override
   public void onDeath(final DeathEvent e) {
   }

   @Override
   public void onMouseClicked(final MouseEvent e) {
   }

   @Override
   public void onSkippedTurn(final SkippedTurnEvent e) {
      out.println("SKIPPED TURN! " + e.getTime());
   }

}