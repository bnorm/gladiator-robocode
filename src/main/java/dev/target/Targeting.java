package dev.target;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import robocode.Robot;
import robocode.Rules;
import dev.draw.Drawable;
import dev.draw.RobotGraphics;
import dev.info.GunInfo;
import dev.robots.RobotData;
import dev.utils.Utils;

public abstract class Targeting implements Drawable {

   protected Robot   robot_;
   protected GunInfo gunInfo_;

   protected Targeting(Robot robot) {
      init(robot);
   }

   private void init(Robot robot) {
      this.robot_ = robot;
      this.gunInfo_ = new GunInfo(robot);
   }

   public abstract double getAngle(RobotData target, double firePower);

   public abstract String getName();

   public abstract String getType();

   public abstract Color getColor();

   protected final double getAngle(RobotData target, double firePower, double velocity, double deltaHeading) {
      double angle = gunInfo_.getHeading();
      if (target != null && !target.isDead()) {
         firePower = Utils.limit(Rules.MIN_BULLET_POWER, Math.min(robot_.getEnergy(), firePower),
               Rules.MAX_BULLET_POWER);

         double bulletVelocity = Rules.getBulletSpeed(firePower);
         Rectangle2D battleField = gunInfo_.getBattleField();

         double x = robot_.getX();
         double y = robot_.getY();
         double prodictedX = target.getX();
         double prodictedY = target.getY();
         double enemyHeading = target.getHeading();
         double enemyVelocity = velocity;

         double dx;
         double dy;

         for (int t = -1; Utils.sqr(t * bulletVelocity) < Utils.distSq(x, y, prodictedX, prodictedY)
               && (deltaHeading != 0.0D || battleField.contains(prodictedX, prodictedY)); t++) {
            enemyHeading += deltaHeading;
            prodictedX += (dx = Utils.deltaX(enemyVelocity, enemyHeading));
            prodictedY += (dy = Utils.deltaY(enemyVelocity, enemyHeading));
            if (!battleField.contains(prodictedX, prodictedY)) {
               prodictedX -= dx;
               prodictedY -= dy;
            }
         }
         angle = gunInfo_.angle(prodictedX, prodictedY);
      }
      return angle;
   }

   @Override
   public void draw(RobotGraphics grid) {

   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Targeting) {
         Targeting targeting = (Targeting) obj;
         return (targeting.getName().equals(this.getName()));
      }
      return false;
   }

}
