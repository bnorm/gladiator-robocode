package kid.twins;

import java.util.HashMap;

import robocode.Event;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

public class BotManager {

   public static HashMap<String, Bot> BOTS;

   static {
      BOTS = new HashMap<String, Bot>();
   }

   public static void onEvent(Event e, TeamRobot r) {
      try {
         ScannedRobotEvent sre = (ScannedRobotEvent) e;
         Bot b = BOTS.get(sre.getName());
         if (b == null)
            BOTS.put(sre.getName(), b = new Bot());
         double angleRad = sre.getBearingRadians() + (b.HEADING = sre.getHeadingRadians());
         b.setLocation(r.getX() + sre.getDistance() * Math.sin(angleRad), r.getY() + sre.getDistance() * Math.cos(angleRad));
         b.NAME = sre.getName();
         b.ENERGY = sre.getEnergy();
         b.VELOCITY = sre.getVelocity();
         b.TIME = sre.getTime();
      } catch (Exception ex) {
         RobotDeathEvent rde = (RobotDeathEvent) e;
         BOTS.get(rde.getName()).ENERGY = -1;
      }
   }

}
