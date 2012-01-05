package kid.Segmentation;

import kid.Data.Robot.*;
import kid.Segmentation.Segmentars.*;

public class Node {

    private Leaf leaf;
    private Splitter splitter;
    private boolean splittercansplit = true;

    private Segmentar[] segmentars;
    private Bin segmentBin;

    public Node(Segmentar[] s, Bin segmentBin) {
        segmentars = s;
        this.segmentBin = segmentBin;
        leaf = new Leaf(segmentBin);
        splitter = null;
    }

    public void addObservation(Observation o, boolean spowning) {
        if (leaf == null) {
            splitter.addObservation(o);
        } else {
            leaf.add(o);
            if (leaf.getBin().canSplit() && !spowning && splittercansplit) {
                if (splitter == null)
                    splitter = new Splitter(segmentars);
                if (splitter.split(leaf, segmentBin)) {
                    leaf.discard();
                    leaf = null;
                } else {
                    splittercansplit = false;
                    splitter = null;
                }
            }
        }
    }
    
    public void printObservation(Observation o) {
        if (leaf == null) {
            splitter.printObservation(o);
        }
    }

    public Bin getBin(Observation o) {
        if (splitter == null)
            return leaf.getBin();
        return splitter.getBin(o);
    }

    public Log getLog(Observation o) {
        if (splitter == null)
            return leaf.getLog();
        return splitter.getLog(o);
    }

    public void clear() {
        leaf.clear();
    }
}
