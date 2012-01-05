package kid.Targeting.Fast;

import kid.Data.Robot.*;
import robocode.*;
import java.awt.Color;
import kid.RobocodeGraphicsDrawer;

public class Linear extends FastTargeting implements java.io.Serializable {
    public Linear(Robot MyRobot) {
        super(MyRobot);
    }

    public Linear(AdvancedRobot MyRobot) {
        super(MyRobot);
    }

    public Linear(TeamRobot MyRobot) {
        super(MyRobot);
    }

    public double getTargetingAngle(EnemyData robot, double firePower) {
        return getAngle(robot, firePower, 0.0, robot.getVelocity());
    }

    public String getNameOfTargeting() {
        return "LinearTargeting";
    }

    public Color getTargetingColor() {
        return Color.GREEN;
    }

    public void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower) {
    }
}
