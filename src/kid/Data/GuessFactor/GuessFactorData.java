package kid.Data.GuessFactor;

import java.util.*;

import robocode.*;

import kid.*;
import kid.Utils;
import kid.Data.*;
import kid.Data.Robot.*;
import kid.Data.Virtual.DataWave;
import kid.Segmentation.*;
import kid.Segmentation.Segmentars.Segmentar;

public class GuessFactorData {

    private static final int AVG_LENGTH = 10;

    private boolean GUESS_FACTOR = false;
    private boolean GUESS_FACTOR_MELEE = false;
    private transient double[] AVG_OFFSET = new double[AVG_LENGTH];
    private transient int AVG_OFFSET_ArrayByte = 0;
    private Node SegmentTree = null;
    private GFBin FlatBin = null;
    private Node SegmentTreeMelee = null;
    private transient List WAVES = new ArrayList();

    private AdvancedRobot MyRobot;
    private RobotInfo i;
    private EnemyData Enemy;
    
    public GuessFactorData() {
    }

    public GuessFactorData(EnemyData Enemy, AdvancedRobot MyRobot) {
        this.Enemy = Enemy;
        this.MyRobot = MyRobot;
        i = new RobotInfo(MyRobot);
    }

    public void addSegmentars(Segmentar[] Segmentars) {
        MyRobot.addCustomEvent(new WaveWhatcher());
        WAVES = new ArrayList();
        if (GUESS_FACTOR)
            return;
        GUESS_FACTOR = true;
        if (Segmentars != null)
            SegmentTree = new Node(Segmentars, new GFBin());
        if (FlatBin == null)
            FlatBin = new GFBin();
    }

    public void addMeleeSegmentars(Segmentar[] Segmentars) {
        if (GUESS_FACTOR_MELEE)
            return;
        GUESS_FACTOR_MELEE = true;
        if (Segmentars != null)
            SegmentTreeMelee = new Node(Segmentars, new GFBin());
        if (FlatBin == null)
            FlatBin = new GFBin();
    }
    
    public void updateGuessFactor(double firePower) {
        if ((GUESS_FACTOR || GUESS_FACTOR_MELEE) && !Enemy.isDead())
            WAVES.add(new DataWave(MyRobot, Enemy, firePower, Enemy.getSectors(firePower)));
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
        Observation o = new Observation(Enemy, MyRobot);
        o.setGFHit(getFirstWave().getGuessFactor(Enemy));
        o.setVirtualWave(new DataWave(MyRobot, Enemy, FirePower, null));
        if (i.getTotalOthers() > 1 && SegmentTreeMelee != null)
            b = (GFBin) SegmentTreeMelee.getBin(o);
        else
            b = (GFBin) SegmentTree.getBin(o);
        for (int j = 0; j < s.length; j++) {
            s[j] += b.get(j);
        }
        return s;
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
        return (avg / AVG_LENGTH) * Utils.asin(RobotInfo.MAX_VELOCITY / Utils.bulletVelocity(firePower));// * d;
    }
    
    public void print() {
        if (MyRobot == null)
            return;
        if (SegmentTree != null && MyRobot.getOthers() == 1)
            SegmentTree.printTree();
        else if (SegmentTree == null && SegmentTreeMelee != null)
            SegmentTreeMelee.printTree();
    }
    
    public DataWave getFirstWave() {
        try {
            return (DataWave) WAVES.get(0);
        } catch (Exception e) {
            return null;
        }
    }


    public void drawData(RobocodeGraphicsDrawer g) {
        if (Enemy.isDead() || MyRobot.getOthers() == 0)
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
    
    public void drawWaves(RobocodeGraphicsDrawer g) {
        g.setColor(Colors.DARK_GRAY);
        for (int w = 0; w < WAVES.size(); w++) {
            DataWave wave = (DataWave) WAVES.get(w);
            g.drawArcCenter((int) wave.getStartX(), (int) wave.getStartY(), (int) wave.getDist(MyRobot.getTime()) * 2,
                    (int) wave.getDist(MyRobot.getTime()) * 2, (int) (wave.getHeading() - wave.maxEscapeAngle()),
                    (int) (wave.maxEscapeAngle() * 2));
        }
    }
    
    private class WaveWhatcher extends Condition {
        public boolean test() {
            for (int w = 0; w < WAVES.size(); w++) {
                DataWave wave = (DataWave) WAVES.get(w);
                if (wave.testHit(Enemy, MyRobot.getTime())) {

                    AVG_OFFSET[AVG_OFFSET_ArrayByte % AVG_OFFSET.length] = wave.getGuessFactor(Enemy)
                            / wave.getDirection();
                    AVG_OFFSET_ArrayByte++;

                    Observation o = wave.getObservation();
                    o.setGFHit(wave.getGuessFactor(Enemy), wave.getFirePower());

                    if (SegmentTree != null && (i.getTotalOthers() == 1 || SegmentTreeMelee == null)) {
                        SegmentTree.addObservation(o, false);
                        FlatBin.add(o);
                    } else if (SegmentTreeMelee != null) {
                        SegmentTreeMelee.addObservation(o, false);
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
