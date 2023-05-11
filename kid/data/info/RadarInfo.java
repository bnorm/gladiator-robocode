package kid.data.info;

import java.io.PrintStream;

import robocode.*;

import kid.Utils;

// BORED perfect documentation

/**
 * The <code>RadarInfo</code> class is used for accessing information about a robot's
 * radar. Whether that information is in the form of constants (e.g., the radar's scan
 * length, turn speed, etc.) or in the form of accessing the robot's current information
 * (e.g., the radar's current heading, turn left to complete, etc.).
 * 
 * @author Brian Norman
 * @version 0.0.1 beta
 */
public class RadarInfo extends GunInfo {

    /**
     * Determines if a de-serialized file is compatible with this class.<BR>
     * <BR>
     * Maintainers must change this value if and only if the new version of this class is
     * not compatible with old versions.
     */
    private static final long serialVersionUID = 5648067665876157986L;


    public static final double RADAR_TURN_RATE = Rules.RADAR_TURN_RATE;
    public static final double RADAR_SCAN_LENGTH = 1200.0D;

    /**
     * Constructs a new <code>RadarInfo</code> with the specific <code>Robot</code>
     * class.
     * 
     * @see robocode.Robot
     * @param myRobot - the specific <code>Robot</code>
     */
    public RadarInfo(Robot myRobot) {
        super(myRobot);
        init(myRobot);
    }

    /**
     * Constructs a new <code>RadarInfo</code> with the specific <code>RadarInfo</code>
     * class. Used for cloning.
     * 
     * @param myRobot - the specific <code>RadarInfo</code>
     */
    protected RadarInfo(RadarInfo myRobot) {
        super(myRobot);
        init(myRobot.robot);
    }


    /**
     * Initializes a new <code>RadarInfo</code> with the specific <code>Robot</code>
     * class.
     * 
     * @see robocode.Robot
     * @param myRobot - the specific <code>Robot</code>
     */
    private void init(Robot myRobot) {
        this.robot = myRobot;
    }

    /**
     * Returns the heading of the radar in degrees.
     * 
     * @return the gun's heading.
     */
    @Override
    public double getHeading() {
        return this.robot.getRadarHeading();
    }

    /**
     * Returns the angle that the robot's radar has yet to turn.<BR>
     * <BR>
     * This will only work if the robot is an <code>AdvancedRobot</code>.
     * 
     * @return the turning anlge remaining.
     */
    @Override
    public double getTurnRemaining() {
        if (robot instanceof AdvancedRobot) {
            AdvancedRobot myRobot = (AdvancedRobot) robot;
            return myRobot.getRadarTurnRemaining();
        }
        return 0.0;
    }

    /**
     * Returns the sign of the angle that the robot's radar has yet to turn. Will return
     * <code>0.0</code> if there is no turning left to turn.<BR>
     * <BR>
     * This will only work if the robot is an <code>AdvancedRobot</code>.
     * 
     * @return the sign of the moveing distance remaining.
     */
    @Override
    public int getTurningSign() {
        return (getTurnRemaining() == 0.0 ? 0 : Utils.sign(getTurnRemaining()));
    }

    @Override
    public void print(PrintStream console) {
        // TODO method stub
    }

    @Override
    public void print(RobocodeFileOutputStream output) {
        // TODO method stub
        // PrintStream file = new PrintStream(output);
    }

    @Override
    public void debug(PrintStream console) {
        // TODO method stub
    }

    @Override
    public void debug(RobocodeFileOutputStream output) {
        // TODO method stub
        // PrintStream file = new PrintStream(output);
    }

    @Override
    public Object clone() {
        return new RadarInfo(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RadarInfo) {
            RadarInfo robot = (RadarInfo) obj;
            return robot.robot.getName().equals(this.robot.getName());
        }
        return false;
    }

    @Override
    public String toString() {
        // TODO method stub
        return new String();
    }

    @Override
    protected void finalize() {
        this.robot = null;
    }

}