package kid.Segmentation;

import kid.Data.Robot.*;
import kid.Segmentation.Segmentars.*;

public class Node {

    private Leaf leaf;
    private Splitter splitter;

    private Segmentar segmentar;
    private Bin segmentBin;

    public Node(Segmentar s, Bin segmentBin) {
        segmentar = s;
        this.segmentBin = segmentBin;
        leaf = new Leaf(segmentBin);
        splitter = null;
    }

    public void addObservation(Observation o, boolean spowning) {
        if (leaf == null) {
            splitter.addObservation(o);
        } else {
            leaf.add(o);
            if (leaf.getBin().canSplit() && !spowning && segmentar.getCanBranch()) {
                if (splitter == null)
                    splitter = new Splitter(segmentar, segmentBin);
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
