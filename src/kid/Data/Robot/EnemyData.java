package kid.Data.Robot;

import java.awt.Color;
import java.io.PrintStream;

import kid.*;
import kid.Clustering.Cluster;
import kid.Data.*;
import kid.Data.DinamicClustering.*;
import kid.Data.GuessFactor.GuessFactorData;
import kid.Data.PatternMatching.*;
import kid.Segmentation.Segmentars.*;
import kid.Data.Virtual.*;
import kid.Targeting.*;
import robocode.*;

public class EnemyData extends TeammateData implements java.io.Serializable {
    private static final long serialVersionUID = 9130243312527050052L;

    public static final int AVG_WEIGHT = 10;

    // ***** THIS ROBOTS INFO *****//
    private transient String SCANNER;
    private transient int arrayByte = 0;
    private transient double[] AVG_HEADING = new double[AVG_WEIGHT];
    private transient double[] AVG_VELOCITY = new double[AVG_WEIGHT];
    private transient double GUN_HEAT = 3.0;
    private transient boolean FIRE_BULLET = false;
    private transient AdvancedRobot MyRobot = null;

    // ***** STATISTICAL DATA *****//
    private GuessFactorClusteringData ClusteringGF;
    private GuessFactorData GuessFactor;

    // ***** VIRTUAL GUNS *****//
    private boolean VIRTUAL_GUNS = false;
    private VirtualGun[] VirtualGuns = null;

    // ***** PATTERN MACHING *****//
    private boolean PATTERN_MATCHING = false;
    private PatternMatchClusteringData ClusteringPM;
    private PolarPatternMatcher POLAR_PATTERN_MATCHER = null;
    private LatVelPatternMatcher LAT_VEL_PATTERN_MATCHER = null;

    public EnemyData() {
        super();
    }

    public EnemyData(String name, double currentX, double currentY, double currentEnergy,
                     double currentHeading, double currentVelocity, long currentTime) {
        super(name, currentX, currentY, currentEnergy, currentHeading, currentVelocity, currentTime);
    }


    public void updateItem(double currentX, double currentY, double currentEnergy,
                           double currentHeading, double currentVelocity, long currentTime) {
        if (currentTime < TIME) {
            updateItemFromFile(NAME, currentX, currentY, currentEnergy, currentHeading,
                               currentVelocity, currentTime);
            System.out.println("Reset Robot for new Round: " + NAME);
        } else if (currentTime != TIME) {
            super.updateItem(currentX, currentY, currentEnergy, currentHeading, currentVelocity,
                             currentTime);
            AVG_HEADING[(arrayByte == AVG_WEIGHT ? arrayByte = 0 : arrayByte)] = DELTA_HEADING;
            AVG_VELOCITY[(arrayByte == AVG_WEIGHT ? arrayByte = 0 : arrayByte)] = VELOCITY;
            arrayByte++;
            updatePatterns();
            GUN_HEAT -= 0.1 * DELTA_TIME;
            FIRE_BULLET = fireBullet();
            if (FIRE_BULLET) {
                GUN_HEAT = Utils.gunHeat(Math.abs(DELTA_ENERGY));
            }
        }
    }

    public void updateItem(EnemyData enemy) {
        updateItem(enemy.getX(), enemy.getY(), enemy.getEnergy(), enemy.getHeading(),
                   enemy.getVelocity(), enemy.getTime());
    }

    public void updateItemFromFile(String name, double x, double y, double energy, double heading,
                                   double velocity, long time) {
        super.updateItemFromFile(name, x, y, energy, heading, velocity, time);
        arrayByte = 0;
        if (PATTERN_MATCHING) {
            POLAR_PATTERN_MATCHER.addPattern(null, null);
            LAT_VEL_PATTERN_MATCHER.addPattern(null, null);
        }
        GUN_HEAT -= 0.1 * time;
    }

    public void addDinamicClusteringGF(AdvancedRobot MyRobot, Cluster[] Clusters) {
        if (ClusteringGF == null)
            ClusteringGF = new GuessFactorClusteringData(this, MyRobot);
        ClusteringGF.addClusters(Clusters);
    }

    public void addDinamicClusteringPM(AdvancedRobot MyRobot, Cluster[] Clusters) {
        if (ClusteringPM == null) {
            addPatternMatching(MyRobot);
            ClusteringPM = new PatternMatchClusteringData(this, MyRobot, POLAR_PATTERN_MATCHER);
        }
        ClusteringPM.addClusters(Clusters);
    }

    public void addGuessFactor(AdvancedRobot MyRobot, Segmentar[] Segmentars) {
        if (GuessFactor == null)
            GuessFactor = new GuessFactorData(this, MyRobot);
        GuessFactor.addSegmentars(Segmentars);
    }

    public void addGuessFactorMelee(AdvancedRobot MyRobot, Segmentar[] Segmentars) {
        if (GuessFactor == null)
            GuessFactor = new GuessFactorData(this, MyRobot);
        GuessFactor.addMeleeSegmentars(Segmentars);
    }

    public void addPatternMatching(AdvancedRobot MyRobot) {
        this.MyRobot = MyRobot;
        if (PATTERN_MATCHING)
            return;
        PATTERN_MATCHING = true;

        POLAR_PATTERN_MATCHER = new PolarPatternMatcher();
        LAT_VEL_PATTERN_MATCHER = new LatVelPatternMatcher();
    }

    public void addVirtualGuns(AdvancedRobot MyRobot, Targeting[] Targeting) {
        this.MyRobot = MyRobot;
        if (Targeting != null && VirtualGuns != null && Targeting.length == VirtualGuns.length
            && VIRTUAL_GUNS) {
            for (int g = 0; g < VirtualGuns.length; g++) {
                VirtualGuns[g].updateVirtualGun(MyRobot, this, Targeting[g]);
            }
            return;
        }
        VIRTUAL_GUNS = true;
        VirtualGuns = new VirtualGun[Targeting.length];
        for (int i = 0; i < Targeting.length; i++) {
            VirtualGuns[i] = new VirtualGun(MyRobot, this, Targeting[i]);
        }
    }


    public void updateGuessFactor(double firePower) {
        if (GuessFactor != null)
            GuessFactor.updateGuessFactor(firePower);
        if (ClusteringGF != null)
            ClusteringGF.updateClusters(firePower);
    }

    // public void updateDinamicClustering(double firePower) {
    // if ((DINAMIC_CLUSTERING) && !isDead())
    // WAVES.add(new DataWave(MyRobot, this, firePower, getCluster(firePower)));
    // }

    public void updateVirtualGuns(double firePower) {
        if (VIRTUAL_GUNS && !isDead())
            for (int g = 0; g < VirtualGuns.length; g++)
                VirtualGuns[g].fireVirtualBullet(firePower);
    }

    public void setScanner(String s) {
        if (SCANNER != DEAD)
            SCANNER = s;
    }


    public VirtualGun[] getVirtualGuns() {
        VirtualGun[] guns = new VirtualGun[VirtualGuns.length];
        for (int i = 0; i < VirtualGuns.length; i++)
            guns[i] = VirtualGuns[i];
        return guns;
    }

    public VirtualGun getTopVirtualGun() {
        if (MyRobot == null)
            return null;
        double hitRate = 0.0;
        int guni = -1;
        for (int i = 0; i < VirtualGuns.length; i++) {
            double GunHitRate = VirtualGuns[i].getHitRate();
            if (GunHitRate > hitRate) {
                guni = i;
                hitRate = GunHitRate;
            }
        }
        if (guni != -1)
            return VirtualGuns[guni];
        else if (VirtualGuns.length != 0)
            return VirtualGuns[0];
        else
            return new VirtualGun(MyRobot);
    }

    public VirtualGun getTopVirtualGun_wRollingRate() {
        if (MyRobot == null)
            return null;
        double hitRate = 0.0;
        int guni = -1;
        for (int i = 0; i < VirtualGuns.length; i++) {
            double GunHitRate = (VirtualGuns[i].getHitRate() + VirtualGuns[i].getRollingRate()) / 2;
            if (GunHitRate > hitRate) {
                guni = i;
                hitRate = GunHitRate;
            }
        }
        if (guni != -1)
            return VirtualGuns[guni];
        else if (VirtualGuns.length != 0)
            return VirtualGuns[0];
        else
            return new VirtualGun(MyRobot);
    }

    public VirtualGun getTopVirtualGunRollingRate() {
        if (MyRobot == null)
            return null;
        double hitRate = 0.0;
        int gunindex = -1;
        for (int i = 0; i < VirtualGuns.length; i++) {
            double GunHitRate = VirtualGuns[i].getRollingRate();
            if (GunHitRate > hitRate) {
                gunindex = i;
                hitRate = GunHitRate;
            }
        }
        if (gunindex != -1)
            return VirtualGuns[gunindex];
        else if (VirtualGuns.length != 0)
            return VirtualGuns[0];
        else
            return new VirtualGun(MyRobot);
    }


    public String getScanner() {
        return SCANNER;
    }

    public boolean didFireBullet() {
        return FIRE_BULLET;
    }

    public double getAverageHeading() {
        double avg = 0.0;
        for (int i = 0; i < AVG_HEADING.length; i++) {
            avg += AVG_HEADING[i];
        }
        return avg / AVG_HEADING.length;
    }

    public double getAverageVelocity() {
        double avg = 0.0;
        for (int i = 0; i < AVG_VELOCITY.length; i++) {
            avg += AVG_VELOCITY[i];
        }
        return avg / AVG_VELOCITY.length;
    }

    public double[] getSectors(double firePower) {
        return GuessFactor.getSectors(firePower);
    }

    public double[] getCluster(double FirePower, int max) {
        return ClusteringGF.getCluster(FirePower, max);
    }

    public PatternMatchClusteringData getPMClustering() {
        return ClusteringPM;
    }


    public void drawVirtualBullets(RobocodeGraphicsDrawer g) {
        if (VirtualGuns != null) {
            for (int gun = 0; gun < VirtualGuns.length; gun++) {
                VirtualGuns[gun].drawBullets(g);
            }
        }
    }

    public void printVirtualGunStates(PrintStream p) {
        VirtualGun[] vg = getVirtualGuns();
        for (int gun = 0; gun < vg.length; gun++) {
            p.println("Hit Rate: " + Utils.round(vg[gun].getHitRate(), .001) + " For: "
                      + vg[gun].getTargeting().getNameOfTargeting());
        }
    }

    public void drawVirtualGunGraph(RobocodeGraphicsDrawer g) {
        drawRobot(g, Color.DARK_GRAY);
        double lengthofbar = 20;
        double heightofbar = ((RobotInfo.HEIGHT - (VirtualGuns.length)) / VirtualGuns.length);

        double x = X + RobotInfo.WIDTH / 2;
        double y = Y - RobotInfo.HEIGHT / 2;

        VirtualGun[] vg = getVirtualGuns();
        double MaxHitRate = 0.0;
        for (int gun = 0; gun < vg.length; gun++) {
            if (vg[gun].getHitRate() > MaxHitRate)
                MaxHitRate = vg[gun].getHitRate();
        }
        for (int gun = vg.length - 1, p = 0; gun >= 0; gun--, p++) {
            g.setColor(vg[gun].getTargeting().getTargetingColor());
            double rateing = (vg[gun].getHitRate() / MaxHitRate * lengthofbar);
            g.fillRect(x + 1, y, rateing, heightofbar);
            y += heightofbar + 1;
        }
    }

    public void drawGuessFactorData(RobocodeGraphicsDrawer g) {
        GuessFactor.drawData(g);
    }

    public void drawGuessFactorWaves(RobocodeGraphicsDrawer g) {
        GuessFactor.drawWaves(g);
    }

    public GuessFactorData getGuessFactorData() {
        if (GuessFactor != null)
            return GuessFactor;
        else
            return new GuessFactorData();
    }

    public void drawClusteringGFData(RobocodeGraphicsDrawer g) {
        ClusteringGF.drawData(g);
    }

    public void drawClusteringGFWaves(RobocodeGraphicsDrawer g) {
        ClusteringGF.drawWaves(g);
    }

    public void drawRecentMovement(RobocodeGraphicsDrawer g) {
        if (POLAR_PATTERN_MATCHER != null)
            POLAR_PATTERN_MATCHER.drawRecentMovement(g, this);
    }

    // public void drawRecentAngleOffSet(RobocodeGraphicsDrawer g) {
    // if (LAT_VEL_PATTERN_MATCHER != null)
    // LAT_VEL_PATTERN_MATCHER.drawRecentMovement(g, getThis());
    // }


    public double getAvgAngleOffSet(double firePower) {
        return GuessFactor.getAvgAngleOffSet(firePower);
    }

    private transient PolarPattern POLAR_MATCHED_PATTERN = null;
    private transient long POLAR_MATCHED_PATTERN_TIME = -1;
    public PolarPattern getPolarPattern() {
        if (POLAR_MATCHED_PATTERN_TIME == TIME)
            return POLAR_MATCHED_PATTERN;
        POLAR_MATCHED_PATTERN_TIME = TIME;
        return POLAR_MATCHED_PATTERN = POLAR_PATTERN_MATCHER.MatchPattern();
    }

    private transient LatVelPattern LAT_VEL_MATCHED_PATTERN = null;
    private transient long LAT_VEL_MATCHED_PATTERN_TIME = -1;
    public LatVelPattern getLatVelPattern() {
        if (LAT_VEL_MATCHED_PATTERN_TIME == TIME)
            return LAT_VEL_MATCHED_PATTERN;
        LAT_VEL_MATCHED_PATTERN_TIME = TIME;
        return LAT_VEL_MATCHED_PATTERN = LAT_VEL_PATTERN_MATCHER.MatchPattern();
    }

    private void updatePatterns() {
        if (DELTA_TIME > 0 && PATTERN_MATCHING) {
            Observation o = new Observation(this, MyRobot);
            for (int i = 0; i < DELTA_TIME; i++) {
                POLAR_PATTERN_MATCHER.addPattern(
                                                 new PolarPattern(
                                                                  DELTA_HEADING / DELTA_TIME,
                                                                  VELOCITY,
                                                                  POLAR_PATTERN_MATCHER.getArrayPosition() + 1),
                                                 o);

                LAT_VEL_PATTERN_MATCHER.addPattern(
                                                   new LatVelPattern(
                                                                     o.getLateralVelocity(),
                                                                     LAT_VEL_PATTERN_MATCHER.getArrayPosition() + 1),
                                                   o);
            }
        }
    }


    private boolean fireBullet() {
        if (GUN_HEAT <= 0 && DELTA_ENERGY >= -3.0 && DELTA_ENERGY < 0.0
            && Math.abs(DELTA_VELOCITY) <= 2) {
            return true;
        }
        return false;
    }

}
