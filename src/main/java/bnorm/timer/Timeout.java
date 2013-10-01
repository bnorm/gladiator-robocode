package bnorm.timer;

/**
 * A simple {@link ITimeout} implementation.
 *
 * @author Brian Norman
 */
public class Timeout extends Timer implements ITimeout {

    /**
     * The timeout value in seconds.
     */
    private double timeout;

    /**
     * Creates a new timer with the specified timeout in seconds.
     *
     * @param seconds how long until the timer times out.
     */
    public Timeout(double seconds) {
        super();

        this.timeout = seconds;
    }

    @Override
    public Timeout start() {
        super.start();
        return this;
    }

    @Override
    public Timeout stop() {
        super.stop();
        return this;
    }

    @Override
    public Timeout reset() {
        super.reset();
        return this;
    }

    @Override
    public double getTimeout() {
        return timeout;
    }

    @Override
    public Timeout setTimeout(double seconds) {
        this.timeout = seconds;
        return this;
    }

    @Override
    public double getRemainingTime() {
        if (TimerState.STOPPED == getTimerState() || getTimeout() <= 0.0) {
            // If the timer is not running or the timeout is less than or equal to 0, there is no timeout
            return Double.POSITIVE_INFINITY;
        } else {
            return getTimeout() - getElapsedTime();
        }
    }

    @Override
    public boolean hasTimedOut() {
        return getRemainingTime() <= 0;
    }
}
