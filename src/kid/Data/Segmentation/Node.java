package kid.Data.Segmentation;

import kid.Data.Robot.*;
import kid.Data.Segmentation.Segmentars.*;

public class Node {

    private Leaf leaf;
    private Splitter splitter;
    private Segmentar segmentar;

    public Node(Segmentar s) {
        segmentar = s;
        leaf = new Leaf();
        splitter = null;
    }

    public void addObservation(Observation o, boolean spowning) {
        if (leaf == null) {
            splitter.addObservation(o);
        } else {
            leaf.add(o);
            if (leaf.size() >= 40 && !spowning && segmentar.getCanBranch()) {
                if (splitter == null)
                    splitter = new Splitter(segmentar);
                splitter.split(leaf);
                leaf.discard();
                leaf = null;
            }
        }
    }

    public Bin getBin(Observation o) {
        if (splitter == null)
            return leaf.getBin();
        return splitter.getBin(o);
    }

    public void clear() {
        leaf.clear();
    }
}
