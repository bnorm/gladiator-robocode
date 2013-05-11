package kid.data.pattern;

import kid.robot.RobotData;
import robocode.Robot;

public interface PatternFactory<E extends Pattern> {

   public E getPattern(RobotData view, Robot reference);

   public E getNullPattern();

}
