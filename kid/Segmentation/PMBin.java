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
            int index = (int) p.getSymbol();
            p.setPreviousIndex(bin[index]);
            if (p.getPreviousIndex() != null) {
                p.getPreviousIndex().setNextIndex(p);
            }
            bin[index] = p;
        }
    }

    public void clear() {
        bin = new PolarPattern[2048];
        PMcount = 0;
    }

    public Bin getNewBin() {
        return new PMBin();
    }

    public boolean canSplit() {
        return PMcount > bin.length / 4;
    }

}