package kid.competition;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Hashtable;

import kid.graphics.Colors;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.utils.Utils;
import robocode.AdvancedRobot;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.DeathEvent;
import robocode.HitByBulletEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;
import robocode.WinEvent;

public class Chuck extends AdvancedRobot {

   Hashtable<String, Robot> robots;
   Robot enemy;

   private RoundRectangle2D battleField;
   Point2D.Double safePoint;

   @Override
   public void run() {
      setColors(Colors.BLACK, Colors.DIRT_GREEN, Colors.GREEN);
      setAdjustGunForRobotTurn(true);
      setAdjustRadarForGunTurn(true);

      robots = new Hashtable<String, Robot>(getOthers());

      battleField = new RoundRectangle2D.Double(40, 40, getBattleFieldWidth() - 80, getBattleFieldHeight() - 80, 100, 100);
      safePoint = new Point2D.Double(getBattleFieldWidth() / 2.0, getBattleFieldHeight() / 2.0);

      while (true) {
         setTurnRadarRight(Double.POSITIVE_INFINITY);
         if (enemy != null && enemy.getEnergy() >= 0.0 && getTime() - enemy.getTime() < 10) {
            double angle = Utils.angle(getX(), getY(), enemy.getX(), enemy.getY());

            setTurnGunRight(Utils.relative(angle - getGunHeading()));
            setFire(1.5);
            if (getOthers() == 1) {
               double theta = angle - getRadarHeading();
               theta += Utils.sign(theta) * 20.0;
               setTurnRadarRight(Utils.relative(theta));
            }
         }

         double pointDist = Utils.distSq(safePoint, getX(), getY());
         double pointRisk = Double.POSITIVE_INFINITY;
         if (pointDist > Utils.sqr(20)) {
            pointRisk = getRisk(safePoint);
         }

         Point2D.Double temp;
         double dist = 100 + 100 * Math.random();
         for (int a = 0; a <= 360; a += Math.random() * 10) {
            temp = new Point2D.Double(getX() + Utils.sin(a) * dist, getY() + Utils.cos(a) * dist);

            if (battleField.contains(temp)) {
               double risk = getRisk(temp);
               if (risk < pointRisk) {
                  safePoint = temp;
                  pointRisk = risk;
               }
            }
         }

         double angle = Utils.angle(getX(), getY(), safePoint.getX(), safePoint.getY());
         setTurnRight(Utils.relative(angle - getHeading()));
         setAhead(safePoint.distance(getX(), getY()));

         execute();
      }
   }

   public double getRisk(Point2D p) {
      double pointRisk = 0.0D;
      for (String s : robots.keySet()) {
         Robot r = robots.get(s);
         if (r.getEnergy() >= 0.0) {
            double robotRisk = 100.0;
            robotRisk /= Utils.distSq(p, r.getX(), r.getY());
            pointRisk += robotRisk;
         }
      }

      pointRisk += getOthers() / Utils.distSq(p, getBattleFieldWidth() / 2, getBattleFieldHeight() / 2);
      pointRisk += 2.0 / Utils.distSq(p, getBattleFieldWidth(), getBattleFieldHeight());
      pointRisk += 2.0 / Utils.distSq(p, 0.0D, getBattleFieldHeight());
      pointRisk += 2.0 / Utils.distSq(p, 0.0D, 0.0D);
      pointRisk += 2.0 / Utils.distSq(p, getBattleFieldWidth(), 0.0D);

      return pointRisk;
   }

   @Override
   public void onPaint(Graphics2D graphics) {
      RGraphics grid = new RGraphics(graphics, this);
      DrawMenu.draw(grid);

      if (enemy != null) {
         grid.setColor(Colors.CYAN);
         grid.drawRectCenter(enemy.getX(), enemy.getY(), 36, 36);
      }
   }

   @Override
   public void onScannedRobot(ScannedRobotEvent e) {
      double angle = getHeading() + e.getBearing();
      double x = getX() + Utils.sin(angle) * e.getDistance();
      double y = getY() + Utils.cos(angle) * e.getDistance();
      if (robots.containsKey(e.getName())) {
         robots.get(e.getName()).update(x, y, e.getHeading(), e.getVelocity(), e.getEnergy(), e.getTime());
      } else {
         robots.put(e.getName(), new Robot(x, y, e.getHeading(), e.getVelocity(), e.getEnergy(), e.getTime()));
      }

      if (enemy == null || enemy.getEnergy() < 0.0 || Utils.sqr(e.getDistance() * 1.2) < Utils.distSq(getX(), getY(), enemy.getX(), enemy.getY()))
         enemy = robots.get(e.getName());
   }

   @Override
   public void onRobotDeath(RobotDeathEvent e) {
      Robot r = robots.get(e.getName());
      r.update(r.getX(), r.getY(), r.getHeading(), r.getVelocity(), -1.0, e.getTime());
   }

   @Override
   public void onHitByBullet(HitByBulletEvent e) {
   }

   @Override
   public void onBulletHit(BulletHitEvent e) {
   }

   @Override
   public void onBulletHitBullet(BulletHitBulletEvent e) {
   }

   @Override
   public void onWin(WinEvent e) {
   }

   @Override
   public void onDeath(DeathEvent e) {
      for (String s : robots.keySet()) {
         Robot r = robots.get(s);
         r.update(r.getX(), r.getY(), r.getHeading(), r.getVelocity(), -1.0, e.getTime());
      }
   }

   @Override
   public void onMouseClicked(MouseEvent e) {
      DrawMenu.inMouseEvent(e);
   }

   @Override
   public void onSkippedTurn(SkippedTurnEvent e) {
      out.println("SKIPPED TURN! " + e.getTime());
   }

   private class Robot extends Point2D.Double {
      private static final long serialVersionUID = -9057114013063977860L;
      
      private double heading;
      private double velocity;
      private double energy;
      private long time;

      public Robot(double x, double y, double h, double v, double e, long t) {
         super(x, y);
         heading = h;
         velocity = v;
         energy = e;
         time = t;
      }

      public void update(double x, double y, double h, double v, double e, long t) {
         super.setLocation(x, y);
         heading = h;
         velocity = v;
         energy = e;
         time = t;
      }

      public double getHeading() {
         return heading;
      }

      public double getVelocity() {
         return velocity;
      }

      public double getEnergy() {
         return energy;
      }

      public long getTime() {
         return time;
      }
   }

}