package kid.robots;

import static kid.utils.Format.coordinateDec0;
import static kid.utils.Format.dec0;
import static kid.utils.Format.dec1;
import static kid.utils.Format.dec3;

/**
 * An abstract representation of a Robocode robot at a moment in time.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
abstract class AbstractRobotSnapshot implements IRobotSnapshot {

   /**
    * The energy that a robot has when it is destroyed.
    */
   private static final double DEAD_ENERGY_ = -1.0D;

   /**
    * The name of the robot.
    */
   private String              name_;

   /**
    * The x coordinate of the robot.
    */
   private double              x_;

   /**
    * The y coordinate of the robot.
    */
   private double              y_;

   /**
    * The energy of the robot.
    */
   private double              energy_;

   /**
    * The heading in radians of the robot.
    */
   private double              heading_;

   /**
    * The speed in pixels per tick of the robot.
    */
   private double              velocity_;

   /**
    * The last time the information of the robot was updated.
    */
   private long                time_;

   /**
    * Default constructor. Creates a blank snapshot that represents a dead robot
    * with no name.
    */
   protected AbstractRobotSnapshot() {
      init(new String(), -1.0, -1.0, DEAD_ENERGY_, 0.0D, 0.0D, -1);
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
   protected AbstractRobotSnapshot(String name, double x, double y, double energy, double heading, double velocity, long time) {
      init(name, x, y, energy, heading, velocity, time);
   }

   /**
    * Copy constructor. Copies the specified snapshot.
    * 
    * @param snapshot
    *           the snapshot to copy.
    */
   protected AbstractRobotSnapshot(IRobotSnapshot snapshot) {
      init(snapshot.getName(), snapshot.getX(), snapshot.getY(), snapshot.getEnergy(), snapshot.getHeading(), snapshot.getVelocity(),
            snapshot.getTime());
   }

   /**
    * Initiates the snapshot with the specified information.
    * 
    * @param n
    *           name of the robot.
    * @param x
    *           x coordinate of the robot.
    * @param y
    *           y coordinate of the robot.
    * @param e
    *           energy of the robot.
    * @param h
    *           heading of the robot.
    * @param v
    *           velocity of the robot.
    * @param t
    *           time the information was retrieved.
    */
   protected void init(String n, double x, double y, double e, double h, double v, long t) {
      this.name_ = n;
      this.x_ = x;
      this.y_ = y;
      this.energy_ = e;
      this.heading_ = h;
      this.velocity_ = v;
      this.time_ = t;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getName() {
      return name_;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public double getX() {
      return x_;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public double getY() {
      return y_;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public double getEnergy() {
      return energy_;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public double getHeading() {
      return heading_;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public double getVelocity() {
      return velocity_;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public long getTime() {
      return time_;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return new String(this.getClass().getName() + "[n:" + getName() + " c:" + coordinateDec0(getX(), getY()) + " e:" + dec1(getEnergy()) + " h:"
            + dec3(getHeading()) + " v:" + dec0(getVelocity()) + " t:" + getTime() + "]");
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return name_.hashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object obj) {
      if (obj instanceof IRobotSnapshot) {
         IRobotSnapshot snapshot = (IRobotSnapshot) obj;
         return getName() != null && getName().equals(snapshot.getName()) && snapshot.getTime() == getTime();
      }
      return super.equals(obj);
   }

}
