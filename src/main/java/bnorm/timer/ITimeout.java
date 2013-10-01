package bnorm.timer;

/**
 * A timer that has a timeout.  Can be used to determine if something has taken too long.
 *
 * @author Brian Norman
 */
public interface ITimeout extends ITimer {

    @Override
    ITimeout start();

    @Override
    ITimeout stop();

    @Override
    ITimeout reset();

    /**
     * Returns the timeout value of the timer in seconds.
     *
     * @return the timeout value.
     */
    double getTimeout();

    /**
     * Sets the timeout of the timer.
     *
     * @param seconds the timeout value.
     * @return self for chaining.
     */
    ITimeout setTimeout(double seconds);

    /**
     * Returns the time remaining in seconds until the timer times out.
     *
     * @return the time remaining.
     */
    double getRemainingTime();

    /**
     * Returns if the timer has timed out.
     *
     * @return if the timer has timed out.
     */
    boolean hasTimedOut();

}
