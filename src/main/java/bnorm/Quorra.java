package main.java.bnorm;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import bnorm.draw.DrawMenu;
import robocode.AdvancedRobot;
import robocode.DeathEvent;
import robocode.WinEvent;

public class Quorra extends AdvancedRobot {

   public static final String DRAW_MENU = "menu.draw";

   @Override
   public void run() {
      DrawMenu.load(getDataFile(DRAW_MENU));
      setColors(Color.BLACK, Color.BLACK, Color.CYAN, Color.CYAN, Color.CYAN);

      do {
         setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
         setFireBullet(3.0);
         execute();
      } while (true);
   }

   @Override
   public void onPaint(Graphics2D g) {
      DrawMenu.draw(g);
   }

   @Override
   public void onMouseClicked(MouseEvent e) {
      DrawMenu.inMouseEvent(e);
   }

   @Override
   public void onDeath(DeathEvent event) {
      DrawMenu.save(getDataFile(DRAW_MENU));
   }

   @Override
   public void onWin(WinEvent event) {
      DrawMenu.save(getDataFile(DRAW_MENU));
   }

}
