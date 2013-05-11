package bnorm.manage;

import java.io.IOException;

import bnorm.events.EventHandler;
import bnorm.messages.Message;
import bnorm.messages.RobotSnapshotMessage;
import bnorm.robots.IRobotSnapshotFactory;
import robocode.Event;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

// TODO document class
public class TeamManager implements EventHandler {

   private static TeamManager instance_;

   private TeamRobot robot_;

   private IRobotSnapshotFactory snapshotFactory_;

   public static TeamManager getInstance(TeamRobot robot, IRobotSnapshotFactory snapshotFactory) {
      if (instance_ == null) {
         instance_ = new TeamManager(robot, snapshotFactory);
      }
      return instance_;
   }

   public static TeamManager getInstance() {
      return instance_;
   }

   private TeamManager(TeamRobot robot, IRobotSnapshotFactory snapshotFactory) {
      this.robot_ = robot;
      this.snapshotFactory_ = snapshotFactory;
   }

   public void sendMe() {
      send(new RobotSnapshotMessage(snapshotFactory_.create(robot_), robot_.getName(), robot_.getTime()));
   }

   public void send(Message message) {
      if (robot_.getTeammates() != null) {
         for (String teammate : robot_.getTeammates()) {
            try {
               robot_.sendMessage(teammate, message);
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
   }

   @Override
   public void inEvent(Event event) {
      if (event instanceof ScannedRobotEvent) {
         handleScannedRobotEvent((ScannedRobotEvent) event);
      }
   }

   @Override
   public void inEvents(Iterable<Event> events) {
      for (Event e : events) {
         inEvent(e);
      }
   }

   private void handleScannedRobotEvent(ScannedRobotEvent event) {
      if (event == null) {
         throw new NullPointerException("ScannedRobotEvent must not be null.");
      }

      send(new RobotSnapshotMessage(snapshotFactory_.create(event, robot_), robot_.getName(), robot_.getTime()));
   }

}
