package kid.data.info;

import java.io.*;

import robocode.*;

import kid.Utils;

// BORED perfect documentation

/**
 * The <code>GunInfo</code> class is used for accessing information about a robot's gun. Whether that information is
 * in the form of constants (e.g., the gun's turn speed, etc.) or in the form of accessing the robot's current
 * information (e.g., the gun's current heat, heading, etc.).
 * 
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class GunInfo extends RobotInfo {

   /**
    * Determines if a de-serialized file is compatible with this class.<BR>
    * <BR>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = -477733568063030206L;


   public static final double GUN_TURN_RATE = Rules.GUN_TURN_RATE;

   /**
    * Constructs a new <code>GunInfo</code> with the specific <code>Robot</code> class.
    * 
    * @see robocode.Robot
    * @param myRobot - the specific <code>Robot</code>
    */
   public GunInfo(Robot myRobot) {
      super(myRobot);
      init(myRobot);
   }

   /**
    * Constructs a new <code>GunInfo</code> with the specific <code>GunInfo</code> class. Used for cloning.
    * 
    * @param myRobot - the specific <code>GunInfo</code>
    */
   protected GunInfo(GunInfo myRobot) {
      super(myRobot);
      init(myRobot.robot);
   }


   /**
    * Initializes a new <code>GunInfo</code> with the specific <code>Robot</code> class.
    * 
    * @see robocode.Robot
    * @param myRobot - the specific <code>Robot</code>
    */
   private void init(Robot myRobot) {
      this.robot = myRobot;
   }

   /**
    * Returns the heading of the gun in degrees.
    * 
    * @return the gun's heading.
    */
   @Override
   public double getHeading() {
      return this.robot.getGunHeading();
   }

   /**
    * Returns the angle that the robot's gun has yet to turn.<BR>
    * <BR>
    * This will only work if the robot is an <code>AdvancedRobot</code>.
    * 
    * @return the turning anlge remaining
    */
   public double getTurnRemaining() {
      if (robot instanceof AdvancedRobot) {
         AdvancedRobot myRobot = (AdvancedRobot) robot;
         return myRobot.getGunTurnRemaining();
      }
      return 0.0;
   }

   /**
    * Returns the sign of the anlge that the robot's gun has yet to turn. Will return <code>0.0</code> if there is no
    * turning left to turn.<BR>
    * <BR>
    * This will only work if the robot is an <code>AdvancedRobot</code>.
    * 
    * @return the sign of the moveing distance remaining
    */
   public int getTurningSign() {
      return (getTurnRemaining() == 0.0 ? 0 : Utils.sign(getTurnRemaining()));
   }

   /**
    * Returns the current heat that the gun is giving off. The gun may fire again when the heat drops to zero.
    * 
    * @return the gun's heat
    */
   public double getHeat() {
      return this.robot.getGunHeat();
   }

   @Override
   public void print(PrintStream console) {
      // TODO method stub
   }

   @Override
   public void print(RobocodeFileOutputStream output) {
      // TODO method stub
      // PrintStream file = new PrintStream(output);
   }

   @Override
   public void debug(PrintStream console) {
      // TODO method stub
   }

   @Override
   public void debug(RobocodeFileOutputStream output) {
      // TODO method stub
      // PrintStream file = new PrintStream(output);
   }

   @Override
   public Object clone() {
      return new GunInfo(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof GunInfo) {
         GunInfo robot = (GunInfo) obj;
         return robot.robot.getName().equals(this.robot.getName());
      }
      return false;
   }

   @Override
   public String toString() {
      // TODO method stub
      return new String();
   }

   @Override
   protected void finalize() {
      this.robot = null;
   }

}