package kid.Segmentation.Segmentars;

import kid.*;
import kid.Data.Robot.*;
import robocode.*;

public class DistToWallSegmentar extends Segmentar {

    private static final double MinDistSegent = .1;

    private double HighDist = 0.0;
    private double LowDist = 0.0;

    private double MidDist = 0.0;

    public DistToWallSegmentar(Robot MyRobot) {
        this(1, 0);
    }

    public DistToWallSegmentar(double HighDist, double LowDist) {
        this.HighDist = HighDist;
        this.LowDist = LowDist;
        MidDist = (HighDist + LowDist) / 2;
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
        return (HighDist - MidDist >= MinDistSegent);
    }

    public String getHighBranchString() {
        return "DistToWall: " + HighDist + " " + MidDist;
    }

    public String getLowBranchString() {
        return "DistToWall: " + MidDist + " " + LowDist;
    }

}
