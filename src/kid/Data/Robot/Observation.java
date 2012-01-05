package kid.Data.Robot;

import robocode.Robot;
import kid.Utils;

public class Observation {

    private double GF;

    private double X;
    private double Y;

    private double DIST;

    private double ENERGY;
    private double DELTA_ENERGY;

    private double HEADING;
    private double DELTA_HEADING;

    private double VELOCITY;
    private double ADVANCING_VELOCITY;
    private double LATERAL_VELOCITY;

    private long TIME;
    private int DELTA_TIME;

    public Observation(EnemyData e, Robot MyRobot) {
        X = e.getX();
        Y = e.getY();

        DIST = Utils.getDist(MyRobot.getX(), MyRobot.getY(), X, Y);

        ENERGY = e.getEnergy();
        DELTA_ENERGY = e.getDeltaEnergy();

        HEADING = e.getHeading();
        DELTA_HEADING = e.getDeltaHeading();

        VELOCITY = e.getVelocity();
        double angle = Utils.getAngle(MyRobot.getX(), MyRobot.getY(), X, Y);
        ADVANCING_VELOCITY = Math.abs(VELOCITY * Utils.sin(HEADING - angle));
        LATERAL_VELOCITY = VELOCITY * -Utils.cos(HEADING - angle);

        TIME = e.getTime();
        DELTA_TIME = e.getDeltaTime();
    }

    public Observation(Robot MyRobot, EnemyData e) {
        X = MyRobot.getX();
        Y = MyRobot.getY();

        DIST = Utils.getDist(e.getX(), e.getY(), X, Y);

        ENERGY = MyRobot.getEnergy();
        //DELTA_ENERGY = MyRobot.getDeltaEnergy();

        HEADING = MyRobot.getHeading();
        //DELTA_HEADING = MyRobot.getDeltaHeading();

        VELOCITY = MyRobot.getVelocity();
        double angle = Utils.getAngle(e.getX(), e.getY(), X, Y);
        ADVANCING_VELOCITY = Math.abs(VELOCITY * Utils.sin(HEADING - angle));
        LATERAL_VELOCITY = VELOCITY * -Utils.cos(HEADING - angle);

        TIME = MyRobot.getTime();
        //DELTA_TIME = MyRobot.getDeltaTime();
    }


    public void setGFHit(double GF) {
        this.GF = GF;
    }

    public double getGF() {
        return GF;
    }

    public double getDist() {
        return DIST;
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

    public double getLateralVelocity() {
        return LATERAL_VELOCITY;
    }

    public double getAdvancingVelocity() {
        return ADVANCING_VELOCITY;
    }


    public long getTime() {
        return TIME;
    }

    public int getDeltaTime() {
        return DELTA_TIME;
    }
}
