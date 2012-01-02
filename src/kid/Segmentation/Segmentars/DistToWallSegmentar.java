package kid.Segmentation.Segmentars;

import kid.*;
import kid.Data.Robot.*;
import robocode.*;

public class DistToWallSegmentar extends Segmentar {

    private static final double MinDistSegment = .1;

    private double HighDist = 0.0;
    private double LowDist = 0.0;

    private double MidDist = 0.0;

    public DistToWallSegmentar(Robot MyRobot) {
        this(Utils.round(2, MinDistSegment), 0);
    }

    public DistToWallSegmentar(double HighDist, double LowDist) {
        this.HighDist = HighDist;
        this.LowDist = LowDist;
        MidDist = Utils.round((HighDist + LowDist) / 2, MinDistSegment);
    }

    public boolean branchHigh(Observation o) {
        return Utils.atan(o.getDistToWall() / o.getDist()) / o.getWave().maxEscapeAngle() >= MidDist;
    }

    public boolean branchLow(Observation o) {
        return Utils.atan(o.getDistToWall() / o.getDist()) / o.getWave().maxEscapeAngle() <= MidDist;
    }

    public Segmentar getHighBranch() {
        return new DistToWallSegmentar(HighDist, MidDist);
    }

    public Segmentar getLowBranch() {
        return new DistToWallSegmentar(MidDist, LowDist);
    }

    public boolean canBranch() {
        return (HighDist - MidDist >= MinDistSegment);
    }

    public String getHighBranchString() {
        return "DistToWall: " + HighDist + " " + MidDist;
    }

    public String getLowBranchString() {
        return "DistToWall: " + MidDist + " " + LowDist;
    }

}
