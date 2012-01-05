package kid.Segmentation.Segmentars;

import kid.Data.Robot.*;
import kid.Data.MyRobotsInfo;

public class AdvancingVelocitySegmentar extends Segmentar {

    private static final double MidVelSegent = .25;

    private double HighVel = 0.0;
    private double LowVel = 0.0;
    private double MidVel = 0.0;

    public AdvancingVelocitySegmentar() {
        this(MyRobotsInfo.MAX_VELOCITY, MyRobotsInfo.MIN_VELOCITY);
    }

    public AdvancingVelocitySegmentar(double HighVel, double LowVel) {
        this.HighVel = HighVel;
        this.LowVel = LowVel;
        this.MidVel = (HighVel + LowVel) / 2;
    }

    public boolean branchHigh(Observation o) {
        if (o.getAdvancingVelocity() >= MidVel)
            return true;
        return false;
    }

    public boolean branchLow(Observation o) {
        if (o.getAdvancingVelocity() <= MidVel)
            return true;
        return false;
    }

    public Segmentar getHighBranch() {
        return new AdvancingVelocitySegmentar(HighVel, MidVel);
    }

    public Segmentar getLowBranch() {
        return new AdvancingVelocitySegmentar(MidVel, LowVel);
    }

    public boolean getCanBranch() {
        return (Math.abs(HighVel - LowVel) > MidVelSegent);
    }

    public String getHighBranchString() {
        return "AdvVel: " + HighVel + " " + MidVel;
    }

    public String getLowBranchString() {
        return "AdvVel: " + MidVel + " " + LowVel;
    }

}
