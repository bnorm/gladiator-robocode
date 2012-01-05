package kid;

import kid.Data.*;
import kid.Data.Robot.*;
import kid.Managers.*;
import kid.Movement.*;
import kid.Movement.Melee.*;
import kid.Movement.OneOnOne.*;
import kid.Targeting.Fast.*;
import kid.Targeting.Statistical.*;
import robocode.*;
import kid.Targeting.Log.PatternMatchingPolar;

public class Gladiator extends AdvancedRobot {

    DataManager Data = new DataManager(false);
    Flatner Flatner = new Flatner();

    MovementManager Movement;

    StatisticsManager Statistics;

    RobotMovement Robot;
    GunMovement Gun;
    RadarMovement Radar;

    boolean DO_TARGETING = true;
    boolean DO_MOVEMENT = true;
    boolean DO_DRAW = false;
    boolean DO_DANCE = false;

    final static double maxFireDistance = 200.0;

    public void run() {

        Data.UpDateRobot(this);
        if (DO_TARGETING) {
            Data.AddGuessFactor();
            Data.AddPatternMatching();
            kid.Targeting.Targeting[] TargetingArray = {new Circular(this), new Circular_wAvgHeading(this),
                    new Circular_wAvgVelocity(this), new Circular_wAvgHeading_wAvgVelocity(this), new HeadOn(this),
                    new Linear(this), new Linear_wAvgVelocity(this), new Random(this),

                    new GuessFactor(this),

                    new PatternMatchingPolar(this, 100)
            };
            Data.AddVirtualGuns(TargetingArray);
        }

        if (DO_MOVEMENT) {
            Flatner.startDataProsseser(this, Data);
            Movement = new MovementManager(this, new TrueSurf(this, Flatner), new AntiGravity_wMinimumRiskPoint(this));
        }

        Statistics = new StatisticsManager(this);

        Robot = new RobotMovement(this);
        Gun = new GunMovement(this);
        Radar = new RadarMovement(this);

        setColors(Colors.BROWN, Colors.LIGHT_GRAY, Colors.LIGHT_BLUE);
        setAdjustRadarForGunTurn(true);
        setAdjustGunForRobotTurn(true);

        while (true) {
            if (DO_MOVEMENT) {
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
            Radar.Melee_TickScan_GunHeat(Data.getColsestEnemy(), 45 / 3, 9, .6);
            execute();
        }
    }

    private double getFirePower(EnemyData Enemy) {
        double firePower = Utils.round(Math.min((maxFireDistance * 3) / Enemy.DistToXY(getX(), getY()), 3), .1);
        try {
            if (Enemy.getTopVirtualGun().getHitRate() != 0.0)
                firePower *= Math.pow(Enemy.getTopVirtualGun().getHitRate(), .09);
        } catch (Exception e) {}
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
            RobocodeGraphicsDrawer Graphics = new RobocodeGraphicsDrawer(this, g);
            if (DO_TARGETING) {
                Data.drawTargetingNamesAndGraph(Graphics);
            }
            if (DO_MOVEMENT) {
                Movement.drawMovement(Graphics, Data);
                Flatner.drawBulletWaves(Graphics);
            }
        } catch (Exception e) {}
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
        if (DO_DANCE) {
            VictoryMovement v = new VictoryMovement(this);
            v.doOrbit_wFire();
        }
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
