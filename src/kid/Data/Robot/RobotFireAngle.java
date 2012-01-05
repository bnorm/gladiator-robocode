package kid.Data.Robot;

public class RobotFireAngle {

    private EnemyData robot;
    private double firePower;
    private double fireAngle;
    private int recent = 0;
    private long time;

    public RobotFireAngle(EnemyData Enemy, double FirePower, double FireAngle, long TimeOfCalculation) {
        robot = Enemy;
        firePower = FirePower;
        fireAngle = FireAngle;
        time = TimeOfCalculation;
    }

    public RobotFireAngle(EnemyData Enemy, double FirePower, double FireAngle, int LenghtOfRecentMovement, long TimeOfCalculation) {
        this(Enemy, FirePower, FireAngle, TimeOfCalculation);
        recent = LenghtOfRecentMovement;
    }

    public EnemyData getEnemy() {
        return robot;
    }

    public double getFirePower() {
        return firePower;
    }

    public double getFireAngle() {
        return fireAngle;
    }

    public int getLenghtOfRecentMovement() {
        return recent;
    }

    public long getTimeOfCalculation() {
        return time;
    }

}
