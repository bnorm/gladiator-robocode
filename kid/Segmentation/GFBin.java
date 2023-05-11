package kid.Segmentation;

import kid.*;
import kid.Data.MyRobotsInfo;
import kid.Data.Robot.Observation;

public class GFBin extends Bin {

    public static final int binsToSide = 15;
    public static final int numBins = binsToSide * 2 + 1;
    public static final int midBin = (numBins - 1) / 2;
    public static final double decayrate = 0.98;
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

        for (int i = 0; i < numBins; i++) {
            bin[i] *= (decayrate);
        }

        double angletoside = Utils.asin(MyRobotsInfo.LENGTH_CONER / o.getDist()) / 2;
        angletoside /= (Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(GFWieght)));
        double indextoside = binsToSide * (angletoside);

        // bin[index] += GFWieght;

        // for (int i = 0; i < numBins; i++) {
        for (int i = Math.max((int) (index - indextoside), 0); i < (index + indextoside + 1) && i < numBins; i++) {
            bin[i] += (GFWieght * indextoside) / (Utils.sqr(i - index) + indextoside);
            // bin[i] += Utils.limet(0, (GFWieght * Utils.sqr(indextoside)) /
            // Utils.sqr(i - index), GFWieght);
            // bin[i] += GFWieght;
        }

        GFcount++;
    }

    public void clear() {
        bin = new double[numBins];
        GFcount = 0;
    }

    public double get(int index) {
        return bin[index];
    }
    
    public double[] getBin() {
        return (double[]) bin.clone();
    }

    public Bin getNewBin() {
        return new GFBin();
    }

    public boolean canSplit() {
        return GFcount > numBins;
    }

}
