package dev.draw;

import dev.draw.RobotGraphics;

/**
 * A <code>public interface</code> that allows drawing to be done by a third party.
 * 
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public interface Drawable {

   public abstract void draw(RobotGraphics grid);

}
