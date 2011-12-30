package kid.robots;

import java.util.ListIterator;
import java.util.Set;

/**
 * Represents a Robocode robot. The basic representation of a robot is a map of
 * series. Each series is of robot snapshots representing the robot for a
 * particular match round.
 * <p>
 * When a snapshot is added to the robot it is placed in the proper series which
 * is sorted by round time. This provides a so called "movie" that tracks the
 * robots changes through each round.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
public interface IRobot<S extends IRobotSnapshot> {

   /**
    * Adds snapshot too the robot. Returns false if the robot already contains a
    * snapshot for that match round and round time. Returns true if the snapshot
    * was added to the snapshot series.
    * 
    * @param snapshot
    *           the robot snapshot to add.
    * @return if the robot snapshot was added properly.
    */
   public boolean add(S snapshot);

   /**
    * Returns the most recent snapshot of the robot. That is, the last element
    * of the series that was most recently added to. This series is assumed to
    * be the current series for the match round.
    * 
    * @return the most recent snapshot of the robot.
    */
   public S getSnapshot();

   /**
    * Returns the snapshot for the specified time in the most recent series. If
    * the time does not exist in the series then the most recent snapshot up to
    * that time is returned.
    * 
    * @param time
    *           time of the snapshot to return.
    * @return the snapshot for the specified time.
    */
   public S getSnapshot(long time);

   /**
    * Returns the snapshot for the specified time and match round. If the round
    * does not exist an error will be thrown. If the time does not exist in the
    * series then the most recent snapshot up to that time is returned.
    * 
    * @param time
    *           time of the snapshot to return.
    * @param round
    *           the match round series to search in.
    * @return the snapshot for the specified time and match round.
    */
   public S getSnapshot(long time, int round);

   /**
    * Returns an iterator of the robot for the most recent series. This iterator
    * is positioned at the very beginning of the series.
    * 
    * @return the match round movie of the robot.
    */
   public ListIterator<S> getMovie();

   /**
    * Returns an iterator of the robot starting at the specified time. The round
    * is assumed to be the most recent. If the exact time does not exist in the
    * series, the iterator is started where the value would exist if it did.
    * 
    * @param time
    *           the time to start the iterator at.
    * @return the match round movie of the robot starting at the specified time.
    */
   public ListIterator<S> getMovie(long time);

   /**
    * Returns an iterator of the robot for the specified match round and at the
    * specified time. If the round does not exist an error will be thrown. If
    * the exact time does not exist in the series, the iterator is started where
    * the value would exist if it did.
    * 
    * @param time
    *           the time to start the iterator at.
    * @param round
    *           the match round of the iterator.
    * @return the movie of the robot for the specified match round and time.
    */
   public ListIterator<S> getMovie(long time, int round);

   /**
    * Returns a set of all the match round values that are mapped to a series.
    * 
    * @return a set of match round values.
    */
   public Set<Integer> getRounds();

}
