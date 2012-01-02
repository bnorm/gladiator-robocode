package kid.Targeting.Fast;

import robocode.*;
import kid.Data.Robot.EnemyData;
import kid.*;
import kid.Utils;
import java.awt.Color;
import kid.RobocodeGraphicsDrawer;

public class HeadOn extends FastTargeting {

    public HeadOn(Robot MyRobot) {
        super(MyRobot);
    }

    public HeadOn(AdvancedRobot MyRobot) {
        super(MyRobot);
    }

    public HeadOn(TeamRobot MyRobot) {
        super(MyRobot);
    }

    public double getTargetingAngle(EnemyData EnemyRobot, double firePower) {
        TimeTracker.startTargetingTime();
        if (EnemyRobot != null) {
            TimeTracker.stopTargetingTime();
            return Utils.getAngle(MyRobot.getX(), MyRobot.getY(), EnemyRobot.getX(), EnemyRobot.getY());
        }
        TimeTracker.stopTargetingTime();
        return MyRobot.getGunHeading();
    }

    public String getNameOfTargeting() {
        return "HeadOnTargeting";
    }

    public Color getTargetingColor() {
        return Color.RED;
    }

    public void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower) {
    }
}
