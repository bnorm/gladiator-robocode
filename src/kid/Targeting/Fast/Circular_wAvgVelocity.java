package kid.Targeting.Fast;

import kid.Data.Robot.*;
import robocode.*;
import java.awt.Color;
import kid.RobocodeGraphicsDrawer;

public class Circular_wAvgVelocity extends Circular  implements java.io.Serializable{

    public Circular_wAvgVelocity(Robot MyRobot) {
        super(MyRobot);
    }

    public Circular_wAvgVelocity(AdvancedRobot MyRobot) {
        super(MyRobot);
    }

    public Circular_wAvgVelocity(TeamRobot MyRobot) {
        super(MyRobot);
    }

    public double getTargetingAngle(EnemyData robot, double firePower) {
        return getAngle(robot, firePower, robot.getDeltaHeading(), robot.getAverageVelocity());
    }

    public String getNameOfTargeting() {
        return "CircularTargeting_wAvgVelocity";
    }

    public Color getTargetingColor() {
        return Color.BLUE.brighter();
    }

    public void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower) {
    }
}
