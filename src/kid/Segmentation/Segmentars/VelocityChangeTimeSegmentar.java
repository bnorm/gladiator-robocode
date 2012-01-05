package kid.Segmentation.Segmentars;

import kid.*;
import kid.Data.Robot.*;

public class VelocityChangeTimeSegmentar extends Segmentar {

    private static final double MidVelSegent = 1;

    private double HighVel = 0.0;
    private double LowVel = 0.0;
    private double MidVel = 0.0;

    public VelocityChangeTimeSegmentar() {
        this(5, 0);
    }

    public VelocityChangeTimeSegmentar(double HighVel, double LowVel) {
        this.HighVel = HighVel;
        this.LowVel = LowVel;
        this.MidVel = (HighVel + LowVel) / 2;
    }

    public boolean branchHigh(Observation o) {
        if (Utils.absMin(o.getTimeSinceAccel(), o.getTimeSinceDeccel()) >= MidVel)
            return true;
        return false;
    }

    public boolean branchLow(Observation o) {
        if (Utils.absMin(o.getTimeSinceAccel(), o.getTimeSinceDeccel()) <= MidVel)
            return true;
        return false;
    }

    public Segmentar getHighBranch() {
        return new VelocityChangeTimeSegmentar(HighVel, MidVel);
    }

    public Segmentar getLowBranch() {
        return new VelocityChangeTimeSegmentar(MidVel, LowVel);
    }

    public boolean getCanBranch() {
        return (Math.abs(HighVel - LowVel) > MidVelSegent);
    }

    public String getHighBranchString() {
        return "VelTime: " + HighVel + " " + MidVel;
    }

    public String getLowBranchString() {
        return "VelTime: " + MidVel + " " + LowVel;
    }

}
