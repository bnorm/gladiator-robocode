package kid.drones;

import java.io.IOException;

import robocode.MessageEvent;
import robocode.TeamRobot;

public class Drone_Tester extends TeamRobot {

   public void run() {
      while (true) {
         try {
            this.broadcastMessage("" + getTime());
         } catch (IOException e) {
            e.printStackTrace();
         }
         execute();
      }
   }

   public void onMessageReceived(MessageEvent me) {
      out.println("Got Message (" + getTime() + ") : " + (String) me.getMessage());
   }

}