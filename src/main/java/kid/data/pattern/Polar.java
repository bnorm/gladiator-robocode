package kid.data.pattern;

import kid.info.RobotInfo;
import kid.utils.Utils;

// TODO documentation: class
public class Polar extends Pattern {

   private static final long serialVersionUID = 8849260510359616074L;

   public static final Polar DUMMY_PATTERN = new Polar(10.0D, 8.0D, 3.0D);

   private double deltaHeading;
   private double velocity;
   private char symbol;

   public Polar(double deltaHeading, double velocity, double acceleration) {
      this.deltaHeading = deltaHeading;
      this.velocity = velocity;
      int charVel = Utils.round((velocity + RobotInfo.MAX_VELOCITY_REV) * 15.0D
            / (RobotInfo.MAX_VELOCITY + RobotInfo.MAX_VELOCITY_REV + 1));
      int charDHead = Utils.round((deltaHeading + RobotInfo.MAX_TURN_RATE) * 63.0D
            / (2.0D * RobotInfo.MAX_TURN_RATE + 1));
      int charAccel = 0; // Utils.round((acceleration + 8.0D));
      this.symbol = (char) (charAccel << 12 | charVel << 7 | charDHead);
   }

   public double getDeltaHeading() {
      return deltaHeading;
   }

   public double getVelocity() {
      return velocity;
   }

   @Override
   public char getFold() {
      return symbol;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Polar) {
         Polar p = (Polar) obj;
         return symbol == p.symbol;
      }
      return false;
   }

}
