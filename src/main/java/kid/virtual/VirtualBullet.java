package kid.virtual;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.PrintStream;

import kid.graphics.RGraphics;
import kid.utils.Utils;
import robocode.Bullet;
import robocode.RobocodeFileOutputStream;
import robocode.Rules;

// TODO documentation: class

public class VirtualBullet extends VirtualObject {

   private static final long serialVersionUID = 8647867336085921279L;

   private static double     MAX_X            = 5000.0D;
   private static double     MAX_Y            = 5000.0D;

   private double            startX;
   private double            stratY;

   private double            deltaX;
   private double            deltaY;

   private double            heading;
   private double            velocity;
   private double            firePower;
   private long              creationTime;

   public VirtualBullet(final double startX, final double startY, final double heading, final double firePower,
         final long creationTime) {
      init(startX, startY, heading, firePower, creationTime);
   }

   public VirtualBullet(final Bullet bullet, final long curTime) {
      init(bullet.getX(), bullet.getY(), bullet.getHeading(), bullet.getPower(), curTime);
   }

   private VirtualBullet(final VirtualBullet bullet) {
      init(bullet.getStartX(), bullet.getStartY(), bullet.getHeading(), bullet.getFirePower(), bullet.getCreationTime());
   }

   private void init(final double startX, final double startY, final double heading, final double firePower,
         final long creationTime) {
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

   public double getX(final long time) {
      return getStartX() + (getDeltaX() * (time - getCreationTime()));
   }

   public double getY(final long time) {
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

   protected double getDist(final long time) {
      return getVelocity() * (time - getCreationTime());
   }

   public double getDist(final double x, final double y, final long time) {
      return Utils.sqrt(getDistSq(x, y, time));
   }

   public double getDistSq(final double x, final double y, final long time) {
      return Utils.distSq(x, y, getX(time), getY(time));
   }

   public double getDist(final Point2D point, final long time) {
      return Utils.sqrt(getDistSq(point, time));
   }

   public double getDistSq(final Point2D point, final long time) {
      return Utils.distSq(point, getBulletPoint(time));
   }

   public Line2D getBulletLine(final long time) {
      return new Line2D.Double(getX(time - 1L), getY(time - 1L), getX(time), getY(time));
   }

   public Point2D getBulletPoint(final long time) {
      return new Point2D.Double(getX(time), getY(time));
   }

   public boolean testActive(final long time) {
      double x = getX(time), y = getY(time);
      return !(x < 0 || x > MAX_X || y < 0 || y > MAX_Y);
   }

   public boolean testHit(final Rectangle2D robot, final long time) {
      return robot.contains(getBulletPoint(time));
   }

   public boolean testMissed(final Rectangle2D robot, final long time) {
      return Utils.distSq(getStartX(), getStartY(), robot.getX(), robot.getY()) < Utils.sqr(getDist(time - 1L));
   }

   public void print(final PrintStream console) {
      // TODO Method Stub
      console.println(this.toString());
   }

   public void print(final RobocodeFileOutputStream output) {
      // TODO Method Stub
   }

   public void draw(final RGraphics grid) {
      long time = grid.getTime();
      grid.drawLine(getX(time - 1L), getY(time - 1L), getX(time), getY(time));
   }

   @Override
   public Object clone() {
      return new VirtualBullet(this);
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof VirtualBullet) {
         VirtualBullet bullet = (VirtualBullet) obj;
         return (bullet.getHeading() == this.getHeading()) && (bullet.getFirePower() == this.getFirePower());
      }
      return false;
   }

   @Override
   public String toString() {
      // TODO Method Stub
      return new String();
   }

   @Override
   public void finalize() {
      // TODO Method Stub
   }

}
