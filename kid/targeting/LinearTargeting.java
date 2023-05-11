package kid.targeting;

import java.awt.Color;
import java.io.PrintStream;

import robocode.*;

import kid.Colors;
import kid.data.robot.RobotData;

// TODO documentation: class

public class LinearTargeting extends Targeting {

   /**
    * Determines if a de-serialized file is compatible with this class.<BR>
    * <BR>
    * Maintainers must change this value if and only if the new version of this class is
    * not compatible with old versions.
    */
   private static final long serialVersionUID = 6581499402431094042L;

   private boolean avgVelocity = false;

   public LinearTargeting(Robot myRobot) {
      this(myRobot, false);
   }

   public LinearTargeting(Robot myRobot, boolean avgVelocity) {
      super(myRobot);
      init(avgVelocity);
   }

   public LinearTargeting(LinearTargeting targeting) {
      super(targeting);
      init(targeting.avgVelocity);
   }

   private void init(boolean avgVelocity) {
      this.avgVelocity = avgVelocity;
   }

   @Override
   public double getAngle(RobotData target, double firePower) {
      return super.getAngle(target, firePower, (avgVelocity ? target.getAvgVelocity() : target.getVelocity()), 0.0D);
   }

   @Override
   public Color getColor() {
      return Colors.GREEN;
   }

   @Override
   public String getName() {
      return new String("Linear Targeting");
   }

   @Override
   public String getType() {
      return new String("Fast");
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
      return new LinearTargeting(this);
   }

   public boolean equals(Object obj) {
      if (obj instanceof LinearTargeting) {
         LinearTargeting targeting = (LinearTargeting) obj;
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