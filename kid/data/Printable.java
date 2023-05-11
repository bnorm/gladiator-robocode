package kid.data;

import java.io.*;

import robocode.RobocodeFileOutputStream;

// BORED perfect documentation

/**
 * A <code>public interface</code> that allows printing to be done by a third party.
 * 
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public interface Printable {

    /**
     * An <code>abstract void</code> that allows data to be printed on a console.
     * 
     * @param console - the <code>java.io.PrintStream</code> that the data will be sent
     *            to.
     */
    public abstract void print(PrintStream console);

    /**
     * An <code>abstract void</code> that allows data to be printed to a file.
     * 
     * @param output - the <code>java.io.FileOutputStream</code> that the data will be
     *            sent to.
     */
    // FIXME find the right output stream for strings.
    public abstract void print(RobocodeFileOutputStream output);

}
