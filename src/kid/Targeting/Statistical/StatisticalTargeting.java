package kid.Targeting.Statistical;

import robocode.*;
import kid.Data.*;
import kid.Data.Robot.*;
import kid.Targeting.Targeting;

public abstract class StatisticalTargeting implements Targeting {

    protected Robot MyRobot;
    protected MyRobotsInfo i;

    public StatisticalTargeting(Robot MyRobot) {
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
    }

    public StatisticalTargeting(AdvancedRobot MyRobot) {
        this((Robot) MyRobot);
        i = new MyRobotsInfo(MyRobot);
    }

    public StatisticalTargeting(TeamRobot MyRobot) {
        this((Robot) MyRobot);
        i = new MyRobotsInfo(MyRobot);
    }

    public abstract double getTargetingAngle(EnemyData robot, double firePower);

    public abstract String getNameOfTargeting();

    public String getTypeOfTargeting() {
        return "StatisticalTargeting";
    }
}
