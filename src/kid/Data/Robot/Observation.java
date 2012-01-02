package kid.Data.Robot;

import robocode.Robot;
import kid.Utils;
import kid.Data.MyRobotsInfo;
import kid.Data.PatternMatching.*;
import kid.Data.Virtual.VirtualWave;

public class Observation {

    private VirtualWave v;

    private PolarPattern POLAR_PATTERN;
    private LatVelPattern GF_PATTERN;

    private double GF;
    private double GFWieght;

    private double X;
    private double Y;

    private double ANGLE_TO_ENEMY;
    private double ANGLE_TO_ME;

    private double DIST;
    private double DIST_TO_WALL;
    private double DIST_TO_WALL_REVERSE;

    private double ESCAPE_ANGLE_WALL;
    private double ESCAPE_ANGLE_WALL_REVERSE;

    private double ENERGY;
    private double DELTA_ENERGY;

    private double HEADING;
    private double DELTA_HEADING;

    private double VELOCITY;
    private double DELTA_VELOCITY;
    private double TIME_OF_ACCEL;
    private double TIME_OF_DECCEL;
    private double ADVANCING_VELOCITY;
    private double LATERAL_VELOCITY;

    private long TIME;
    private int DELTA_TIME;

    private int OTHERS;

    public Observation(EnemyData observedRobot, Robot referenceRobot) {
        X = observedRobot.getX();
        Y = observedRobot.getY();
        ANGLE_TO_ENEMY = Utils.getAngle(X, Y, referenceRobot.getX(), referenceRobot.getY());
        ANGLE_TO_ME = Utils.getAngle(referenceRobot.getX(), referenceRobot.getY(), X, Y);

        DIST = Utils.getDist(referenceRobot.getX(), referenceRobot.getY(), X, Y);

        ENERGY = observedRobot.getEnergy();
        DELTA_ENERGY = observedRobot.getDeltaEnergy();

        HEADING = observedRobot.getHeading();
        DELTA_HEADING = observedRobot.getDeltaHeading();

        VELOCITY = observedRobot.getVelocity();
        DELTA_VELOCITY = observedRobot.getVelocity() - observedRobot.getDeltaVelocity();
        TIME_OF_ACCEL = observedRobot.getTime() - observedRobot.getTimeSinceAccel();
        TIME_OF_DECCEL = observedRobot.getTime() - observedRobot.getTimeSinceDeccel();
        ADVANCING_VELOCITY = VELOCITY * -Utils.cos(HEADING - ANGLE_TO_ENEMY);
        LATERAL_VELOCITY = VELOCITY * Utils.sin(HEADING - ANGLE_TO_ENEMY);

        TIME = observedRobot.getTime();
        DELTA_TIME = observedRobot.getDeltaTime();

        OTHERS = referenceRobot.getOthers();

        DIST_TO_WALL = DistToWall(referenceRobot.getBattleFieldHeight(), referenceRobot.getBattleFieldWidth(), Utils
                .absolute(HEADING));
        DIST_TO_WALL_REVERSE = DistToWall(referenceRobot.getBattleFieldHeight(), referenceRobot.getBattleFieldWidth(),
                Utils.absolute(HEADING + 180));

        ESCAPE_ANGLE_WALL = DistToWall(referenceRobot.getBattleFieldHeight(), referenceRobot.getBattleFieldWidth(),
                Utils.absolute(ANGLE_TO_ME + 90 * Utils.sign(LATERAL_VELOCITY)));
        ESCAPE_ANGLE_WALL_REVERSE = DistToWall(referenceRobot.getBattleFieldHeight(), referenceRobot
                .getBattleFieldWidth(), Utils.absolute(ANGLE_TO_ME + 90 * Utils.sign(-LATERAL_VELOCITY)));
    }

    public Observation(TeammateData observedRobot, EnemyData referenceRobot, Robot gameInfo) {
        X = observedRobot.getX();
        Y = observedRobot.getY();
        ANGLE_TO_ENEMY = Utils.getAngle(X, Y, referenceRobot.getX(), referenceRobot.getY());
        ANGLE_TO_ME = Utils.getAngle(referenceRobot.getX(), referenceRobot.getY(), X, Y);

        DIST = Utils.getDist(X, Y, referenceRobot.getX(), referenceRobot.getY());

        ENERGY = observedRobot.getEnergy();
        // DELTA_ENERGY = MyRobot.getDeltaEnergy();

        HEADING = observedRobot.getHeading();
        DELTA_HEADING = observedRobot.getDeltaHeading();

        VELOCITY = observedRobot.getVelocity();
        DELTA_VELOCITY = observedRobot.getVelocity() - observedRobot.getDeltaVelocity();
        TIME_OF_ACCEL = observedRobot.getTime() - observedRobot.getTimeSinceAccel();
        TIME_OF_DECCEL = observedRobot.getTime() - observedRobot.getTimeSinceDeccel();
        ADVANCING_VELOCITY = VELOCITY * -Utils.cos(HEADING - ANGLE_TO_ENEMY);
        LATERAL_VELOCITY = VELOCITY * Utils.sin(HEADING - ANGLE_TO_ENEMY);

        TIME = observedRobot.getTime();
        DELTA_TIME = observedRobot.getDeltaTime();

        OTHERS = gameInfo.getOthers();

        DIST_TO_WALL = DistToWall(gameInfo.getBattleFieldHeight(), gameInfo.getBattleFieldWidth(), Utils
                .absolute(HEADING));
        DIST_TO_WALL_REVERSE = DistToWall(gameInfo.getBattleFieldHeight(), gameInfo.getBattleFieldWidth(), Utils
                .absolute(HEADING + 180));

        ESCAPE_ANGLE_WALL = DistToWall(gameInfo.getBattleFieldHeight(), gameInfo.getBattleFieldWidth(), Utils
                .absolute(ANGLE_TO_ME + 90 * Utils.sign(LATERAL_VELOCITY)));
        ESCAPE_ANGLE_WALL_REVERSE = DistToWall(gameInfo.getBattleFieldHeight(), gameInfo.getBattleFieldWidth(), Utils
                .absolute(ANGLE_TO_ME + 90 * Utils.sign(-LATERAL_VELOCITY)));
    }

    public void setVirtualWave(VirtualWave wave) {
        this.v = wave;
    }

    public VirtualWave getWave() {
        return v;
    }

    public final double DistToWall(double bfh, double bfw, double heading) {
        double a = Utils.absolute(heading);
        double wallDist = bfh - getY() - MyRobotsInfo.MIN_WALL_DIST;

        if (a < Utils.absolute(Utils.getAngle(X, Y, bfw, bfh))) {
        } else if (a < Utils.absolute(Utils.getAngle(X, Y, bfw, 0.0))) {
            a -= 90.0;
            wallDist = bfw - getX() - MyRobotsInfo.MIN_WALL_DIST;
        } else if (a < Utils.absolute(Utils.getAngle(X, Y, 0.0, 0.0))) {
            a -= 180.0;
            wallDist = getY() - MyRobotsInfo.MIN_WALL_DIST;
        } else if (a < Utils.absolute(Utils.getAngle(X, Y, 0.0, bfh))) {
            a -= 270.0;
            wallDist = getX() - MyRobotsInfo.MIN_WALL_DIST;
        }
        a = Utils.relative(a);
        return wallDist / Utils.cos(Math.abs(a));
    }

    public void setGFHit(double GF) {
        this.GF = GF;
        GFWieght = 1;
    }

    public void setGFHit(double GF, double GFWieght) {
        this.GF = GF;
        this.GFWieght = GFWieght;
    }

    public void setGFPattern(LatVelPattern p) {
        GF_PATTERN = p;
    }

    public LatVelPattern getGFPattern() {
        return GF_PATTERN;
    }

    public void setPolarPattern(PolarPattern p) {
        POLAR_PATTERN = p;
    }

    public PolarPattern getPolarPattern() {
        return POLAR_PATTERN;
    }

    public double getGF() {
        return GF;
    }

    public double getGFWieght() {
        return GFWieght;
    }

    public double getDist() {
        return DIST;
    }

    public double getDistToWall() {
        return DIST_TO_WALL;
    }

    public double getDistToWallReverse() {
        return DIST_TO_WALL_REVERSE;
    }

    public double getEscapeAngleWall() {
        return ESCAPE_ANGLE_WALL;
    }

    public double getEscapeAngleWallReverse() {
        return ESCAPE_ANGLE_WALL_REVERSE;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getAngleToEnemy() {
        return ANGLE_TO_ENEMY;
    }

    public double getAngleFromEnemy() {
        return ANGLE_TO_ME;
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

    public int getOthers() {
        return OTHERS;
    }
}