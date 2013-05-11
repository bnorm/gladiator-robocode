package kid.communication;

import kid.utils.Utils;
import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 * A <code>class</code> that can be used to send information about a
 * <code>{@link robocode.ScannedRobotEvent ScannedRobotEvent}</code> to teammates. It copies all the data into private
 * fields so that it can be easily be sent to teammates.
 */
public class ScannedRobotMessage extends Message {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = 5103237704700781761L;

   /**
    * The name of the scanned robot.
    */
   private String            name;

   private double            x;
   private double            y;

   /**
    * The current energy level of the scanned robot.
    */
   private double            energy;

   /**
    * The current heading of the scanned robot.
    */
   private double            heading;

   /**
    * The current heading of the scanned robot in radians.
    */
   private double            headingRadians;

   /**
    * The velocity of the scanned robot.
    */
   private double            velocity;

   /**
    * Converts a <code>{@link robocode.ScannedRobotEvent ScannedRobotEvent}</code> in to a
    * <code>ScannedRobotMessage</code> so that it may be sent to teammates or used for a reference when updating
    * information on a robot.
    * 
    * @param sre
    *           - the <code>{@link robocode.ScannedRobotEvent ScannedRobotEvent}</code> to convert
    * @param reference
    *           - the <code>{@link robocode.Robot Robot}</code> that this event was passed to
    */
   public ScannedRobotMessage(final ScannedRobotEvent sre, final Robot reference) {
      super(sre.getTime());
      init(sre, reference);
   }

   /**
    * Initializes all the fields. Used for congruence between all the constructors.
    * 
    * @param sre
    *           - the <code>{@link robocode.ScannedRobotEvent ScannedRobotEvent}</code> to convert
    * @param reference
    *           - the <code>{@link robocode.Robot Robot}</code> that this event was passed to
    */
   private void init(final ScannedRobotEvent sre, final Robot reference) {
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
