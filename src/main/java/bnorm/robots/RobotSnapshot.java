package bnorm.robots;

import bnorm.virtual.Vector;

import static bnorm.utils.Format.coordinateDec0;
import static bnorm.utils.Format.dec0;
import static bnorm.utils.Format.dec1;
import static bnorm.utils.Format.dec3;

/**
 * An abstract representation of a Robocode robot at a moment in time.
 *
 * @author Brian Norman
 * @version 1.3
 */
class RobotSnapshot extends Vector implements IRobotSnapshot {

   /**
    * Determines if a deserialized file is compatible with this class.
    * Maintainers must change this value if and only if the new version of this
    * class is not compatible with old versions.
    */
   private static final long serialVersionUID = -71766535645624169L;

   /**
    * The name of the robot.
    */
   private String name;

   /**
    * The energy of the robot.
    */
   private double energy;

   /**
    * The round time the information of the robot was updated.
    */
   private long time;

   /**
    * The match round the information of the robot was updated.
    */
   private int round;

   /**
    * Default constructor. Creates a blank snapshot that represents a dead
    * robot with no name.
    */
   protected RobotSnapshot() {
      this("", -1.0, -1.0, -1.0, 0.0, 0.0, -1, -1);
   }

   /**
    * Creates a new snapshot with the specified information.
    *
    * @param name name of the robot.
    * @param x x coordinate of the robot.
    * @param y y coordinate of the robot.
    * @param energy energy of the robot.
    * @param heading heading of the robot.
    * @param velocity velocity of the robot.
    * @param time round time the information was retrieved.
    * @param round match round the information was retrieved.
    */
   protected RobotSnapshot(String name, double x, double y, double energy, double heading, double velocity, long time,
                           int round) {
      super(x, y, heading, velocity);

      this.name = name;
      this.energy = energy;
      this.time = time;
      this.round = round;
   }

   /**
    * Copy constructor. Copies the specified snapshot.
    *
    * @param snapshot the snapshot to copy.
    */
   protected RobotSnapshot(IRobotSnapshot snapshot) {
      this(snapshot.getName(), snapshot.getX(), snapshot.getY(), snapshot.getEnergy(), snapshot.getHeading(),
           snapshot.getVelocity(), snapshot.getTime(), snapshot.getRound());
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public double getEnergy() {
      return energy;
   }

   @Override
   public long getTime() {
      return time;
   }

   @Override
   public int getRound() {
      return round;
   }

   @Override
   public String toString() {
      return this.getClass().getName() + "[n:" + getName() + " c:" + coordinateDec0(getX(), getY()) + " e:" + dec1(
              getEnergy()) + " h:" + dec3(getHeading()) + " v:" + dec0(getVelocity()) + " t:" + getTime() + " r:"
              + getRound() + "]";
   }

   @Override
   public int hashCode() {
      return name.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof IRobotSnapshot) {
         IRobotSnapshot snapshot = (IRobotSnapshot) obj;
         return getName() != null && getName().equals(snapshot.getName()) && snapshot.getTime() == getTime()
                 && snapshot.getRound() == getRound() && snapshot.getX() == getX() && snapshot.getY() == getY()
                 && snapshot.getEnergy() == getEnergy() && snapshot.getHeading() == getHeading()
                 && snapshot.getVelocity() == getVelocity();
      }
      return super.equals(obj);
   }
}
