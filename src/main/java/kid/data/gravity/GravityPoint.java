package kid.data.gravity;

import java.io.PrintStream;
import java.io.Serializable;

import kid.data.Drawable;
import kid.data.Printable;
import kid.graphics.RGraphics;
import kid.utils.Utils;
import robocode.RobocodeFileOutputStream;

// TODO documentation: class (0% complete)

public class GravityPoint implements Cloneable, Serializable, Printable, Drawable, GravityObject {

   private static final long serialVersionUID = 5070033474705879832L;

   public double x;
   public double y;

   public double strength;

   public long creationTime;
   public long life;

   public GravityPoint() {
      this(0.0D, 0.0D, 0.0D, -1L, -1L);
   }

   public GravityPoint(final double x, final double y, final double strength) {
      init(x, y, strength, 0, 0);
   }

   public GravityPoint(final double x, final double y, final double strength, final long creationTime, final long life) {
      init(x, y, strength, creationTime, life);
   }

   public GravityPoint(final GravityPoint point) {
      init(point.getX(), point.getY(), point.getStrength(), point.getCreationTime(), point.getLife());
   }

   private void init(final double x, final double y, final double strength, final long creationTime, final long life) {
      this.x = x;
      this.y = y;
      this.strength = strength;
      this.creationTime = creationTime;
      this.life = life;
   }

   protected double getX() {
      return x;
   }

   protected double getY() {
      return y;
   }

   public double getStrength() {
      return strength;
   }

   protected long getCreationTime() {
      return creationTime;
   }

   protected long getLife() {
      return life;
   }

   public double dist(final double x, final double y) {
      return Math.sqrt(distSq(x, y));
   }

   public double distSq(final double x, final double y) {
      return Utils.distSq(x, y, getX(), getY());
   }

   public double deltaX(final double x) {
      return getX() - x;
   }

   public double deltaY(final double y) {
      return getY() - y;
   }

   public double angle(final double x, final double y) {
      return Utils.angle(x, y, getX(), getY());
   }

   public boolean active(final long time) {
      return ((getLife() == 0) || (getCreationTime() + getLife()) > time);
   }

   // TODO code: method - print(PrintStream)
   public void print(final PrintStream console) {
   }

   // TODO code: method - print(RobocodeFileOutputSteam)
   public void print(final RobocodeFileOutputStream output) {
      // PrintStream file = new PrintStream(output);
   }

   // TODO code: method - draw(RobocodeGraphicsDrawer, String)
   public void draw(final RGraphics grid) {
   }

   @Override
   public Object clone() {
      return new GravityPoint(this);
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof GravityPoint) {
         GravityPoint point = (GravityPoint) obj;
         return (point.getX() == this.getX()) && (point.getY() == this.getY()) && (point.getStrength() == this.getStrength()) && (point.getCreationTime() == getCreationTime())
               && (point.getLife() == this.getLife());
      }
      return false;
   }

   // TODO code: method - toString()
   @Override
   public String toString() {
      return new String();
   }

   @Override
   public void finalize() throws Throwable {
      this.x = 0.0D;
      this.y = 0.0D;
      this.strength = 0.0D;
      this.creationTime = -1L;
      this.life = -1L;
      super.finalize();
   }

}