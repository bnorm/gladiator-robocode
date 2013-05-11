package dev.utils;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import robocode.Bullet;
import robocode.Rules;
import dev.robots.RobotData;
import dev.virtual.VirtualWave;

public final class Utils {

   private final static Logger logger_ = Logger.getLogger(Utils.class.getName());

   private Utils() {
   }

   public static final double absolute(double n) {
      if (!Double.isInfinite(n) && !Double.isNaN(n)) {
         n %= Trig.CIRCLE;
         if (n < 0.0)
            n += Trig.CIRCLE;
      }
      return n;
   }

   public static final double relative(double n) {
      if (!Double.isInfinite(n) && !Double.isNaN(n)) {
         n %= Trig.CIRCLE;
         if (n <= -Trig.HALF_CIRCLE)
            n += Trig.CIRCLE;
         else if (n > Trig.HALF_CIRCLE)
            n -= Trig.CIRCLE;
      }
      return n;
   }

   public static final double normal(double n, double min, double max) {
      if (!Double.isInfinite(n) && !Double.isNaN(n) && min < max) {
         n %= (max - min);
         if (n <= min)
            n += (max - min);
         else if (n > max)
            n -= (max - min);
      }
      return n;
   }

   public static final double projectX(double x, double d, double a) {
      return x + deltaX(d, a);
   }

   public static final double deltaX(double d, double a) {
      return d * Trig.sin(a);
   }

   public static final double projectY(double y, double d, double a) {
      return y + deltaY(d, a);
   }

   public static final double deltaY(double d, double a) {
      return d * Trig.cos(a);
   }

   public static final Point2D project(Point2D p, double d, double a) {
      return new Point2D.Double(projectX(p.getX(), d, a), projectY(p.getY(), d, a));
   }

   public static final double avg(double n1, double w1, double n2, double w2) {
      return (n1 * w1 + n2 * w2) / (w1 + w2);
   }

   public static final double round(double n) {
      return ((int) (n + 0.5));
   }

   public static final double floor(double n) {
      return ((int) n);
   }

   public static final double ceil(double n) {
      return ((int) (n + 1.0));
   }

   public static final int sign(double n) {
      return (n < 0.0 ? -1 : 1);
   }

   public static final int signum(double n) {
      return (n == 0.0 ? 0 : (n > 0.0 ? 1 : -1));
   }

   public static final double abs(double n) {
      return (n < 0 ? -n : n);
   }

   public static final double sqr(double n) {
      return n * n;
   }

   public static final double sqrt(double n) {
      return StrictMath.sqrt(n);
   }

   public static final double max(double a, double b) {
      return (a < b ? b : a);
   }

   public static final double absMax(double a, double b) {
      return (abs(a) < abs(b) ? b : a);
   }

   public static final double min(double a, double b) {
      return (a > b ? b : a);
   }

   public static final double absMin(double a, double b) {
      return (abs(a) > abs(b) ? b : a);
   }

   public static final double max(double a, double b, double c) {
      return max(a, max(b, c));
   }

   public static final double absMax(double a, double b, double c) {
      return absMax(a, absMax(b, c));
   }

   public static final double min(double a, double b, double c) {
      return min(c, min(b, c));
   }

   public static final double absMin(double a, double b, double c) {
      return absMin(a, absMin(b, c));
   }

   public static final double max(double a, double b, double c, double d) {
      return max(max(a, b), max(c, d));
   }

   public static final double absMax(double a, double b, double c, double d) {
      return absMax(absMax(a, b), absMax(c, d));
   }

   public static final double min(double a, double b, double c, double d) {
      return min(min(a, b), min(c, d));
   }

   public static final double absMin(double a, double b, double c, double d) {
      return absMin(absMin(a, b), absMin(c, d));
   }

   public static final double limit(double low, double n, double high) {
      return max(low, min(n, high));
   }

   public static final double round(double value, double nearest) {
      return round(value / nearest) * nearest;
   }

   public static final double angle(Point2D start, Point2D finish) {
      return Trig.atan2(finish.getX() - start.getX(), finish.getY() - start.getY());
   }

   public static final double angle(double x1, double y1, double x2, double y2) {
      return Trig.atan2(x2 - x1, y2 - y1);
   }

   public static final double distSq(double x1, double y1, double x2, double y2) {
      return sqr(x2 - x1) + sqr(y2 - y1);
   }

   public static final double distSq(Point2D p1, Point2D p2) {
      return sqr(p2.getX() - p1.getX()) + sqr(p2.getY() - p1.getY());
   }

   public static final double dist(double x1, double y1, double x2, double y2) {
      return StrictMath.sqrt(sqr(x2 - x1) + sqr(y2 - y1));
   }

   public static final double dist(Point2D p1, Point2D p2) {
      return StrictMath.sqrt(sqr(p2.getX() - p1.getX()) + sqr(p2.getY() - p1.getY()));
   }

   public static final boolean inRange(double low, double n, double high) {
      return low <= n && n <= high;
   }

   public static final boolean inRange(double low, double n, double high, double eps) {
      return low - eps <= n && n <= high + eps;
   }

   public static final boolean isNear(double n1, double n2, double percent) {
      return abs(2.0 * (n1 - n2) / (n1 + n2)) < percent;
   }

   public static final double maxEscapeAngle(double bulletVelocity) {
      return Trig.asin(Rules.MAX_VELOCITY / bulletVelocity);
   }

   public static final double firePower(double bulletDamage) {
      if (bulletDamage < 4.0) {
         return bulletDamage / 4.0;
      } else {
         return (bulletDamage + 2.0) / 6.0;
      }
   }

   public static final double energyReturn(double firePower) {
      return 3.0 * firePower;
   }

   public static final double energyReturn_BulletDamage(double bulletDamage) {
      if (bulletDamage < 4.0) {
         return 3.0 / 4.0 * bulletDamage;
      } else {
         return (bulletDamage + 2.0) / 2.0;
      }
   }

   public static final <E extends VirtualWave> E findWaveMatch(List<E> waves, Bullet bullet, long time) {
      E w;
      E match = null;
      Iterator<E> iter = waves.iterator();
      while (match == null && iter.hasNext()) {
         w = iter.next();
         double bulletDist = Utils.distSq(w.getStartX(), w.getStartY(), bullet.getX(), bullet.getY());
         double waveDist = Utils.sqr(w.getDist(time) - (bullet.isActive() ? 0.0 : bullet.getVelocity()));
         if (isNear(bulletDist, waveDist, .1) // compare travel dist
               // || isNear(bulletDist, waveDist + bullet.getVelocity(), .01)
               && isNear(w.getFirePower(), bullet.getPower(), .01)) { // compare
                                                                      // bullet
                                                                      // power
            match = w;
         }
      }
      if (match == null) {
         System.out.println("NO MATCH FOUND! - Utils.findWaveMatch(List<VirtualWave>, Bullet, long)");
         System.out.println("Bullet: " + bullet.getVelocity() + " " + bullet.isActive());
         for (E wave : waves) {
            double bulletDist = Utils.dist(wave.getStartX(), wave.getStartY(), bullet.getX(), bullet.getY());
            double waveDist = wave.getDist(time);
            System.out.println("   Bullet Dist: " + bulletDist + " Wave Dist: " + waveDist + " Wave Velocity: "
                  + wave.getVelocity());
         }
      }
      return match;
   }

   public static final double getGuessFactor(VirtualWave wave, RobotData start, Bullet bullet) {
      double direction = Utils.getDirection(start.getHeading(), start.getVelocity(), wave.getHeading());
      double angleOffset = Utils.relative(bullet.getHeadingRadians() - wave.getHeading());
      return limit(-1, angleOffset / wave.getMaxEscapeAngle(), 1) * direction;
   }

   /**
    * Returns the rotation direction. That is, the direction a robot is
    * traveling around a point with 1 being clockwise, and -1 being
    * counter-clockwise.
    * 
    * @param robotHeading
    *           the robot's heading
    * @param robotVelocity
    *           the robot's relative speed
    * @param angleToRobot
    *           the angle to the robot from the rotation point
    * @return the rotation direction
    */
   public static final int getDirection(double robotHeading, double robotVelocity, double angleToRobot) {
      return sign(Trig.sin(robotHeading - angleToRobot) * robotVelocity);
   }

}
