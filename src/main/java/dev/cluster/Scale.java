package dev.cluster;

import dev.robots.RobotData;

public abstract class Scale {

   private double max = 1.0;

   /**
    * Returns a positive double if <code>v1</code> is greater than <code>v2</code>, a negative double if <code>v1</code>
    * is less than <code>v2</code>, and a zero if they are equal. Another way to think about this is <code>v1</code> -
    * <code>v2</code> or <code>v2</code> subtracted from <code>v1</code>.
    * 
    * @param v1
    * @param v2
    * @return a double representing the relationship between <code>v1</code> and <code>v2</code>: positive, negative, or
    *         zero.
    */
   public double compare(Vector<?> v1, Vector<?> v2) {
      return v1.getComponent(this) - v2.getComponent(this);
   }

   public double compareNormalized(Vector<?> v1, Vector<?> v2) {
      return compare(v1, v2) / max;
   }

   protected abstract double getValue(RobotData view, RobotData reference);

   public double value(RobotData view, RobotData reference) {
      double value = getValue(view, reference);
      max = Math.max(value, max);
      return value;
   }


   public double normalized(RobotData view, RobotData reference) {
      double value = value(view, reference);
      return value / max;
   }

}
