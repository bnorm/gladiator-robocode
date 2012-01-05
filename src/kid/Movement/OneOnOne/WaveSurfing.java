package kid.Movement.OneOnOne;

import kid.Data.*;
import kid.Data.Robot.*;
import robocode.*;

public abstract class WaveSurfing extends OneOnOneMovement {

    protected double SafeDist;
    protected MovementProfile f;

    protected AdvancedRobot MyRobot;

    public WaveSurfing(AdvancedRobot MyRobot, MovementProfile f) {
        super(MyRobot);
        this.MyRobot = MyRobot;
        SafeDist = Math.min(Math.min(MyRobot.getBattleFieldHeight(), MyRobot.getBattleFieldWidth()), 600);
        this.f = f;
    }

    public WaveSurfing(TeamRobot MyRobot, MovementProfile f) {
        super(MyRobot);
        this.MyRobot = MyRobot;
        SafeDist = Math.min(Math.min(MyRobot.getBattleFieldHeight(), MyRobot.getBattleFieldWidth()), 600);
        this.f = f;
    }

    protected double SafeDist(double SafeDist, EnemyData Enemy) {
        double DistFactor = Math.min(Math.max(i.DistTo(Enemy) / SafeDist, 0.8), 1.2);
        double EnergyFacter = 1;// Math.max(Enemy.getEnergy() /
        // MyRobot.getEnergy(), 1);
        // DistFacter = (DistFacter > 1.2 ? 1.2 : DistFacter);
        // DistFacter = (DistFacter < 0.8 ? 0.8 : DistFacter);
        return EnergyFacter * DistFactor * SafeDist;
    }

}
