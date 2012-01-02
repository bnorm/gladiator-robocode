package kid.Data.Robot;

import robocode.Robot;
import kid.Utils;
import kid.Data.RobotInfo;

public class TeammateData extends RobotData implements java.io.Serializable {
    private static final long serialVersionUID = -6898830233847825000L;

    // ***** THIS ROBOTS INFO *****//
    protected transient double DELTA_ENERGY;
    protected transient double DELTA_HEADING;
    protected transient double DELTA_VELOCITY;
    protected transient double TIME_OF_ACCEL;
    protected transient double TIME_OF_DECCEL;

    public TeammateData() {
        super();
    }

    public TeammateData(String name, double currentX, double currentY, double currentEnergy,
                        double currentHeading, double currentVelocity, long currentTime) {
        super(name, currentX, currentY, currentEnergy, currentHeading, currentVelocity, currentTime);
    }

    public TeammateData(Robot currentObservation) {
        super(currentObservation.getName(), currentObservation.getX(), currentObservation.getY(),
              currentObservation.getEnergy(), currentObservation.getHeading(),
              currentObservation.getVelocity(), currentObservation.getTime());
    }

    public TeammateData(TeammateData currentObservation) {
        super(currentObservation.getName(), currentObservation.getX(), currentObservation.getY(),
              currentObservation.getEnergy(), currentObservation.getHeading(),
              currentObservation.getVelocity(), currentObservation.getTime());
    }

    public void updateItem(double currentX, double currentY, double currentEnergy,
                           double currentHeading, double currentVelocity, long currentTime) {
        if (currentTime < TIME) {
            updateItemFromFile(NAME, currentX, currentY, currentEnergy, currentHeading,
                               currentVelocity, currentTime);
            System.out.println("Reset Robot for new Round: " + NAME);
        } else if (currentTime != TIME) {
            DELTA_ENERGY = currentEnergy - ENERGY;
            DELTA_HEADING = Utils.relative(currentHeading - HEADING);
            DELTA_VELOCITY = currentVelocity - VELOCITY;
            if (Math.abs(DELTA_VELOCITY) == RobotInfo.ACCELERATION)
                TIME_OF_ACCEL = currentTime;
            else if (Math.abs(DELTA_VELOCITY) == RobotInfo.DECCELERATION)
                TIME_OF_DECCEL = currentTime;
            super.updateItem(currentX, currentY, currentEnergy, currentHeading, currentVelocity,
                             currentTime);
        }
    }

    public void updateItem(TeammateData teammate) {
        updateItem(teammate.getX(), teammate.getY(), teammate.getEnergy(), teammate.getHeading(),
                   teammate.getVelocity(), teammate.getTime());
    }

    public double getDeltaEnergy() {
        return DELTA_ENERGY;
    }

    public double getDeltaHeading() {
        return DELTA_HEADING;
    }

    public double getDeltaVelocity() {
        return DELTA_VELOCITY;
    }

    public double getTimeSinceAccel() {
        return TIME - TIME_OF_ACCEL;
    }

    public double getTimeSinceDeccel() {
        return TIME - TIME_OF_DECCEL;
    }

    public Object clone() {
        return new TeammateData(this);
    }

}
