package bnorm.robots;

import org.junit.Test;

import static bnorm.utils.Format.coordinateDec0;
import static bnorm.utils.Format.dec0;
import static bnorm.utils.Format.dec1;
import static bnorm.utils.Format.dec3;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
      assertEquals("Name for snapshot should be an empty string.", "", s.getName());
      assertEquals("X coordinate for snapshot should be -1.0.", -1.0, s.getX(), 0.0);
      assertEquals("Y coordinate for snapshot should be -1.0.", -1.0, s.getY(), 0.0);
      assertEquals("Energy for snapshot should be -1.0.", -1.0, s.getEnergy(), 0.0);
      assertEquals("Heading for snapshot should be 0.0.", 0.0, s.getHeading(), 0.0);
      assertEquals("Velocity for snapshot should be 0.0.", 0.0, s.getVelocity(), 0.0);
      assertEquals("Time for snapshot should be -1.", -1, s.getTime());
      assertEquals("Round for snapshot should be -1.", -1, s.getRound());
   }

   /**
    * Test method for
    * {@link RobotSnapshot#RobotSnapshot(java.lang.String, double, double, double, double, double, long, int)}.
    */
   @Test
   public void testRobotSnapshotStringDoubleDoubleDoubleDoubleDoubleLongInt() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("Name for snapshot should be \'test\'.", "test", s.getName());
      assertEquals("X coordinate for snapshot should be 0.0.", 0.0, s.getX(), 0.0);
      assertEquals("Y coordinate for snapshot should be 0.0.", 0.0, s.getY(), 0.0);
      assertEquals("Energy for snapshot should be 0.0.", 0.0, s.getEnergy(), 0.0);
      assertEquals("Heading for snapshot should be 0.0.", 0.0, s.getHeading(), 0.0);
      assertEquals("Velocity for snapshot should be 0.0.", 0.0, s.getVelocity(), 0.0);
      assertEquals("Time for snapshot should be 0.", 0, s.getTime());
      assertEquals("Round for snapshot should be 0.", 0, s.getRound());

      s = new RobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42);
      assertEquals("Name for snapshot should be \'TEST\'.", "TEST", s.getName());
      assertEquals("X coordinate for snapshot should be 5.0.", 5.0, s.getX(), 0.0);
      assertEquals("Y coordinate for snapshot should be 3.0.", 3.0, s.getY(), 0.0);
      assertEquals("Energy for snapshot should be 200.0.", 200.0, s.getEnergy(), 0.0);
      assertEquals("Heading for snapshot should be -70.0.", -70.0, s.getHeading(), 0.0);
      assertEquals("Velocity for snapshot should be 23.0.", 23.0, s.getVelocity(), 0.0);
      assertEquals("Time for snapshot should be 34.", 34, s.getTime());
      assertEquals("Round for snapshot should be 42.", 42, s.getRound());
   }

   /**
    * Test method for {@link RobotSnapshot#RobotSnapshot(bnorm.robots.IRobotSnapshot)}.
    */
   @Test
   public void testRobotSnapshotIRobotSnapshot() {
      RobotSnapshot s = new RobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42);
      assertEquals("Name for snapshot should be \'TEST\'.", "TEST", s.getName());
      assertEquals("X coordinate for snapshot should be 5.0.", 5.0, s.getX(), 0.0);
      assertEquals("Y coordinate for snapshot should be 3.0.", 3.0, s.getY(), 0.0);
      assertEquals("Energy for snapshot should be 200.0.", 200.0, s.getEnergy(), 0.0);
      assertEquals("Heading for snapshot should be -70.0.", -70.0, s.getHeading(), 0.0);
      assertEquals("Velocity for snapshot should be 23.0.", 23.0, s.getVelocity(), 0.0);
      assertEquals("Time for snapshot should be 34.", 34, s.getTime());
      assertEquals("Round for snapshot should be 42.", 42, s.getRound());

      RobotSnapshot copy = new RobotSnapshot(s);
      assertEquals("Name for snapshot should be \'TEST\'.", "TEST", copy.getName());
      assertEquals("X coordinate for snapshot should be 5.0.", 5.0, copy.getX(), 0.0);
      assertEquals("Y coordinate for snapshot should be 3.0.", 3.0, copy.getY(), 0.0);
      assertEquals("Energy for snapshot should be 200.0.", 200.0, copy.getEnergy(), 0.0);
      assertEquals("Heading for snapshot should be -70.0.", -70.0, copy.getHeading(), 0.0);
      assertEquals("Velocity for snapshot should be 23.0.", 23.0, copy.getVelocity(), 0.0);
      assertEquals("Time for snapshot should be 34.", 34, copy.getTime());
      assertEquals("Round for snapshot should be 42.", 42, copy.getRound());
   }

   /**
    * Test method for {@link RobotSnapshot#getName()}.
    */
   @Test
   public void testGetName() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("Name for snapshot should be \'test\'.", "test", s.getName());

      s = new RobotSnapshot("test.-#;lkasd39uvn2", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("Name for snapshot should be \'test.-#;lkasd39uvn2\'.", "test.-#;lkasd39uvn2", s.getName());
   }

   /**
    * Test method for {@link RobotSnapshot#getX()}.
    */
   @Test
   public void testGetX() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("X coordinate for snapshot should be 0.0.", 0.0, s.getX(), 0.0);

      s = new RobotSnapshot("test", 2951.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("X coordinate for snapshot should be 2951.0.", 2951.0, s.getX(), 0.0);

      s = new RobotSnapshot("test", -2951.92765283975256, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("X coordinate for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getX(), 0.0);
   }

   /**
    * Test method for {@link RobotSnapshot#getY()}.
    */
   @Test
   public void testGetY() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("Y coordinate for snapshot should be 0.0.", 0.0, s.getY(), 0.0);

      s = new RobotSnapshot("test", 0.0, 2951.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("Y coordinate for snapshot should be 2951.0.", 2951.0, s.getY(), 0.0);

      s = new RobotSnapshot("test", 0.0, -2951.92765283975256, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("Y coordinate for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getY(), 0.0);
   }

   /**
    * Test method for {@link RobotSnapshot#getEnergy()}.
    */
   @Test
   public void testGetEnergy() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("Energy for snapshot should be 0.0.", 0.0, s.getEnergy(), 0.0);

      s = new RobotSnapshot("test", 0.0, 0.0, 2951.0, 0.0, 0.0, 0, 0);
      assertEquals("Energy for snapshot should be 2951.0.", 2951.0, s.getEnergy(), 0.0);

      s = new RobotSnapshot("test", 0.0, 0.0, -2951.92765283975256, 0.0, 0.0, 0, 0);
      assertEquals("Energy for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getEnergy(), 0.0);
   }

   /**
    * Test method for {@link RobotSnapshot#getHeading()}.
    */
   @Test
   public void testGetHeading() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("Heading for snapshot should be 0.0.", 0.0, s.getHeading(), 0.0);

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 2951.0, 0.0, 0, 0);
      assertEquals("Heading for snapshot should be -2951.0.", 2951.0, s.getHeading(), 0.0);

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, -2951.92765283975256, 0.0, 0, 0);
      assertEquals("Heading for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getHeading(), 0.0);
   }

   /**
    * Test method for {@link RobotSnapshot#getVelocity()}.
    */
   @Test
   public void testGetVelocity() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("Velocity for snapshot should be 0.0.", 0.0, s.getVelocity(), 0.0);

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 2951.0, 0, 0);
      assertEquals("Velocity for snapshot should be -2951.0.", 2951.0, s.getVelocity(), 0.0);

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, -2951.92765283975256, 0, 0);
      assertEquals("Velocity for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getVelocity(), 0.0);
   }

   /**
    * Test method for {@link RobotSnapshot#getTime()}.
    */
   @Test
   public void testGetTime() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("Time for snapshot should be 0.", 0, s.getTime());

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 2951, 0);
      assertEquals("Time for snapshot should be 2951.", 2951, s.getTime());

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, -2951, 0);
      assertEquals("Time for snapshot should be -2951.", -2951, s.getTime());
   }

   /**
    * Test method for {@link RobotSnapshot#getRound()}.
    */
   @Test
   public void testGetRound() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("Round for snapshot should be 0.", 0, s.getRound());

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 2951);
      assertEquals("Round for snapshot should be 2951.", 2951, s.getRound());

      s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, -2951);
      assertEquals("Round for snapshot should be -2951.", -2951, s.getRound());
   }

   /**
    * Test method for {@link RobotSnapshot#toString()}.
    */
   @Test
   public void testToString() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      String str1 =
              s.getClass().getName() + "[n:" + s.getName() + " c:" + coordinateDec0(s.getX(), s.getY()) + " e:" + dec1(
                      s.getEnergy()) + " h:" + dec3(s.getHeading()) + " v:" + dec0(s.getVelocity()) + " t:" + s
                      .getTime() + " r:" + s.getRound() + "]";
      assertEquals("String for s should be " + str1, str1, s.toString());

      String str2 = s.getClass().getName();
      assertFalse("String for s should not be " + str2, str2.equals(s.toString()));
   }

   /**
    * Test method for {@link RobotSnapshot#hashCode()}.
    */
   @Test
   public void testHashCode() {
      RobotSnapshot s = new RobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("Hash code for snapshot should be " + "test".hashCode() + ".", "test".hashCode(), s.hashCode());

      s = new RobotSnapshot("test.-#;lkasd39uvn2", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0);
      assertEquals("Hash code for snapshot should be " + "test.-#;lkasd39uvn2".hashCode() + ".",
                   "test.-#;lkasd39uvn2".hashCode(), s.hashCode());
   }

   /**
    * Test method for {@link RobotSnapshot#equals(java.lang.Object)}.
    */
   @Test
   public void testEqualsObject() {
      RobotSnapshot s1 = new RobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42);
      assertTrue("Snapshot s1 should be equal to itself.", s1.equals(s1));

      RobotSnapshot s2 = new RobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42);
      assertFalse("Snapshots s1 and s2 should not be the same object.", (s1 == s2));
      assertTrue("Snapshots s1 and s2 should be equal.", s1.equals(s2));

      RobotSnapshot s3 = new RobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 43);
      assertFalse("Snapshots s1 and s3 should not be the same object.", (s1 == s3));
      assertFalse("Snapshots s1 and s3 should not be equal.", s1.equals(s3));

      assertFalse("Snapshots s2 and s3 should not be the same object.", (s2 == s3));
      assertFalse("Snapshots s2 and s3 should not be equal.", s2.equals(s3));
   }
}
