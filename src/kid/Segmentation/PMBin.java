package kid.Segmentation;

import java.util.*;

import kid.Data.PatternMatching.*;
import kid.Data.Robot.*;

public class PMBin extends Bin {

    private ArrayList[] bin;
    private int PMcount;

    public PMBin() {
        bin = new ArrayList[512];
        for (int v = 0; v < bin.length; v++)
            bin[v] = new ArrayList();
        PMcount = 0;
    }

    public void add(Observation o) {
        if (o != null) {
            PMcount++;
            Pattern p = o.getPattern();
            if ((int) p.getSymbol() < bin.length)
                bin[(int) p.getSymbol()].add(p);
        }
    }

    public void clear() {
        bin = new ArrayList[512];
        for (int v = 0; v < bin.length; v++)
            bin[v] = new ArrayList();
        PMcount = 0;
    }

    public int[] getPatterns(Pattern p) {
        if (p == null)
            return new int[0];

        int length = 0;
        length += bin[(int) p.getSymbol()].size();

        int[] patttern = new int[length];
        for (int i = 0; i < patttern.length; i++)
            patttern[i] = ((Pattern) bin[(int) p.getSymbol()].get(i)).getArrayPosition();
        return patttern;
    }

    public Bin getNewBin() {
        return new PMBin();
    }

    public boolean canSplit() {
        return PMcount > 300;
    }

}
