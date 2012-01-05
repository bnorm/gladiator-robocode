package kid.Data.Segmentation.Segmentars;

import kid.Data.Robot.*;

public class DeltaHeadingSegmentar extends Segmentar {

    private static final double MidDHSegent = 0.5;

    private double HighDH = 0.0;
    private double LowDH = 0.0;
    private double MidDH = 0.0;

    public DeltaHeadingSegmentar(double HighDH, double LowDH) {
        this.HighDH = HighDH;
        this.LowDH = LowDH;
        this.MidDH = (HighDH + LowDH) / 2;
    }

    public boolean branchHigh(Observation o) {
        if (Math.abs(o.getDeltaHeading()) >= MidDH)
            return true;
        return false;
    }

    public boolean branchLow(Observation o) {
        if (Math.abs(o.getDeltaHeading()) <= MidDH)
            return true;
        return false;
    }

    public Segmentar getHighBranch() {
        return new DeltaHeadingSegmentar(MidDH, HighDH);
    }

    public Segmentar getLowBranch() {
        return new DeltaHeadingSegmentar(MidDH, LowDH);
    }

    public boolean getCanBranch() {
        return (Math.abs(HighDH - LowDH) > MidDHSegent);
    }

}
