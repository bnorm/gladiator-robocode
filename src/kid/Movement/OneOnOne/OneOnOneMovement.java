package kid.Movement.OneOnOne;

import kid.RobocodeGraphicsDrawer;
import kid.Data.RobotInfo;
import kid.Data.Robot.EnemyData;
import kid.Movement.RobotMovement;
import robocode.*;

public abstract class OneOnOneMovement {

    protected Robot MyRobot;
    protected RobotInfo i;
    protected RobotMovement Movement;

    protected OneOnOneMovement(Robot MyRobot) {
        this.MyRobot = MyRobot;
        Movement = new RobotMovement(MyRobot);
        i = new RobotInfo(MyRobot);
    }

    protected OneOnOneMovement(AdvancedRobot MyRobot) {
        this.MyRobot = MyRobot;
        Movement = new RobotMovement(MyRobot);
        i = new RobotInfo(MyRobot);
    }

    protected OneOnOneMovement(TeamRobot MyRobot) {
        this.MyRobot = MyRobot;
        Movement = new RobotMovement(MyRobot);
        i = new RobotInfo(MyRobot);
    }

    public abstract void doMovement(EnemyData EnemyData);

    public abstract void inEvent(Event e);

    public abstract void drawMovement(RobocodeGraphicsDrawer g, EnemyData EnemyData);

}
