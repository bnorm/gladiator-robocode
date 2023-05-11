package kid.messages;

import kid.data.virtual.VirtualBullet;
import robocode.*;

// TODO documentation: class

public class BulletMessage extends Message {

   private static final long serialVersionUID = -7080534829884517840L;
   
   private VirtualBullet[] vbullets;

   public BulletMessage(VirtualBullet[] vbullets, Robot sender) {
      super(sender.getTime());
      init(vbullets);
   }

   private void init(VirtualBullet[] vbullets) {
      this.vbullets = vbullets;
   }

   public VirtualBullet[] getVirtualBullets() {
      return vbullets;
   }
}
