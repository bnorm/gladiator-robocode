package kid.Segmentation;

import kid.Data.Robot.*;
import kid.Segmentation.Segmentars.*;

public class Splitter implements java.io.Serializable {

    private static final long serialVersionUID = -325891155039849199L;

    private Node LChild = null;
    private Node HChild = null;

    private Segmentar[] Segmentars;
    private Segmentar Segmentar;

    private boolean canSplit = true;

    public Splitter(Segmentar[] s) {
        Segmentars = s;
        Segmentar = null;
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

    public void printTree(int spaces) {
        String s = "";
        for (int i = 0; i < spaces; i++)
            s += "  ";
        System.out.println(s + Segmentar.getHighBranchString());
        HChild.printTree(spaces);
        System.out.println(s + Segmentar.getLowBranchString());
        LChild.printTree(spaces);
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
        if (!canSplit)
            return false;
        boolean cannotsplit = true;
        for (int s = 0; s < Segmentars.length; s++) {
            if (Segmentars[s].canBranch()) {
                cannotsplit = false;
                break;
            }
        }
        if (cannotsplit) {
            canSplit = false;
            return false;
        }
        Observation[] log = parent.getLog().getList();
        int[] HighTaly = new int[Segmentars.length];
        int[] LowTaly = new int[Segmentars.length];
        int[] DifTaly = new int[Segmentars.length];
        for (int s = 0; s < Segmentars.length; s++) {
            if (!Segmentars[s].canBranch()) {
                DifTaly[s] = Integer.MAX_VALUE;
                continue;
            }
            for (int j = 0; j < log.length; j++) {
                Observation o = log[j];
                if (Segmentars[s].branchLow(o)) {
                    HighTaly[s]++;
                } else {
                    LowTaly[s]++;
                }
            }
            DifTaly[s] = Math.abs(HighTaly[s] - LowTaly[s]);
        }
        int lowdiftaly = parent.size();
        for (int s = 0; s < Segmentars.length; s++) {
            if (DifTaly[s] < lowdiftaly) {
                lowdiftaly = DifTaly[s];
                Segmentar = Segmentars[s];
            }
        }
        if (Segmentar == null)
            return false;
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
        LChild = new Node(low, segmentBin);
        HChild = new Node(high, segmentBin);
        for (int j = 0; j < log.length; j++) {
            Observation o = log[j];
            if (Segmentar.branchLow(o)) {
                LChild.addObservation(o, true);
            } else {
                HChild.addObservation(o, true);
            }
        }
        return true;
    }

}
