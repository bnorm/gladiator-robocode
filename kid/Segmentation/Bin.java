package kid.Segmentation;

import kid.Data.Robot.Observation;

public abstract class Bin {

    public abstract void add(Observation o);

    public abstract Bin getNewBin();

    public abstract void clear();

    public abstract boolean canSplit();

}
