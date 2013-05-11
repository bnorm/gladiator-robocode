package kid.competition;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import kid.data.RobotChooser;
import kid.data.pattern.PatternMatcher;
import kid.data.pattern.Polar;
import kid.data.pattern.PolarFactory;
import kid.graphics.Colors;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.management.RobotManager;
import kid.movement.gun.GunMovement;
import kid.movement.radar.RadarMovement;
import kid.robot.EnemyData;
import kid.targeting.PatternMatchingTargeting;
import kid.targeting.Targeting;
import kid.utils.Utils;
import robocode.AdvancedRobot;
import robocode.DeathEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;
import robocode.WinEvent;

public class Toa_PMC extends AdvancedRobot {

   private static long SKIPPED_TURNS = 0;

   private RobotManager robots;
   private Targeting targeting;
   private static PatternMatcher<Polar> matcher = new PatternMatcher<Polar>();

   private RadarMovement radar;
   private GunMovement gun;

   @Override
   public void run() {

      setColors(Colors.DARK_RED, Colors.SILVER, Colors.DIRT_GREEN);
      setAdjustGunForRobotTurn(true);
      setAdjustRadarForGunTurn(true);

      matcher.start(this, new PolarFactory());

      robots = new RobotManager(this);
      targeting = new PatternMatchingTargeting(this, matcher);

      radar = new RadarMovement(this);
      gun = new GunMovement(this);

      while (true) {
         EnemyData enemy = robots.getEnemy(RobotChooser.CLOSEST);

         setTurnRadarRight(Double.POSITIVE_INFINITY);
         if (getGunHeat() < .4 || getOthers() == 1)
            radar.setSweep(enemy, Utils.EIGHTIETH_CIRCLE / 3);

         if (!enemy.isDead()) {
            gun.setTurnTo(targeting, enemy, 0.5D);
            setFire(0.5D);
         }

         execute();
      }

   }

   @Override
   public void onPaint(final Graphics2D graphics) {
      RGraphics grid = new RGraphics(graphics, this);
      DrawMenu.draw(grid);
      robots.draw(grid);
      matcher.draw(grid);
   }

   @Override
   public void onScannedRobot(final ScannedRobotEvent e) {
      robots.inEvent(e);
      matcher.inEvent(e);
   }

   @Override
   public void onRobotDeath(final RobotDeathEvent e) {
      robots.inEvent(e);
      matcher.inEvent(e);
   }

   @Override
   public void onWin(final WinEvent e) {
      robots.inEvent(e);
      matcher.inEvent(e);
   }

   @Override
   public void onDeath(final DeathEvent e) {
      robots.inEvent(e);
      matcher.inEvent(e);
   }

   @Override
   public void onMouseClicked(final MouseEvent e) {
      DrawMenu.inMouseEvent(e);
   }

   @Override
   public void onSkippedTurn(final SkippedTurnEvent e) {
      out.println("SKIPPED TURN! (Time: " + e.getTime() + ", Total: " + ++SKIPPED_TURNS + ")");
   }

}
