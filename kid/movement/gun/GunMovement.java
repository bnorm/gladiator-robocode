package kid.movement.gun;

import java.awt.geom.Point2D;

import robocode.*;

import kid.data.info.*;
import kid.data.robot.RobotData;
import kid.targeting.Targeting;

//TODO document class (20% complete)

public class GunMovement {

   private Robot robot;
   private AdvancedRobot advancedRobot;

   private GunInfo info;


   public GunMovement(Robot myRobot) {
      init(myRobot);
   }


   private void init(Robot myRobot) {
      this.robot = myRobot;
      if (robot instanceof AdvancedRobot)
         this.advancedRobot = (AdvancedRobot) this.robot;
      this.info = new GunInfo(myRobot);
   }


   /**
    * Makes the robot's gun turn right <code>a</code> degrees before returning.
    * 
    * @param a The angle to turn.
    */
   public void right(double a) {
      robot.turnGunRight(a);
   }

   /**
    * Sets the robot's gun turn right <code>a</code> degrees.<BR>
    * <BR>
    * Will return before turning even commences, turning will happen when
    * <code>{@see robocode.AdvancedRobot#execute()}</code> is called.
    * 
    * @param a The angle to turn.
    */
   public void setRight(double a) {
      if (advancedRobot != null)
         advancedRobot.setTurnGunRight(a);
   }

   /**
    * Makes the robot's gun turn left <code>a</code> degrees before returning.
    * 
    * @param a The angle to turn.
    */
   public void left(double a) {
      robot.turnGunLeft(a);
   }

   /**
    * Sets the robot's gun turn left <code>a</code> degrees.<BR>
    * <BR>
    * Will return before turning even commences, turning will happen when
    * <code>{@see robocode.AdvancedRobot#execute()}</code> is called.
    * 
    * @param a The angle to turn.
    */
   public void setLeft(double a) {
      if (advancedRobot != null)
         advancedRobot.setTurnGunLeft(a);
   }


   public final void turnTo(double angle) {
      double theta = info.bearing(angle);
      right(theta);
   }

   public final void setTurnTo(double angle) {
      double theta = info.bearing(angle);
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


   public final void turnTo(Point2D point) {
      double theta = info.bearing(point);
      right(theta);
   }

   public final void setTurnTo(Point2D point) {
      double theta = info.bearing(point);
      setRight(theta);
   }


   public final void turnTo(RobotData robot) {
      if (robot != null && !robot.isDead()) {
         double theta = info.bearing(robot);
         right(theta);
      }
   }

   public final void setTurnTo(RobotData robot) {
      if (robot != null && !robot.isDead()) {
         double theta = info.bearing(robot);
         setRight(theta);
      }
   }

   // public final void turnTo(VirtualGun vGun, double firePower) {
   // if (vGun != null && vGun.getTarget() != null && !vGun.getTarget().isDead())
   // turnTo(vGun.getTargeting().getTargetingAngle(vGun.getTarget(), firePower));
   // }
   //
   // public final void setTurnTo(VirtualGun vGun, double firePower) {
   // if (vGun != null && vGun.getTarget() != null && !vGun.getTarget().isDead())
   // setTurnTo(vGun.getTargeting().getTargetingAngle(vGun.getTarget(), firePower));
   // }

   public final void turnTo(Targeting targeting, RobotData robot, double firePower) {
      if (robot != null && !robot.isDead() && targeting != null)
         turnTo(targeting.getAngle(robot, firePower));
   }

   public final void setTurnTo(Targeting targeting, RobotData robot, double firePower) {
      if (robot != null && !robot.isDead() && targeting != null)
         setTurnTo(targeting.getAngle(robot, firePower));
   }

}
