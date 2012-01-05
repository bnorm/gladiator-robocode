package kid.Segmentation;

import kid.Data.Robot.*;
import kid.Segmentation.Segmentars.*;

public class Splitter implements java.io.Serializable {

    private static final long serialVersionUID = -325891155039849199L;

    private Node LChild;
    private Node HChild;

    private Segmentar[] Segmentars;
    private Segmentar Segmentar;

    public Splitter(Segmentar[] s) {
        Segmentars = s;
    }

    public void addObservation(Observation o) {
        if (Segmentar.branchHigh(o)) {
            HChild.addObservation(o, false);
        } else {
            LChild.addObservation(o, false);
        }
    }

    public void printObservation(Observation o) {
        if (Segmentar.branchHigh(o)) {
            System.out.println(Segmentar.getHighBranchString());
            HChild.printObservation(o);
        } else {
            System.out.println(Segmentar.getLowBranchString());
            LChild.printObservation(o);
        }
    }

    public Bin getBin(Observation o) {
        if (Segmentar.branchHigh(o))
            return HChild.getBin(o);
        return LChild.getBin(o);
    }

    public Log getLog(Observation o) {
        if (Segmentar.branchHigh(o))
            return HChild.getLog(o);
        return LChild.getLog(o);
    }

    public boolean split(Leaf parent, Bin segmentBin) {
        boolean cannotsplit = true;
        for (int s = 0; s < Segmentars.length; s++) {
            if (Segmentars[s].getCanBranch()) {
                cannotsplit = false;
                break;
            }
        }
        if (cannotsplit)
            return false;
        Log log = parent.getLog();
        int[] HighTaly = new int[Segmentars.length];
        int[] LowTaly = new int[Segmentars.length];
        int[] DifTaly = new int[Segmentars.length];
        for (int s = 0; s < Segmentars.length; s++) {
            for (int j = 0; j < log.size(); j++) {
                Observation o = (Observation) log.get(j);
                if (Segmentars[s].branchLow(o)) {
                    HighTaly[s]++;
                } else {
                    LowTaly[s]++;
                }
            }
            DifTaly[s] = Math.abs(HighTaly[s] - LowTaly[s]);
            if (!Segmentars[s].getCanBranch()) {
                DifTaly[s] = Integer.MAX_VALUE;
            }
        }
        int lowdiftaly = Integer.MAX_VALUE;
        for (int s = 0; s < Segmentars.length; s++) {
            if (DifTaly[s] < lowdiftaly) {
                lowdiftaly = DifTaly[s];
                Segmentar = Segmentars[s];
            }
        }
        Segmentar[] high = new Segmentar[Segmentars.length];
        Segmentar[] low = new Segmentar[Segmentars.length];
        for (int s = 0; s < Segmentars.length; s++) {
            if (Segmentar == Segmentars[s]) {
                high[s] = Segmentar.getHighBranch();
                low[s] = Segmentar.getLowBranch();
            } else {
                high[s] = Segmentars[s];
                low[s] = Segmentars[s];
            }
        }
        LChild = new Node(high, segmentBin);
        HChild = new Node(low, segmentBin);
        for (int j = 0; j < log.size(); j++) {
            Observation o = (Observation) log.get(j);
            if (Segmentar.branchLow(o)) {
                LChild.addObservation(o, true);
            } else {
                HChild.addObservation(o, true);
            }
        }
        return true;
    }

}
