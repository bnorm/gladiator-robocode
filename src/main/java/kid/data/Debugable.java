package kid.data;

import java.io.PrintStream;

import robocode.RobocodeFileOutputStream;

// BORED documentation: perfect
// BORED thought: necessary class? JUnit batter? eclipse debugger better?

/**
 * A <code>public interface</code> that allows debugging to be done by a third party.
 * 
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public interface Debugable {

   /**
    * An <code>abstract void</code> that allows debugging data to be printed on a console.
    * 
    * @param console - the <code>java.io.PrintStream</code> that the debugging data will be sent to.
    */
   public abstract void debug(PrintStream console);

   /**
    * An <code>abstract void</code> that allows debugging data to be printed to a file.
    * 
    * @param output - the <code>java.io.ObjectOutputStream</code> that the data will be sent to.
    */
   public abstract void debug(RobocodeFileOutputStream output);

}
