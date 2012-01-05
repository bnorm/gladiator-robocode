package kid.Movement;

import robocode.*;
import kid.Data.MyRobotsInfo;

public class VictoryMovement {

    MyRobotsInfo i;
    AdvancedRobot MyRobot;

    RobotMovement Robot;
    GunMovement Gun;

    public VictoryMovement() {
        Robot = new RobotMovement();
        Gun = new GunMovement();
    }

    public VictoryMovement(Robot MyRobot) {
        i = new MyRobotsInfo(MyRobot);
        Robot = new RobotMovement(MyRobot);
        Gun = new GunMovement(MyRobot);
    }

    public VictoryMovement(AdvancedRobot MyRobot) {
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
        Robot = new RobotMovement(MyRobot);
        Gun = new GunMovement(MyRobot);
    }

    public VictoryMovement(TeamRobot MyRobot) {
        this.MyRobot = MyRobot;
        i = new MyRobotsInfo(MyRobot);
        Robot = new RobotMovement(MyRobot);
        Gun = new GunMovement(MyRobot);
    }


    public void doRandomVicteryDanceS() {

    }

    public void doShakeShake() {
        int TURN = 1;
        int MOVE = 1;
        double danceTurn = 45;
        do {
            Robot.setAhead(Double.POSITIVE_INFINITY * MOVE);
            Robot.right(danceTurn * TURN);
            TURN *= -1;
            Robot.right(danceTurn * TURN);
            TURN *= -1;
            MOVE *= -1;
        } while (true);
    }

    public void doOrbit_wFire() {
        MyRobot.setMaxVelocity(3);
        for (; ; ) {
            Robot.setAhead(Double.POSITIVE_INFINITY);
            Robot.setRight(Double.POSITIVE_INFINITY);
            Gun.setTo(i.getRobotFrontHeading() - 90);
            MyRobot.setFire(0.1);
            MyRobot.execute();
        }
    }

}
