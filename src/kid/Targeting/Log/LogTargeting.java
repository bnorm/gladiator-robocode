package kid.Targeting.Log;

import robocode.*;
import kid.Data.Robot.EnemyData;
import kid.Targeting.Targeting;

public abstract class LogTargeting implements Targeting {

    protected Robot MyRobot;
    protected int RecentLogLength = 9;

    public LogTargeting(Robot MyRobot, int RecentLogLength) {
        this.MyRobot = MyRobot;
        this.RecentLogLength = RecentLogLength;
    }

    public LogTargeting(AdvancedRobot MyRobot, int RecentLogLength) {
        this((Robot) MyRobot, RecentLogLength);
    }

    public LogTargeting(TeamRobot MyRobot, int RecentLogLength) {
        this((Robot) MyRobot, RecentLogLength);
    }

    public abstract double getTargetingAngle(EnemyData robot, double firePower);

    public abstract String getNameOfTargeting();

    public String getTypeOfTargeting() {
        return "LogTargeting";
    }


}
