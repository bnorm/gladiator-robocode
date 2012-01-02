package kid;

public class TimeTracker {

    private static long totalTime = 0;
    private static long dataTime = 0;
    private static long movementTime = 0;
    private static long targetingTime = 0;

    private static long totalTimeTemp;
    private static long dataTimeTemp;
    private static long movementTimeTemp;
    private static long targetingTimeTemp;

    private static boolean totalTimeBoolean = false;
    private static boolean dataTimeBoolean = false;
    private static boolean movementTimeBoolean = false;
    private static boolean targetingTimeBoolean = false;

    public static long getTotalTime() {
        return totalTime;
    }

    public static long getDataTime() {
        return dataTime;
    }

    public static long getMovementTime() {
        return movementTime;
    }

    public static long getTargetingTime() {
        return targetingTime;
    }

    public static void startTotalTime() {
        totalTimeBoolean = true;
        totalTimeTemp = System.nanoTime();
    }

    public static void startDataTime() {
        dataTimeBoolean = true;
        dataTimeTemp = System.nanoTime();
    }

    public static void startMovementTime() {
        movementTimeBoolean = true;
        movementTimeTemp = System.nanoTime();
    }

    public static void startTargetingTime() {
        targetingTimeBoolean = true;
        targetingTimeTemp = System.nanoTime();
    }

    public static void stopTotalTime() {
        if (totalTimeBoolean)
            totalTime += System.nanoTime() - totalTimeTemp;
        totalTimeBoolean = false;
    }

    public static void stopDataTime() {
        if (dataTimeBoolean)
            dataTime += System.nanoTime() - dataTimeTemp;
        dataTimeBoolean = false;
    }

    public static void stopMovementTime() {
        if (movementTimeBoolean)
            movementTime += System.nanoTime() - movementTimeTemp;
        movementTimeBoolean = false;
    }

    public static void stopTargetingTime() {
        if (targetingTimeBoolean)
            targetingTime += System.nanoTime() - targetingTimeTemp;
        targetingTimeBoolean = false;
    }
}
