package dev;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

import robocode.AdvancedRobot;

public class Logger extends AdvancedRobot {

   @Override
   public void run() {
      try {
         LogManager.getLogManager().readConfiguration(new FileInputStream(getDataFile("logging.properties")));
      } catch (IOException e) {
         e.printStackTrace();
      }

      do {
         this.execute();
      } while (true);
   }

}
