package kid.Movement.OneOnOne;

import robocode.*;
import kid.Data.*;
import kid.Data.Robot.*;
import kid.Movement.*;
import kid.RobocodeGraphicsDrawer;

public abstract class OneOnOneMovement {

    protected Robot MyRobot;
    protected MyRobotsInfo i;
    protected RobotMovement Movement;

    protected OneOnOneMovement(Robot MyRobot) {
        this.MyRobot = MyRobot;
        Movement = new RobotMovement(MyRobot);
        i = new MyRobotsInfo(MyRobot);
    }

    protected OneOnOneMovement(AdvancedRobot MyRobot) {
        this((Robot) MyRobot);
        Movement = new RobotMovement(MyRobot);
        i = new MyRobotsInfo(MyRobot);
    }

    protected OneOnOneMovement(TeamRobot MyRobot) {
        this((Robot) MyRobot);
        Movement = new RobotMovement(MyRobot);
        i = new MyRobotsInfo(MyRobot);
    }

    public abstract void doMovement(EnemyData EnemyData);

    public abstract void inEvent(Event e);

    public abstract void drawMovement(RobocodeGraphicsDrawer g, EnemyData EnemyData);

}
