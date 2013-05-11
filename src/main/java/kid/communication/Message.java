package kid.communication;

import java.io.Serializable;

public abstract class Message implements Serializable {

   /**
    * Determines if a deserialized file is compatible with this class.<br>
    * <br>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = -2325608535263037963L;

   private long time;

   public Message(final long time) {
      init(time);
   }

   private void init(final long time) {
      this.time = time;
   }

   public long getTime() {
      return time;
   }

}
