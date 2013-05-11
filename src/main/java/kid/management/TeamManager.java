package kid.management;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import kid.communication.BulletMessage;
import kid.communication.Message;
import kid.data.Drawable;
import kid.graphics.Colors;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.virtual.VirtualBullet;
import robocode.Event;
import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

public class TeamManager implements Drawable {

   private TeamRobot robot;

   private static HashMap<String, List<VirtualBullet>> bulletTable = null;

   private long updateBulletTime;
   private List<VirtualBullet> bullets;

   public TeamManager(final TeamRobot myRobot) {
      init(myRobot);
   }

   private void init(final TeamRobot myRobot) {
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

   public void broadcast(final Serializable message) {
      try {
         robot.broadcastMessage(message);
      } catch (IOException e) {
      }
   }

   public void inEvent(final Event e) {
      if (e instanceof ScannedRobotEvent)
         handleScannedRobot((ScannedRobotEvent) e);
      else if (e instanceof MessageEvent)
         inMessage((MessageEvent) e);
   }

   private void handleScannedRobot(final ScannedRobotEvent event) {
      if (!bulletTable.containsKey(event.getName())) {
         bulletTable.put(event.getName(), new LinkedList<VirtualBullet>());
      }
   }

   public VirtualBullet[] getTeammateBullets() {
      return bullets.toArray(new VirtualBullet[0]);
   }

   public VirtualBullet[] getTeammatesBullets(final String name) {
      VirtualBullet[] vbullets = new VirtualBullet[0];
      if (bulletTable.containsKey(name))
         vbullets = bulletTable.get(name).toArray(vbullets);
      return vbullets;
   }

   public void inMessage(final MessageEvent me) {
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
   public void draw(final RGraphics grid) {
      if (DrawMenu.getValue("Team Bullets", "Team")) {
         long time = robot.getTime();
         grid.setColor(Colors.RED);
         for (VirtualBullet b : bullets)
            grid.drawOvalCenter(b.getX(time), b.getY(time), 10.0D, 10.0D);
      }
   }

}
