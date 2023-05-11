package kid.messages;

import robocode.*;

// TODO documentation: class (30% complete)

public class RobotMessage extends Message {

   private static final long serialVersionUID = 5103237704700781761L;

   private String name;
   private double x;
   private double y;
   private double energy;
   private double heading;
   private double headingRadians;
   private double velocity;

   public RobotMessage(Robot robot) {
      super(robot.getTime());
      init(robot);
   }

   private void init(Robot robot) {
      this.name = robot.getName();
      this.x = robot.getX();
      this.y = robot.getY();
      this.energy = robot.getEnergy();
      this.heading = robot.getHeading();
      this.headingRadians = Math.toRadians(robot.getHeading());
      this.velocity = robot.getVelocity();
   }

   public double getX() {
      return x;
   }

   public double getY() {
      return y;
   }

   /**
    * Returns the energy of the robot.
    * 
    * @return the energy of the robot
    */
   public double getEnergy() {
      return energy;
   }

   /**
    * Returns the heading of the robot, in degrees (0 <= getHeading() < 360)
    * 
    * @return the heading of the robot, in degrees
    */
   public double getHeading() {
      return heading;
   }

   /**
    * Returns the heading of the robot, in radians (0 <= getHeading() < 2 * PI)
    * 
    * @return the heading of the robot, in radians
    */
   public double getHeadingRadians() {
      return headingRadians;
   }

   /**
    * Returns the name of the robot.
    * 
    * @return the name of the robot
    */
   public String getName() {
      return name;
   }

   /**
    * Returns the velocity of the robot.
    * 
    * @return the velocity of the robot
    */
   public double getVelocity() {
      return velocity;
   }
}