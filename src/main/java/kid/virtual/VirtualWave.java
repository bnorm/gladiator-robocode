package kid.virtual;

import java.awt.geom.Rectangle2D;
import java.io.PrintStream;

import kid.graphics.Colors;
import kid.graphics.RGraphics;
import kid.robot.RobotData;
import kid.utils.Utils;
import robocode.RobocodeFileOutputStream;
import robocode.Robot;
import robocode.Rules;

// TODO documentation: class (60% complete) - VirtualWave : VirtualObject
// BORED documentation: perfect

public class VirtualWave extends VirtualObject {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = 3030661579076677220L;

   /**
    * The X coordinate of the point from which this wave originated.
    */
   protected double startX;

   /**
    * The Y coordinate of the point from which this wave originated.
    */
   protected double startY;

   /**
    * The supposed heading of this wave. I.e., a wave has no real heading, it is only used for a reference.
    */
   protected double heading;

   /**
    * The fire power at which the wave was fired.
    */
   protected double firePower;

   /**
    * The speed at which the wave is traveling.
    */
   protected double velocity;

   /**
    * The maximum angle a robot could reach with reference to the supposed heading before the wave passes it.
    */
   protected double maxEscapeAngle;

   /**
    * The time at which the wave was fired or created.
    */
   protected long creationTime;

   /**
    * Creates a blank {@code VirtualWave class}.
    */
   public VirtualWave() {
      init(-100.0D, -100.0D, Utils.HALF_CIRCLE, 1.0D / Double.POSITIVE_INFINITY, -1L);
   }

   public VirtualWave(final double startX, final double startY, final double heading, final double firePower, final long creationTime) {
      init(startX, startY, heading, firePower, creationTime);
   }

   protected VirtualWave(final VirtualWave wave) {
      init(wave.getStartX(), wave.getStartX(), wave.getHeading(), wave.getFirePower(), wave.getCreationTime());
   }

   private void init(final double startX, final double startY, final double heading, final double firePower, final long creationTime) {
      this.startX = startX;
      this.startY = startY;
      this.heading = heading;
      this.firePower = firePower;
      this.velocity = Rules.getBulletSpeed(this.firePower);
      this.maxEscapeAngle = Utils.maxEscapeAngle(this.velocity);
      this.creationTime = creationTime;
   }

   /**
    * @return the maximum escape angle
    * @see #maxEscapeAngle
    */
   public double getMaxEscapeAngle() {
      return maxEscapeAngle;
   }

   /**
    * Returns the X coordinate of the wave's origin to {@code double} precision.
    * 
    * @return the X coordinate of the wave's origin
    * @see #startX
    */
   public double getStartX() {
      return startX;
   }

   /**
    * Returns the Y coordinate of the wave's origin to {@code double} precision.
    * 
    * @return the Y coordinate of the wave's origin
    * @see #startY
    */
   public double getStartY() {
      return startY;
   }

   /**
    * Returns the supposed heading that this wave is traveling in to <code>double</code> precision.
    * 
    * @return the wave's supposed heading
    * @see #heading
    */
   public double getHeading() {
      return heading;
   }

   /**
    * Returns the fire power of the wave to <code>double</code> precision.
    * 
    * @return the wave's fire power
    * @see #firePower
    */
   public double getFirePower() {
      return firePower;
   }

   /**
    * Returns the speed at which the wave is traveling to <code>double</code> precision.
    * 
    * @return the wave's speed
    * @see #velocity
    */
   public double getVelocity() {
      return velocity;
   }

   /**
    * Returns the creation time of the wave to <code>long</code> precision.
    * 
    * @return the wave's creation time.
    * @see #creationTime
    */
   public long getCreationTime() {
      return creationTime;
   }

   // BORED documentation: VirtualWave - active(long)
   public boolean active(final long time) {
      return !(getStartX() < 0 || getStartY() < 0 || getDist(time) > 7500.0D);
   }

   /**
    * Returns the travel distance of the wave at <code>time</code> to <code>double</code> precision.
    * 
    * @param time game time.
    * @return wave's travel distance.
    */
   public double getDist(final long time) {
      return getVelocity() * (time - getCreationTime());
   }

   // BORED documentation: VirtualWave - getDistSq(long)
   public double getDistSq(final long time) {
      return Utils.sqr(getDist(time));
   }

   /**
    * Returns the distance to the wave's impact with the coordinates <code>(x,y)</code> to <code>double</code>
    * precision, with relation to <code>time</code>.
    * 
    * @param x impact's x coordinate
    * @param y impact's y coordinate
    * @param time game time
    * @return distance to wave's impact
    */
   public double getDistToImpact(final double x, final double y, final long time) {
      double dist = Utils.dist(getStartX(), getStartY(), x, y);
      dist -= getDist(time);
      return dist;
   }

   /**
    * Returns the time to the wave's impact with the coordinates <code>(x,y)</code> to <code>double</code> precision,
    * with relation to <code>time</code>.
    * 
    * @param x impact's x coordinate
    * @param y impact's y coordinate
    * @param time game time
    * @return time to wave's impact
    */
   public long getTimeToImpact(final double x, final double y, final long time) {
      double dist = getDistToImpact(x, y, time);
      return (long) (dist / getVelocity());
   }

   public boolean testHit(final RobotData robot, final long time) {
      return testHit(robot.getX(), robot.getY(), time);
   }

   public boolean testHit(final Robot robot) {
      return testHit(robot.getX(), robot.getY(), robot.getTime());
   }

   public boolean testHit(final Rectangle2D rectangle, final long time) {
      return testHit(rectangle.getCenterX(), rectangle.getCenterY(), time);
   }

   public boolean testHit(final double x, final double y, final long time) {
      return Utils.distSq(getStartX(), getStartY(), x, y) <= Utils.sqr(getDist(time));
   }

   // TODO code: VirtualWave - print(PrintStream)
   public void print(final PrintStream console) {
      console.println("VirtualWave: (" + getStartX() + ", " + getStartY() + ") " + getHeading());
   }

   // TODO code: VirtualWave - print(RobocodeFileOutputStream)
   public void print(final RobocodeFileOutputStream output) {
   }

   public void draw(final RGraphics grid) {
      double dist = getDist(grid.getTime());
      if (dist < 7500) {
         grid.setColor(Colors.SILVER);
         grid.drawArcCenter(getStartX(), getStartY(), 2.0D * dist, 2.0D * dist, getHeading() - getMaxEscapeAngle(), 2.0D * getMaxEscapeAngle());
         grid.fillOvalCenter(getStartX(), getStartY(), 5.0D, 5.0D);
         grid.drawLine(getStartX(), getStartY(), Utils.getX(getStartX(), dist, getHeading() - getMaxEscapeAngle()), Utils.getY(getStartY(), dist,
               getHeading() - getMaxEscapeAngle()));
         grid.drawLine(getStartX(), getStartY(), Utils.getX(getStartX(), dist, getHeading() + getMaxEscapeAngle()), Utils.getY(getStartY(), dist,
               getHeading() + getMaxEscapeAngle()));
      }
   }

   @Override
   public Object clone() {
      return new VirtualWave(this);
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof VirtualWave) {
         VirtualWave wave = (VirtualWave) obj;
         return (wave.getStartX() == this.getStartX()) && (wave.getStartY() == this.getStartY()) && (wave.getHeading() == getHeading())
               && (wave.getCreationTime() == getCreationTime());
      }
      return false;
   }

   // TODO code: VirtualWave - toString()
   @Override
   public String toString() {
      return new String();
   }

   @Override
   public void finalize() {
      startX = 0.0D;
      startY = 0.0D;
      heading = 0.0D;
      firePower = 0.0D;
      velocity = 0.0D;
      maxEscapeAngle = 0.0D;
      creationTime = 0L;
   }

}