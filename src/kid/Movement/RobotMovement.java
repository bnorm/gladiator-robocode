package kid.Movement;

import java.awt.geom.*;

import kid.*;
import kid.Data.*;
import kid.Data.Robot.*;
import robocode.*;

public class RobotMovement {

    private Robot r;
    private AdvancedRobot ar;
    private TeamRobot tr;
    private MyRobotsInfo i;

    /**
     * Switch Robot Number, <br> = 1, if r is != to null, <br> = 2, if ar is !=
     * to null, <br> = 3, if tr is != to null, <br> = 0, if all are == to null,
     * <br>
     * This int makes some methods look better, and run faster.
     */
    private final int srn;

    private double Move = 0.0;
    private long Time_Move = 0;
    public double getMove() {
        if (Time_Move == i.getTime())
            return Move;
        else
            return 0.0;
    }

    private double Turn = 0.0;
    private long Time_Turn = 0;
    public double getTurn() {
        if (Time_Turn == i.getTime())
            return Turn;
        else
            return 0.0;
    }


    public RobotMovement() {
        this(null, null, null);
    }

    public RobotMovement(Robot MyRobot) {
        this(MyRobot, null, null);
    }

    public RobotMovement(AdvancedRobot MyRobot) {
        this(null, MyRobot, null);
    }

    public RobotMovement(TeamRobot MyRobot) {
        this(null, null, MyRobot);
    }

    public RobotMovement(Robot robot, AdvancedRobot advancedRobot, TeamRobot teamRobot) {
        r = robot;
        ar = advancedRobot;
        tr = teamRobot;
        srn = (r != null ? 1 : (ar != null ? 2 : (tr != null ? 3 : 0)));
        i = new MyRobotsInfo(r, ar, tr);
    }


    // ***** THESE METHODS MAKE THIS ROBOT TURN OR MAVE *****//
    // THESE METHODS ARE MORE TO HELP THE OTHER METHODS THEN TO AID THE USER.
    // NOT FINAL FOR TESTING.
    public void right(double a) {
        switch (srn) {
        case 1:
            r.turnRight(a);
            Turn = a;
            Time_Turn = i.getTime();
            break;
        case 2:
            ar.turnRight(a);
            Turn = a;
            Time_Turn = i.getTime();
            break;
        case 3:
            tr.turnRight(a);
            Turn = a;
            Time_Turn = i.getTime();
        }
    }

    public void setRight(double a) {
        switch (srn) {
        case 2:
            ar.setTurnRight(a);
            Turn = a;
            Time_Turn = i.getTime();
            break;
        case 3:
            tr.setTurnRight(a);
            Turn = a;
            Time_Turn = i.getTime();
        }
    }

    public void ahead(double d) {
        // if (d > i.getFutureVelocity(d))
        // d = i.getFutureVelocity(d);
        switch (srn) {
        case 1:
            r.ahead(d);
            Move = d;
            Time_Move = i.getTime();
            break;
        case 2:
            ar.ahead(d);
            Move = d;
            Time_Move = i.getTime();
            break;
        case 3:
            tr.ahead(d);
            Move = d;
            Time_Move = i.getTime();
        }
    }

    public void setAhead(double d) {
        switch (srn) {
        case 2:
            ar.setAhead(d);
            Move = d;
            Time_Move = i.getTime();
            break;
        case 3:
            tr.setAhead(d);
            Move = d;
            Time_Move = i.getTime();
        }
    }

    public void left(double a) {
        switch (srn) {
        case 1:
            r.turnLeft(a);
            Turn = a;
            Time_Turn = i.getTime();
            break;
        case 2:
            ar.turnLeft(a);
            Turn = a;
            Time_Turn = i.getTime();
            break;
        case 3:
            tr.turnLeft(a);
            Turn = a;
            Time_Turn = i.getTime();
        }
    }

    public void setLeft(double a) {
        switch (srn) {
        case 2:
            ar.setTurnLeft(a);
            Turn = a;
            Time_Turn = i.getTime();
            break;
        case 3:
            tr.setTurnLeft(a);
            Turn = a;
            Time_Turn = i.getTime();
        }
    }

    public void back(double d) {
        // if (d > i.getFutureVelocity(d))
        // d = i.getFutureVelocity(d);
        switch (srn) {
        case 1:
            r.back(d);
            Move = d;
            Time_Move = i.getTime();
            break;
        case 2:
            ar.back(d);
            Move = d;
            Time_Move = i.getTime();
            break;
        case 3:
            tr.back(d);
            Move = d;
            Time_Move = i.getTime();
        }
    }

    public void setBack(double d) {
        switch (srn) {
        case 2:
            ar.setBack(d);
            Move = d;
            Time_Move = i.getTime();
            break;
        case 3:
            tr.setBack(d);
            Move = d;
            Time_Move = i.getTime();
        }
    }

    // ***** THESE METHODS MAKE THIS ROBOT TURN RELATIVE TO A (X,Y) POINT
    // *****//
    public final void turnToXY(double x, double y) {
        double theta = i.RobotBearingTo(x, y);
        right(theta);
    }

    public final void setTurnToXY(double x, double y) {
        double theta = i.RobotBearingTo(x, y);
        setRight(theta);
    }

    public final void turnPerpenToXY(double x, double y) {
        double theta = Utils.relative(i.RobotBearingTo(x, y) + 90);
        right(theta);
    }

    public final void setTurnPerpenToXY(double x, double y) {
        double theta = Utils.relative(i.RobotBearingTo(x, y) + 90);
        setRight(theta);
    }

    // RETURNS 1 IF THIS ROBOT NEEDS TO GO FORWORDS.
    // RETURNS -1 IF THIS ROBOT NEEDS TO GO BACKWARDS.
    public final int turnToXYwBF(double x, double y) {
        double theta = i.AngleTo(x, y);
        return turnToAnglewBF(theta);
    }

    public final int setTurnToXYwBF(double x, double y) {
        double theta = i.AngleTo(x, y);
        return setTurnToAnglewBF(theta);
    }

    public final int turnPerpenToXYwBF(double x, double y) {
        double theta = Utils.relative(i.AngleTo(x, y) + 90);
        return turnToAnglewBF(theta);
    }

    public final int setTurnPerpenToXYwBF(double x, double y) {
        double theta = Utils.relative(i.AngleTo(x, y) + 90);
        return setTurnToAnglewBF(theta);
    }

    public final int turnPerpenToXYwBFwDC(double x, double y, double cDist) {
        return setTurnPerpenToAnglewBFwDC(i.AngleTo(x, y), i.DistTo(x, y), cDist);
    }

    public final int setTurnPerpenToXYwBFwDC(double x, double y, double cDist) {
        return setTurnPerpenToAnglewBFwDC(i.AngleTo(x, y), i.DistTo(x, y), cDist);
    }

    public final void setTurnPerpenToXYwBFwDCwRM(double x, double y, double cDist) {
        setTurnPerpenToAnglewBFwDCwRM(i.AngleTo(x, y), i.DistTo(x, y), cDist);
    }


    // ***** THESE METHODS MAKE THIS ROBOT MOVE RELATIVE TO A (X,Y) POINT
    // *****//
    public final void moveToXY(double x, double y) {
        ahead((i.DistTo(x, y)) * turnToXYwBF(x, y));
    }

    public final void setMoveToXY(double x, double y) {
        setAhead((i.DistTo(x, y)) * setTurnToXYwBF(x, y));
    }


    // ***** THESE METHODS MAKE THIS ROBOT MOVE RELATIVE TO A POINT *****//
    public final void turnToPoint(Point2D point) {
        double theta = i.RobotBearingTo(point);
        right(theta);
    }

    public final void setTurnToPoint(Point2D point) {
        double theta = i.RobotBearingTo(point);
        setRight(theta);
    }

    public final void turnPerpenToPoint(Point2D point) {
        double theta = Utils.relative(i.RobotBearingTo(point) + 90);
        right(theta);
    }

    public final void setTurnPerpenToPoint(Point2D point) {
        double theta = Utils.relative(i.RobotBearingTo(point) + 90);
        setRight(theta);
    }

    // RETURNS 1 IF THIS ROBOT NEEDS TO GO FORWORDS.
    // RETURNS -1 IF THIS ROBOT NEEDS TO GO BACKWARDS.
    public final int turnToPointwBF(Point2D point) {
        double theta = i.AngleTo(point);
        return turnToAnglewBF(theta);
    }

    public final int setTurnToPointwBF(Point2D point) {
        double theta = i.AngleTo(point);
        return setTurnToAnglewBF(theta);
    }

    public final int turnPerpenToPointwBF(Point2D point) {
        double theta = Utils.relative(i.AngleTo(point) + 90);
        return turnToAnglewBF(theta);
    }

    public final int setTurnPerpenToPointwBF(Point2D point) {
        double theta = Utils.relative(i.AngleTo(point) + 90);
        return setTurnToAnglewBF(theta);
    }

    public final int turnPerpenToPointwBFwDC(Point2D point, double cDist) {
        return turnPerpenToAnglewBFwDC(i.AngleTo(point), i.DistTo(point), cDist);
    }

    public final int setTurnPerpenToPointwBFwDC(Point2D point, double cDist) {
        return setTurnPerpenToAnglewBFwDC(i.AngleTo(point), i.DistTo(point), cDist);
    }


    public final void setTurnPerpenToPointwBFwDCwRM(Point2D point, double cDist) {
        setTurnPerpenToAnglewBFwDCwRM(i.AngleTo(point), i.DistTo(point), cDist);
    }

    public final void moveToPoint(Point2D point) {
        moveToXY(point.getX(), point.getY());
    }

    public final void setMoveToPoint(Point2D point) {
        setMoveToXY(point.getX(), point.getY());
    }


    // ***** THESE METHODS MAKE THIS ROBOT MOVE RELATIVE TO AN ANGLE *****//
    public final void turnToAngle(double angle) {
        double theta = i.RobotBearingTo(angle);
        right(theta);
    }

    public final void setTurnToAngle(double angle) {
        double theta = i.RobotBearingTo(angle);
        setRight(theta);
    }

    public final void turnPerpenToAngle(double angle) {
        double theta = i.RobotBearingTo(angle + 90);
        right(theta);
    }

    public final void setTurnPerpenToAngle(double angle) {
        double theta = i.RobotBearingTo(angle + 90);
        setRight(theta);
    }

    public final void turnPerpenToAnglewDC(double angle, double dist, double cDist) {
        double f = DistFactor(dist, cDist);
        double theta = i.RobotBearingTo(angle + (90 * f));
        right(theta);
    }

    public final void setTurnPerpenToAnglewDC(double angle, double dist, double cDist) {
        double f = DistFactor(dist, cDist);
        double theta = i.RobotBearingTo(angle + (90 * f));
        setRight(theta);
    }

    // RETURNS 1 IF THIS ROBOT NEEDS TO GO FORWORDS.
    // RETURNS -1 IF THIS ROBOT NEEDS TO GO BACKWARDS.
    public final int turnToAnglewBF(double angle) {
        double theta = i.RobotBearingTo(angle);
        if (Math.abs(theta) < 90) {
            right(theta);
            return 1;
        } else {
            right(Utils.oppositeRelative(theta));
            return -1;
        }
    }

    public final int setTurnToAnglewBF(double angle) {
        double theta = i.RobotBearingTo(angle);
        if (Math.abs(theta) < 90) {
            setRight(theta);
            return 1;
        } else {
            setRight(Utils.oppositeRelative(theta));
            return -1;
        }
    }

    public final int turnPerpenToAnglewBF(double angle) {
        double theta = Utils.relative(angle + 90);
        return turnToAnglewBF(theta);
    }

    public final int setTurnPerpenToAnglewBF(double angle) {
        double theta = Utils.relative(angle + 90);
        return setTurnToAnglewBF(theta);
    }

    public final int turnPerpenToAnglewBFwDC(double angle, double dist, double cDist) {
        double f = DistFactor(dist, cDist);
        double right = i.RobotBearingTo(angle + (90 * f));
        double left = i.RobotBearingTo(angle - (90 * f));
        double ahead = Utils.absMin(right, left);
        double back = Utils.absMin(Utils.oppositeRelative(right), Utils.oppositeRelative(left));
        if (Math.abs(ahead) < Math.abs(back)) {
            right(ahead);
            return 1;
        } else {
            right(back);
            return -1;
        }
    }

    public final int setTurnPerpenToAnglewBFwDC(double angle, double dist, double cDist) {
        double f = DistFactor(dist, cDist);
        double right = i.RobotBearingTo(angle + (90 * f));
        double left = i.RobotBearingTo(angle - (90 * f));
        double ahead = Utils.absMin(right, left);
        double back = Utils.absMin(Utils.oppositeRelative(right), Utils.oppositeRelative(left));
        if (Math.abs(ahead) < Math.abs(back)) {
            setRight(ahead);
            return 1;
        } else {
            setRight(back);
            return -1;
        }
    }


    public final void setTurnPerpenToAnglewBFwDCwRM(double angle, double dist, double cDist) {
        double f = DistFactor(dist, cDist);
        double right = i.RobotBearingTo(angle + (90 * f));
        double left = i.RobotBearingTo(angle - (90 * f));
        double ahead = Utils.absMin(right, left);
        double back = Utils.absMin(Utils.oppositeRelative(right), Utils.oppositeRelative(left));
        if (i.getRobotMovingSign() == -1)
            setRight(back);
        else
            setRight(ahead);
    }


    // ***** THESE METHODS MAKE THIS ROBOT MOVE RELATIVE TO AN OTHER ROBOT
    // *****//
    public final void turnToRobot(EnemyData robot) {
        double theta = i.RobotBearingTo(robot);
        right(theta);
    }

    public final void setTurnToRobot(EnemyData robot) {
        double theta = i.RobotBearingTo(robot);
        setRight(theta);
    }

    public final void turnPerpenToRobot(EnemyData robot) {
        double theta = Utils.relative(i.RobotBearingTo(robot) + 90);
        right(theta);
    }

    public final void setTurnPerpenToRobot(EnemyData robot) {
        double theta = Utils.relative(i.RobotBearingTo(robot) + 90);
        setRight(theta);
    }

    // RETURNS 1 IF THIS ROBOT NEEDS TO GO FORWORDS.
    // RETURNS -1 IF THIS ROBOT NEEDS TO GO BACKWARDS.
    public final int turnToRobotwBF(EnemyData robot) {
        double theta = i.AngleTo(robot);
        return turnToAnglewBF(theta);
    }

    public final int setTurnToRobotwBF(EnemyData robot) {
        double theta = i.AngleTo(robot);
        return setTurnToAnglewBF(theta);
    }

    public final int turnPerpenToRobotwBF(EnemyData robot) {
        double theta = Utils.relative(i.AngleTo(robot) + 90);
        return turnToAnglewBF(theta);
    }

    public final int setTurnPerpenToRobotwBF(EnemyData robot) {
        double theta = Utils.relative(i.AngleTo(robot) + 90);
        return setTurnToAnglewBF(theta);
    }

    public final int turnPerpenToRobotwBFwDC(EnemyData robot, double cDist) {
        return turnPerpenToAnglewBFwDC(i.AngleTo(robot), i.DistTo(robot), cDist);
    }

    public final int setTurnPerpenToRobotwBFwDC(EnemyData robot, double cDist) {
        return setTurnPerpenToAnglewBFwDC(i.AngleTo(robot), i.DistTo(robot), cDist);
    }


    public final void setTurnPerpenToRobotwBFwDCwRM(EnemyData robot, double cDist) {
        setTurnPerpenToAnglewBFwDCwRM(i.AngleTo(robot), i.DistTo(robot), cDist);
    }


    /**
     * @todo (1)(turnToSmoothWalls) make like setTurnToSmoothWalls but with no
     *       set
     */
    public final void turnToSmoothWalls() {
    }

    public final void setTurnToSmoothWalls() {
        double radius = i.getRobotTurnRadius(MyRobotsInfo.MAX_VELOCITY);
        double mX = i.getFutureX(), mY = i.getFutureY();
        double bfh = i.getBattleFieldHeight(), bfw = i.getBattleFieldWidth();

        double RAngle = -360.0;
        double LAngle = -360.0;
        double dist;
        double angle;

        boolean NORTH = false, EAST = false, SOUTH = false, WEST = false;

        dist = ((dist = mX - MyRobotsInfo.MIN_WALL_DIST - 10) < 0.0 ? 0.0 : dist);
        if (dist <= radius) {
            angle = Utils.asin((radius - dist) / radius);
            angle = (dist == 0.0 && radius == 0.0 ? 90.0 : angle);
            RAngle = -90.0 + angle;
            LAngle = -90.0 - angle;
            WEST = true;
        }

        dist = ((dist = bfw - mX - MyRobotsInfo.MIN_WALL_DIST - 10) < 0.0 ? 0.0 : dist);
        if (dist <= radius) {
            angle = Utils.asin((radius - dist) / radius);
            angle = (dist == 0.0 && radius == 0.0 ? 90.0 : angle);
            RAngle = 90.0 + angle;
            LAngle = 90.0 - angle;
            EAST = true;
        }

        dist = ((dist = mY - MyRobotsInfo.MIN_WALL_DIST - 10) < 0.0 ? 0.0 : dist);
        if (dist <= radius) {
            angle = Utils.asin((radius - dist) / radius);
            angle = (dist == 0.0 && radius == 0.0 ? 90.0 : angle);
            if (WEST) {
                LAngle = 180.0 - angle;
            } else if (EAST) {
                RAngle = -180.0 + angle;
            } else {
                RAngle = 180.0 + angle;
                LAngle = 180.0 - angle;
            }
            SOUTH = true;
        }

        dist = ((dist = bfh - mY - MyRobotsInfo.MIN_WALL_DIST - 10) < 0.0 ? 0.0 : dist);
        if (dist <= radius) {
            angle = Utils.asin((radius - dist) / radius);
            angle = (dist == 0.0 && radius == 0.0 ? 90.0 : angle);
            if (WEST) {
                RAngle = angle;
            } else if (EAST) {
                LAngle = -angle;
            } else {
                RAngle = angle;
                LAngle = -angle;
            }
            NORTH = true;
        }

        if (!(NORTH || SOUTH || EAST || WEST))
            return;

        boolean twoWalls = false;
        if ((NORTH && (EAST || WEST)) || (SOUTH && (EAST || WEST)))
            twoWalls = true;

        double frb = i.RobotBearingTo(Utils.relative(RAngle)); // FRONT RIGHT
        // BEARING
        double flb = i.RobotBearingTo(Utils.relative(LAngle)); // FRONT LEFT
        // BEARING
        double brb = i.RobotBearingTo(Utils.oppositeRelative(RAngle)); // BACK
        // RIGHT
        // BEARING
        double blb = i.RobotBearingTo(Utils.oppositeRelative(LAngle)); // BACK
        // LEFT
        // BEARING

        if (i.getRobotMovingSign() > -1) {
            if (isAngleBetween(i.getRobotFrontHeading() + i.getRobotTurn(), LAngle, RAngle)
                    || (!isAngleBetween(i.getRobotFrontHeading() + i.getRobotTurn(), LAngle, RAngle) && twoWalls && Utils
                            .absolute(RAngle + (360 - LAngle)) > 180)) {
                if (Math.abs(frb) <= Math.abs(flb)) {
                    setRight(frb);
                } else {
                    setRight(flb);
                }
            }
        } else {
            if (isAngleBetween(i.getRobotBackHeading() + i.getRobotTurn(), LAngle, RAngle)
                    || (!isAngleBetween(i.getRobotBackHeading() + i.getRobotTurn(), LAngle, RAngle) && twoWalls && Utils
                            .absolute(RAngle + (360 - LAngle)) > 180)) {
                if (Math.abs(brb) <= Math.abs(blb)) {
                    setRight(brb);
                } else {
                    setRight(blb);
                }
            }
        }
    }


    public void ajustVectorForWall(RobotVector v) {
        RobotVector f = v;
        double x = f.getX(), y = f.getY();

        double radius = i.getRobotTurnRadius(MyRobotsInfo.MAX_VELOCITY);
        double bfh = i.getBattleFieldHeight(), bfw = i.getBattleFieldWidth();

        double RAngle = 0.0;
        double LAngle = 0.0;
        double dist;
        double angle;

        boolean NORTH = false, EAST = false, SOUTH = false, WEST = false;

        dist = ((dist = x - MyRobotsInfo.MIN_WALL_DIST) < 0.0 ? 0.0 : dist);
        if (dist <= radius) {
            angle = Utils.asin((radius - dist) / radius);
            angle = (dist == 0.0 && radius == 0.0 ? 90.0 : angle);
            RAngle = -90.0 + angle;
            LAngle = -90.0 - angle;
            WEST = true;
        }

        dist = ((dist = bfw - x - MyRobotsInfo.MIN_WALL_DIST) < 0.0 ? 0.0 : dist);
        if (dist <= radius) {
            angle = Utils.asin((radius - dist) / radius);
            angle = (dist == 0.0 && radius == 0.0 ? 90.0 : angle);
            RAngle = 90.0 + angle;
            LAngle = 90.0 - angle;
            EAST = true;
        }

        dist = ((dist = y - MyRobotsInfo.MIN_WALL_DIST) < 0.0 ? 0.0 : dist);
        if (dist <= radius) {
            angle = Utils.asin((radius - dist) / radius);
            angle = (dist == 0.0 && radius == 0.0 ? 90.0 : angle);
            if (WEST) {
                LAngle = 180.0 - angle;
            } else if (EAST) {
                RAngle = -180.0 + angle;
            } else {
                RAngle = 180.0 + angle;
                LAngle = 180.0 - angle;
            }
            SOUTH = true;
        }

        dist = ((dist = bfh - y - MyRobotsInfo.MIN_WALL_DIST) < 0.0 ? 0.0 : dist);
        if (dist <= radius) {
            angle = Utils.asin((radius - dist) / radius);
            angle = (dist == 0.0 && radius == 0.0 ? 90.0 : angle);
            if (WEST) {
                RAngle = angle;
            } else if (EAST) {
                LAngle = -angle;
            } else {
                RAngle = angle;
                LAngle = -angle;
            }
            NORTH = true;
        }

        if (!(NORTH || SOUTH || EAST || WEST))
            return;

        boolean twoWalls = false;
        if ((NORTH && (EAST || WEST)) || (SOUTH && (EAST || WEST)))
            twoWalls = true;

        double h = v.getHeading();
        // FRONT RIGHT BEARING
        double frb = Utils.relative(RAngle - h);
        // FRONT LEFT BEARING
        double flb = Utils.relative(LAngle - h);
        // BACK RIGHT BEARING
        double brb = Utils.oppositeRelative(RAngle - h);
        // BACK LEFT BEARING
        double blb = Utils.oppositeRelative(LAngle - h);

        double ajustangle = 0;

        if (v.getVelocity() > -1) {
            if (isAngleBetween(h, LAngle, RAngle)
                    || (!isAngleBetween(h, LAngle, RAngle) && twoWalls && Utils.absolute(RAngle + (360 - LAngle)) > 180)) {
                if (Math.abs(frb) <= Math.abs(flb)) {
                    ajustangle = frb;
                } else {
                    ajustangle = flb;
                }
            }
        } else {
            if (isAngleBetween(Utils.oppositeRelative(h), LAngle, RAngle)
                    || (!isAngleBetween(Utils.oppositeRelative(h), LAngle, RAngle) && twoWalls && Utils.absolute(RAngle
                            + (360 - LAngle)) > 180)) {
                if (Math.abs(brb) <= Math.abs(blb)) {
                    ajustangle = brb;
                } else {
                    ajustangle = blb;
                }
            }
        }
        v.setHeading(h + ajustangle);
    }

    public double getAngleForPerpenToRobotwDC(RobotVector v, double TargetVelocity, EnemyData EnemyRobot, double cDist) {
        double angle = Utils.getAngle(v.getX(), v.getY(), EnemyRobot.getX(), EnemyRobot.getY());
        double f = DistFactor(Utils.getDist(v.getX(), v.getY(), EnemyRobot.getX(), EnemyRobot.getY()), cDist);
        double right = Utils.relative(angle + (90 * f) - v.getHeading());
        double left = Utils.relative(angle - (90 * f) - v.getHeading());
        double ahead = Utils.absMin(right, left);
        double back = Utils.absMin(Utils.oppositeRelative(right), Utils.oppositeRelative(left));
        if (Utils.sign(TargetVelocity) == -1)
            return back;
        else
            return ahead;
    }

    // USE WITH DISTANCE CONTROLL.
    // f = 2 if to close, to f = 0 if to far away.
    private final double DistFactor(double Dist, double ControllDist) {
        return Math.max(Math.min(ControllDist / Dist, 1.75), 0.25);
    }

    // USED WITH WALL SMOOTHING.
    public final boolean isAngleBetween(double angle, double testAngle_1, double testAngle_2) {
        double bump = 360 - Utils.absolute(testAngle_1);
        angle = Utils.relative(angle + bump);
        testAngle_1 = Utils.relative(testAngle_1 + bump);
        testAngle_2 = Utils.relative(testAngle_2 + bump);
        return (angle > testAngle_1 && angle < testAngle_2);
    }

}
