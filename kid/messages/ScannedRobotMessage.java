package kid.messages;

import kid.Utils;
import robocode.*;

public class ScannedRobotMessage extends Message {

   private static final long serialVersionUID = 5103237704700781761L;

   private String name;
   private double x;
   private double y;
   private double energy;
   private double heading;
   private double headingRadians;
   private double velocity;

   public ScannedRobotMessage(ScannedRobotEvent sre, Robot reference) {
      super(sre.getTime());
      init(sre, reference);
   }

   private void init(ScannedRobotEvent sre, Robot reference) {
      this.name = sre.getName();
      this.x = Utils.getX(reference.getX(), sre.getDistance(), reference.getHeading() + sre.getBearing());
      this.y = Utils.getY(reference.getY(), sre.getDistance(), reference.getHeading() + sre.getBearing());
      this.energy = sre.getEnergy();
      this.heading = sre.getHeading();
      this.headingRadians = sre.getHeadingRadians();
      this.velocity = sre.getVelocity();
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
