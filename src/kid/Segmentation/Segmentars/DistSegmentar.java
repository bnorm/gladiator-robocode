package kid.Segmentation.Segmentars;

import kid.*;
import kid.Data.Robot.*;
import robocode.*;

public class DistSegmentar extends Segmentar {

    private static final double MinDistSegment = 150.0;

    private double HighDist = 0.0;
    private double LowDist = 0.0;

    private double MidDist = 0.0;

    public DistSegmentar(Robot MyRobot) {
        this(Utils.round(Utils.getDist(0, 0, MyRobot.getBattleFieldWidth(),
                                       MyRobot.getBattleFieldHeight()), MinDistSegment), 0);
    }

    public DistSegmentar(double HighDist, double LowDist) {
        this.HighDist = HighDist;
        this.LowDist = LowDist;
        MidDist = Utils.round((HighDist + LowDist) / 2, MinDistSegment);
    }

    public boolean branchHigh(Observation o) {
        return o.getDist() >= MidDist;
    }

    public boolean branchLow(Observation o) {
        return o.getDist() <= MidDist;
    }

    public Segmentar getHighBranch() {
        return new DistSegmentar(HighDist, MidDist);
    }

    public Segmentar getLowBranch() {
        return new DistSegmentar(MidDist, LowDist);
    }

    public boolean canBranch() {
        return (HighDist - MidDist >= MinDistSegment);
    }

    public String getHighBranchString() {
        return "Dist: " + HighDist + " " + MidDist;
    }

    public String getLowBranchString() {
        return "Dist: " + MidDist + " " + LowDist;
    }

}
