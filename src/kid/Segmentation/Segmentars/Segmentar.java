package kid.Segmentation.Segmentars;

import kid.Data.Robot.Observation;

public abstract class Segmentar {

    public abstract boolean branchHigh(Observation o);

    public abstract boolean branchLow(Observation o);

    public abstract Segmentar getHighBranch();

    public abstract Segmentar getLowBranch();

    public abstract boolean getCanBranch();

}
