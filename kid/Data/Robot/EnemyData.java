package kid.Data.Robot;

import java.awt.Color;
import java.io.PrintStream;
import java.util.*;

import kid.*;
import kid.Clustering.Cluster;
import kid.Clustering.ClusterManager;
import kid.Data.*;
import kid.Data.PatternMatching.*;
import kid.Segmentation.*;
import kid.Segmentation.Segmentars.*;
import kid.Data.Virtual.*;
import kid.Targeting.*;
import robocode.*;

public class EnemyData extends TeammateData implements java.io.Serializable {
    private static final long serialVersionUID = 9130243312527050052L;

    // ***** THIS ROBOTS INFO *****//
    private transient String SCANNER;

    // private transient double BULLET_POWER = 0.0;
    private transient double GUN_HEAT = 3.0;
    // private transient long FIRE_TIME = 0;
    private transient boolean FIRE_BULLET = false;
    // private transient List BULLETS = new ArrayList();

    public static final int AvgLength = 10;
    private transient int arrayByte = 0;
    private transient double[] AVG_HEADING = new double[AvgLength];
    private transient double[] AVG_VELOCITY = new double[AvgLength];

    private transient AdvancedRobot MyRobot = null;
    private transient kid.Data.MyRobotsInfo i = null;

    // ***** STATISTICAL DATA *****//
    private boolean DINAMIC_CLUSTERING = false;
    private ClusterManager Cluster = null;

    private boolean GUESS_FACTOR = false;
    private boolean GUESS_FACTOR_MELEE = false;
    private transient double[] AVG_OFFSET = new double[AvgLength];
    private transient int AVG_OFFSET_ArrayByte = 0;
    private Node SegmentTree = null;
    private GFBin FlatBin = null;
    private Node SegmentTreeMelee = null;
    private transient List WAVES = new ArrayList();

    // ***** VIRTUAL GUNS *****//
    private boolean VIRTUAL_GUNS = false;
    private VirtualGun[] VirtualGuns = null;

    // ***** PATTERN MACHING *****//
    private boolean PATTERN_MATCHING = false;
    private PolarPatternMatcher POLAR_PATTERN_MATCHER = null;
    private LatVelPatternMatcher LAT_VEL_PATTERN_MATCHER = null;


    public EnemyData() {
        this(new String(), 0.0, 0.0, 0.0, 0.0, 0, Long.MAX_VALUE);
        ALIAS = DEAD;
    }

    public EnemyData(String name, double x, double y, double energy, double heading, double velocity, long time) {
        NAME = name;
        updateItemFromFile(name, x, y, energy, heading, velocity, time);
    }

    public void addDinamicClustering(AdvancedRobot MyRobot, Cluster[] Clusters) {
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
        if (!GUESS_FACTOR_MELEE || !GUESS_FACTOR)
            MyRobot.addCustomEvent(new WaveWhatcher());
        WAVES = new ArrayList();
        if (DINAMIC_CLUSTERING)
            return;
        DINAMIC_CLUSTERING = true;
        Cluster = new ClusterManager(Clusters);
    }

    public void addGuessFactor(AdvancedRobot MyRobot, Segmentar[] Segmentars) {
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
        if (!GUESS_FACTOR_MELEE || !DINAMIC_CLUSTERING)
            MyRobot.addCustomEvent(new WaveWhatcher());
        WAVES = new ArrayList();
        if (GUESS_FACTOR)
            return;
        GUESS_FACTOR = true;
        if (Segmentars != null) {
            SegmentTree = new Node(Segmentars, new GFBin());
        }
        FlatBin = new GFBin();
    }

    public void addGuessFactorMelee(AdvancedRobot MyRobot, Segmentar[] Segmentars) {
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
        if (!GUESS_FACTOR || !DINAMIC_CLUSTERING)
            MyRobot.addCustomEvent(new WaveWhatcher());
        WAVES = new ArrayList();
        if (GUESS_FACTOR_MELEE)
            return;
        GUESS_FACTOR_MELEE = true;
        if (Segmentars != null) {
            SegmentTreeMelee = new Node(Segmentars, new GFBin());
        }
        FlatBin = new GFBin();
    }

    public void addPatternMatching(AdvancedRobot MyRobot) {
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
        if (PATTERN_MATCHING)
            return;
        PATTERN_MATCHING = true;

        POLAR_PATTERN_MATCHER = new PolarPatternMatcher();
        LAT_VEL_PATTERN_MATCHER = new LatVelPatternMatcher();
    }

    public void addVirtualGuns(AdvancedRobot MyRobot, Targeting[] Targeting) {
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
        if (Targeting != null && VirtualGuns != null && Targeting.length == VirtualGuns.length && VIRTUAL_GUNS) {
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


    public void updateItem(double x, double y, double energy, double heading, double velocity, long time) {
        if (time < TIME) {
            updateItemFromFile(NAME, x, y, energy, heading, velocity, time);
        } else if (time != TIME) {
            super.updateItem(x, y, energy, heading, velocity, time);
            AVG_HEADING[(arrayByte == AvgLength ? arrayByte = 0 : arrayByte)] = DELTA_HEADING;
            AVG_VELOCITY[(arrayByte == AvgLength ? arrayByte = 0 : arrayByte)] = VELOCITY;
            arrayByte++;
            updatePatterns();
            GUN_HEAT -= 0.1 * DELTA_TIME;
            FIRE_BULLET = fireBullet();
            if (FIRE_BULLET) {
                // FIRE_TIME = time;
                GUN_HEAT = Utils.gunHeat(Math.abs(DELTA_ENERGY));
            }
        }
    }

    public void updateItem(EnemyData enemyData) {
    }

    public void updateItemFromFile(String name, double x, double y, double energy, double heading, double velocity,
            long time) {
        super.updateItemFromFile(name, x, y, energy, heading, velocity, time);
        arrayByte = 0;
        AVG_OFFSET = new double[AvgLength];
        AVG_OFFSET_ArrayByte = 0;
        if (PATTERN_MATCHING) {
            POLAR_PATTERN_MATCHER.addPattern(null, null);
            LAT_VEL_PATTERN_MATCHER.addPattern(null, null);
        }
        GUN_HEAT -= 0.1 * time;
    }


    public void updateGuessFactor(double firePower) {
        if ((GUESS_FACTOR || GUESS_FACTOR_MELEE || DINAMIC_CLUSTERING) && !isDead())
            WAVES.add(new DataWave(MyRobot, this, firePower, getSectors(firePower)));
    }

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
        int guni = -1;
        for (int i = 0; i < VirtualGuns.length; i++) {
            double GunHitRate = VirtualGuns[i].getRollingRate();
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

    public double[] getSectors(double FirePower) {
        double[] s = new double[GFBin.numBins];
        if (getFirstWave() == null)
            return s;
        if (SegmentTreeMelee == null && SegmentTree == null) {
            for (int j = 0; j < s.length; j++) {
                s[j] += FlatBin.get(j);
            }
            return s;
        }
        GFBin b = new GFBin();
        Observation o = new Observation(getThis(), MyRobot);
        o.setGFHit(getFirstWave().getGuessFactor(getThis()));
        o.setVirtualWave(new DataWave(MyRobot, getThis(), FirePower, null));
        if (i.getTotalOthers() > 1 && SegmentTreeMelee != null)
            b = (GFBin) SegmentTreeMelee.getBin(o);
        else
            b = (GFBin) SegmentTree.getBin(o);
        for (int j = 0; j < s.length; j++) {
            s[j] += b.get(j);
        }
        return s;
    }

    public double[] getCluster(double FirePower, int max) {
        double[] s = new double[GFBin.numBins];
        if (getFirstWave() == null)
            return s;
        if (DINAMIC_CLUSTERING) {
            Observation o = new Observation(getThis(), MyRobot);
            o.setGFHit(getFirstWave().getGuessFactor(getThis()));
            o.setVirtualWave(new DataWave(MyRobot, getThis(), FirePower, null));
            Observation[] obs = Cluster.getCluster(max, o);
            if (obs == null)
                return s;
            GFBin bin = new GFBin();
            for (int i = 0; i < obs.length; i++) {
                bin.add(obs[i]);
            }
            for (int j = 0; j < s.length; j++) {
                s[j] += bin.get(j);
            }
        }
        return s;
    }

    public void printObservation() {
        Observation o = new Observation(getThis(), MyRobot);
        if (SegmentTree != null && MyRobot.getOthers() == 1)
            SegmentTree.printObservation(o);
        else if (SegmentTree == null)
            SegmentTreeMelee.printObservation(o);
    }

    public DataWave[] getWaves() {
        DataWave[] waves = new DataWave[WAVES.size()];
        for (int w = 0; w < waves.length; w++) {
            waves[w] = (DataWave) WAVES.get(w);
        }
        return waves;
    }

    public DataWave getFirstWave() {
        try {
            return (DataWave) WAVES.get(0);
        } catch (Exception e) {
            return null;
        }
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
        double heightofbar = (36 / VirtualGuns.length) - 1;

        double x = X + MyRobotsInfo.WIDTH / 2;
        double y = Y - MyRobotsInfo.HEIGHT / 2;

        VirtualGun[] vg = getVirtualGuns();
        double MaxHitRate = 0.0;
        for (int gun = 0; gun < vg.length; gun++) {
            if (vg[gun].getHitRate() > MaxHitRate)
                MaxHitRate = vg[gun].getHitRate();
        }
        for (int gun = vg.length - 1, p = 0; gun >= 0; gun--, p++) {
            g.setColor(vg[gun].getTargeting().getTargetingColor());
            double rateing = (vg[gun].getHitRate() / MaxHitRate * lengthofbar);
            g.fillRect(x + 1, y + (p * heightofbar) + (p), rateing, heightofbar);

        }
    }

    public void drawGuessFactorData(RobocodeGraphicsDrawer g, int robotNum) {
        if (isDead() || MyRobot.getOthers() == 0)
            return;
        double sx = 10, sy = 10;
        double px = sx, py = sy;

        double multi = 5;

        double[] sectors = getSectors(2);

        for (int s = 0; s < sectors.length * multi; s++) {
            double x = sx + s;
            int index1 = (int) (s / multi), index2 = (int) (s / multi) + 1;
            double height = Utils.weightedAvg(sectors[index1], index2 - (s / multi), sectors[index2], (s / multi)
                    - index1);
            double y = Math.max(height, 0) * multi + sy;
            g.setColor(Colors.GREEN);

            g.drawLine(px, py, x, y);

            px = x;
            py = y;
        }
    }
    public void drawGuessFactorWaves(RobocodeGraphicsDrawer g) {
        g.setColor(Colors.DARK_GRAY);
        for (int w = 0; w < WAVES.size(); w++) {
            DataWave wave = (DataWave) WAVES.get(w);
            g.drawArcCenter((int) wave.getStartX(), (int) wave.getStartY(), (int) wave.getDist(MyRobot.getTime()) * 2,
                    (int) wave.getDist(MyRobot.getTime()) * 2, (int) (wave.getHeading() - wave.maxEscapeAngle()),
                    (int) (wave.maxEscapeAngle() * 2));
        }
    }

    public void drawRecentMovement(RobocodeGraphicsDrawer g) {
        if (POLAR_PATTERN_MATCHER != null)
            POLAR_PATTERN_MATCHER.drawRecentMovement(g, getThis());
    }

    public void drawRecentAngleOffSet(RobocodeGraphicsDrawer g) {
        // if (LAT_VEL_PATTERN_MATCHER != null)
        // LAT_VEL_PATTERN_MATCHER.drawRecentMovement(g, getThis());
    }

    public void drawRobot(RobocodeGraphicsDrawer g, Color c) {
        g.setColor(c);
        g.drawRectCenter(X, Y, MyRobotsInfo.WIDTH, MyRobotsInfo.HEIGHT);
    }


    public double getAvgAngleOffSet(double firePower) {
        double avg = 0.0;
        for (int i = 0; i < AVG_OFFSET.length; i++) {
            avg += AVG_OFFSET[i];
        }
        // double angle = Utils.getAngle(MyRobot.getX(), MyRobot.getY(), getX(),
        // getY());
        // double d = (Utils.sin(getHeading() - angle) * getVelocity()) < 0 ? -1
        // : 1;
        return (avg / AvgLength) * Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(firePower));// * d;
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
            Observation o = new Observation(getThis(), MyRobot);
            for (int i = 0; i < DELTA_TIME; i++) {
                POLAR_PATTERN_MATCHER.addPattern(new PolarPattern(DELTA_HEADING / DELTA_TIME, VELOCITY,
                        POLAR_PATTERN_MATCHER.getArrayPosition() + 1), o);

                LAT_VEL_PATTERN_MATCHER.addPattern(new LatVelPattern(o.getLateralVelocity(), LAT_VEL_PATTERN_MATCHER
                        .getArrayPosition() + 1), o);
            }
        }
    }


    private boolean fireBullet() {
        if (GUN_HEAT <= 0 && DELTA_ENERGY >= -3.0 && DELTA_ENERGY < 0.0 && Math.abs(DELTA_VELOCITY) <= 2) {
            return true;
        }
        return false;
    }

    private EnemyData getThis() {
        return this;
    }

    private class WaveWhatcher extends Condition {
        public boolean test() {
            for (int w = 0; w < WAVES.size(); w++) {
                DataWave wave = (DataWave) WAVES.get(w);
                if (wave.testHit(getRobot(), getTime())) {

                    AVG_OFFSET[AVG_OFFSET_ArrayByte % AVG_OFFSET.length] = wave.getGuessFactor(getThis())
                            / wave.getDirection();
                    AVG_OFFSET_ArrayByte++;


                    Observation o = wave.getObservation();
                    o.setGFHit(wave.getGuessFactor(getThis()), wave.getFirePower());

                    if (SegmentTree != null && (i.getTotalOthers() == 1 || SegmentTreeMelee == null)) {
                        SegmentTree.addObservation(o, false);
                        FlatBin.add(o);
                    } else if (SegmentTreeMelee != null) {
                        SegmentTreeMelee.addObservation(o, false);
                        FlatBin.add(o);
                    }
                    if (Cluster != null)
                        Cluster.add(o);

                    WAVES.remove(w);
                    w--;
                }
            }
            return false;
        }
    }

}
