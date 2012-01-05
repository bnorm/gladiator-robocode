package kid.Data.Segmentation.Segmentars;

import kid.Data.Robot.*;

public class LateralVelocitySegmentar extends Segmentar {

    private static final double MidVelSegent = 0.5;

    private double HighVel = 0.0;
    private double LowVel = 0.0;
    private double MidVel = 0.0;

    public LateralVelocitySegmentar(double HighVel, double LowVel) {
        this.HighVel = HighVel;
        this.LowVel = LowVel;
        this.MidVel = (HighVel + LowVel) / 2;
    }

    public boolean branchHigh(Observation o) {
        if (o.getLateralVelocity() >= MidVel)
            return true;
        return false;
    }

    public boolean branchLow(Observation o) {
        if (o.getLateralVelocity() <= MidVel)
            return true;
        return false;
    }

    public Segmentar getHighBranch() {
        return new LateralVelocitySegmentar(MidVel, HighVel);
    }

    public Segmentar getLowBranch() {
        return new LateralVelocitySegmentar(MidVel, LowVel);
    }

    public boolean getCanBranch() {
        return (Math.abs(HighVel - LowVel) > MidVelSegent);
    }

}
