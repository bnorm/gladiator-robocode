package kid.movement.robot;

import java.awt.geom.Point2D;
import java.io.PrintStream;

import kid.data.gravity.GravityEngine;
import kid.data.gravity.GravityPoint;
import kid.robot.RobotData;
import kid.virtual.VirtualBullet;
import robocode.Event;
import robocode.RobocodeFileOutputStream;
import robocode.Robot;

// TODO documentation: class - AntiGravity : Movement
// TODO code: methods - AntiGravity : Movement

/**
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class AntiGravity extends Movement {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = 5830527994881762865L;

   private GravityEngine engine;

   /**
    * @param myRobot
    */
   public AntiGravity(final Robot myRobot) {
      super(myRobot);
      init(myRobot);
   }

   private void init(final Robot myRobot) {
      engine = new GravityEngine(myRobot.getBattleFieldWidth(), myRobot.getBattleFieldHeight());
      engine.add(new GravityPoint(0.0D, 0.0D, 10.0D));
      engine.add(new GravityPoint(0.0D, myRobot.getBattleFieldHeight(), 10.0D));
      engine.add(new GravityPoint(myRobot.getBattleFieldWidth(), 0.0D, 10.0D));
      engine.add(new GravityPoint(myRobot.getBattleFieldWidth(), myRobot.getBattleFieldHeight(), 10.0D));
   }

   @Override
   public void inEvent(final Event event) {
   }

   @Override
   public void move(final RobotData[] robots, final VirtualBullet[] teammateBullets) {
   }

   @Override
   public Point2D.Double[] getMove(final RobotData[] robots, final VirtualBullet[] teammateBullets, final long time) {
      return null;
   }

   @Override
   public String getName() {
      return new String("Anti Gravity Movement");
   }

   @Override
   public String getType() {
      return new String("? Movement");
   }

   public void print(final PrintStream console) {
   }

   public void print(final RobocodeFileOutputStream output) {
   }

}
