package kid.data.virtual;

import java.io.*;
import java.util.*;

import kid.*;
import kid.data.robot.RobotData;
import kid.targeting.*;

import robocode.*;

// TODO documentation: class

public class VirtualGun extends VirtualObject {

   private static final long serialVersionUID = 1715236387787837088L;

   private Targeting targeting;
   private RobotData target;

   private transient List<VirtualBullet> virtualBullets;
   private transient List<Bullet> realBullets;

   private double virtualFired;
   private double virtualHit;

   private double realFired;
   private double realHit;

   public VirtualGun(AdvancedRobot myRobot) {
      init(myRobot, new RobotData(), new HeadOnTargeting(myRobot));
   }

   public VirtualGun(AdvancedRobot myRobot, RobotData target, Targeting targeting) {
      init(myRobot, target, targeting);
   }

   // TODO code: constructor
   private VirtualGun(VirtualGun gun) {
   }

   private void init(AdvancedRobot myRobot, RobotData target, Targeting targeting) {
      if (myRobot != null)
         myRobot.addCustomEvent(new BulletWatcher(myRobot.getTime()));
      this.target = target;
      this.targeting = targeting;
      this.virtualBullets = new ArrayList<VirtualBullet>();
      this.realBullets = new ArrayList<Bullet>();
      this.virtualFired = 0.0D;
      this.virtualHit = 0.0D;
      this.realFired = 0.0D;
      this.realHit = 0.0D;
   }

   public void update(AdvancedRobot myRobot) {
      if (myRobot != null)
         myRobot.addCustomEvent(new BulletWatcher(myRobot.getTime()));
      this.virtualBullets = new ArrayList<VirtualBullet>();
      this.realBullets = new ArrayList<Bullet>();
   }

   public void fire(double firePower) {
      virtualBullets.add(targeting.getBullet(target, firePower));
   }

   public void fire(Bullet bullet, long curTime) {
      if (bullet != null) {
         realBullets.add(bullet);
      }
   }

   public double getVirtualHitRate() {
      return virtualHit / virtualFired;
   }

   public double getRealHitRate() {
      return realHit / realFired;
   }

   public double getCombinedHitRate() {
      return Utils.weightedAvg(getVirtualHitRate(), virtualFired, getRealHitRate(), 2.0D * realFired);
   }

   public Targeting getTargeting() {
      return targeting;
   }

   public void print(PrintStream console) {
      console.println("Virtual Gun for: " + target.getName() + " (" + targeting.getName() + ")");
      console.println("   Virtual:  (" + virtualHit + "/" + virtualFired + ") " + getVirtualHitRate());
      console.println("   Real:     (" + realHit + "/" + realFired + ") " + getRealHitRate());
      console.println("   Compined: " + getCombinedHitRate());
   }

   // TODO code: method
   public void print(RobocodeFileOutputStream output) {
   }

   public void draw(RobocodeGraphicsDrawer grid, String commandString) {
      grid.setColor(targeting.getColor());
      for (VirtualBullet vb : virtualBullets)
         vb.draw(grid, commandString);
      for (Bullet b : realBullets)
         grid.drawOvalCenter(b.getX(), b.getY(), 10.0D, 10.0D);
   }

   public Object clone() {
      return new VirtualGun(this);
   }

   // TODO code: method
   public boolean equals(Object obj) {
      return false;
   }

   // TODO code: method
   public String toString() {
      return new String();
   }

   public void finalize() {
      targeting = null;
      target = null;
      virtualBullets = null;
      virtualFired = 0.0D;
      virtualHit = 0.0D;
      realBullets = null;
      realFired = 0.0D;
      realHit = 0.0D;
   }

   private class BulletWatcher extends Condition {

      private long time;

      public BulletWatcher(long curTime) {
         init(curTime);
      }

      private void init(long curTime) {
         this.time = curTime;
      }

      public boolean test() {
         for (int i = 0; i < virtualBullets.size(); i++) {
            VirtualBullet b = virtualBullets.get(i);
            if (b.testHit(target.getRectangle(), time)) {
               virtualHit++;
               virtualFired++;
               virtualBullets.remove(i);
               i--;
            } else if (!b.testActive(time) || b.testMissed(target.getRectangle(), time - 5)) {
               virtualFired++;
               virtualBullets.remove(i);
               i--;
            }
         }
         if (virtualFired > 1000.0D) {
            virtualHit /= 2.0D;
            virtualFired /= 2.0D;
         }
         for (int i = 0; i < realBullets.size(); i++) {
            Bullet b = realBullets.get(i);
            if (!b.isActive()) {
               if (b.getVictim() != null && b.getVictim().equals(target.getName()))
                  realHit++;
               realFired++;
               realBullets.remove(i);
               i--;
            }
         }
         if (realFired > 1000.0D) {
            realHit /= 2.0D;
            realFired /= 2.0D;
         }
         time++;
         return false;
      }
   }

}