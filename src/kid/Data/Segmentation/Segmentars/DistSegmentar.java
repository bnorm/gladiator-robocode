package kid.Data.Segmentation.Segmentars;

import kid.Data.Robot.*;

public class DistSegmentar extends Segmentar {

    private static final double MinDistSegent = 25.0;

    private double HighDist = 0.0;
    private double LowDist = 0.0;

    private double MidDist = 0.0;

    public DistSegmentar(double HighDist, double LowDist) {
        this.HighDist = HighDist;
        this.LowDist = LowDist;
        MidDist = (HighDist + LowDist) / 2;
    }

    public boolean branchHigh(Observation o) {
        if (o.getDist() >= MidDist)
            return true;
        return false;
    }

    public boolean branchLow(Observation o) {
        if (o.getDist() <= MidDist)
            return true;
        return false;
    }

    public Segmentar getHighBranch() {
        return new DistSegmentar(HighDist, MidDist);
    }

    public Segmentar getLowBranch() {
        return new DistSegmentar(MidDist, LowDist);
    }

    public boolean getCanBranch() {
        return (HighDist - LowDist > MinDistSegent);
    }

}
