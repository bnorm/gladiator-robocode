package kid.Data.Robot;

import robocode.Robot;

public class MyRobotData extends RobotData implements java.io.Serializable {

    //***** THIS ROBOTS INFO *****//
    protected double X;
    protected double Y;

    protected double HEADING;

    protected double VELOCITY;

    public MyRobotData() {
        this(new String(), 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
    }

    public MyRobotData(String name, double x, double y, double dist, double energy, double heading, double velocity,
                       long time) {
        updateItemFromFile(x, y, dist, energy, heading, velocity, time);
    }

    public MyRobotData(Robot MyRobot) {
        X = MyRobot.getX();
        Y = MyRobot.getY();
        HEADING = MyRobot.getHeading();
        VELOCITY = MyRobot.getVelocity();
    }

    public void updateItem(double x, double y, double dist, double energy, double heading, double velocity, long time) {
        X = x;
        Y = y;
        HEADING = heading;
        VELOCITY = velocity;
    }


    public void updateItemFromFile(double x, double y, double dist, double energy, double heading, double velocity,
                                   long time) {
        X = x;
        Y = y;
        HEADING = heading;
        VELOCITY = velocity;
    }


    public void setDeath() {
    }

    public void setAlias(String a) {
    }


    public String getName() {
        return new String();
    }

    public String getAlias() {
        return new String();
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getEnergy() {
        return 0;
    }

    public double getHeading() {
        return HEADING;
    }

    public double getVelocity() {
        return VELOCITY;
    }

    public long getTime() {
        return 0;
    }

    public int getDeltaTime() {
        return 0;
    }


}
