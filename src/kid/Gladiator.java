package kid;

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

/**
 * TODO: WORK ON GUN!!
 */
public class Gladiator extends AdvancedRobot {

    private MyRobotsInfo i;

    private DataManager Data = new DataManager(false);
    private MovementProfile MovementProfile = new MovementProfile();

    private MovementManager Movement = new MovementManager();

    private StatisticsManager Statistics;

    private GunMovement Gun;
    private RadarMovement Radar;

    private boolean DO_TARGETING = true;
    private boolean DO_MOVEMENT = true;
    private boolean DO_DRAW = false;
    private boolean DO_DRAW_TARGETING = false;
    private boolean DO_DRAW_MOVEMENT = false;

    private final static double maxFireDistance = 200.0;

    public void run() {

        i = new MyRobotsInfo(this);

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

        Gun = new GunMovement(this);
        Radar = new RadarMovement(this);

        setColors(Colors.BROWN, Colors.LIGHT_GRAY, Colors.LIGHT_BLUE);
        setAdjustRadarForGunTurn(true);
        setAdjustGunForRobotTurn(true);

        while (true) {
            if (DO_MOVEMENT) {
                MovementProfile.inEvent(null);
                Movement.doMovement(Data);
            }
            if (DO_TARGETING) {
                double firepower = getFirePower(Data.getColsestEnemy());
                if (!Data.getColsestEnemy().isDead() && getGunTurnRemaining() == 0.0) {
                    if (getGunHeat() == 0.0 && getEnergy() > 0) {
                        Data.getColsestEnemy().updateVirtualGuns(firepower);
                        Data.getColsestEnemy().updateGuessFactor(firepower);
                    }
                    setFire(firepower);
                }
                Gun.setTo(Data.getColsestEnemy().getTopVirtualGun(), firepower);
            }
            Radar.Melee_TickScan_GunHeat(Data.getColsestEnemy(), 45 / 3, 3, .6);
            execute();
        }
    }

    private double getFirePower(EnemyData Enemy) {
        double firepower = (maxFireDistance * 3) / Enemy.DistTo(getX(), getY());
        if (getOthers() > 1) {
            firepower *= Math.max((getOthers() + 1) / 4, 1)
                    * (getOthers() > 5 ? i.DistToWall(i.getGunHeading()) / (i.getBattleFieldHeight() / i.getOthers())
                            : 1);
        } else {
            if (Data.getColsestEnemy().getTopVirtualGun() != null)
                firepower *= Data.getColsestEnemy().getTopVirtualGun().getHitRate() * 8;
            if (getEnergy() < 15)
                firepower *= Math.min(getEnergy() / Enemy.getEnergy(), 1);
        }
        firepower = Math.min(firepower, Enemy.getEnergy() / 4);
        return Math.max(Math.min(3, firepower), .1);
    }

    public void onPaint(java.awt.Graphics2D g) {
        if (!DO_DRAW)
            return;
        try {
            RobocodeGraphicsDrawer Graphics = new RobocodeGraphicsDrawer(this, g);
            if (DO_TARGETING && DO_DRAW_TARGETING) {
                Data.drawTargetingNames(Graphics);
                Data.drawVirtualGunGraph(Graphics);
                Data.drawVirtualBullets(Graphics);
            }
            if (DO_MOVEMENT && DO_DRAW_MOVEMENT) {
                Movement.drawMovement(Graphics, Data);
                MovementProfile.drawBulletWaves(Graphics);
            }
        } catch (Exception e) {
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        Data.inEvent(e);
        Movement.inEvent(e);
        Statistics.inEvent(e);
    }

    public void onRobotDeath(RobotDeathEvent e) {
        Data.inEvent(e);
        Movement.inEvent(e);
        Statistics.inEvent(e);
    }

    public void onWin(WinEvent e) {
        Data.inEvent(e);
        Movement.inEvent(e);
        Statistics.inEvent(e);
        //Statistics.printStatistics();
    }

    public void onDeath(DeathEvent e) {
        Data.inEvent(e);
        Movement.inEvent(e);
        Statistics.inEvent(e);
        //Statistics.printStatistics();
    }

    public void onHitRobot(HitRobotEvent e) {
        Data.inEvent(e);
        Movement.inEvent(e);
        Statistics.inEvent(e);
    }

    public void onBulletHit(BulletHitEvent e) {
        Data.inEvent(e);
        Movement.inEvent(e);
        Statistics.inEvent(e);
    }

    public void onBulletMissed(BulletMissedEvent e) {
        Data.inEvent(e);
        Movement.inEvent(e);
        Statistics.inEvent(e);
    }

    public void onBulletHitBullet(BulletHitBulletEvent e) {
        Data.inEvent(e);
        Movement.inEvent(e);
        Statistics.inEvent(e);
    }

    public void onHitByBullet(HitByBulletEvent e) {
        Data.inEvent(e);
        Movement.inEvent(e);
        MovementProfile.inEvent(e);
        Statistics.inEvent(e);
    }

    public void onHitWall(HitWallEvent e) {
        Data.inEvent(e);
        Movement.inEvent(e);
        Statistics.inEvent(e);
    }

    public void onSkippedTurn(SkippedTurnEvent e) {
        Data.inEvent(e);
        Movement.inEvent(e);
        Statistics.inEvent(e);
    }

}