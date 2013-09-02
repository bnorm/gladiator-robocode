package bnorm.events;

public interface RobotFiredSender {

   void addRobotFiredListener(RobotFiredListener listener);

   void removeRobotFiredListener(RobotFiredListener listener);

}
