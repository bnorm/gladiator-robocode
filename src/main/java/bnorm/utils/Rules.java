package main.java.bnorm.utils;

public class Rules {
   
   public static final double ROBOT_COLLISION_DAMAGE = robocode.Rules.ROBOT_HIT_DAMAGE;

   public static double getBulletDamage(double firepower) {
      return robocode.Rules.getBulletDamage(firepower);
   }
   
   public static double getBulletVelocity(double firepower) {
      return robocode.Rules.getBulletSpeed(firepower);
   }
   
   public static double getGunHeat(double firepower) {
      return robocode.Rules.getGunHeat(firepower);
   }
   
   public static double getEnergyGained(double firepower) {
      return robocode.Rules.getBulletHitBonus(firepower);
   }
}
