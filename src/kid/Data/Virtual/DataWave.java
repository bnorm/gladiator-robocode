package kid.Data.Virtual;

import robocode.*;
import kid.Utils;
import kid.Data.Robot.*;

public class DataWave extends VirtualWave {

    private static final long serialVersionUID = -9065938269590423309L;

    private Observation o;
    private double[] s;

    public DataWave(double Start_X, double Start_Y, double Heading, double FirePower, int Direction, boolean CouldFire,
            long CreationTime, Observation o, double[] MyRobotsSectors) {
        super(Start_X, Start_Y, Heading, FirePower, Direction, CouldFire, CreationTime);
        this.o = o;
        this.o.setVirtualWave(this);
        this.s = MyRobotsSectors;
    }

    public DataWave(Robot whoFired, EnemyData firedAt, double firePower, double[] firedAtSectors) {
        super(whoFired.getX(), whoFired.getY(), Utils.getAngle(whoFired.getX(), whoFired.getY(), firedAt.getX(),
                firedAt.getY()), firePower, (Utils.sin(firedAt.getHeading()
                - Utils.getAngle(whoFired.getX(), whoFired.getY(), firedAt.getX(), firedAt.getY())) * firedAt
                .getVelocity()) < 0 ? -1 : 1, whoFired.getGunHeat() == 0.0, whoFired.getTime());
        o = new Observation(firedAt, whoFired);
        o.setVirtualWave(this);
        this.s = firedAtSectors;
    }

    public double[] getSectors() {
        double[] a = new double[s.length];
        for (int i = 0; i < a.length; i++) {
            a[i] = s[i];
        }
        return a;
    }

    public double distToImpact(EnemyData EnemyRobot, long t) {
        double dist = Utils.getDist(sX, sY, EnemyRobot.getX(), EnemyRobot.getY());
        dist -= getDist(t);
        return dist;
    }

    public boolean testHit(EnemyData EnemyRobot, long t) {
        return testHit(EnemyRobot.getRobot(), t);
    }

    public double getGuessFactor(EnemyData enemy) {
        return getGuessFactor(enemy.getRobot());
    }
    public Observation getObservation() {
        return o;
    }

    public double getAngleOffset(EnemyData EnemyRobot) {
        double desiredDirection = Utils.atan2(EnemyRobot.getX() - sX, EnemyRobot.getY() - sY);
        double angleOffset = Utils.relative(desiredDirection - h);
        return angleOffset;
    }

}
