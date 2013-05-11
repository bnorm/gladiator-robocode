package kid.virtual;

import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.robot.RobotData;
import kid.targeting.HeadOnTargeting;
import kid.targeting.Targeting;
import kid.utils.Utils;
import robocode.AdvancedRobot;
import robocode.Bullet;
import robocode.Condition;
import robocode.RobocodeFileOutputStream;

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

   public VirtualGun(final AdvancedRobot myRobot) {
      init(myRobot, new RobotData(), new HeadOnTargeting(myRobot));
   }

   public VirtualGun(final AdvancedRobot myRobot, final RobotData target, final Targeting targeting) {
      init(myRobot, target, targeting);
   }

   private VirtualGun(final VirtualGun gun) {
      init(null, gun.getTarget(), gun.getTargeting());
   }

   private void init(final AdvancedRobot myRobot, final RobotData target, final Targeting targeting) {
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

   public void update(final AdvancedRobot myRobot) {
      if (myRobot != null)
         myRobot.addCustomEvent(new BulletWatcher(myRobot.getTime()));
      this.virtualBullets = new ArrayList<VirtualBullet>();
      this.realBullets = new ArrayList<Bullet>();
   }

   public void fire(final double firePower) {
      virtualBullets.add(targeting.getBullet(target, firePower));
   }

   public void fire(final Bullet bullet) {
      if (bullet != null)
         realBullets.add(bullet);
   }

   public Targeting getTargeting() {
      return targeting;
   }

   protected RobotData getTarget() {
      return target;
   }

   protected List<VirtualBullet> getVirtualBullets() {
      return virtualBullets;
   }

   protected List<Bullet> getRealBullets() {
      return realBullets;
   }

   public double getVirtualHitRate() {
      return virtualHit / virtualFired;
   }

   public double getRealHitRate() {
      return realHit / realFired;
   }

   // TODO documentation: method - getComdinedHitRate()
   public double getCombinedHitRate() {
      return Utils.weightedAvg(getVirtualHitRate(), virtualFired, getRealHitRate(), 2.0D * realFired);
   }

   // TODO documentation: method - getHitRate()
   public double getHitRate() {
      if (getRealHitRate() >= 0.0D)
         return getCombinedHitRate();
      else if (getVirtualHitRate() >= 0.0D)
         return getVirtualHitRate();
      else
         return -1.0D;
   }

   // TODO documentation: method - print(PrintStream)
   public void print(final PrintStream console) {
      console.println("Virtual Gun for: " + target.getName() + " (" + targeting.getName() + ")");
      console.println("   Virtual:  (" + virtualHit + "/" + virtualFired + ") " + getVirtualHitRate());
      console.println("   Real:     (" + realHit + "/" + realFired + ") " + getRealHitRate());
      console.println("   Compined: " + getCombinedHitRate());
   }

   // TODO code: method - print(RobocodeFileOutputStream)
   // TODO documentation: method - print(RobocodeFileOutputStream)
   public void print(final RobocodeFileOutputStream output) {
   }

   // TODO documentation: method - clone()
   @Override
   public Object clone() {
      return new VirtualGun(this);
   }

   // TODO documentation: method - equals(Object)
   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof VirtualGun) {
         VirtualGun gun = (VirtualGun) obj;
         return gun.getTarget().equals(target) && gun.getTargeting().equals(targeting);
      }
      return false;
   }

   // TODO documentation: method - toString()
   @Override
   public String toString() {
      return new String("Virtual Gun for: " + target.getName() + " (" + targeting.getName() + ") Virtual: " + getVirtualHitRate() + " Real: "
            + getRealHitRate() + " Compined: " + getCombinedHitRate());
   }

   @Override
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

      public BulletWatcher(final long curTime) {
         init(curTime);
      }

      private void init(final long curTime) {
         this.time = curTime;
      }

      @Override
      public boolean test() {
         for (int i = 0; i < virtualBullets.size(); i++) {
            VirtualBullet b = virtualBullets.get(i);
            if (b.testHit(target.getRectangle(), time)) {
               virtualHit++;
               virtualFired++;
               virtualBullets.remove(i--);
            } else if (!b.testActive(time) || b.testMissed(target.getRectangle(), time - 5)) {
               virtualFired++;
               virtualBullets.remove(i--);
            }
         }
         for (int i = 0; i < realBullets.size(); i++) {
            Bullet b = realBullets.get(i);
            if (!b.isActive()) {
               if (b.getVictim() != null && b.getVictim().equals(target.getName()))
                  realHit++;
               realFired++;
               realBullets.remove(i--);
            }
         }
         time++;
         return false;
      }
   }

   public void draw(final RGraphics grid) {
      grid.setColor(targeting.getColor());
      if (DrawMenu.getValue("Virtual Bullets", "Targeting")) {
         for (VirtualBullet vb : virtualBullets)
            vb.draw(grid);
      }
      if (DrawMenu.getValue("Real Bullets", "Targeting")) {
         for (Bullet b : realBullets)
            grid.drawOvalCenter(b.getX(), b.getY(), 10.0D, 10.0D);
      }
   }

   public void drawGunStats(final RGraphics grid) {
      if (DrawMenu.getValue("Gun Statistics", "Targeting")) {
         grid.setColor(targeting.getColor());
         double x = grid.getRobot().getX();
         double y = grid.getRobot().getY();
         double dist = 30.0D + Math.max(0.0D, 70.0D * getHitRate());
         if (!target.isDead()) {
            double angle = targeting.getAngle(target, 2.0D);
            grid.drawLine(x, y, Utils.getX(x, dist, angle), Utils.getY(y, dist, angle));
         }
         grid.drawOvalCenter(x, y, 2 * dist, 2 * dist);
      }
   }

   public void drawOptions(final RGraphics grid) {
      // TODO Auto-generated method stub

   }

   public void inMouseEvent(final MouseEvent e) {
      // TODO Auto-generated method stub

   }

}
