package kid.Data.Gravity;

import java.util.*;
import robocode.*;
import kid.Utils;

/**
 * AGravEngine -> an engine encapsulating the Anti-gravity movement
 * algorithm. By NEH (aka Hanji)
 **/
public class GravityEngine {

    /**The Points that it is concerned with.**/
    protected List gravPoints = new ArrayList();

    /**
     * The force, if any, of the walls. It is assumed that the walls will
     * never attract, so this is always negative (meaning repulsion in this
     * case).
     **/
    protected double wallForce = -50.0;

    /**The width of the battlefield.**/
    protected double width;

    /**The height of the battlefield**/
    protected double height;

    /*The components of the force**/
    protected double xRobotForce = 0.0;
    protected double yRobotForce = 0.0;
    protected double xWallForce = 0.0;
    protected double yWallForce = 0.0;
    //protected double xBulletForce = 0.0;
    //protected double yBulletForce = 0.0;

    /**
     * The exponent used in cacluating a point's effect on a location.
     * The effect is proportionate to distance^pointDropoff. This field
     * has an initial value of 2.0.
     **/
    protected double pointDropoff = 2.0;

    /**
     * The exponent used in cacluating the walls' effect on a location.
     * The effect is proportionate to distance^wallDropoff. This field
     * has an initial value of 3.0.
     **/
    protected double wallDropoff = 2.0;

    /**
     * The exponent used in cacluating the bullets' effect on a location.
     * The effect is proportionate to distance^bulletDropoff. This field
     * has an initial value of 2.0.
     **/
    protected double bulletDropoff = 2.0;

    /**Initializes the width and height to 0**/
    public GravityEngine() {
        this(0, 0);
    }

    /**Initializes the dimensions to the specified values**/
    public GravityEngine(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**Adds a point to the list.**/
    public void addPoint(GravityPoint g) {
        gravPoints.add(g);
    }

    /**Removes a point from the list.**/
    public boolean removePoint(GravityPoint g) {
        return gravPoints.remove(g);
    }

    /**Returns the number of points currently contained.**/
    public long getNumPoints() {
        return gravPoints.size();
    }

    /**Returns the force with which the walls repel.**/
    public double getWallForce() {
        return wallForce;
    }

    /**Sets the force with which the walls repel.**/
    public void setWallForce(double wf) {
        wallForce = wf;
    }

    /**Returns the value of the point dropoff exponent.**/
    public double getPointDropoff() {
        return pointDropoff;
    }

    /**Sets the value of the point dropoff exponent.**/
    public void setPointDropoff(double pdf) {
        pointDropoff = pdf;
    }

    /**Returns the value of the wall dropoff exponent.**/
    public double getWallDropoff() {
        return wallDropoff;
    }

    /**Sets the value of the wall dropoff exponent.**/
    public void setWallDropoff(double wdf) {
        wallDropoff = wdf;
    }

    /**Removes all points from the list.**/
    public void reset() {
        gravPoints = new Vector();
    }


    /**
     * Calculates the current force and kills any GravPoints
     * that have gone past their lifetimes
     * @param curX, The X coordinate of the point for which
     * gravity is being calculated.
     * @param curY, The Y coordinate of the point for which
     * gravity is being calculated.
     * @param time, The current time.
     **/
    public void update(double curX, double curY, long time) {
        xRobotForce = 0.0;
        yRobotForce = 0.0;
        xWallForce = 0.0;
        yWallForce = 0.0;
        Vector deadPoints = new Vector();
        GravityPoint g;
        double force;
        double angle;
        for(int i = 0; i < gravPoints.size(); i++) {
            g = (GravityPoint) gravPoints.get(i);
            if(g.update(time)) {
                deadPoints.add(g);
                continue;
            }
            force = g.strength / Math.pow(Utils.getDist(curX, curY, g.x, g.y), pointDropoff);
            angle = Utils.getAngle(curX, curY, g.x, g.y);
            xRobotForce += force * Utils.sin(angle);
            yRobotForce += force * Utils.cos(angle);
        }
        xWallForce -= wallForce / Math.pow(curX, wallDropoff);
        xWallForce += wallForce / Math.pow(width - curX, wallDropoff);
        yWallForce += wallForce / Math.pow(height - curY, wallDropoff);
        yWallForce -= wallForce / Math.pow(curY, wallDropoff);
        for(int i = 0; i < deadPoints.size(); i++) {
            gravPoints.remove(deadPoints.elementAt(i));
        }
    }

    /**
     * Calculates the current force and kills any GravPoints
     * that have gone past their lifetimes
     * @param curX double
     * @param curY double
     * @param time long
     */
    public void update(Robot MyRobot) {
        xRobotForce = 0.0;
        yRobotForce = 0.0;
        xWallForce = 0.0;
        yWallForce = 0.0;
        Vector deadPoints = new Vector();
        GravityPoint g;
        double force;
        double angle;
        for(int i = 0; i < gravPoints.size(); i++) {
            g = (GravityPoint) gravPoints.get(i);
            if(g.update(MyRobot.getTime())) {
                deadPoints.add(g);
                continue;
            }
            force = g.strength / Math.pow(Utils.getDist(MyRobot.getX(), MyRobot.getY(), g.x, g.y), pointDropoff);
            angle = Math.toRadians(Utils.getAngle(MyRobot.getX(), MyRobot.getY(), g.x, g.y));
            xRobotForce += force * Math.sin(angle);
            yRobotForce += force * Math.cos(angle);
        }
        xWallForce -= wallForce / Math.pow(MyRobot.getX(), wallDropoff);
        xWallForce += wallForce / Math.pow(width - MyRobot.getX(), wallDropoff);
        yWallForce += wallForce / Math.pow(height - MyRobot.getY(), wallDropoff);
        yWallForce -= wallForce / Math.pow(MyRobot.getY(), wallDropoff);
        for(int i = 0; i < deadPoints.size(); i++) {
            gravPoints.remove(deadPoints.elementAt(i));
        }
    }


    /**
     * Returns the force in the X direction (calculated in
     * update())
     **/
    public double getXForce() {
        return xRobotForce + xWallForce; // + xBulletForce
    }

    /**
     * Returns the force in the Y direction (calculated in
     * update())
     **/
    public double getYForce() {
        return yRobotForce + yWallForce; // + yBulletForce
    }

    /**
     * Returns the force for the GravPoints in the X direction (calculated in
     * update())
     **/
    public double getXRobotForce() {
        return xRobotForce;
    }

    /**
     * Returns the force for the GravPoints in the Y direction (calculated in
     * update())
     **/
    public double getYRobotForce() {
        return yRobotForce;
    }

    /**
     * Returns the force for the walls in the X direction (calculated in
     * update())
     **/
    public double getXWallForce() {
        return xWallForce;
    }

    /**
     * Returns the force for the walls in the Y direction (calculated in
     * update())
     **/
    public double getYWallForce() {
        return yWallForce;
    }

    /**
     * Returns the force for the bullets in the X direction (calculated in
     * update())
     **/
    /*
        public double getXBulletForce() {
            return xBulletForce;
        }
     */

    /**
     * Returns the force for the bullets in the Y direction (calculated in
     * update())
     **/
    /*
         public double getYBulletForce() {
            return yBulletForce;
        }
     */

}
