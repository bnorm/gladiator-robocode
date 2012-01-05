package kid.Data;

import java.util.*;

import kid.*;
import kid.Data.Robot.*;
import kid.Segmentation.*;
import kid.Segmentation.Segmentars.*;
import kid.Data.Virtual.*;
import kid.Managers.*;
import robocode.*;

public class MovementProfile implements java.io.Serializable {

    private static final long serialVersionUID = 141232629373536209L;

    private transient AdvancedRobot MyRobot = null;
    private transient MyRobotsInfo i = null;
    private transient DataManager Data;
    private transient List Waves = new ArrayList();

    private static Node SegmentTree = null;
    private static GFBin Bin = null;

    public MovementProfile() {
    }

    public void startDataProsseser(AdvancedRobot MyRobot, DataManager Data, Segmentar[] Sementars) {
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
        this.Data = Data;

        if (SegmentTree == null && Sementars != null) {
            SegmentTree = new Node(Sementars, new GFBin());
        } else if (Bin == null && Sementars != null) {
            Bin = new GFBin();
        }
    }

    public void startDataProsseser(TeamRobot MyRobot, DataManager Data, Segmentar[] Sementars) {
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
        this.Data = Data;

        if (SegmentTree == null && Sementars != null) {
            SegmentTree = new Node(Sementars, new GFBin());
        } else if (Bin == null && Sementars != null) {
            Bin = new GFBin();
        }
    }

    private long time = -1;
    public void inEvent(Event e) {
        if (time != MyRobot.getTime()) {
            DataProsseser();
        }
        time = MyRobot.getTime();
    }

    public double[] getSectors(EnemyData enemy) {
        double[] s = new double[GFBin.numBins];
        if (getMaxRiskWave() == null)
            return s;
        if (SegmentTree == null) {
            for (int j = 0; j < s.length; j++) {
                s[j] += (Bin.get(j));
            }
            return s;
        }
        GFBin b = new GFBin();
        Observation o = new Observation(MyRobot, enemy);
        o.setGFHit(getMaxRiskWave().getGuessFactor(MyRobot));
        b = (GFBin) SegmentTree.getBin(o);
        for (int j = 0; j < s.length && j < GFBin.numBins; j++) {
            s[j] += (b.get(j));
        }
        return s;
    }

    public EnemyWave[] getMaxRiskWaves() {
        List HighRiskWaves = new ArrayList();
        EnemyWave[] waves = null;
        int i = 0;
        for (int w = 0; w < Waves.size(); w++) {
            EnemyWave wave = (EnemyWave) Waves.get(w);
            HighRiskWaves.add(wave);
            i++;
        }
        waves = new EnemyWave[i];
        for (int w = 0; w < HighRiskWaves.size(); w++) {
            EnemyWave wave = (EnemyWave) HighRiskWaves.get(w);
            if (wave.getFirePower() != 0.0) {
                waves[w] = wave;
            }
        }
        return waves;
    }

    public EnemyWave getMaxRiskWave() {
        EnemyWave wave;
        for (int w = 0; w < Waves.size(); w++) {
            wave = (EnemyWave) Waves.get(w);
            if (wave.getFirePower() != 0.0)
                return wave;
        }
        return new EnemyWave();
    }

    public void drawSectors(RobocodeGraphicsDrawer g, EnemyData enemy) {
        int bestindex = 15;
        double[] Sectors = getSectors(enemy);
        double distspred = MyRobot.getBattleFieldWidth() / (Sectors.length + 1);
        double y = MyRobot.getBattleFieldHeight() - 10 - (10 / 2);
        for (int i = 0; i < Sectors.length; i++) {
            if (Sectors[bestindex] < Sectors[i])
                bestindex = i;
        }
        for (int s = 0; s < Sectors.length; s++) {
            if (s == (Sectors.length - 1) / 2) {
                g.setColor(Colors.YELLOW);
            } else if (s > (Sectors.length - 1) / 2) {
                g.setColor(Colors.GREEN);
            } else {
                g.setColor(Colors.RED);
            }
            g.fillOval((distspred + distspred * s), y, Math.max((Sectors[s] / Sectors[bestindex]) * 10, 2), Math.max(
                    (Sectors[s] / Sectors[bestindex]) * 10, 2));
            g.setColor(Colors.WHITE);
            g.drawString(String.valueOf((int) Sectors[s]), (distspred + distspred * s) - 2, (y - 10));
        }
    }

    public void drawBulletWaves(RobocodeGraphicsDrawer g) {
        EnemyWave wave;
        EnemyWave[] Waves = getMaxRiskWaves();
        long t = MyRobot.getTime();
        for (int w = 0; w < Waves.length; w++) {
            wave = Waves[w];

            double wsx = wave.getStartX(), wsy = wave.getStartY();
            double angle = wave.getHeading();
            double dist = wave.getDist(t);
            double d = wave.getDirection();
            double[] sectors = wave.getMyRobotsSectors();
            int bestindex = 15;
            for (int i = 0; i < sectors.length; i++) {
                if (sectors[bestindex] < sectors[i])
                    bestindex = i;
            }
            for (int s = 0; s < sectors.length; s++) {
                double guessfactor = Utils.getGuessFactor(s, sectors.length);
                double angleOffset = d * guessfactor
                        * Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(wave.getFirePower()));

                if (s == (sectors.length - 1) / 2) {
                    g.setColor(Colors.YELLOW);
                } else if (s > (sectors.length - 1) / 2) {
                    g.setColor(Colors.GREEN);
                } else {
                    g.setColor(Colors.RED);
                }

                g.fillOvalCenter((int) Utils.getX(wsx, dist, angle + angleOffset), (int) Utils.getY(wsy, dist, angle
                        + angleOffset), (int) Math.max(((double) sectors[s] / sectors[bestindex]) * 5, 1), (int) Math
                        .max(((double) sectors[s] / sectors[bestindex]) * 5, 1));
            }
        }
    }

    public void drawRobotDravlePoint(RobocodeGraphicsDrawer g) {
        EnemyWave wave;
        long t = MyRobot.getTime();
        for (int w = 0; w < Waves.size(); w++) {
            wave = (EnemyWave) Waves.get(w);
            if (wave.getFirePower() != 0.0) {

                double angle = Utils.getAngle(wave.getStartX(), wave.getStartY(), i.getX(), i.getY());
                double wx = Utils.getX(wave.getStartX(), wave.getDist(t), angle), wy = Utils.getY(wave.getStartY(),
                        wave.getDist(t), angle);
                double dist = wave.distToImpact(MyRobot);
                double d = wave.getDirection();
                double angleOffset = d * wave.maxEscapeAngle();

                g.setColor(Colors.GREEN);
                g.fillOval((int) Utils.getX(wx, dist, angle + angleOffset), (int) Utils.getY(wy, dist, angle
                        + angleOffset), (int) 5, (int) 5);

                g.setColor(Colors.RED);
                g.fillOval((int) Utils.getX(wx, dist, angle - angleOffset), (int) Utils.getY(wy, dist, angle
                        - angleOffset), (int) 5, (int) 5);

            }
        }
    }

    private void DataProsseser() {
        if (i.getEnemys() > 1)
            return;
        EnemyData[] EnemyData = Data.getEnemys();
        for (int w = 0; w < Waves.size(); w++) {
            EnemyWave wave = (EnemyWave) Waves.get(w);
            if (MyRobot.getEnergy() > 0.0) {
                if (wave.testHit(MyRobot)) {
                    Observation o = wave.getObservation();
                    o.setGFHit(wave.getGuessFactor(MyRobot));
                    if (SegmentTree != null) {
                        SegmentTree.addObservation(o, false);
                    } else {
                        Bin.add(o);
                    }

                    Waves.remove(w);
                    w--;
                }
            }
        }
        if (EnemyData != null) {
            for (int b = 0; b < EnemyData.length; b++) {
                if (EnemyData[b] != null && !EnemyData[b].isDead() && EnemyData[b].didFireBullet()
                        && 3.0 / MyRobot.getGunCoolingRate() < i.getTime()) {
                    Waves.add(new EnemyWave(MyRobot, EnemyData[b], getSectors(EnemyData[b])));
                }
            }
        }
    }

}
