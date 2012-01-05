package kid.Segmentation.Segmentars;

import kid.Data.Robot.*;
import robocode.*;

public class MeleeSegmentar extends Segmentar {

    private static final int MinOthersSegent = 1;

    private int HighOthers = 0;
    private int LowOthers = 0;

    private int MidOthers = 0;

    public MeleeSegmentar(Robot MyRobot) {
        this(MyRobot.getOthers(), 0);
    }

    public MeleeSegmentar(int HighOthers, int LowOthers) {
        this.HighOthers = HighOthers;
        this.LowOthers = LowOthers;
        MidOthers = (int) ((HighOthers + LowOthers) / 2);
    }

    public boolean branchHigh(Observation o) {
        return o.getDist() >= MidOthers;
    }

    public boolean branchLow(Observation o) {
        return (o.getDist() <= MidOthers);
    }

    public Segmentar getHighBranch() {
        return new MeleeSegmentar(HighOthers, MidOthers);
    }

    public Segmentar getLowBranch() {
        return new MeleeSegmentar(MidOthers, LowOthers);
    }

    public boolean canBranch() {
        return (HighOthers - MidOthers > MinOthersSegent);
    }

    public String getHighBranchString() {
        return "Others: " + HighOthers + " " + MidOthers;
    }

    public String getLowBranchString() {
        return "Others: " + MidOthers + " " + LowOthers;
    }

}
