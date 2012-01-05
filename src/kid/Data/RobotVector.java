package kid.Data;

import java.awt.geom.Point2D;
import kid.Utils;

public class RobotVector {

    double x;
    double y;
    double h;
    double v;

    public RobotVector(double x, double y, double heading, double velocity) {
        this.x = x;
        this.y = y;
        this.h = heading;
        this.v = velocity;
    }

    public RobotVector(Point2D point, double heading, double velocity) {
        this(point.getX(), point.getY(), heading, velocity);
    }

    public void moveVector(double TargetTurn, double TargetVelocity) {
        double nh = (Math.min(MyRobotsInfo.getRobotTurnRate(v), Math.abs(TargetTurn)) * Utils.sign(TargetTurn)) + h;
        double nv = MyRobotsInfo.getFutureVelocity(v, TargetVelocity);
        if (TargetVelocity != 0 || TargetVelocity != v) {
            this.x = Utils.getX(x, nv, nh);
            this.y = Utils.getY(y, nv, nh);
        }
        this.h = Utils.relative(nh);
        this.v = nv;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeading() {
        return h;
    }

    public double getVelocity() {
        return v;
    }

    public void setHeading(double h) {
        this.h = h;
    }

    public void turnVector(double TargetTurn) {
        double nh = (Math.min(MyRobotsInfo.getRobotTurnRate(v), Math.abs(TargetTurn)) * Utils.sign(TargetTurn)) + h;
        this.h = nh;
    }

    public void setVelocity(double v) {
        this.v = v;
    }

    public RobotVector getNextVector(double TargetTurn, double TargetVelocity) {
        double nh = (Math.min(MyRobotsInfo.getRobotTurnRate(v), Math.abs(TargetTurn)) * Utils.sign(TargetTurn)) + h;
        double nv = MyRobotsInfo.getFutureVelocity(v, TargetVelocity);
        return new RobotVector(Utils.getX(x, nv, nh), Utils.getY(y, nv, nh), nh, nv);
    }

    public RobotVector getNextVector(Point2D TargetPoint) {
        double tx = TargetPoint.getX(), ty = TargetPoint.getY();
        double tturn = Utils.relative(Utils.getAngle(x, y, tx, ty) - h);
        double nh = (Math.min(MyRobotsInfo.getRobotTurnRate(v), Math.abs(tturn)) * Utils.sign(tturn)) + h;
        double nv = MyRobotsInfo.getFutureVelocity(v, Utils.getDist(x, y, tx, ty));
        return new RobotVector(Utils.getX(x, nv, nh), Utils.getY(y, nv, nh), nh, nv);
    }

    public Object clone() {
        return new RobotVector(x, y, h, v);
    }

}
