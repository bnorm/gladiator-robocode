package kid.Segmentation;

import kid.*;
import kid.Data.MyRobotsInfo;
import kid.Data.Robot.Observation;

public class GFBin extends Bin {

    public static final int binsToSide = 20;
    public static final int numBins = binsToSide * 2 + 1;
    public static final int midBin = (numBins - 1) / 2;
    private double[] bin;
    private int GFcount;

    public GFBin() {
        bin = new double[numBins];
        GFcount = 0;
    }

    public void add(Observation o) {
        int index = Utils.getIndex(o.getGF(), numBins);
        double GFWieght = o.getGFWieght();

        if (!(index > -1 && index < numBins))
            return;

        double angletoside = Utils.asin(MyRobotsInfo.WIDTH / o.getDist());
        int indextoside = (Utils.getIndex(angletoside
                / (Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(GFWieght))), numBins) - binsToSide) / 2;

        for (int i = 1; i < indextoside && index + i < numBins && index - i > -1; i++) {
            bin[index + i] += GFWieght / (i + 1);
            bin[index - i] += GFWieght / (i + 1);
        }

        GFcount++;
        bin[index] += GFWieght;
    }

    public void clear() {
        bin = new double[numBins];
        GFcount = 0;
    }

    /*
     * public int get(double GF) { return bin[Utils.getIndex(GF, numBins)]; }
     */

    public double get(int index) {
        return bin[index];
    }

    public Bin getNewBin() {
        return new GFBin();
    }

    public boolean canSplit() {
        return GFcount > numBins;
    }

}
