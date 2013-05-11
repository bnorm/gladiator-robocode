package kid.drones;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import kid.graphics.Colors;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.management.RobotManager;
import kid.movement.robot.Movement;
import kid.movement.robot.WallMovement;
import robocode.AdvancedRobot;
import robocode.DeathEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;

public abstract class MovementDrone extends AdvancedRobot {

   private RobotManager robots;
   private Movement movement;

   public abstract Movement getMovement();

   @Override
   public void run() {

      robots = new RobotManager(this);
      movement = new WallMovement(this);

      setColors(Colors.GRAY, Colors.GREEN, Colors.GREEN);
      setAdjustGunForRobotTurn(true);
      setAdjustRadarForGunTurn(true);

      setTurnRadarRight(Double.POSITIVE_INFINITY);
      while (true) {
         movement.move(robots.getRobots());
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