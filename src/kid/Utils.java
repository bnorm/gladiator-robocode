package kid;

import java.awt.geom.Point2D;

import kid.Data.MyRobotsInfo;
import kid.Data.Robot.EnemyData;
import robocode.Robot;

public class Utils {

    public static final double absMin(double value1, double value2) {
        return (Math.abs(value1) <= Math.abs(value2) ? value1 : value2);
    }

    public static final double limet(double min, double value, double max) {
        return Math.max(min, Math.min(value, max));
    }

    public static final double absolute(double angle) {
        angle %= 360;
        while (angle < 0.0)
            angle += 360;
        return angle;
    }

    public static final double acos(double n) {
        return Math.toDegrees(Math.acos(n));
    }

    public static final double asin(double n) {
        return Math.toDegrees(Math.asin(n));
    }

    public static final double atan(double n) {
        return Math.toDegrees(Math.atan(n));
    }

    public static final double atan2(double xDelta, double yDelta) {
        return Math.toDegrees(Math.atan2(xDelta, yDelta));
    }

    public final static double bulletDamage(double power) {
        return (int) Math.round(4 * power + 2 * Math.max(power - 1, 0));
    }

    public static final double bulletFirePower(double velocity) {
        return (velocity - 20) / -3;
    }

    public static final double bulletVelocity(double power) {
        return 20 - 3 * power;
    }

    public static final double cos(double n) {
        return Math.cos(Math.toRadians(n));
    }

    public static final double getAngle(double x1, double y1, double x2, double y2) {
        return atan2(x2 - x1, y2 - y1);
    }

    public static final double getAngle(Point2D p1, Point2D p2) {
        return getAngle(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public static final double getAngleOffset(double x, double y, EnemyData Enemy, double guessfactor, double FirePower) {
        double d = (Utils.sin(Enemy.getHeading() - getAngle(x, y, Enemy.getX(), Enemy.getY())) * Enemy.getVelocity()) < 0 ? -1
                : 1;
        return d * guessfactor * Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(FirePower));
    }


    public static final double getAngleOffset(Robot MyRobot, EnemyData Enemy, double guessfactor, double FirePower) {
        double d = (Utils
                .sin(Enemy.getHeading() - getAngle(MyRobot.getX(), MyRobot.getY(), Enemy.getX(), Enemy.getY())) * Enemy
                .getVelocity()) < 0 ? -1 : 1;
        return d * guessfactor * Utils.asin(MyRobotsInfo.MAX_VELOCITY / Utils.bulletVelocity(FirePower));
    }

    public static final int getDirection(double robotHeading, double robotVelocity, double angleToRobot) {
        return Utils.sign(Utils.sin(robotHeading - angleToRobot) * robotVelocity);
    }

    public static final double getDist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(getDistSq(x1, y1, x2, y2));
    }

    public static final int getDist(int x1, int y1, int x2, int y2) {
        return (int) Math.round(Math.sqrt(getDistSq(x1, y1, x2, y2)));
    }

    public static final double getDistSq(double x1, double y1, double x2, double y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    public static final int getDistSq(int x1, int y1, int x2, int y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    public static final double getDistSq(Point2D p, double x, double y) {
        return getDistSq(p.getX(), p.getY(), x, y);
    }

    public static final double getDistSq(Point2D p1, Point2D p2) {
        return getDistSq(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }


    public static final double getGuessFactor(int index, int listlength) {
        return (index - ((double) listlength - 1) / 2) / ((listlength - 1) / 2);
    }

    public static final int getIndex(double guessfactor, int listlength) {
        return (int) Math.round(((double) listlength - 1) / 2 * (guessfactor + 1));
    }

    public static final double getX(double x, double distance, double bearing) {
        return (x + (distance * sin(bearing)));
    }

    public static final double getY(double y, double distance, double bearing) {
        return (y + (distance * cos(bearing)));
    }

    public static final double oppositeAbsolute(double angle) {
        return absolute(angle - 180);
    }

    public static final double oppositeRelative(double angle) {
        return relative(angle - 180);
    }

    public static final double random(double n1, double n2) {
        return ((Math.max(n1, n2) - Math.min(n1, n2)) * Math.random()) + Math.min(n1, n2);
    }

    public static final double relative(double angle) {
        if (angle > -180 && angle <= 180)
            return angle;
        while (angle <= -180)
            angle += 360;
        while (angle > 180)
            angle -= 360;
        return angle;
    }

    public static final double rollingAvg(double value, double newEntry, double n, double weighting) {
        return (value * n + newEntry * weighting) / (n + weighting);
    }

    public static final double round(double n, double p) {
        return (Math.round(n * (1 / p)) / (1 / p));
    }

    public static final int roundUp(double n) {
        return (int) n + 1;
    }

    public static final int sign(double n) {
        return n < 0 ? -1 : 1;
    }

    public static final double sin(double n) {
        return Math.sin(Math.toRadians(n));
    }

    public static final double stdDev(double[] list) {
        return 0.0;
    }

    public static final double stdDev(double[] list, double[] freqlist) {
        if (list.length != freqlist.length)
            return Double.POSITIVE_INFINITY;
        double freqsum = 0;
        for (int i = 0; i < freqlist.length; i++) {
            freqsum += freqlist[i];
        }
        double[] sum = new double[list.length];
        double sumofsum = 0;
        for (int i = 0; i < list.length; i++) {
            sum[i] = list[i] * freqlist[i];
            sumofsum += sum[i];
        }
        double[] midptsq = new double[list.length];
        for (int i = 0; i < list.length; i++) {
            midptsq[i] = list[i] * list[i];
        }
        double[] sumsqs = new double[list.length];
        for (int i = 0; i < midptsq.length; i++) {
            sumsqs[i] = midptsq[i] * freqlist[i];
        }
        double sumsqssum = 0;
        for (int i = 0; i < sumsqs.length; i++) {
            sumsqssum += sumsqs[i];
        }

        double meansq = sumsqssum / freqsum;
        double mean = sumofsum / freqsum;

        return Math.sqrt(meansq - mean * mean);
    }

    public static final double stdDev(int[] list) {
        return 0.0;
    }

    public static final double stdDev(int[] list, int[] freqlist) {
        if (list.length != freqlist.length)
            return Double.POSITIVE_INFINITY;
        int freqsum = 0;
        for (int i = 0; i < freqlist.length; i++) {
            freqsum += freqlist[i];
        }
        int[] sum = new int[list.length];
        int sumofsum = 0;
        for (int i = 0; i < list.length; i++) {
            sum[i] = list[i] * freqlist[i];
            sumofsum += sum[i];
        }
        int[] midptsq = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            midptsq[i] = list[i] * list[i];
        }
        int[] sumsqs = new int[list.length];
        for (int i = 0; i < midptsq.length; i++) {
            sumsqs[i] = midptsq[i] * freqlist[i];
        }
        int sumsqssum = 0;
        for (int i = 0; i < sumsqs.length; i++) {
            sumsqssum += sumsqs[i];
        }

        double meansq = sumsqssum / freqsum;
        double mean = sumofsum / freqsum;

        return Math.sqrt(meansq - mean * mean);
    }

    public static final double tan(double n) {
        return Math.tan(Math.toRadians(n));
    }

    public static final double weightedAvg(double value_1, double weight_1, double value_2, double weight_2) {
        return (value_1 * weight_1 + value_2 * weight_2) / (weight_1 + weight_2);
    }

    private Utils() {
    }

}
