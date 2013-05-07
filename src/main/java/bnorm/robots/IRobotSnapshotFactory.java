package bnorm.robots;

import robocode.Robot;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

/**
 * A factory class for creating new robot snapshots.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
public interface IRobotSnapshotFactory {

   /**
    * Returns a new blank snapshot. This snapshot contains no information and is
    * easily distinguishable from real snapshots.
    * 
    * @return a blank snapshot.
    */
   public IRobotSnapshot create();

   /**
    * Returns a new snapshot based on the specified ScannedRobotEvent. The
    * specified Robot should be the same Robot that was passed the specified
    * ScannedRobotEvent.
    * 
    * @param event
    *           a ScannedRobotEvent to base the snapshot.
    * @param robot
    *           the Robot that received the ScannedRobotEvent.
    * @return a new snapshot based on the ScannedRobotEvent.
    */
   public IRobotSnapshot create(ScannedRobotEvent event, Robot robot);

   /**
    * Returns a new snapshot based on the specified RobotDeathEvent. The
    * specified IRobotSnapshot should be the most recent snapshot of the robot
    * that the specified RobotDeathEvent references.
    * 
    * @param event
    *           a RobotDeathEvent to base the snapshot.
    * @param recent
    *           the most recent snapshot of the robot that died.
    * @return a new snapshot based on the RobotDeathEvent.
    */
   public IRobotSnapshot create(RobotDeathEvent event, IRobotSnapshot recent);

   /**
    * Returns a new snapshot based on the specified Robot.
    * 
    * @param robot
    *           a Robot to base the snapshot.
    * @return a new snapshot based on the Robot.
    */
   public IRobotSnapshot create(Robot robot);

   /**
    * Returns a new snapshot based on the specified IRobotSnapshot. This new
    * snapshot is a copy of the specified IRobotSnapshot.
    * 
    * @param snapshot
    *           a IRobotSnapshot to base the snapshot.
    * @return a copy snapshot of the IRobotSnapshot.
    */
   public IRobotSnapshot create(IRobotSnapshot snapshot);

}
