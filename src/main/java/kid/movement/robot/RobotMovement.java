package kid.movement.robot;

import java.awt.geom.Point2D;

import kid.info.RobotInfo;
import kid.robot.RobotData;
import kid.robot.RobotVector;
import kid.utils.Utils;
import robocode.AdvancedRobot;
import robocode.Robot;

// BORED documentation: class (90% complete)
/**
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class RobotMovement {

   private Robot robot;
   private AdvancedRobot advancedRobot;

   private RobotInfo info;

   /**
    * The <code>integer</code> representation of forwards.
    */
   public static final int FORWARD = 1;

   /**
    * The <code>integer</code> representation of backwards. This is also just the negative of
    * <code>{@link #FORWARD FORWARDS}</code>.
    * 
    * @see #FORWARD
    */
   public static final int BACKWARD = -FORWARD;

   private static final double MAX_DIST_RATIO = 1.75D;
   private static final double MIN_DIST_RATIO = 0.25D;


   public RobotMovement(Robot myRobot) {
      this.robot = myRobot;
      if (robot instanceof AdvancedRobot)
         this.advancedRobot = (AdvancedRobot) robot;
      this.info = new RobotInfo(robot);
   }

   /**
    * Makes the robot turn right <code>a</code> degrees before returning.
    * 
    * @param a
    *           - the angle to turn.
    */
   public void right(double a) {
      this.robot.turnRight(a);
   }

   /**
    * Sets the robot turn right <code>a</code> degrees.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param a
    *           - the angle to turn.
    */
   public void setRight(double a) {
      if (advancedRobot != null)
         advancedRobot.setTurnRight(a);
   }

   /**
    * Makes the robot move forwards <code>d</code> pixels before returning.
    * 
    * @param d
    *           - the distance to move.
    */
   public void ahead(double d) {
      this.robot.ahead(d);
   }

   /**
    * Sets the robot to move forwards <code>d</code> pixels.<br>
    * <br>
    * Will return before moving even commences, moving will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param d
    *           - the distance to move.
    */
   public void setAhead(double d) {
      if (advancedRobot != null)
         advancedRobot.setAhead(d);
   }

   /**
    * Makes the robot turn left <code>a</code> degrees before returning.
    * 
    * @param a
    *           - the angle to turn.
    */
   public void left(double a) {
      this.robot.turnLeft(a);
   }

   /**
    * Sets the robot turn left <code>a</code> degrees.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute() }</code> is called.
    * 
    * @param a
    *           - the angle to turn.
    */
   public void setLeft(double a) {
      if (advancedRobot != null)
         advancedRobot.setTurnLeft(a);
   }

   /**
    * Makes the robot move backwards <code>d</code> pixels before returning.
    * 
    * @param d
    *           - the distance to move.
    */
   public void back(double d) {
      this.robot.back(d);
   }

   /**
    * Sets the robot to move forwards <code>d</code> pixels.<br>
    * <br>
    * Will return before moving even commences, moving will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param d
    *           - the distance to move.
    */
   public void setBack(double d) {
      if (advancedRobot != null)
         advancedRobot.setBack(d);
   }

   /**
    * Makes the robot turn to a angle before returning.
    * 
    * @param angle
    *           - the angle.
    */
   public final void turnToAngle(double angle) {
      double theta = info.bearing(angle);
      this.right(theta);
   }

   /**
    * Sets the robot to turn to an angle.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param angle
    *           - the angle.
    */
   public final void setTurnToAngle(double angle) {
      double theta = info.bearing(angle);
      this.setRight(theta);
   }

   /**
    * Makes the robot turn perpendicular to an angle before returning.
    * 
    * @param angle
    *           - the angle.
    */
   public final void turnPerpenToAngle(double angle) {
      double theta = info.bearing(angle + Utils.QUARTER_CIRCLE);
      this.right(theta);
   }

   /**
    * Sets the robot to turn perpendicular to an angle.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param angle
    *           - the angle.
    */
   public final void setTurnPerpenToAngle(double angle) {
      double theta = info.bearing(angle + Utils.QUARTER_CIRCLE);
      this.setRight(theta);
   }

   // BORED complete comment with proper method usage
   /**
    * Makes the robot turn perpendicular to an angle with distance control before returning.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep a certain point at
    * a set distance. This point is loosely defined as the angle and the distance you are from it.
    * 
    * @param angle
    *           - the angle.
    * @param curDist
    *           - the current distance away from something.
    * @param desiredDist
    *           - the distance away from that thing that is desired.
    */
   public final void turnPerpenToAnglewDC(double angle, double curDist, double desiredDist) {
      double distRatio = distRatio(curDist, desiredDist);
      double theta = info.bearing(angle + (Utils.QUARTER_CIRCLE * distRatio));
      this.right(theta);
   }

   // BORED complete comment with proper method usage
   /**
    * Sets the robot to turn perpendicular to an angle with distance control.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep a certain point at
    * a set distance. This point is loosely defined as the angle and the distance you are from it.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.<br>
    * <br>
    * Some example code may help in understanding how to use this method:
    * 
    * <pre>
    * class TestRobot extends AdvancedRobot {
    * 
    *    RobotData enemy = new EnemyData();
    *    RobotMovement movement;
    *    RobotInfo info;
    * 
    *    void run() {
    * 
    *       movement = new RobotMovement(this);
    *       info = new RobotInfo(this);
    * 
    *       while (true) {
    *          movement.setAhead(Double.POSITIVE_INFINITY);
    *          movement.setTurnPerpenToAnglewDC(info.getAngle(enemy), info.getDist(enemy), 300.0D);
    *       }
    *    }
    * 
    *    void onScannedRobotEvent(ScannedRobotEvent sre) {
    *       if (enemy.isDummy())
    *          enemy = new EnemyData(sre, this);
    *       else
    *          enemy.update(sre, this);
    *    }
    * 
    * }
    * </pre>
    * 
    * @param angle
    *           - the angle to which it turns perpendicular.
    * @param curDist
    *           - the current distance away from something.
    * @param desiredDist
    *           - the distance away from that thing that is desired.
    */
   public final void setTurnPerpenToAnglewDC(double angle, double curDist, double desiredDist) {
      double distRatio = distRatio(curDist, desiredDist);
      double theta = info.bearing(angle + (Utils.QUARTER_CIRCLE * distRatio));
      this.setRight(theta);
   }

   /**
    * Makes the robot turn to an angle before returning.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the point,
    * <code>BACKWARD</code> otherwise. Some example code may help in understanding how to use the
    * return value of this method:
    * 
    * <pre>
    * class TestRobot extends AdvancedRobot {
    * 
    *    RobotData enemy = new EnemyData();
    *    RobotMovement movement;
    *    RobotInfo info;
    * 
    *    void run() {
    * 
    *       movement = new RobotMovement(this);
    *       info = new RobotInfo(this);
    * 
    *       while (true) {
    *          movement.setAhead(Double.POSITIVE_INFINITY
    *                * movement.setTurnPerpenToAnglewDC(info.getAngle(enemy), info.getDist(enemy), 300.0D));
    *       }
    *    }
    * 
    *    void onScannedRobotEvent(ScannedRobotEvent sre) {
    *       if (enemy.isDummy())
    *          enemy = new EnemyData(sre, this);
    *       else
    *          enemy.update(sre, this);
    *    }
    * 
    * }
    * </pre>
    * 
    * @param angle
    *           - the angle.
    * @return whether the front or back was turned to the angle.
    * @see #FORWARD
    * @see #BACKWARD
    */
   public final int turnToAnglewBF(double angle) {
      double theta = info.bearing(angle);
      if (Math.abs(theta) < Utils.QUARTER_CIRCLE) {
         this.right(theta);
         return FORWARD;
      } else {
         this.right(Utils.oppositeRelative(theta));
         return BACKWARD;
      }
   }

   /**
    * Sets the robot to turn to an angle.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the point,
    * <code>BACKWARD</code> otherwise.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param angle
    *           - the angle.
    * @return whether the front or back was turned to the angle.
    * @see #FORWARD
    * @see #BACKWARD
    */
   public final int setTurnToAnglewBF(double angle) {
      double theta = info.bearing(angle);
      if (Math.abs(theta) < Utils.QUARTER_CIRCLE) {
         this.setRight(theta);
         return FORWARD;
      } else {
         this.setRight(Utils.oppositeRelative(theta));
         return BACKWARD;
      }
   }

   /**
    * Makes the robot turn perpendicular to an angle before returning.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the point,
    * <code>BACKWARD</code> otherwise.
    * 
    * @param angle
    *           - the angle.
    * @return whether the front or back was turned to the angle.
    * @see #FORWARD
    * @see #BACKWARD
    */
   public final int turnPerpenToAnglewBF(double angle) {
      double theta = Utils.relative(angle + Utils.QUARTER_CIRCLE);
      return this.turnToAnglewBF(theta);
   }

   /**
    * Sets the robot to turn perpendicular to an angle.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the point,
    * <code>BACKWARD</code> otherwise.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param angle
    *           - the angle.
    * @return whether the front or back was turned to the angle.
    * @see #FORWARD
    * @see #BACKWARD
    */
   public final int setTurnPerpenToAnglewBF(double angle) {
      double theta = Utils.relative(angle + Utils.QUARTER_CIRCLE);
      return this.setTurnToAnglewBF(theta);
   }

   /**
    * Makes the robot turn perpendicular to an angle with distance control before returning.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep a certain point at
    * a set distance. This point is loosely defined as the angle and the distance you are from it.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the point,
    * <code>BACKWARD</code> otherwise. Best used by multiplying it by the moving distance and moving
    * forward that distance. This will move the robot into an arc around the robot that is exactly
    * <code>desiredDist</code> away.
    * 
    * @param angle
    *           - the angle.
    * @param curDist
    *           - the current distance away from something.
    * @param desiredDist
    *           - the distance away from that thing that is desired.
    * @return whether the front or back was turned perpendicular to the angle.
    * @see #FORWARD
    * @see #BACKWARD
    */
   public final int turnPerpenToAnglewBFwDC(double angle, double curDist, double desiredDist) {
      double distRatio = distRatio(curDist, desiredDist);
      double right = info.bearing(angle + (Utils.QUARTER_CIRCLE * distRatio));
      double left = info.bearing(angle - (Utils.QUARTER_CIRCLE * distRatio));
      double ahead = Utils.absMin(right, left);
      double back = Utils.absMin(Utils.oppositeRelative(right), Utils.oppositeRelative(left));
      if (Math.abs(ahead) < Math.abs(back)) {
         this.right(ahead);
         return FORWARD;
      } else {
         this.right(back);
         return BACKWARD;
      }
   }

   /**
    * Sets the robot to turn perpendicular to an angle with distance control.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep a certain point at
    * a set distance. This point is loosely defined as the angle and the distance you are from it.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the angle,
    * <code>BACKWARD</code> otherwise. Best used by multiplying it by the moving distance and moving
    * forward that distance. This will move the robot into an arc around the robot that is exactly
    * <code>desiredDist</code> away.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param angle
    *           - the angle.
    * @param curDist
    *           - the current distance away from something.
    * @param desiredDist
    *           - the distance away from that thing that is desired.
    * @return whether the front or back was turned perpendicular to the angle.
    * @see #FORWARD
    * @see #BACKWARD
    */
   public final int setTurnPerpenToAnglewBFwDC(double angle, double curDist, double desiredDist) {
      double distRatio = distRatio(curDist, desiredDist);
      double right = info.bearing(angle + (Utils.QUARTER_CIRCLE * distRatio));
      double left = info.bearing(angle - (Utils.QUARTER_CIRCLE * distRatio));
      double ahead = Utils.absMin(right, left);
      double back = Utils.absMin(Utils.oppositeRelative(right), Utils.oppositeRelative(left));
      if (Math.abs(ahead) < Math.abs(back)) {
         setRight(ahead);
         return FORWARD;
      } else {
         setRight(back);
         return BACKWARD;
      }
   }

   // BORED documentation: method - vectorTurnPerpenToAnglewBFeDC(RobotVector, double, double,
   // double)
   // FIXME code: method - vectorTurnPerpenToAnglewBFeDC(RobotVector, double, double, double)
   public static final void vectorTurnPerpenToAnglewBFeDC(RobotVector vector, double angle, double curDist,
         double desiredDist) {
      double distRatio = distRatio(curDist, desiredDist);
      double right = Utils.relative(angle + (Utils.QUARTER_CIRCLE * distRatio) - vector.getHeading());
      double left = Utils.relative(angle - (Utils.QUARTER_CIRCLE * distRatio) - vector.getHeading());
      double ahead = Utils.absMin(right, left);
      double back = Utils.absMin(Utils.oppositeRelative(right), Utils.oppositeRelative(left));
      if (Math.abs(ahead) < Math.abs(back)) {
         vector.rotate(ahead);
         vector.velocity(Math.abs(vector.getVelocity()));
      } else {
         vector.rotate(back);
         vector.velocity(-Math.abs(vector.getVelocity()));
      }
   }

   /**
    * Sets the robot to turn perpendicular to an angle with distance control and robot monitoring.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep a certain point at
    * a set distance. This point is loosely defined as the angle and the distance you are from it.<br>
    * <br>
    * Robot monitoring will allow the robot to move forward or backward and still keep
    * <code>disiredDist</code> away from the angle.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param angle
    *           - the angle.
    * @param curDist
    *           - the current distance away from something.
    * @param desiredDist
    *           - the distance away from that thing that is desired.
    * @see #FORWARD
    * @see #BACKWARD
    */
   public final void setTurnPerpenToAnglewBFwDCwRM(double angle, double curDist, double desiredDist) {
      double distRatio = distRatio(curDist, desiredDist);
      double right = info.bearing(angle + (Utils.QUARTER_CIRCLE * distRatio));
      double left = info.bearing(angle - (Utils.QUARTER_CIRCLE * distRatio));
      double ahead = Utils.absMin(right, left);
      double back = Utils.absMin(Utils.oppositeRelative(right), Utils.oppositeRelative(left));
      if (info.getMovingSign() == BACKWARD) {
         setRight(back);
      } else {
         setRight(ahead);
      }
   }

   /**
    * Makes the robot turn to a coordinate <code>(x, y)</code> before returning.
    * 
    * @param x
    *           - the <code>x</code> value of the coordinate.
    * @param y
    *           - the <code>y</code> value of the coordinate.
    */
   public final void turnToXY(double x, double y) {
      double theta = info.bearing(x, y);
      this.right(theta);
   }

   /**
    * Sets the robot to turn to a coordinate <code>(x, y)</code>.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param x
    *           - the <code>x</code> value of the coordinate.
    * @param y
    *           - the <code>y</code> value of the coordinate.
    */
   public final void setTurnToXY(double x, double y) {
      double theta = info.bearing(x, y);
      this.setRight(theta);
   }

   /**
    * Makes the robot turn perpendicular to a coordinate <code>(x, y)</code> before returning.
    * 
    * @param x
    *           - the <code>x</code> value of the coordinate.
    * @param y
    *           - the <code>y</code> value of the coordinate.
    */
   public final void turnPerpenToXY(double x, double y) {
      double theta = Utils.relative(info.bearing(x, y) + Utils.QUARTER_CIRCLE);
      this.right(theta);
   }

   /**
    * Sets the robot to turn perpendicular to a coordinate <code>(x, y)</code>.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param x
    *           - the <code>x</code> value of the coordinate.
    * @param y
    *           - the <code>y</code> value of the coordinate.
    */
   public final void setTurnPerpenToXY(double x, double y) {
      double theta = Utils.relative(info.bearing(x, y) + Utils.QUARTER_CIRCLE);
      setRight(theta);
   }

   /**
    * Makes the robot turn to a coordinate <code>(x, y)</code> before returning.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the coordinate,
    * <code>BACKWARD</code> otherwise.
    * 
    * @param x
    *           - the <code>x</code> value of the coordinate.
    * @param y
    *           - the <code>y</code> value of the coordinate.
    * @return whether the front or back was turned to the coordinate.
    * @see #FORWARD
    * @see #BACKWARD
    */
   public final int turnToXYwBF(double x, double y) {
      double theta = info.angle(x, y);
      return this.turnToAnglewBF(theta);
   }

   /**
    * Sets the robot to turn to a coordinate <code>(x, y)</code>.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the coordinate,
    * <code>BACKWARD</code> otherwise.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param x
    *           the <code>x</code> value of the coordinate.
    * @param y
    *           the <code>y</code> value of the coordinate.
    * @return whether the front or back was turned to the coordinate.
    * @see #FORWARD
    * @see #BACKWARD
    */
   public final int setTurnToXYwBF(double x, double y) {
      double theta = info.angle(x, y);
      return this.setTurnToAnglewBF(theta);
   }

   /**
    * Makes the robot turn perpendicular to a coordinate <code>(x, y)</code> before returning.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the coordinate,
    * <code>BACKWARD</code> otherwise.
    * 
    * @param x
    *           - the <code>x</code> value of the coordinate.
    * @param y
    *           - the <code>y</code> value of the coordinate.
    * @return whether the front or back was turned to the coordinate.
    * @see #FORWARD
    * @see #BACKWARD
    */
   public final int turnPerpenToXYwBF(double x, double y) {
      double theta = Utils.relative(info.angle(x, y) + Utils.QUARTER_CIRCLE);
      return this.turnToAnglewBF(theta);
   }

   /**
    * Sets the robot to turn perpendicular to a coordinate <code>(x, y)</code>.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the coordinate,
    * <code>BACKWARD</code> otherwise.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param x
    *           - the <code>x</code> value of the coordinate.
    * @param y
    *           - the <code>y</code> value of the coordinate.
    * @return whether the front or back was turned perpendicular to the coordinate.
    * @see #FORWARD
    * @see #BACKWARD
    */
   public final int setTurnPerpenToXYwBF(double x, double y) {
      double theta = Utils.relative(info.angle(x, y) + Utils.QUARTER_CIRCLE);
      return setTurnToAnglewBF(theta);
   }

   /**
    * Makes the robot turn perpendicular to a coordinate <code>(x, y)</code> with distance control
    * before returning.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep the coordinate at a
    * set distance.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the coordinate,
    * <code>BACKWARD</code> otherwise. Best used by multiplying it by the moving distance and moving
    * forward that distance. This will move the robot into an arc around the robot that is exactly
    * <code>desiredDist</code> away.
    * 
    * @param x
    *           - the <code>x</code> value of the coordinate.
    * @param y
    *           - the <code>y</code> value of the coordinate.
    * @param desiredDist
    *           - the distance away from the coordinate that is desired.
    * @return whether the front or back was turned perpendicular to the coordinate.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #turnPerpenToAnglewBFwDC(double, double, double)
    */
   public final int turnPerpenToXYwBFwDC(double x, double y, double desiredDist) {
      return this.setTurnPerpenToAnglewBFwDC(info.angle(x, y), info.dist(x, y), desiredDist);
   }

   /**
    * Sets the robot to turn perpendicular to a coordinate <code>(x, y)</code> with distance
    * control.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep the coordinate at a
    * set distance.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the coordinate,
    * <code>BACKWARD</code> otherwise. Best used by multiplying it by the moving distance and moving
    * forward that distance. This will move the robot into an arc around the robot that is exactly
    * <code>desiredDist</code> away.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param x
    *           - the <code>x</code> value of the coordinate.
    * @param y
    *           - the <code>y</code> value of the coordinate.
    * @param disiredDist
    *           - the distance away from the coordinate that is desired.
    * @return whether the front or back was turned perpendicular to the coordinate.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #setTurnPerpenToAnglewBFwDC(double, double, double)
    */
   public final int setTurnPerpenToXYwBFwDC(double x, double y, double disiredDist) {
      return this.setTurnPerpenToAnglewBFwDC(info.angle(x, y), info.dist(x, y), disiredDist);
   }

   /**
    * Sets the robot to turn perpendicular to a coordinate <code>(x, y)</code> with distance control
    * and robot monitoring.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep the coordinate at a
    * set distance.<br>
    * <br>
    * Robot monitoring will allow the robot to move forward or backward and still keep
    * <code>disiredDist</code> away from the coordinate.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param x
    *           - the <code>x</code> value of the coordinate.
    * @param y
    *           - the <code>y</code> value of the coordinate.
    * @param disiredDist
    *           - the distance away from the coordinate that is desired.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #setTurnPerpenToAnglewBFwDCwRM(double, double, double)
    */
   public final void setTurnPerpenToXYwBFwDCwRM(double x, double y, double disiredDist) {
      this.setTurnPerpenToAnglewBFwDCwRM(info.angle(x, y), info.dist(x, y), disiredDist);
   }

   /**
    * Makes the robot move to a coordinate <code>(x, y)</code> before returning.
    * 
    * @param x
    *           - the <code>x</code> value of the coordinate.
    * @param y
    *           - the <code>y</code> value of the coordinate.
    */
   public final void moveToXY(double x, double y) {
      this.ahead(info.dist(x, y) * turnToXYwBF(x, y));
   }

   /**
    * Sets the robot to move to a coordinate <code>(x, y)</code>.<br>
    * <br>
    * Will return before moving even commences, moving will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param x
    *           - the <code>x</code> value of the coordinate.
    * @param y
    *           - the <code>y</code> value of the coordinate.
    */
   public final void setMoveToXY(double x, double y) {
      this.setAhead(info.dist(x, y) * setTurnToXYwBF(x, y));
   }

   /**
    * Makes the robot turn to another robot before returning.
    * 
    * @param robot
    *           - the other robot.
    * @see #turnToAngle(double)
    */
   public final void turnToRobot(RobotData robot) {
      double theta = info.bearing(robot);
      this.right(theta);
   }

   /**
    * Sets the robot to turn to another robot.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param robot
    *           - the other robot.
    * @see #setTurnToAngle(double)
    */
   public final void setTurnToRobot(RobotData robot) {
      double theta = info.bearing(robot);
      this.setRight(theta);
   }

   /**
    * Makes the robot turn perpendicular to another robot before returning.
    * 
    * @param robot
    *           - the other robot.
    * @see #turnPerpenToAngle(double)
    */
   public final void turnPerpenToRobot(RobotData robot) {
      double theta = Utils.relative(info.bearing(robot) + Utils.QUARTER_CIRCLE);
      this.right(theta);
   }

   /**
    * Sets the robot to turn perpendicular to another robot.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param robot
    *           - the other robot.
    * @see #setTurnPerpenToAngle(double)
    */
   public final void setTurnPerpenToRobot(RobotData robot) {
      double theta = Utils.relative(info.bearing(robot) + Utils.QUARTER_CIRCLE);
      this.setRight(theta);
   }

   /**
    * Makes the robot turn to another robot before returning.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the other robot,
    * <code>BACKWARD</code> otherwise.
    * 
    * @param robot
    *           - the other robot.
    * @return whether the front or back was turned to the other robot.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #turnToAnglewBF(double)
    */
   public final int turnToRobotwBF(RobotData robot) {
      double theta = info.angle(robot);
      return this.turnToAnglewBF(theta);
   }

   /**
    * Sets the robot to turn to another robot.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the other robot,
    * <code>BACKWARD</code> otherwise.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param robot
    *           - the other robot.
    * @return whether the front or back was turned to the other robot.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #setTurnToAnglewBF(double)
    */
   public final int setTurnToRobotwBF(RobotData robot) {
      double theta = info.angle(robot);
      return this.setTurnToAnglewBF(theta);
   }

   /**
    * Makes the robot turn perpendicular to another robot before returning.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the other robot,
    * <code>BACKWARD</code> otherwise.
    * 
    * @param robot
    *           - the other robot.
    * @return whether the front or back was turned to the other robot.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #turnPerpenToAnglewBF(double)
    */
   public final int turnPerpenToRobotwBF(RobotData robot) {
      double theta = Utils.relative(info.angle(robot) + Utils.QUARTER_CIRCLE);
      return this.turnToAnglewBF(theta);
   }

   /**
    * Sets the robot to turn perpendicular to another robot.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the other robot,
    * <code>BACKWARD</code> otherwise.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param robot
    *           - the other robot.
    * @return whether the front or back was turned to the other robot.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #setTurnPerpenToAnglewBF(double)
    */
   public final int setTurnPerpenToRobotwBF(RobotData robot) {
      double theta = Utils.relative(info.angle(robot) + Utils.QUARTER_CIRCLE);
      return this.setTurnToAnglewBF(theta);
   }

   /**
    * Makes the robot turn perpendicular to another robot with distance control before returning.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep the other robot at
    * a set distance.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the other robot,
    * <code>BACKWARD</code> otherwise. Best used by multiplying it by the moving distance and moving
    * forward that distance. This will move the robot into an arc around the robot that is exactly
    * <code>desiredDist</code> away.
    * 
    * @param robot
    *           - the other robot.
    * @param desiredDist
    *           - the distance away from the other robot that is desired.
    * @return whether the front or back was turned perpendicular to the other robot.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #turnPerpenToAnglewBFwDC(double, double, double)
    */
   public final int turnPerpenToRobotwBFwDC(RobotData robot, double desiredDist) {
      return this.turnPerpenToAnglewBFwDC(info.angle(robot), info.dist(robot), desiredDist);
   }

   /**
    * Sets the robot to turn perpendicular to another robot with distance control.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep the other robot at
    * a set distance.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the other robot,
    * <code>BACKWARD</code> otherwise. Best used by multiplying it by the moving distance and moving
    * forward that distance. This will move the robot into an arc around the robot that is exactly
    * <code>desiredDist</code> away.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param robot
    *           - the other robot.
    * @param desiredDist
    *           - the distance away from the other robot that is desired.
    * @return whether the front or back was turned perpendicular to the other robot.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #setTurnPerpenToAnglewBFwDC(double, double, double)
    */
   public final int setTurnPerpenToRobotwBFwDC(RobotData robot, double desiredDist) {
      return this.setTurnPerpenToAnglewBFwDC(info.angle(robot), info.dist(robot), desiredDist);
   }

   /**
    * Sets the robot to turn perpendicular to another robot with distance control and robot
    * monitoring.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep the other robot at
    * a set distance.<br>
    * <br>
    * Robot monitoring will allow the robot to move forward or backward and still keep
    * <code>disiredDist</code> away from the other robot.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute() }</code> is called.
    * 
    * @param robot
    *           - the other robot.
    * @param desiredDist
    *           - the distance away from the other robot that is desired.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #setTurnPerpenToAnglewBFwDCwRM(double, double, double)
    */
   public final void setTurnPerpenToRobotwBFwDCwRM(RobotData robot, double desiredDist) {
      this.setTurnPerpenToAnglewBFwDCwRM(info.angle(robot), info.dist(robot), desiredDist);
   }

   /**
    * Makes the robot turn to a point before returning.
    * 
    * @param point
    *           - the point.
    * @see #turnToAngle(double)
    */
   public final void turnToPoint(Point2D point) {
      double theta = info.bearing(point);
      right(theta);
   }

   /**
    * Sets the robot to turn to a point.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param point
    *           - the point
    * @see #setTurnToAngle(double)
    */
   public final void setTurnToPoint(Point2D point) {
      double theta = info.bearing(point);
      this.setRight(theta);
   }

   /**
    * Makes the robot turn perpendicular to a point before returning.
    * 
    * @param point
    *           - the point.
    * @see #turnPerpenToAngle(double)
    */
   public final void turnPerpenToPoint(Point2D point) {
      double theta = Utils.relative(info.bearing(point) + Utils.QUARTER_CIRCLE);
      this.right(theta);
   }

   /**
    * Sets the robot to turn perpendicular to a point.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param point
    *           - the point.
    * @see #setTurnPerpenToAngle(double)
    */
   public final void setTurnPerpenToPoint(Point2D point) {
      double theta = Utils.relative(info.bearing(point) + Utils.QUARTER_CIRCLE);
      this.setRight(theta);
   }

   /**
    * Makes the robot turn to a point before returning.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the point,
    * <code>BACKWARD</code> otherwise.
    * 
    * @param point
    *           - the point.
    * @return whether the front or back was turned to the coordinate.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #turnToAnglewBF(double)
    */
   public final int turnToPointwBF(Point2D point) {
      double theta = info.angle(point);
      return this.turnToAnglewBF(theta);
   }

   /**
    * Sets the robot to turn to a point.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the point,
    * <code>BACKWARD</code> otherwise.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param point
    *           - the point.
    * @return whether the front or back was turned to the coordinate.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #setTurnToAnglewBF(double)
    */
   public final int setTurnToPointwBF(Point2D point) {
      double theta = info.angle(point);
      return this.setTurnToAnglewBF(theta);
   }

   /**
    * Makes the robot turn perpendicular to a point before returning.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the point,
    * <code>BACKWARD</code> otherwise.
    * 
    * @param point
    *           - the point.
    * @return whether the front or back was turned to the coordinate.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #turnPerpenToAnglewBF(double)
    */
   public final int turnPerpenToPointwBF(Point2D point) {
      double theta = Utils.relative(info.angle(point) + Utils.QUARTER_CIRCLE);
      return this.turnToAnglewBF(theta);
   }

   /**
    * Sets the robot to turn perpendicular to a point.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the point,
    * <code>BACKWARD</code> otherwise.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param point
    *           - the point.
    * @return whether the front or back was turned to the coordinate.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #setTurnPerpenToAnglewBF(double)
    */
   public final int setTurnPerpenToPointwBF(Point2D point) {
      double theta = Utils.relative(info.angle(point) + Utils.QUARTER_CIRCLE);
      return this.setTurnToAnglewBF(theta);
   }

   /**
    * Makes the robot turn perpendicular to a point with distance control before returning.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep the point at a set
    * distance.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the point,
    * <code>BACKWARD</code> otherwise. Best used by multiplying it by the moving distance and moving
    * forward that distance. This will move the robot into an arc around the robot that is exactly
    * <code>desiredDist</code> away.
    * 
    * @param point
    *           - the point.
    * @param desiredDist
    *           - the distance away from the coordinate that is desired.
    * @return whether the front or back was turned perpendicular to the coordinate.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #turnPerpenToAnglewBFwDC(double, double, double)
    */
   public final int turnPerpenToPointwBFwDC(Point2D point, double desiredDist) {
      return this.turnPerpenToAnglewBFwDC(info.angle(point), info.dist(point), desiredDist);
   }

   /**
    * Sets the robot to turn perpendicular to a point with distance control.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep the point at a set
    * distance.<br>
    * <br>
    * Will return <code>FORWARD</code> if the front of the robot was turned to the point,
    * <code>BACKWARD</code> otherwise. Best used by multiplying it by the moving distance and moving
    * forward that distance. This will move the robot into an arc around the robot that is exactly
    * <code>desiredDist</code> away.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute() }</code> is called.
    * 
    * @param point
    *           - the point.
    * @param desiredDist
    *           - the distance away from the coordinate that is desired.
    * @return whether the front or back was turned perpendicular to the coordinate.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #setTurnPerpenToAnglewBFwDC(double, double, double)
    */
   public final int setTurnPerpenToPointwBFwDC(Point2D point, double desiredDist) {
      return this.setTurnPerpenToAnglewBFwDC(info.angle(point), info.dist(point), desiredDist);
   }

   /**
    * Sets the robot to turn perpendicular to a point with distance control and robot monitoring.<br>
    * <br>
    * Distance control will allow the robot to move in such a way that will keep the point at a set
    * distance.<br>
    * <br>
    * Robot monitoring will allow the robot to move forward or backward and still keep
    * <code>disiredDist</code> away from the point.<br>
    * <br>
    * Will return before turning even commences, turning will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute() }</code> is called.
    * 
    * @param point
    *           - the point.
    * @param desiredDist
    *           - the distance away from the coordinate that is desired.
    * @see #FORWARD
    * @see #BACKWARD
    * @see #setTurnPerpenToAnglewBFwDCwRM(double, double, double)
    */
   public final void setTurnPerpenToPointwBFwDCwRM(Point2D point, double desiredDist) {
      this.setTurnPerpenToAnglewBFwDCwRM(info.angle(point), info.dist(point), desiredDist);
   }

   /**
    * Makes the robot move to a point before returning.
    * 
    * @param point
    *           - the point.
    */
   public final void moveToPoint(Point2D point) {
      this.moveToXY(point.getX(), point.getY());
   }

   /**
    * Sets the robot to move to a point.<br>
    * <br>
    * Will return before moving even commences, moving will happen when
    * <code>{@link robocode.AdvancedRobot#execute() execute()}</code> is called.
    * 
    * @param point
    *           - the point.
    */
   public final void setMoveToPoint(Point2D point) {
      this.setMoveToXY(point.getX(), point.getY());
   }

   // TODO documentation: method - setSmoothWalls()
   public final void setSmoothWalls() {
      double turnRadius = RobotInfo.turnRadius(RobotInfo.MAX_VELOCITY) + 8;

      double myX = info.getX();
      double myY = info.getY();

      double wallGrace = 0.0D;


      double leftAngle;
      double rightAngle;
      leftAngle = rightAngle = 0.0D;

      boolean NORTH, EAST, SOUTH, WEST, TWO_WALLS;
      NORTH = EAST = SOUTH = WEST = TWO_WALLS = false;

      double distNorth = Math.max((info.getBattleFieldHeight() - myY - RobotInfo.MIN_WALL_DIST - wallGrace), 0.0D);
      double distEast = Math.max((info.getBattleFieldWidth() - myX - RobotInfo.MIN_WALL_DIST - wallGrace), 0.0D);
      double distSouth = Math.max((myY - RobotInfo.MIN_WALL_DIST - wallGrace), 0.0D);
      double distWest = Math.max((myX - RobotInfo.MIN_WALL_DIST - wallGrace), 0.0D);

      NORTH = distNorth <= 2 * turnRadius;
      EAST = distEast <= 2 * turnRadius;
      SOUTH = distSouth <= 2 * turnRadius;
      WEST = distWest <= 2 * turnRadius;

      double northOffsetAngle = 0.0D;
      double eastOffsetAngle = 0.0D;
      double southOffsetAngle = 0.0D;
      double westOffsetAngle = 0.0D;

      if (distNorth <= turnRadius || (NORTH && (EAST || WEST)))
         northOffsetAngle = wallOffsetAngle(distNorth, turnRadius);
      if (distEast <= turnRadius || (EAST && (NORTH || SOUTH)))
         eastOffsetAngle = wallOffsetAngle(distEast, turnRadius);
      if (distSouth <= turnRadius || (SOUTH && (EAST || WEST)))
         southOffsetAngle = wallOffsetAngle(distSouth, turnRadius);
      if (distWest <= turnRadius || (WEST && (NORTH || SOUTH)))
         westOffsetAngle = wallOffsetAngle(distWest, turnRadius);

      /**
       * Very confusing but the code is right. The basic idea of it is shown by the figure:
       * 
       * <pre>
       *   _________________________________________________
       *  |          |             |             |          |
       *  |          |             r             |          |
       *  |          |    _________|_________    |          |
       *  |          |   |                   |   |          |
       *  |-------- 2*r -|                   |- 2*r --------|
       *  |        __|___|                   |___|__        |
       *  |       |                                 |       |
       *  |       |                                 |       |
       *  |       |                                 |       |
       *  |-- r --|                                 |-- r --|
       *  |       |                                 |       |
       *  |       |                                 |       |
       *  |       |______                     ______|       |
       *  |          |   |                   |   |          |
       *  |-------- 2*r -|                   |- 2*r --------|
       *  |          |   |___________________|   |          |
       *  |          |             |             |          |
       *  |          |             r             |          |
       *  |__________|_____________|_____________|__________|
       * 
       * </pre>
       * 
       * Where r is equal to the turn radius of the robot.<br>
       * <br>
       * If the robot is outside the 'plus sign' then the robot's heading might have to be adjusted.
       */
      if (NORTH) {
         if (EAST) {
            TWO_WALLS = true;
            leftAngle = Math.min(-northOffsetAngle, Utils.QUARTER_CIRCLE - eastOffsetAngle);
            rightAngle = Math.max(Utils.QUARTER_CIRCLE + eastOffsetAngle, northOffsetAngle);
         } else if (WEST) {
            TWO_WALLS = true;
            leftAngle = Math.min(-Utils.QUARTER_CIRCLE - westOffsetAngle, -northOffsetAngle);
            rightAngle = Math.max(northOffsetAngle, -Utils.QUARTER_CIRCLE + westOffsetAngle);
         } else if (distNorth <= turnRadius) {
            leftAngle = -northOffsetAngle;
            rightAngle = northOffsetAngle;
         }
      } else if (SOUTH) {
         if (EAST) {
            TWO_WALLS = true;
            leftAngle = Math.min(Utils.QUARTER_CIRCLE - eastOffsetAngle, Utils.HALF_CIRCLE - southOffsetAngle);
            rightAngle = Math.max(Utils.HALF_CIRCLE + southOffsetAngle, Utils.QUARTER_CIRCLE + eastOffsetAngle);
         } else if (WEST) {
            TWO_WALLS = true;
            leftAngle = Math.min(-Utils.HALF_CIRCLE - southOffsetAngle, -Utils.QUARTER_CIRCLE - westOffsetAngle);
            rightAngle = Math.max(-Utils.QUARTER_CIRCLE + westOffsetAngle, -Utils.HALF_CIRCLE + southOffsetAngle);
         } else if (distSouth <= turnRadius) {
            leftAngle = Utils.HALF_CIRCLE - southOffsetAngle;
            rightAngle = Utils.HALF_CIRCLE + southOffsetAngle;
         }
      } else if (EAST && distEast <= turnRadius) {
         leftAngle = Utils.QUARTER_CIRCLE - eastOffsetAngle;
         rightAngle = Utils.QUARTER_CIRCLE + eastOffsetAngle;
      } else if (WEST && distWest <= turnRadius) {
         leftAngle = -Utils.QUARTER_CIRCLE - westOffsetAngle;
         rightAngle = -Utils.QUARTER_CIRCLE + westOffsetAngle;
      }

      /**
       * The other thing that effects whether or not the robot's heading is adjusted is whether or
       * not the robot's heading is in between two angles. again this is best shown be the figure:
       * 
       * <pre>
       * 
       * wall
       * |
       * |\
       * | \ right danger angle
       * |  \  
       * |   \ | heading
       * |d   \|
       * |a  |---|
       * |n a|   | robot
       * |g r|---|
       * |e e /
       * |r a/
       * |  / left danger angle
       * | /
       * |/
       * |
       * 
       * </pre>
       * 
       * If the robot's heading is between the right and left danger angles then the robot's heading
       * will be adjusted.
       */
      double dangerAngle = Utils.absolute(rightAngle - leftAngle);
      if (TWO_WALLS
            && leftAngle > rightAngle
            && ((distNorth > turnRadius && NORTH) || (distEast > turnRadius && EAST)
                  || (distSouth > turnRadius && SOUTH) || (distWest > turnRadius && WEST)))
         dangerAngle = Utils.absolute(leftAngle - rightAngle);

      double movingHeading = (info.getMovingSign() < 0 ? Utils.oppositeRelative(info.getHeading()) : info.getHeading())
            + info.getTurn();

      boolean testBetween = Utils.absolute(movingHeading - leftAngle) < dangerAngle;
      testBetween = testBetween && Utils.absolute(rightAngle - movingHeading) < dangerAngle;

      if (testBetween) {
         setRight(Utils.absMin(Utils.relative(leftAngle - movingHeading), Utils.relative(rightAngle - movingHeading)));
      }
   }

   public final double setSmoothWalls(double x, double y, double heading) {
      double wallGrace = 0.0D;

      double turnRadius = RobotInfo.turnRadius(RobotInfo.MAX_VELOCITY) + 8;

      double leftAngle;
      double rightAngle;
      leftAngle = rightAngle = 0.0D;

      boolean NORTH, EAST, SOUTH, WEST, TWO_WALLS;
      NORTH = EAST = SOUTH = WEST = TWO_WALLS = false;

      double distNorth = Math.max((info.getBattleFieldHeight() - y - RobotInfo.MIN_WALL_DIST - wallGrace), 0.0D);
      double distEast = Math.max((info.getBattleFieldWidth() - x - RobotInfo.MIN_WALL_DIST - wallGrace), 0.0D);
      double distSouth = Math.max((y - RobotInfo.MIN_WALL_DIST - wallGrace), 0.0D);
      double distWest = Math.max((x - RobotInfo.MIN_WALL_DIST - wallGrace), 0.0D);

      NORTH = distNorth <= 2 * turnRadius;
      EAST = distEast <= 2 * turnRadius;
      SOUTH = distSouth <= 2 * turnRadius;
      WEST = distWest <= 2 * turnRadius;

      double northOffsetAngle = 0.0D;
      double eastOffsetAngle = 0.0D;
      double southOffsetAngle = 0.0D;
      double westOffsetAngle = 0.0D;

      if (distNorth <= turnRadius || (NORTH && (EAST || WEST)))
         northOffsetAngle = wallOffsetAngle(distNorth, turnRadius);
      if (distEast <= turnRadius || (EAST && (NORTH || SOUTH)))
         eastOffsetAngle = wallOffsetAngle(distEast, turnRadius);
      if (distSouth <= turnRadius || (SOUTH && (EAST || WEST)))
         southOffsetAngle = wallOffsetAngle(distSouth, turnRadius);
      if (distWest <= turnRadius || (WEST && (NORTH || SOUTH)))
         westOffsetAngle = wallOffsetAngle(distWest, turnRadius);

      if (NORTH) {
         if (EAST) {
            TWO_WALLS = true;
            leftAngle = Math.min(-northOffsetAngle, Utils.QUARTER_CIRCLE - eastOffsetAngle);
            rightAngle = Math.max(Utils.QUARTER_CIRCLE + eastOffsetAngle, northOffsetAngle);
         } else if (WEST) {
            TWO_WALLS = true;
            leftAngle = Math.min(-Utils.QUARTER_CIRCLE - westOffsetAngle, -northOffsetAngle);
            rightAngle = Math.max(northOffsetAngle, -Utils.QUARTER_CIRCLE + westOffsetAngle);
         } else if (distNorth <= turnRadius) {
            leftAngle = -northOffsetAngle;
            rightAngle = northOffsetAngle;
         }
      } else if (SOUTH) {
         if (EAST) {
            TWO_WALLS = true;
            leftAngle = Math.min(Utils.QUARTER_CIRCLE - eastOffsetAngle, Utils.HALF_CIRCLE - southOffsetAngle);
            rightAngle = Math.max(Utils.HALF_CIRCLE + southOffsetAngle, Utils.QUARTER_CIRCLE + eastOffsetAngle);
         } else if (WEST) {
            TWO_WALLS = true;
            leftAngle = Math.min(-Utils.HALF_CIRCLE - southOffsetAngle, -Utils.QUARTER_CIRCLE - westOffsetAngle);
            rightAngle = Math.max(-Utils.QUARTER_CIRCLE + westOffsetAngle, -Utils.HALF_CIRCLE + southOffsetAngle);
         } else if (distSouth <= turnRadius) {
            leftAngle = Utils.HALF_CIRCLE - southOffsetAngle;
            rightAngle = Utils.HALF_CIRCLE + southOffsetAngle;
         }
      } else if (EAST && distEast <= turnRadius) {
         leftAngle = Utils.QUARTER_CIRCLE - eastOffsetAngle;
         rightAngle = Utils.QUARTER_CIRCLE + eastOffsetAngle;
      } else if (WEST && distWest <= turnRadius) {
         leftAngle = -Utils.QUARTER_CIRCLE - westOffsetAngle;
         rightAngle = -Utils.QUARTER_CIRCLE + westOffsetAngle;
      }

      double dangerAngle = Utils.absolute(rightAngle - leftAngle);
      if (TWO_WALLS
            && leftAngle > rightAngle
            && ((distNorth > turnRadius && NORTH) || (distEast > turnRadius && EAST)
                  || (distSouth > turnRadius && SOUTH) || (distWest > turnRadius && WEST)))
         dangerAngle = Utils.absolute(leftAngle - rightAngle);

      boolean testBetween = Utils.absolute(heading - leftAngle) < dangerAngle;
      testBetween = testBetween && Utils.absolute(rightAngle - heading) < dangerAngle;

      if (testBetween) {
         heading = heading + Utils.absMin(Utils.relative(leftAngle - heading), Utils.relative(rightAngle - heading));
      }
      return heading;
   }

   /**
    * Returns the distance ratio of the the current distance (<code>dist</code>) and the desired
    * distance ( <code>disiredDist</code>).<br>
    * <br>
    * The value will be less then one when the robot must move farther away to achieve the desired
    * distance, and greater then one when the opposite is true.<br>
    * <br>
    * The maximum this value will be is: {@value MAX_DIST_RATIO}<br>
    * The minimum this value will be is: {@value MIN_DIST_RATIO}
    * 
    * @param curDist
    *           - the current distance.
    * @param desiredDist
    *           - the desired distance.
    * @return the distance ratio.
    */
   private static final double distRatio(double curDist, double desiredDist) {
      return Utils.limit(MIN_DIST_RATIO, (desiredDist / curDist), MAX_DIST_RATIO);
   }

   // TODO documentation: method - wallOffsetAngle(double, double)
   private static final double wallOffsetAngle(final double distFromWall, final double turnRadius) {
      double offsetAngle;
      if (turnRadius > distFromWall) {
         offsetAngle = Utils.acos(distFromWall / turnRadius);
         offsetAngle = (turnRadius == 0.0D ? 0.0D : offsetAngle);
      } else {
         offsetAngle = -Utils.acos((2 * turnRadius - distFromWall) / turnRadius);
         offsetAngle = (distFromWall == 0.0D ? 0.0D : offsetAngle);
      }
      return offsetAngle;
   }

}
