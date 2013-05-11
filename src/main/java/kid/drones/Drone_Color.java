package kid.drones;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import kid.utils.Utils;
import robocode.AdvancedRobot;

public class Drone_Color extends AdvancedRobot {

   private double bodyH = 1.0D;
   private double bodyS = 1.0D;
   private double bodyB = 1.0D;

   private double gunH = 1.0D;
   private double gunS = 1.0D;
   private double gunB = 1.0D;

   private double radarH = 1.0D;
   private double radarS = 1.0D;
   private double radarB = 1.0D;

   private Color body;
   private Color gun;
   private Color radar;

   @Override
   public void run() {

      setAdjustGunForRobotTurn(true);
      setAdjustRadarForGunTurn(true);

      setTurnRight(Utils.relative(Utils.QUARTER_CIRCLE - getHeading()));
      setTurnGunRight(Utils.relative(-getGunHeading()));
      setTurnRadarRight(Utils.relative(-getRadarHeading()));

      body = getHSBColor(bodyH, bodyS, bodyB);
      gun = getHSBColor(gunH, gunS, gunB);
      radar = getHSBColor(radarH, radarS, radarB);

      while (true) {
         setBodyColor(body);
         setGunColor(gun);
         setRadarColor(radar);

         out.println("Body: " + body.getRed() + ", " + body.getGreen() + ", " + body.getBlue());
         out.println("Gun: " + gun.getRed() + ", " + gun.getGreen() + ", " + gun.getBlue());
         out.println("Radar: " + radar.getRed() + ", " + radar.getGreen() + ", " + radar.getBlue());

         execute();
      }
   }

   @Override
   public void onPaint(final Graphics2D grid) {
      double height = getBattleFieldHeight();

      grid.setColor(Color.WHITE);
      grid.drawRect(20, 0, 10, (int) height);
      grid.drawRect(50, 0, 10, (int) height);
      grid.drawRect(80, 0, 10, (int) height);
      grid.drawRect(150, 0, 10, (int) height);
      grid.drawRect(180, 0, 10, (int) height);
      grid.drawRect(210, 0, 10, (int) height);
      grid.drawRect(280, 0, 10, (int) height);
      grid.drawRect(310, 0, 10, (int) height);
      grid.drawRect(340, 0, 10, (int) height);
      for (int i = 0; i <= height; i++) {
         grid.setColor(getHSBColor(i / height, 1.0, 1.0));
         grid.drawLine(21, i, 29, i);
         grid.drawLine(151, i, 159, i);
         grid.drawLine(281, i, 289, i);
         grid.setColor(getHSBColor(bodyH, i / height, 1.0));
         grid.drawLine(51, i, 59, i);
         grid.setColor(getHSBColor(bodyH, bodyS, i / height));
         grid.drawLine(81, i, 89, i);
         grid.setColor(getHSBColor(gunH, i / height, 1.0));
         grid.drawLine(181, i, 189, i);
         grid.setColor(getHSBColor(gunH, gunS, i / height));
         grid.drawLine(211, i, 219, i);
         grid.setColor(getHSBColor(radarH, i / height, 1.0));
         grid.drawLine(311, i, 319, i);
         grid.setColor(getHSBColor(radarH, radarS, i / height));
         grid.drawLine(341, i, 349, i);
      }
   }

   @Override
   public void onMousePressed(final MouseEvent e) {
      handleMouseEvent(e);
   }

   @Override
   public void onMouseDragged(final MouseEvent e) {
      handleMouseEvent(e);
   }

   private void handleMouseEvent(final MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1) {
         double x = e.getX();
         double y = e.getY();

         if (Utils.between(20, x, 30)) {
            bodyH = y / getBattleFieldHeight();
            body = getHSBColor(bodyH, bodyS, bodyB);
         } else if (Utils.between(50, x, 60)) {
            bodyS = y / getBattleFieldHeight();
            body = getHSBColor(bodyH, bodyS, bodyB);
         } else if (Utils.between(80, x, 90)) {
            bodyB = y / getBattleFieldHeight();
            body = getHSBColor(bodyH, bodyS, bodyB);
         } else if (Utils.between(150, x, 160)) {
            gunH = y / getBattleFieldHeight();
            gun = getHSBColor(gunH, gunS, gunB);
         } else if (Utils.between(180, x, 190)) {
            gunS = y / getBattleFieldHeight();
            gun = getHSBColor(gunH, gunS, gunB);
         } else if (Utils.between(210, x, 220)) {
            gunB = y / getBattleFieldHeight();
            gun = getHSBColor(gunH, gunS, gunB);
         } else if (Utils.between(280, x, 290)) {
            radarH = y / getBattleFieldHeight();
            radar = getHSBColor(radarH, radarS, radarB);
         } else if (Utils.between(310, x, 320)) {
            radarS = y / getBattleFieldHeight();
            radar = getHSBColor(radarH, radarS, radarB);
         } else if (Utils.between(340, x, 350)) {
            radarB = y / getBattleFieldHeight();
            radar = getHSBColor(radarH, radarS, radarB);
         }
      }
   }

   public static final Color getHSBColor(final double h, final double s, final double b) {
      return Color.getHSBColor((float) h, (float) s, (float) b);
   }

}