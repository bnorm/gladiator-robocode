package bnorm.virtual;

import bnorm.base.Tank;
import bnorm.utils.Utils;

/**
 * Represents a wave that exists on the Robocode battlefield. This wave has a
 * starting coordinate, a speed, and a starting time. Given a specified time,
 * we can see where this wave is on the battlefield and how far it has
 * traveled.
 *
 * @author Brian Norman
 */
public abstract class Wave extends Point implements IWave {

   /**
    * The speed of the wave in pixels/tick.
    */
   private double velocity;

   /**
    * The time the wave started.
    */
   private long time;

   /**
    * Creates a new blank wave. This wave is centered at negative infinity and has no speed and was created at time
    * <code>0</code>.
    */
   public Wave() {
      this(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0, 0);
   }

   /**
    * Creates a new wave at the specified starting coordinates and with the specified speed and start time.
    *
    * @param x the starting x coordinate.
    * @param y the starting y coordinate.
    * @param velocity the speed of the wave in pixels/tick.
    * @param time the time the wave started.
    */
   public Wave(double x, double y, double velocity, long time) {
      super(x, y);
      this.velocity = velocity;
      this.time = time;
   }

   /**
    * Creates a copy of the wave.
    *
    * @param wave the wave to copy.
    */
   protected Wave(IWave wave) {
      this(wave.getX(), wave.getY(), wave.getVelocity(), wave.getTime());
   }

   @Override
   public double getVelocity() {
      return velocity;
   }

   @Override
   public long getTime() {
      return time;
   }

   @Override
   public boolean isActive(long currentTime) {
      return getX() >= 0 && getY() >= 0 && getX() <= Tank.MAX_BATTLEFIELD_WIDTH && getY() <= Tank.MAX_BATTLEFIELD_HEIGHT
              && distSq(currentTime) <= Tank.MAX_BATTLEFIELD_DIAGONAL;
   }

   @Override
   public boolean isActive(long currentTime, IPoint target) {
      return isActive(currentTime) && distSq(currentTime) < Points.distSq(this, target);
   }

   @Override
   public double dist(long currentTime) {
      return velocity * (currentTime - time);
   }

   @Override
   public double distSq(long currentTime) {
      return Utils.sqr(dist(currentTime));
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof IWave) {
         IWave wave = (IWave) obj;
         return wave.getX() == getX() && wave.getY() == getY() && wave.getVelocity() == velocity
                 && wave.getTime() == time;
      }
      return super.equals(obj);
   }

   @Override
   public String toString() {
      return this.getClass() + "[x=" + getX() + ", y=" + getY() + ", velocity=" + velocity + ", time=" + time + "]";
   }
}
