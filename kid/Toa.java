package kid;

import kid.data.RobotChooser;
import kid.data.factor.*;
import kid.data.robot.RobotData;
import kid.managers.*;
import kid.movement.MovementProfiler;
import kid.movement.radar.RadarMovement;
import kid.movement.robot.*;
import kid.segmentation.Segmenter;
import kid.segmentation.segmenters.*;
import kid.targeting.*;
import robocode.*;

public class Toa extends AdvancedRobot {

   private Movement movement;
   private RadarMovement radar;
   private MovementProfiler profile;

   private RobotManager robots;
   private TargetingManager targeting;

   // private RobotRecorder recorder;
   // private PatternMatchingTargeting patternMatching;

   private RobotGrapher grapher;


   @SuppressWarnings("unchecked")
   public void run() {

      setColors(Colors.DARK_RED, Colors.SILVER, Colors.DIRT_GREEN);
      setAdjustGunForRobotTurn(true);
      setAdjustRadarForGunTurn(true);

      Targeting[] targetings = { new HeadOnTargeting(this),
                                new LinearTargeting(this),
                                new LinearTargeting(this, true),
                                new CircularTargeting(this),
                                new CircularTargeting(this, false, true),
                                new CircularTargeting(this, true, false),
                                new CircularTargeting(this, true, true) };

      Segmenter<GuessFactor, RobotData, RobotData>[] segmenters = new Segmenter[3];
      segmenters[0] = new DeltaVelocity<GuessFactor, RobotData, RobotData>(this);
      segmenters[1] = new LateralVelocity<GuessFactor, RobotData, RobotData>(this);
      segmenters[2] = new Distance<GuessFactor, RobotData, RobotData>(this);

      robots = new RobotManager(this);
      targeting = new TargetingManager(this, targetings);
      movement = new Perpendicular(this);
      radar = new RadarMovement(this);
      profile = new MovementProfiler(this, segmenters);
      // recorder = new RobotRecorder(this);
      grapher = new RobotGrapher(this, segmenters);

      // patternMatching = new PatternMatchingTargeting(this, recorder);

      while (true) {
         RobotData enemy = robots.getEnemy(RobotChooser.CLOSEST);

         // movement
         movement.move(robots.getRobots());

         // radar
         setTurnRadarRight(Double.POSITIVE_INFINITY);
         if (getGunHeat() < .4 || getOthers() == 1)
            radar.setSweep(enemy, Utils.EIGHTIETH_CIRCLE / 3);

         // gun
         if (!enemy.isDead() && targeting.getBestGun(enemy) != null)
            grapher.fire(targeting.fire(enemy, targeting.getBestGun(enemy).getTargeting(), 2), enemy);

         execute();
      }
   }

   public void onScannedRobot(ScannedRobotEvent e) {
      robots.inEvent(e);
      targeting.inEvent(e);
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

   public void onPaint(java.awt.Graphics2D graphics) {
      RobocodeGraphicsDrawer grid = new RobocodeGraphicsDrawer(graphics, this);
      robots.draw(grid, "");
      targeting.draw(grid, "");

      // "-waves", "-graph", "-3Dgraph"
      profile.draw(grid, "");

      // recorder.draw(grid, "");
      // patternMatching.draw(grid, robots.getEnemy(RobotChooser.CLOSEST), 2.0D);

      // "-waves", "-virtual", "-real"
      grapher.draw(grid, "");
   }

}
