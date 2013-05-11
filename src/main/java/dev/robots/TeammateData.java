package dev.robots;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class TeammateData extends RobotData {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this
    * class is not compatible with old versions.
    */
   private static final long serialVersionUID = 2826387746220645438L;


   public TeammateData() {
      super();
   }

   public TeammateData(String n, double x, double y, double e, double h, double v, long t) {
      super(n, x, y, e, h, v, t);
   }

   public TeammateData(ScannedRobotEvent e, Robot r) {
      super(e, r);
   }

   public TeammateData(Robot r) {
      super(r);
   }

   protected TeammateData(TeammateData t) {
      super(t);
   }

}
