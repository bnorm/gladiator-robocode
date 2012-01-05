package kid.Data.Virtual;

import kid.Utils;
import java.awt.geom.Rectangle2D;
import kid.Data.MyRobotsInfo;

public abstract class VirtualWave implements java.io.Serializable {

    double sX, sY;
    double tX, tY;
    double h, fp;
    int d;
    boolean cf;
    long ct;

    public VirtualWave(double Start_X, double Start_Y, double Heading, double FirePower, int Direction, boolean couldFire,
                       long CreationTime) {
        sX = Start_X;
        sY = Start_Y;
        h = Heading;
        fp = FirePower;
        d = Direction;
        ct = CreationTime - 2;
        cf = couldFire;
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
        return Utils.bulletVelocity(fp) * (t - ct);
    }

    public boolean testHit(Rectangle2D Robot, long t) {
        if (Utils.getDistSq(sX, sY, Robot.getCenterX(), Robot.getCenterY()) <= getDist(t) * getDist(t))
            return true;
        return false;
    }

    public double getGuessFactor(Rectangle2D Robot) {
        double desiredDirection = Utils.atan2(Robot.getCenterX() - sX, Robot.getCenterY() - sY);
        double angleOffset = Utils.relative(desiredDirection - h);
        double guessFactor = Math.max( -1, Math.min(1, angleOffset / maxEscapeAngle())) * d;
        return guessFactor;
    }

}
