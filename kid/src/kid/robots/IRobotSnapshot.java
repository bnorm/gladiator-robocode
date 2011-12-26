package kid.robots;

/**
 * Represents a Robocode robot at a moment in time.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
public interface IRobotSnapshot {

   /**
    * Returns the name of the robot.
    * 
    * @return the name of the robot.
    */
   public String getName();

   /**
    * Returns the x coordinate of the current position of the robot.
    * 
    * @return the x coordinate of the robot.
    */
   public double getX();

   /**
    * Returns the y coordinate of the current position of the robot.
    * 
    * @return the y coordinate of the robot.
    */
   public double getY();

   /**
    * Returns the current energy of the robot.
    * 
    * @return the energy of the robot.
    */
   public double getEnergy();

   /**
    * Returns the current heading of the robot.
    * 
    * @return the heading of the robot.
    */
   public double getHeading();

   /**
    * Returns the current velocity of the robot.
    * 
    * @return the velocity of the robot.
    */
   public double getVelocity();

   /**
    * Returns the time at which the robot was scanned.
    * 
    * @return the time at which the robot was scanned.
    */
   public long getTime();

}
