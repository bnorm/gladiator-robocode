package bnorm.timer;

/**
 * A simple {@link ITimer} implementation.
 *
 * @author Brian Norman
 */
public class Timer implements ITimer {

    /**
     * The end time of the timer if it is running.
     */
    public static final double RUNNING_END_TIME = -1.0;

    /**
     * The current state of the timer.
     */
    private TimerState state;

    /**
     * The start time of the timer.
     */
    private double startTime;

    /**
     * The end time of the timer.
     */
    private double endTime;

    /**
     * Creates a new timer which is stopped and has no start time or end time.
     */
    public Timer() {
        super();

        this.state = TimerState.STOPPED;
        this.startTime = 0.0;
        this.endTime = 0.0;
    }

    @Override
    public TimerState getTimerState() {
        return state;
    }

    @Override
    public Timer start() {
        state = TimerState.RUNNING;
        startTime = getCurrentTime();
        endTime = RUNNING_END_TIME;
        return this;
    }

    @Override
    public Timer stop() {
        // Can only stop if the timer is running
        if (TimerState.RUNNING == getTimerState()) {
            state = TimerState.STOPPED;
            endTime = getCurrentTime();
        }
        return this;
    }

    @Override
    public Timer reset() {
        if (TimerState.RUNNING == getTimerState()) {
            return start();
        } else {
            startTime = 0.0;
            endTime = 0.0;
            return this;
        }
    }

    @Override
    public double getCurrentTime() {
        return System.currentTimeMillis() / 1000.0;
    }

    @Override
    public double getStartTime() {
        return startTime;
    }

    @Override
    public double getEndTime() {
        return endTime;
    }

    @Override
    public double getElapsedTime() {
        if (TimerState.STOPPED == getTimerState()) {
            return getEndTime() - getStartTime();
        } else {
            return getCurrentTime() - getStartTime();
        }
    }
}
