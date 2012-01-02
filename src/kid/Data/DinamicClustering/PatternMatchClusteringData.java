package kid.Data.DinamicClustering;

import robocode.*;

import kid.Clustering.*;
import kid.Data.*;
import kid.Data.PatternMatching.PolarPatternMatcher;
import kid.Data.Robot.*;
import kid.Data.Virtual.DataWave;
import kid.Targeting.Log.PatternMatchingPolar;

public class PatternMatchClusteringData {

    private boolean DINAMIC_CLUSTERING = false;
    private ClusterManager Cluster = null;
    private PatternMatchingPolar Targeting;
    private PolarPatternMatcher PatternMatcher;

    private AdvancedRobot MyRobot;
    private RobotInfo i;
    private EnemyData Enemy;

    private long MaxMatchLegth = 500;
    
    public PatternMatchClusteringData(EnemyData Enemy, AdvancedRobot MyRobot, PolarPatternMatcher PatternMatcher) {
        this.Enemy = Enemy;
        this.MyRobot = MyRobot;
        i = new RobotInfo(MyRobot);
        Targeting = new PatternMatchingPolar(MyRobot);
        this.PatternMatcher = PatternMatcher;
    }

    public void addClusters(Cluster[] Clusters) {
        if (DINAMIC_CLUSTERING)
            return;
        DINAMIC_CLUSTERING = true;
        Cluster = new ClusterManager(Clusters);
    }

    public double getClusterAnlge(double FirePower, int max) {
        double angle = i.AngleTo(Enemy);
        Observation o = new Observation(Enemy, MyRobot);
        o.setVirtualWave(new DataWave(MyRobot, Enemy, FirePower, null));
        Observation[] obs = Cluster.getCluster(max, o);
        if (obs == null) 
            return angle;
        
        int length = 0;
        int best = -1;
        for (int i = 0; i < obs.length; i++) {
            int l = PatternMatcher.getLength(obs[i].getPolarPattern());
            if (l > length) {
                best = i;
                length = l;
                if (l >= MaxMatchLegth)
                    break;
            }
        }
        if (best == -1)
            return angle;
        return Targeting.getTargetingAngle(Enemy, obs[best].getPolarPattern(), FirePower);
    }

}
