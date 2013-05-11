package dev.virtual;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import robocode.Bullet;
import robocode.Rules;
import dev.draw.RobotGraphics;
import dev.utils.Utils;


public class VirtualBullet implements Serializable {

   private static final long   serialVersionUID = -1034412077920214709L;

   private static final double MAX_X            = 5000.0D;
   private static final double MAX_Y            = 5000.0D;

   private boolean             real_;

   private double              startX_;
   private double              startY_;

   private double              deltaX_;
   private double              deltaY_;

   private double              heading_;
   private double              velocity_;
   private double              firePower_;
   private long                creationTime_;

   public VirtualBullet(double startX, double startY, double heading, double firePower, long creationTime) {
      init(false, startX, startY, heading, firePower, creationTime);
   }

   public VirtualBullet(Bullet bullet, long curTime) {
      init(true, bullet.getX(), bullet.getY(), bullet.getHeading(), bullet.getPower(), curTime);
   }

   private VirtualBullet(VirtualBullet bullet) {
      init(bullet.real_, bullet.startX_, bullet.startY_, bullet.heading_, bullet.firePower_, bullet.creationTime_);
   }

   private void init(boolean real, double startX, double startY, double heading, double firePower, long creationTime) {
      this.real_ = real;
      this.startX_ = startX;
      this.startY_ = startY;
      this.deltaX_ = Utils.deltaX(Rules.getBulletSpeed(firePower), heading);
      this.deltaY_ = Utils.deltaY(Rules.getBulletSpeed(firePower), heading);
      this.heading_ = Utils.relative(heading);
      this.velocity_ = Rules.getBulletSpeed(firePower);
      this.firePower_ = firePower;
      this.creationTime_ = creationTime;
   }

   protected boolean isReal() {
      return real_;
   }

   /**
    * Returns the x coordinate of the bullet's origin to <code>double</code> precision.
    * 
    * @return the x coordinate of the bullet's origin.
    */
   protected double getStartX() {
      return startX_;
   }

   /**
    * Returns the y coordinate of the bullet's origin to <code>double</code> precision.
    * 
    * @return the y coordinate of the bullet's origin.
    */
   protected double getStartY() {
      return startY_;
   }

   protected double getDeltaX() {
      return deltaX_;
   }

   protected double getDeltaY() {
      return deltaY_;
   }

   public double getX(long time) {
      return (deltaX_ * (time - creationTime_)) + startX_;
   }

   public double getY(long time) {
      return (deltaY_ * (time - creationTime_)) + startY_;
   }

   public double getHeading() {
      return heading_;
   }

   public double getFirePower() {
      return firePower_;
   }

   public double getVelocity() {
      return velocity_;
   }

   /**
    * Returns the creation time of the wave to <code>long</code> precision.
    * 
    * @return wave's creation time.
    */
   protected long getCreationTime() {
      return creationTime_;
   }

   protected double getDistTraveled(long time) {
      return velocity_ * (time - creationTime_);
   }

   public double getDist(double x, double y, long time) {
      return Utils.sqrt(getDistSq(x, y, time));
   }

   public double getDistSq(double x, double y, long time) {
      return Utils.distSq(x, y, getX(time), getY(time));
   }

   public double getDist(Point2D point, long time) {
      return Utils.sqrt(getDistSq(point, time));
   }

   public double getDistSq(Point2D point, long time) {
      return Utils.distSq(point, getBulletPoint(time));
   }

   public Line2D getBulletLine(long time) {
      return new Line2D.Double(getX(time - 1L), getY(time - 1L), getX(time), getY(time));
   }

   public Point2D getBulletPoint(long time) {
      return new Point2D.Double(getX(time), getY(time));
   }

   public boolean testActive(long time) {
      double x = getX(time), y = getY(time);
      return x > 0 && x < MAX_X && y > 0 && y < MAX_Y;
   }

   public boolean testHit(Rectangle2D robot, long time) {
      return robot.intersectsLine(getX(time - 1L), getY(time - 1L), getX(time), getY(time));
   }

   public boolean testMissed(Rectangle2D robot, long time) {
      return Utils.distSq(getStartX(), getStartY(), robot.getX(), robot.getY()) < Utils.sqr(getDistTraveled(time - 1L));
   }

   public void draw(RobotGraphics grid) {
      long time = grid.getTime();
      if (real_) {
         grid.drawOvalCenter(getX(time), getY(time), 6, 6);
      } else {
         grid.draw(getBulletLine(grid.getTime()));
      }
      if (grid.getTime() - 10 < creationTime_) {
         grid.fillOvalCenter(getX(time), getY(time), 6, 6);
      }
   }

   public VirtualBullet copy() {
      return new VirtualBullet(this);
   }

   @Override
   public Object clone() {
      return copy();
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof VirtualBullet) {
         VirtualBullet bullet = (VirtualBullet) obj;
         return bullet.real_ == this.real_ && bullet.heading_ == this.heading_ && bullet.firePower_ == this.firePower_;
      }
      return false;
   }

}
