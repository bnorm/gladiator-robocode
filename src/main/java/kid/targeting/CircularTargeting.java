package kid.targeting;

import java.awt.Color;
import java.io.PrintStream;

import kid.graphics.Colors;
import kid.robot.RobotData;
import robocode.RobocodeFileOutputStream;
import robocode.Robot;

// TODO documentation: class

public class CircularTargeting extends Targeting {

   /**
    * Determines if a de-serialized file is compatible with this class.<BR>
    * <BR>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = 6581499402431094042L;

   private boolean avgHeading = false;
   private boolean avgVelocity = false;

   public CircularTargeting(Robot myRobot) {
      this(myRobot, false, false);
   }

   public CircularTargeting(Robot myRobot, boolean avgVelocity, boolean avgHeading) {
      super(myRobot);
      init(avgHeading, avgVelocity);
   }

   public CircularTargeting(CircularTargeting targeting) {
      super(targeting);
      init(targeting.avgVelocity, targeting.avgHeading);
   }

   private void init(boolean avgVelocity, boolean avgHeading) {
      this.avgHeading = avgHeading;
      this.avgVelocity = avgVelocity;
   }

   @Override
   public double getAngle(RobotData target, double firePower) {
      return super.getAngle(target, firePower, (avgVelocity ? target.getAvgVelocity() : target.getVelocity()), (avgHeading ? target.getAvgDeltaHeading() : target.getDeltaHeading()));
   }

   @Override
   public Color getColor() {
      return Colors.BLUE;
   }

   @Override
   public String getName() {
      return new String("Circular Targeting");
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

   @Override
   public Object clone() {
      return new CircularTargeting(this);
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
      // TODO method stub
      return new String();
   }

   @Override
   protected void finalize() throws Throwable {
      super.finalize();
   }

}