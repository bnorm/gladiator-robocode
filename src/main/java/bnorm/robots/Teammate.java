package bnorm.robots;

/**
 * Concrete representation of a Robocode robot. Specifically, this robot
 * represents a teammate robot.
 * 
 * @author Brian Norman (KID)
 * @version 1.1
 */
public class Teammate extends Robot {

   /**
    * Creates a new teammate robot.
    */
   protected Teammate() {
      super();
   }

   /**
    * Creates a new teammate robot with the specified name.
    * 
    * @param name
    *           the name of the teammate.
    */
   public Teammate(String name) {
      super(name);
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
