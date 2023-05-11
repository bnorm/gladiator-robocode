package kid.Clustering;

import kid.Utils;
import kid.Data.Robot.Observation;

public abstract class Cluster {

    public double getDiff(Observation o1, Observation o2) {
        return Math.abs(getValue(o1) - getValue(o2));
    }

    public double getDiffSq(Observation o1, Observation o2) {
        return Utils.sqr(getValue(o1) - getValue(o2));
    }

    public abstract double getValue(Observation o);
    public abstract double getMaxDiff();

}
