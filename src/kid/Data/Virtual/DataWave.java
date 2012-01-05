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
        this.s = MyRobotsSectors;
    }

    public DataWave(Robot MyRobot, EnemyData EnemyRobot, double FirePower, double[] MyRobotsSectors) {
        super(MyRobot.getX(), MyRobot.getY(),
              Utils.getAngle(MyRobot.getX(), MyRobot.getY(), EnemyRobot.getX(), EnemyRobot.getY()), FirePower,
              (Utils.
               sin(EnemyRobot.getHeading() -
                   Utils.getAngle(MyRobot.getX(), MyRobot.getY(), EnemyRobot.getX(), EnemyRobot.getY())) *
               EnemyRobot.getVelocity()) < 0 ? -1 : 1, MyRobot.getGunHeat() == 0.0, MyRobot.getTime());
        o = new Observation(EnemyRobot, MyRobot);
        this.s = MyRobotsSectors;
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
