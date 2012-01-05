package kid.Targeting.Fast;

import kid.Data.Robot.EnemyData;
import robocode.*;
import java.awt.Color;

import kid.RobocodeGraphicsDrawer;

public class Circular extends FastTargeting {

    public Circular(Robot MyRobot) {
        super(MyRobot);
    }

    public Circular(AdvancedRobot MyRobot) {
        super(MyRobot);
    }

    public Circular(TeamRobot MyRobot) {
        super(MyRobot);
    }

    public double getTargetingAngle(EnemyData EnemyRobot, double firePower) {
        return super.getAngle(EnemyRobot, firePower, EnemyRobot.getDeltaHeading() / EnemyRobot.getDeltaTime(), EnemyRobot.getVelocity());
    }

    public String getNameOfTargeting() {
        return "CircularTargeting";
    }

    public Color getTargetingColor() {
        return Color.BLUE;
    }

    public void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower) {
    }

}
