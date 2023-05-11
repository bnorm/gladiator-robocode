package kid.DeltaSquad;

import kid.*;
import kid.Data.*;
import kid.Data.Robot.*;
import kid.Managers.*;
import kid.Movement.*;
import kid.Movement.Melee.*;
import kid.Movement.OneOnOne.*;
import kid.Segmentation.Segmentars.*;
import kid.Targeting.Fast.*;
import kid.Targeting.Log.*;
import kid.Targeting.Statistical.*;
import robocode.*;

//exploseves, heavy blaster, wild
/*
 maybe the front line kind of guy
 fire higher power shots
 get up in the face of weak bots

 */
/**
 * <p>
 * Title: DeltaSixTwo, RC-1262, AKA 'Scorch'
 * </p>
 * 
 * <p>
 * Description: War does funny things to people. Similarly, some people do funny
 * things with war. Six-Two is the Delta's resident wiseacre, regularly dropping
 * a world-weary bon mot into the stew of violence and destruction that serves
 * as the Deltas' steady diet. A competent soldier, and an excellent explosives
 * technician, Six-Two has an overdeveloped sense of irony that could be
 * mistaken for fatalism. 'Scorch' earned his nickname after an ordnance
 * accident that left him and Sergeant Walon Vau without eyebrows for a short
 * time.
 * </p>
 * 
 * @author Brian Norman
 * @version .1
 */
public class Scorch extends TeamRobot {

    DataManager Data = new DataManager(false);
    MovementProfile MovementProfile = new MovementProfile();

    MovementManager Movement;

    StatisticsManager Statistics;

    RobotMovement Robot;
    GunMovement Gun;
    RadarMovement Radar;

    boolean DO_TARGETING = true;
    boolean DO_MOVEMENT = true;
    boolean DO_DRAW = false;
    boolean DO_ROBOCODE_2K6 = false;
    boolean DO_DRAW_TARGETING = false;
    boolean DO_DRAW_MOVEMENT = false;

    final static double maxFireDistance = 200.0;

    public void run() {

        Data.UpDateRobot(this);
        if (DO_TARGETING) {
            kid.Segmentation.Segmentars.Segmentar[] TargetingSegmentarArray = {
                    new MeleeSegmentar(this), 
                    new DistSegmentar(this), 
                    new LateralVelocitySegmentar(),
                    new DistToWallSegmentar(this), 
                    new VelocityChangeTimeSegmentar()
            };
            Data.AddGuessFactor(TargetingSegmentarArray);
            Data.AddPatternMatching();
            kid.Targeting.Targeting[] TargetingArray = {
                    new Circular(this), 
                    new Circular_wAvgHeading(this), 
                    new Circular_wAvgVelocity(this),
                    new Circular_wAvgHeading_wAvgVelocity(this), 
                    new HeadOn(this), 
                    new Linear(this),
                    new Linear_wAvgVelocity(this), 
                    new Random(this),
                    new GuessFactor(this), 
                    new TidalWave(this), 
                    new PatternMatchingPolar(this),
                    new PatternMatchingLatVel(this)
            };
            Data.AddVirtualGuns(TargetingArray);
        }
        if (DO_MOVEMENT) {
            kid.Segmentation.Segmentars.Segmentar[] MovementSegmentarArray = {
                    new DistSegmentar(this),
                    new LateralVelocitySegmentar(),
                    new DistToWallSegmentar(this),
                    new DeltaVelocitySegmentar(),
                    new VelocityChangeTimeSegmentar()
            };
            MovementProfile.startDataProsseser(this, Data, MovementSegmentarArray);
            Movement = new MovementManager(this);
            Movement.setOneOnOneMovement(new TrueSurf(this, MovementProfile));
            Movement.setMeleeMovement(new MinimumRiskPoint(this));
        }

        Statistics = new StatisticsManager(this);

        Robot = new RobotMovement(this);
        Gun = new GunMovement(this);
        Radar = new RadarMovement(this);

        setColors(Colors.DIRT_YELLOW, Colors.WHITE, Colors.TURQUOISE);
        setAdjustRadarForGunTurn(true);
        setAdjustGunForRobotTurn(true);

        while (true) {
            if (DO_MOVEMENT) {
                Movement.doMovement(Data);
                MovementProfile.inEvent(null);
            }
            if (DO_TARGETING) {
                double firepower = getFirePower(Data.getColsestEnemy());
                Gun.setTo(Data.getColsestEnemy().getTopVirtualGun(), firepower);
                if (!Data.getColsestEnemy().isDead()) {
                    if (getEnergy() == 0.0 && getGunHeat() == 0.0) {
                        Data.getColsestEnemy().updateVirtualGuns(firepower);
                        Data.getColsestEnemy().updateGuessFactor(firepower);
                    }
                    Data.firedBullet(setFireBullet(firepower));
                }
            }
            Data.sendInfo();
            Radar.Melee_TickScan_GunHeat(Data.getColsestEnemy(), 45 / 3, 9, .6);
            execute();
        }
    }
    private double getFirePower(EnemyData Enemy) {
        double firepower = (maxFireDistance * 3) / Enemy.DistTo(getX(), getY());
        if (getOthers() > 1)
            firepower *= Math.max(100 / (getTime() + 1), 1);
        firepower *= Math.max((getOthers() + 1) / 4, 1);
        // firepower *= (getEnergy() / Enemy.getEnergy());
        firepower = Math.min(firepower, Enemy.getEnergy() / 4);
        return Math.max(Math.min(3, firepower), .1);
    }

    public void onPaint(java.awt.Graphics2D g) {
        if (!DO_DRAW)
            return;
        try {
            RobocodeGraphicsDrawer Graphics = new RobocodeGraphicsDrawer(this, g, DO_ROBOCODE_2K6);
            if (DO_TARGETING && DO_DRAW_TARGETING) {
                Data.drawTargetingNamesAndGraph(Graphics);
                Data.getColsestEnemy().drawGuessFactorData(Graphics, 0);
            }
            if (DO_MOVEMENT && DO_DRAW_MOVEMENT) {
                Movement.drawMovement(Graphics, Data);
                MovementProfile.drawBulletWaves(Graphics);
                MovementProfile.drawSectors(Graphics, Data.getColsestEnemy());
            }
        } catch (Exception e) {
            e.printStackTrace(this.out);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        Data.inEvent(e);
        Statistics.inEvent(e);
        Movement.inEvent(e);
    }

    public void onRobotDeath(RobotDeathEvent e) {
        Data.inEvent(e);
        Statistics.inEvent(e);
        Movement.inEvent(e);
    }

    public void onWin(WinEvent e) {
        Data.inEvent(e);
        Statistics.inEvent(e);
        Statistics.printStatistics();
        Movement.inEvent(e);
    }

    public void onDeath(DeathEvent e) {
        Data.inEvent(e);
        Statistics.inEvent(e);
        Statistics.printStatistics();
        Movement.inEvent(e);
    }

    public void onHitRobot(HitRobotEvent e) {
        Data.inEvent(e);
        Statistics.inEvent(e);
        Movement.inEvent(e);
    }

    public void onBulletHit(BulletHitEvent e) {
        Data.inEvent(e);
        Statistics.inEvent(e);
        Movement.inEvent(e);
    }

    public void onBulletMissed(BulletMissedEvent e) {
        Data.inEvent(e);
        Statistics.inEvent(e);
        Movement.inEvent(e);
    }

    public void onBulletHitBullet(BulletHitBulletEvent e) {
        Data.inEvent(e);
        Statistics.inEvent(e);
        Movement.inEvent(e);
    }

    public void onHitByBullet(HitByBulletEvent e) {
        Data.inEvent(e);
        Statistics.inEvent(e);
        Movement.inEvent(e);
        MovementProfile.inEvent(e);
    }

    public void onHitWall(HitWallEvent e) {
        Data.inEvent(e);
        Statistics.inEvent(e);
        Movement.inEvent(e);
    }

    public void onSkippedTurn(SkippedTurnEvent e) {
        Data.inEvent(e);
        Statistics.inEvent(e);
        Movement.inEvent(e);
    }

    public void onMessageReceived(MessageEvent e) {
        Data.inEvent(e);
        Statistics.inEvent(e);
        Movement.inEvent(e);
    }
}