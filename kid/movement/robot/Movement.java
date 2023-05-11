package kid.movement.robot;

import java.awt.geom.Point2D;
import java.io.Serializable;

import kid.data.Printable;
import kid.data.factor.GuessFactor;
import kid.data.info.RobotInfo;
import kid.data.robot.RobotData;
import kid.data.virtual.DataWave;
import robocode.Event;
import robocode.Robot;

// TODO documentation: class (0% complete)

public abstract class Movement implements Cloneable, Serializable, Printable {

   protected Robot robot;
   protected RobotInfo info;
   protected RobotMovement movement;

   protected Movement(Robot myRobot) {
      init(myRobot);
   }

   private void init(Robot myRobot) {
      this.robot = myRobot;
      this.movement = new RobotMovement(myRobot);
      this.info = new RobotInfo(myRobot);
   }

   public abstract void move(final RobotData[] robots);

   public abstract void move(final RobotData[] robots, final DataWave<GuessFactor, RobotData, RobotData>[] enemyWaves);

   public abstract Point2D[] getMove(final RobotData[] robots, final long time);

   public abstract void inEvent(final Event event);


   public abstract String getName();

   public abstract String getType();


   @Override
   protected void finalize() throws Throwable {
      robot = null;
      info = null;
      super.finalize();
   }

}