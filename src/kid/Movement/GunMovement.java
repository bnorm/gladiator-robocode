package kid.Movement;

import kid.Data.MyRobotsInfo;
import kid.Data.Virtual.VirtualGun;
import kid.Data.Robot.EnemyData;
import kid.Targeting.Targeting;
import java.awt.geom.*;
import robocode.*;
import kid.Data.Virtual.VirtualGun;

public class GunMovement {

    private Robot r;
    private AdvancedRobot ar;
    private TeamRobot tr;

    private MyRobotsInfo i;

    /**
     * Switch Robot Number,
     *  = 1, if r is != to null,
     *  = 2, if ar is != to null,
     *  = 3, if tr is != to null,
     *  = 0, if all are == to null,
     * This int makes some methods
     * look better, and run faster.
     */
    private final int srn;


    public GunMovement() {
        this(null, null, null);
    }

    public GunMovement(Robot MyRobot) {
        this(MyRobot, null, null);
    }

    public GunMovement(AdvancedRobot MyRobot) {
        this(null, MyRobot, null);
    }

    public GunMovement(TeamRobot MyRobot) {
        this(null, null, MyRobot);
    }

    public GunMovement(Robot robot, AdvancedRobot advancedRobot, TeamRobot teamRobot) {
        r = robot;
        ar = advancedRobot;
        tr = teamRobot;
        srn = (r != null ? 1 : (ar != null ? 2 : (tr != null ? (int) 3 : 0)));
        i = new MyRobotsInfo(r, ar, tr);
    }


    public final void right(double a) {
        switch (srn) {
            case 1:
                r.turnGunRight(a);
                break;
            case 2:
                ar.turnGunRight(a);
                break;
            case 3:
                tr.turnGunRight(a);
                break;
            default:
                return;
        }
    }

    public final void setRight(double a) {
        switch (srn) {
            case 1:
                return;
            case 2:
                ar.setTurnGunRight(a);
                break;
            case 3:
                tr.setTurnGunRight(a);
                break;
            default:
                return;
        }
    }

    public final void left(double a) {
        switch (srn) {
            case 1:
                r.turnGunLeft(a);
                break;
            case 2:
                ar.turnGunLeft(a);
                break;
            case 3:
                tr.turnGunLeft(a);
                break;
            default:
                return;
        }
    }

    public final void setLeft(double a) {
        switch (srn) {
            case 1:
                return;
            case 2:
                ar.setTurnGunLeft(a);
                break;
            case 3:
                tr.setTurnGunLeft(a);
                break;
            default:
                return;
        }
    }


    public final void to(double x, double y) {
        double theta = i.GunBearingTo(x, y);
        right(theta);
    }

    public final void setTo(double x, double y) {
        double theta = i.GunBearingTo(x, y);
        setRight(theta);
    }


    public final void to(Point2D point) {
        double theta = i.GunBearingTo(point);
        right(theta);
    }

    public final void setTo(Point2D point) {
        double theta = i.GunBearingTo(point);
        setRight(theta);
    }


    public final void to(double angle) {
        double theta = i.GunBearingTo(angle);
        right(theta);
    }

    public final void setTo(double angle) {
        double theta = i.GunBearingTo(angle);
        setRight(theta);
    }


    public final void to(EnemyData EnemyRobot) {
        if (EnemyRobot != null && !EnemyRobot.isDead()) {
            double theta = i.GunBearingTo(EnemyRobot);
            right(theta);
        }
    }

    public final void setTo(EnemyData EnemyRobot) {
        if (EnemyRobot != null && !EnemyRobot.isDead()) {
            double theta = i.GunBearingTo(EnemyRobot);
            setRight(theta);
        }
    }

    public final void to(VirtualGun Gun, double firePower) {
        if (Gun != null && Gun.getTarget() != null && !Gun.getTarget().isDead())
            to(Gun.getTargeting().getTargetingAngle(Gun.getTarget(), firePower));
    }

    public final void setTo(VirtualGun Gun, double firePower) {
        if (Gun != null && Gun.getTarget() != null && !Gun.getTarget().isDead())
            setTo(Gun.getTargeting().getTargetingAngle(Gun.getTarget(), firePower));
    }

    public final void to(Targeting targeting, EnemyData EnemyRobot, double firePower) {
        if (EnemyRobot != null && targeting != null && !EnemyRobot.isDead())
            to(targeting.getTargetingAngle(EnemyRobot, firePower));
    }

    public final void setTo(Targeting targeting, EnemyData EnemyRobot, double firePower) {
        if (EnemyRobot != null && targeting != null && !EnemyRobot.isDead())
            setTo(targeting.getTargetingAngle(EnemyRobot, firePower));
    }

}
