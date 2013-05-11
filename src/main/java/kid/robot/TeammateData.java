package kid.robot;

import kid.communication.RobotMessage;
import kid.communication.ScannedRobotMessage;
import robocode.Robot;
import robocode.ScannedRobotEvent;

// TODO documentation: class
public class TeammateData extends RobotData {

   private static final long serialVersionUID = -4891701872652077531L;

   public TeammateData() {
      super();
   }

   public TeammateData(TeammateData teammate) {
      super(teammate);
   }

   public TeammateData(ScannedRobotEvent sre, Robot myRobot) {
      super(sre, myRobot);
   }

   public TeammateData(ScannedRobotMessage srm, Robot myRobot) {
      super(srm, myRobot);
   }

   public TeammateData(RobotMessage rm, Robot myRobot) {
      super(rm.getName(), rm.getX(), rm.getY(), rm.getEnergy(), rm.getHeading(), rm.getVelocity(), rm.getTime());
   }

   public TeammateData(Robot robot) {
      super(robot);
   }

   @Override
   public double getStrength() {
      return super.getStrength() / 2.0D;
   }

   @Override
   public TeammateData copy() {
      return new TeammateData(this);
   }

}
