package bnorm.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.ListIterator;

import bnorm.robots.IRobot;
import bnorm.robots.IRobotSnapshot;
import bnorm.utils.Utils;

public class RobotDraw {

   private RobotDraw() {
   }

   public static void drawPath(Graphics2D g, IRobot robot) {
      if (!DrawMenu.getValue("Robots", "History", true)) {
         return;
      }

      drawPathUnchecked(g, robot);
   }

   public static void drawPaths(Graphics2D g, Collection<IRobot> robots) {
      if (!DrawMenu.getValue("Robots", "History", true)) {
         return;
      }

      for (IRobot r : robots) {
         drawPathUnchecked(g, r);
      }
   }

   private static void drawPathUnchecked(Graphics2D g, IRobot robot) {
      if (robot.getSnapshot().getEnergy() >= 0.0) {
         ListIterator<IRobotSnapshot> history = robot.getMovie(Long.MAX_VALUE);
         IRobotSnapshot newer = robot.getSnapshot();
         IRobotSnapshot older;
         long startTime = newer.getTime();
         while (history.hasPrevious() && startTime - (older = history.previous()).getTime() < 40) {
            double engDif = newer.getEnergy() - older.getEnergy();
            double norEngDif = 40 + 40 * Utils.signZ(engDif);

            g.setColor(Color.getHSBColor((float) (norEngDif / 240.0), 1.0F,
                                         0.25F + 0.75F * Math.abs(Utils.signZ(engDif))));

            g.fillOval((int) older.getX() - 2, (int) older.getY() - 2, 4, 4);
            newer = older;
         }
      }
   }

   public static void drawBox(Graphics2D g, IRobot robot) {
      if (!DrawMenu.getValue("Robots", "Box", true)) {
         return;
      }

      drawBoxUnchecked(g, robot);
   }

   public static void drawBoxes(Graphics2D g, Collection<IRobot> robots) {
      if (!DrawMenu.getValue("Robots", "Box", true)) {
         return;
      }

      for (IRobot r : robots) {
         drawBoxUnchecked(g, r);
      }
   }

   private static void drawBoxUnchecked(Graphics2D g, IRobot robot) {
      if (robot.getSnapshot().getEnergy() >= 0.0) {
         g.setColor(Color.GRAY);
         g.drawRect((int) robot.getSnapshot().getX() - 18, (int) robot.getSnapshot().getY() - 18, 36, 36);
      }
   }

}
