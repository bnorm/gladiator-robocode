package kid.data;

import kid.graphics.RGraphics;

/**
 * A <code>public interface</code> that allows drawing to be done by a third party.
 * 
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public interface Drawable {

   /**
    * An <code>abstract void</code> that allows graphics to be drawn on a grid.
    * 
    * @param grid - the <code>{@link kid.graphics.RGraphics RobocodeGraphicsDrawer}</code> that the graphics will be
    *           drawn on
    */
   public abstract void draw(RGraphics grid);

   // TODO documentation: method - drawOptions(RobocodeGraphicsDrawer)
   // public abstract List<String> getOptions();

}
