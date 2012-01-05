package kid.Data.Segmentation;

import kid.Data.Robot.*;
import kid.Data.Segmentation.Segmentars.*;

public class Splitter implements java.io.Serializable {

    private Node LChild;
    private Node HChild;

    private Segmentar Segmentar;

    public Splitter(Segmentar s) {
        Segmentar = s;
        LChild = new Node(s.getLowBranch());
        HChild = new Node(s.getHighBranch());
    }

    public void addObservation(Observation o) {
        if (Segmentar.branchHigh(o))
            HChild.addObservation(o, false);
        LChild.addObservation(o, false);
    }

    public Bin getBin(Observation o) {
        if (Segmentar.branchHigh(o))
            return HChild.getBin(o);
        return LChild.getBin(o);
    }

    public void split(Leaf parent) {
        Log log = parent.getLog();
        for (int j = 0; j < log.size(); j++) {
            Observation o = (Observation) log.get(j);
            if (Segmentar.branchLow(o)) {
                LChild.addObservation(o, true);
            } else {
                HChild.addObservation(o, true);
            }
        }
    }

}
