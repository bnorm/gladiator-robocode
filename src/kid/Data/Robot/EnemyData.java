package kid.Data.Robot;

import java.util.*;

import kid.*;
import kid.Data.*;
import kid.Data.PatternMatching.*;
import kid.Data.Segmentation.*;
import kid.Data.Segmentation.Segmentars.*;
import kid.Data.Virtual.*;
import kid.Graphics.*;
import kid.Targeting.*;
import robocode.*;

public class EnemyData extends TeammateData implements java.io.Serializable {

    //***** THIS ROBOTS INFO *****//
    private transient String NAME;
    private transient String ALIAS;
    private transient String SCANNER;

    private transient double X;
    private transient double Y;
    private transient double DIST;

    private transient double ENERGY;
    private transient double DELTA_ENERGY;

    private transient double HEADING;
    private transient double DELTA_HEADING;

    private transient double VELOCITY;

    private transient long TIME;
    private transient int DELTA_TIME;

    private transient boolean FIRE_BULLET = false;
    private transient List BULLETS = new ArrayList();

    public static final int AvgLength = 3;
    private transient int arrayByte = 0;
    private transient double[] AVG_HEADING = new double[AvgLength];
    private transient double[] AVG_VELOCITY = new double[AvgLength];

    private transient AdvancedRobot MyRobot = null;
    private transient kid.Data.MyRobotsInfo i = null;

    //***** STATISTICAL DATA *****//
    private boolean GUESS_FACTOR = false;
    private transient double[] AVG_OFFSET = new double[AvgLength];
    private transient int AVG_OFFSET_ArrayByte = 0;
    private Node[] Segments = null;
    private transient List WAVES = new ArrayList();

    //***** VIRTUAL GUNS *****//
    private boolean VIRTUAL_GUNS = false;
    private VirtualGun[] VirtualGuns = null;

    //***** PATTERN MACHING *****//
    private boolean PATTERN_MATCHING = false;
    private static final int LENGTH_OF_PATTERN = 1500;
    private Pattern[] POLAR_PATTERN;
    private Pattern[] SYMBOL_PATTERN;
    private PatternMatcher PATTERN_MATCHER_POLAR;
    private PatternMatcher PATTERN_MATCHER_SYMBOL;
    private boolean SCAN_THIS_TURN = false;
    private int arrayInt;

    public EnemyData() {
        this(new String(), 0.0, 0.0, 0.0, 0.0, 0.0, 0, Long.MAX_VALUE);
        ALIAS = DEAD;
    }

    public EnemyData(String name, double x, double y, double dist, double energy, double heading, double velocity,
                     long time) {
        NAME = name;
        updateItemFromFile(x, y, dist, energy, heading, velocity, time);
    }


    public void addGuessFactor(AdvancedRobot MyRobot) {
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
        MyRobot.addCustomEvent(new WaveWhatcher());
        WAVES = new ArrayList();
        if (GUESS_FACTOR)
            return;
        //MyRobot.out.println("Seting-up Guess Factor Sectors");
        GUESS_FACTOR = true;

        double maxdist = Utils.getDist(0, 0, MyRobot.getBattleFieldWidth(), MyRobot.getBattleFieldHeight());
        Segmentar[] seg = {
                          new DistSegmentar(maxdist, 0.0),
                          new WallSegmentar(MyRobot.getBattleFieldWidth(), MyRobot.getBattleFieldHeight(), 25, 0.0),
                          new DeltaHeadingSegmentar(i.getRobotTurnRate(i.MAX_VELOCITY), 0),
                          new AdvancingVelocitySegmentar(8, -8),
                          new LateralVelocitySegmentar(8, 0)
        };

        Segments = new Node[seg.length];
        for (int i = 0; i < Segments.length; i++) {
            Segments[i] = new Node(seg[i]);
        }
    }

    public void addPatternMatching() {
        try {
            if (PATTERN_MATCHING && POLAR_PATTERN.length == EnemyData.LENGTH_OF_PATTERN &&
                SYMBOL_PATTERN.length == EnemyData.LENGTH_OF_PATTERN)
                return;
        } catch (Exception e) {}
        //System.out.println("Seting-up PatternMatching");
        PATTERN_MATCHING = true;
        POLAR_PATTERN = new Pattern[LENGTH_OF_PATTERN];
        SYMBOL_PATTERN = new Pattern[LENGTH_OF_PATTERN];

        PATTERN_MATCHER_POLAR = new PatternMatcher(POLAR_PATTERN);
        PATTERN_MATCHER_SYMBOL = new PatternMatcher(SYMBOL_PATTERN);
        arrayInt = 0;
    }

    public void addVirtualGuns(AdvancedRobot MyRobot, Targeting[] Targeting) {
        try {
            if (Targeting.length == VirtualGuns.length && VIRTUAL_GUNS) {
                for (int g = 0; g < VirtualGuns.length; g++) {
                    VirtualGuns[g].updateVirtualGun(MyRobot, this, Targeting[g]);
                }
                return;
            }
        } catch (Exception e) {}
        //System.out.println("Seting-up VirtualGuns");
        VIRTUAL_GUNS = true;
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
        VirtualGuns = new VirtualGun[Targeting.length];
        for (int i = 0; i < Targeting.length; i++) {
            VirtualGuns[i] = new VirtualGun(MyRobot, this, Targeting[i]);
        }
    }

    public void addVirtualGuns(TeamRobot MyRobot, Targeting[] Targeting) {
        addVirtualGuns((AdvancedRobot) MyRobot, Targeting);
    }


    public void updateItem(double x, double y, double dist, double energy, double heading, double velocity, long time) {
        if (time < TIME) {
            updateItemFromFile(NAME, x, y, dist, energy, heading, velocity, time);
        } else if (time != TIME) {
            X = x;
            Y = y;
            DIST = dist;
            DELTA_ENERGY = energy - ENERGY;
            ENERGY = energy;
            DELTA_HEADING = Utils.relative(heading - HEADING);
            HEADING = heading;
            AVG_HEADING[(arrayByte == AvgLength ? arrayByte = 0 : arrayByte)] = DELTA_HEADING;
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

    public void updateItemFromFile(String name, double x, double y, double dist, double energy, double heading,
                                   double velocity, long time) {
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
            POLAR_PATTERN[arrayInt % LENGTH_OF_PATTERN] = null;
            SYMBOL_PATTERN[arrayInt % LENGTH_OF_PATTERN] = null;
            arrayInt++;
        }
    }


    public void updateGuessFatorGun(double firePower) {
        if (GUESS_FACTOR && !isDead())
            WAVES.add(new DataWave(MyRobot, this, firePower));
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
        /*   OLD
         VirtualGun TopGun = null;
         if (MyRobot != null) {
            TopGun = new VirtualGun(MyRobot);
            double hitRate = 0.0;
            for (int i = 0; i < NumVirtualGuns; i++) {
                VirtualGun Gun = VirtualGuns[i];
                if (Gun.getHitRate() > hitRate) {
                    TopGun = Gun;
                    hitRate = Gun.getHitRate();
                }
            }
          }
          return TopGun;
         */
    }

    public void printVirtualGunStatus() {
        System.out.println("This Is The Status For The VirtualGuns Of " + NAME);
        for (int i = 0; i < VirtualGuns.length; i++) {
            VirtualGuns[i].printStatus();
        }
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

    public double getDist() {
        return DIST;
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

    public int[] getSectors() {
        //return SECTORS;

        int[] s = new int[Bin.numBins];
        if (getFirstWave() == null)
            return s;
        Bin[] b = new Bin[Segments.length];
        Observation o = new Observation(getThis(), MyRobot);
        o.setGFHit(getFirstWave().getGuessFactor(getThis()));
        for (int i = 0; i < b.length; i++) {
            b[i] = Segments[i].getBin(o);
        }
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < s.length; j++) {
                s[j] += (b[i].get(j));
            }
        }
        return s;

    }

    public List getWaves() {
        return WAVES;
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

    public void drawGuessFactorData(RobocodeGraphicsDrawer g) {
        if (isDead() || MyRobot.getOthers() == 0 || !GUESS_FACTOR)
            return;
        double mx = MyRobot.getX(), my = MyRobot.getY();
        double angle = Utils.getAngle(mx, my, getX(), getY());
        double dist = Utils.getDist(mx, my, getX(), getY()) - MyRobotsInfo.WIDTH;
        double d = (Utils.sin(getHeading() - angle) * getVelocity()) < 0 ? -1 : 1;
        int bestindex = 15;
        int[] sectors = getSectors();
        for (int i = 0; i < sectors.length; i++) {
            if (sectors[bestindex] < sectors[i])
                bestindex = i;
        }
        for (int s = 0; s < sectors.length; s++) {
            double guessfactor = Utils.getGuessFactor(s, sectors.length);
            double angleOffset = d * guessfactor * Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(2));
            if (s == (sectors.length - 1) / 2) {
                g.setColor(Colors.YELLOW);
            } else if (s > (sectors.length - 1) / 2) {
                g.setColor(Colors.GREEN);
            } else {
                g.setColor(Colors.RED);
            }
            g.fillOval((int) Utils.getX(mx, dist, angle + angleOffset), (int) Utils.getY(my, dist, angle + angleOffset),
                       (int) (Math.max(((double) sectors[s] / sectors[bestindex]) * 10, 1)),
                       (int) (Math.max(((double) sectors[s] / sectors[bestindex]) * 10, 1)));
        }
    }

    public void drawGuessFactorWaves(RobocodeGraphicsDrawer g) {
        g.setColor(Colors.DARK_GRAY);
        for (int w = 0; w < WAVES.size(); w++) {
            DataWave wave = (DataWave) WAVES.get(w);
            g.drawOval((int) wave.getStartX(), (int) wave.getStartY(), (int) (wave.getDist(MyRobot.getTime()) * 2),
                       (int) (wave.getDist(MyRobot.getTime()) * 2));
        }
    }


    public double getAvgAngleOffSet() {
        double avg = 0.0;
        for (int i = 0; i < AVG_OFFSET.length; i++) {
            avg += AVG_OFFSET[i];
        }
        double angle = Utils.getAngle(MyRobot.getX(), MyRobot.getY(), getX(), getY());
        double d = (Utils.sin(getHeading() - angle) * getVelocity()) < 0 ? -1 : 1;
        return avg / AvgLength * d;
    }


    public Polar getPolarPattern(long t) {
        try {
            Polar p = (Polar) POLAR_PATTERN[(short) t];
            return p;
        } catch (Exception e) {
            return new Polar();
        }
    }

    private transient Pattern[] MATCHED_PATTERN_POLAR = null;
    private transient long MATCHED_PATTERN_POLAR_TIME = -1;
    public Pattern[] getPolarPattern(int RecentMovmentLength) {
        if (MATCHED_PATTERN_POLAR_TIME == TIME)
            return MATCHED_PATTERN_POLAR;
        MATCHED_PATTERN_POLAR_TIME = TIME;
        return MATCHED_PATTERN_POLAR = PATTERN_MATCHER_POLAR.MatchPattern(arrayInt, RecentMovmentLength, TIME);
    }

    public Symbol getSymbolPattern(long t) {
        try {
            Symbol p = (Symbol) SYMBOL_PATTERN[(int) t];
            return p;
        } catch (Exception e) {
            return new Symbol();
        }
    }

    private transient Pattern[] MATCHED_PATTERN_SYMBOL = null;
    private transient long MATCHED_PATTERN_SYMBOL_TIME = -1;
    public Pattern[] getSymbolPattern(int RecentMovmentLength) {
        if (MATCHED_PATTERN_SYMBOL_TIME == TIME)
            return MATCHED_PATTERN_SYMBOL;
        MATCHED_PATTERN_SYMBOL_TIME = TIME;
        return MATCHED_PATTERN_SYMBOL = PATTERN_MATCHER_SYMBOL.MatchPattern(arrayInt, RecentMovmentLength, TIME);
    }

    public void drawRecentMovement(RobocodeGraphicsDrawer g) {
        PATTERN_MATCHER_POLAR.drawRecentMovement(g, getThis());
    }

    private void updatePatterns() {
        if (DELTA_TIME > 0 && PATTERN_MATCHING) {
            for (int i = 0; i < DELTA_TIME; i++) {
                if (i == DELTA_TIME - 1)
                    SCAN_THIS_TURN = true;
                POLAR_PATTERN[arrayInt %
                        POLAR_PATTERN.length] = new Polar((DELTA_HEADING / DELTA_TIME), VELOCITY, SCAN_THIS_TURN);
                SYMBOL_PATTERN[arrayInt %
                        SYMBOL_PATTERN.length] = new Symbol((DELTA_HEADING / DELTA_TIME), VELOCITY, SCAN_THIS_TURN);
                arrayInt++;
                SCAN_THIS_TURN = false;
            }
            if (arrayInt > LENGTH_OF_PATTERN * 2)
                arrayInt -= LENGTH_OF_PATTERN;
        }
    }


    private boolean fireBullet() {
        if (DELTA_ENERGY >= -3.0 && DELTA_ENERGY <= -0.1 && DELTA_TIME == 1) {
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

                    AVG_OFFSET[AVG_OFFSET_ArrayByte % AVG_OFFSET.length] = wave.getAngleOffset(getThis());
                    AVG_OFFSET_ArrayByte++;

                    Observation o = wave.getObservation();
                    o.setGFHit(getFirstWave().getGuessFactor(getThis()));
                    for (int i = 0; i < Segments.length; i++) {
                        Segments[i].addObservation(o, false);
                    }

                    WAVES.remove(w);
                    w--;
                }
            }
            return false;
        }
    }

}
