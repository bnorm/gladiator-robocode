package kid.Segmentation.Segmentars;

import kid.Data.Robot.*;
import kid.Data.MyRobotsInfo;

public class LateralVelocitySegmentar extends Segmentar {

    private static final double MidVelSegent = 1;

    private double HighVel = 0.0;
    private double LowVel = 0.0;
    private double MidVel = 0.0;

    public LateralVelocitySegmentar() {
        this(MyRobotsInfo.MAX_VELOCITY, 0);
    }

    public LateralVelocitySegmentar(double HighVel, double LowVel) {
        this.HighVel = HighVel;
        this.LowVel = LowVel;
        this.MidVel = (HighVel + LowVel) / 2;
    }

    public boolean branchHigh(Observation o) {
        return Math.abs(o.getLateralVelocity()) >= MidVel;
    }

    public boolean branchLow(Observation o) {
        return Math.abs(o.getLateralVelocity()) <= MidVel;
    }

    public Segmentar getHighBranch() {
        return new LateralVelocitySegmentar(HighVel, MidVel);
    }

    public Segmentar getLowBranch() {
        return new LateralVelocitySegmentar(MidVel, LowVel);
    }

    public boolean canBranch() {
        return (Math.abs(HighVel - MidVel) >= MidVelSegent);
    }

    public String getHighBranchString() {
        return "LatVel: " + HighVel + " " + MidVel;
    }

    public String getLowBranchString() {
        return "LatVel: " + MidVel + " " + LowVel;
    }

}
