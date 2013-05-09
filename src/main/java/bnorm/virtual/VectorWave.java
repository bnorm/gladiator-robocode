package bnorm.virtual;

import bnorm.utils.Trig;

public class VectorWave extends Wave implements IVectorWave {

   private IVector vector;

   public VectorWave(double x, double y, double velocity, double heading, long time) {
      super(x, y, velocity, time);
      this.vector = new Vector(0, 0, heading, velocity);
   }

   public VectorWave(double x, double y, double velocity, IPoint target, long time) {
      this(x, y, velocity, Trig.angle(x - target.getX(), y - target.getY()), time);
   }

   @Override
   public double getDeltaX() {
      return vector.getDeltaX();
   }

   @Override
   public double getDeltaY() {
      return vector.getDeltaY();
   }

   @Override
   public double getHeading() {
      return vector.getHeading();
   }
}
