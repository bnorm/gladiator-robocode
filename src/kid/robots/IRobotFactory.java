package kid.robots;

import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 * A factory class for creating new robots.
 * 
 * @author Brian Norman (KID)
 * @version 1.0
 */
public interface IRobotFactory {

   /**
    * Returns a new blank enemy robot. This robot contains no information and is
    * easily distinguishable from real robot.
    * 
    * @return a blank enemy robot.
    */
   public IRobot createEnemy();

   /**
    * Returns a new blank teammate robot. This robot contains no information and
    * is easily distinguishable from real robot.
    * 
    * @return a blank teammate robot.
    */
   public IRobot createTeammate();

   /**
    * Returns a new robot based on the specified ScannedRobotEvent. The
    * specified Robot should be the same Robot that was passed the specified
    * ScannedRobotEvent.
    * 
    * @param event
    *           a ScannedRobotEvent to base the robot.
    * @param robot
    *           the Robot that received the ScannedRobotEvent.
    * @return a new robot based on the ScannedRobotEvent.
    */
   public IRobot create(ScannedRobotEvent event, Robot robot);

   /**
    * Returns a new robot based on the specified IRobotSnapshot.
    * 
    * @param snapshot
    *           a IRobotSnapshot to base the robot.
    * @param robot
    *           the Robot for who the snapshot was created.
    * @return a new robot based on the IRobotSnapshot.
    */
   public IRobot create(IRobotSnapshot snapshot, Robot robot);

   /**
    * Returns a new robot based on the specified Robot.
    * 
    * @param robot
    *           the Robot to base the robot.
    * @return a new robot based on the Robot.
    */
   public IRobot create(Robot robot);

   /**
    * Returns a new robot based on the specified IRobot.
    * 
    * @param robot
    *           the IRobot to base the robot.
    * @return a new robot based on the IRobot.
    */
   public IRobot create(IRobot robot);

}
