package kid.Targeting.Fast;

import kid.Data.Robot.*;
import robocode.*;
import java.awt.Color;
import kid.RobocodeGraphicsDrawer;

public class Circular_wAvgHeading_wAvgVelocity extends Circular  implements java.io.Serializable{

    public Circular_wAvgHeading_wAvgVelocity(Robot MyRobot) {
        super(MyRobot);
    }

    public Circular_wAvgHeading_wAvgVelocity(AdvancedRobot MyRobot) {
        super(MyRobot);
    }

    public Circular_wAvgHeading_wAvgVelocity(TeamRobot MyRobot) {
        super(MyRobot);
    }

    public double getTargetingAngle(EnemyData robot, double firePower) {
        return getAngle(robot, firePower, robot.getAverageHeading(), robot.getAverageVelocity());
    }

    public String getNameOfTargeting() {
        return "CircularTargeting_wAvgHeading_wAvgVelocity";
    }

    public Color getTargetingColor() {
        return Color.CYAN;
    }

    public void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower) {
    }
}
