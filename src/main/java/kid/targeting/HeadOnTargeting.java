package kid.targeting;

import java.awt.Color;
import java.io.PrintStream;

import kid.graphics.Colors;
import kid.robot.RobotData;
import robocode.RobocodeFileOutputStream;
import robocode.Robot;

// TODO documentation: class

public class HeadOnTargeting extends Targeting {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = 6581499402431094042L;

   public HeadOnTargeting(Robot myRobot) {
      super(myRobot);
   }

   public HeadOnTargeting(Targeting targeting) {
      super(targeting);
   }

   @Override
   public double getAngle(RobotData target, double firePower) {
      return gunInfo.angle(target);
   }

   @Override
   public Color getColor() {
      return Colors.RED;
   }

   @Override
   public String getName() {
      return new String("Head On Targeting");
   }

   @Override
   public String getType() {
      return new String("Fast");
   }

   public void print(PrintStream console) {
      console.println("");
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
      return new HeadOnTargeting(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof HeadOnTargeting) {
         HeadOnTargeting targeting = (HeadOnTargeting) obj;
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
