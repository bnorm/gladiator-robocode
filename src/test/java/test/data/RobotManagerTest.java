package test.data;

import org.junit.Assert;
import org.junit.Test;

import dev.robots.RobotData;
import dev.robots.RobotManager;
import test.MockRobot;
import test.MockScannedRobotEvent;

public class RobotManagerTest {

   @Test
   public void test_getInstance() {
      MockRobot robot = new MockRobot();

      RobotManager robots = RobotManager.getInstance();
      Assert.assertTrue(robots == null);

      robots = RobotManager.getInstance(robot);
      Assert.assertTrue(robots != null);

      robots = RobotManager.getInstance();
      Assert.assertTrue(robots != null);
   }

   @Test
   public void test_ScannedRobotEvent() {
      MockScannedRobotEvent dummy1a =
              new MockScannedRobotEvent(1, "com.bnorm.pathfinder.core.test.dummy1", 100.0, Math.PI / 16.0, 300.0,
                                        Math.PI, 8.0);
      MockScannedRobotEvent dummy1b =
              new MockScannedRobotEvent(2, "com.bnorm.pathfinder.core.test.dummy1", 98.0, Math.PI / 16.0, 295.0,
                                        17.0 / 16.0 * Math.PI, 6.0);
      MockScannedRobotEvent dummy2a =
              new MockScannedRobotEvent(1, "com.bnorm.pathfinder.core.test.dummy2", 30.0, Math.PI / 4.0, 300.0, Math.PI,
                                        8.0);

      RobotManager robots = RobotManager.getInstance();

      robots.inEvent(dummy1a);
      RobotData dummy1 = robots.getRobot("com.bnorm.pathfinder.core.test.dummy1");
      Assert.assertTrue(!dummy1.isDead());
      Assert.assertEquals(100.0, dummy1.getEnergy());
      Assert.assertEquals(Math.PI, dummy1.getHeading());
      Assert.assertEquals(8.0, dummy1.getVelocity());

      robots.inEvent(dummy2a);
      RobotData dummy2 = robots.getRobot("com.bnorm.pathfinder.core.test.dummy2");
      Assert.assertTrue(!dummy2.isDead());
      Assert.assertEquals(30.0, dummy2.getEnergy());
      Assert.assertEquals(Math.PI, dummy2.getHeading());
      Assert.assertEquals(8.0, dummy2.getVelocity());

      robots.inEvent(dummy1b);
      dummy1 = robots.getRobot("com.bnorm.pathfinder.core.test.dummy1");
      Assert.assertTrue(!dummy1.isDead());
      Assert.assertEquals(98.0, dummy1.getEnergy());
      Assert.assertEquals(-2.0, dummy1.getDeltaEnergy());
      Assert.assertEquals(17.0 / 16.0 * Math.PI, dummy1.getHeading());
      Assert.assertEquals(Math.PI / 16.0, dummy1.getDeltaHeading());
      Assert.assertEquals(6.0, dummy1.getVelocity());
      Assert.assertEquals(-2.0, dummy1.getDeltaVelocity());

   }

}
