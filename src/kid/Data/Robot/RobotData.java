package kid.Data.Robot;

import kid.*;
import kid.Data.RobotInfo;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import robocode.Robot;

public class RobotData implements java.io.Serializable {
    private static final long serialVersionUID = -6599674688945197719L;

    public static final double ENERGY_DEAD = -1.0;

    protected String NAME;
    protected String ALIAS;
    protected double X;
    protected double Y;
    protected double ENERGY;
    protected double HEADING;
    protected double VELOCITY;
    protected long TIME;
    protected transient int DELTA_TIME;

    // ***** POSABLE ALIASES *****//
    public static final String ALIVE = "ALIVE";
    public static final String DEAD = "DEAD";

    public static final String BOMB = "BOMB";
    public static final String SHELTER = "SHELTER";

    public static final String HILL = "HILL";
    public static final String TREE = "TREE";


    public RobotData() {
        this(new String(), 0.0, 0.0, 0.0, 0.0, 0, Long.MAX_VALUE);
        ALIAS = DEAD;
    }

    public RobotData(String name, double x, double y, double energy, double heading,
                     double velocity, long time) {
        NAME = name;
        updateItemFromFile(name, x, y, energy, heading, velocity, time);
    }

    public RobotData(Robot MyRobot) {
        NAME = MyRobot.getName();
        updateItemFromFile(MyRobot.getName(), MyRobot.getX(), MyRobot.getY(), MyRobot.getEnergy(),
                           MyRobot.getHeading(), MyRobot.getVelocity(), MyRobot.getTime());
    }

    public void updateItem(double currentX, double currentY, double currentEnergy,
                           double currentHeading, double currentVelocity, long currentTime) {
        if (currentTime < TIME) {
            updateItemFromFile(NAME, currentX, currentY, currentEnergy, currentHeading,
                               currentVelocity, currentTime);
            System.out.println("Reset Robot for new Round: " + NAME);
        } else if (currentTime != TIME) {
            X = currentX;
            Y = currentY;
            ENERGY = currentEnergy;
            HEADING = currentHeading;
            VELOCITY = currentVelocity;
            DELTA_TIME = (int) (currentTime - TIME);
            TIME = currentTime;
        }
    }

    public void updateItemFromFile(String name, double x, double y, double energy, double heading,
                                   double velocity, long time) {
        ALIAS = ALIVE;
        NAME = name;
        X = x;
        Y = y;
        ENERGY = energy;
        HEADING = heading;
        VELOCITY = velocity;
        TIME = time;
    }

    public void setDeath() {
        ENERGY = ENERGY_DEAD;
        setAlias(DEAD);
    }

    public void setAlias(String a) {
        if (ALIAS != DEAD)
            ALIAS = a;
    }

    public String getName() {
        return NAME;
    }

    public String getAlias() {
        return ALIAS;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public Rectangle2D getRobot() {
        return new Rectangle2D.Double(getX() - RobotInfo.WIDTH / 2, getY() - RobotInfo.HEIGHT / 2,
                                      RobotInfo.WIDTH, RobotInfo.HEIGHT);
    }

    public double DistTo(double x, double y) {
        return Utils.getDist(getX(), getY(), x, y);
    }

    public double DistSqTo(double x, double y) {
        return Utils.getDistSq(getX(), getY(), x, y);
    }


    public double BearingToXY(double x, double y) {
        return Utils.relative(Utils.getAngle(getX(), getY(), x, y) - getHeading());
    }

    public double getEnergy() {
        return ENERGY;
    }

    public boolean isDead() {
        return getAlias() == DEAD;
    }

    public boolean isAlive() {
        return !isDead();
    }

    public double getHeading() {
        return HEADING;
    }

    public double getVelocity() {
        return VELOCITY;
    }

    public long getTime() {
        return TIME;
    }

    public int getDeltaTime() {
        return DELTA_TIME;
    }

    public void drawRobot(RobocodeGraphicsDrawer g, Color c) {
        if (isDead())
            return;
        g.setColor(c);
        g.draw(getRobot());
    }

    public void drawRobot(RobocodeGraphicsDrawer g) {
        if (isDead())
            return;
        float h = Math.max(120F - (float) getEnergy() * 1.2F, 0F) / 360F;
        float s = 1F;
        float b = (75 + (float) Math.max(getEnergy() - RobotInfo.START_ENERGY, 0)) / 100F;
        Color color = Color.getHSBColor(h, s, b);
        g.setColor(color);
        g.draw(getRobot());
    }
}
