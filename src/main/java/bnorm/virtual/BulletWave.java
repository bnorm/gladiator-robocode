package bnorm.virtual;

import robocode.Bullet;

public class BulletWave extends VectorWave implements IBulletWave {

   private Bullet bullet;

   public BulletWave(Bullet bullet, long time) {
      super(bullet.getX(), bullet.getY(), bullet.getVelocity(), bullet.getHeadingRadians(), time);
      this.bullet = bullet;
   }

   @Override
   public boolean isActive(long currentTime) {
      return bullet != null && bullet.isActive() && super.isActive(currentTime);
   }

   @Override
   public Bullet getBullet() {
      return bullet;
   }
}
