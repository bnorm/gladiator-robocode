package kid.Data.Robot;

import java.awt.Color;
import java.io.PrintStream;
import java.util.*;

import kid.*;
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
    private transient String NAME;
    private transient String ALIAS;
    private transient String SCANNER;

    private transient double X;
    private transient double Y;

    private transient double ENERGY;
    private transient double DELTA_ENERGY;

    private transient double HEADING;
    private transient double DELTA_HEADING;

    private transient double VELOCITY;
    private transient double DELTA_VELOCITY;
    private transient double TIME_OF_ACCEL;
    private transient double TIME_OF_DECCEL;

    private transient long TIME = Long.MAX_VALUE;
    private transient int DELTA_TIME;

    // private transient double BULLET_POWER = 0.0;
    // private transient double GUN_HEAT = 3.0;
    private transient boolean FIRE_BULLET = false;
    // private transient List BULLETS = new ArrayList();

    public static final int AvgLength = 3;
    private transient int arrayByte = 0;
    private transient double[] AVG_HEADING = new double[AvgLength];
    private transient double[] AVG_VELOCITY = new double[AvgLength];

    private transient AdvancedRobot MyRobot = null;
    // private transient kid.Data.MyRobotsInfo i = null;

    // ***** STATISTICAL DATA *****//
    private boolean GUESS_FACTOR = false;
    private transient double[] AVG_OFFSET = new double[AvgLength];
    private transient int AVG_OFFSET_ArrayByte = 0;
    private Node SegmentTree = null;
    private GFBin FlatBin = null;
    private transient List WAVES = new ArrayList();

    // ***** VIRTUAL GUNS *****//
    private boolean VIRTUAL_GUNS = false;
    private VirtualGun[] VirtualGuns = null;

    // ***** PATTERN MACHING *****//
    private boolean PATTERN_MATCHING = false;
    private RobotVector OldVector = null;
    private RobotVector NewVector = null;
    private PolarPatternMatcher POLAR_PATTERN_MATCHER = null;
    private LatVelPatternMatcher LAT_VEL_PATTERN_MATCHER = null;


    public EnemyData() {
        this(new String(), 0.0, 0.0, 0.0, 0.0, 0, Long.MAX_VALUE);
        ALIAS = DEAD;
    }

    public EnemyData(String name, double x, double y, double energy, double heading, double velocity, long time) {
        NAME = name;
        updateItemFromFile(x, y, energy, heading, velocity, time);
    }


    public void addGuessFactor(AdvancedRobot MyRobot, Segmentar[] Segmentars) {
        this.MyRobot = MyRobot;
        // i = new MyRobotsInfo(MyRobot);
        MyRobot.addCustomEvent(new WaveWhatcher());
        WAVES = new ArrayList();
        if (GUESS_FACTOR)
            return;
        GUESS_FACTOR = true;
        if (Segmentars != null) {
            SegmentTree = new Node(Segmentars, new GFBin());
        } else {
            FlatBin = new GFBin();
        }
    }

    public void addPatternMatching(AdvancedRobot MyRobot) {
        this.MyRobot = MyRobot;
        // i = new MyRobotsInfo(MyRobot);
        if (PATTERN_MATCHING)
            return;
        PATTERN_MATCHING = true;
        POLAR_PATTERN_MATCHER = new PolarPatternMatcher();
        LAT_VEL_PATTERN_MATCHER = new LatVelPatternMatcher();
    }

    public void addVirtualGuns(AdvancedRobot MyRobot, Targeting[] Targeting) {
        this.MyRobot = MyRobot;
        // i = new MyRobotsInfo(MyRobot);
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
            X = x;
            Y = y;
            DELTA_ENERGY = energy - ENERGY;
            ENERGY = energy;
            DELTA_HEADING = Utils.relative(heading - HEADING);
            HEADING = heading;
            AVG_HEADING[(arrayByte == AvgLength ? arrayByte = 0 : arrayByte)] = DELTA_HEADING;
            DELTA_VELOCITY = velocity - VELOCITY;
            if (Math.abs(DELTA_VELOCITY) == MyRobotsInfo.ACCELERATION)
                TIME_OF_ACCEL = time;
            else if (Math.abs(DELTA_VELOCITY) == MyRobotsInfo.DECCELERATION)
                TIME_OF_DECCEL = time;
            VELOCITY = velocity;
            AVG_VELOCITY[(arrayByte == AvgLength ? arrayByte = 0 : arrayByte)] = VELOCITY;
            arrayByte++;
            DELTA_TIME = (int) (time - TIME);
            TIME = time;
            updatePatterns();
            FIRE_BULLET = fireBullet();
        }
    }

    public void updateItem(EnemyData enemyData) {
    }

    public void updateItemFromFile(String name, double x, double y, double energy, double heading, double velocity,
            long time) {
        ALIAS = ALIVE;
        NAME = name;
        X = x;
        Y = y;
        ENERGY = energy;
        HEADING = heading;
        AVG_HEADING = new double[AvgLength];
        VELOCITY = velocity;
        AVG_VELOCITY = new double[AvgLength];
        TIME = time;
        arrayByte = 0;
        AVG_OFFSET = new double[AvgLength];
        AVG_OFFSET_ArrayByte = 0;
        if (PATTERN_MATCHING) {
            POLAR_PATTERN_MATCHER.addPattern(null, null);
            LAT_VEL_PATTERN_MATCHER.addPattern(null, null);
            OldVector = null;
            NewVector = new RobotVector(x, y, heading, velocity);
        }
    }


    public void updateGuessFactor(double firePower) {
        if (GUESS_FACTOR && !isDead())
            WAVES.add(new DataWave(MyRobot, this, firePower, getSectors()));
    }

    public void updateVirtualGuns(double firePower) {
        if (VIRTUAL_GUNS && !isDead())
            for (int g = 0; g < VirtualGuns.length; g++)
                VirtualGuns[g].fireVirtualBullet(firePower);
    }


    public void setDeath() {
        ENERGY = ENERGY_DEAD;
        setAlias(DEAD);
    }

    public void setAlias(String a) {
        if (ALIAS != DEAD)
            ALIAS = a;
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


    public String getName() {
        return NAME;
    }

    public String getAlias() {
        return ALIAS;
    }

    public String getScanner() {
        return SCANNER;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getEnergy() {
        return ENERGY;
    }

    public double getDeltaEnergy() {
        return DELTA_ENERGY;
    }

    public boolean didFireBullet() {
        return FIRE_BULLET;
    }

    public double getHeading() {
        return HEADING;
    }

    public double getDeltaHeading() {
        return DELTA_HEADING;
    }

    public double getAverageHeading() {
        double avg = 0.0;
        for (int i = 0; i < AVG_HEADING.length; i++) {
            avg += AVG_HEADING[i];
        }
        return avg / AVG_HEADING.length;
    }

    public double getVelocity() {
        return VELOCITY;
    }

    public double getDeltaVelocity() {
        return DELTA_VELOCITY;
    }

    public double getTimeSinceAccel() {
        return TIME - TIME_OF_ACCEL;
    }

    public double getTimeSinceDeccel() {
        return TIME - TIME_OF_DECCEL;
    }

    public double getAverageVelocity() {
        double avg = 0.0;
        for (int i = 0; i < AVG_VELOCITY.length; i++) {
            avg += AVG_VELOCITY[i];
        }
        return avg / AVG_VELOCITY.length;
    }

    public long getTime() {
        return TIME;
    }

    public int getDeltaTime() {
        return DELTA_TIME;
    }

    public double[] getSectors() {
        // return SECTORS;
        double[] s = new double[GFBin.numBins];
        if (getFirstWave() == null)
            return s;
        if (FlatBin != null) {
            for (int j = 0; j < s.length; j++) {
                s[j] += FlatBin.get(j);
            }
            return s;
        }
        GFBin b = new GFBin();
        Observation o = new Observation(getThis(), MyRobot);
        o.setGFHit(getFirstWave().getGuessFactor(getThis()));
        b = (GFBin) SegmentTree.getBin(o);
        for (int j = 0; j < s.length; j++) {
            s[j] += b.get(j);
        }
        return s;
    }

    public void printObservation() {
        Observation o = new Observation(getThis(), MyRobot);
        SegmentTree.printObservation(o);
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
        double heightofbar = (36 - (VirtualGuns.length - 1)) / (VirtualGuns.length - 1);

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

    public void drawGuessFactorData(RobocodeGraphicsDrawer g) {
        if (isDead() || MyRobot.getOthers() == 0 || !GUESS_FACTOR)
            return;
        double mx = MyRobot.getX(), my = MyRobot.getY();
        double angle = Utils.getAngle(mx, my, getX(), getY());
        double dist = Utils.getDist(mx, my, getX(), getY()) - MyRobotsInfo.WIDTH;
        double d = (Utils.sin(getHeading() - angle) * getVelocity()) < 0 ? -1 : 1;
        int bestindex = 15;
        double[] sectors = getSectors();
        for (int i = 0; i < sectors.length; i++) {
            if (sectors[bestindex] < sectors[i])
                bestindex = i;
        }
        for (int s = 0; s < sectors.length; s++) {
            double guessfactor = Utils.getGuessFactor(s, sectors.length);
            double angleOffset = d * guessfactor * Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(3.0));
            if (s == (sectors.length - 1) / 2) {
                g.setColor(Colors.YELLOW);
            } else if (s > (sectors.length - 1) / 2) {
                g.setColor(Colors.GREEN);
            } else {
                g.setColor(Colors.RED);
            }
            g.fillOvalCenter(Utils.getX(mx, dist, angle + angleOffset), Utils.getY(my, dist, angle + angleOffset), Math
                    .max((sectors[s] / sectors[bestindex]) * 10, 1), Math
                    .max((sectors[s] / sectors[bestindex]) * 10, 1));
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
            double angle = Utils.getAngle(MyRobot.getX(), MyRobot.getY(), X, Y);
            for (int i = 0; i < DELTA_TIME; i++) {
                POLAR_PATTERN_MATCHER.addPattern(new PolarPattern(DELTA_HEADING / DELTA_TIME, VELOCITY,
                        POLAR_PATTERN_MATCHER.getArrayPosition() + 1), o);

                LAT_VEL_PATTERN_MATCHER.addPattern(new LatVelPattern(VELOCITY * Utils.sin(HEADING - angle),
                        LAT_VEL_PATTERN_MATCHER.getArrayPosition() + 1), o);
            }
        }
    }


    private boolean fireBullet() {
        if (DELTA_ENERGY >= -3.0 && DELTA_ENERGY <= -0.1) {
            // System.out.println("Bullet Power Of: " + Math.abs(DELTA_ENERGY));
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
                    if (FlatBin == null) {
                        SegmentTree.addObservation(o, false);
                    } else {
                        FlatBin.add(o);
                    }


                    WAVES.remove(w);
                    w--;
                }
            }
            return false;
        }
    }

}
