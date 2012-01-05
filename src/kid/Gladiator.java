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

public class Gladiator extends AdvancedRobot {

    private DataManager Data = new DataManager(false);
    private MovementProfile MovementProfile = new MovementProfile();

    private MovementManager Movement;

    private StatisticsManager Statistics;
    
    private GunMovement Gun;
    private RadarMovement Radar;

    private final boolean DO_TARGETING = true;
    private final boolean DO_MOVEMENT = true;
    private final boolean DO_DRAW = true;
    private final boolean DO_DRAW_TARGETING = true;
    private final boolean DO_DRAW_MOVEMENT = true;

    final static double maxFireDistance = 200.0;

    public void run() {

        Data.UpDateRobot(this);
        if (DO_TARGETING) {
            kid.Segmentation.Segmentars.Segmentar[] TargetingSegmentarArray = {
                    new DistSegmentar(this), 
                    new WallSegmentar(this, 10, 0), 
                    new LateralVelocitySegmentar(),
                    new AdvancingVelocitySegmentar()
            };
            Data.AddGuessFactor(TargetingSegmentarArray);
            Data.AddPatternMatching();
            kid.Targeting.Targeting[] TargetingArray = {
                    new Circular(this), new Circular_wAvgHeading(this), new Circular_wAvgVelocity(this),
                    new Circular_wAvgHeading_wAvgVelocity(this), new HeadOn(this), new Linear(this),
                    new Linear_wAvgVelocity(this), new Random(this), new GuessFactor(this),
                    new PatternMatchingPolar(this, 100)
            };
            Data.AddVirtualGuns(TargetingArray);
        }
        if (DO_MOVEMENT) {
            kid.Segmentation.Segmentars.Segmentar[] MovementSegmentarArray = {
                    new DistSegmentar(this), 
                    new WallSegmentar(this, 10, 0), 
                    new LateralVelocitySegmentar(),
                    new AdvancingVelocitySegmentar()
            };
            MovementProfile.startDataProsseser(this, Data, MovementSegmentarArray);
            Movement = new MovementManager(this, new TrueSurf(this, MovementProfile), new MinimumRiskPoint(this));
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
                Gun.setTo(Data.getColsestEnemy().getTopVirtualGun(), firepower);
                if (!Data.getColsestEnemy().isDead()) {
                    if (getGunHeat() == 0.0 && getEnergy() > 0) {
                        Data.getColsestEnemy().updateVirtualGuns(firepower);
                        Data.getColsestEnemy().updateGuessFatorGun(firepower);
                    }
                    setFire(firepower);
                }
            }
            Radar.Melee_TickScan_GunHeat(Data.getColsestEnemy(), 45 / 3, 3, .6);
            execute();
        }
    }

    private double getFirePower(EnemyData Enemy) {
        double firepower = (maxFireDistance * 3) / Enemy.DistTo(getX(), getY());
        // try {
        // if (Enemy.getTopVirtualGun().getHitRate() != 0.0 && firePower < 2.5)
        // firePower *= Math.pow(Enemy.getTopVirtualGun().getHitRate(), .09);
        // } catch (Exception e) {}
        if (getEnergy() < 20) {
            if (Enemy.getEnergy() > getEnergy()) {
                firepower /= 3;
            }
            firepower /= 2;
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
                Data.drawTargetingNamesAndGraph(Graphics);
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
        //Statistics.outStatistics();
    }

    public void onDeath(DeathEvent e) {
        Data.inEvent(e);
        Movement.inEvent(e);
        Statistics.inEvent(e);
        //Statistics.outStatistics();
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