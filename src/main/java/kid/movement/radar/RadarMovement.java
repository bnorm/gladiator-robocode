package kid.movement.radar;

import java.awt.geom.Point2D;

import kid.info.RadarInfo;
import kid.robot.RobotData;
import kid.utils.Utils;
import robocode.AdvancedRobot;
import robocode.Robot;

// TODO document class (20% complete)

public class RadarMovement {

   private Robot robot;
   private AdvancedRobot advancedRobot;
   public RadarInfo info;

   public RadarMovement(Robot myRobot) {
      init(myRobot);
   }

   private void init(Robot myRobot) {
      this.robot = myRobot;
      if (myRobot instanceof AdvancedRobot)
         this.advancedRobot = (AdvancedRobot) myRobot;
      this.info = new RadarInfo(myRobot);
   }

   public void move(RobotData[] robots) {
   }

   /**
    * Makes the robot's radar turn right <code>a</code> degrees before returning.
    * 
    * @param a The angle to turn.
    */
   public void right(double a) {
      robot.turnRadarRight(a);
   }

   /**
    * Sets the robot's radar turn right <code>a</code> degrees.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param a The angle to turn.
    */
   public void setRight(double a) {
      if (advancedRobot != null)
         advancedRobot.setTurnRadarRight(a);
   }

   /**
    * Makes the robot's radar turn left <code>a</code> degrees before returning.
    * 
    * @param a The angle to turn.
    */
   public void left(double a) {
      robot.turnRadarLeft(a);
   }

   /**
    * Sets the robot's radar turn left <code>a</code> degrees.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param a The angle to turn.
    */
   public void setLeft(double a) {
      if (advancedRobot != null)
         advancedRobot.setTurnRadarLeft(a);
   }

   public final void turnTo(double angle) {
      double theta = info.bearing(angle);
      right(theta);
   }

   public final void setTurnTo(double angle) {
      double theta = info.bearing(angle);
      setRight(theta);
   }

   public final void sweep(double angle, double sweep) {
      double theta = info.bearing(angle);
      theta += Utils.sign(theta) * Math.abs(sweep);
      right(theta);
   }

   public final void setSweep(double angle, double sweep) {
      double theta = info.bearing(angle);
      theta += Utils.sign(theta) * Math.abs(sweep);
      setRight(theta);
   }

   public final void turnTo(double x, double y) {
      double theta = info.bearing(x, y);
      right(theta);
   }

   public final void setTurnTo(double x, double y) {
      double theta = info.bearing(x, y);
      setRight(theta);
   }

   public final void sweep(double x, double y, double sweep) {
      double theta = info.bearing(x, y);
      theta += Utils.sign(theta) * Math.abs(sweep);
      right(theta);
   }

   public final void setSweep(double x, double y, double sweep) {
      double theta = info.bearing(x, y);
      theta += Utils.sign(theta) * Math.abs(sweep);
      setRight(theta);
   }

   public final void turnTo(Point2D point) {
      double theta = info.bearing(point);
      right(theta);
   }

   public final void setTurnTo(Point2D point) {
      double theta = info.bearing(point);
      setRight(theta);
   }

   public final void sweep(Point2D point, double sweep) {
      double theta = info.bearing(point);
      theta += Utils.sign(theta) * Math.abs(sweep);
      right(theta);
   }

   public final void setSweep(Point2D point, double sweep) {
      double theta = info.bearing(point);
      theta += Utils.sign(theta) * Math.abs(sweep);
      setRight(theta);
   }

   public final void turnTo(RobotData robot) {
      double theta = info.bearing(robot);
      right(theta);
   }

   public final void setTurnTo(RobotData robot) {
      double theta = info.bearing(robot);
      setRight(theta);
   }

   public final void sweep(RobotData robot, double sweep) {
      double theta = info.bearing(robot);
      theta += Utils.sign(theta) * Math.abs(sweep);
      right(theta);
   }

   /**
    * Sets the radar to sweep past the targeted <code>{@link robocode.Robot Robot}</code> how ever far. If the targeted
    * <code>{@link robocode.Robot Robot}</code> is null or dead, the radar will be set to turn right for infinity.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param robot - the targeted <code>{@link robocode.Robot Robot}</code> to sweep.
    * @param sweep - the angle past the <code>{@link robocode.Robot Robot}</code> the radar will sweep.
    */
   public final void setSweep(RobotData robot, double sweep) {
      double theta = info.bearing(robot);
      theta += Utils.sign(theta) * Math.abs(sweep);
      setRight(theta);
   }

}