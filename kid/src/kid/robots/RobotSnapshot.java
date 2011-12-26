package kid.robots;

/**
 * The concrete representation of a Robocode robot at a moment in time.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
class RobotSnapshot extends AbstractRobotSnapshot {

   /**
    * Default constructor. Creates a blank snapshot that represents a dead robot
    * with no name.
    */
   public RobotSnapshot() {
      super();
   }

   /**
    * Creates a new snapshot with the specified information.
    * 
    * @param name
    *           name of the robot.
    * @param x
    *           x coordinate of the robot.
    * @param y
    *           y coordinate of the robot.
    * @param energy
    *           energy of the robot.
    * @param heading
    *           heading of the robot.
    * @param velocity
    *           velocity of the robot.
    * @param time
    *           time the information was retrieved.
    */
   public RobotSnapshot(String name, double x, double y, double energy, double heading, double velocity, long time) {
      super(name, x, y, energy, heading, velocity, time);
   }

   /**
    * Copy constructor. Copies the specified snapshot.
    * 
    * @param snapshot
    *           the snapshot to copy.
    */
   protected RobotSnapshot(IRobotSnapshot snapshot) {
      super(snapshot);
   }

}
