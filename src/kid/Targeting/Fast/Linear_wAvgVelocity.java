package kid.Targeting.Fast;

import kid.Data.Robot.*;
import robocode.*;
import java.awt.Color;
import kid.RobocodeGraphicsDrawer;

public class Linear_wAvgVelocity extends Linear {

    public Linear_wAvgVelocity(Robot MyRobot) {
        super(MyRobot);
    }

    public Linear_wAvgVelocity(AdvancedRobot MyRobot) {
        super(MyRobot);
    }

    public Linear_wAvgVelocity(TeamRobot MyRobot) {
        super(MyRobot);
    }

    public double getTargetingAngle(EnemyData robot, double firePower) {
        return getAngle(robot, firePower, 0.0, robot.getAverageVelocity());
    }

    public String getNameOfTargeting() {
        return "LinearTargeting_wAvgVelocity";
    }

    public Color getTargetingColor() {
        return Color.GREEN.brighter();
    }

    public void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower) {
    }
}
