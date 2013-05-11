package kid.drones;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import robocode.AdvancedRobot;

public class Tester extends AdvancedRobot {

   @Override
   public void run() {

      out.println(getGunTurnRemaining());
      out.println(getGunHeading());
      setTurnGunRight(360);
      out.println(getGunTurnRemaining());
      out.println(getGunHeading());
      execute();
      out.println(getGunTurnRemaining());
      out.println(getGunHeading());
      while (true) {

         execute();
      }
   }

   @Override
   public void onPaint(final Graphics2D g) {
      DrawMenu.draw(new RGraphics(g, this));
   }

   @Override
   public void onMouseClicked(final MouseEvent e) {
      DrawMenu.inMouseEvent(e);
   }

}