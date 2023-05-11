package kid.data.virtual;

import java.awt.geom.*;
import java.io.PrintStream;

import robocode.*;

import kid.*;

//TODO documentation: class

public class VirtualBullet extends VirtualObject {

   private static final long serialVersionUID = 8647867336085921279L;

   private static double MAX_X = 5000.0D;
   private static double MAX_Y = 5000.0D;

   private double startX;
   private double stratY;

   private double deltaX;
   private double deltaY;

   private double heading;
   private double velocity;
   private double firePower;
   private long creationTime;

   public VirtualBullet(double startX, double startY, double heading, double firePower, long creationTime) {
      init(startX, startY, heading, firePower, creationTime);
   }

   public VirtualBullet(Bullet bullet, long curTime) {
      init(bullet.getX(), bullet.getY(), bullet.getHeading(), bullet.getPower(), curTime);
   }

   private VirtualBullet(VirtualBullet bullet) {
      init(bullet.getStartX(), bullet.getStartY(), bullet.getHeading(), bullet.getFirePower(), bullet.getCreationTime());
   }

   private void init(double startX, double startY, double heading, double firePower, long creationTime) {
      this.startX = startX;
      this.stratY = startY;
      this.deltaX = Utils.getDeltaX(Rules.getBulletSpeed(firePower), heading);
      this.deltaY = Utils.getDeltaY(Rules.getBulletSpeed(firePower), heading);
      this.heading = Utils.relative(heading);
      this.velocity = Rules.getBulletSpeed(firePower);
      this.firePower = firePower;
      this.creationTime = creationTime;
   }


   /**
    * Returns the x coordinate of the bullet's origin to <code>double</code> precision.
    * 
    * @return the x coordinate of the bullet's origin.
    */
   protected double getStartX() {
      return startX;
   }
   
   protected double getDeltaX() {
      return deltaX; 
   }

   /**
    * Returns the y coordinate of the bullet's origin to <code>double</code> precision.
    * 
    * @return the y coordinate of the bullet's origin.
    */
   protected double getStartY() {
      return stratY;
   }
   
   protected double getDeltaY() {
      return deltaY;
   }

   public double getX(long time) {
      return getStartX() + (getDeltaX() * (time - getCreationTime()));
   }

   public double getY(long time) {
      return getStartY() + (getDeltaY() * (time - getCreationTime()));
   }

   public double getHeading() {
      return heading;
   }

   public double getFirePower() {
      return firePower;
   }
   
   public double getVelocity() {
      return velocity;
   }

   /**
    * Returns the creation time of the wave to <code>long</code> precision.
    * 
    * @return wave's creation time.
    */
   protected long getCreationTime() {
      return creationTime;
   }

   protected double getDist(long time) {
      return getVelocity() * (time - getCreationTime());
   }

   public double getDist(double x, double y, long time) {
      return Utils.sqr(getDistSq(x, y, time));
   }

   public double getDistSq(double x, double y, long time) {
      return Utils.getDistSq(x, y, getX(time), getY(time));
   }

   public double getDist(Point2D point, long time) {
      return Utils.sqr(getDistSq(point, time));
   }

   public double getDistSq(Point2D point, long time) {
      return Utils.getDistSq(point, getBulletPoint(time));
   }

   public Line2D getBulletLine(long time) {
      return new Line2D.Double(getX(time - 1L), getY(time - 1L), getX(time), getY(time));
   }

   public Point2D getBulletPoint(long time) {
      return new Point2D.Double(getX(time), getY(time));
   }

   public boolean testActive(long time) {
      double x = getX(time), y = getY(time);
      return !(x < 0 || x > MAX_X || y < 0 || y > MAX_Y);
   }

   public boolean testHit(Rectangle2D robot, long time) {
      return robot.contains(getBulletPoint(time));
   }

   public boolean testMissed(Rectangle2D robot, long time) {
      return Utils.getDistSq(getStartX(), getStartY(), robot.getX(), robot.getY()) < Utils.sqr(getDist(time - 1L));
   }

   public void print(PrintStream console) {
      // TODO Method Stub
      console.println(this.toString());
   }

   public void print(RobocodeFileOutputStream output) {
      // TODO Method Stub
   }

   public void draw(RobocodeGraphicsDrawer grid, String commandString) {
      grid.draw(getBulletLine(grid.getTime()));
   }

   public Object clone() {
      return new VirtualBullet(this);
   }

   public boolean equals(Object obj) {
      if (obj instanceof VirtualBullet) {
         VirtualBullet bullet = (VirtualBullet) obj;
         return (bullet.getHeading() == this.getHeading()) && (bullet.getFirePower() == this.getFirePower());
      }
      return false;
   }

   public String toString() {
      // TODO Method Stub
      return new String();
   }

   public void finalize() {
      // TODO Method Stub
   }

}
