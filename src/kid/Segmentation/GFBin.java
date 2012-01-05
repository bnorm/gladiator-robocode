package kid.Segmentation;

import kid.*;
import kid.Data.Robot.Observation;

public class GFBin extends Bin {

    public static final int binsToSide = 20;
    public static final int numBins = binsToSide * 2 + 1;
    private int[] bin;
    private int GFcount;

    public GFBin() {
        bin = new int[numBins];
        GFcount = 0;
    }

    public void add(Observation o) {
        bin[Utils.getIndex(o.getGF(), numBins)]++;
    }

    public void clear() {
        bin = new int[numBins];
        GFcount = 0;
    }

    /*
        public int get(double GF) {
            return bin[Utils.getIndex(GF, numBins)];
        }
     */

    public int get(int index) {
        return bin[index];
    }

    public Bin getNewBin() {
        return new GFBin();
    }

    public boolean canSplit() {
        return GFcount > numBins;
    }

}
