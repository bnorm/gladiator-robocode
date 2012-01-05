package kid.Data.Segmentation;

import kid.*;

public class Bin {

    public static final int numBins = 31;
    private int[] bin;

    public Bin() {
        bin = new int[numBins];
    }

    public void add(double GF) {
        bin[Utils.getIndex(GF, numBins)]++;
    }

    public void clear() {
        bin = new int[numBins];
    }

    public int get(double GF) {
        return bin[Utils.getIndex(GF, numBins)];
    }

    public int get(int index) {
        return bin[index];
    }
}
