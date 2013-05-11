package kid.management;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import kid.communication.BulletMessage;
import kid.data.Drawable;
import kid.data.Printable;
import kid.graphics.RGraphics;
import kid.robot.RobotData;
import kid.targeting.Targeting;
import kid.virtual.VirtualBullet;
import kid.virtual.VirtualGun;
import robocode.AdvancedRobot;
import robocode.Bullet;
import robocode.Condition;
import robocode.Event;
import robocode.RobocodeFileOutputStream;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

// TODO documentation: class

public class TargetingManager implements Drawable, Printable {

   private AdvancedRobot robot;
   private RobotManager robots;

   private RobotData target;

   /**
    * A HashMap of Lists that store all the virtual guns for a given robot.
    */
   private static HashMap<String, List<VirtualGun>> virtualGunTable = null;

   /**
    * All the preliminary targeting types. Used to add virtual guns to enemy robots that have been
    * scanned after the class has been created.
    */
   private static List<Targeting> targetings = null;

   private List<Bullet> bullets;

   public TargetingManager(final AdvancedRobot myRobot) {
      init(myRobot);
   }

   public TargetingManager(final AdvancedRobot myRobot, final Targeting[] guns) {
      init(myRobot);
      if (targetings == null) {
         targetings = new LinkedList<Targeting>();
         for (Targeting element : guns)
            targetings.add(element);
      }
   }

   private void init(final AdvancedRobot r) {
      this.robot = r;
      this.robots = new RobotManager(r);
      this.target = new RobotData();
      // this.info = new GunInfo(myRobot);
      bullets = new ArrayList<Bullet>();
      r.addCustomEvent(new BulletWatcher(r.getTime()));
      if (virtualGunTable == null)
         virtualGunTable = new HashMap<String, List<VirtualGun>>(r.getOthers());
      else {
         Set<String> keys = virtualGunTable.keySet();
         for (String s : keys) {
            List<VirtualGun> guns = virtualGunTable.get(s);
            for (VirtualGun g : guns) {
               g.update(robot);
            }
         }
      }
   }

   public void add(final Targeting[] newTargetings) {
      if (newTargetings != null) {
         RobotData[] robots = this.robots.getRobots();
         for (RobotData r : robots) {
            List<VirtualGun> guns = virtualGunTable.get(r.getName());
            for (Targeting t : newTargetings)
               guns.add(new VirtualGun(robot, r, t));
         }
         for (Targeting t : newTargetings)
            targetings.add(t);
      }
   }

   public void add(final Targeting t) {
      if (t != null) {
         RobotData[] robots = this.robots.getRobots();
         for (RobotData r : robots)
            virtualGunTable.get(r.getName()).add(new VirtualGun(robot, r, t));
         targetings.add(t);
      }
   }

   public void inEvent(final Event e) {
      robots.inEvent(e);
      if (e instanceof ScannedRobotEvent)
         handleScannedRobot((ScannedRobotEvent) e);
   }

   private void handleScannedRobot(final ScannedRobotEvent event) {
      if (!virtualGunTable.containsKey(event.getName())) {
         virtualGunTable.put(event.getName(), new LinkedList<VirtualGun>());
         if (targetings != null) {
            List<VirtualGun> guns = virtualGunTable.get(event.getName());
            for (Targeting t : targetings)
               guns.add(new VirtualGun(robot, robots.getRobot(event.getName()), t));
         }
      }
   }

   public Bullet fire(final RobotData target, final double firePower) {
      return fire(target, getBestGun(target).getTargeting(), firePower);
   }

   public Bullet fire(final RobotData target, final Targeting targeting, final double firePower) {
      Bullet bullet = robot.setFireBullet(firePower);
      if (bullet != null) {
         bullets.add(bullet);
         if (virtualGunTable.containsKey(target.getName())) {
            ListIterator<VirtualGun> iterator = virtualGunTable.get(target.getName()).listIterator();
            while (iterator.hasNext()) {
               VirtualGun gun = iterator.next();
               gun.fire(bullet.getPower());
               if (gun.getTargeting() == targeting)
                  gun.fire(bullet);
            }
         }
      }
      return bullet;
   }

   public VirtualBullet[] getBullets() {
      VirtualBullet[] vbullets = new VirtualBullet[bullets.size()];
      long time = robot.getTime();
      for (int i = 0; i < vbullets.length; i++)
         vbullets[i] = new VirtualBullet(bullets.get(i), time);
      return vbullets;
   }

   public void broadcastBullets() {
      if (robot instanceof TeamRobot) {
         TeamRobot teamrobot = (TeamRobot) robot;
         try {
            teamrobot.broadcastMessage(new BulletMessage(getBullets(), robot));
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   public VirtualGun getBestGun(final RobotData target) {
      VirtualGun bestGun = new VirtualGun(robot);
      List<VirtualGun> virtualGuns = virtualGunTable.get(target.getName());
      if (virtualGuns != null) {
         double bestHitRate = bestGun.getHitRate();
         for (VirtualGun gun : virtualGuns) {
            double hitRate = gun.getHitRate();
            if (hitRate > bestHitRate) {
               bestGun = gun;
               bestHitRate = hitRate;
            }
         }
      }
      return bestGun;
   }

   public VirtualGun[] getVirtualGuns(final RobotData target) {
      VirtualGun[] guns = new VirtualGun[0];
      if (virtualGunTable.containsKey(target.getName()))
         guns = virtualGunTable.get(target.getName()).toArray(guns);
      return guns;
   }

   @Override
   public void draw(final RGraphics grid) {
      for (RobotData r : robots.getRobots())
         for (VirtualGun g : getVirtualGuns(r))
            g.draw(grid);
      for (VirtualGun g : getVirtualGuns(target))
         g.drawGunStats(grid);
   }

   @Override
   public void print(PrintStream console) {
      for (RobotData r : robots.getRobots())
         for (VirtualGun g : getVirtualGuns(r))
            g.print(console);
   }

   @Override
   public void print(RobocodeFileOutputStream output) {
      for (RobotData r : robots.getRobots())
         for (VirtualGun g : getVirtualGuns(r))
            g.print(output);
   }

   private class BulletWatcher extends Condition {

      private long time;

      public BulletWatcher(final long curTime) {
         this.time = curTime;
      }

      @Override
      public boolean test() {
         for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if (!b.isActive())
               bullets.remove(i--);
         }
         time++;
         return false;
      }
   }

}
