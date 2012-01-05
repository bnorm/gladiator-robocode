package kid.Data.Virtual;

import java.awt.geom.*;

import kid.*;
import kid.Data.Robot.*;
import robocode.*;

public class VirtualBullet implements java.io.Serializable {

    private static final long serialVersionUID = 8647867336085921279L;

    private static final double maxdist = Utils.getDist(0D, 0D, 5000D, 5000D);

    private EnemyData Target;
    private Point2D StartingPosition;
    private double Heading;
    private double FirePower;
    private long CreationTime;

    public VirtualBullet(EnemyData Target, Point2D StartingPosition, double Heading, double FirePower,
                         long CreationTime) {
        this.Target = Target;
        this.StartingPosition = StartingPosition;
        this.Heading = Utils.relative(Heading);
        this.FirePower = FirePower;
        this.CreationTime = CreationTime;
    }

    public VirtualBullet(EnemyData Target, double StartX, double StartY, double Heading, double FirePower,
                         long CreationTime) {
        this(Target, new Point2D.Double(StartX, StartY), Heading, FirePower, CreationTime);
    }

    public VirtualBullet(Point2D StartingPosition, double Heading, double FirePower, long CreationTime) {
        this(null, StartingPosition, Heading, FirePower, CreationTime);
    }

    public VirtualBullet(double StartX, double StartY, double Heading, double FirePower, long CreationTime) {
        this(null, new Point2D.Double(StartX, StartY), Heading, FirePower, CreationTime);
    }

    public VirtualBullet(EnemyData Target, Bullet b, Robot MyRobot) {
        this(Target, new Point2D.Double(MyRobot.getX(), MyRobot.getY()), b.getHeading(),
             Utils.bulletFirePower(b.getVelocity()), MyRobot.getTime());
    }

    public VirtualBullet(Bullet b, Robot MyRobot) {
        this.StartingPosition = new Point2D.Double(MyRobot.getX(), MyRobot.getY());
        if (b != null) {
            this.Heading = Utils.relative(b.getHeading());
            this.FirePower = Utils.bulletFirePower(b.getVelocity());
        } else {
            this.Heading = 0.0;
            this.FirePower = Utils.bulletFirePower(10000D);
        }
        this.CreationTime = MyRobot.getTime();

    }


    public double getX(long t) {
        return Utils.getX(StartingPosition.getX(), getDist(t), Heading);
    }

    public double getY(long t) {
        return Utils.getY(StartingPosition.getY(), getDist(t), Heading);
    }

    public double getDist(long t) {
        return Utils.bulletVelocity(FirePower) * (t - CreationTime);
    }

    public double getFirePower() {
        return FirePower;
    }

    public Line2D getBulletLine(long t) {
        return new Line2D.Double(getX(t - 1), getY(t - 1), getX(t), getY(t));
    }

    public Point2D getBulletPoint(long t) {
        return new Point2D.Double(getX(t), getY(t));
    }


    public EnemyData getTarget() {
        return Target;
    }

    public boolean testActive(long t) {
        return getDist(t) < maxdist;
    }

    public boolean testHit(long t) {
        return testHit(getTarget().getRobot(), t);
    }

    public boolean testHit(Rectangle2D robot, long t) {
        return robot.contains(getBulletPoint(t));
    }


    public boolean testMissed(long t) {
        return testMissed(getTarget().getRobot(), t);
    }

    public boolean testMissed(Rectangle2D robot, long t) {
        return StartingPosition.distanceSq(robot.getX(), robot.getY()) < getDist(t) * getDist(t);
    }

}
