package bnorm.draw;

import java.awt.Graphics2D;

import bnorm.virtual.IPoint;
import bnorm.virtual.IVector;
import bnorm.virtual.IVectorWave;
import bnorm.virtual.IWave;

public final class Draw {

   private Draw() {
   }

   public static void point(Graphics2D g, IPoint point, double radius) {
      g.fillOval((int) (point.getX() - radius), (int) (point.getY() - radius), (int) (2.0 * radius),
                 (int) (2.0 * radius));
   }

   public static void points(Graphics2D g, Iterable<? extends IPoint> points, double radius) {
      for (IPoint p : points) {
         point(g, p, radius);
      }
   }

   public static void box(Graphics2D g, IPoint point, double side) {
      g.drawRect((int) (point.getX() - side), (int) (point.getY() - side), (int) (2.0 * side), (int) (2.0 * side));
   }

   public static void boxes(Graphics2D g, Iterable<? extends IPoint> points, double side) {
      for (IPoint p : points) {
         box(g, p, side);
      }
   }

   public static void vector(Graphics2D g, IVector vector) {
      double x = vector.getX();
      double y = vector.getY();
      g.drawLine((int) x, (int) y, (int) (x + vector.getDeltaX()), (int) (y + vector.getDeltaY()));
   }

   public static void vectors(Graphics2D g, Iterable<? extends IVector> vectors) {
      for (IVector v : vectors) {
         vector(g, v);
      }
   }

   public static void wave(Graphics2D g, IWave wave, long time) {
      int dist = (int) wave.dist(time);
      g.drawOval((int) (wave.getX() - dist), (int) (wave.getY() - dist), 2 * dist, 2 * dist);
   }

   public static void wave(Graphics2D g, IVectorWave wave, long time) {
      int dist = (int) wave.dist(time);
      long dt = time - wave.getTime();
      int dx = (int) (wave.getDeltaX() * dt);
      int dy = (int) (wave.getDeltaY() * dt);
      g.drawOval((int) (wave.getX() - dist), (int) (wave.getY() - dist), 2 * dist, 2 * dist);
      g.drawLine((int) wave.getX(), (int) wave.getY(), dx, dy);
   }

   public static <W extends IWave> void waves(Graphics2D g, Iterable<W> waves, long time) {
      for (W w : waves) {
         wave(g, w, time);
      }
   }
}
