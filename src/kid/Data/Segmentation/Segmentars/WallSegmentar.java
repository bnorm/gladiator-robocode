package kid.Data.Segmentation.Segmentars;

import kid.Data.Robot.*;

public class WallSegmentar extends Segmentar {

    private static final double MinDistSegent = 5;

    private double HighDist = 0.0;
    private double LowDist = 0.0;

    private double MidDist = 0.0;

    private double BattleWidth = 0.0;
    private double BattleHeight = 0.0;

    public WallSegmentar(double battlewidth, double battleheight, double HighWallDist, double LowWallDist) {
        HighDist = HighWallDist;
        LowDist = LowWallDist;
        MidDist = (HighDist + LowDist) / 2;

        BattleWidth = battlewidth;
        BattleHeight = battleheight;
    }

    public boolean branchHigh(Observation o) {
        double dist = Math.min(Math.min(o.getY(), o.getX()), Math.min(BattleWidth - o.getX(), BattleHeight - o.getY()));
        if (dist >= MidDist)
            return true;
        return false;
    }

    public boolean branchLow(Observation o) {
        double dist = Math.min(Math.min(o.getY(), o.getX()), Math.min(BattleWidth - o.getX(), BattleHeight - o.getY()));
        if (dist <= MidDist)
            return true;
        return false;
    }

    public Segmentar getHighBranch() {
        return new WallSegmentar(BattleWidth, BattleHeight, HighDist, MidDist);
    }

    public Segmentar getLowBranch() {
        return new WallSegmentar(BattleWidth, BattleHeight, MidDist, LowDist);
    }

    public boolean getCanBranch() {
        return (MidDist - LowDist > MinDistSegent);
    }
}
