package kid.Data.Robot;

import kid.Utils;
import kid.Data.MyRobotsInfo;
import java.awt.geom.Rectangle2D;

public abstract class RobotData implements java.io.Serializable {

    public static final double ENERGY_DEAD = -1.0;

    //***** POSABLE ALIASES *****//
    public static final String ALIVE = "ALIVE";
    public static final String DEAD = "DEAD";

    public static final String BOMB = "BOMB";
    public static final String SHELTER = "SHELTER";

    public static final String HILL = "HILL";
    public static final String TREE = "TREE";


    public abstract void updateItem(double x, double y, double dist, double energy, double heading, double velocity, long time);

    public abstract void updateItemFromFile(double x, double y, double dist, double energy, double heading, double velocity,
                                            long time);


    public abstract void setDeath();

    public abstract void setAlias(String a);


    public abstract String getName();

    public abstract String getAlias();

    public abstract double getX();

    public abstract double getY();

    public Rectangle2D getRobot() {
        return new Rectangle2D.Double(getX() - MyRobotsInfo.WIDTH / 2, getY() + MyRobotsInfo.HEIGHT / 2,
                                      MyRobotsInfo.WIDTH, MyRobotsInfo.HEIGHT);
    }

    public double DistToXY(double x, double y) {
        return Utils.getDist(getX(), getY(), x, y);
    }

    public double BearingToXY(double x, double y) {
        return Utils.relative(Utils.getAngle(getX(), getY(), x, y) - getHeading());
    }

    public abstract double getEnergy();

    public boolean isDead() {
        return getAlias() == DEAD;
    }

    public abstract double getHeading();

    public abstract double getVelocity();

    public abstract long getTime();

    public abstract int getDeltaTime();

}
