package kid.Data.Segmentation;

import kid.Data.Robot.*;

public class Leaf implements java.io.Serializable {

    private Log log;
    private Bin nodeBin;

    public Leaf() {
        log = new Log();
        nodeBin = new Bin();
    }

    public Log getLog() {
        return log;
    }

    public Bin getBin() {
        return nodeBin;
    }

    public void add(Observation o) {
        log.add(o);
        nodeBin.add(o.getGF());
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
