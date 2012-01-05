package kid.Movement;

import robocode.*;
import kid.Utils;
import kid.Data.Robot.EnemyData;
import kid.Data.MyRobotsInfo;
import java.awt.geom.Point2D;
import java.util.Arrays;

//import java.awt.geom.Rectangle2D;

public class RadarMovement {

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

    private double x, y;
    private Point2D p;
    private double a;
    //private EnemyData b;

    private final double OneOnOneScanTolerance = 1;
    //private double MeleeScanTolerance = 1;
    private final double OneOnOneRadarGive = MyRobotsInfo.MAX_VELOCITY * 2; // this is in pixels
    //private final double MeleeRadarGive = MyRobotsInfo.MAX_VELOCITY * 2; // this is in pixels

    public RadarMovement() {
        this(null, null, null);
    }

    public RadarMovement(Robot MyRobot) {
        this(MyRobot, null, null);
    }

    public RadarMovement(AdvancedRobot MyRobot) {
        this(null, MyRobot, null);
    }

    public RadarMovement(TeamRobot MyRobot) {
        this(null, null, MyRobot);
    }

    public RadarMovement(Robot robot, AdvancedRobot advancedRobot, TeamRobot teamRobot) {
        r = robot;
        ar = advancedRobot;
        tr = teamRobot;
        srn = (r != null ? 1 : (ar != null ? 2 : (tr != null ? (int) 3 : 0)));
        i = new MyRobotsInfo(r, ar, tr);
    }

    public void OneOnOne(EnemyData EnemyRobot, double SweepAngle) {
        if (EnemyRobot == null || EnemyRobot.isDead()) {
            Melee();
            return;
        }
        switch (srn) {
            case 1:
                sweep(EnemyRobot, SweepAngle);
                return;
            case 2:
            case 3:
                setSweep(EnemyRobot, SweepAngle);
                break;
            default:
        }
    }

    public void OneOnOne_RadarControl(EnemyData EnemyRobot) {
        OneOnOne_RadarControl_TickScan(EnemyRobot, Integer.MAX_VALUE);
    }

    public void OneOnOne_TickScan(EnemyData EnemyRobot, double SweepAngle, int TickTolerance) {
        if (EnemyRobot == null || EnemyRobot.isDead()) {
            Melee();
            return;
        } else if (Math.abs(i.getTime() - EnemyRobot.getTime()) >= TickTolerance) {
            Melee();
            return;
        }
        switch (srn) {
            case 1:
                sweep(EnemyRobot, SweepAngle);
                return;
            case 2:
            case 3:
                break;
            default:
                return;
        }
        setSweep(EnemyRobot, SweepAngle);
    }

    public void OneOnOne_RadarControl_TickScan(EnemyData EnemyRobot, int TickTolerance) {
        if (EnemyRobot == null || EnemyRobot.isDead()) {
            Melee();
            return;
        } else if (Math.abs(i.getTime() - EnemyRobot.getTime()) >= TickTolerance) {
            Melee();
            return;
        }

        double SweepAngle = Utils.asin(((MyRobotsInfo.MAX_VELOCITY + OneOnOneRadarGive)) / i.DistTo(EnemyRobot));
        switch (srn) {
            case 1:
                sweep(EnemyRobot, SweepAngle);
                return;
            case 2:
            case 3:
                break;
            default:
                return;
        }
        setSweep(EnemyRobot, SweepAngle);
    }


    public void Melee() {
        switch (srn) {
            case 1:
                right(Double.POSITIVE_INFINITY);
                return;
            case 2:
            case 3:
                setRight(Double.POSITIVE_INFINITY);
                break;
            default:
                return;
        }
    }


    public void Melee_RadarControl(EnemyData[] EnemyData) {
        if (EnemyData == null) {
            Melee();
            return;
        }
        switch (srn) {
            case 1:
                scanArea(EnemyData);
                return;
            case 2:
            case 3:
                break;
            default:
                return;
        }
        setScanArea(EnemyData);
    }


    public void Melee_TickScan(EnemyData EnemyRobot, double SweepAngle, int TimeTolerance) {
        Melee_TickScan_GunHeat(EnemyRobot, SweepAngle, TimeTolerance, -.1);
    }

    public void Melee_GunHeat(EnemyData EnemyRobot, double SweepAngle, double gunLockHeat) {
        Melee_TickScan_GunHeat(EnemyRobot, SweepAngle, Integer.MAX_VALUE, gunLockHeat);
    }

    public void Melee_TickScan_GunHeat(EnemyData EnemyRobot, double SweepAngle, int TimeTolerance, double GunLockHeat) {
        if (EnemyRobot == null || EnemyRobot.isDead()) {
            Melee();
            return;
        }
        if ((i.getEnemys() == 1 || i.getGunHeat() < GunLockHeat)) {
            OneOnOne_TickScan(EnemyRobot, SweepAngle, TimeTolerance);
        } else {
            Melee();
        }
    }


    public void Melee_RadarControl_TickScan(EnemyData EnemyRobot, EnemyData[] EnemyData, int TimeTolerance) {
        Melee_RadarControl_TickScan_GunHeat(EnemyRobot, EnemyData, TimeTolerance, -.1);
    }

    public void Melee_RadarControl_GunHeat(EnemyData EnemyRobot, EnemyData[] EnemyData, double GunLockHeat) {
        Melee_RadarControl_TickScan_GunHeat(EnemyRobot, EnemyData, Integer.MAX_VALUE, GunLockHeat);
    }

    public void Melee_RadarControl_TickScan_GunHeat(EnemyData EnemyRobot, EnemyData[] EnemyData, int TimeTolerance,
            double GunLockHeat) {
        if (EnemyRobot == null || EnemyRobot.isDead()) {
            Melee();
            return;
        }
        if (i.getEnemys() == 1 || i.getGunHeat() < GunLockHeat) {
            OneOnOne_RadarControl_TickScan(EnemyRobot, TimeTolerance);
        } else {
            Melee_RadarControl(EnemyData);
        }
    }


    public final void right(double a) {
        switch (srn) {
            case 1:
                r.turnRadarRight(a);
                break;
            case 2:
                ar.turnRadarRight(a);
                break;
            case 3:
                tr.turnRadarRight(a);
                break;
            default:
                return;
        }
    }

    public final void setRight(double a) {
        switch (srn) {
            case 2:
                ar.setTurnRadarRight(a);
                break;
            case 3:
                tr.setTurnRadarRight(a);
                break;
            default:
                return;
        }
    }

    public final void left(double a) {
        switch (srn) {
            case 1:
                r.turnRadarLeft(a);
                break;
            case 2:
                ar.turnRadarLeft(a);
                break;
            case 3:
                tr.turnRadarLeft(a);
                break;
            default:
                return;
        }
    }

    public final void setLeft(double a) {
        switch (srn) {
            case 2:
                ar.setTurnRadarLeft(a);
                break;
            case 3:
                tr.setTurnRadarLeft(a);
                break;
            default:
                return;
        }
    }


    public final void to(double x, double y) {
        double theta = i.RadarBearingTo(x, y);
        right(theta);
    }

    public final void setTo(double x, double y) {
        double theta = i.RadarBearingTo(x, y);
        setRight(theta);
    }

    public final void sweep(double x, double y, double sweep) {
        double theta = i.RadarBearingTo(x, y);
        theta += Utils.sign(theta) * sweep;
        right(theta);
    }

    public final void setSweep(double x, double y, double sweep) {
        double theta = i.RadarBearingTo(x, y);
        theta += Utils.sign(theta) * sweep;
        setRight(theta);
    }

    public final void scanArea(double x_1, double y_1, double x_2, double y_2) {
        if (i.getRadarTurningSign() * i.RadarBearingTo(x_1, y_1) > OneOnOneScanTolerance) {
            x = x_2;
            y = y_2;
        } else if (i.getRadarTurningSign() * i.RadarBearingTo(x_2, y_2) > OneOnOneScanTolerance) {
            x = x_1;
            y = y_1;
        }
        to(x, y);
    }

    public final void setScanArea(double x_1, double y_1, double x_2, double y_2) {
        if (i.getRadarTurningSign() * i.RadarBearingTo(x_1, y_1) > OneOnOneScanTolerance) {
            x = x_2;
            y = y_2;
        } else if (i.getRadarTurningSign() * i.RadarBearingTo(x_2, y_2) > OneOnOneScanTolerance) {
            x = x_1;
            y = y_1;
        }
        setTo(x, y);
    }


    public final void to(Point2D point) {
        double theta = i.RadarBearingTo(point);
        right(theta);
    }

    public final void setTo(Point2D point) {
        double theta = i.RadarBearingTo(point);
        setRight(theta);
    }

    public final void sweep(Point2D point, double sweep) {
        double theta = i.RadarBearingTo(point);
        theta += Utils.sign(theta) * sweep;
        right(theta);
    }

    public final void setSweep(Point2D point, double sweep) {
        double theta = i.RadarBearingTo(point);
        theta += Utils.sign(theta) * sweep;
        setRight(theta);
    }

    public final void scanArea(Point2D point_1, Point2D point_2) {
        if (i.getRadarTurningSign() * i.RadarBearingTo(point_1) > OneOnOneScanTolerance) {
            p = point_2;
        } else if (i.getRadarTurningSign() * i.RadarBearingTo(point_2) > OneOnOneScanTolerance) {
            p = point_1;
        }
        to(p);
    }

    public final void setScanArea(Point2D point_1, Point2D point_2) {
        if (i.getRadarTurningSign() * i.RadarBearingTo(point_1) > OneOnOneScanTolerance) {
            p = point_2;
        } else if (i.getRadarTurningSign() * i.RadarBearingTo(point_2) > OneOnOneScanTolerance) {
            p = point_1;
        }
        setTo(p);
    }


    public final void to(double angle) {
        double theta = i.RadarBearingTo(angle);
        right(theta);
    }

    public final void setTo(double angle) {
        double theta = i.RadarBearingTo(angle);
        setRight(theta);
    }

    public final void sweep(double angle, double sweep) {
        double theta = i.RadarBearingTo(angle);
        theta += Utils.sign(theta) * sweep;
        right(theta);
    }

    public final void setSweep(double angle, double sweep) {
        double theta = i.RadarBearingTo(angle);
        theta += Utils.sign(theta) * sweep;
        setRight(theta);
    }

    public final void scanArea(double angle_1, double angle_2) {
        if (a != angle_1 || a != angle_2) {
            a = (Math.abs(i.RadarBearingTo(angle_1)) > Math.abs(i.RadarBearingTo(angle_2)) ? angle_2 : angle_1);
        } else {
            if (i.getRadarTurningSign() * i.RadarBearingTo(angle_1) > OneOnOneScanTolerance) {
                a = angle_2;
            } else if (i.getRadarTurningSign() * i.RadarBearingTo(angle_2) > OneOnOneScanTolerance) {
                a = angle_1;
            }
        }
        to(a);
    }

    public final void setScanArea(double angle_1, double angle_2) {
        if (a != angle_1 || a != angle_2) {
            a = Utils.absMin(i.RadarBearingTo(angle_1), i.RadarBearingTo(angle_2));
        } else {
            if (i.getRadarTurningSign() * i.RadarBearingTo(angle_1) > OneOnOneScanTolerance) {
                a = angle_2;
            } else if (i.getRadarTurningSign() * i.RadarBearingTo(angle_2) > OneOnOneScanTolerance) {
                a = angle_1;
            }
        }
        setTo(a);
    }


    public final void to(EnemyData robot) {
        double theta = i.RadarBearingTo(robot);
        right(theta);
    }

    public final void setTo(EnemyData robot) {
        double theta = i.RadarBearingTo(robot);
        setRight(theta);
    }

    public final void sweep(EnemyData robot, double sweep) {
        double theta = i.RadarBearingTo(robot);
        theta += Utils.sign(theta) * sweep;
        right(theta);
    }

    public final void setSweep(EnemyData robot, double sweep) {
        double theta = i.RadarBearingTo(robot);
        theta += Utils.sign(theta) * sweep;
        setRight(theta);
    }


    public final void scanArea(EnemyData[] robots) {}

    public final void setScanArea(EnemyData[] EnemyData) {
        if (EnemyData.length != i.getTotalEnemys()) {
            //System.out.println("Have Not Scaned All Enemys");
            setRight(Double.POSITIVE_INFINITY);
        } else if (EnemyData.length == 1) {
            //System.out.println("Only One Enemy");
            OneOnOne_RadarControl(EnemyData[0]);
        } else {

            double[] angles = new double[i.getEnemys()];

            for (int e = 0, a = 0; e < EnemyData.length; e++) {
                EnemyData enemy = EnemyData[e];
                if (enemy.isDead())
                    continue;
                else {
                    angles[a] = i.AngleTo(enemy);
                    a++;
                }
            }

            Arrays.sort(angles);

            double radarangle = Utils.relative(i.getRadarHeading());
            int radarturnsign = i.getRadarTurningSign();
            double nextangle = 0.0;

            for (int a = 0; a < angles.length; a++) {
                if (radarangle > angles[a] && radarangle < angles[(a + 1) % angles.length]) {
                    nextangle = angles[(a + radarturnsign + angles.length) % angles.length];
                    break;
                }
            }

            if (Utils.absolute(Math.abs(nextangle - radarangle)) < 180) {
                setRight(Double.POSITIVE_INFINITY * i.getRadarTurningSign());
                return;
            } else {
                setRight(Double.POSITIVE_INFINITY * i.getRadarTurningSign() * -1);
                return;
            }
        }
    }


}
