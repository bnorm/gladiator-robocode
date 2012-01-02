package kid.Targeting;

import kid.Data.Robot.EnemyData;
import java.awt.Color;
import kid.RobocodeGraphicsDrawer;

public interface Targeting {

    public abstract double getTargetingAngle(EnemyData EnemyRobot, double FirePower);

    public abstract String getNameOfTargeting();

    public abstract String getTypeOfTargeting();

    public abstract Color getTargetingColor();

    public abstract void drawTargeting(RobocodeGraphicsDrawer g, EnemyData EnemyRobot, double FirePower);

}
