package bnorm.events;

public interface RobotFoundSender {

   void addListener(RobotFoundListener listener);

   void removeListener(RobotFoundListener listener);

}
