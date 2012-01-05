package kid.Data.Virtual;

import kid.Utils;
import java.awt.geom.Rectangle2D;
import kid.Data.MyRobotsInfo;
import kid.Data.Robot.EnemyData;
import kid.Data.Robot.RobotData;

public abstract class VirtualWave implements java.io.Serializable {

    double sX, sY;
    double tX, tY;
    double h;
    double fp, bv;
    int d;
    boolean cf;
    long ct;

    public VirtualWave() {
        sX = 0.0;
        sY = 0.0;
        h = 0.0;
        fp = Utils.bulletFirePower(10000);
        bv = Utils.bulletVelocity(fp);
        d = 0;
        ct = 0;
        cf = false;
    }

    public VirtualWave(double Start_X, double Start_Y, double Heading, double FirePower, int Direction,
            boolean couldFire, long CreationTime) {
        sX = Start_X;
        sY = Start_Y;
        h = Heading;
        fp = FirePower;
        bv = Utils.bulletVelocity(fp);
        d = Direction;
        ct = CreationTime;
        cf = couldFire;

        // System.out.println("X: " + sX + " Y: " + sY + " Heading: " + h + "
        // FirePower: " + fp + " Direction: " + d +
        // " CreationTime: " + ct);
    }

    public double maxEscapeAngle() {
        return Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(fp));
    }

    public double getStartX() {
        return sX;
    }

    public double getStartY() {
        return sY;
    }

    public double getHeading() {
        return h;
    }

    public boolean didFire() {
        return cf;
    }

    public double getFirePower() {
        return fp;
    }

    public int getDirection() {
        return d;
    }

    public double getDist(long t) {
        return bv * (t - ct);
    }

    public double distToImpact(double x, double y, long t) {
        double dist = Utils.getDist(sX, sY, x, y);
        dist -= getDist(t);
        return dist;
    }

    public boolean testHit(RobotData Robot, long t) {
        return testHit(Robot.getX(), Robot.getY(), t);
    }

    public boolean testHit(Rectangle2D Robot, long t) {
        return testHit(Robot.getCenterX(), Robot.getCenterY(), t);
    }

    public boolean testHit(double x, double y, long t) {
        return Utils.getDistSq(sX, sY, x, y) <= getDist(t) * getDist(t);
    }

    public double getGuessFactor(RobotData Robot) {
        return getGuessFactor(Robot.getX(), Robot.getY());
    }

    public double getGuessFactor(Rectangle2D Robot) {
        return getGuessFactor(Robot.getCenterX(), Robot.getCenterY());
    }

    public double getGuessFactor(double x, double y) {
        double desiredDirection = Utils.atan2(x - sX, y - sY);
        double angleOffset = Utils.relative(desiredDirection - h);
        double guessFactor = Math.max(-1, Math.min(1, angleOffset / maxEscapeAngle())) * d;
        return guessFactor;
    }


}
