package kid.data.virtual;

import java.awt.geom.Rectangle2D;
import java.io.PrintStream;

import kid.*;
import kid.data.robot.RobotData;
import robocode.*;

// TODO documentation: class (40% complete)
// BORED documentation: perfect

public class VirtualWave extends VirtualObject {

   private static final long serialVersionUID = 3030661579076677220L;

   protected double START_X;
   protected double START_Y;
   protected double HEADING;
   protected double FIRE_POWER;
   protected double BULLET_VELOCITY;
   protected double MAX_ESCAPE_ANGLE;
   protected long CREATION_TIME;

   /**
    * Creates a blank <code>VirtualWave</code> class.
    */
   public VirtualWave() {
      init(-100.0D, -100.0D, Utils.HALF_CIRCLE, 1.0D / Double.POSITIVE_INFINITY, -1L);
   }

   public VirtualWave(double startX, double startY, double heading, double firePower, long creationTime) {
      init(startX, startY, heading, firePower, creationTime);
   }

   protected VirtualWave(VirtualWave wave) {
      init(wave.getStartX(), wave.getStartX(), wave.getHeading(), wave.getFirePower(), wave.getCreationTime());
   }

   private void init(double startX, double startY, double heading, double firePower, long creationTime) {
      this.START_X = startX;
      this.START_Y = startY;
      this.HEADING = heading;
      this.FIRE_POWER = firePower;
      this.BULLET_VELOCITY = Rules.getBulletSpeed(FIRE_POWER);
      this.MAX_ESCAPE_ANGLE = Utils.maxEscapeAngle(BULLET_VELOCITY);
      this.CREATION_TIME = creationTime;
   }


   public double getMaxEscapeAngle() {
      return MAX_ESCAPE_ANGLE;
   }

   /**
    * Returns the x coordinate of the wave's origin to <code>double</code> precision.
    * 
    * @return the x coordinate of the wave's origin.
    */
   public double getStartX() {
      return START_X;
   }

   /**
    * Returns the y coordinate of the wave's origin to <code>double</code> precision.
    * 
    * @return the y coordinate of the wave's origin.
    */
   public double getStartY() {
      return START_Y;
   }

   public double getHeading() {
      return HEADING;
   }

   /**
    * Returns the fire power of the wave to <code>double</code> precision.
    * 
    * @return wave's fire power.
    */
   public double getFirePower() {
      return FIRE_POWER;
   }

   public double getVelocity() {
      return BULLET_VELOCITY;
   }

   /**
    * Returns the creation time of the wave to <code>long</code> precision.
    * 
    * @return wave's creation time.
    */
   public long getCreationTime() {
      return CREATION_TIME;
   }

   public boolean active(long time) {
      return !(getStartX() < 0 || getStartY() < 0 || getDist(time) > 5000.0D);
   }

   /**
    * Returns the travel distance of the wave at <code>time</code> to
    * <code>double</code> precision.
    * 
    * @param time game time.
    * @return wave's travel distance.
    */
   public double getDist(long time) {
      return getVelocity() * (time - getCreationTime());
   }

   /**
    * Returns the distance to the wave's impact with the coordinates <code>(x,y)</code>
    * to <code>double</code> precision, with relation to <code>time</code>.
    * 
    * @param x impact's x coordinate.
    * @param y impact's y coordinate.
    * @param time game time.
    * @return distance to wave's impact.
    */
   public double getDistToImpact(double x, double y, long time) {
      double dist = Utils.getDist(getStartX(), getStartY(), x, y);
      dist -= getDist(time);
      return dist;
   }

   /**
    * Returns the time to the wave's impact with the coordinates <code>(x,y)</code> to
    * <code>double</code> precision, with relation to <code>time</code>.
    * 
    * @param x impact's x coordinate.
    * @param y impact's y coordinate.
    * @param Time game time.
    * @return time to wave's impact.
    */
   public int getTimeToImpact(double x, double y, long time) {
      double dist = getDistToImpact(x, y, time);
      return (int) (dist / getVelocity());
   }

   public boolean testHit(RobotData robot, long time) {
      return testHit(robot.getX(), robot.getY(), time);
   }

   public boolean testHit(Robot robot) {
      return testHit(robot.getX(), robot.getY(), robot.getTime());
   }

   public boolean testHit(Rectangle2D rectangle, long time) {
      return testHit(rectangle.getCenterX(), rectangle.getCenterY(), time);
   }

   public boolean testHit(double x, double y, long time) {
      return Utils.getDistSq(getStartX(), getStartY(), x, y) <= Utils.sqr(getDist(time));
   }

   public void print(PrintStream console) {
      // TODO method stub
      console.println("VirtualWave: (" + getStartX() + ", " + getStartY() + ") " + getHeading());
   }

   public void print(RobocodeFileOutputStream output) {
      // TODO method stub
   }

   public void draw(RobocodeGraphicsDrawer grid, String commandString) {
      double dist = getDist(grid.getTime());
      if (dist < 5555) {
         grid.setColor(Colors.LIGHT_GRAY);
         grid.drawArcCenter(getStartX(), getStartY(), 2.0D * dist, 2.0D * dist, getHeading() - getMaxEscapeAngle(), 2.0D * getMaxEscapeAngle());
         grid.drawOvalCenter(getStartX(), getStartY(), 5.0D, 5.0D);
         grid.drawLine(getStartX(), getStartY(), Utils.getX(getStartX(), dist, getHeading() - getMaxEscapeAngle()),
                       Utils.getY(getStartY(), dist, getHeading() - getMaxEscapeAngle()));
         grid.drawLine(getStartX(), getStartY(), Utils.getX(getStartX(), dist, getHeading() + getMaxEscapeAngle()),
                       Utils.getY(getStartY(), dist, getHeading() + getMaxEscapeAngle()));
      }
   }

   public Object clone() {
      return new VirtualWave(this);
   }

   public boolean equals(Object obj) {
      if (obj instanceof VirtualWave) {
         VirtualWave wave = (VirtualWave) obj;
         return (wave.getStartX() == this.getStartX()) && (wave.getStartY() == this.getStartY()) && (wave.getHeading() == getHeading())
                && (wave.getCreationTime() == getCreationTime());
      }
      return false;
   }

   public String toString() {
      // TODO method stub
      return new String();
   }

   public void finalize() {
      START_X = 0.0D;
      START_Y = 0.0D;
      HEADING = 0.0D;
      FIRE_POWER = 0.0D;
      BULLET_VELOCITY = 0.0D;
      MAX_ESCAPE_ANGLE = 0.0D;
      CREATION_TIME = 0L;
   }

}