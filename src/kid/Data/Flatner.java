package kid.Data;

import java.util.*;

import kid.*;
import kid.Data.Robot.*;
import kid.Data.Segmentation.*;
import kid.Data.Segmentation.Segmentars.*;
import kid.Data.Virtual.*;
import kid.Managers.*;
import robocode.*;

public class Flatner implements java.io.Serializable {

    private transient AdvancedRobot MyRobot = null;
    private transient MyRobotsInfo i = null;
    private transient DataManager Data;
    private transient List Waves = new ArrayList();

    private Node[] Segments = null;

    private static boolean HaveStared = false;

    public Flatner() {}

    public void startDataProsseser(AdvancedRobot MyRobot, DataManager Data) {
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
        this.Data = Data;
        MyRobot.addCustomEvent(new DataProsseser());

        double maxdist = Utils.getDist(0, 0, MyRobot.getBattleFieldWidth(), MyRobot.getBattleFieldHeight());
        Segmentar[] seg = {new DistSegmentar(maxdist, 0.0),
                          new WallSegmentar(MyRobot.getBattleFieldWidth(), MyRobot.getBattleFieldHeight(), 25, 0.0),
                          new DeltaHeadingSegmentar(i.getRobotTurnRate(i.MAX_VELOCITY), 0),
                          new AdvancingVelocitySegmentar(8, -8), new LateralVelocitySegmentar(8, 0)
        };

        Segments = new Node[seg.length];
        for (int i = 0; i < Segments.length; i++) {
            Segments[i] = new Node(seg[i]);
        }
    }

    public void startDataProsseser(TeamRobot MyRobot, DataManager Data) {
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
        this.Data = Data;
        MyRobot.addCustomEvent(new DataProsseser());

        double maxdist = Utils.getDist(0, 0, MyRobot.getBattleFieldWidth(), MyRobot.getBattleFieldHeight());
        Segmentar[] seg = {new DistSegmentar(maxdist, 0.0),
                          new WallSegmentar(MyRobot.getBattleFieldWidth(), MyRobot.getBattleFieldHeight(), 25, 0.0),
                          new DeltaHeadingSegmentar(i.getRobotTurnRate(i.MAX_VELOCITY), 0),
                          new AdvancingVelocitySegmentar(8, -8), new LateralVelocitySegmentar(8, 0)
        };

        Segments = new Node[seg.length];
        for (int i = 0; i < Segments.length; i++) {
            Segments[i] = new Node(seg[i]);
        }
    }


    public int[] getSectors(EnemyData enemy) {
        int[] s = new int[Bin.numBins];
        if (getMaxRiskWave() == null)
            return s;
        Bin[] b = new Bin[Segments.length];
        Observation o = new Observation(MyRobot, enemy);
        o.setGFHit(getMaxRiskWave().getGuessFactor(MyRobot));
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

    public void drawSectors(RobocodeGraphicsDrawer g, EnemyData enemy) {
        if (enemy.isDead())
            return;
        double ex = enemy.getX(), ey = enemy.getY();
        double angle = Utils.getAngle(ex, ey, MyRobot.getX(), MyRobot.getY());
        double dist = Utils.getDist(ex, ey, MyRobot.getX(), MyRobot.getY()) + MyRobotsInfo.WIDTH;
        double d = (Utils.sin(MyRobot.getHeading() - angle) * MyRobot.getVelocity() < 0) ? -1 : 1;
        int bestindex = 15;
        int[] Sectors = getSectors(enemy);
        for (int i = 0; i < Sectors.length; i++) {
            if (Sectors[bestindex] < Sectors[i])
                bestindex = i;
        }
        for (int s = 0; s < Sectors.length; s++) {
            double guessfactor = (double) (s - (Sectors.length - 1) / 2) / ((Sectors.length - 1) / 2);
            double angleOffset = d * guessfactor * Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(2));
            if (s == (Sectors.length - 1) / 2) {
                g.setColor(Colors.YELLOW);
            } else if (s > (Sectors.length - 1) / 2) {
                g.setColor(Colors.GREEN);
            } else {
                g.setColor(Colors.RED);
            }
            g.fillOval((int) Utils.getX(ex, dist, angle + angleOffset), (int) Utils.getY(ey, dist, angle + angleOffset),
                       (int) Math.max(((double) Sectors[s] / Sectors[bestindex]) * 10, 1),
                       (int) Math.max(((double) Sectors[s] / Sectors[bestindex]) * 10, 1));
        }
    }

    public EnemyWave[] getMaxRiskWaves() {
        List HighRiskWaves = new ArrayList();
        EnemyWave[] waves = null;
        int i = 0;
        for (int w = 0; w < Waves.size(); w++) {
            EnemyWave wave = (EnemyWave) Waves.get(w);
            if (wave.getFirePower() != 0.0) {
                HighRiskWaves.add(wave); i++;
            }
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
        return null;
    }

    public void drawBulletWaves(RobocodeGraphicsDrawer g) {
        EnemyWave wave;
        long t = MyRobot.getTime();
        for (int w = 0; w < Waves.size(); w++) {
            wave = (EnemyWave) Waves.get(w);
            if (wave.getFirePower() != 0.0) {

                double wsx = wave.getStartX(), wsy = wave.getStartY();
                double angle = wave.getHeading();
                double dist = wave.getDist(t);
                double d = wave.getDirection();
                int[] sectors = wave.getMyRobotsSectors();
                int bestindex = 15;
                for (int i = 0; i < sectors.length; i++) {
                    if (sectors[bestindex] < sectors[i])
                        bestindex = i;
                }
                for (int s = 0; s < sectors.length; s++) {
                    double guessfactor = Utils.getGuessFactor(s, sectors.length);
                    double angleOffset = d * guessfactor *
                                         Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(wave.getFirePower()));

                    if (s == (sectors.length - 1) / 2) {
                        g.setColor(Colors.YELLOW);
                    } else if (s > (sectors.length - 1) / 2) {
                        g.setColor(Colors.GREEN);
                    } else {
                        g.setColor(Colors.RED);
                    }

                    g.fillOval((int) Utils.getX(wsx, dist, angle + angleOffset),
                               (int) Utils.getY(wsy, dist, angle + angleOffset),
                               (int) Math.max(((double) sectors[s] / sectors[bestindex]) * 5, 1),
                               (int) Math.max(((double) sectors[s] / sectors[bestindex]) * 5, 1));
                }
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
                double wx = Utils.getX(wave.getStartX(), wave.getDist(t), angle),
                        wy = Utils.getY(wave.getStartY(), wave.getDist(t), angle);
                double dist = wave.distToImpact(MyRobot);
                double d = wave.getDirection();
                double angleOffset = d * wave.maxEscapeAngle();

                g.setColor(Colors.GREEN);
                g.fillOval((int) Utils.getX(wx, dist, angle + angleOffset),
                           (int) Utils.getY(wy, dist, angle + angleOffset), (int) 5, (int) 5);

                g.setColor(Colors.RED);
                g.fillOval((int) Utils.getX(wx, dist, angle - angleOffset),
                           (int) Utils.getY(wy, dist, angle - angleOffset), (int) 5, (int) 5);

            }
        }
    }


    private class DataProsseser extends Condition {
        public boolean test() {
            if (i.getEnemys() > 1)
                return false;
            if (!HaveStared)
                MyRobot.out.println("Started Flatner");
            HaveStared = true;

            EnemyData[] EnemyData = Data.getEnemys();
            if (EnemyData != null) {
                for (int b = 0; b < EnemyData.length; b++) {
                    if (EnemyData[b] != null && !EnemyData[b].isDead() && EnemyData[b].didFireBullet()) {
                        Waves.add(new EnemyWave(MyRobot, EnemyData[b], getSectors(EnemyData[b]),
                                new Observation(MyRobot, EnemyData[b])));
                    }
                }
            }
            for (int w = 0; w < Waves.size(); w++) {
                EnemyWave wave = (EnemyWave) Waves.get(w);
                if (MyRobot.getEnergy() > 0.0)
                    if (wave.testHit(MyRobot)) {

                        Observation o = wave.getObservation();
                        o.setGFHit(wave.getGuessFactor(MyRobot));
                        for (int s = 0; s < Segments.length; s++) {
                            Segments[s].addObservation(o, false);
                        }

                        Waves.remove(w);
                        w--;
                    }
            }
            return false;
        }
    }

}
