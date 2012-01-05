package kid.Segmentation;

import kid.Data.Robot.*;

public class Leaf implements java.io.Serializable {

    private static final long serialVersionUID = 1359764339473097463L;
    
    private Log log;
    private Bin nodeBin;

    public Leaf(Bin b) {
        log = new Log();
        nodeBin = b.getNewBin();
    }

    public Log getLog() {
        return log;
    }

    public Bin getBin() {
        return nodeBin;
    }

    public void add(Observation o) {
        log.add(o);
        nodeBin.add(o);
    }

    public int size() {
        return log.size();
    }

    public void discard() {
        log = null;
        nodeBin = null;
    }

    public void clear() {
        log.clear();
        nodeBin.clear();
    }
}
