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

//sniper, killer, hunter
/*
 Maybe use a simaler angle melee targeting algarithum.
 defintly want to use VG, with PM, and GF
 be more agresive
 
 */
/**
 * <p>
 * Title: DeltaOhSeven, RC-1207, AKA 'Sev'
 * </p>
 * 
 * <p>
 * Description: Some soldiers fight because they have to; others fight because
 * they like it. Oh-Seven is definitely one of the latter. Most of the time he's
 * a coldly efficient killer with a grim sense of humor. But when things get
 * hairy, he gets scary. Other members of the squad suspect that someone spiked
 * his cloning vat.
 * </p>
 * 
 * @author Brian Norman
 * @version .1
 */
public class Sev extends TeamRobot {

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

        setColors(Colors.DARK_RED, Colors.WHITE, Colors.TURQUOISE);
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
                    if (getGunHeat() == 0.0) {
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
        double firePower = Utils.round(Math.min((maxFireDistance * 3) / Enemy.DistTo(getX(), getY()), 3), .1);
        try {
            if (Enemy.getTopVirtualGun().getHitRate() != 0.0)
                firePower *= Math.pow(Enemy.getTopVirtualGun().getHitRate(), .09);
        } catch (Exception e) {
        }
        if (getEnergy() > 20) {
        } else {
            if (Enemy.getEnergy() > getEnergy()) {
                firePower /= 3;
            }
            firePower /= 2;
        }
        firePower = Math.min(firePower, Enemy.getEnergy() / 4);
        return Math.max(Math.min(3, firePower), .1);
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