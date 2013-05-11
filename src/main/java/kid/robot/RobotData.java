package kid.robot;

import java.awt.geom.Rectangle2D;
import java.io.PrintStream;
import java.io.Serializable;

import kid.communication.RobotMessage;
import kid.communication.ScannedRobotMessage;
import kid.data.Drawable;
import kid.data.Printable;
import kid.data.gravity.GravityObject;
import kid.graphics.Colors;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.info.RobotInfo;
import kid.segmentation.Segmentable;
import kid.utils.Utils;
import robocode.RobocodeFileOutputStream;
import robocode.Robot;
import robocode.ScannedRobotEvent;

// TODO documentation: class (60% complete) - RoboData
// BORED documentation: perfect

/**
 * A class used for storing information about a robot.
 * 
 * @author Brian Norman
 * @version 0.0.5 beta
 */
public class RobotData implements Cloneable, Serializable, Segmentable, Printable, Drawable, GravityObject {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not
    * compatible with old versions.
    */
   private static final long serialVersionUID = 6554353696765554133L;

   /**
    * A possible alias. Used when a robot is still active.
    */
   public static final String ALIVE = "Alive";

   /**
    * A possible alias. Used when a robot is deactivated but not destroyed.
    */
   public static final String DISABLED = "Disabled";

   /**
    * A possible alias. Used when a robot is destroyed.
    */
   public static final String DEAD = "Dead";

   /**
    * A possible alias. Used when a robot is a fake robot.
    */
   public static final String DUMMY = "Dummy";

   /**
    * The weight that the original average cares when calculating the new average.
    */
   protected static final double AVERAGE_WEIGHT = 5.0D;

   /**
    * The energy that a robot has when it is destroyed.
    */
   protected static final double DEAD_ENERGY = -1.0D;

   /**
    * The name of the robot.
    */
   protected String name;

   /**
    * The alias of the robot.<br>
    * <br>
    * e.g. "alive", "dead", "disabled", etc.
    */
   protected String alias;

   /**
    * The <code>x</code> coordinate of the robot.
    */
   protected double x;

   /**
    * The <code>y</code> coordinate of the robot.
    */
   protected double y;

   /**
    * The energy of the robot.
    */
   protected double energy;

   /**
    * The difference of the robot's current energy and it's last known energy.
    */
   protected double deltaEnergy;

   /**
    * The robot's heading in degrees.
    */
   protected double heading;

   /**
    * The difference in degrees of the robot's current heading and it's last known heading.
    */
   protected double deltaHeading;

   /**
    * The robot's average delta heading.
    * 
    * @see #deltaHeading
    */
   protected double avgDeltaHeading;

   /**
    * The robot's speed in pixels per tick.
    */
   protected double velocity;

   /**
    * The difference in speed of the robot's current velocity and it's last known velocity.
    */
   protected double deltaVelocity;

   /**
    * The robot's average velocity.
    * 
    * @see #velocity
    */
   protected double avgVelocity;

   /**
    * The robot's average delta velocity.
    * 
    * @see #avgDeltaVelocity
    */
   protected double avgDeltaVelocity;

   /**
    * The last time at which the robot's information was updated.
    */
   protected long time;

   public RobotData() {
      init(new String(), -5000000.0D, -5000000.0D, DEAD_ENERGY, 0.0D, 0.0D, -1);
      setAlias(RobotData.DUMMY);
   }

   public RobotData(final String name, final double curX, final double curY, final double curEnergy,
         final double curHeading, final double curVelocity, final long curTime) {
      init(name, curX, curY, curEnergy, curHeading, curVelocity, curTime);
   }

   public RobotData(final ScannedRobotEvent sre, final Robot myRobot) {
      double curX = Utils.getX(myRobot.getX(), sre.getDistance(), myRobot.getHeading() + sre.getBearing());
      double curY = Utils.getY(myRobot.getY(), sre.getDistance(), myRobot.getHeading() + sre.getBearing());
      init(sre.getName(), curX, curY, sre.getEnergy(), sre.getHeading(), sre.getVelocity(), sre.getTime());
   }

   public RobotData(final ScannedRobotMessage srm, final Robot myRobot) {
      init(srm.getName(), srm.getX(), srm.getY(), srm.getEnergy(), srm.getHeading(), srm.getVelocity(), srm.getTime());
   }

   public RobotData(final ScannedRobotEvent sre, final RobotData myRobot) {
      double curX = Utils.getX(myRobot.getX(), sre.getDistance(), myRobot.getHeading() + sre.getBearing());
      double curY = Utils.getY(myRobot.getY(), sre.getDistance(), myRobot.getHeading() + sre.getBearing());
      init(sre.getName(), curX, curY, sre.getEnergy(), sre.getHeading(), sre.getVelocity(), sre.getTime());
   }

   public RobotData(final ScannedRobotMessage srm, final RobotData myRobot) {
      init(srm.getName(), srm.getX(), srm.getY(), srm.getEnergy(), srm.getHeading(), srm.getVelocity(), srm.getTime());
   }

   public RobotData(final Robot myRobot) {
      init(myRobot.getName(), myRobot.getX(), myRobot.getY(), myRobot.getEnergy(), myRobot.getHeading(), myRobot
            .getVelocity(), myRobot.getTime());
   }

   public RobotData(final RobotData robot) {
      init(robot.getName(), robot.getAlias(), robot.getX(), robot.getY(), robot.getEnergy(), robot.getDeltaEnergy(),
            robot.getHeading(), robot.getDeltaHeading(), robot.getAvgDeltaHeading(), robot.getVelocity(), robot
                  .getDeltaVelocity(), robot.getAvgVelocity(), robot.getAvgDeltaVelocity(), robot.getTime());
   }

   protected void init(final String name, final double curX, final double curY, final double curEnergy,
         final double curHeading, final double curVelocity, final long curTime) {
      init(name, (curEnergy > 0.0D ? ALIVE : DEAD), curX, curY, curEnergy, 0.0D, curHeading, 0.0D, 0.0D, curVelocity,
            0.0D, curVelocity, 0.0D, curTime);
   }

   protected void init(final String name, final String alias, final double x, final double y, final double energy,
         final double deltaEnergy, final double heading, final double deltaHeading, final double avgDeltaHeading,
         final double velocity, final double deltaVelocity, final double avgVelocity, final double avgDeltaVelocity,
         final long time) {
      this.name = name;
      this.alias = alias;
      this.x = x;
      this.y = y;
      this.energy = energy;
      this.deltaEnergy = deltaEnergy;
      this.heading = heading;
      this.deltaHeading = deltaHeading;
      this.avgDeltaHeading = avgDeltaHeading;
      this.velocity = velocity;
      this.deltaVelocity = deltaVelocity;
      this.avgVelocity = avgVelocity;
      this.avgDeltaVelocity = avgDeltaVelocity;
      this.time = time;
   }

   /**
    * Updates the <code>RobotData class</code> with the new given information. It assumes that the
    * robot went straight from its previous known position to the new position.<br>
    * <br>
    * This method should not be called directly but should be called through either
    * <code>{@link kid.robot.RobotData#update(ScannedRobotEvent, Robot) update(ScannedRobotEvent, Robot)}</code>
    * or <code>{@link kid.robot.RobotData#update(Robot) update(Robot)}</code>.
    * 
    * @param curX
    *           - the <code>x</code> component of the robot's current position.
    * @param curY
    *           - the <code>y</code> component of the robot's current position.
    * @param curEnergy
    *           - the robot's current energy.
    * @param curHeading
    *           - the robot's current heading in degrees.
    * @param curVelocity
    *           - the robot's current velocity.
    * @param curTime
    *           - the time at which all these things were <code>true</code> of the robot.
    * @see #update(ScannedRobotEvent, Robot)
    * @see #update(Robot)
    */
   public void update(final double curX, final double curY, final double curEnergy, final double curHeading,
         final double curVelocity, final long curTime) {
      long deltaTime = (curTime - this.time);
      this.x = curX;
      this.y = curY;
      this.deltaEnergy = (curEnergy - this.energy) / deltaTime;
      this.energy = curEnergy;
      this.deltaHeading = Utils.relative((curHeading - this.heading)) / deltaTime;
      this.avgDeltaHeading = Utils.weightedAvg(this.avgDeltaHeading, RobotData.AVERAGE_WEIGHT, this.deltaHeading, 1.0D);
      this.heading = curHeading;
      this.deltaVelocity = (curVelocity - this.velocity) / deltaTime;
      this.avgDeltaVelocity = Utils.weightedAvg(this.avgDeltaVelocity, RobotData.AVERAGE_WEIGHT, this.deltaVelocity,
            1.0D);
      this.avgVelocity = Utils.weightedAvg(this.avgVelocity, RobotData.AVERAGE_WEIGHT, this.velocity, 1.0D);
      this.velocity = curVelocity;
      this.time = curTime;
   }

   /**
    * Updates the robot when it was previously dead. Used only once at the beginning of each round.
    * Updates the robot so that all deltas and averages are reset and the robot's information is all
    * reset to the current information.<br>
    * <br>
    * This method should never be called directly but should only be called through either
    * <code>{@link kid.robot.RobotData#update(ScannedRobotEvent, Robot) update(ScannedRobotEvent, Robot)}</code>
    * or <code>{@link kid.robot.RobotData#update(Robot) update(Robot)}</code>.
    * 
    * @param curX
    *           - the <code>x</code> component of the robot's starting position.
    * @param curY
    *           - the <code>y</code> component of the robot's starting position.
    * @param curEnergy
    *           - the robot's starting energy.
    * @param curHeading
    *           - the robot's start heading in degrees.
    * @param curVelocity
    *           - the robot's starting velocity.
    * @param curTime
    *           - the time at which all these things were <code>true</code> of the robot.
    * @see #update(ScannedRobotEvent, Robot)
    * @see #update(Robot)
    */
   private void updateFromDeath(final double curX, final double curY, final double curEnergy, final double curHeading,
         final double curVelocity, final long curTime) {
      setAlias(RobotData.ALIVE);
      this.x = curX;
      this.y = curY;
      this.energy = curEnergy;
      this.deltaEnergy = 0.0D;
      this.deltaHeading = 0.0D;
      this.avgDeltaHeading = 0.0D;
      this.heading = curHeading;
      this.deltaVelocity = 0.0D;
      this.avgDeltaVelocity = 0.0D;
      this.avgVelocity = 0.0D;
      this.velocity = curVelocity;
      this.time = curTime;
   }

   public void update(final ScannedRobotEvent sre, final Robot myRobot) {
      if ((myRobot.getTime() - time) < 0 && isDead()) {
         double curX = Utils.getX(myRobot.getX(), sre.getDistance(), myRobot.getHeading() + sre.getBearing());
         double curY = Utils.getY(myRobot.getY(), sre.getDistance(), myRobot.getHeading() + sre.getBearing());
         updateFromDeath(curX, curY, sre.getEnergy(), sre.getHeading(), sre.getVelocity(), sre.getTime());
      } else if ((myRobot.getTime() - time) > 0 && !isDead()) {
         double curX = Utils.getX(myRobot.getX(), sre.getDistance(), myRobot.getHeading() + sre.getBearing());
         double curY = Utils.getY(myRobot.getY(), sre.getDistance(), myRobot.getHeading() + sre.getBearing());
         update(curX, curY, sre.getEnergy(), sre.getHeading(), sre.getVelocity(), sre.getTime());
      }
   }

   public void update(final ScannedRobotMessage srm, final Robot myRobot) {
      if ((myRobot.getTime() - time) < 0 && isDead()) {
         updateFromDeath(srm.getX(), srm.getY(), srm.getEnergy(), srm.getHeading(), srm.getVelocity(), srm.getTime());
      } else if ((myRobot.getTime() - time) > 0 && !isDead()) {
         update(srm.getX(), srm.getY(), srm.getEnergy(), srm.getHeading(), srm.getVelocity(), srm.getTime());
      }
   }

   public void update(final RobotMessage rm, final Robot myRobot) {
      if ((myRobot.getTime() - time) < 0 && isDead()) {
         updateFromDeath(rm.getX(), rm.getY(), rm.getEnergy(), rm.getHeading(), rm.getVelocity(), rm.getTime());
      } else if ((myRobot.getTime() - time) > 0 && !isDead()) {
         update(rm.getX(), rm.getY(), rm.getEnergy(), rm.getHeading(), rm.getVelocity(), rm.getTime());
      }
   }

   public void update(final Robot myRobot) {
      if ((myRobot.getTime() - time) < 0 && isDead()) {
         updateFromDeath(myRobot.getX(), myRobot.getY(), myRobot.getEnergy(), myRobot.getHeading(), myRobot
               .getVelocity(), myRobot.getTime());
      } else if ((myRobot.getTime() - time) > 0 && !isDead()) {
         update(myRobot.getX(), myRobot.getY(), myRobot.getEnergy(), myRobot.getHeading(), myRobot.getVelocity(),
               myRobot.getTime());
      }
   }

   /**
    * Sets a robot as dead. Should be called as soon as a robot is destroyed.
    */
   public void setDeath() {
      setAlias(DEAD);
      energy = DEAD_ENERGY;
   }

   /**
    * Sets the alias of a robot. Will not set it to <code>null</code>.
    * 
    * @param newAlias
    *           - the desired alias.
    */
   public void setAlias(final String newAlias) {
      if (newAlias != null) {
         alias = newAlias;
      }
   }

   /**
    * Returns the name of the robot.
    * 
    * @return the robot's name.
    */
   public String getName() {
      return name;
   }

   /**
    * Returns the alias of the robot.
    * 
    * @return the robot's alias.
    */
   public String getAlias() {
      return alias;
   }

   /**
    * Returns if the robot is dead or not.
    * 
    * @return if the robot is dead or not.
    */
   public boolean isDead() {
      return (getEnergy() == DEAD_ENERGY || isDummy());
   }

   /**
    * Returns if the robot is a fake or not.
    * 
    * @return if the robot is fake or not.
    */
   public boolean isDummy() {
      return (getAlias().equals(DUMMY));
   }

   /**
    * Returns the x value of the robot's current coordinate.
    * 
    * @return the robot's x coordinate.
    */
   public double getX() {
      return x;
   }

   /**
    * Returns the y value of the robot's current coordinate.
    * 
    * @return the robot's y coordinate.
    */
   public double getY() {
      return y;
   }

   /**
    * Returns a <code>Rectangle2D</code> that is the height, width and at the current position of
    * the robot.
    * 
    * @return the robot's <code>Rectangle</code>.
    */
   public Rectangle2D getRectangle() {
      return new Rectangle2D.Double(getX() - (RobotInfo.WIDTH / 2.0D), getY() - (RobotInfo.HEIGHT / 2.0D),
            RobotInfo.WIDTH, RobotInfo.HEIGHT);
   }

   /**
    * Returns the robot's current energy.
    * 
    * @return the robot's energy.
    */
   public double getEnergy() {
      return energy;
   }

   /**
    * Returns the robot's delta energy.
    * 
    * @return the robot's delta energy.
    */
   public double getDeltaEnergy() {
      return deltaEnergy;
   }

   /**
    * Returns the robot's current heading.
    * 
    * @return the robot's heading.
    */
   public double getHeading() {
      return heading;
   }

   /**
    * Returns the robot's delta heading.
    * 
    * @return the robot's delta heading.
    * @see #deltaHeading
    */
   public double getDeltaHeading() {
      return deltaHeading;
   }

   /**
    * Returns the robot's average delta heading.
    * 
    * @return the robot's average delta heading.
    * @see #avgDeltaHeading
    */
   public double getAvgDeltaHeading() {
      return avgDeltaHeading;
   }

   /**
    * Returns the robot's current velocity.
    * 
    * @return the robot's velocity.
    */
   public double getVelocity() {
      return velocity;
   }

   /**
    * Returns the robot's delta velocity.
    * 
    * @return the robot's delta velocity.
    * @see #deltaVelocity
    */
   public double getDeltaVelocity() {
      return deltaVelocity;
   }

   /**
    * Returns the robot's average velocity.
    * 
    * @return the robot's average velocity
    * @see #avgVelocity
    */
   public double getAvgVelocity() {
      return avgVelocity;
   }

   /**
    * Returns the robot's average delta velocity.
    * 
    * @return the robot's average delta velocity.
    * @see #avgDeltaVelocity
    */
   public double getAvgDeltaVelocity() {
      return avgDeltaVelocity;
   }

   /**
    * Returns the time at which the robot was scanned.
    * 
    * @return the time at which the robot was scanned.
    */
   public long getTime() {
      return time;
   }

   @Override
   public boolean active(final long time) {
      return !isDead();
   }

   @Override
   public double angle(final double x, final double y) {
      return Utils.angle(getX(), getY(), x, y);
   }

   @Override
   public double deltaX(final double x) {
      return getX() - x;
   }

   @Override
   public double deltaY(final double y) {
      return getY() - y;
   }

   @Override
   public double dist(final double x, final double y) {
      return Utils.sqrt(distSq(x, y));
   }

   @Override
   public double distSq(final double x, final double y) {
      return Utils.distSq(deltaX(x), deltaY(y));
   }

   @Override
   public double getStrength() {
      return getEnergy();
   }

   public void print(final PrintStream console) {
      console.println(toString());
   }

   public void print(final RobocodeFileOutputStream output) {
      PrintStream file = new PrintStream(output);
      file.println(getClass() + ": " + getName() + " at Time: " + getTime());
      file.println("Current Alias:          " + getAlias());
      file.println("Coordinates:           (" + Math.round(getX()) + ", " + Math.round(getY()) + ")");
      file.println("Energy:                 " + getEnergy());
      file.println("Heading:                " + getHeading());
      file.println("Delta Heading:          " + getDeltaHeading());
      file.println("Average Delta Heading:  " + getAvgDeltaHeading());
      file.println("Velocty:                " + getVelocity());
      file.println("Delta Velocity:         " + getDeltaVelocity());
      file.println("Average Delta Velocity: " + getAvgDeltaVelocity());
      file.println();
   }

   public void draw(final RGraphics grid) {
      if (isDead())
         return;
      if (DrawMenu.getValue("Square", "Robot")) {
         grid.setColor(Colors.LIGHT_GRAY);
         grid.draw(getRectangle());
      }
   }

   public RobotData copy() {
      return new RobotData(this);
   }

   @Override
   public Object clone() {
      return new RobotData(this);
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof RobotData) {
         RobotData robot = (RobotData) obj;
         return robot.getName().equals(getName()) && robot.getTime() == getTime();
      }
      return false;
   }

   @Override
   public String toString() {
      return new String(getClass() + ": " + getName() + ": (" + getX() + ", " + getY() + ") " + getEnergy() + " "
            + getHeading() + " " + getVelocity() + " " + getTime());
   }

   @Override
   protected void finalize() throws Throwable {
      this.name = null;
      this.alias = null;
      this.x = 0.0D;
      this.y = 0.0D;
      this.energy = 0.0D;
      this.heading = 0.0D;
      this.deltaHeading = 0.0D;
      this.avgDeltaHeading = 0.0D;
      this.velocity = 0.0D;
      this.deltaVelocity = 0.0D;
      this.avgDeltaVelocity = 0.0D;
      this.time = 0L;
      super.finalize();
   }

}
