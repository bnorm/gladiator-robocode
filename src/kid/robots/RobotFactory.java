package kid.robots;

import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

/**
 * A factory class for creating new robots.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
public class RobotFactory implements IRobotFactory {

   /**
    * The robot snapshot factory used to create snapshots.
    */
   private IRobotSnapshotFactory snapshots_;

   /**
    * Creates a new robot factory using the specified robot snapshot factory to
    * create snapshots.
    * 
    * @param factory
    *           robot snapshot factory.
    */
   public RobotFactory(IRobotSnapshotFactory factory) {
      snapshots_ = factory;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public IRobot createEnemy() {
      return new Enemy();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public IRobot createTeammate() {
      return new Teammate();
   }

   /**
    * {@inheritDoc}
    * 
    * @throws NullPointerException
    *            if <code>event</code> is null.
    * @throws NullPointerException
    *            if <code>robot</code> is null.
    */
   @Override
   public IRobot create(ScannedRobotEvent event, Robot robot) {
      if (event == null) {
         throw new NullPointerException("ScannedRobotEvent must not be null.");
      } else if (robot == null) {
         throw new NullPointerException("Robot must not be null.");
      }

      IRobot r = null;
      if (robot instanceof TeamRobot && ((TeamRobot) robot).isTeammate(event.getName())) {
         r = createTeammate();
      } else {
         r = createEnemy();
      }
      r.add(snapshots_.create(event, robot));

      return r;
   }

   /**
    * {@inheritDoc}
    * 
    * @throws NullPointerException
    *            if <code>snapshot</code> is null.
    * @throws NullPointerException
    *            if <code>robot</code> is null.
    */
   @Override
   public IRobot create(IRobotSnapshot snapshot, Robot robot) {
      if (snapshot == null) {
         throw new NullPointerException("IRobotSnapshot must not be null.");
      } else if (robot == null) {
         throw new NullPointerException("Robot must not be null.");
      }

      IRobot r = null;
      if (robot instanceof TeamRobot && ((TeamRobot) robot).isTeammate(snapshot.getName())) {
         r = createTeammate();
      } else {
         r = createEnemy();
      }
      r.add(snapshot);

      return r;
   }

   /**
    * {@inheritDoc}
    * 
    * @throws NullPointerException
    *            if <code>robot</code> is null.
    */
   @Override
   public IRobot create(Robot robot) {
      if (robot == null) {
         throw new NullPointerException("Robot must not be null.");
      }

      IRobot r = new Teammate();
      r.add(snapshots_.create(robot));
      return r;
   }

   /**
    * {@inheritDoc}
    * 
    * @throws NullPointerException
    *            if <code>robot</code> is null.
    * @throws IllegalArgumentException
    *            if the <code>robot</code> is not of type <code>Enemy</code> or
    *            <code>Teammate</code>.
    */
   @Override
   public IRobot create(IRobot robot) {
      if (robot == null) {
         throw new NullPointerException("IRobot must not be null.");
      }

      IRobot r;
      if (robot instanceof Enemy) {
         r = new Enemy(robot);
      } else if (robot instanceof Teammate) {
         r = new Teammate(robot);
      } else {
         throw new IllegalArgumentException("IRobot is not a recognized IRobot type (" + robot.getClass().getName()
               + ").");
      }

      return r;
   }

}
