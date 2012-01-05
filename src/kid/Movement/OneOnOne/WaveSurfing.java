package kid.Movement.OneOnOne;

import kid.Data.*;
import kid.Data.Robot.*;
import robocode.*;

public abstract class WaveSurfing extends OneOnOneMovement {

    protected double SafeDist;
    protected Flatner f;

    public WaveSurfing(AdvancedRobot MyRobot, Flatner f) {
        super(MyRobot);
        SafeDist = Math.min(MyRobot.getBattleFieldHeight(), MyRobot.getBattleFieldWidth()) / 1.2;
        this.f = f;
    }

    public WaveSurfing(TeamRobot MyRobot, Flatner f) {
        super(MyRobot);
        SafeDist = Math.min(MyRobot.getBattleFieldHeight(), MyRobot.getBattleFieldWidth()) / 1.2;
        this.f = f;
    }

    protected double SafeDist(double SafeDist, EnemyData Enemy) {
        double DistFacter = Enemy.getEnergy() / MyRobot.getEnergy();
        DistFacter = (DistFacter > 1.2 ? 1.2 : DistFacter);
        DistFacter = (DistFacter < 0.8 ? 0.8 : DistFacter);
        return Math.min(DistFacter * SafeDist, 1000);
    }

}
