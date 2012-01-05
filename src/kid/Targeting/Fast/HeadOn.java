package kid.Targeting.Fast;

import robocode.*;
import kid.Data.Robot.EnemyData;
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
        if (EnemyRobot != null) {
            return Utils.getAngle(MyRobot.getX(), MyRobot.getY(), EnemyRobot.getX(), EnemyRobot.getY());
        }
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
