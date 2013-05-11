package dev.robots;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class EnemyData extends RobotData {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this
    * class is not compatible with old versions.
    */
   private static final long serialVersionUID = 6175127557378950453L;


   public EnemyData() {
      super();
   }

   public EnemyData(String n, double x, double y, double e, double h, double v, long t) {
      super(n, x, y, e, h, v, t);
   }

   public EnemyData(ScannedRobotEvent e, Robot r) {
      super(e, r);
   }

   protected EnemyData(EnemyData e) {
      super(e);
   }

}
