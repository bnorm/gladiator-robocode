package kid.Segmentation.Segmentars;

import kid.*;
import kid.Data.Robot.*;

public class VelocityChangeTimeSegmentar extends Segmentar {

    private static final double MidVelSegent = .2;

    private double HighVelTime;
    private double LowVelTime;
    private double MidVelTime;
    
    private int others = 0;

    public VelocityChangeTimeSegmentar() {
        this(Utils.round(2, MidVelSegent), 0);
    }

    public VelocityChangeTimeSegmentar(double HighVelTime, double LowVelTime) {
        this.HighVelTime = HighVelTime;
        this.LowVelTime = LowVelTime;
        this.MidVelTime = Utils.round((HighVelTime + LowVelTime) / 2, MidVelSegent);
        others = 1;
    }

    public boolean branchHigh(Observation o) {
        others = o.getOthers();
        double time = Utils.absMin(o.getTimeSinceAccel(), o.getTimeSinceDeccel());
        double bullettime = o.getWave().timeToImpact(o.getX(), o.getY(), o.getWave().getCreationTime());
        return time / bullettime >= MidVelTime;
    }

    public boolean branchLow(Observation o) {
        others = o.getOthers();
        double time = Utils.absMin(o.getTimeSinceAccel(), o.getTimeSinceDeccel());
        double bullettime = o.getWave().timeToImpact(o.getX(), o.getY(), o.getWave().getCreationTime());
        return time / bullettime <= MidVelTime;
    }

    public Segmentar getHighBranch() {
        return new VelocityChangeTimeSegmentar(HighVelTime, MidVelTime);
    }

    public Segmentar getLowBranch() {
        return new VelocityChangeTimeSegmentar(MidVelTime, LowVelTime);
    }

    public boolean canBranch() {
        return (Math.abs(HighVelTime - LowVelTime) >= 2 * MidVelSegent) && others < 2;
    }

    public String getHighBranchString() {
        return "VelChgTime: " + HighVelTime + " " + MidVelTime;
    }

    public String getLowBranchString() {
        return "VelChgTime: " + MidVelTime + " " + LowVelTime;
    }

}
