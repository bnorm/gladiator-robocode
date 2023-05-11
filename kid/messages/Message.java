package kid.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

   private long time;

   public Message(long time) {
      init(time);
   }

   private void init(long time) {
      this.time = time;
   }

   public long getTime() {
      return time;
   }

}
