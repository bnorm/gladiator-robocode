package kid.Segmentation.Segmentars;

import kid.Utils;
import kid.Data.Robot.*;

public class BearingSegmentar extends Segmentar {

    private static final double MidDHSegent = 5;

    private double HighBear = 0.0;
    private double LowBear = 0.0;
    private double MidBear = 0.0;

    public BearingSegmentar() {
        this(90.0, 0.0);
    }

    public BearingSegmentar(double HighDH, double LowDH) {
        this.HighBear = HighDH;
        this.LowBear = LowDH;
        this.MidBear = (HighDH + LowDH) / 2;
    }

    public boolean branchHigh(Observation o) {
        return Math.abs(Utils.relative(o.getAngleToEnemy() - o.getHeading()) - 90) >= MidBear;
    }

    public boolean branchLow(Observation o) {
        return Math.abs(Utils.relative(o.getAngleToEnemy() - o.getHeading()) - 90) <= MidBear;
    }

    public Segmentar getHighBranch() {
        return new BearingSegmentar(HighBear, MidBear);
    }

    public Segmentar getLowBranch() {
        return new BearingSegmentar(MidBear, LowBear);
    }

    public boolean getCanBranch() {
        return (Math.abs(HighBear - LowBear) > MidDHSegent);
    }

    public String getHighBranchString() {
        return "DeltaHead: " + HighBear + " " + MidBear;
    }

    public String getLowBranchString() {
        return "DeltaHead: " + MidBear + " " + LowBear;
    }

}
