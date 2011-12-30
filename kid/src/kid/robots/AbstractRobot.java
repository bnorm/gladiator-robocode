package kid.robots;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * An abstract representation of a Robocode robot. Provides base functionality
 * to build upon.
 * <p>
 * See {@link IRobot} for details of the requirements of a robot.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
class AbstractRobot<S extends IRobotSnapshot> implements IRobot<S> {

   /**
    * The map of time series keyed by the match round.
    */
   private Map<Integer, List<S>> rounds_;

   /**
    * The round time series that was most recently added to.
    */
   private List<S>               movie_;

   /**
    * The most recent snapshot of the current match round. I.e., the last
    * snapshot of the current match round series.
    */
   private S                     recent_;

   /**
    * Creates a new robot.
    */
   public AbstractRobot() {
      rounds_ = new Hashtable<>();
      movie_ = new LinkedList<>();
      recent_ = null;
   }

   /**
    * {@inheritDoc}
    * 
    * Returns null if there have been no snapshots added to the robot.
    * 
    * @throws NullPointerException
    *            if <code>snapshot</code> is null.
    */
   @Override
   public boolean add(S snapshot) {
      if (snapshot == null) {
         throw new NullPointerException("IRobotSnapshot must not be null.");
      }

      movie_ = rounds_.get(snapshot.getRound());
      if (movie_ == null) {
         rounds_.put(snapshot.getRound(), movie_ = new LinkedList<>());
      }

      int index = getIndex(movie_, snapshot.getTime());
      if (movie_.get(index).getTime() == snapshot.getTime()) {
         return false;
      }

      movie_.add(index + 1, snapshot);
      recent_ = movie_.get(movie_.size() - 1);
      return true;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public S getSnapshot() {
      return recent_;
   }

   /**
    * {@inheritDoc}
    * 
    * @throws IllegalArgumentException
    *            if <code>time</code> is less than zero.
    */
   @Override
   public S getSnapshot(long time) {
      if (time < 0) {
         throw new IllegalArgumentException("Time must not be less than zero (" + time + ").");
      }

      return getSnapshot(movie_, time);
   }

   /**
    * {@inheritDoc}
    * 
    * @throws IllegalArgumentException
    *            if <code>time</code> is less than zero.
    * @throws IllegalArgumentException
    *            if <code>round</code> is less than zero.
    */
   @Override
   public S getSnapshot(long time, int round) {
      if (time < 0) {
         throw new IllegalArgumentException("Time must not be less than zero (" + time + ").");
      } else if (round < 0) {
         throw new IllegalArgumentException("Round must not be less than zero (" + round + ").");
      }

      return getSnapshot(rounds_.get(round), time);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListIterator<S> getMovie() {
      return getMovie(movie_, 0);
   }

   /**
    * {@inheritDoc}
    * 
    * @throws IllegalArgumentException
    *            if <code>time</code> is less than zero.
    */
   @Override
   public ListIterator<S> getMovie(long time) {
      if (time < 0) {
         throw new IllegalArgumentException("Time must not be less than zero (" + time + ").");
      }

      return getMovie(movie_, time);
   }

   /**
    * {@inheritDoc}
    * 
    * @throws IllegalArgumentException
    *            if <code>time</code> is less than zero.
    * @throws IllegalArgumentException
    *            if <code>round</code> is less than zero.
    */
   @Override
   public ListIterator<S> getMovie(long time, int round) {
      if (time < 0) {
         throw new IllegalArgumentException("Time must not be less than zero (" + time + ").");
      } else if (round < 0) {
         throw new IllegalArgumentException("Round must not be less than zero (" + round + ").");
      }

      return getMovie(rounds_.get(round), time);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Set<Integer> getRounds() {
      return rounds_.keySet();
   }

   /**
    * Returns the index of the snapshot that matches the specified time in the
    * specified series. The series is assumed to be sorted. If the exact time
    * does not appear in the series, then the index of the greatest time that is
    * still less than the specified time is return.
    * <p>
    * Properties:<br>
    * 1) If <code>getIndex(movie,time=t)</code> returns a snapshot for which the
    * time is equal to <code>t</code>, then the snapshot at index
    * <code>getIndex(movie,time=t-1)+1</code> will be the same snapshot.<br>
    * 2) If <code>getIndex(movie,time=t)</code> returns a snapshot for which the
    * time is less than <code>t</code>, then the snapshot at index
    * <code>getIndex(movie,time=t-1)+1</code> will be the snapshot with the
    * smallest time that is still greater than the time <code>t</code>.
    * 
    * @param movie
    *           the series to search.
    * @param time
    *           the time to search for.
    * @return the index of the time, rounding down.
    * 
    * @throws IllegalArgumentException
    *            if <code>movie</code> is null.
    * @throws IllegalArgumentException
    *            if <code>time</code> is less than zero.
    */
   protected static <T extends IRobotSnapshot> int getIndex(List<T> movie, long time) {
      if (movie == null) {
         throw new NullPointerException("List must not be null.");
      } else if (time < 0) {
         throw new IllegalArgumentException("Time must not be less than zero (" + time + ").");
      } else if (movie.size() == 0) {
         return -1;
      }

      int index = movie.size() - 1;

      long headDiff = Math.abs(time - movie.get(0).getTime());
      long tailDiff = Math.abs(time - movie.get(movie.size() - 1).getTime());

      if (headDiff < tailDiff) {
         index = -1;
         ListIterator<? extends IRobotSnapshot> iter = movie.listIterator();
         while (iter.hasNext() && iter.next().getTime() <= time) {
            index++;
         }
      } else {
         ListIterator<? extends IRobotSnapshot> iter = movie.listIterator(movie.size());
         while (iter.hasPrevious() && iter.previous().getTime() > time) {
            index--;
         }
      }

      return index;
   }

   /**
    * Returns an iterator over the specified list starting at the specified
    * time. If the specified list is null then an error is thrown. If the exact
    * time does not appear in the series, then the index of the greatest time
    * that is still less than the specified time is used.
    * 
    * @param movie
    *           the list series to be played as a movie.
    * @param time
    *           the time when the movie starts.
    * @return an iterator over the specified list starting at the specified
    *         time.
    * 
    * @throws IllegalArgumentException
    *            if <code>movie</code> is null.
    * @throws IllegalArgumentException
    *            if <code>time</code> is less than zero.
    */
   protected static <T extends IRobotSnapshot> ListIterator<T> getMovie(List<T> movie, long time) {
      if (movie == null) {
         throw new NullPointerException("List must not be null.");
      } else if (time < 0) {
         throw new IllegalArgumentException("Time must not be less than zero (" + time + ").");
      }

      if (time == 0) {
         return movie.listIterator();
      } else {
         // See comment of getIndex(List<>, long) why the following works.
         int index = getIndex(movie, time - 1);
         return movie.listIterator(Math.min(index + 1, movie.size()));
      }
   }

   /**
    * Returns the snapshot at the specified time from the specified list. If the
    * specified list is null then an error is thrown. If the exact time does not
    * appear in the series, then the snapshot of the greatest time that is still
    * less than the specified time is returned.
    * 
    * @param movie
    *           the series to search.
    * @param time
    *           the time to search for.
    * @return the snapshot at the specified time from the specified list.
    * 
    * @throws IllegalArgumentException
    *            if <code>movie</code> is null.
    * @throws IllegalArgumentException
    *            if <code>time</code> is less than zero.
    */
   protected static <T extends IRobotSnapshot> T getSnapshot(List<T> movie, long time) {
      if (movie == null) {
         throw new NullPointerException("List must not be null.");
      } else if (time < 0) {
         throw new IllegalArgumentException("Time must not be less than zero (" + time + ").");
      }

      int index = getIndex(movie, time);
      return movie.get(Math.max(0, index));
   }

}
