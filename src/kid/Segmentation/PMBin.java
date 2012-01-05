package kid.Segmentation;

import kid.Data.PatternMatching.*;
import kid.Data.Robot.*;

public class PMBin extends Bin {

    private PolarPattern[] bin;
    private int PMcount;

    public PMBin() {
        bin = new PolarPattern[2048];
        PMcount = 0;
    }

    public void add(Observation o) {
        if (o != null) {
            PMcount++;
            PolarPattern p = o.getPolarPattern();
            p.setPreviousIndexPattern(bin[(int) p.getSymbol()]);
            if (p.getPreviousIndex() != null) {
                p.getPreviousIndex().setNextIndexPattern(p);
            }
            bin[(int) p.getSymbol()] = p;
        }
    }

    public void clear() {
        bin = new PolarPattern[2048];
        PMcount = 0;
    }

    public PolarPattern getPatternHead(PolarPattern p) {
        if (p == null) {
            return p.getPreviousIndex();
        }
        return null;
    }

    public Bin getNewBin() {
        return new PMBin();
    }

    public boolean canSplit() {
        return PMcount > 300;
    }

}