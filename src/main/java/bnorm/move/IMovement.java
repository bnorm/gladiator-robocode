package main.java.bnorm.move;

import bnorm.robots.IRobot;

/**
 * Interface for movement algorithms.
 *
 * @author Brian Norman
 * @version 1.0
 */
public interface IMovement {

   /**
    * Tells the movement algorithm that it is now time to move the robot.
    *
    * @param robots the other robots on the battlefield.
    * @param target the robot that we are targeting for movement.
    */
   void move(Iterable<IRobot> robots, IRobot target);

}
