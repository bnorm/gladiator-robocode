package bnorm.events;

public interface RobotFoundSender {

   void addRobotFoundListener(RobotFoundListener listener);

   void removeRobotFoundListener(RobotFoundListener listener);

}
