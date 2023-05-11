package kid;

import java.awt.geom.Point2D;
import java.util.List;

import kid.data.Data;
import kid.data.info.RobotInfo;
import kid.data.robot.RobotData;
import kid.data.virtual.*;
import kid.segmentation.Segmentable;
import robocode.*;

// TODO document class (50% complete)

/**
 * @author Brian Norman
 * @author Paul Evans
 * @version 0.0.1 beta
 */
public class Utils {

   /**
    * The <code>double</code> value of one-third.
    */
   public static final double ONE_THIRD = (1.0D / 3.0D);

   /**
    * The <code>double</code> value of two-thirds.
    */
   public static final double TWO_THIRDS = (2.0D / 3.0D);

   /**
    * The <code>double</code> value of a complete circle in degrees.
    */
   public static final double CIRCLE = 360.0D;

   /**
    * The <code>double</code> value of half a circle in degrees.
    */
   public static final double HALF_CIRCLE = CIRCLE / 2.0D;

   /**
    * The <code>double</code> value of one-fourth a circle in degrees.
    */
   public static final double QUARTER_CIRCLE = CIRCLE / 4.0D;

   /**
    * The <code>double</code> value of one-eightieth a circle in degrees.
    */
   public static final double EIGHTIETH_CIRCLE = CIRCLE / 8.0D;

   /**
    * The private constructor. Private so no one can initialize it.
    */
   private Utils() {
   }

   public static final double toRadians(double a) {
      return Math.PI * a / Utils.HALF_CIRCLE;
   }

   public static final double fromRadians(double a) {
      return Utils.HALF_CIRCLE * a / Math.PI;
   }

   /**
    * Returns the absolute smaller of two <code>double</code> values. That is, the result is the argument closer to
    * zero. If the arguments have the same value, the result is the first argument. If either value is <code>NaN</code>,
    * then the result is <code>NaN</code>.
    * 
    * @param a - a number.
    * @param b - another number.
    * @return the argument closer to zero.
    */
   public static final double absMin(double a, double b) {
      return (Math.abs(a) <= Math.abs(b) ? a : b);
   }

   /**
    * Returns the absolute greater of two <code>double</code> values. That is, the result is the argument farther from
    * zero. If the arguments have the same value, the result is the first argument. If either value is <code>NaN</code>,
    * then the result is <code>NaN</code>.
    * 
    * @param a - a number.
    * @param b - another number.
    * @return the argument farther from zero.
    */
   public static final double absMax(double a, double b) {
      return (Math.abs(a) >= Math.abs(b) ? a : b);
   }

   /**
    * Returns the absolute angle in degrees of the <code>double</code> value. That is, the result is between
    * <code>0.0</code> and <code>360.0</code>.
    * 
    * @param n The angle whose absolute angle is to be determined.
    * @return The absolute angle in degrees.
    */
   public static final double absolute(double n) {
      while (n < 0.0D)
         n += Utils.CIRCLE;
      while (n >= Utils.CIRCLE)
         n -= Utils.CIRCLE;
      return n;
   }

   /**
    * Returns the trigonometric sine of an angle. Special cases:
    * <ul>
    * <li>If the argument is <code>NaN</code> or an infinity, then the result is <code>NaN</code>.
    * <li>If the argument is zero, then the result is a zero with the same sign as the argument.
    * </ul>
    * <p>
    * The computed result must be within 1 ulp of the exact result. Results must be semi-monotonic.
    * 
    * @param n - an angle in degrees.
    * @return the sine of the argument.
    */
   public static final double sin(double n) {
      return Math.sin(Utils.toRadians(n));
   }

   /**
    * Returns the trigonometric cosine of an angle. Special cases:
    * <ul>
    * <li>If the argument is <code>NaN</code> or an infinity, then the result is <code>NaN</code>.
    * </ul>
    * <p>
    * The computed result must be within 1 ulp of the exact result. Results must be semi-monotonic.
    * 
    * @param n - an angle, in degrees.
    * @return the cosine of the argument.
    */
   public static final double cos(double n) {
      return Math.cos(Utils.toRadians(n));
   }

   /**
    * Returns the trigonometric tangent of an angle. Special cases:
    * <ul>
    * <li>If the argument is <code>NaN</code> or an infinity, then the result is <code>NaN</code>.
    * <li>If the argument is zero, then the result is a zero with the same sign as the argument.
    * </ul>
    * <p>
    * The computed result must be within 1 ulp of the exact result. Results must be semi-monotonic.
    * 
    * @param n - an angle, in degrees.
    * @return the tangent of the argument.
    */
   public static final double tan(double n) {
      return Math.tan(Utils.toRadians(n));
   }

   /**
    * Returns the arc sine of an angle, in the range of <code>-180.0</code> though <code>180.0</code>. Special
    * case:
    * <ul>
    * <li>If the argument is <code>NaN</code> or its absolute value is greater than 1, then the result is
    * <code>NaN</code>.
    * <li>If the argument is zero, then the result is a zero with the same sign as the argument.
    * </ul>
    * <p>
    * The computed result must be within 1 ulp of the exact result. Results must be semi-monotonic.
    * 
    * @param n - the value whose arc sine is to be returned.
    * @return the arc sine of the argument.
    */
   public static final double asin(double n) {
      return Utils.fromRadians(Math.asin(n));
   }

   /**
    * Returns the arc cosine of an angle, in the range of <code>0.0</code> through <code>180.0</code>. Special
    * case:
    * <ul>
    * <li>If the argument is <code>NaN</code> or its absolute value is greater than 1, then the result is
    * <code>NaN</code>.
    * </ul>
    * <p>
    * The computed result must be within 1 ulp of the exact result. Results must be semi-monotonic.
    * 
    * @param n - the value whose arc cosine is to be returned.
    * @return the arc cosine of the argument.
    */
   public static final double acos(double n) {
      return Utils.fromRadians(Math.acos(n));
   }

   /**
    * Returns the arc tangent of an angle, in the range of <code>-180.0</code> though <code>180.0</code>. Special
    * case:
    * <ul>
    * <li>If the argument is <code>NaN</code> or its absolute value is greater than 1, then the result is
    * <code>NaN</code>.
    * <li>If the argument is zero, then the result is a zero with the same sign as the argument.
    * </ul>
    * <p>
    * The computed result must be within 1 ulp of the exact result. Results must be semi-monotonic.
    * 
    * @param n - the value whose arc tangent is to be returned.
    * @return the arc tangent of the argument.
    */
   public static final double atan(double n) {
      return Utils.fromRadians(Math.atan(n));
   }

   /**
    * Converts rectangular coordinates (x, y) to compass (r, <i>theta</i>). This method computes the phase <i>theta</i>
    * by computing an arc tangent of x/y in the range of <code>-180.0</code> to <code>180.0</code>. Special cases:
    * <ul>
    * <li>If the argument is <code>NaN</code>, then the result is <code>NaN</code>.
    * <li>If the first argument is positive and the second argument is positive zero, or the first argument is positive
    * infinity and the second argument is positive and finite, then the result is positive zero.
    * <li>If the first argument is positive and the second argument is negative zero, or the first argument is positive
    * infinity and the second argument is negative and finite, then the result is negative zero.
    * <li>If the first argument is negative and the second argument is positive zero, or the first argument is negative
    * infinity and the second argument is positive and finite, then the result is the double value closest to
    * <code>180.0</code>.
    * <li>If the first argument is negative and the second argument is negative zero, or the first argument is negative
    * infinity and the second argument is negative and finite, then the result is the double value closest to
    * <code>-180.0</code>.
    * <li>If the first argument is positive zero or negative zero and the second argument is positive, or the first
    * argument is finite and the second argument is positive infinity, then the result is the double value closest to
    * <code>90.0</code>.
    * <li>If the first argument is positive zero or negative zero and the second argument is negative, or the first
    * argument is finite and the second argument is negative infinity, then the result is the double value closest to
    * <code>-90.0</code>.
    * <li>If both arguments are positive infinity, then the result is the double value closest to <code>45.0</code>.
    * <li>If the first argument is negative infinity and the second argument is positive infinity, then the result is
    * the double value closest to <code>135.0</code>.
    * <li>If the first argument is positive infinity and the second argument is negative infinity, then the result is
    * the double value closest to <code>-45.0</code>.
    * <li>If both arguments are negative infinity, then the result is the double value closest to <code>-135.0</code>.
    * </ul>
    * <p>
    * The computed result must be within 2 ulp of the exact result. Results must be semi-monotonic.
    * <p>
    * Note: "compass" is the coordinate system used in Robocode and is very similar to polar but with some differences:
    * <ul>
    * <li> Absolute zero (0.0) is pointing north.
    * <li> Angles increase clockwise.
    * </ul>
    * 
    * @param x - the ordinate coordinate
    * @param y - the abscissa coordinate
    * @return the <i>theta</i> component of the point (r, <i>theta</i>) in compass coordinates that corresponds to the
    *         point (x, y) in Cartesian coordinates.
    */
   public static final double atan2(double x, double y) {
      return Utils.fromRadians(Math.atan2(x, y));
   }

   /**
    * Returns the average of the array to <code>double</code> precision. Special cases:
    * <ul>
    * <li> If the array is <code>null</code>, then the result is <code>0.0</code>.
    * <li> If one of the values of the array is <code>NaN</code>, then the result is <code>NaN</code>.
    * </ul>
    * 
    * @param values - the array whose average is to be returned.
    * @return the average of the array.
    */
   public static final double avg(double[] values) {
      if (values == null)
         return 0.0D;
      double value = 0.0D;
      for (double item : values)
         value += item;
      return value / values.length;
   }

   /**
    * Returns the weighed average of the array to <code>double</code> precision. Special cases:
    * <ul>
    * <li> If either array is <code>null</code>, then the result is <code>0.0</code>.
    * <li> If the arrays are not the some length, then the result is <code>0.0</code>.
    * <li> If one of the values of the array is <code>NaN</code>, then the result is <code>NaN</code>.
    * </ul>
    * 
    * @param values - the array whose average is to be returned.
    * @param weight - the respective weight of each value in the array.
    * @return the weighed average of the array.
    */
   public static final double avg(double[] values, double[] weight) {
      if (values == null || weight == null || values.length != weight.length)
         return 0.0D;
      double total = 0.0D;
      double totalWeight = 0.0D;
      for (int index = 0; index < values.length; index++)
         total += (values[index] + (totalWeight += weight[index]));
      return total / totalWeight;
   }

   /**
    * Returns the square of the <code>double</code>. That is, the double multiplied be it's self once.
    * 
    * @param n - the number whose square is to be calculated.
    * @return the square of the argument.
    */
   public static final double sqr(double n) {
      return n * n;
   }

   /**
    * Returns the cube of a <code>double</code>. That is, the double multiplied be itself three times.
    * 
    * @param n - the number whose cube is to be calculated.
    * @return the cube of the argument.
    */
   public static final double cube(double n) {
      return n * n * n;
   }

   // BORED documentation: method
   public static final double angle(double deltaX, double deltaY) {
      return atan2(deltaX, deltaY);
   }

   /**
    * Returns the angle from the first point to the point. That is, the angle from <code>(x1, y1)</code> to
    * <code>(x2, y2)</code>.
    * 
    * @param x1 - the x coordinate of the first point
    * @param y1 - the y coordinate of the first point
    * @param x2 - the x coordinate of the second point
    * @param y2 - the y coordinate of the second point
    * @return the angle from the first point to the second
    */
   public static final double angle(double x1, double y1, double x2, double y2) {
      return angle(x2 - x1, y2 - y1);
   }

   /**
    * Returns the angle from the first robot to the second. That is, the angle from <code>r1</code> to <code>r2</code>.
    * 
    * @param r1 - the first robot
    * @param r2 - the second robot
    * @return the angle from the first robot to the second
    */
   public static final double angle(RobotData r1, RobotData r2) {
      return angle(r1.getX(), r1.getY(), r2.getX(), r2.getY());
   }

   /**
    * Returns the angle from the first point to the second. That is, the angle from <code>p1</code> to <code>p2</code>.
    * 
    * @param p1 - the first point
    * @param p2 - the second point
    * @return the angle from the first point to the second
    */
   public static final double angle(Point2D p1, Point2D p2) {
      return angle(p1.getX(), p1.getY(), p2.getX(), p2.getY());
   }

   /**
    * Returns the angle offset that the guess factor will introduce. That is, the angle differing from the the robots
    * position with relation to the guess factor and the maximum escape angle.
    * 
    * @param x
    * @param y
    * @param robot
    * @param guessFactor
    * @param firePower
    * @return the offset angle
    * @see #getMaxEscapeAngle(double)
    */
   public static final double getAngleOffset(double x, double y, RobotData robot, double guessFactor, double firePower) {
      double d = getDirection(robot.getHeading(), angle(x, y, robot.getX(), robot.getY()), robot.getVelocity());
      return d * guessFactor * getMaxEscapeAngle(firePower);
   }


   public static final double getAngleOffset(Robot myRobot, RobotData robot, double guessFactor, double firePower) {
      return getAngleOffset(myRobot.getX(), myRobot.getY(), robot, guessFactor, firePower);
   }

   /**
    * Returns the rotation direction. That is, the direction a robot is traveling around a point with 1 being clockwise,
    * and -1 being counter-clockwise.
    * 
    * @param robotHeading - the robot's heading
    * @param robotVelocity - the robot's relative speed
    * @param angleToRobot - the angle to the robot from the rotation point
    * @return the rotation direction
    */
   public static final int getDirection(double robotHeading, double robotVelocity, double angleToRobot) {
      return Utils.sign(Utils.sin(robotHeading - angleToRobot) * robotVelocity);
   }

   /**
    * Returns the maximum escape angle. That is, the greatest angle that a robot well achieve before a bullet reaches
    * it; with relation to the robot's position at time of fire and bullet's firing location.
    * 
    * @param firePower - the fire power of the bullet
    * @return the maximum escape angle
    */
   public static final double getMaxEscapeAngle(double firePower) {
      return Utils.asin(Rules.MAX_VELOCITY / Rules.getBulletSpeed(firePower));
   }


   public static final double distSq(double deltaX, double deltaY) {
      return sqr(deltaX) + sqr(deltaY);
   }

   public static final double distSq(double x1, double y1, double x2, double y2) {
      return distSq(x1 - x2, y1 - y2);
   }

   public static final double distSq(Point2D p, double x, double y) {
      return distSq(p.getX(), p.getY(), x, y);
   }

   public static final double distSq(Point2D p1, Point2D p2) {
      return distSq(p1.getX(), p1.getY(), p2.getX(), p2.getY());
   }

   public static final double dist(double deltaX, double deltaY) {
      return Math.sqrt(distSq(deltaX, deltaY));
   }

   public static final double dist(double x1, double y1, double x2, double y2) {
      return Math.sqrt(distSq(x1, y1, x2, y2));
   }

   public static final double dist(Point2D p1, Point2D p2) {
      return Math.sqrt(distSq(p1, p2));
   }

   public static final double getGuessFactor(int index, int listlength) {
      return (index - ((double) listlength - 1) / 2) / ((listlength - 1) / 2);
   }

   public static final double getGuessFactor(VirtualWave wave, RobotData start, RobotData finish) {
      double angleToStart = angle(wave.getStartX(), wave.getStartY(), start.getX(), start.getY());
      double direction = getDirection(start.getHeading(), start.getVelocity(), angleToStart);

      double angleToFinish = angle(wave.getStartX(), wave.getStartY(), finish.getX(), finish.getY());
      double angleOffset = Utils.relative(angleToFinish - wave.getHeading());
      return limit(-1, angleOffset / wave.getMaxEscapeAngle(), 1) * direction;
   }

   public static final double getGuessFactor(VirtualWave wave, RobotData start, Robot finish) {
      double angleToStart = angle(wave.getStartX(), wave.getStartY(), start.getX(), start.getY());
      double direction = getDirection(start.getHeading(), start.getVelocity(), angleToStart);

      double angleToFinish = angle(wave.getStartX(), wave.getStartY(), finish.getX(), finish.getY());
      double angleOffset = Utils.relative(angleToFinish - wave.getHeading());
      return limit(-1, angleOffset / wave.getMaxEscapeAngle(), 1) * direction;
   }

   public static final double getGuessFactor(VirtualWave wave, RobotData start, Bullet bullet) {
      double angleToStart = angle(wave.getStartX(), wave.getStartY(), start.getX(), start.getY());
      double direction = getDirection(start.getHeading(), start.getVelocity(), angleToStart);

      double angleOffset = Utils.relative(bullet.getHeading() - wave.getHeading());
      return limit(-1, angleOffset / wave.getMaxEscapeAngle(), 1) * direction;
   }

   public static final int getIndex(double guessfactor, int listlength) {
      return round(((double) listlength - 1) / 2 * (guessfactor + 1));
   }

   public static final Point2D getPoint(Point2D origin, double distance, double angle) {
      return getPoint(origin.getX(), origin.getY(), distance, angle);
   }

   public static final Point2D getPoint(double x, double y, double distance, double angle) {
      return new Point2D.Double(getX(x, distance, angle), getY(y, distance, angle));
   }

   public static final double getX(double x, double distance, double angle) {
      return x + getDeltaX(distance, angle);
   }

   public static final double getDeltaX(double distance, double angle) {
      return distance * sin(angle);
   }

   public static final double getY(double y, double distance, double angle) {
      return y + getDeltaY(distance, angle);
   }

   public static final double getDeltaY(double distance, double angle) {
      return distance * cos(angle);
   }

   /**
    * Returns <code>value</code> limited to the <code>min</code> and <code>max</code>. That is, if
    * <code>value</code> is greater then <code>max</code>, <code>max</code> will be returned. If
    * <code>value</code> is less then <code>min</code>, <code>min</code> will be returned. Otherwise
    * <code>value</code> will be returned.
    * 
    * @param min The smaller limiter.
    * @param value The number that will be limited.
    * @param max The larger limiter.
    * @return <code>value</code> limited.
    */
   public static final double limit(double min, double value, double max) {
      return Math.max(min, Math.min(value, max));
   }

   public static final double oppositeRelative(double angle) {
      return relative(angle + Utils.HALF_CIRCLE);
   }

   public static final double random(double n1, double n2) {
      return (Math.abs(n1 - n2) * Math.random()) + Math.min(n1, n2);
   }

   public static final double oppositeAbsolute(double angle) {
      return absolute(angle + Utils.HALF_CIRCLE);
   }

   public static final double relative(double n) {
      while (n <= -Utils.HALF_CIRCLE)
         n += Utils.CIRCLE;
      while (n > Utils.HALF_CIRCLE)
         n -= Utils.CIRCLE;
      return n;
   }

   /**
    * Returns the rolling average. That is, the weighted average of the current average and the new reading.
    * 
    * @param value - the current average.
    * @param newEntry - the most recent reading.
    * @param n - the recent history length.
    * @param weighting - the weight or importance of the most recent reading.
    * @return the rolling average.
    * @see <a href="http://robowiki.net/cgi-bin/robowiki?RollingAverage">Rolling Average</a>, <a
    *      href="http://robowiki.net/cgi-bin/robowiki?Paul_Evans">Paul Evans</a>
    * @author Paul Evans
    */
   public static final double rollingAvg(double value, double newEntry, double n, double weighting) {
      return (value * n + newEntry * weighting) / (n + weighting);
   }

   public static final int round(double n) {
      return (int) (n + 0.5D);
   }

   public static final double round(double n, double p) {
      return (round(n / p) * p);
   }

   public static final int roundUp(double n) {
      return (int) (n + 1.0D);
   }

   public static final int sign(double n) {
      return n < 0 ? -1 : 1;
   }

   public static final double weightedAvg(double value_1, double weight_1, double value_2, double weight_2) {
      return (value_1 * weight_1 + value_2 * weight_2) / (weight_1 + weight_2);
   }

   public static final char getPattern(double deltaHeading, double velocity) {
      int charVelocity = (int) ((velocity + RobotInfo.MAX_VELOCITY_REV) * 15.0D / (RobotInfo.MAX_VELOCITY + RobotInfo.MAX_VELOCITY_REV));
      int charDeltaHeading = (int) ((deltaHeading + RobotInfo.MAX_TURN_RATE) * 127.0D / (2.0D * RobotInfo.MAX_TURN_RATE));
      return (char) (charVelocity << 7 | charDeltaHeading);
   }

   public static final <E extends Segmentable, F extends Data, G extends Data> DataWave<E, F, G> findWaveMatch(List<DataWave<E, F, G>> waves,
                                                                                                               Bullet bullet, long time) {
      DataWave<E, F, G> wave = null;
      int i = 0;
      for (DataWave<E, F, G> w; i < waves.size() && wave == null; i++) {
         w = waves.get(i);
         double bulletDistSq = Utils.distSq(w.getStartX(), w.getStartY(), bullet.getX(), bullet.getY());
         double waveDistSq = Utils.sqr(w.getDist(time - (bullet.getVictim() == null ? 1L : 0L)));
         if (Math.abs(bulletDistSq - waveDistSq) < Utils.sqr(bullet.getVelocity() + Rules.MIN_BULLET_POWER)
             && Math.abs(w.getFirePower() - bullet.getPower()) < 2.0D * Rules.MIN_BULLET_POWER) {
            wave = w;
         }
      }
      return wave;
   }

   public static final double maxEscapeAngle(double bulletVelocity) {
      return Utils.asin(Rules.MAX_VELOCITY / bulletVelocity);
   }

}
