package kid.Segmentation.Segmentars;

import kid.Data.*;
import kid.Data.Robot.Observation;

public class DeltaVelocitySegmentar extends Segmentar {

    private static final double MidVelSegent = 1.0;

    private double HighVel = 0.0;
    private double LowVel = 0.0;
    private double MidVel = 0.0;

    public DeltaVelocitySegmentar() {
        this(MyRobotsInfo.DECCELERATION + 1, 0);
    }

    public DeltaVelocitySegmentar(double HighVel, double LowVel) {
        this.HighVel = HighVel;
        this.LowVel = LowVel;
        this.MidVel = (int) ((HighVel + LowVel) / 2);
    }

    public boolean branchHigh(Observation o) {
        return (Math.abs(o.getDeltaVelocity()) >= MidVel);
    }

    public boolean branchLow(Observation o) {
        return (Math.abs(o.getDeltaVelocity()) <= MidVel);
    }

    public Segmentar getHighBranch() {
        return new DeltaVelocitySegmentar(HighVel, MidVel);
    }

    public Segmentar getLowBranch() {
        return new DeltaVelocitySegmentar(MidVel, LowVel);
    }

    public boolean canBranch() {
        return (Math.abs(HighVel - LowVel) >= MidVelSegent);
    }

    public String getHighBranchString() {
        return "DeltaVel: " + HighVel + " " + MidVel;
    }

    public String getLowBranchString() {
        return "DeltaVel: " + MidVel + " " + LowVel;
    }

}
