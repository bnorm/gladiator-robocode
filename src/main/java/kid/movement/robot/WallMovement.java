package kid.movement.robot;

import java.awt.geom.Point2D;
import java.io.PrintStream;
import java.util.Arrays;

import kid.data.RobotChooser;
import kid.robot.RobotData;
import kid.utils.Utils;
import kid.virtual.VirtualBullet;
import robocode.Event;
import robocode.RobocodeFileOutputStream;
import robocode.Robot;

// TODO documentation: class - Wall : Movement
// TODO code: methods - Wall : Movement

/**
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class WallMovement extends Movement {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = -2185474595949395107L;

   private int movingSign;

   /**
    * @param myRobot
    */
   public WallMovement(final Robot myRobot) {
      super(myRobot);
      init(myRobot);
      movement.setRight(info.getHeading() % Utils.QUARTER_CIRCLE);
   }

   private void init(final Robot myRobot) {
      this.movingSign = RobotMovement.FORWARD;
   }

   /*
    * (non-Javadoc)
    * @see kid.upgrade.movement.Movement#inEvent(robocode.Event)
    */
   @Override
   public void inEvent(final Event event) {
   }

   /*
    * (non-Javadoc)
    * @see kid.upgrade.movement.Movement#move(kid.upgrade.data.robot.RobotData[])
    */
   @Override
   public void move(final RobotData[] robots, final VirtualBullet[] teammateBullets) {
      RobotData r = RobotChooser.CLOSEST.getRobot(robot, Arrays.asList(robots));
      if (info.distSq(r) < Utils.sqr(200)) {
         if (Math.abs(info.bearing(r)) < Utils.QUARTER_CIRCLE)
            movingSign = RobotMovement.BACKWARD;
         else
            movingSign = RobotMovement.FORWARD;
      }
      movement.setAhead(movingSign * Double.POSITIVE_INFINITY);
      movement.setSmoothWalls();
   }

   /*
    * (non-Javadoc)
    * @see kid.upgrade.movement.Movement#getMove(kid.upgrade.data.robot.RobotData[])
    */
   @Override
   public Point2D.Double[] getMove(final RobotData[] robots, final VirtualBullet[] teammateBullets, final long time) {
      return null;
   }

   // @SuppressWarnings("unused")
   // private RobotVector getMovement(RobotData[] robots) {
   // RobotData r = RobotChooser.CLOSEST.getRobot(robot, robots);
   // if (info.distToSq(r) < Utils.sqr(200)) {
   // if (Math.abs(info.bearingTo(r)) < Utils.QUARTER_CIRCLE)
   // movingSign = movement.BACKWARD;
   // else
   // movingSign = movement.FORWARD;
   // }
   // movement.setAhead(movingSign * Double.POSITIVE_INFINITY);
   // movement.setSmoothWalls();
   // double heading = 0;
   // double velocity = 0;
   // return new RobotVector.Polar(heading, velocity);
   // }

   /*
    * (non-Javadoc)
    * @see kid.upgrade.movement.Movement#getName()
    */
   @Override
   public String getName() {
      return new String("Wall Movement");
   }

   /*
    * (non-Javadoc)
    * @see kid.upgrade.movement.Movement#getType()
    */
   @Override
   public String getType() {
      return new String("Pattern Movement");
   }

   /*
    * (non-Javadoc)
    * @see kid.upgrade.data.Printable#print(java.io.PrintStream)
    */
   public void print(final PrintStream console) {
   }

   /*
    * (non-Javadoc)
    * @see kid.upgrade.data.Printable#print(java.io.FileOutputStream)
    */
   public void print(final RobocodeFileOutputStream output) {
   }

   /*
    * (non-Javadoc)
    * @see kid.upgrade.data.Debugable#debug(java.io.PrintStream)
    */
   public void debug(final PrintStream console) {
   }

   /*
    * (non-Javadoc)
    * @see kid.upgrade.data.Debugable#debug(java.io.ObjectOutputStream)
    */
   public void debug(final RobocodeFileOutputStream output) {
   }

}
