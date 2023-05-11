package kid.managers;

import java.util.*;

import kid.*;
import kid.data.Drawable;
import kid.data.robot.RobotData;
import kid.data.virtual.VirtualGun;
import kid.targeting.Targeting;
import robocode.*;

// TODO documentation: class

public class TargetingManager implements Drawable {

   private AdvancedRobot robot;
   private RobotManager robots;

   /**
    * A HashMap of Lists that store all the virtual guns for a given robot.
    */
   private static HashMap<String, List<VirtualGun>> virtualGunTable = null;

   /**
    * All the preliminary targeting types. Used to add virtual guns to enemy robots that have been scanned after the
    * class has been created.
    */
   private static List<Targeting> targetings = null;

   public TargetingManager(AdvancedRobot myRobot) {
      init(myRobot);
   }

   public TargetingManager(AdvancedRobot myRobot, Targeting[] guns) {
      init(myRobot);
      if (targetings == null) {
         targetings = new LinkedList<Targeting>();
         for (int i = 0; i < guns.length; i++)
            targetings.add(guns[i]);
      }
   }

   private void init(AdvancedRobot myRobot) {
      this.robot = myRobot;
      this.robots = new RobotManager(myRobot);
      if (virtualGunTable == null)
         virtualGunTable = new HashMap<String, List<VirtualGun>>(myRobot.getOthers());
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

   public void add(Targeting[] newTargetings) {
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

   public void add(Targeting t) {
      if (t != null) {
         RobotData[] robots = this.robots.getRobots();
         for (RobotData r : robots) {
            virtualGunTable.get(r.getName()).add(new VirtualGun(robot, r, t));
         }
         targetings.add(t);
      }
   }

   public void inEvent(Event e) {
      robots.inEvent(e);
      if (e instanceof ScannedRobotEvent)
         handleScannedRobot((ScannedRobotEvent) e);
   }

   private void handleScannedRobot(ScannedRobotEvent event) {
      if (!virtualGunTable.containsKey(event.getName())) {
         virtualGunTable.put(event.getName(), new LinkedList<VirtualGun>());
         if (targetings != null) {
            List<VirtualGun> guns = virtualGunTable.get(event.getName());
            for (Targeting t : targetings)
               guns.add(new VirtualGun(robot, robots.getRobot(event.getName()), t));
         }
      }
   }

   public Bullet fire(RobotData target, Targeting targeting, double firePower) {
      robot.setTurnGunRight(Utils.relative(targeting.getAngle(target, 2) - robot.getGunHeading()));
      Bullet bullet = robot.setFireBullet(firePower);
      if (bullet != null) {
         List<VirtualGun> virtualGuns = virtualGunTable.get(target.getName());
         if (virtualGuns != null) {
            ListIterator<VirtualGun> iterator = virtualGuns.listIterator();
            while (iterator.hasNext()) {
               VirtualGun gun = iterator.next();
               gun.fire(bullet.getPower());
               if (gun.getTargeting().equals(targeting))
                  gun.fire(bullet, robot.getTime());
            }
         }
      }
      return bullet;
   }

   public VirtualGun getBestGun(RobotData target) {
      VirtualGun bestGun = new VirtualGun(robot);
      List<VirtualGun> virtualGuns = virtualGunTable.get(target.getName());
      if (virtualGuns != null) {
         double bestHitRate = getHitRate(bestGun);
         for (VirtualGun gun : virtualGuns) {
            double hitRate = getHitRate(gun);
            if (hitRate > bestHitRate) {
               bestGun = gun;
               bestHitRate = hitRate;
            }
         }
      }
      return bestGun;
   }

   public VirtualGun[] getVirtualGuns(RobotData target) {
      VirtualGun[] guns = new VirtualGun[0];
      if (virtualGunTable.containsKey(target.getName()))
         guns = virtualGunTable.get(target.getName()).toArray(guns);
      return guns;
   }

   public double getHitRate(VirtualGun gun) {
      if (gun.getRealHitRate() >= 0.0D)
         return gun.getCombinedHitRate();
      else if (gun.getVirtualHitRate() >= 0.0D)
         return gun.getVirtualHitRate();
      else
         return -1.0D;
   }

   @Override
   public void draw(RobocodeGraphicsDrawer grid, String commandString) {
      for (RobotData r : robots.getRobots())
         for (VirtualGun g : getVirtualGuns(r))
            g.draw(grid, commandString);
   }

}