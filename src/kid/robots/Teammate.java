package kid.robots;

/**
 * Concrete representation of a Robocode robot. Specifically, this robot
 * represents a teammate robot.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
public class Teammate extends AbstractRobot {

   /**
    * Creates a new teammate robot.
    */
   protected Teammate() {
      super();
   }

   /**
    * Creates a new teammate robot that is a copy of the specified robot.
    * 
    * @param robot
    *           the robot to copy.
    */
   protected Teammate(IRobot robot) {
      super(robot);
   }

}
