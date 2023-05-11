package kid.team;

import java.io.*;

import kid.*;
import kid.data.RobotChooser;
import kid.data.factor.*;
import kid.data.pattern.RobotRecorder;
import kid.data.robot.*;
import kid.managers.*;
import kid.messages.*;
import kid.movement.MovementProfiler;
import kid.movement.radar.RadarMovement;
import kid.movement.robot.*;
import kid.segmentation.Segmenter;
import kid.segmentation.segmenters.*;
import kid.targeting.*;

import robocode.*;

public class Niner extends TeamRobot {

   private Movement movement;
   private RadarMovement radar;
   private MovementProfiler profile;

   private RobotManager robots;
   private TargetingManager targeting;
   private TeamManager team;

   // private RobotRecorder recorder;
   // private PatternMatchingTargeting patternMatching;

   private RobotGrapher grapher;
   private GuessFactorTargeting guessFactor;


   @SuppressWarnings("unchecked")
   public void run() {

      setColors(Colors.BLACK, Colors.BLACK, Colors.VISER_BLUE);
      setAdjustGunForRobotTurn(true);
      setAdjustRadarForGunTurn(true);

      Segmenter<GuessFactor, RobotData, RobotData>[] segmenters = new Segmenter[3];
      segmenters[0] = new DeltaVelocity<GuessFactor, RobotData, RobotData>(this);
      segmenters[1] = new LateralVelocity<GuessFactor, RobotData, RobotData>(this);
      segmenters[2] = new Distance<GuessFactor, RobotData, RobotData>(this);

      // recorder = new RobotRecorder(this);
      grapher = new RobotGrapher(this, segmenters);

      Targeting[] targetings = { new HeadOnTargeting(this),
                                new LinearTargeting(this),
                                new LinearTargeting(this, true),
                                new CircularTargeting(this),
                                new CircularTargeting(this, false, true),
                                new CircularTargeting(this, true, false),
                                new CircularTargeting(this, true, true),
                                // patternMatching = new PatternMatchingTargeting(this, recorder),
                                guessFactor = new GuessFactorTargeting(this, grapher) };

      robots = new RobotManager(this);
      targeting = new TargetingManager(this, targetings);
      team = new TeamManager(this);

      movement = new MinimumRiskPoint(this);

      radar = new RadarMovement(this);

      profile = new MovementProfiler(this, segmenters);

      while (true) {
         EnemyData enemy = robots.getEnemy(RobotChooser.EASIEST);
         double firepower = getFirePower(enemy);

         // movement
         movement.move(robots.getRobots(), team.getTeammateBullets());

         // radar
         setTurnRadarRight(Double.POSITIVE_INFINITY);
         if (getGunHeat() < .4 || robots.numAliveEnemies() == 1)
            radar.setSweep(enemy, Utils.EIGHTIETH_CIRCLE / 3);

         // gun
         if (!enemy.isDead() && targeting.getBestGun(enemy) != null)
            grapher.fire(targeting.fire(enemy, firepower), enemy);

         targeting.broadcastBullets();

         broadcast(new RobotMessage(this));

         execute();
      }
   }

   public double getFirePower(EnemyData enemy) {
      if (getEnergy() <= 1.0D)
         return 0.0D;
      double firePower = 900.0D / Utils.dist(getX(), getY(), enemy.getX(), enemy.getY());
      double hitRate = targeting.getHitRate(targeting.getBestGun(enemy));
      if (hitRate > 0.0D) {
         firePower *= 0.85D + hitRate;
      }
      if (getEnergy() < 20.0D) {
         if (enemy.getEnergy() > getEnergy())
            firePower /= 3.0D;
         else
            firePower /= 2.0D;
      }
      firePower = Math.min(firePower, enemy.getEnergy() / 4.0D);
      return Utils.limit(Rules.MIN_BULLET_POWER, firePower, Rules.MAX_BULLET_POWER);
   }

   public void onPaint(java.awt.Graphics2D graphics) {
      RobocodeGraphicsDrawer grid = new RobocodeGraphicsDrawer(graphics, this);
      robots.draw(grid, "");
      // targeting.draw(grid, "");

      // "-waves", "-graph", "-3Dgraphs"
      // profile.draw(grid, "");

      // recorder.draw(grid, "");
      // patternMatching.draw(grid, robots.getEnemy(RobotChooser.CLOSEST), 2.0D);

      // "-waves", "-virtual", "-real", "-hits"
      // grapher.draw(grid, "");
      // guessFactor.draw(grid, robots.getEnemy(RobotChooser.CLOSEST), 2.0D);

      team.draw(grid, "");
   }

   public void onMessageReceived(MessageEvent e) {
      team.inEvent(e);
      robots.inEvent(e);
   }

   public void onScannedRobot(ScannedRobotEvent e) {
      broadcast(new ScannedRobotMessage(e, this));
      robots.inEvent(e);
      targeting.inEvent(e);
      team.inEvent(e);
      movement.inEvent(e);
      profile.inEvent(e);
      // recorder.inEvent(e);
      grapher.inEvent(e);
   }

   public void onRobotDeath(RobotDeathEvent e) {
      robots.inEvent(e);
      targeting.inEvent(e);
      movement.inEvent(e);
      profile.inEvent(e);
      // recorder.inEvent(e);
      grapher.inEvent(e);
   }

   public void onHitByBullet(HitByBulletEvent e) {
      profile.inEvent(e);
   }

   public void onBulletHit(BulletHitEvent e) {
      grapher.inEvent(e);
   }

   public void onBulletHitBullet(BulletHitBulletEvent e) {
      profile.inEvent(e);
      grapher.inEvent(e);
   }

   public void onWin(WinEvent e) {
      robots.inEvent(e);
      targeting.inEvent(e);
      profile.inEvent(e);
      // recorder.inEvent(e);
      grapher.inEvent(e);
   }

   public void onDeath(DeathEvent e) {
      robots.inEvent(e);
      targeting.inEvent(e);
      profile.inEvent(e);
      // recorder.inEvent(e);
      grapher.inEvent(e);
   }

   public void onSkippedTurn(SkippedTurnEvent e) {
      out.println("SKIPPED TURN! " + e.getTime());
   }

   public void broadcast(Serializable message) {
      try {
         this.broadcastMessage(message);
      } catch (IOException e) {
      }
   }

}