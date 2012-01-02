package kid.robots;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * TODO assert messages
 * 
 * @author Brian Norman
 * @version 1.0
 */
public class AbstractRobotSnapshotTest {

   /**
    * Test method for {@link AbstractRobotSnapshot#AbstractRobotSnapshot()}.
    */
   @Test
   public void testAbstractRobotSnapshot() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot() {
      };

      assertEquals("", new String(), s.getName());
      assertEquals("", -1.0, s.getX(), 0.0);
      assertEquals("", -1.0, s.getY(), 0.0);
      assertEquals("", -1.0, s.getEnergy(), 0.0);
      assertEquals("", 0.0, s.getHeading(), 0.0);
      assertEquals("", 0.0, s.getVelocity(), 0.0);
      assertEquals("", -1, s.getTime());
      assertEquals("", -1, s.getRound());
   }

   /**
    * Test method for
    * {@link AbstractRobotSnapshot#AbstractRobotSnapshot(java.lang.String, double, double, double, double, double, long, int)}
    * .
    */
   @Test
   public void testAbstractRobotSnapshotStringDoubleDoubleDoubleDoubleDoubleLongInt() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", "test", s.getName());
      assertEquals("", 0.0, s.getX(), 0.0);
      assertEquals("", 0.0, s.getY(), 0.0);
      assertEquals("", 0.0, s.getEnergy(), 0.0);
      assertEquals("", 0.0, s.getHeading(), 0.0);
      assertEquals("", 0.0, s.getVelocity(), 0.0);
      assertEquals("", 0, s.getTime());
      assertEquals("", 0, s.getRound());

      s = new AbstractRobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42) {
      };

      assertEquals("", "TEST", s.getName());
      assertEquals("", 5.0, s.getX(), 0.0);
      assertEquals("", 3.0, s.getY(), 0.0);
      assertEquals("", 200.0, s.getEnergy(), 0.0);
      assertEquals("", -70.0, s.getHeading(), 0.0);
      assertEquals("", 23.0, s.getVelocity(), 0.0);
      assertEquals("", 34, s.getTime());
      assertEquals("", 42, s.getRound());
   }

   /**
    * Test method for
    * {@link AbstractRobotSnapshot#AbstractRobotSnapshot(kid.robots.IRobotSnapshot)}
    * .
    */
   @Test
   public void testAbstractRobotSnapshotIRobotSnapshot() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42) {
      };

      assertEquals("", "TEST", s.getName());
      assertEquals("", 5.0, s.getX(), 0.0);
      assertEquals("", 3.0, s.getY(), 0.0);
      assertEquals("", 200.0, s.getEnergy(), 0.0);
      assertEquals("", -70.0, s.getHeading(), 0.0);
      assertEquals("", 23.0, s.getVelocity(), 0.0);
      assertEquals("", 34, s.getTime());
      assertEquals("", 42, s.getRound());

      AbstractRobotSnapshot copy = new AbstractRobotSnapshot(s) {
      };

      assertEquals("", "TEST", copy.getName());
      assertEquals("", 5.0, copy.getX(), 0.0);
      assertEquals("", 3.0, copy.getY(), 0.0);
      assertEquals("", 200.0, copy.getEnergy(), 0.0);
      assertEquals("", -70.0, copy.getHeading(), 0.0);
      assertEquals("", 23.0, copy.getVelocity(), 0.0);
      assertEquals("", 34, copy.getTime());
      assertEquals("", 42, copy.getRound());
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getName()}.
    */
   @Test
   public void testGetName() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", "test", s.getName());

      s = new AbstractRobotSnapshot("test.-#;lkasd39uvn2", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", "test.-#;lkasd39uvn2", s.getName());
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getX()}.
    */
   @Test
   public void testGetX() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", 0.0, s.getX(), 0.0);

      s = new AbstractRobotSnapshot("test", 2951.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", 2951.0, s.getX(), 0.0);

      s = new AbstractRobotSnapshot("test", -2951.92765283975256, 0.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", -2951.92765283975256, s.getX(), 0.0);
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getY()}.
    */
   @Test
   public void testGetY() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", 0.0, s.getY(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 2951.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", 2951.0, s.getY(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, -2951.92765283975256, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", -2951.92765283975256, s.getY(), 0.0);
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getEnergy()}.
    */
   @Test
   public void testGetEnergy() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", 0.0, s.getEnergy(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 2951.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", 2951.0, s.getEnergy(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, -2951.92765283975256, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", -2951.92765283975256, s.getEnergy(), 0.0);
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getHeading()}.
    */
   @Test
   public void testGetHeading() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", 0.0, s.getHeading(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 2951.0, 0.0, 0, 0) {
      };

      assertEquals("", 2951.0, s.getHeading(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, -2951.92765283975256, 0.0, 0, 0) {
      };

      assertEquals("", -2951.92765283975256, s.getHeading(), 0.0);
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getVelocity()}.
    */
   @Test
   public void testGetVelocity() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", 0.0, s.getVelocity(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 2951.0, 0, 0) {
      };

      assertEquals("", 2951.0, s.getVelocity(), 0.0);

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, -2951.92765283975256, 0, 0) {
      };

      assertEquals("", -2951.92765283975256, s.getVelocity(), 0.0);
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getTime()}.
    */
   @Test
   public void testGetTime() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", 0, s.getTime());

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 2951, 0) {
      };

      assertEquals("", 2951, s.getTime());

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, -2951, 0) {
      };

      assertEquals("", -2951, s.getTime());
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#getRound()}.
    */
   @Test
   public void testGetRound() {
      AbstractRobotSnapshot s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", 0, s.getRound());

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 2951) {
      };

      assertEquals("", 2951, s.getRound());

      s = new AbstractRobotSnapshot("test", 0.0, 0.0, 0.0, 0.0, 0.0, 0, -2951) {
      };

      assertEquals("", -2951, s.getRound());
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
      };

      assertEquals("", "test".hashCode(), s.hashCode());

      s = new AbstractRobotSnapshot("test.-#;lkasd39uvn2", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0) {
      };

      assertEquals("", "test.-#;lkasd39uvn2".hashCode(), s.hashCode());
   }

   /**
    * Test method for {@link AbstractRobotSnapshot#equals(java.lang.Object)}.
    */
   @Test
   public void testEqualsObject() {
      AbstractRobotSnapshot s1 = new AbstractRobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42) {
      };
      AbstractRobotSnapshot s2 = new AbstractRobotSnapshot("TEST", 5.0, 3.0, 200.0, -70.0, 23.0, 34, 42) {
      };

      assertFalse("", (s1 == s2));
      assertTrue("", s1.equals(s2));
   }

}
