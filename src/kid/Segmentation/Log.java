package kid.Segmentation;

import java.util.*;

import kid.Data.Robot.*;

public class Log {

    private List Log;

    public Log() {
        Log = new ArrayList();
    }

    public void add(Observation o) {
        Log.add(o);
    }

    public Observation get(int index) {
        return (Observation) Log.get(index);
    }

    public int size() {
        return Log.size();
    }

    public void clear() {
        Log = null;
    }
}
