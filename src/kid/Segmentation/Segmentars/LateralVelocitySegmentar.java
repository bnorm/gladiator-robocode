package kid.Segmentation.Segmentars;

import kid.Utils;
import kid.Data.Robot.*;
import kid.Data.RobotInfo;

public class LateralVelocitySegmentar extends Segmentar {

    private static final double MidVelSegment = 2;

    private double HighVel = 0.0;
    private double LowVel = 0.0;
    private double MidVel = 0.0;

    public LateralVelocitySegmentar() {
        this(Utils.round(RobotInfo.MAX_VELOCITY, MidVelSegment), 0);
    }

    public LateralVelocitySegmentar(double HighVel, double LowVel) {
        this.HighVel = HighVel;
        this.LowVel = LowVel;
        this.MidVel = Utils.round((HighVel + LowVel) / 2, MidVelSegment);
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
        return (Math.abs(HighVel - MidVel) >= MidVelSegment);
    }

    public String getHighBranchString() {
        return "LatVel: " + HighVel + " " + MidVel;
    }

    public String getLowBranchString() {
        return "LatVel: " + MidVel + " " + LowVel;
    }

}
