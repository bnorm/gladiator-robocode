package kid.targeting;

import java.awt.Color;
import java.io.PrintStream;

import kid.*;
import kid.data.factor.*;
import kid.data.robot.RobotData;
import kid.segmentation.Leaf;
import robocode.*;

// TODO documentation: class

public class GuessFactorTargeting extends Targeting {

   /**
    * Determines if a deserialized file is compatible with this class.<BR>
    * <BR>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = 6581499402431094042L;

   public static final int MAX_MATCH_LENGTH = 500;

   private RobotGrapher grapher;

   public GuessFactorTargeting(Robot myRobot, RobotGrapher grapher) {
      super(myRobot);
      init(grapher);
   }

   public GuessFactorTargeting(GuessFactorTargeting targeting) {
      super(targeting);
      init(targeting.grapher);
   }

   private void init(RobotGrapher graph) {
      this.grapher = graph;
   }

   @Override
   public double getAngle(RobotData target, double firePower) {
      RobotData myRobot = new RobotData(robot);
      GuessFactor[] factors = grapher.getData(target, myRobot);

      double angle = Utils.angle(myRobot, target);

      int direction = Utils.getDirection(target.getHeading(), target.getVelocity(), angle);

      double[] values = new double[Leaf.DATA_BINS];
      double maxvalue = 0.01D;
      int halfbins = (Leaf.DATA_BINS - 1) / 2;
      int bestindex = halfbins;

      for (int i = 0; i < Leaf.DATA_BINS; i++) {
         for (GuessFactor gf : factors) {
            values[i] += 5.0D / (Utils.sqr(i - (halfbins + halfbins * gf.getGuessFactor())) + 5.0D);
         }
         maxvalue = Math.max(maxvalue, values[i]);
         if (maxvalue == values[i])
            bestindex = i;
      }

      return Utils.relative(angle + direction * Utils.maxEscapeAngle(Rules.getBulletSpeed(firePower))
                            * Utils.getGuessFactor(bestindex, Leaf.DATA_BINS));
   }

   public void draw(RobocodeGraphicsDrawer grid, RobotData target, double firePower) {

   }

   @Override
   public Color getColor() {
      return Colors.YELLOW;
   }

   @Override
   public String getName() {
      return new String("Guess Factor Targeting");
   }

   @Override
   public String getType() {
      return new String("Statistical");
   }

   public void print(PrintStream console) {
      // TODO method stub
   }

   public void print(RobocodeFileOutputStream output) {
      // TODO method stub
   }

   public void debug(PrintStream console) {
      // TODO method stub
   }

   public void debug(RobocodeFileOutputStream output) {
      // TODO method stub
   }

   public Object clone() {
      return new GuessFactorTargeting(this);
   }

   public boolean equals(Object obj) {
      if (obj instanceof GuessFactorTargeting) {
         GuessFactorTargeting targeting = (GuessFactorTargeting) obj;
         if (targeting.robot != null && targeting.robot.getName() != null && this.robot != null)
            return targeting.robot.getName().equals(this.robot.getName());
      }
      return false;
   }

   public String toString() {
      // TODO method stub
      return new String();
   }

   protected void finalize() throws Throwable {
      super.finalize();
   }

}