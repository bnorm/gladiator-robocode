package dev.team;

import dev.robots.RobotData;

public abstract class Message {

   private long      time;
   private RobotData sender;

   public Message(RobotData sender, long time) {
      this.sender = sender;
      this.time = time;
   }

   public RobotData getSender() {
      return sender;
   }

   public long getTime() {
      return time;
   }

}
