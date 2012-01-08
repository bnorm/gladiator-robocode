package kid.robots;

/**
 * Concrete representation of a Robocode robot. Specifically, this robot
 * represents an enemy robot.
 * 
 * @author Brian Norman (KID)
 * @version 1.1
 */
public class Enemy extends AbstractRobot {

   /**
    * Creates a new enemy robot.
    */
   protected Enemy() {
      super();
   }

   /**
    * Creates a new enemy robot with the specified name.
    * 
    * @param name
    *           the name of the enemy.
    */
   public Enemy(String name) {
      super(name);
   }

   /**
    * Creates a new enemy robot that is a copy of the specified robot.
    * 
    * @param robot
    *           the robot to copy.
    */
   protected Enemy(IRobot robot) {
      super(robot);
   }

}
