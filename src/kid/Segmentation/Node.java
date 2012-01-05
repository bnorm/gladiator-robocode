package kid.Segmentation;

import kid.Data.Robot.*;
import kid.Segmentation.Segmentars.*;

public class Node {

    private Leaf leaf;
    private Splitter splitter;

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
            if (leaf.size() > 2 && !spowning) {
                if (splitter == null)
                    splitter = new Splitter(segmentars);
                if (splitter.split(leaf, segmentBin)) {
                    leaf.discard();
                    leaf = null;
                } else {
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

    public void printTree() {
        if (splitter != null) {
            splitter.printTree(0);
            System.out.println();
        }
    }

    protected void printTree(int spaces) {
        if (splitter != null) {
            splitter.printTree(spaces + 1);
        } else {
            String s = "";
            for (int i = 0; i <= spaces; i++)
                s += "  ";
            System.out.println(s + "Leaf");
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
