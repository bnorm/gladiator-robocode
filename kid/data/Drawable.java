package kid.data;

import kid.RobocodeGraphicsDrawer;

/**
 * A <code>public interface</code> that allows drawing to be done by a third party.
 * 
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public interface Drawable {

   /**
    * An <code>abstract void</code> that allows graphics to be draw on a grid.
    * 
    * @param grid - the
    *           <code>{@link kid.RobocodeGraphicsDrawer RobocodeGraphicsDrawer}</code>
    *           that the graphics will be drawn on.
    * @param commandString - the command string that initiates certain commands.
    */
   public abstract void draw(RobocodeGraphicsDrawer grid, String commandString);

}
