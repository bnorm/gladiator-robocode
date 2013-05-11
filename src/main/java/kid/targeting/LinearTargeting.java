package kid.targeting;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.PrintStream;

import kid.graphics.Colors;
import kid.robot.RobotData;
import kid.utils.Utils;
import robocode.RobocodeFileOutputStream;
import robocode.Robot;
import robocode.Rules;

// TODO documentation: class

public class LinearTargeting extends Targeting {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = 6581499402431094042L;

   private boolean avgVelocity = false;

   public LinearTargeting(final Robot myRobot) {
      this(myRobot, false);
   }

   public LinearTargeting(final Robot myRobot, final boolean avgVelocity) {
      super(myRobot);
      init(avgVelocity);
   }

   public LinearTargeting(final LinearTargeting targeting) {
      super(targeting);
      init(targeting.avgVelocity);
   }

   private void init(final boolean avgVelocity) {
      this.avgVelocity = avgVelocity;
   }

   @Override
   public double getAngle(final RobotData target, double firePower) {
      double angle = Utils.relative(gunInfo.getHeading() + gunInfo.getHeading());
      if (target != null && !target.isDead()) {

         firePower = Math.min(robot.getEnergy(), firePower);
         firePower = Utils.limit(Rules.MIN_BULLET_POWER, firePower, Rules.MAX_BULLET_POWER);

         double bulletVelocity = Rules.getBulletSpeed(firePower);
         Rectangle2D battleField = gunInfo.getBattleField();

         double myX = robot.getX();
         double myY = robot.getY();
         double prodictedX = target.getX();
         double prodictedY = target.getY();
         double enemyVelocity = (avgVelocity ? target.getAvgVelocity() : target.getVelocity());

         double deltaX = Utils.getDeltaX(enemyVelocity, target.getHeading());
         double deltaY = Utils.getDeltaY(enemyVelocity, target.getHeading());

         for (int t = -1; Utils.sqr(t * bulletVelocity) < Utils.distSq(myX, myY, prodictedX, prodictedY) && battleField.contains(prodictedX, prodictedY); t++) {
            prodictedX += deltaX;
            prodictedY += deltaY;
            if (!battleField.contains(prodictedX, prodictedY)) {
               prodictedX -= deltaX;
               prodictedY -= deltaY;
            }
         }
         angle = gunInfo.angle(prodictedX, prodictedY);
      }
      return angle;
   }

   @Override
   public Color getColor() {
      return Colors.GREEN;
   }

   @Override
   public String getName() {
      return new String("Linear Targeting");
   }

   @Override
   public String getType() {
      return new String("Fast");
   }

   public void print(final PrintStream console) {
      // TODO method stub
   }

   public void print(final RobocodeFileOutputStream output) {
      // TODO method stub
   }

   public void debug(final PrintStream console) {
      // TODO method stub
   }

   public void debug(final RobocodeFileOutputStream output) {
      // TODO method stub
   }

   @Override
   public Object clone() {
      return new LinearTargeting(this);
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof LinearTargeting) {
         LinearTargeting targeting = (LinearTargeting) obj;
         if (targeting.robot != null && targeting.robot.getName() != null && this.robot != null)
            return targeting.robot.getName().equals(this.robot.getName());
      }
      return false;
   }

   @Override
   public String toString() {
      // TODO method stub
      return new String();
   }

   @Override
   protected void finalize() throws Throwable {
      super.finalize();
   }

}