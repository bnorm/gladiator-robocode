package kid.Targeting.Log;

import robocode.*;
import kid.Data.Robot.EnemyData;
import kid.Targeting.Targeting;

public abstract class LogTargeting implements Targeting {

    protected Robot MyRobot;

    public LogTargeting(Robot MyRobot) {
        this.MyRobot = MyRobot;
    }

    public LogTargeting(AdvancedRobot MyRobot) {
        this((Robot) MyRobot);
    }

    public LogTargeting(TeamRobot MyRobot) {
        this((Robot) MyRobot);
    }

    public abstract double getTargetingAngle(EnemyData robot, double firePower);

    public abstract String getNameOfTargeting();

    public String getTypeOfTargeting() {
        return "LogTargeting";
    }


}
