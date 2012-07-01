package kid.robots;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for {@link AbstractRobotSnapshotTest}.
 * 
 * @author Brian Norman
 * @version 1.1
 */
public class AbstractRobotSnapshotTest {

   /**
    * Test method for {@link AbstractRobotSnapshot#AbstractRobotSnapshot()}.
    */
   @Test
   public void testAbstractRobotSnapshot() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot() {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Name for snapshot should be an empty string.", new String(), s.getName());
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
    * {@link AbstractRobotSnapshot#AbstractRobotSnapshot(java.lang.String, double, double, double, double, double, long, int)}
    * .
    */
   @Test
   public void testAbstractRobotSnapshotStringDoubleDoubleDoubleDoubleDoubleLongInt() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Name for snapshot should be \'test\'.", "test", s.getName());
      assertEquals("X coordinate for snapshot should be 0.0.", 0.0, s.getX(), 0.0);
      assertEquals("Y coordinate for snapshot should be 0.0.", 0.0, s.getY(), 0.0);
      assertEquals("Energy for snapshot should be 0.0.", 0.0, s.getEnergy(), 0.0);
      assertEquals("Heading for snapshot should be 0.0.", 0.0, s.getHeading(), 0.0);
      assertEquals("Velocity for snapshot should be 0.0.", 0.0, s.getVelocity(), 0.0);
      assertEquals("Time for snapshot should be 0.", 0, s.getTime());
      assertEquals("Round for snapshot should be 0.", 0, s.getRound());

      s = new AbstractRobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42) {
         private static final long serialVersionUID = 1L;
      };

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
    * Test method for
    * {@link AbstractRobotSnapshot#AbstractRobotSnapshot(kid.robots.IRobotSnapshot)}
    * .
    */
   @Test
   public void testAbstractRobotSnapshotIRobotSnapshot() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Name for snapshot should be \'TEST\'.", "TEST", s.getName());
      assertEquals("X coordinate for snapshot should be 5.0.", 5.0, s.getX(), 0.0);
      assertEquals("Y coordinate for snapshot should be 3.0.", 3.0, s.getY(), 0.0);
      assertEquals("Energy for snapshot should be 200.0.", 200.0, s.getEnergy(), 0.0);
      assertEquals("Heading for snapshot should be -70.0.", -70.0, s.getHeading(), 0.0);
      assertEquals("Velocity for snapshot should be 23.0.", 23.0, s.getVelocity(), 0.0);
      assertEquals("Time for snapshot should be 34.", 34, s.getTime());
      assertEquals("Round for snapshot should be 42.", 42, s.getRound());

      AbstractRobotSnapshot copy = new AbstractRobotSnapshot(s) {
         private static final long serialVersionUID = 1L;
      };

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
    * Test method for {@link AbstractRobotSnapshot#getName()}.
    */
   @Test
   public void testGetName() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Name for snapshot should be \'test\'.", "test", s.getName());

      s = new AbstractRobotSnapshot("test.-#;lkasd39uvn2", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Name for snapshot should be \'test.-#;lkasd39uvn2\'.", "test.-#;lkasd39uvn2", s.getName());
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getX()}.
    */
   @Test
   public void testGetX() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("X coordinate for snapshot should be 0.0.", 0.0, s.getX(), 0.0);

      s = new AbstractRobotSnapshot("test", 2951.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("X coordinate for snapshot should be 2951.0.", 2951.0, s.getX(), 0.0);

      s = new AbstractRobotSnapshot("test", -2951.92765283975256, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("X coordinate for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getX(), 0.0);
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getY()}.
    */
   @Test
   public void testGetY() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Y coordinate for snapshot should be 0.0.", 0.0, s.getY(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 2951.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Y coordinate for snapshot should be 2951.0.", 2951.0, s.getY(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, -2951.92765283975256, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Y coordinate for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getY(), 0.0);
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getEnergy()}.
    */
   @Test
   public void testGetEnergy() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Energy for snapshot should be 0.0.", 0.0, s.getEnergy(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 2951.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Energy for snapshot should be 2951.0.", 2951.0, s.getEnergy(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, -2951.92765283975256, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Energy for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getEnergy(), 0.0);
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getHeading()}.
    */
   @Test
   public void testGetHeading() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Heading for snapshot should be 0.0.", 0.0, s.getHeading(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 2951.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Heading for snapshot should be -2951.0.", 2951.0, s.getHeading(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, -2951.92765283975256, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Heading for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getHeading(), 0.0);
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getVelocity()}.
    */
   @Test
   public void testGetVelocity() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Velocity for snapshot should be 0.0.", 0.0, s.getVelocity(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 2951.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Velocity for snapshot should be -2951.0.", 2951.0, s.getVelocity(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, -2951.92765283975256, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Velocity for snapshot should be -2951.92765283975256.", -2951.92765283975256, s.getVelocity(), 0.0);
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getTime()}.
    */
   @Test
   public void testGetTime() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Time for snapshot should be 0.", 0, s.getTime());

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 2951, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Time for snapshot should be 2951.", 2951, s.getTime());

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, -2951, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Time for snapshot should be -2951.", -2951, s.getTime());
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getRound()}.
    */
   @Test
   public void testGetRound() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Round for snapshot should be 0.", 0, s.getRound());

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 2951) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Round for snapshot should be 2951.", 2951, s.getRound());

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, -2951) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Round for snapshot should be -2951.", -2951, s.getRound());
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#toString()}.
    */
   @Test
   public void testToString() {
      // TODO decide how to test this
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#hashCode()}.
    */
   @Test
   public void testHashCode() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Hash code for snapshot should be " + "test".hashCode() + ".", "test".hashCode(), s.hashCode());

      s = new AbstractRobotSnapshot("test.-#;lkasd39uvn2", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
         private static final long serialVersionUID = 1L;
      };

      assertEquals("Hash code for snapshot should be " + "test.-#;lkasd39uvn2".hashCode() + ".",
            "test.-#;lkasd39uvn2".hashCode(), s.hashCode());
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#equals(java.lang.Object)}.
    */
   @Test
   public void testEqualsObject() {
      AbstractRobotSnapshot s1 = new AbstractRobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42) {
         private static final long serialVersionUID = 1L;
      };
      AbstractRobotSnapshot s2 = new AbstractRobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42) {
         private static final long serialVersionUID = 1L;
      };

      assertFalse("Snapshots s1 and s2 should not be the same object.", (s1 == s2));
      assertTrue("Snapshots s1 and s2 should be equal.", s1.equals(s2));
   }

}
