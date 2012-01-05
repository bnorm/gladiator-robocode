package kid.Data.Robot;

public class TeammateData extends RobotData implements java.io.Serializable {

    public String toString() {
        return ("Name " + getName() + ", " + "Coordinates: " + "(" + (int) getX() + ", " + (int) getY() + ")" + ", " +
                "Heading: " + (int) getHeading() + ", " + "Velocity: " + (int) getVelocity()); //(+ "ALIAS: " + getAlias() + ", ")()
    }

    //***** THIS ROBOTS INFO *****//
    protected String NAME;
    protected String ALIAS;

    protected double X;
    protected double Y;

    protected double ENERGY;

    protected double HEADING;

    protected double VELOCITY;

    protected long TIME;
    protected int DELTA_TIME;

    public TeammateData() {
        this(new String(), 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
        ALIAS = DEAD;
    }

    public TeammateData(String name, double x, double y, double dist, double energy, double heading, double velocity,
                        long time) {
        NAME = name;
        updateItemFromFile(x, y, dist, energy, heading, velocity, time);
    }


    public void updateItem(double x, double y, double dist, double energy, double heading, double velocity, long time) {
        if (time != TIME) {
            X = x;
            Y = y;
            ENERGY = energy;
            HEADING = heading;
            VELOCITY = velocity;
            DELTA_TIME = (int) (time - TIME);
            TIME = time;
        }
    }


    public void updateItemFromFile(double x, double y, double dist, double energy, double heading, double velocity, long time) {
        ALIAS = ALIVE;
        X = x;
        Y = y;
        ENERGY = energy;
        HEADING = heading;
        VELOCITY = velocity;
        TIME = time;
    }

    public void updateItemFromTeammate(TeammateData teammate) {
        if (teammate.getTime() != TIME) {
            X = teammate.getX();
            Y = teammate.getY();
            ENERGY = teammate.getEnergy();
            HEADING = teammate.getHeading();
            VELOCITY = teammate.getVelocity();
            DELTA_TIME = (int) (teammate.getTime() - TIME);
            TIME = teammate.getTime();
        }
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

    public double getEnergy() {
        return ENERGY;
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


}
