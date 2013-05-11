package kid.robot;

import java.awt.Color;

import kid.communication.ScannedRobotMessage;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.info.RobotInfo;
import kid.utils.Utils;
import robocode.Robot;
import robocode.ScannedRobotEvent;

// TODO document class

public class EnemyData extends RobotData {

   private static final long serialVersionUID = 5687706098613243802L;

   public EnemyData() {
      super();
   }

   public EnemyData(final EnemyData enemy) {
      super(enemy);
   }

   public EnemyData(final ScannedRobotEvent sre, final Robot myRobot) {
      super(sre, myRobot);
   }

   public EnemyData(final ScannedRobotMessage srm, final Robot myRobot) {
      super(srm, myRobot);
   }

   public EnemyData(final Robot robot) {
      super(robot);
   }

   @Override
   public EnemyData copy() {
      return new EnemyData(this);
   }

   @Override
   public void draw(final RGraphics grid) {
      if (isDead())
         return;
      if (DrawMenu.getValue("Energy Circle", "Robot")) {
         float hue = (float) (Utils.ONE_THIRD - (Math.min(getEnergy(), RobotInfo.START_ENERGY) / RobotInfo.START_ENERGY) * Utils.ONE_THIRD);
         Color energyColor = Color.getHSBColor(hue, 1.0F, 0.5F);
         grid.setColor(energyColor);
         grid.drawOvalCenter(getX(), getY(), RobotInfo.WIDTH, RobotInfo.HEIGHT);
         grid.drawOvalCenter(getX(), getY(), RobotInfo.WIDTH - 1.0D, RobotInfo.HEIGHT - 1.0D);
         grid.drawOvalCenter(getX(), getY(), RobotInfo.WIDTH - 2.0D, RobotInfo.HEIGHT - 2.0D);
      }
      super.draw(grid);
   }

}
