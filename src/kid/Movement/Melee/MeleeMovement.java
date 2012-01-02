package kid.Movement.Melee;

import kid.Data.*;
import kid.Data.Robot.*;
import kid.Data.Virtual.*;
import kid.Movement.*;
import robocode.*;
import kid.RobocodeGraphicsDrawer;

public abstract class MeleeMovement {

    protected Robot MyRobot;
    protected MyRobotsInfo i;
    protected RobotMovement Robot;

    protected MeleeMovement(Robot MyRobot) {
        this.MyRobot = MyRobot;
        Robot = new RobotMovement(MyRobot);
        i = new MyRobotsInfo(MyRobot);
    }

    protected MeleeMovement(AdvancedRobot MyRobot) {
        this.MyRobot = MyRobot;
        Robot = new RobotMovement(MyRobot);
        i = new MyRobotsInfo(MyRobot);
    }

    protected MeleeMovement(TeamRobot MyRobot) {
        this.MyRobot = MyRobot;
        Robot = new RobotMovement(MyRobot);
        i = new MyRobotsInfo(MyRobot);
    }

    public abstract void doMovement(EnemyData[] EnemyData);

    public abstract void doMovement(EnemyData[] EnemyData, TeammateData[] TeammateData, VirtualBullet[] VirtualBullets);

    public abstract void inEvent(Event e);

    public abstract void drawMovement(RobocodeGraphicsDrawer g, EnemyData[] EnemyData);

    public abstract void drawMovement(RobocodeGraphicsDrawer g, EnemyData[] EnemyData, TeammateData[] TeammateData,
                                      VirtualBullet[] VirtualBullets);

}
