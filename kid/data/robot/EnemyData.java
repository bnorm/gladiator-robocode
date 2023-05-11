package kid.data.robot;

import java.awt.Color;

import kid.*;
import kid.data.info.RobotInfo;
import kid.messages.ScannedRobotMessage;
import robocode.*;

// TODO document class

public class EnemyData extends RobotData {

   private static final long serialVersionUID = 5687706098613243802L;

   public EnemyData() {
      super();
   }

   public EnemyData(EnemyData enemy) {
      super(enemy);
   }

   public EnemyData(ScannedRobotEvent sre, Robot myRobot) {
      super(sre, myRobot);
   }

   public EnemyData(ScannedRobotMessage srm, Robot myRobot) {
      super(srm, myRobot);
   }

   public EnemyData(Robot robot) {
      super(robot);
   }

   @Override
   public EnemyData copy() {
      return new EnemyData(this);
   }

   public void draw(RobocodeGraphicsDrawer grid, String commandString) {
      if (!getAlias().equals(DEAD)) {
         float hue = (float) (Utils.ONE_THIRD - (Math.min(getEnergy(), RobotInfo.START_ENERGY) / RobotInfo.START_ENERGY) * Utils.ONE_THIRD);
         Color energyColor = Color.getHSBColor(hue, 1.0F, 0.5F);
         grid.setColor(energyColor);
         grid.drawOvalCenter(getX(), getY(), RobotInfo.WIDTH, RobotInfo.HEIGHT);
         grid.drawOvalCenter(getX(), getY(), RobotInfo.WIDTH - 1.0D, RobotInfo.HEIGHT - 1.0D);
         grid.drawOvalCenter(getX(), getY(), RobotInfo.WIDTH - 2.0D, RobotInfo.HEIGHT - 2.0D);
      }
   }

}
