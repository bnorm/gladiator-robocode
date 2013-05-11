package kid.competition;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import kid.cluster.Comparison;
import kid.cluster.dimensions.BulletFirePower;
import kid.cluster.dimensions.DeltaVelocity;
import kid.cluster.dimensions.Distance;
import kid.cluster.dimensions.LateralVelocity;
import kid.cluster.dimensions.WallDanger;
import kid.data.RobotChooser;
import kid.graphics.Colors;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.management.RobotManager;
import kid.movement.MovementProfiler;
import kid.movement.radar.RadarMovement;
import kid.movement.robot.Perpendicular;
import kid.robot.EnemyData;
import kid.utils.Utils;
import robocode.AdvancedRobot;
import robocode.BulletHitBulletEvent;
import robocode.DeathEvent;
import robocode.HitByBulletEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;
import robocode.WinEvent;

public class Toa_MC extends AdvancedRobot {

   private Perpendicular oneonone;
   private RadarMovement radar;

   private RobotManager robots;

   private MovementProfiler profile;

   @Override
   public void run() {

      setColors(Colors.DARK_RED, Colors.SILVER, Colors.DIRT_GREEN);
      setAdjustGunForRobotTurn(true);
      setAdjustRadarForGunTurn(true);

      Rectangle2D field = new Rectangle2D.Double(0.0, 0.0, getBattleFieldWidth(), getBattleFieldHeight());
      profile = new MovementProfiler(this, new Comparison[] {
            new LateralVelocity(),
            // new Velocity(),
            new Distance(), new DeltaVelocity(),
            // new DeltaHeading(),
            new BulletFirePower(),
            // new WallDistance(field),
            new WallDanger(field)
      }, 25);

      robots = new RobotManager(this);

      oneonone = new Perpendicular(this);
      radar = new RadarMovement(this);

      while (true) {
         EnemyData enemy = robots.getEnemy(RobotChooser.CLOSEST);

         oneonone.move(enemy, profile);

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
      profile.draw(grid);
   }

   @Override
   public void onScannedRobot(final ScannedRobotEvent e) {
      robots.inEvent(e);
      oneonone.inEvent(e);
      profile.inEvent(e);
   }

   @Override
   public void onRobotDeath(final RobotDeathEvent e) {
      robots.inEvent(e);
      oneonone.inEvent(e);
   }

   @Override
   public void onHitByBullet(final HitByBulletEvent e) {
      profile.inEvent(e);
   }

   @Override
   public void onBulletHitBullet(final BulletHitBulletEvent e) {
      profile.inEvent(e);
   }

   @Override
   public void onWin(final WinEvent e) {
      robots.inEvent(e);
      profile.inEvent(e);
   }

   @Override
   public void onDeath(final DeathEvent e) {
      robots.inEvent(e);
      profile.inEvent(e);
   }

   @Override
   public void onMouseClicked(final MouseEvent e) {
      DrawMenu.inMouseEvent(e);
   }


   private static long SKIPPED_TURNS = 0;

   @Override
   public void onSkippedTurn(final SkippedTurnEvent e) {
      out.println("SKIPPED TURN! (Time: " + e.getTime() + ", Total: " + ++SKIPPED_TURNS + ")");
   }

}
