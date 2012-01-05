package kid.Data;

import java.awt.geom.*;
import java.io.*;

import kid.*;
import kid.Data.Robot.*;
import robocode.*;
import kid.Data.Virtual.VirtualBullet;

public class MyRobotsInfo implements java.io.Serializable {

    private static final long serialVersionUID = 1222200022791856596L;
    
    private Robot r;
    private Robot ir;
    private AdvancedRobot ar;
    private AdvancedRobot iar;
    private TeamRobot tr;

    public static final double WIDTH = 36.0;
    public static final double HEIGHT = 36.0;
    public static final double MIN_WALL_DIST = 18.1;
    public static final double ACCELERATION = 1.0;
    public static final double DECCELERATION = 2.0;
    public static final double MAX_VELOCITY = 8.0;
    public static final double MIN_VELOCITY = -8.0;

    public static final double MAX_ROBOT_TURN_RATE = 10.0;
    public static final double MAX_GUN_TURN_RATE = 20.0;
    public static final double MAX_RADAR_TURN_RATE = 45.0;

    private int enemys = 0;
    private int teammates = 0;


    public MyRobotsInfo(Robot MyRobot) {
        this(MyRobot, null, null);
    }

    public MyRobotsInfo(AdvancedRobot MyAdvancedRobot) {
        this(null, MyAdvancedRobot, null);
    }

    public MyRobotsInfo(TeamRobot MyTeamRobot) {
        this(null, null, MyTeamRobot);
    }

    public MyRobotsInfo(Robot MyRobot, AdvancedRobot MyAdvancedRobot, TeamRobot MyTeamRobot) {
        r = MyRobot;
        ar = MyAdvancedRobot;
        tr = MyTeamRobot;
        int srn = (r != null ? 1 : (ar != null ? 2 : (tr != null ? 3 : 0)));
        switch (srn) {
            case 2:
                iar = MyAdvancedRobot;
                break;
            case 3:
                iar = MyTeamRobot;
                break;
            default:
                return;
        }
        switch (srn) {
            case 1:
                ir = MyRobot;
                break;
            case 2:
                ir = MyAdvancedRobot;
                break;
            case 3:
                ir = MyTeamRobot;
                break;
            default:
                return;
        }
        enemys = getEnemys();
        teammates = getTeammates();
    }

    public long getTime() {
        return ir.getTime();
    }

    public int getRobotMovingSign() {
        return (getRobotMoveRemaining() == 0.0 ? 0 : Utils.sign(getRobotMoveRemaining()));
    }

    public double getRobotMoveRemaining() {
        if (iar != null) {
            return iar.getDistanceRemaining();
        }
        return 0.0;
    }

    public int getRobotTurningSign() {
        return (getRobotTurnRemaining() == 0.0 ? 0 : Utils.sign(getRobotTurnRemaining()));
    }

    public double getRobotTurnRemaining() {
        if (iar != null) {
            return iar.getTurnRemaining();
        }
        return 0.0;
    }


    public int getGunTurningSign() {
        return (getGunTurnRemaining() == 0.0 ? 0 : Utils.sign(getGunTurnRemaining()));
    }

    public double getGunTurnRemaining() {
        if (iar != null) {
            return iar.getGunTurnRemaining();
        }
        return 0.0;
    }


    public int getRadarTurningSign() {
        return (getRadarTurnRemaining() == 0.0 ? 0 : Utils.sign(getRadarTurnRemaining()));
    }

    public double getRadarTurnRemaining() {
        if (iar != null) {
            return iar.getRadarTurnRemaining();
        }
        return 0.0;
    }


    public double getRobotFrontHeading() {
        return Utils.relative(ir.getHeading());
    }

    public double getFutureRobotFrontHeading() {
        return Utils.relative(getRobotFrontHeading() + getRobotTurn());
    }

    public double getFutureRobotFrontHeading(double AngleTurn) {
        return Utils.relative(getRobotFrontHeading() + (getRobotTurnRate() * Utils.sign(AngleTurn)));
    }

    public double getFutureRobotFrontHeading(double AngleTurn, double Dist) {
        return Utils.relative(getRobotFrontHeading() +
                              (getRobotTurnRate(getFutureVelocity(Dist)) * Utils.sign(AngleTurn)));
    }

    public double getFutureRobotFrontHeading(double Heading, double Velocity, double AngleTurn) {
        return Utils.relative(Heading + (getRobotTurnRate(Velocity) * Utils.sign(AngleTurn)));
    }

    public double getRobotBackHeading() {
        return Utils.oppositeRelative(getRobotFrontHeading());
    }

    public double getFutureRobotBackHeading() {
        return Utils.oppositeRelative(getRobotFrontHeading() + getRobotTurn());
    }

    public double getFutureRobotBackHeading(double AngleTurn) {
        return Utils.oppositeRelative(getRobotFrontHeading() + (getRobotTurnRate() * Utils.sign(AngleTurn)));
    }

    public double getFutureRobotBackHeading(double AngleTurn, double Dist) {
        return Utils.oppositeRelative(getRobotFrontHeading() +
                              (getRobotTurnRate(getFutureVelocity(Dist)) * Utils.sign(AngleTurn)));
    }

    public double getFutureRobotBackHeading(double Heading, double Velocity, double AngleTurn) {
        return Utils.oppositeRelative(Heading + (getRobotTurnRate(Velocity) * Utils.sign(AngleTurn)));
    }

    public double getGunHeading() {
        return ir.getGunHeading();
    }

    public double getRadarHeading() {
        return ir.getRadarHeading();
    }


    public double getX() {
        return ir.getX();
    }

    public double getY() {
        return ir.getY();
    }

    public Point2D getMyPoint() {
        return new Point2D.Double(getX(), getY());
    }

    public double getFutureX() {
        return Utils.getX(getX(), getVelocity(), getRobotFrontHeading());
    }

    public double getFutureY() {
        return Utils.getY(getY(), getVelocity(), getRobotFrontHeading());
    }

    public double getFutureX(double velocity, double turnangle) {
        return Utils.getX(getX(), getFutureVelocity(velocity), getFutureRobotFrontHeading(turnangle, velocity));
    }

    public double getFutureY(double velocity, double turnangle) {
        return Utils.getY(getY(), getFutureVelocity(velocity), getFutureRobotFrontHeading(turnangle, velocity));
    }

    public Point2D getMyFuturePoint() {
        return new Point2D.Double(getFutureY(), getFutureY());
    }

    public boolean isInCorner() {
        boolean NORTH = isNereNorthWall(), EAST = isNereEastWall(), SOUTH = isNereSouthWall(), WEST = isNereWestWall();
        if ((NORTH && (EAST || WEST)) || (SOUTH && (EAST || WEST)))
            return true;
        return false;
    }

    public boolean isNereWall() {
        if (isNereNorthWall() || isNereEastWall() || isNereSouthWall() || isNereWestWall())
            return true;
        return false;
    }

    public boolean isNereNorthWall() {
        double dist = ((dist = getBattleFieldHeight() - getY() - MIN_WALL_DIST) < 0.0 ? 0.0 : dist);
        if (dist <= getRobotTurnRadius() || dist < HEIGHT)
            return true;
        return false;
    }

    public boolean isNereEastWall() {
        double dist = ((dist = getBattleFieldWidth() - getX() - MIN_WALL_DIST) < 0.0 ? 0.0 : dist);
        if (dist <= getRobotTurnRadius() || dist < WIDTH)
            return true;
        return false;
    }

    public boolean isNereSouthWall() {
        double dist = ((dist = getY() - MIN_WALL_DIST) < 0.0 ? 0.0 : dist);
        if (dist <= getRobotTurnRadius() || dist < HEIGHT)
            return true;
        return false;
    }

    public boolean isNereWestWall() {
        double dist = ((dist = getX() - MIN_WALL_DIST) < 0.0 ? 0.0 : dist);
        if (dist <= getRobotTurnRadius() || dist < WIDTH)
            return true;
        return false;
    }

    public double getBattleFieldHeight() {
        return ir.getBattleFieldHeight();
    }

    public double getBattleFieldWidth() {
        return ir.getBattleFieldWidth();
    }

    public Rectangle2D getBattleField() {
        return new Rectangle2D.Double(getBattleFieldWidth() / 2, getBattleFieldHeight() / 2, getBattleFieldWidth(),
                                      getBattleFieldHeight());
    }


    public double getVelocity() {
        return ir.getVelocity();
    }

    public static double getVelocity(double RobotTurnRate) {
        if (Math.abs(RobotTurnRate) >= MAX_ROBOT_TURN_RATE)
            return 0;
        return (Math.abs(RobotTurnRate) - MAX_ROBOT_TURN_RATE) / -0.75;
    }

    public double getFutureVelocity(double direction) {
        int vs = Utils.sign(getVelocity());
        int ds = Utils.sign(direction);
        if (ds * vs == -1) {
            return getVelocity() - DECCELERATION * vs;
        }
        return (getVelocity() + (vs * ACCELERATION) > MAX_VELOCITY * vs ? MAX_VELOCITY * vs :
                getVelocity() + (vs * ACCELERATION));
    }

    public static double getFutureVelocity(double startvelocity, double direction) {
        int vs = Utils.sign(startvelocity);
        int ds = Utils.sign(direction);
        if (direction == 0)
            return startvelocity - Math.min(DECCELERATION, Math.abs(startvelocity)) * vs;
        else if (ds * vs == -1)
            return startvelocity - DECCELERATION * vs;
        return (Math.abs(startvelocity + (vs * ACCELERATION)) > MAX_VELOCITY ? MAX_VELOCITY * vs :
                startvelocity + (vs * ACCELERATION));
    }


    public double getVelocityChangeDist(int velocity) {
        double dist = 0.0;
        double v = getVelocity();
        double sv = Utils.sign(v);
        if (Utils.sign(v) * Utils.sign(velocity) == -1)
            while (sv * v > 0) {
                v -= sv * DECCELERATION;
                dist += DECCELERATION;
            }
        while (Math.abs(v) < Math.abs(velocity)) {
            v += Utils.sign(v) * ACCELERATION;
            dist += ACCELERATION;
        }
        return dist;
    }

    public long getVelocityChangeTime(int velocity) {
        long time = 0;
        double v = getVelocity();
        double sv = Utils.sign(v);
        if (sv * Utils.sign(velocity) == -1)
            while (sv * v > 0) {
                v -= sv * DECCELERATION;
                time++;
            }
        while (Math.abs(v) < Math.abs(velocity)) {
            v += Utils.sign(v) * ACCELERATION;
            time++;
        }
        return time;
    }


    public double getRobotTurnRate() {
        return getRobotTurnRate(getVelocity());
    }

    public double getRobotTurn() {
        return getRobotTurnRate() * getRobotTurningSign();
    }


    public static double getRobotTurnRate(double v) {
        return (MAX_ROBOT_TURN_RATE - 0.75 * Math.abs(v));
    }

    public double getGunTurnRate(int RobotTurnSign, int GunTurnSign) {
        if (GunTurnSign == 0)
            return 0.0;
        boolean isAdjustGunForRobotTurn = false;
        if (iar != null) {
            isAdjustGunForRobotTurn = iar.isAdjustGunForRobotTurn();
        }
        if (isAdjustGunForRobotTurn || RobotTurnSign == 0) {
            return MAX_GUN_TURN_RATE;
        } else {
            if (GunTurnSign == RobotTurnSign) {
                return MAX_GUN_TURN_RATE + getRobotTurnRate();
            } else {
                return MAX_GUN_TURN_RATE - getRobotTurnRate();
            }
        }
    }

    public double getRadarTurnRate(int RobotTurnSign, int GunTurnSign, int RadarTurnSign) {
        if (RadarTurnSign == 0)
            return 0.0;
        boolean isAdjustRadarForGunTurn = false;
        if (iar != null) {
            isAdjustRadarForGunTurn = iar.isAdjustRadarForGunTurn();
        }
        if (isAdjustRadarForGunTurn || (RobotTurnSign == 0 && GunTurnSign == 0)) {
            return MAX_RADAR_TURN_RATE;
        } else {
            if (RadarTurnSign == GunTurnSign) {
                return MAX_RADAR_TURN_RATE + getGunTurnRate(RobotTurnSign, GunTurnSign);
            } else {
                return MAX_RADAR_TURN_RATE - getGunTurnRate(RobotTurnSign, GunTurnSign);
            }
        }
    }


    public double getRobotTurnRadius() {
        return getRobotTurnRadius(ir.getVelocity());
    }

    public double getRobotTurnRadius(double v) {
        return (180 * Math.abs(v)) / (Math.PI * getRobotTurnRate(v));
    }


    public final double RobotBearingTo(double x, double y) {
        return Utils.relative(AngleTo(x, y) - getRobotFrontHeading());
    }

    public final double RobotBearingTo(Point2D p) {
        return Utils.relative(AngleTo(p) - getRobotFrontHeading());
    }

    public final double RobotBearingTo(double a) {
        return Utils.relative(a - getRobotFrontHeading());
    }

    public final double RobotBearingTo(RobotData r) {
        return Utils.relative(AngleTo(r) - getRobotFrontHeading());
    }

    public final double RobotBackBearingTo(double x, double y) {
        return Utils.relative(AngleTo(x, y) - getRobotBackHeading());
    }

    public final double RobotBackBearingTo(Point2D p) {
        return Utils.relative(AngleTo(p) - getRobotBackHeading());
    }

    public final double RobotBackBearingTo(double a) {
        return Utils.relative(a - getRobotBackHeading());
    }

    public final double RobotBackBearingTo(RobotData r) {
        return Utils.relative(AngleTo(r) - getRobotBackHeading());
    }

    public final double GunBearingTo(double x, double y) {
        return Utils.relative(AngleTo(x, y) - getGunHeading());
    }

    public final double GunBearingTo(Point2D p) {
        return Utils.relative(AngleTo(p) - getGunHeading());
    }

    public final double GunBearingTo(double a) {
        return Utils.relative(a - getGunHeading());
    }

    public final double GunBearingTo(RobotData r) {
        return Utils.relative(AngleTo(r) - getGunHeading());
    }


    public final double RadarBearingTo(double x, double y) {
        return Utils.relative(AngleTo(x, y) - getRadarHeading());
    }

    public final double RadarBearingTo(Point2D p) {
        return Utils.relative(AngleTo(p) - getRadarHeading());
    }

    public final double RadarBearingTo(double a) {
        return Utils.relative(a - getRadarHeading());
    }

    public final double RadarBearingTo(RobotData r) {
        return Utils.relative(AngleTo(r) - getRadarHeading());
    }


    public final double AngleTo(double x, double y) {
        double theta = Utils.atan2(x - getX(), y - getY());
        return Utils.relative(theta);
    }

    public final double AngleTo(Point2D p) {
        double theta = Utils.atan2(p.getX() - getX(), p.getY() - getY());
        return Utils.relative(theta);
    }

    public final double AngleTo(RobotData r) {
        double theta = Utils.atan2(r.getX() - getX(), r.getY() - getY());
        return Utils.relative(theta);
    }


    public final double DistToSq(double x, double y) {
        return Utils.getDistSq(getX(), getY(), x, y);
    }

    public final double DistToSq(Point2D p) {
        return DistToSq(p.getX(), p.getY());
    }

    public final double DistToSq(RobotData r) {
        return DistToSq(r.getX(), r.getY());
    }

    public final double DistToSq(VirtualBullet b) {
        return DistToSq(b.getX(getTime()), b.getY(getTime()));
    }


    public final double DistTo(double x, double y) {
        return Math.sqrt(DistToSq(x, y));
    }

    public final double DistTo(Point2D p) {
        return Math.sqrt(DistToSq(p));
    }

    public final double DistTo(RobotData RobotData) {
        return Math.sqrt(DistToSq(RobotData));
    }

    public final double DistTo(VirtualBullet b) {
        return Math.sqrt(DistToSq(b));
    }

    public final double DistToWall(double a) {
        a = Utils.absolute(a);
        double bfh = getBattleFieldHeight();
        double bfw = getBattleFieldWidth();
        double wallDist = bfh - getY() - MIN_WALL_DIST;

        if (a < Utils.absolute(AngleTo(bfw, bfh))) {
        } else if (a < Utils.absolute(AngleTo(bfw, 0.0))) {
            a -= 90.0;
            wallDist = bfw - getX() - MIN_WALL_DIST;
        } else if (a < Utils.absolute(AngleTo(0.0, 0.0))) {
            a -= 180.0;
            wallDist = getY() - MIN_WALL_DIST;
        } else if (a < Utils.absolute(AngleTo(0.0, bfh))) {
            a -= 270.0;
            wallDist = getX() - MIN_WALL_DIST;
        }

        a = Utils.relative(a);
        return wallDist / Utils.cos(Math.abs(a));
    }

    public final int getOthers() {
        return ir.getOthers();
    }

    public final int getTotalOthers() {
        return getTotalEnemys() + getTotalTeammates();
    }

    public final int getEnemys() {
        return getOthers() - getTeammates();
    }

    public final int getTotalEnemys() {
        return enemys;
    }

    public final int getTeammates() {
        if (tr != null) {
            try {
                String[] teammates = tr.getTeammates();
                return teammates.length;
            } catch (Exception e) {}
        }
        return 0;
    }

    public final int getTotalTeammates() {
        return teammates;
    }

    public boolean isTeammate(String name) {
        if (tr != null) {
            return tr.isTeammate(name);
        }
        return false;
    }

    public final double getGunHeat() {
        return ir.getGunHeat();
    }

    public double getEnergy() {
        return ir.getEnergy();
    }

    public File getDataFile(String string) {
        return iar.getDataFile(string);
    }

}
