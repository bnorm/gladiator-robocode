package kid.Segmentation;

import kid.Data.PatternMatching.*;
import kid.Data.Robot.*;

public class PMBin extends Bin {

    private Pattern[] bin;
    private int PMcount;

    public PMBin() {
        bin = new Pattern[2048];
        PMcount = 0;
    }

    public void add(Observation o) {
        if (o != null) {
            PMcount++;
            Pattern p = o.getPattern();
            p.setPreviousIndexPattern(bin[(int) p.getSymbol()]);
            if (p.getPreviousIndex() != null) {
                p.getPreviousIndex().setNextIndexPattern(p);
            }
            bin[(int) p.getSymbol()] = p;
        }
    }

    public void clear() {
        bin = new Pattern[2048];
        PMcount = 0;
    }

    public Pattern getPatternHead(Pattern p) {
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