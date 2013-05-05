package bnorm.utils;

import java.awt.geom.Point2D;

import bnorm.robots.IRobotSnapshot;

/**
 * Utility class for {@link bnorm.robots.IRobot}s and {@link IRobotSnapshot}s.
 *
 * @author Brian Norman
 * @version 1.0
 */
public class Robots {

   /**
    * Private constructor.
    */
   private Robots() {
   }

   /**
    * Calculates and returns the distance between the <code>(x,y)</code>
    * coordinates.
    *
    * @param x1 the <code>x</code> coordinate of the first coordinate.
    * @param y1 the <code>y</code> coordinate of the first coordinate.
    * @param x2 the <code>x</code> coordinate of the second coordinate.
    * @param y2 the <code>y</code> coordinate of the second coordinate.
    * @return the distance between coordinates.
    */
   public static double distSq(double x1, double y1, double x2, double y2) {
      return Point2D.distanceSq(x1, y1, x2, y2);
   }

   /**
    * Calculates and returns the distance between the <code>(x,y)</code>
    * coordinate and the IRobotSnapshot.
    *
    * @param snapshot the starting robot snapshot.
    * @param x the <code>x</code> coordinate of the ending point.
    * @param y the <code>y</code> coordinate of the ending point.
    * @return the distance between coordinate and robot snapshot.
    */
   public static double distSq(IRobotSnapshot snapshot, double x, double y) {
      return distSq(snapshot.getX(), snapshot.getY(), x, y);
   }
}
