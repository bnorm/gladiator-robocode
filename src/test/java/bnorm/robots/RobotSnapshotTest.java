package bnorm.robots;

import org.junit.Assert;
import org.junit.Test;

import bnorm.utils.Format;

/**
 * Test class for {@link RobotSnapshot}.
 *
 * @author Brian Norman
 * @version 1.4
 */
public class RobotSnapshotTest {

   /**
    * Test method for {@link RobotSnapshot#RobotSnapshot()}.
    */
   @Test
   public void testRobotSnapshot() {
      RobotSnapshot s = new RobotSnapshot();
      Assert.assertEquals("Name for snapshot should be an empty string.", "", s.getName());
      Assert.assertEquals("X coordinate for snapshot should be -1.0.", -1.0, s.getX(), 0.0);
      Assert.assertEquals("Y coordinate for snapshot should be -1.0.", -1.0, s.getY(), 0.0);
      Assert.assertEquals("Energy for snapshot should be -1.0.", -1.0, s.getEnergy(), 0.0);
      Assert.assertEquals("Heading for snapshot should be 0.0.", 0.0, s.getHeading(), 0.0);
      Assert.assertEquals("Velocity for snapshot should be 0.0.", 0.0, s.getVelocity(), 0.0);
      Assert.assertEquals("Time for snapshot should be -1.", -1, s.getTime());
      Assert.assertEquals("Round for snapshot should be -1.", -1, s.getRound());
   }

   /**
    * Test method for
    * {@link RobotSnapshot#RobotSnapshot(java.lang.String, double, double, double, double, double, long, int)}.
    */
   @Test
   public void testRobotSnapshotStringDoubleDoubleDoubleDoubleDoubleLongInt() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Name for snapshot should be \'test\'.", "test", s.getName());
      Assert.assertEquals("X coordinate for snapshot should be 0.0.", 0.0, s.getX(), 0.0);
      Assert.assertEquals("Y coordinate for snapshot should be 0.0.", 0.0, s.getY(), 0.0);
      Assert.assertEquals("Energy for snapshot should be 0.0.", 0.0, s.getEnergy(), 0.0);
      Assert.assertEquals("Heading for snapshot should be 0.0.", 0.0, s.getHeading(), 0.0);
      Assert.assertEquals("Velocity for snapshot should be 0.0.", 0.0, s.getVelocity(), 0.0);
      Assert.assertEquals("Time for snapshot should be 0.", 0, s.getTime());
      Assert.assertEquals("Round for snapshot should be 0.", 0, s.getRound());

      s = new RobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42);
      Assert.assertEquals("Name for snapshot should be \'TEST\'.", "TEST", s.getName());
      Assert.assertEquals("X coordinate for snapshot should be 5.0.", 5.0, s.getX(), 0.0);
      Assert.assertEquals("Y coordinate for snapshot should be 3.0.", 3.0, s.getY(), 0.0);
      Assert.assertEquals("Energy for snapshot should be 200.0.", 200.0, s.getEnergy(), 0.0);
      Assert.assertEquals("Heading for snapshot should be -70.0.", -70.0, s.getHeading(), 0.0);
      Assert.assertEquals("Velocity for snapshot should be 23.0.", 23.0, s.getVelocity(), 0.0);
      Assert.assertEquals("Time for snapshot should be 34.", 34, s.getTime());
      Assert.assertEquals("Round for snapshot should be 42.", 42, s.getRound());
   }

   /**
    * Test method for {@link RobotSnapshot#RobotSnapshot(bnorm.robots.IRobotSnapshot)}.
    */
   @Test
   public void testRobotSnapshotIRobotSnapshot() {
      RobotSnapshot s = new RobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42);
      Assert.assertEquals("Name for snapshot should be \'TEST\'.", "TEST", s.getName());
      Assert.assertEquals("X coordinate for snapshot should be 5.0.", 5.0, s.getX(), 0.0);
      Assert.assertEquals("Y coordinate for snapshot should be 3.0.", 3.0, s.getY(), 0.0);
      Assert.assertEquals("Energy for snapshot should be 200.0.", 200.0, s.getEnergy(), 0.0);
      Assert.assertEquals("Heading for snapshot should be -70.0.", -70.0, s.getHeading(), 0.0);
      Assert.assertEquals("Velocity for snapshot should be 23.0.", 23.0, s.getVelocity(), 0.0);
      Assert.assertEquals("Time for snapshot should be 34.", 34, s.getTime());
      Assert.assertEquals("Round for snapshot should be 42.", 42, s.getRound());

      RobotSnapshot copy = new RobotSnapshot(s);
      Assert.assertEquals("Name for snapshot should be \'TEST\'.", "TEST", copy.getName());
      Assert.assertEquals("X coordinate for snapshot should be 5.0.", 5.0, copy.getX(), 0.0);
      Assert.assertEquals("Y coordinate for snapshot should be 3.0.", 3.0, copy.getY(), 0.0);
      Assert.assertEquals("Energy for snapshot should be 200.0.", 200.0, copy.getEnergy(), 0.0);
      Assert.assertEquals("Heading for snapshot should be -70.0.", -70.0, copy.getHeading(), 0.0);
      Assert.assertEquals("Velocity for snapshot should be 23.0.", 23.0, copy.getVelocity(), 0.0);
      Assert.assertEquals("Time for snapshot should be 34.", 34, copy.getTime());
      Assert.assertEquals("Round for snapshot should be 42.", 42, copy.getRound());
   }

   /**
    * Test method for {@link RobotSnapshot#getName()}.
    */
   @Test
   public void testGetName() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Name for snapshot should be \'test\'.", "test", s.getName());

      s = new RobotSnapshot("test.-#;lkasd39uvn2", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Name for snapshot should be \'test.-#;lkasd39uvn2\'.", "test.-#;lkasd39uvn2", s.getName());
   }

   /**
    * Test method for {@link RobotSnapshot#getX()}.
    */
   @Test
   public void testGetX() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("X coordinate for snapshot should be 0.0.", 0.0, s.getX(), 0.0);

      s = new RobotSnapshot("test", 2951.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("X coordinate for snapshot should be 2951.0.", 2951.0, s.getX(), 0.0);

      s = new RobotSnapshot("test", -2951.92765283975256, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("X coordinate for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getX(),
                          0.0);
   }

   /**
    * Test method for {@link RobotSnapshot#getY()}.
    */
   @Test
   public void testGetY() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Y coordinate for snapshot should be 0.0.", 0.0, s.getY(), 0.0);

      s = new RobotSnapshot("test", 0.0, 2951.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Y coordinate for snapshot should be 2951.0.", 2951.0, s.getY(), 0.0);

      s = new RobotSnapshot("test", 0.0, -2951.92765283975256, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Y coordinate for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getY(),
                          0.0);
   }

   /**
    * Test method for {@link RobotSnapshot#getEnergy()}.
    */
   @Test
   public void testGetEnergy() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Energy for snapshot should be 0.0.", 0.0, s.getEnergy(), 0.0);

      s = new RobotSnapshot("test", 0.0, 0.0, 2951.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Energy for snapshot should be 2951.0.", 2951.0, s.getEnergy(), 0.0);

      s = new RobotSnapshot("test", 0.0, 0.0, -2951.92765283975256, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Energy for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getEnergy(),
                          0.0);
   }

   /**
    * Test method for {@link RobotSnapshot#getHeading()}.
    */
   @Test
   public void testGetHeading() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Heading for snapshot should be 0.0.", 0.0, s.getHeading(), 0.0);

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 2951.0, 0.0, 0, 0);
      Assert.assertEquals("Heading for snapshot should be -2951.0.", 2951.0, s.getHeading(), 0.0);

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, -2951.92765283975256, 0.0, 0, 0);
      Assert.assertEquals("Heading for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getHeading(),
                          0.0);
   }

   /**
    * Test method for {@link RobotSnapshot#getVelocity()}.
    */
   @Test
   public void testGetVelocity() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Velocity for snapshot should be 0.0.", 0.0, s.getVelocity(), 0.0);

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 2951.0, 0, 0);
      Assert.assertEquals("Velocity for snapshot should be -2951.0.", 2951.0, s.getVelocity(), 0.0);

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, -2951.92765283975256, 0, 0);
      Assert.assertEquals("Velocity for snapshot should be -2951.92765283975256.", -2951.92765283975256,
                          s.getVelocity(), 0.0);
   }

   /**
    * Test method for {@link RobotSnapshot#getTime()}.
    */
   @Test
   public void testGetTime() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Time for snapshot should be 0.", 0, s.getTime());

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 2951, 0);
      Assert.assertEquals("Time for snapshot should be 2951.", 2951, s.getTime());

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, -2951, 0);
      Assert.assertEquals("Time for snapshot should be -2951.", -2951, s.getTime());
   }

   /**
    * Test method for {@link RobotSnapshot#getRound()}.
    */
   @Test
   public void testGetRound() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Round for snapshot should be 0.", 0, s.getRound());

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 2951);
      Assert.assertEquals("Round for snapshot should be 2951.", 2951, s.getRound());

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, -2951);
      Assert.assertEquals("Round for snapshot should be -2951.", -2951, s.getRound());
   }

   /**
    * Test method for {@link RobotSnapshot#toString()}.
    */
   @Test
   public void testToString() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      String str1 = s.getClass().getName() + "[n:" + s.getName() + " c:" + "(" + Format.NO_DEC.format(s.getX()) + ", "
              + Format.NO_DEC.format(s.getY()) + ")" + " e:" + Format.ONE_DEC.format(s.getEnergy()) + " h:" + Format
              .THREE_DEC.format(s.getHeading()) + " v:" + Format.NO_DEC.format(s.getVelocity()) + " t:" + s.getTime()
              + " r:" + s.getRound() + "]";
      Assert.assertEquals("String for s should be " + str1, str1, s.toString());

      String str2 = s.getClass().getName();
      Assert.assertFalse("String for s should not be " + str2, str2.equals(s.toString()));
   }

   /**
    * Test method for {@link RobotSnapshot#hashCode()}.
    */
   @Test
   public void testHashCode() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Hash code for snapshot should be " + "test".hashCode() + ".", "test".hashCode(),
                          s.hashCode());

      s = new RobotSnapshot("test.-#;lkasd39uvn2", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      Assert.assertEquals("Hash code for snapshot should be " + "test.-#;lkasd39uvn2".hashCode() + ".",
                          "test.-#;lkasd39uvn2".hashCode(), s.hashCode());
   }

   /**
    * Test method for {@link RobotSnapshot#equals(java.lang.Object)}.
    */
   @Test
   public void testEqualsObject() {
      RobotSnapshot s1 = new RobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42);
      Assert.assertTrue("Snapshot s1 should be equal to itself.", s1.equals(s1));

      RobotSnapshot s2 = new RobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42);
      Assert.assertFalse("Snapshots s1 and s2 should not be the same object.", (s1 == s2));
      Assert.assertTrue("Snapshots s1 and s2 should be equal.", s1.equals(s2));

      RobotSnapshot s3 = new RobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 43);
      Assert.assertFalse("Snapshots s1 and s3 should not be the same object.", (s1 == s3));
      Assert.assertFalse("Snapshots s1 and s3 should not be equal.", s1.equals(s3));

      Assert.assertFalse("Snapshots s2 and s3 should not be the same object.", (s2 == s3));
      Assert.assertFalse("Snapshots s2 and s3 should not be equal.", s2.equals(s3));
   }
}
