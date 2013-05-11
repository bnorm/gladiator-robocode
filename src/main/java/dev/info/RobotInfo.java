package dev.info;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import kid.robot.RobotData;
import kid.robot.RobotVector;
import kid.utils.Utils;
import robocode.AdvancedRobot;
import robocode.Robot;
import robocode.Rules;

// BORED perfect documentation

/**
 * The <code>RobotInfo</code> class is used for accessing information about a robot. Whether that
 * information is in the form of constants (e.g., the robot's height, width, etc.) or in the form of
 * accessing the robot's current information (e.g., the robot's current heading, velocity, etc.).
 * 
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class RobotInfo implements Cloneable, Serializable {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not
    * compatible with old versions.
    */
   private static final long serialVersionUID = -5451313209259625420L;

   /**
    * The width in pixels of a robot.
    */
   public static final double WIDTH = 36.0D;

   /**
    * The height in pixels of a robot.
    */
   public static final double HEIGHT = 36.0D;

   /**
    * The minimum distance that a robot can be away from a wall and not hit it.<br>
    * <br>
    * Averages the robot's height and width to obtain this value. The robot may be able to get
    * closer to the north and south or east and west wall depending on the dimensions of the robot.
    */
   public static final double MIN_WALL_DIST = (Math.max(HEIGHT, WIDTH) / 2.0D);

   /**
    * The starting energy of a robot.
    */
   public static final double START_ENERGY = 100.0D;

   /**
    * The starting energy of a droid.
    */
   public static final double START_ENERGY_DROID = 120.0D;

   /**
    * The starting energy of a team leader.
    */
   public static final double START_ENERGY_TEAM_LEADER = 200.0D;

   /**
    * The starting energy of a droid that is also a team leader.
    */
   public static final double START_ENERGY_DROID_TEAM_LEADER = 220.0D;

   /**
    * The maximum speed that a robot may travel at.
    */
   public static final double MAX_VELOCITY = Rules.MAX_VELOCITY;

   /**
    * The maximum speed that a robot may travel at.
    */
   public static final double MAX_VELOCITY_REV = -Rules.MAX_VELOCITY;

   // BORED documentation: field - MAX_TURN_RATE
   public static final double MAX_TURN_RATE = Rules.getTurnRate(0.0D);

   /**
    * The robot that information will be taken from and manipulated to produce more information.
    */
   protected Robot robot;

   // BORED documentation: field - battlefield
   private Rectangle2D battlefield;
   private double battleFieldWidth;
   private double battleFieldHeight;

   /**
    * Constructs a new <code>RobotInfo</code> with the specific <code>Robot</code> class.
    * 
    * @see robocode.Robot
    * @param myRobot
    *           - the specific <code>Robot</code>
    */
   public RobotInfo(final Robot myRobot) {
      init(myRobot);
   }

   /**
    * Constructs a new <code>RobotInfo</code> with the specific <code>RobotInfo</code> class. Used
    * for cloning.
    * 
    * @param myRobot
    *           - the specific <code>RobotInfo</code>
    */
   protected RobotInfo(final RobotInfo myRobot) {
      init(myRobot.robot);
   }

   /**
    * Initializes a new <code>RobotInfo</code> with the specific <code>Robot</code> class.
    * 
    * @see robocode.Robot
    * @param myRobot
    *           - the specific <code>Robot</code>
    */
   private void init(final Robot myRobot) {
      this.robot = myRobot;
      this.battleFieldWidth = robot.getBattleFieldWidth();
      this.battleFieldHeight = robot.getBattleFieldHeight();
      this.battlefield = new Rectangle2D.Double(RobotInfo.MIN_WALL_DIST, RobotInfo.MIN_WALL_DIST, battleFieldWidth
            - 2.0 * (RobotInfo.MIN_WALL_DIST), battleFieldHeight - 2.0 * (RobotInfo.MIN_WALL_DIST));
   }

   /**
    * Returns the name of the robot.
    * 
    * @return the robot's name
    */
   public String getName() {
      return this.robot.getName();
   }

   /**
    * Returns the <code>x</code> coordinate of the robot.
    * 
    * @return the robot's <code>x</code> coordinate
    */
   public double getX() {
      return this.robot.getX();
   }

   // public double getFutureX() {
   // return Utils.getX(getX(), getFutureVelocity(), getFutureHeading());
   // }

   /**
    * Returns the <code>y</code> coordinate of the robot.
    * 
    * @return the robot's <code>y</code> coordinate
    */
   public double getY() {
      return this.robot.getY();
   }

   // public double getFutureY() {
   // return Utils.getY(getY(), getFutureVelocity(), getFutureHeading());
   // }

   // BORED: documentation: method - getBattleField()
   public Rectangle2D getBattleField() {
      return this.battlefield;
   }

   /**
    * Returns the distance that the robot has yet to move.<br>
    * <br>
    * This will only work if the robot is an <code>AdvancedRobot</code>.
    * 
    * @return the moving distance remaining
    */
   public double getMoveRemaining() {
      if (robot instanceof AdvancedRobot)
         return ((AdvancedRobot) robot).getDistanceRemaining();
      return 0.0D;
   }

   /**
    * Returns the sign of the distance that the robot has yet to move. Will return <code>0</code> if
    * there is not movement left to move.<br>
    * <br>
    * This will only work if the robot is an <code>AdvancedRobot</code>.
    * 
    * @return the sign of the moving distance remaining
    */
   public int getMovingSign() {
      return (getMoveRemaining() == 0.0 ? 0 : Utils.sign(getMoveRemaining()));
   }

   /**
    * Returns the energy of the robot.
    * 
    * @return the robot's energy
    */
   public double getEnergy() {
      return this.robot.getEnergy();
   }

   /**
    * Returns the heading of the robot in degrees.
    * 
    * @return the robot's heading
    */
   public double getHeading() {
      return this.robot.getHeading();
   }

   public final double getFutureHeading() {
      return getHeading() + getTurningSign() * Math.min(Math.abs(getTurnRemaining()), getTurnRate());
   }

   public static final double getFutureHeading(RobotVector current, double targetHeading) {
      double targetTurn = current.getHeading() - targetHeading;
      return current.getHeading() + Utils.absMin(targetTurn, Rules.getTurnRate(current.getHeading()));
   }

   // BORED document
   public double getTurn() {
      return getTurnRate() * getTurningSign();
   }

   // BORED document
   public double getTurnRate() {
      return Rules.getTurnRate(this.getVelocity());
   }

   /**
    * Returns the angle that the robot has yet to turn.<br>
    * <br>
    * This will only work if the robot is an <code>AdvancedRobot</code>.
    * 
    * @return the turning angle remaining
    */
   public double getTurnRemaining() {
      if (robot instanceof AdvancedRobot)
         return ((AdvancedRobot) robot).getTurnRemaining();
      return 0.0;
   }

   /**
    * Returns the sign of the angle that the robot has yet to turn. Will return <code>0</code> if
    * there is not angle left to turn.<br>
    * <br>
    * This will only work if the robot is an <code>AdvancedRobot</code>.
    * 
    * @return the sign of the turning angle remaining
    */
   public int getTurningSign() {
      return (getTurnRemaining() == 0.0 ? 0 : Utils.sign(getTurnRemaining()));
   }

   /**
    * Returns the velocity of the robot in pixels per tick.
    * 
    * @return the robot's velocity
    */
   public double getVelocity() {
      return this.robot.getVelocity();
   }

   public final double getFutureVelocity() {
      return getFutureVelocity(getVelocity(), getMoveRemaining());
   }

   public static final double getFutureVelocity(final RobotVector current, final double targetVelocity) {
      return getFutureVelocity(current.getVelocity(), targetVelocity);
   }

   public static final double getFutureVelocity(final double current, final double target) {
      if (current * target < 0.0) {
         return (Math.abs(current) - Rules.DECELERATION);
      } else if (current == 0.0) {
         return Utils.sign(target) * Math.min(Rules.MAX_VELOCITY, Math.abs(current) + Rules.ACCELERATION);
      } else {
         return Utils.sign(current) * Math.min(Rules.MAX_VELOCITY, Math.abs(current) + Rules.ACCELERATION);
      }
   }

   /**
    * Returns the current time of the game in ticks.
    * 
    * @return the time of the game
    */
   public long getTime() {
      return this.robot.getTime();
   }

   /**
    * Returns the current number of robots on the battle field minus one. The reason for there being
    * one less is you do not want to include your robot in that number.
    * 
    * @return the number of other robots on the battle field
    */
   public int getOthers() {
      return this.robot.getOthers();
   }

   /**
    * Returns the width of the battle field.
    * 
    * @return the width of the battle field
    */
   public double getBattleFieldWidth() {
      return battleFieldWidth;
   }

   /**
    * Returns the height of the battle field.
    * 
    * @return the height of the battle field
    */
   public double getBattleFieldHeight() {
      return battleFieldHeight;
   }

   /**
    * Returns the angle to a coordinate <code>(x, y)</code>.
    * 
    * @param x
    *           - the ordinate coordinate
    * @param y
    *           - the abscissa coordinate
    * @return the angle to the coordinates
    */
   public final double angle(final double x, final double y) {
      double theta = Utils.atan2(x - this.getX(), y - this.getY());
      return Utils.relative(theta);
   }

   /**
    * Returns the angle to a robot.<br>
    * <br>
    * If robot is <code>null</code> or dead, <code>Double.POSITIVE_INFINITY</code> will be returned.
    * 
    * @param robot
    *           - the robot
    * @return the angle to the robot
    */
   public final double angle(final RobotData robot) {
      if (robot == null || robot.isDead())
         return getHeading();
      return angle(robot.getX(), robot.getY());
   }

   /**
    * Returns the angle to a point.<br>
    * <br>
    * If point is <code>null</code>, <code>Double.POSITIVE_INFINITY</code> will be returned.
    * 
    * @param point
    *           - the point
    * @return the angle to the point
    */
   public final double angle(final Point2D point) {
      if (point == null)
         return Double.POSITIVE_INFINITY;
      return angle(point.getX(), point.getY());
   }

   /**
    * Returns the bearing, relative to the robot's heading, to an angle.<br>
    * <br>
    * Note: The robot's heading may be over riding with the gun or radar heading if the class was
    * initialized by <code>GunInfo</code> or <code>RadarInfo</code>.
    * 
    * @param angle
    *           - the angle
    * @return the bearing to the angle
    */
   public final double bearing(final double angle) {
      return Utils.relative(angle - getHeading());
   }

   /**
    * Returns the bearing, relative to the robot's heading, to a coordinate <code>(x, y)</code>.<br>
    * <br>
    * Note: The robot's heading may be over riding with the gun or radar heading if the class was
    * initialized by <code>GunInfo</code> or <code>RadarInfo</code>.
    * 
    * @param x
    *           - the ordinate coordinate
    * @param y
    *           - the abscissa coordinate
    * @return the bearing to the coordinate
    */
   public final double bearing(final double x, final double y) {
      return bearing(angle(x, y));
   }

   /**
    * Returns the bearing, relative to the robot's heading, to an other robot.<br>
    * <br>
    * If robot is <code>null</code> or dead, <code>Double.POSITIVE_INFINITY</code> will be returned. <br>
    * Note: The robot's heading may be over riding with the gun or radar heading if the class was
    * initialized by <code>GunInfo</code> or <code>RadarInfo</code>.
    * 
    * @param robot
    *           - the other robot
    * @return the bearing to the other robot
    */
   public final double bearing(final RobotData robot) {
      if (robot == null || robot.isDead())
         return Double.POSITIVE_INFINITY;
      return bearing(angle(robot));
   }

   /**
    * Returns the bearing, relative to the robot's heading, to a point.<br>
    * <br>
    * If point is <code>null</code>, <code>Double.POSITIVE_INFINITY</code> will be returned. <br>
    * Note: The robot's heading may be over riding with the gun or radar heading if the class was
    * initialized by <code>GunInfo</code> or <code>RadarInfo</code>.
    * 
    * @param point
    *           - the point
    * @return the bearing to the point
    */
   public final double bearing(final Point2D point) {
      if (point == null)
         return Double.POSITIVE_INFINITY;
      return bearing(angle(point));
   }

   /**
    * Returns the distance squared to a coordinate <code>(x, y)</code>.
    * 
    * @param x
    *           - the ordinate coordinate
    * @param y
    *           - the abscissa coordinate
    * @return the distance squared to the coordinate
    */
   public final double distSq(final double x, final double y) {
      return Utils.distSq(getX(), getY(), x, y);
   }

   /**
    * Returns the distance squared to a robot.<br>
    * <br>
    * If robot is <code>null</code> or dead, <code>Double.POSITIVE_INFINITY</code> will be returned.
    * 
    * @param robot
    *           - the robot
    * @return the distance squared to the robot
    */
   public final double distSq(final RobotData robot) {
      if (robot == null || robot.isDead())
         return Double.POSITIVE_INFINITY;
      return distSq(robot.getX(), robot.getY());
   }

   /**
    * Returns the distance squared to a point.<br>
    * <br>
    * If point is <code>null</code>, <code>Double.POSITIVE_INFINITY</code> will be returned.
    * 
    * @param point
    *           - the point
    * @return the distance squared to the point
    */
   public final double distSq(final Point2D point) {
      if (point == null)
         return Double.POSITIVE_INFINITY;
      return distSq(point.getX(), point.getY());
   }

   /**
    * Returns the distance to a coordinate <code>(x, y)</code>.
    * 
    * @param x
    *           - the ordinate coordinate
    * @param y
    *           - the abscissa coordinate
    * @return the distance to the coordinate
    */
   public final double dist(final double x, final double y) {
      return Utils.sqrt(distSq(x, y));
   }

   /**
    * Returns the distance to a robot.<br>
    * <br>
    * If robot is <code>null</code> or dead, <code>Double.POSITIVE_INFINITY</code> will be returned.
    * 
    * @param robot
    *           - the robot
    * @return the distance to the robot
    */
   public final double dist(final RobotData robot) {
      return Utils.sqrt(distSq(robot));
   }

   /**
    * Returns the distance to a point.<br>
    * <br>
    * If point is <code>null</code>, <code>Double.POSITIVE_INFINITY</code> will be returned.
    * 
    * @param point
    *           - the point
    * @return the distance to the point
    */
   public final double dist(final Point2D point) {
      return Utils.sqrt(distSq(point));
   }

   // BORED document method
   public static final double turnRadius(final double velocity) {
      return (Utils.HALF_CIRCLE * Math.abs(velocity)) / (Math.PI * turnRate(velocity));
   }

   // BORED document method
   public static final double turnRate(final double velocity) {
      return Rules.getTurnRate(velocity);
   }


   @Override
   public Object clone() {
      return new RobotInfo(this);
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof RobotInfo) {
         RobotInfo robot = (RobotInfo) obj;
         return robot.getName().equals(this.getName());
      }
      return false;
   }

   @Override
   protected void finalize() throws Throwable {
      this.robot = null;
      super.finalize();
   }

}
