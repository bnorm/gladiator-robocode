package kid.test;

import kid.*;
import kid.Data.*;
import kid.Managers.*;
import kid.Movement.*;
import kid.Movement.Melee.*;
import kid.Movement.OneOnOne.*;
import kid.Segmentation.Segmentars.*;
import kid.Targeting.Targeting;
import kid.Targeting.Fast.HeadOn;
import robocode.*;

public class NightFox extends AdvancedRobot {

    private DataManager Data = new DataManager(false);
    private MovementProfile MovementProfile = new MovementProfile();

    private MovementManager Movement = new MovementManager();
    private Targeting HeadOn;
    private GunMovement Gun;

    private StatisticsManager Statistics;

    private RadarMovement Radar;

    public void run() {

        Data.UpDateRobot(this);
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
        HeadOn = new HeadOn(this);


        Statistics = new StatisticsManager(this);

        Gun = new GunMovement(this);
        Radar = new RadarMovement(this);

        setColors(Colors.BLACK, Colors.BROWN, Colors.DARK_RED);
        setAdjustRadarForGunTurn(true);
        setAdjustGunForRobotTurn(true);

        while (true) {
            MovementProfile.inEvent(null);
            Movement.doMovement(Data);
            if (getGunTurnRemaining() == 0.0 && getEnergy() > 1) {
                double firepower = Math.min(Math.min(getEnergy() / 6, 1300 / Data.getColsestEnemy().DistTo(getX(), getY())), Data
                        .getColsestEnemy().getEnergy() / 3);
                setFire(firepower);
            }
            Gun.setTo(HeadOn, Data.getColsestEnemy(), 0);
            Radar.Melee_TickScan_GunHeat(Data.getColsestEnemy(), 45 / 3, 3, .6);
            execute();
        }
    }

    public void onPaint(java.awt.Graphics2D g) {
        try {
            RobocodeGraphicsDrawer Graphics = new RobocodeGraphicsDrawer(this, g);
            Movement.drawMovement(Graphics, Data);
            MovementProfile.drawBulletWaves(Graphics);
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
        Statistics.printFinishings();
    }

    public void onDeath(DeathEvent e) {
        Data.inEvent(e);
        Movement.inEvent(e);
        Statistics.inEvent(e);
        Statistics.printFinishings();
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