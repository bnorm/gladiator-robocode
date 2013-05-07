package bnorm.events;

public interface RobotFiredEventSender {

   void addListener(RobotFiredEventListener listener);

   void removeListener(RobotFiredEventListener listener);

}
