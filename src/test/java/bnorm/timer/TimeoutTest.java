package bnorm.timer;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

/**
 * A group of unit tests for {@link Timeout}.
 *
 * @author bnorman
 */
public class TimeoutTest {

    /**
     * Tests the {@link Timeout#getTimeout()} method.
     */
    @Test
    public void testGetTimeout() {
        // Covered in other cases
    }

    /**
     * Tests the {@link Timeout#setTimeout(double)} method.
     */
    @Test
    public void testSetTimeout() {
        // Covered in other cases
    }

    /**
     * Tests the {@link Timeout#getRemainingTime()} method.
     */
    @Test
    public void testGetRemainingTime() {
        ITimeout timeout = PowerMockito.spy(new Timeout(5.0));

        Assert.assertEquals(Double.POSITIVE_INFINITY, timeout.getRemainingTime(), 0.0);

        PowerMockito.when(timeout.getCurrentTime()).thenReturn(1.0);
        timeout.start();
        Assert.assertEquals(5.0, timeout.getRemainingTime(), 0.0);

        PowerMockito.when(timeout.getCurrentTime()).thenReturn(2.0);
        timeout.stop();
        Assert.assertEquals(Double.POSITIVE_INFINITY, timeout.getRemainingTime(), 0.0);

        PowerMockito.when(timeout.getCurrentTime()).thenReturn(3.0);
        timeout.start();
        Assert.assertEquals(5.0, timeout.getRemainingTime(), 0.0);

        timeout.setTimeout(-1.0);
        Assert.assertEquals(Double.POSITIVE_INFINITY, timeout.getRemainingTime(), 0.0);

        PowerMockito.when(timeout.getCurrentTime()).thenReturn(4.0);
        Assert.assertEquals(Double.POSITIVE_INFINITY, timeout.getRemainingTime(), 0.0);
    }

    /**
     * Tests the {@link Timeout#hasTimedOut()} method.
     */
    @Test
    public void testHasTimedOut() {
        ITimeout timeout = PowerMockito.spy(new Timeout(5.0));

        PowerMockito.when(timeout.getCurrentTime()).thenReturn(1.0);
        timeout.start();
        Assert.assertEquals(1.0, timeout.getStartTime(), 0.0);
        Assert.assertEquals(5.0, timeout.getRemainingTime(), 0.0);
        Assert.assertFalse(timeout.hasTimedOut());

        PowerMockito.when(timeout.getCurrentTime()).thenReturn(3.0);
        Assert.assertEquals(1.0, timeout.getStartTime(), 0.0);
        Assert.assertEquals(3.0, timeout.getRemainingTime(), 0.0);
        Assert.assertFalse(timeout.hasTimedOut());

        PowerMockito.when(timeout.getCurrentTime()).thenReturn(5.0);
        Assert.assertEquals(1.0, timeout.getStartTime(), 0.0);
        Assert.assertEquals(1.0, timeout.getRemainingTime(), 0.0);
        Assert.assertFalse(timeout.hasTimedOut());

        PowerMockito.when(timeout.getCurrentTime()).thenReturn(6.0);
        Assert.assertEquals(1.0, timeout.getStartTime(), 0.0);
        Assert.assertEquals(0.0, timeout.getRemainingTime(), 0.0);
        Assert.assertTrue(timeout.hasTimedOut());

        PowerMockito.when(timeout.getCurrentTime()).thenReturn(8.0);
        Assert.assertEquals(1.0, timeout.getStartTime(), 0.0);
        Assert.assertEquals(-2.0, timeout.getRemainingTime(), 0.0);
        Assert.assertTrue(timeout.hasTimedOut());

        timeout.setTimeout(8.0);

        Assert.assertEquals(1.0, timeout.getStartTime(), 0.0);
        Assert.assertEquals(1.0, timeout.getRemainingTime(), 0.0);
        Assert.assertFalse(timeout.hasTimedOut());

        PowerMockito.when(timeout.getCurrentTime()).thenReturn(9.0);
        Assert.assertEquals(1.0, timeout.getStartTime(), 0.0);
        Assert.assertEquals(0.0, timeout.getRemainingTime(), 0.0);
        Assert.assertTrue(timeout.hasTimedOut());

        PowerMockito.when(timeout.getCurrentTime()).thenReturn(11.0);
        Assert.assertEquals(1.0, timeout.getStartTime(), 0.0);
        Assert.assertEquals(-2.0, timeout.getRemainingTime(), 0.0);
        Assert.assertTrue(timeout.hasTimedOut());
    }
}
