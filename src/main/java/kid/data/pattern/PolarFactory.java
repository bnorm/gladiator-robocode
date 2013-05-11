package kid.data.pattern;

import kid.robot.RobotData;
import robocode.Robot;

public class PolarFactory implements PatternFactory<Polar> {

   @Override
   public Polar getNullPattern() {
      return Polar.DUMMY_PATTERN;
   }

   @Override
   public Polar getPattern(RobotData view, Robot reference) {
      return new Polar(view.getDeltaHeading(), view.getVelocity(), view.getDeltaVelocity());
   }

}
