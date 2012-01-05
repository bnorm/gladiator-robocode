package kid.Targeting.Fast;

import kid.Data.Robot.EnemyData;
import robocode.*;
import java.awt.Color;
import kid.RobocodeGraphicsDrawer;

public class Circular_wAvgHeading extends Circular {

    public Circular_wAvgHeading(Robot MyRobot) {
        super(MyRobot);
    }

    public Circular_wAvgHeading(AdvancedRobot MyRobot) {
        super(MyRobot);
    }

    public Circular_wAvgHeading(TeamRobot MyRobot) {
        super(MyRobot);
    }

    public double getTargetingAngle(EnemyData robot, double firePower) {
        return getAngle(robot, firePower, robot.getAverageHeading(), robot.getVelocity());
    }

    public String getNameOfTargeting() {
        return "CircularTargeting_wAvgHeading";
    }

    public Color getTargetingColor() {
        return Color.BLUE.darker();
    }

    public void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower) {
    }

}
