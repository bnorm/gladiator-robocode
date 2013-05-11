package kid;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import kid.cluster.Comparison;
import kid.cluster.dimensions.BulletFirePower;
import kid.cluster.dimensions.DeltaHeading;
import kid.cluster.dimensions.DeltaVelocity;
import kid.cluster.dimensions.Distance;
import kid.cluster.dimensions.LateralVelocity;
import kid.cluster.dimensions.Velocity;
import kid.cluster.dimensions.WallDanger;
import kid.cluster.dimensions.WallDistance;
import kid.data.RobotChooser;
import kid.data.pattern.PatternMatcher;
import kid.data.pattern.Polar;
import kid.data.pattern.PolarFactory;
import kid.graphics.Colors;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.management.ProfileManager;
import kid.management.RobotManager;
import kid.management.TargetingManager;
import kid.movement.MovementProfiler;
import kid.movement.gun.GunMovement;
import kid.movement.radar.RadarMovement;
import kid.movement.robot.MinimumRiskPoint;
import kid.movement.robot.Movement;
import kid.movement.robot.Perpendicular;
import kid.robot.EnemyData;
import kid.targeting.CircularTargeting;
import kid.targeting.GuessFactorTargeting;
import kid.targeting.HeadOnTargeting;
import kid.targeting.LinearTargeting;
import kid.targeting.PatternMatchingTargeting;
import kid.targeting.Targeting;
import kid.utils.Utils;
import kid.virtual.VirtualGun;
import robocode.AdvancedRobot;
import robocode.Bullet;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.DeathEvent;
import robocode.HitByBulletEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;
import robocode.WinEvent;

public class Toa extends AdvancedRobot {

   private Perpendicular oneonone;
   private Movement melee;
   private RadarMovement radar;
   private GunMovement gun;

   private RobotManager robots;
   private TargetingManager targeting;

   private MovementProfiler profile;
   private ProfileManager profiles;

   private static PatternMatcher<Polar> matcher = new PatternMatcher<Polar>();

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
      }, 7);

      profiles = new ProfileManager(this, new Comparison[] {
            new LateralVelocity(), new Velocity(), new Distance(), new DeltaVelocity(), new DeltaHeading(),
            new BulletFirePower(), new WallDistance(field), new WallDanger(field)
      });
      matcher.start(this, new PolarFactory());

      Targeting[] targetings = {
            new HeadOnTargeting(this), new LinearTargeting(this), new LinearTargeting(this, true),
            new CircularTargeting(this), new CircularTargeting(this, false, true),
            new CircularTargeting(this, true, false), new CircularTargeting(this, true, true),
            new PatternMatchingTargeting(this, matcher), new GuessFactorTargeting(this, profiles, 50)
      // ,new GuessFactorTargeting(this, profiles, 30)
      };

      robots = new RobotManager(this);
      targeting = new TargetingManager(this, targetings);

      oneonone = new Perpendicular(this);
      melee = new MinimumRiskPoint(this);
      radar = new RadarMovement(this);
      gun = new GunMovement(this);

      while (true) {
         EnemyData enemy = robots.getEnemy(RobotChooser.CLOSEST);

         if (robots.getAliveEnemies().length == 1)
            oneonone.move(enemy, profile);
         else
            melee.move(robots.getRobots());

         setTurnRadarRight(Double.POSITIVE_INFINITY);
         if (getGunHeat() < .4 || getOthers() == 1)
            radar.setSweep(enemy, Utils.EIGHTIETH_CIRCLE / 3);

         double firepower = getFirePower(enemy);
         gun.setTurnTo(targeting.getBestGun(enemy).getTargeting(), enemy, firepower);
         if (shouldFire(enemy, firepower)) {
            Bullet b = targeting.fire(enemy, firepower);
            profiles.fire(enemy, b);
         }

         execute();
      }
   }

   private boolean shouldFire(EnemyData enemy, double firepower) {
      boolean fire = !enemy.isDead() && firepower < getEnergy() && Math.abs(getGunTurnRemaining()) < 2.0;
      if (getOthers() > 1)
         return fire;
      else
         return enemy.distSq(getX(), getY()) < 40000
               || (fire && (getEnergy() - firepower > enemy.getEnergy() || getEnergy() > 16.0));
   }

   private double getFirePower(EnemyData enemy) {
      double firepower = 3.0;
      if (getOthers() > 1) {
         firepower = (400.0 * 3.0) / enemy.dist(getX(), getY());
         // double[] walls = {
         // getBattleFieldHeight(), getBattleFieldWidth()
         // };
         // firepower *= i.distToWall(getGunHeading()) / Utils.avg(walls) * getOthers();
      } else if (enemy.distSq(getX(), getY()) > 40000) {
         firepower = (400.0 * 2.0) / enemy.dist(getX(), getY());
         VirtualGun gun = targeting.getBestGun(enemy);
         if (gun.getRealHitRate() >= 0.12)
            firepower *= gun.getHitRate() / 0.15;
         if (getEnergy() < 32.0)
            firepower *= Utils.limit(0.5, getEnergy() / enemy.getEnergy(), 2.0);
      }
      firepower = Math.min(firepower, enemy.getEnergy() / 4.0);
      return Utils.limit(0.1, firepower, 3.0);
   }

   @Override
   public void onPaint(final Graphics2D graphics) {
      RGraphics grid = new RGraphics(graphics, this);
      DrawMenu.draw(grid);
      robots.draw(grid);
      targeting.draw(grid);
      profiles.draw(grid);
      profile.draw(grid);
      matcher.draw(grid);
   }

   @Override
   public void onScannedRobot(final ScannedRobotEvent e) {
      robots.inEvent(e);
      targeting.inEvent(e);
      profiles.inEvent(e);
      oneonone.inEvent(e);
      profile.inEvent(e);
      matcher.inEvent(e);
   }

   @Override
   public void onRobotDeath(final RobotDeathEvent e) {
      robots.inEvent(e);
      targeting.inEvent(e);
      profiles.inEvent(e);
      oneonone.inEvent(e);
      matcher.inEvent(e);
   }

   @Override
   public void onHitByBullet(final HitByBulletEvent e) {
      profile.inEvent(e);
   }

   @Override
   public void onBulletHit(final BulletHitEvent e) {
   }

   @Override
   public void onBulletHitBullet(final BulletHitBulletEvent e) {
      profile.inEvent(e);
   }

   @Override
   public void onWin(final WinEvent e) {
      robots.inEvent(e);
      profile.inEvent(e);
      matcher.inEvent(e);
   }

   @Override
   public void onDeath(final DeathEvent e) {
      robots.inEvent(e);
      targeting.inEvent(e);
      profiles.inEvent(e);
      profile.inEvent(e);
      matcher.inEvent(e);
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
