package kid.robots;

import kid.utils.Utils;
import robocode.Robot;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

/**
 * A factory class for creating new robot snapshots.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
public class RobotSnapshotFactory implements IRobotSnapshotFactory {

   /**
    * Default constructor.
    */
   public RobotSnapshotFactory() {
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public IRobotSnapshot createSnapshot() {
      return new RobotSnapshot();
   }

   /**
    * {@inheritDoc}
    * 
    * @throws NullPointerException
    *            if <code>event</code> is null.
    * @throws NullPointerException
    *            if <code>robot</code> is null.
    * @throws IllegalArgumentException
    *            if the time for <code>event</code> and <code>robot</code> are
    *            not equal.
    */
   @Override
   public IRobotSnapshot createSnapshot(ScannedRobotEvent event, Robot robot) {
      if (event == null) {
         throw new NullPointerException("ScannedRobotEvent must not be null.");
      } else if (robot == null) {
         throw new NullPointerException("Robot must not be null.");
      } else if (event.getTime() != robot.getTime()) {
         throw new IllegalArgumentException("ScannedRobotEvent time must equal Robot time.");
      }

      double x = Utils.projectX(robot.getX(), Math.toRadians(robot.getHeading()) + event.getBearingRadians(), event.getDistance());
      double y = Utils.projectY(robot.getY(), Math.toRadians(robot.getHeading()) + event.getBearingRadians(), event.getDistance());
      return new RobotSnapshot(event.getName(), x, y, event.getEnergy(), event.getHeadingRadians(), event.getVelocity(), event.getTime());
   }

   /**
    * {@inheritDoc}
    * 
    * @throws NullPointerException
    *            if <code>event</code> is null.
    * @throws NullPointerException
    *            if <code>last</code> is null.
    * @throws IllegalArgumentException
    *            if the name for <code>event</code> and <code>last</code> are
    *            not equal.
    * @throws IllegalArgumentException
    *            if the time for <code>event</code> is less than the time of
    *            <code>last</code>.
    */
   @Override
   public IRobotSnapshot createSnapshot(RobotDeathEvent event, IRobotSnapshot last) {
      if (event == null) {
         throw new NullPointerException("RobotDeathEvent must not be null.");
      } else if (last == null) {
         throw new NullPointerException("IRobotSnapshot must not be null.");
      } else if (!event.getName().equals(last.getName())) {
         throw new IllegalArgumentException("RobotDeathEvent name must equal IRobotSnapshot name.");
      } else if (event.getTime() < last.getTime()) {
         throw new IllegalArgumentException("RobotDeathEvent time must be greater than or equal IRobotSnapshot time.");
      }

      return new RobotSnapshot(last.getName(), last.getX(), last.getY(), -1.0, last.getHeading(), last.getVelocity(), event.getTime());
   }

   /**
    * {@inheritDoc}
    * 
    * @throws NullPointerException
    *            if <code>robot</code> is null.
    */
   @Override
   public IRobotSnapshot createSnapshot(Robot robot) {
      if (robot == null) {
         throw new NullPointerException("Robot must not be null.");
      }

      return new RobotSnapshot(robot.getName(), robot.getX(), robot.getY(), robot.getEnergy(), Math.toRadians(robot.getHeading()),
            robot.getVelocity(), robot.getTime());
   }

   /**
    * {@inheritDoc}
    * 
    * @throws NullPointerException
    *            if <code>snapshot</code> is null.
    */
   @Override
   public IRobotSnapshot createSnapshot(IRobotSnapshot snapshot) {
      if (snapshot == null) {
         throw new NullPointerException("IRobotSnapshot must not be null.");
      }

      return new RobotSnapshot(snapshot);
   }

}
