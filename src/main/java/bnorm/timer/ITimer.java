package bnorm.timer;

/**
 * Timer that can be started, stopped, and reset.  Can be used to measure elapsed time since a specific moment or how
 * long it takes to do something.  Can also be used to determine when something started and stopped.
 *
 * @author Brian Norman
 */
public interface ITimer {

    /**
     * The various states of a timer.
     */
    enum TimerState {

        /**
         * Signifies that a timer is running.
         */
        RUNNING,

        /**
         * Signifies that a timer is stopped.
         */
        STOPPED
    }

    /**
     * Returns the current state of the timer.  Whether it is running or stopped.
     *
     * @return the current state of the timer.
     */
    TimerState getTimerState();

    /**
     * Starts the timer.  If the timer is already running then the timer should be restarted.
     *
     * @return self for chaining.
     */
    ITimer start();

    /**
     * Stops the timer.  If the timer is already stopped, nothing should happen.
     *
     * @return self for chaining.
     */
    ITimer stop();

    /**
     * Resets the timer.  If the timer is running, the timer should be started again.  If the timer is stopped, the
     * start time and end time should be reset to 0.
     *
     * @return self for chaining.
     */
    ITimer reset();

    /**
     * Returns the current time of the timer.  Useful for knowing what time the timer thinks it is.
     *
     * @return the current time of the timer.
     */
    double getCurrentTime();

    /**
     * Returns the start time of the timer.  If the timer is not running, it returns the last start time of the timer or
     * zero if it has been reset.
     *
     * @return the start time of the timer.
     */
    double getStartTime();

    /**
     * Returns the end time of the timer.  If the timer is running, nothing with meaning will be returned.  If the
     * timer is stopped, the time at which the timer was stop is returned.
     *
     * @return the end time of the timer.
     */
    double getEndTime();

    /**
     * Returns the elapsed time of the timer.  If the timer is running, this is the difference between the current time
     * and the time the timer was started.  If the timer is stopped, this is the difference between the end time and the
     * start time.
     *
     * @return the elapsed time of the timer.
     */
    double getElapsedTime();

}
