package kid.Movement.OneOnOne;

import robocode.*;
import kid.Data.*;
import kid.Data.Robot.*;
import kid.Movement.*;
import kid.Debuger;
import kid.RobocodeGraphicsDrawer;

public abstract class OneOnOneMovement {

    protected Robot MyRobot;
    protected MyRobotsInfo i;
    protected RobotMovement Movement;
    protected boolean DEBUG = true;
    protected Debuger d;

    protected OneOnOneMovement(Robot MyRobot) {
        this.MyRobot = MyRobot;
        Movement = new RobotMovement(MyRobot);
        i = new MyRobotsInfo(MyRobot);
        d = new Debuger(MyRobot, DEBUG);
    }

    protected OneOnOneMovement(AdvancedRobot MyRobot) {
        this.MyRobot = MyRobot;
        Movement = new RobotMovement(MyRobot);
        i = new MyRobotsInfo(MyRobot);
        d = new Debuger(MyRobot, DEBUG);
    }

    protected OneOnOneMovement(TeamRobot MyRobot) {
        this.MyRobot = MyRobot;
        Movement = new RobotMovement(MyRobot);
        i = new MyRobotsInfo(MyRobot);
        d = new Debuger(MyRobot, DEBUG);
    }

    public abstract void doMovement(EnemyData EnemyData);

    public abstract void inEvent(Event e);

    public abstract void drawMovement(RobocodeGraphicsDrawer g, EnemyData EnemyData);

}
