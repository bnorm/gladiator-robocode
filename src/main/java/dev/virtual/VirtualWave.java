package dev.virtual;

import java.awt.geom.Rectangle2D;

import robocode.Robot;
import robocode.Rules;
import dev.draw.RobotColor;
import dev.draw.RobotGraphics;
import dev.robots.RobotData;
import dev.utils.Trig;
import dev.utils.Utils;


// TODO comment: class -> VirtualWave
public class VirtualWave {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = 3030661579076677220L;

   /**
    * The x coordinate of the point from which this wave originated.
    */
   protected double          originX_;

   /**
    * The y coordinate of the point from which this wave originated.
    */
   protected double          originY_;

   /**
    * The supposed heading of this wave. I.e., a wave has no real heading, it is only used for a reference.
    */
   protected double          heading_;

   /**
    * The fire power at which the wave was fired.
    */
   protected double          firePower_;

   /**
    * The speed at which the wave is traveling.
    */
   protected double          velocity_;

   /**
    * The maximum angle a robot could reach with reference to the supposed heading before the wave passes it.
    */
   protected double          maxEscapeAngle_;

   /**
    * The time at which the wave was fired or created.
    */
   protected long            creationTime_;

   /**
    * Creates a blank {@code VirtualWave class}.
    */
   public VirtualWave() {
      this(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Trig.HALF_CIRCLE, 0.0, -1L);
   }

   protected VirtualWave(VirtualWave wave) {
      this(wave.getStartX(), wave.getStartX(), wave.getHeading(), wave.getFirePower(), wave.getCreationTime());
   }

   public VirtualWave(double startX, double startY, double heading, double firePower, long creationTime) {
      init(startX, startY, heading, firePower, creationTime);
   }

   private void init(double startX, double startY, double heading, double firePower, final long creationTime) {
      this.originX_ = startX;
      this.originY_ = startY;
      this.heading_ = heading;
      this.firePower_ = firePower;
      this.velocity_ = Rules.getBulletSpeed(this.firePower_);
      this.maxEscapeAngle_ = Utils.maxEscapeAngle(this.velocity_);
      this.creationTime_ = creationTime;
   }

   /**
    * @return the maximum escape angle
    * @see #maxEscapeAngle_
    */
   public double getMaxEscapeAngle() {
      return maxEscapeAngle_;
   }

   /**
    * Returns the X coordinate of the wave's origin to {@code double} precision.
    * 
    * @return the X coordinate of the wave's origin
    * @see #originX_
    */
   public double getStartX() {
      return originX_;
   }

   /**
    * Returns the Y coordinate of the wave's origin to {@code double} precision.
    * 
    * @return the Y coordinate of the wave's origin
    * @see #originY_
    */
   public double getStartY() {
      return originY_;
   }

   /**
    * Returns the supposed heading that this wave is traveling in to <code>double</code> precision.
    * 
    * @return the wave's supposed heading
    * @see #heading_
    */
   public double getHeading() {
      return heading_;
   }

   /**
    * Returns the fire power of the wave to <code>double</code> precision.
    * 
    * @return the wave's fire power
    * @see #firePower_
    */
   public double getFirePower() {
      return firePower_;
   }

   /**
    * Returns the speed at which the wave is traveling to <code>double</code> precision.
    * 
    * @return the wave's speed
    * @see #velocity_
    */
   public double getVelocity() {
      return velocity_;
   }

   /**
    * Returns the creation time of the wave to <code>long</code> precision.
    * 
    * @return the wave's creation time.
    * @see #creationTime_
    */
   public long getCreationTime() {
      return creationTime_;
   }

   // BORED documentation: VirtualWave - active(long)
   public boolean active(long time) {
      return originX_ > 0 && originY_ > 0 && getDist(time) < 7500.0D;
   }

   /**
    * Returns the travel distance of the wave at <code>time</code> to <code>double</code> precision.
    * 
    * @param time
    *           game time.
    * @return wave's travel distance.
    */
   public double getDist(long time) {
      return velocity_ * (time - creationTime_);
   }

   // BORED documentation: VirtualWave - getDistSq(long)
   public double getDistSq(long time) {
      return Utils.sqr(getDist(time));
   }

   /**
    * Returns the distance to the wave's impact with the coordinates <code>(x,y)</code> to <code>double</code>
    * precision, with relation to <code>time</code>.
    * 
    * @param x
    *           impact's x coordinate
    * @param y
    *           impact's y coordinate
    * @param time
    *           game time
    * @return distance to wave's impact
    */
   public double getDistToImpact(double x, double y, long time) {
      double dist = Utils.dist(originX_, originY_, x, y);
      dist -= getDist(time);
      return dist;
   }

   /**
    * Returns the time to the wave's impact with the coordinates <code>(x,y)</code> to <code>double</code> precision,
    * with relation to <code>time</code>.
    * 
    * @param x
    *           impact's x coordinate
    * @param y
    *           impact's y coordinate
    * @param time
    *           game time
    * @return time to wave's impact
    */
   public long getTimeToImpact(double x, double y, long time) {
      double dist = getDistToImpact(x, y, time);
      return (long) (dist / velocity_);
   }

   public boolean testHit(RobotData robot) {
      return testHit(robot.getX(), robot.getY(), robot.getTime());
   }

   public boolean testHit(Robot robot) {
      return testHit(robot.getX(), robot.getY(), robot.getTime());
   }

   public boolean testHit(Rectangle2D rectangle, long time) {
      return testHit(rectangle.getCenterX(), rectangle.getCenterY(), time);
   }

   public boolean testHit(double x, double y, long time) {
      return Utils.distSq(originX_, originY_, x, y) <= getDistSq(time);
   }

   public void draw(RobotGraphics grid) {
      double dist = getDist(grid.getTime());
      if (dist < 7500) {
         grid.setColor(RobotColor.SILVER);
         grid.drawArcCenter(getStartX(), getStartY(), 2.0D * dist, 2.0D * dist, getHeading() - getMaxEscapeAngle(),
               2.0D * getMaxEscapeAngle());
         grid.fillOvalCenter(getStartX(), getStartY(), 5.0D, 5.0D);
         grid.drawLine(getStartX(), getStartY(), Utils.projectX(getStartX(), dist, getHeading() - getMaxEscapeAngle()),
               Utils.projectY(getStartY(), dist, getHeading() - getMaxEscapeAngle()));
         grid.drawLine(getStartX(), getStartY(), Utils.projectX(getStartX(), dist, getHeading() + getMaxEscapeAngle()),
               Utils.projectY(getStartY(), dist, getHeading() + getMaxEscapeAngle()));
      }
   }

   public VirtualWave copy() {
      return new VirtualWave(this);
   }

   @Override
   public Object clone() {
      return copy();
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof VirtualWave) {
         VirtualWave wave = (VirtualWave) obj;
         return (wave.getStartX() == this.getStartX()) && (wave.getStartY() == this.getStartY())
               && (wave.getHeading() == getHeading()) && (wave.getCreationTime() == getCreationTime());
      }
      return super.equals(obj);
   }

   @Override
   public void finalize() {
      originX_ = 0.0D;
      originY_ = 0.0D;
      heading_ = 0.0D;
      firePower_ = 0.0D;
      velocity_ = 0.0D;
      maxEscapeAngle_ = 0.0D;
      creationTime_ = 0L;
   }

}