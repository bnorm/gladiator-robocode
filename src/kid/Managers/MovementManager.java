package kid.Managers;

import robocode.*;
import kid.Movement.OneOnOne.OneOnOneMovement;
import kid.Movement.Melee.MeleeMovement;
import kid.Data.MyRobotsInfo;
import kid.Movement.RobotMovement;
import kid.RobocodeGraphicsDrawer;

/**
 * <p>
 * Title: MovementManager
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author KID
 * @version 0.1b
 */
public class MovementManager {

    OneOnOneMovement OneOnOne;
    MeleeMovement Melee;

    RobotMovement Robot;

    Robot MyRobot;
    MyRobotsInfo i;

    public MovementManager() {
        this(null, null, null);
    }

    public MovementManager(Robot MyRobot) {
        this(MyRobot, null, null);
        this.MyRobot = MyRobot;
    }

    public MovementManager(AdvancedRobot MyRobot) {
        this(null, MyRobot, null);
        this.MyRobot = MyRobot;
    }

    public MovementManager(TeamRobot MyRobot) {
        this(null, null, MyRobot);
        this.MyRobot = MyRobot;
    }

    private MovementManager(Robot Robot, AdvancedRobot AdvancedRobot, TeamRobot TeamRobot) {
        this.Robot = new RobotMovement(Robot, AdvancedRobot, TeamRobot);
        i = new MyRobotsInfo(Robot, AdvancedRobot, TeamRobot);
    }

    public void setMeleeMovement(MeleeMovement m) {
        Melee = m;
    }

    public void setOneOnOneMovement(OneOnOneMovement m) {
        OneOnOne = m;
    }

    public void inEvent(Event e) {
        if (Melee != null)
            Melee.inEvent(e);
        if (OneOnOne != null)
            OneOnOne.inEvent(e);
    }

    public void doMovement(DataManager Data) {
        if ((isMelee() || OneOnOne == null) && Melee != null) {
            if (i.getTeammates() == 0)
                Melee.doMovement(Data.getEnemys());
            else
                Melee.doMovement(Data.getEnemys(), Data.getTeammates(), Data.getTeammateBullets());
        } else if (OneOnOne != null) {
            OneOnOne.doMovement(Data.getColsestEnemy());
        }
    }

    public void drawMovement(RobocodeGraphicsDrawer g, DataManager Data) {
        if ((isMelee() || OneOnOne == null) && Melee != null) {
            if (i.getTeammates() == 0) {
                Melee.drawMovement(g, Data.getEnemys());
            } else
                Melee.drawMovement(g, Data.getEnemys(), Data.getTeammates(), Data.getTeammateBullets());
        } else if (OneOnOne != null) {
            OneOnOne.drawMovement(g, Data.getColsestEnemy());
        }
    }


    public boolean isMelee() {
        return i.getEnemys() > 1;
    }

    public boolean isOneOnOne() {
        return i.getEnemys() == 1;
    }

}
