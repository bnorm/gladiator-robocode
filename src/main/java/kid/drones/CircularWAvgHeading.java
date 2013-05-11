package kid.drones;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import kid.data.RobotChooser;
import kid.graphics.Colors;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.management.RobotManager;
import kid.movement.gun.GunMovement;
import kid.movement.radar.RadarMovement;
import kid.robot.RobotData;
import kid.targeting.CircularTargeting;
import kid.targeting.Targeting;
import kid.utils.Utils;
import robocode.AdvancedRobot;
import robocode.DeathEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;

public class CircularWAvgHeading extends AdvancedRobot {

   private RobotManager robots;
   private RadarMovement radar;
   private GunMovement gun;
   private Targeting targeting;

   @Override
   public void run() {

      robots = new RobotManager(this);
      radar = new RadarMovement(this);
      gun = new GunMovement(this);
      targeting = new CircularTargeting(this, false, true);

      setColors(Colors.GRAY, Colors.RED, Colors.RED);
      setAdjustGunForRobotTurn(true);
      setAdjustRadarForGunTurn(true);

      setTurnRadarRight(Double.POSITIVE_INFINITY);
      while (true) {
         RobotData enemy = robots.getEnemy(RobotChooser.CLOSEST);
         if (getGunHeat() < .4 || getOthers() == 1)
            radar.setSweep(enemy, Utils.EIGHTIETH_CIRCLE / 3);
         gun.setTurnTo(targeting.getAngle(enemy, 2.0D));
         if (!enemy.isDead())
            setFire(2.0D);
         execute();
      }
   }

   @Override
   public void onPaint(final Graphics2D graphics) {
      RGraphics grid = new RGraphics(graphics, this);
      DrawMenu.draw(grid);
      robots.draw(grid);
   }

   @Override
   public void onScannedRobot(final ScannedRobotEvent e) {
      robots.inEvent(e);
   }

   @Override
   public void onRobotDeath(final RobotDeathEvent e) {
      robots.inEvent(e);
   }

   @Override
   public void onDeath(final DeathEvent e) {
      robots.inEvent(e);
   }

   @Override
   public void onMouseClicked(final MouseEvent e) {
      DrawMenu.inMouseEvent(e);
   }

   @Override
   public void onSkippedTurn(final SkippedTurnEvent e) {
      out.println("SKIPPED TURN! " + e.getTime());
   }

}