package kid.Data.Virtual;

import robocode.*;
import kid.Utils;
import kid.Data.Robot.*;

public class DataWave extends VirtualWave {

    private Observation o;

    public DataWave(double Start_X, double Start_Y, double Heading, double FirePower, int Direction, boolean CouldFire,
                    long CreationTime, Observation o) {
        super(Start_X, Start_Y, Heading, FirePower, Direction, CouldFire, CreationTime);
        this.o = o;
    }

    public DataWave(Robot MyRobot, EnemyData EnemyRobot, double FirePower) {
        super(MyRobot.getX(), MyRobot.getY(),
              Utils.getAngle(MyRobot.getX(), MyRobot.getY(), EnemyRobot.getX(), EnemyRobot.getY()), FirePower,
              (Utils.
               sin(EnemyRobot.getHeading() -
                   Utils.getAngle(MyRobot.getX(), MyRobot.getY(), EnemyRobot.getX(), EnemyRobot.getY())) *
               EnemyRobot.getVelocity()) < 0 ? -1 : 1, MyRobot.getGunHeat() == 0.0, MyRobot.getTime());
        o = new Observation(EnemyRobot, MyRobot);
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
