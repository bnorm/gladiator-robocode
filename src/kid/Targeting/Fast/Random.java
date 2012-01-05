package kid.Targeting.Fast;

import robocode.*;
import kid.Utils;
import kid.Data.Robot.*;
import kid.Data.MyRobotsInfo;
import java.awt.Color;
import kid.RobocodeGraphicsDrawer;

public class Random extends FastTargeting implements java.io.Serializable {
    public Random(Robot MyRobot) {
        super(MyRobot);
    }

    public Random(AdvancedRobot MyRobot) {
        super(MyRobot);
    }

    public Random(TeamRobot MyRobot) {
        super(MyRobot);
    }

    public double getTargetingAngle(EnemyData robot, double firePower) {
        if (robot != null) {
            double angle;
            angle = Utils.atan2(robot.getX() - MyRobot.getX(), robot.getY() - MyRobot.getY());
            return Utils.relative(Utils.random(angle - Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(firePower)),
                    angle + Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(firePower))));
        }
        return MyRobot.getGunHeading();
    }

    public String getNameOfTargeting() {
        return "RandomTargeting";
    }

    public Color getTargetingColor() {
        return Color.MAGENTA;
    }

    public void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower) {
    }
}
