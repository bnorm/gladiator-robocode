package kid;

import robocode.*;

public class Debuger {

    private Robot r;
    private boolean p;
    private boolean d;

    public Debuger(Robot MyRobot, boolean debug) {
        r = MyRobot;
        p = false;
        d = debug;
    }
    
    public Debuger(AdvancedRobot MyRobot, boolean debug) {
        r = MyRobot;
        p = true;
        d = debug;
    }
    
    public void print(boolean arg) {
        if (d)
            r.out.print(arg);
    }

    public void print(char arg) {
        if (d)
            r.out.print(arg);
    }

    public void print(int arg) {
        if (d)
            r.out.print(arg);
    }

    public void print(long arg) {
        if (d)
            r.out.print(arg);
    }

    public void print(float arg) {
        if (d)
            r.out.print(arg);
    }

    public void print(double arg) {
        if (d)
            r.out.print(arg);
    }

    public void print(char[] arg) {
        if (d)
            r.out.print(arg);
    }

    public void print(String arg) {
        if (d)
            r.out.print(arg);
    }

    public void print(Object arg) {
        if (d)
            r.out.print(arg);
    }

    public void println() {
        if (d)
            r.out.println();
    }

    public void println(boolean arg) {
        if (d)
            r.out.println(arg);
    }

    public void println(char arg) {
        if (d)
            r.out.println(arg);
    }

    public void println(int arg) {
        if (d)
            r.out.println(arg);
    }

    public void println(long arg) {
        if (d)
            r.out.println(arg);
    }

    public void println(float arg) {
        if (d)
            r.out.println(arg);
    }

    public void println(double arg) {
        if (d)
            r.out.println(arg);
    }

    public void println(char[] arg) {
        if (d)
            r.out.println(arg);
    }

    public void println(String arg) {
        if (d)
            r.out.println(arg);
    }

    public void println(Object arg) {
        if (d)
            r.out.println(arg);
    }
}
