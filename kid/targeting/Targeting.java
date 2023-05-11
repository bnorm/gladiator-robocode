package kid.targeting;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import robocode.*;

import kid.Utils;
import kid.data.Printable;
import kid.data.info.*;
import kid.data.robot.RobotData;
import kid.data.virtual.VirtualBullet;

// TODO documentation: class

public abstract class Targeting implements Cloneable, Serializable, Printable {

   protected Robot robot;
   protected GunInfo gunInfo;

   protected Targeting(Robot myRobot) {
      init(myRobot);
   }

   protected Targeting(Targeting targeting) {
      init(targeting.robot);
   }

   private final void init(Robot myRobot) {
      this.robot = myRobot;
      gunInfo = new GunInfo(robot);
   }

   public abstract double getAngle(RobotData target, double firePower);

   // BORED code: update heading before position or after?
   // BORED code: start at -1? why?...
   // BORED code: unstructured
   protected final double getAngle(RobotData target, double firePower, double velocity, double deltaHeading) {
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
         double enemyHeading = target.getHeading();
         double enemyVelocity = velocity;

         for (int t = 0; Utils.sqr(t * bulletVelocity) < Utils.distSq(myX, myY, prodictedX, prodictedY); t++) {
            enemyHeading += deltaHeading;
            prodictedX += Utils.getX(0.0D, enemyVelocity, enemyHeading);
            prodictedY += Utils.getY(0.0D, enemyVelocity, enemyHeading);
            if (!battleField.contains(prodictedX, prodictedY)) {
               prodictedX -= Utils.getX(0.0D, enemyVelocity, enemyHeading);
               prodictedY -= Utils.getY(0.0D, enemyVelocity, enemyHeading);
               if (deltaHeading == 0.0D)
                  break;
            }
         }
         angle = gunInfo.angle(prodictedX, prodictedY);
      }
      return angle;
   }

   public final VirtualBullet getBullet(RobotData target, double firePower) {
      return new VirtualBullet(robot.getX(), robot.getY(), getAngle(target, firePower), firePower, robot.getTime());
   }

   public abstract String getName();

   public abstract String getType();

   public abstract Color getColor();

   public boolean equals(Object obj) {
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