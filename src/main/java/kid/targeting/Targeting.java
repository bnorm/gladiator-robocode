package kid.targeting;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import kid.data.Printable;
import kid.info.GunInfo;
import kid.robot.RobotData;
import kid.utils.Utils;
import kid.virtual.VirtualBullet;
import robocode.Robot;
import robocode.Rules;

// TODO documentation: class

public abstract class Targeting implements Cloneable, Serializable, Printable {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = -4109180260777309708L;

   protected Robot robot;
   protected GunInfo gunInfo;

   protected Targeting(final Robot myRobot) {
      init(myRobot);
   }

   protected Targeting(final Targeting targeting) {
      init(targeting.robot);
   }

   private final void init(final Robot myRobot) {
      this.robot = myRobot;
      gunInfo = new GunInfo(robot);
   }

   public abstract double getAngle(RobotData target, double firePower);

   protected final double getAngle(final RobotData target, double firePower, final double velocity, final double deltaHeading) {
      double angle = Utils.relative(gunInfo.getHeading() + gunInfo.getHeading());
      if (target != null && !target.isDead()) {

         firePower = Utils.limit(Rules.MIN_BULLET_POWER, Math.min(robot.getEnergy(), firePower), Rules.MAX_BULLET_POWER);

         double bulletVelocity = Rules.getBulletSpeed(firePower);
         Rectangle2D battleField = gunInfo.getBattleField();

         double myX = robot.getX();
         double myY = robot.getY();
         double prodictedX = target.getX();
         double prodictedY = target.getY();
         double enemyHeading = target.getHeading();
         double enemyVelocity = velocity;

         double deltaX;
         double deltaY;

         for (int t = -1; Utils.sqr(t * bulletVelocity) < Utils.distSq(myX, myY, prodictedX, prodictedY)
               && (deltaHeading != 0.0D || battleField.contains(prodictedX, prodictedY)); t++) {
            enemyHeading += deltaHeading;
            prodictedX += (deltaX = Utils.getDeltaX(enemyVelocity, enemyHeading));
            prodictedY += (deltaY = Utils.getDeltaY(enemyVelocity, enemyHeading));
            if (!battleField.contains(prodictedX, prodictedY)) {
               prodictedX -= deltaX;
               prodictedY -= deltaY;
            }
         }
         angle = gunInfo.angle(prodictedX, prodictedY);
      }
      return angle;
   }

   public final VirtualBullet getBullet(final RobotData target, final double firePower) {
      return new VirtualBullet(robot.getX(), robot.getY(), getAngle(target, firePower), firePower, robot.getTime());
   }

   public abstract String getName();

   public abstract String getType();

   public abstract Color getColor();

   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof Targeting) {
         Targeting targeting = (Targeting) obj;
         return (targeting.getName().equals(this.getName()));
      }
      return false;
   }

   @Override
   protected void finalize() throws Throwable {
      robot = null;
      gunInfo = null;
      super.finalize();
   }

}