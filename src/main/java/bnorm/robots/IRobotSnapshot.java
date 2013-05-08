package bnorm.robots;

import java.io.Serializable;

import bnorm.virtual.IVector;

/**
 * Represents a Robocode robot at a moment in time.
 * <p>
 * A blank robot snapshot, commonly called a blank snapshot by documentation,
 * is a snapshot that contains no relevant information. There are only two
 * guarantees that can be made about a blank snapshot. First, the name of the
 * robot will be non-null but contain no characters, therefore the
 * {@link String#isEmpty() isEmpty()} method of {@link String} will return
 * <code>true</code>. Second, the energy of the robot will be less than zero
 * which is considered a dead robot.
 *
 * @author Brian Norman
 * @version 1.2
 */
public interface IRobotSnapshot extends IVector, Serializable {

   /**
    * Returns the name of the robot.
    *
    * @return the name of the robot.
    */
   public String getName();

   /**
    * Returns the current energy of the robot. If the energy is less than zero
    * the robot is considered dead.
    *
    * @return the energy of the robot.
    */
   public double getEnergy();

   /**
    * Returns the time of the round at which the robot snapshot was taken.
    *
    * @return the time of the snapshot.
    */
   public long getTime();

   /**
    * Returns the round of the match at which the robot snapshot was taken.
    *
    * @return the round of the snapshot.
    */
   public int getRound();

}
