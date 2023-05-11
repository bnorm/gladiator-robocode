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
    private transient TeammateData[] EnemyScans = null;
    private transient int EnemyScansPosition = 0;
    private transient MyRobotsInfo i = null;
    private transient DataManager Data;
    private transient List Waves = new ArrayList();
    private transient double lastEnemyFirePower = 2;

    private static int LengthOfRollingGF = 50;
    private static double[] RollingGF = null;
    private transient int RollingGFPosition = 0;

    private static Node[] SegmentTree = null;
    private static GFBin FlatBin = null;

    public MovementProfile() {
    }

    public void startDataProsseser(AdvancedRobot MyRobot, DataManager Data, Segmentar[] Sementars) {
        this.MyRobot = MyRobot;
        EnemyScans = new TeammateData[5];
        for (int i = 0; i < EnemyScans.length; i++)
            EnemyScans[i] = new TeammateData(MyRobot);
        EnemyScansPosition = EnemyScans.length;
        i = new MyRobotsInfo(MyRobot);
        this.Data = Data;

        if (SegmentTree == null && Sementars != null) {
            SegmentTree = new Node[i.getOthers()];
            for (int e = 0; e < SegmentTree.length; e++) {
                SegmentTree[e] = new Node(Sementars, new GFBin());
            }
            RollingGF = new double[LengthOfRollingGF];
        } else if (FlatBin == null) {
            FlatBin = new GFBin();
            RollingGF = new double[LengthOfRollingGF];
        }
    }

    public void startDataProsseser(TeamRobot MyRobot, DataManager Data, Segmentar[] Sementars) {
        startDataProsseser((AdvancedRobot) MyRobot, Data, Sementars);
        i = new MyRobotsInfo(MyRobot);
    }

    private long time = -1;
    public void inEvent(Event e) {
        if (MyRobot == null || i.getOthers() > 1)
            return;

        if (time != i.getTime()) {
            DataProsseser();
            TeammateData old = new TeammateData(EnemyScans[(EnemyScansPosition - 1) % EnemyScans.length]);
            old.updateItemFromTeammate(new TeammateData(MyRobot));
            EnemyScans[EnemyScansPosition % EnemyScans.length] = old;
            EnemyScansPosition++;
            if (EnemyScansPosition > EnemyScans.length * 2)
                EnemyScansPosition -= EnemyScans.length;
        }
        time = i.getTime();

        if (e instanceof HitByBulletEvent) {
            HitByBulletEvent HBBE = (HitByBulletEvent) e;
            EnemyWave[] waves = getBulletWaves();
            if (waves != null) {
                EnemyWave wave = new EnemyWave();
                Bullet b = HBBE.getBullet();
                double dist = Double.POSITIVE_INFINITY;
                for (int w = 0; w < waves.length; w++) {
                    double wavedist = Math.abs(waves[w].distToImpact(b.getX(), b.getY(), time));
                    if (wavedist < dist && Math.abs(b.getPower() - waves[w].getFirePower()) < .01) {
                        dist = wavedist;
                        wave = waves[w];
                    }
                }
                Observation o = wave.getObservation();
                if (o != null) {
                    o.setGFHit(wave.getGuessFactor(b.getX(), b.getY()), wave.getFirePower());
                    if (SegmentTree != null) {
                        SegmentTree[Data.getEnemyNum(Data.getColsestEnemy().getName())].addObservation(o, false);
                    } else {
                        FlatBin.add(o);
                    }
                }
            }
        } else if (e instanceof BulletHitBulletEvent) {
            BulletHitBulletEvent BHBE = (BulletHitBulletEvent) e;
            EnemyWave[] waves = getBulletWaves();
            if (waves != null) {
                EnemyWave wave = new EnemyWave();
                Bullet b = BHBE.getHitBullet();
                double dist = Double.POSITIVE_INFINITY;
                for (int w = 0; w < waves.length; w++) {
                    double wavedist = Math.abs(waves[w].distToImpact(b.getX(), b.getY(), time));
                    if (wavedist < dist && Math.abs(b.getPower() - waves[w].getFirePower()) < .01) {
                        dist = wavedist;
                        wave = waves[w];
                    }
                }
                Observation o = wave.getObservation();
                if (o != null) {
                    o.setGFHit(wave.getGuessFactor(b.getX(), b.getY()), wave.getFirePower());
                    if (SegmentTree != null) {
                        SegmentTree[Data.getEnemyNum(Data.getColsestEnemy().getName())].addObservation(o, false);
                    } else {
                        FlatBin.add(o);
                    }
                }
            }
        }
    }

    public double[] getSectors(EnemyData enemy, double FirePower) {
        double[] s = new double[GFBin.numBins];
        for (int i = 0; i < s.length; i++) {
            s[i] = 0;
        }
        if (getBulletWaves() == null || Data.getEnemyNum(enemy.getName()) == -1)
            return s;
        if (SegmentTree == null) {
            for (int j = 0; j < s.length; j++) {
                s[j] += (FlatBin.get(j));
            }
            return s;
        }
        TeammateData myrobot = EnemyScans[(EnemyScansPosition - 3) % EnemyScans.length];
        myrobot.updateItemFromTeammate(EnemyScans[(EnemyScansPosition - 2) % EnemyScans.length]);
        GFBin b = new GFBin();
        Observation o = new Observation(myrobot, enemy, MyRobot);
        o.setVirtualWave(new EnemyWave(myrobot, MyRobot, enemy, null));
        b = (GFBin) SegmentTree[Data.getEnemyNum(enemy.getName())].getBin(o);
        for (int j = 0; j < s.length && j < GFBin.numBins; j++) {
            s[j] += (b.get(j));
        }
        return s;
    }

    public double[] getRollingGF() {
        return (double[]) RollingGF.clone();
    }

    public EnemyWave[] getBulletWaves() {
        List BulletWaves = new ArrayList();
        EnemyWave[] waves = null;
        double mx = i.getX(), my = i.getY();
        long t = MyRobot.getTime();
        int i = 0;
        for (int w = 0; w < Waves.size(); w++) {
            EnemyWave wave = (EnemyWave) Waves.get(w);
            if (wave.getFirePower() != 0.0 && !wave.testHit(mx, my, t)) {
                BulletWaves.add(wave);
                i++;
            }
        }
        waves = new EnemyWave[i];
        for (int w = 0; w < BulletWaves.size(); w++) {
            EnemyWave wave = (EnemyWave) BulletWaves.get(w);
            waves[w] = wave;
        }
        return waves;
    }

    public EnemyWave getClosestWave() {
        EnemyWave ClosestWave = null;
        long time = Long.MAX_VALUE;
        for (int w = 0; w < Waves.size(); w++) {
            EnemyWave wave = (EnemyWave) Waves.get(w);
            long t = wave.timeToImpact(MyRobot);
            if (wave.getFirePower() != 0.0 && t < time) {
                time = t;
                ClosestWave = wave;
            }
        }
        return ClosestWave;
    }

    public void drawSectors(RobocodeGraphicsDrawer g, EnemyData enemy) {
        if (i == null || i.getOthers() > 1)
            return;
        double HighestHitCount = 0;
        double[] Sectors = getSectors(enemy, lastEnemyFirePower);
        double distspred = MyRobot.getBattleFieldWidth() / (Sectors.length + 1);
        double y = MyRobot.getBattleFieldHeight() - 10 - (10 / 2);
        for (int i = 0; i < Sectors.length; i++) {
            if (HighestHitCount < Sectors[i])
                HighestHitCount = Sectors[i];
        }
        for (int s = 0; s < Sectors.length; s++) {
            if (s == (Sectors.length - 1) / 2) {
                g.setColor(Colors.YELLOW);
            } else if (s > (Sectors.length - 1) / 2) {
                g.setColor(Colors.GREEN);
            } else {
                g.setColor(Colors.RED);
            }
            g.fillOval((distspred + distspred * s), y, Math.max((Sectors[s] / HighestHitCount) * 10, 2), Math.max(
                    (Sectors[s] / HighestHitCount) * 10, 2));
            g.setColor(Colors.WHITE);
            g.drawString(String.valueOf((int) Sectors[s]), (distspred + distspred * s) - 2, (y - 10));
        }
    }
    public void drawBulletWaves(RobocodeGraphicsDrawer g) {
        drawBulletWaves(g, false);
    }
    public void drawBulletWaves(RobocodeGraphicsDrawer g, boolean with_RollingGF) {
        if (i == null || i.getOthers() > 1)
            return;
        EnemyWave wave;
        EnemyWave[] Waves = getBulletWaves();
        long t = MyRobot.getTime();
        for (int w = 0; w < Waves.length; w++) {
            wave = Waves[w];

            double wsx = wave.getStartX(), wsy = wave.getStartY();
            double angle = wave.getHeading();
            double dist = wave.getDist(t);
            double d = wave.getDirection();
            double[] sectors = wave.getSectors();
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

    public void printTree(EnemyData enemy) {
        if (Data.getEnemyNum(enemy.getName()) != -1) {
            System.out.println(enemy.getName() + "'s view of my movement profile");
            SegmentTree[Data.getEnemyNum(enemy.getName())].printTree();
        }
    }

    private void DataProsseser() {
        if (i.getOthers() > 1 || MyRobot.getEnergy() == 0.0)
            return;
        double mx = i.getX(), my = i.getY();
        long t = MyRobot.getTime();
        for (int w = 0; w < Waves.size(); w++) {
            EnemyWave wave = (EnemyWave) Waves.get(w);
            if (wave.testHit(mx, my, t)) {
                if (!wave.testHit(mx, my, t - 1)) {
                    RollingGF[RollingGFPosition++ % LengthOfRollingGF] = wave.getGuessFactor(MyRobot);
                    if (RollingGFPosition > LengthOfRollingGF * 2)
                        RollingGFPosition -= LengthOfRollingGF;

                    // Observation o = wave.getObservation();
                    // o.setGFHit(wave.getGuessFactor(MyRobot), .01);
                    // if (SegmentTree != null) {
                    // SegmentTree[Data.getEnemyNum(Data.getColsestEnemy().getName())].addObservation(o,
                    // false);
                    // } else {
                    // Bin.add(o);
                    // }
                }

                if (wave.testHit(mx, my, t - 2)) {
                    Waves.remove(w);
                    w--;
                }
            }
        }
        EnemyData[] EnemyData = Data.getEnemys();
        if (EnemyData != null) {
            TeammateData myrobot = EnemyScans[(EnemyScansPosition - 2) % EnemyScans.length];
            for (int b = 0; b < EnemyData.length; b++) {
                if (EnemyData[b] != null && !EnemyData[b].isDead() && EnemyData[b].didFireBullet()
                        && 3.0 / MyRobot.getGunCoolingRate() < t) {
                    lastEnemyFirePower = Math.abs(EnemyData[b].getDeltaEnergy());
                    Waves.add(new EnemyWave(myrobot, MyRobot, EnemyData[b],
                            getSectors(EnemyData[b], lastEnemyFirePower)));
                }
            }
        }
    }

}
