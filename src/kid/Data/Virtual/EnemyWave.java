package kid.Data.Virtual;

import robocode.*;
import kid.Data.Robot.*;
import kid.Data.MyRobotsInfo;
import java.awt.geom.Rectangle2D;
import kid.Utils;

public class EnemyWave extends VirtualWave {

    private static final long serialVersionUID = -7549956564287468147L;

    private double[] MyRobotsSectors;
    private Observation o;
    private double disttotarget;

     public EnemyWave() {}

    public EnemyWave(double Start_X, double Start_Y, double Heading, double FirePower, int Direction,
            boolean CouldFire, long CreationTime, double[] MyRobotsSectors, Observation Observation) {
        super(Start_X, Start_Y, Heading, FirePower, Direction, CouldFire, CreationTime - 1);
        this.MyRobotsSectors = MyRobotsSectors;
        o = Observation;
        disttotarget = Utils.getDist(Start_X, Start_Y, o.getX(), o.getY());
    }

    public EnemyWave(Robot MyRobot, EnemyData EnemyRobot, double[] MyRobotsSectors) {
        super();
        o = new Observation(MyRobot, EnemyRobot);
        disttotarget = Utils.getDist(EnemyRobot.getX(), EnemyRobot.getY(), MyRobot.getX(), MyRobot.getY());
        
        sX = EnemyRobot.getX();
        sY = EnemyRobot.getY();
        h = Utils.getAngle(sX, sY, MyRobot.getX(), MyRobot.getY());
        fp = Math.abs(EnemyRobot.getDeltaEnergy());
        d = Utils.getDirection(MyRobot.getHeading(), MyRobot.getVelocity(), h);
        ct = MyRobot.getTime() - 1;
        cf = EnemyRobot.didFireBullet();
        this.MyRobotsSectors = MyRobotsSectors;
    }

    public Observation getObservation() {
        return o;
    }

    public double[] getMyRobotsSectors() {
        double[] a = new double[MyRobotsSectors.length];
        for (int i = 0; i < a.length; i++) {
            a[i] = MyRobotsSectors[i];
        }
        return a;
    }

    public boolean testHit(Robot MyRobot) {
        return testHit(new Rectangle2D.Double(MyRobot.getX() - MyRobotsInfo.WIDTH / 2, MyRobot.getY()
                + MyRobotsInfo.HEIGHT / 2, MyRobotsInfo.WIDTH, MyRobotsInfo.HEIGHT), MyRobot.getTime());
    }

    public int timeToImpact(Robot MyRobot) {
        double dist = distToImpact(MyRobot);
        double velocity = Utils.bulletVelocity(fp);
        return (int) (dist / (velocity));
    }

    public double distToImpact(Robot MyRobot) {
        double dist = Utils.getDist(sX, sY, MyRobot.getX(), MyRobot.getY());
        dist -= getDist(MyRobot.getTime());
        return dist;
    }

    public double distSqToImpact(Robot MyRobot) {
        double dist = Utils.getDistSq(sX, sY, MyRobot.getX(), MyRobot.getY());
        dist -= (getDist(MyRobot.getTime()) * getDist(MyRobot.getTime()));
        return dist;
    }

    public double getDistToAtFire() {
        return disttotarget;
    }

    public double getGuessFactor(Robot MyRobot) {
        double desiredDirection = Utils.atan2(MyRobot.getX() - sX, MyRobot.getY() - sY);
        double angleOffset = Utils.relative(desiredDirection - h);
        double guessFactor = Math.max(-1, Math.min(1, angleOffset / maxEscapeAngle())) * d;
        return guessFactor;
    }

    public double getAngleOffset(Robot MyRobot) {
        double desiredDirection = Utils.atan2(MyRobot.getX() - sX, MyRobot.getY() - sY);
        double angleOffset = Utils.relative(desiredDirection - h);
        return angleOffset;
    }

    public double getFirePower() {
        return fp;
    }

    public int getDirection() {
        return d;
    }


}
