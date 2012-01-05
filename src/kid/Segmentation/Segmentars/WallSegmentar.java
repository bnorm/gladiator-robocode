package kid.Segmentation.Segmentars;

import kid.Data.Robot.*;
import kid.Data.MyRobotsInfo;
import robocode.Robot;

public class WallSegmentar extends Segmentar {

    private static final double MinDistSegent = 1;

    private double HighDist = 0.0;
    private double LowDist = 0.0;

    private double MidDist = 0.0;

    private double BattleWidth = 0.0;
    private double BattleHeight = 0.0;

    public WallSegmentar(Robot MyRobot) {
        this(MyRobot.getBattleFieldWidth(), MyRobot.getBattleFieldHeight(), 25, 0);
    }

    public WallSegmentar(Robot MyRobot, double HighWallDist, double LowWallDist) {
        this(MyRobot.getBattleFieldWidth(), MyRobot.getBattleFieldHeight(), HighWallDist, LowWallDist);
    }

    public WallSegmentar(double battlewidth, double battleheight) {
        this(battlewidth, battleheight, 25, 0);
    }

    public WallSegmentar(double battlewidth, double battleheight, double HighWallDist, double LowWallDist) {
        HighDist = HighWallDist;
        LowDist = LowWallDist;
        MidDist = (HighDist + LowDist) / 2;

        BattleWidth = battlewidth;
        BattleHeight = battleheight;
    }

    public boolean branchHigh(Observation o) {
        double dist = Math.min(Math.min(o.getY(), o.getX()), Math.min(BattleWidth - o.getX(), BattleHeight - o.getY()))
                - MyRobotsInfo.MIN_WALL_DIST;
        if (dist >= MidDist)
            return true;
        return false;
    }

    public boolean branchLow(Observation o) {
        double dist = Math.min(Math.min(o.getY(), o.getX()), Math.min(BattleWidth - o.getX(), BattleHeight - o.getY()))
                - MyRobotsInfo.MIN_WALL_DIST;
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

    public String getHighBranchString() {
        return "WallDist: " + HighDist + " " + MidDist;
    }

    public String getLowBranchString() {
        return "WallDist: " + MidDist + " " + LowDist;
    }
}
