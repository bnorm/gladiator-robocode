package kid.Data.Robot;

import kid.Utils;
import kid.Data.MyRobotsInfo;
import robocode.Robot;

public class TeammateData extends RobotData implements java.io.Serializable {
    private static final long serialVersionUID = -6898830233847825000L;

    // ***** THIS ROBOTS INFO *****//
    protected String NAME;
    protected transient String ALIAS;

    protected transient double X;
    protected transient double Y;

    protected transient double ENERGY;
    protected transient double DELTA_ENERGY;

    protected transient double HEADING;
    protected transient double DELTA_HEADING;

    protected transient double VELOCITY;
    protected transient double DELTA_VELOCITY;
    protected transient double TIME_OF_ACCEL;
    protected transient double TIME_OF_DECCEL;

    protected transient long TIME;
    protected transient int DELTA_TIME;

    public TeammateData() {
        this(new String(), 0.0, 0.0, 0.0, 0.0, 0, 0);
        ALIAS = DEAD;
    }

    public TeammateData(String name, double x, double y, double energy, double heading, double velocity, long time) {
        NAME = name;
        updateItemFromFile(name, x, y, energy, heading, velocity, time);
    }

    public TeammateData(Robot MyRobot) {
        NAME = MyRobot.getName();
        updateItemFromFile(MyRobot.getName(), MyRobot.getX(), MyRobot.getY(), MyRobot.getEnergy(),
                MyRobot.getHeading(), MyRobot.getVelocity(), MyRobot.getTime());
    }

    public TeammateData(TeammateData MyRobot) {
        NAME = MyRobot.getName();
        updateItemFromFile(MyRobot.getName(), MyRobot.getX(), MyRobot.getY(), MyRobot.getEnergy(),
                MyRobot.getHeading(), MyRobot.getVelocity(), MyRobot.getTime());
    }

    public void updateItem(double x, double y, double energy, double heading, double velocity, long time) {
        if (time < TIME) {
            updateItemFromFile(NAME, x, y, energy, heading, velocity, time);
        } else if (time != TIME) {
            X = x;
            Y = y;
            DELTA_ENERGY = energy - ENERGY;
            ENERGY = energy;
            DELTA_HEADING = Utils.relative(heading - HEADING);
            HEADING = heading;
            DELTA_VELOCITY = velocity - VELOCITY;
            VELOCITY = velocity;
            if (Math.abs(DELTA_VELOCITY) == MyRobotsInfo.ACCELERATION)
                TIME_OF_ACCEL = time;
            else if (Math.abs(DELTA_VELOCITY) == MyRobotsInfo.DECCELERATION)
                TIME_OF_DECCEL = time;
            DELTA_TIME = (int) (time - TIME);
            TIME = time;
        }
    }


    public void updateItemFromFile(String name, double x, double y, double energy, double heading, double velocity,
            long time) {
        ALIAS = ALIVE;
        NAME = name;
        X = x;
        Y = y;
        ENERGY = energy;
        HEADING = heading;
        VELOCITY = velocity;
        TIME = time;
    }

    public void updateItemFromTeammate(TeammateData teammate) {
        updateItem(teammate.getX(), teammate.getY(), teammate.getEnergy(), teammate.getHeading(), teammate
                .getVelocity(), teammate.getTime());
    }


    public void setDeath() {
        ENERGY = ENERGY_DEAD;
        setAlias(DEAD);
    }

    public void setAlias(String a) {
        if (ALIAS != DEAD)
            ALIAS = a;
    }


    public String getName() {
        return NAME;
    }

    public String getAlias() {
        return ALIAS;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getEnergy() {
        return ENERGY;
    }
    
    public double getDeltaEnergy() {
        return DELTA_ENERGY;
    }

    public double getHeading() {
        return HEADING;
    }

    public double getDeltaHeading() {
        return DELTA_HEADING;
    }

    public double getVelocity() {
        return VELOCITY;
    }


    public double getDeltaVelocity() {
        return DELTA_VELOCITY;
    }

    public double getTimeSinceAccel() {
        return TIME - TIME_OF_ACCEL;
    }

    public double getTimeSinceDeccel() {
        return TIME - TIME_OF_DECCEL;
    }

    public long getTime() {
        return TIME;
    }

    public int getDeltaTime() {
        return DELTA_TIME;
    }

}
