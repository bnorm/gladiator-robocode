package kid.Segmentation.Segmentars;

import kid.*;
import kid.Data.Robot.*;

public class VelocityChangeTimeSegmentar extends Segmentar {

    private static final double MidVelSegent = .1;

    private long HighVelTime;
    private long LowVelTime;
    private long MidVelTime;

    public VelocityChangeTimeSegmentar() {
        this(2, 0);
    }

    public VelocityChangeTimeSegmentar(long HighVelTime, long LowVelTime) {
        this.HighVelTime = HighVelTime;
        this.LowVelTime = LowVelTime;
        this.MidVelTime = (long) ((HighVelTime + LowVelTime) / 2);
    }

    public boolean branchHigh(Observation o) {
        double time = Utils.absMin(o.getTimeSinceAccel(), o.getTimeSinceDeccel());
        long bullettime = o.getWave().timeToImpact(o.getX(), o.getY(), o.getWave().getCreationTime());
        return time / bullettime >= MidVelTime;
    }

    public boolean branchLow(Observation o) {
        double time = Utils.absMin(o.getTimeSinceAccel(), o.getTimeSinceDeccel());
        long bullettime = o.getWave().timeToImpact(o.getX(), o.getY(), o.getWave().getCreationTime());
        return time / bullettime <= MidVelTime;
    }

    public Segmentar getHighBranch() {
        return new VelocityChangeTimeSegmentar(HighVelTime, MidVelTime);
    }

    public Segmentar getLowBranch() {
        return new VelocityChangeTimeSegmentar(MidVelTime, LowVelTime);
    }

    public boolean canBranch() {
        return (Math.abs(HighVelTime - LowVelTime) >= MidVelSegent);
    }

    public String getHighBranchString() {
        return "VelChgTime: " + HighVelTime + " " + MidVelTime;
    }

    public String getLowBranchString() {
        return "VelChgTime: " + MidVelTime + " " + LowVelTime;
    }

}
