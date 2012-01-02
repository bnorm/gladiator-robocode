package kid.Data.DinamicClustering;

import java.awt.Color;
import java.util.*;

import robocode.*;

import kid.*;
import kid.Clustering.*;
import kid.Data.*;
import kid.Data.Robot.*;
import kid.Data.Virtual.DataWave;
import kid.Segmentation.*;

public class GuessFactorClusteringData {

    private boolean DINAMIC_CLUSTERING = false;
    private ClusterManager Cluster = null;
    private transient List WAVES = new ArrayList();

    private AdvancedRobot MyRobot;
    private RobotInfo i;
    private EnemyData Enemy;

    private int length = 50;

    public GuessFactorClusteringData(EnemyData Enemy, AdvancedRobot MyRobot) {
        this.Enemy = Enemy;
        this.MyRobot = MyRobot;
        i = new RobotInfo(MyRobot);
    }

    public void addClusters(Cluster[] Clusters) {
        this.MyRobot.addCustomEvent(new WaveWhatcher());
        WAVES = new ArrayList();
        if (DINAMIC_CLUSTERING)
            return;
        DINAMIC_CLUSTERING = true;
        Cluster = new ClusterManager(Clusters);
    }

    public void updateClusters(double firePower) {
        if ((DINAMIC_CLUSTERING) && Enemy.isAlive()) {
            WAVES.add(new DataWave(MyRobot, Enemy, firePower, getCluster(firePower, length)));
        }
    }

    public double[] getCluster(double FirePower, int max) {
        double[] s = new double[GFBin.numBins];
        if (getFirstWave() == null)
            return s;
        if (DINAMIC_CLUSTERING) {
            Observation o = new Observation(Enemy, MyRobot);
            o.setGFHit(getFirstWave().getGuessFactor(Enemy));
            o.setVirtualWave(new DataWave(MyRobot, Enemy, FirePower, null));
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


    public void drawData(RobocodeGraphicsDrawer g) {
        if (Enemy.isDead() || MyRobot.getOthers() == 0)
            return;
        double sx = 10, sy = 10;
        double px = sx, py = sy;

        double multi = 5;

        double[] sectors = getCluster(2, length);

        for (int s = 0; s < sectors.length * multi; s++) {
            double x = sx + s;
            int index1 = (int) (s / multi), index2 = (int) Math.min((s / multi) + 1,
                                                                    sectors.length - 1);
            double height = Utils.weightedAvg(sectors[index1], index2 - (s / multi),
                                              sectors[index2], (s / multi) - index1);
            double y = Math.max(height, 0) * multi + sy;
            g.setColor(Color.GREEN);

            g.drawLine(px, py, x, y);

            px = x;
            py = y;
        }
    }

    public void drawWaves(RobocodeGraphicsDrawer g) {
        g.setColor(Color.GRAY);
        for (int w = 0; w < WAVES.size(); w++) {
            DataWave wave = (DataWave) WAVES.get(w);
            g.drawArc((int) wave.getStartX(), (int) wave.getStartY(),
                      (int) wave.getDist(MyRobot.getTime()) * 2,
                      (int) wave.getDist(MyRobot.getTime()) * 2,
                      (int) (wave.getHeading() - wave.maxEscapeAngle()),
                      (int) (wave.maxEscapeAngle() * 2));
        }
    }

    private DataWave getFirstWave() {
        try {
            return (DataWave) WAVES.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    private class WaveWhatcher extends Condition {
        public boolean test() {
            for (int w = 0; w < WAVES.size(); w++) {
                DataWave wave = (DataWave) WAVES.get(w);
                if (wave.testHit(Enemy.getRobot(), i.getTime())) {

                    Observation o = wave.getObservation();
                    o.setGFHit(wave.getGuessFactor(Enemy), wave.getFirePower());
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
