package kid.movement.robot;

import java.awt.geom.Point2D;
import java.io.Serializable;

import kid.data.Printable;
import kid.info.RobotInfo;
import kid.robot.RobotData;
import kid.virtual.VirtualBullet;
import robocode.Event;
import robocode.Robot;

// TODO documentation: class (0% complete)

public abstract class Movement implements Cloneable, Serializable, Printable {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = -2922544471044145148L;

   protected Robot robot;
   protected RobotInfo info;
   protected RobotMovement movement;

   protected Movement(final Robot myRobot) {
      init(myRobot);
   }

   private void init(final Robot myRobot) {
      this.robot = myRobot;
      this.movement = new RobotMovement(myRobot);
      this.info = new RobotInfo(myRobot);
   }

   public final void move(final RobotData[] robots) {
      move(robots, new VirtualBullet[0]);
   }

   public abstract void move(final RobotData[] robots, final VirtualBullet[] teammateBullets);

   public final Point2D[] getMove(final RobotData[] robots, final long time) {
      return getMove(robots, new VirtualBullet[0], time);
   }

   public abstract Point2D[] getMove(final RobotData[] robots, final VirtualBullet[] teammateBullets, final long time);

   public abstract void inEvent(final Event event);

   public abstract String getName();

   public abstract String getType();

   @Override
   protected void finalize() throws Throwable {
      this.robot = null;
      this.info = null;
      super.finalize();
   }

}