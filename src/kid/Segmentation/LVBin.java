package kid.Segmentation;

import kid.Data.PatternMatching.*;
import kid.Data.Robot.*;

public class LVBin extends Bin {

    private LatVelPattern[] bin;
    private int PMcount;

    public LVBin() {
        bin = new LatVelPattern[2048];
        PMcount = 0;
    }

    public void add(Observation o) {
        if (o != null) {
            PMcount++;
            LatVelPattern p = o.getGFPattern();
            p.setPreviousIndexPattern(bin[(int) p.getSymbol()]);
            if (p.getPreviousIndex() != null) {
                p.getPreviousIndex().setNextIndexPattern(p);
            }
            bin[(int) p.getSymbol()] = p;
        }
    }

    public void clear() {
        bin = new LatVelPattern[2048];
        PMcount = 0;
    }

    public LatVelPattern getPatternHead(LatVelPattern p) {
        if (p == null) {
            return p.getPreviousIndex();
        }
        return null;
    }

    public Bin getNewBin() {
        return new LVBin();
    }

    public boolean canSplit() {
        return PMcount > 300;
    }

}