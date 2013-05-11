package kid.targeting;

import java.awt.Color;
import java.io.PrintStream;

import kid.data.factor.GuessFactor;
import kid.graphics.Colors;
import kid.management.ProfileManager;
import kid.robot.EnemyProfile;
import kid.robot.RobotData;
import kid.utils.Utils;
import robocode.RobocodeFileOutputStream;
import robocode.Robot;

public class GuessFactorTargeting extends Targeting {
   private static final long serialVersionUID = 7865246338138092517L;

   private ProfileManager profiles;
   private int clusterSize = 10;

   public GuessFactorTargeting(Robot myRobot, ProfileManager profiles) {
      super(myRobot);
      init(profiles, -1);
   }

   public GuessFactorTargeting(Robot myRobot, ProfileManager profiles, int clusterSize) {
      super(myRobot);
      init(profiles, clusterSize);
   }

   public GuessFactorTargeting(GuessFactorTargeting targeting) {
      super(targeting);
      init(targeting.profiles, targeting.clusterSize);
   }

   private void init(ProfileManager p, int s) {
      profiles = p;
      if (s > 0)
         clusterSize = s;
   }

   @Override
   public double getAngle(RobotData target, double firePower) {
      if (target.getEnergy() == 0.0 && target.getVelocity() == 0.0)
         return gunInfo.angle(target);

      EnemyProfile profile = profiles.getProfile(target.getName());
      GuessFactor[] cluster = profile.getCluster(clusterSize);

      double[] bins = new double[41];
      for (GuessFactor gf : cluster) {
         for (int i = 0; i < bins.length; i++) {
            bins[i] += Utils.limit(0.0, 10.0 / (30.0 * Utils.sqr(Utils.getGuessFactor(i, bins.length)
                  - gf.getGuessFactor()) + 1.0) - 4.99, 5.0);
         }
      }

      double max = -1.0;
      int index = (bins.length - 1) / 2;
      for (int i = 0; i < bins.length; i++) {
         if (bins[i] > max) {
            max = bins[i];
            index = i;
         }
      }
      double gf = Utils.getGuessFactor(index, bins.length);
      return gunInfo.angle(target) + Utils.getAngleOffset(robot, target, gf, firePower);
   }

   @Override
   public Color getColor() {
      return Colors.YELLOW;
   }

   @Override
   public String getName() {
      return new String("Guess Factor Targeting (Cluster Size: " + clusterSize + ")");
   }

   @Override
   public String getType() {
      return new String("Statisticle");
   }

   public void print(PrintStream console) {
   }

   public void print(RobocodeFileOutputStream output) {
   }

   @Override
   public Object clone() {
      return new GuessFactorTargeting(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof CircularTargeting) {
         CircularTargeting targeting = (CircularTargeting) obj;
         if (targeting.robot != null && targeting.robot.getName() != null && this.robot != null)
            return targeting.robot.getName().equals(this.robot.getName());
      }
      return false;
   }

   @Override
   public String toString() {
      return new String();
   }

   @Override
   protected void finalize() throws Throwable {
      super.finalize();
   }

}
