package kid.drones;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import kid.data.RobotChooser;
import kid.graphics.Colors;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.management.RobotManager;
import kid.movement.radar.RadarMovement;
import kid.movement.robot.Movement;
import kid.movement.robot.Perpendicular;
import kid.robot.RobotData;
import kid.utils.Utils;
import robocode.AdvancedRobot;
import robocode.DeathEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;

public class Drone_AntiGravity extends AdvancedRobot {

   private RobotManager robots;
   private Movement movement;
   private RadarMovement radar;

   @Override
   public void run() {

      robots = new RobotManager(this);
      movement = new Perpendicular(this);
      radar = new RadarMovement(this);

      setColors(Colors.GRAY, Colors.GREEN, Colors.GREEN);
      setAdjustGunForRobotTurn(true);
      setAdjustRadarForGunTurn(true);

      while (true) {
         RobotData enemy = robots.getEnemy(RobotChooser.CLOSEST);
         movement.move(robots.getRobots());
         setTurnRadarRight(Double.POSITIVE_INFINITY);
         if (getGunHeat() < .4 || getOthers() == 1)
            radar.setSweep(enemy, Utils.EIGHTIETH_CIRCLE / 3);
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
      movement.inEvent(e);
   }

   @Override
   public void onRobotDeath(final RobotDeathEvent e) {
      robots.inEvent(e);
      movement.inEvent(e);
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