package kid.managers;

import java.util.*;

import kid.*;
import kid.data.Drawable;
import kid.data.virtual.VirtualBullet;
import kid.messages.*;
import robocode.*;

public class TeamManager implements Drawable {

   private TeamRobot robot;

   private static HashMap<String, List<VirtualBullet>> bulletTable = null;

   private long updateBulletTime;
   private List<VirtualBullet> bullets;


   public TeamManager(TeamRobot myRobot) {
      init(myRobot);
   }

   private void init(TeamRobot myRobot) {
      this.robot = myRobot;
      this.bullets = new LinkedList<VirtualBullet>();
      this.updateBulletTime = -1;
      if (bulletTable == null)
         bulletTable = new HashMap<String, List<VirtualBullet>>(myRobot.getOthers());
      else {
         for (String s : bulletTable.keySet())
            bulletTable.get(s).clear();
      }
   }

   public void inEvent(Event e) {
      if (e instanceof ScannedRobotEvent)
         handleScannedRobot((ScannedRobotEvent) e);
      else if (e instanceof MessageEvent)
         inMessage((MessageEvent) e);
   }

   private void handleScannedRobot(ScannedRobotEvent event) {
      if (!bulletTable.containsKey(event.getName())) {
         bulletTable.put(event.getName(), new LinkedList<VirtualBullet>());
      }
   }

   public VirtualBullet[] getTeammateBullets() {
      return bullets.toArray(new VirtualBullet[0]);
   }

   public VirtualBullet[] getTeammatesBullets(String name) {
      VirtualBullet[] vbullets = new VirtualBullet[0];
      if (bulletTable.containsKey(name))
         vbullets = bulletTable.get(name).toArray(vbullets);
      return vbullets;
   }

   public void inMessage(MessageEvent me) {
      if (me.getMessage() instanceof Message) {
         Message m = (Message) me.getMessage();
         if (m instanceof BulletMessage) {
            BulletMessage bulletMessage = (BulletMessage) m;
            VirtualBullet[] vbullets = bulletMessage.getVirtualBullets();
            List<VirtualBullet> list;
            if (!bulletTable.containsKey(me.getSender()))
               bulletTable.put(me.getSender(), list = new LinkedList<VirtualBullet>());
            else {
               (list = bulletTable.get(me.getSender())).clear();
            }

            if (m.getTime() != updateBulletTime) {
               bullets.clear();
               updateBulletTime = m.getTime();
            }

            for (VirtualBullet b : vbullets) {
               list.add(b);
               bullets.add(b);
            }
         }
      }
   }

   @Override
   public void draw(RobocodeGraphicsDrawer grid, String commandString) {
      long time = robot.getTime();
      grid.setColor(Colors.RED);
      for (VirtualBullet b : bullets)
         grid.drawOvalCenter(b.getX(time), b.getY(time), 10.0D, 10.0D);
   }

}
