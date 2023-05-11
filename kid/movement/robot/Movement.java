package kid.movement.robot;

import java.awt.geom.Point2D;
import java.io.Serializable;

import kid.data.Printable;
import kid.data.factor.GuessFactor;
import kid.data.info.RobotInfo;
import kid.data.robot.RobotData;
import kid.data.virtual.*;
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

   public final void move(final RobotData[] robots) {
      move(robots, new VirtualBullet[0]);
   }
   
   public abstract void move(final RobotData[] robots, final VirtualBullet[] teammateBullets);

   public abstract void move(final RobotData[] robots, final DataWave<GuessFactor, RobotData, RobotData>[] enemyWaves);

   public final Point2D[] getMove(final RobotData[] robots, final long time) {
      return getMove(robots, new VirtualBullet[0], time);
   }
   
   public abstract Point2D[] getMove(final RobotData[] robots, final VirtualBullet[] teammateBullets, final long time);

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