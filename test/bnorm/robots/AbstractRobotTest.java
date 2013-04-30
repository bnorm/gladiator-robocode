package bnorm.robots;

import org.junit.Test;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Test class for {@link AbstractRobot}.
 *
 * @author Brian Norman
 * @version 1.2
 */
public class AbstractRobotTest {

   /**
    * Test method for {@link AbstractRobot#AbstractRobot()}.
    */
   @Test
   public void testAbstractRobot() {
      AbstractRobot r = new AbstractRobot() {
      };

      // Testing snapshot assumptions
      assertNotNull("Snapshot should not be null.", r.getSnapshot());
      assertNotNull("Snapshot should not be null.", r.getSnapshot(0));
      assertNotNull("Snapshot should not be null.", r.getSnapshot(3));
      assertNotNull("Snapshot should not be null.", r.getSnapshot(0, 0));
      assertNotNull("Snapshot should not be null.", r.getSnapshot(3, 0));
      assertNotNull("Snapshot should not be null.", r.getSnapshot(0, 3));
      assertNotNull("Snapshot should not be null.", r.getSnapshot(3, 3));

      try {
         r.getSnapshot(-1);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      try {
         r.getSnapshot(-1, 0);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      try {
         r.getSnapshot(0, -1);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      try {
         r.getSnapshot(-1, -1);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      // Testing movie assumptions
      ListIterator<IRobotSnapshot> iter = r.getMovie();
      assertNotNull("ListIterator should not be null.", iter);

      iter = r.getMovie(0);
      assertNotNull("ListIterator should not be null.", iter);

      iter = r.getMovie(3);
      assertNotNull("ListIterator should not be null.", iter);

      iter = r.getMovie(0, 0);
      assertNotNull("ListIterator should not be null.", iter);

      iter = r.getMovie(3, 0);
      assertNotNull("ListIterator should not be null.", iter);

      iter = r.getMovie(0, 3);
      assertNotNull("ListIterator should not be null.", iter);

      iter = r.getMovie(3, 3);
      assertNotNull("ListIterator should not be null.", iter);

      try {
         r.getMovie(-1);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      try {
         r.getMovie(-1, 0);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      try {
         r.getMovie(0, -1);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      try {
         r.getMovie(-1, -1);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      // Testing round set assumptions
      Set<Integer> set = r.getRounds();
      assertEquals("Set should be of size zero.", 0, set.size());
   }

   /**
    * Test method for {@link AbstractRobot#AbstractRobot(String)}.
    */
   @Test
   public void testAbstractRobotString() {
      AbstractRobot r = new AbstractRobot("Name") {
      };

      // Testing snapshot assumptions
      assertNotNull("Snapshot should not be null.", r.getSnapshot());
      assertNotNull("Snapshot should not be null.", r.getSnapshot(0));
      assertNotNull("Snapshot should not be null.", r.getSnapshot(3));
      assertNotNull("Snapshot should not be null.", r.getSnapshot(0, 0));
      assertNotNull("Snapshot should not be null.", r.getSnapshot(3, 0));
      assertNotNull("Snapshot should not be null.", r.getSnapshot(0, 3));
      assertNotNull("Snapshot should not be null.", r.getSnapshot(3, 3));

      try {
         r.getSnapshot(-1);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      try {
         r.getSnapshot(-1, 0);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      try {
         r.getSnapshot(0, -1);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      try {
         r.getSnapshot(-1, -1);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      // Testing movie assumptions
      ListIterator<IRobotSnapshot> iter = r.getMovie();
      assertNotNull("ListIterator should not be null.", iter);

      iter = r.getMovie(0);
      assertNotNull("ListIterator should not be null.", iter);

      iter = r.getMovie(3);
      assertNotNull("ListIterator should not be null.", iter);

      iter = r.getMovie(0, 0);
      assertNotNull("ListIterator should not be null.", iter);

      iter = r.getMovie(3, 0);
      assertNotNull("ListIterator should not be null.", iter);

      iter = r.getMovie(0, 3);
      assertNotNull("ListIterator should not be null.", iter);

      iter = r.getMovie(3, 3);
      assertNotNull("ListIterator should not be null.", iter);

      try {
         r.getMovie(-1);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      try {
         r.getMovie(-1, 0);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      try {
         r.getMovie(0, -1);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      try {
         r.getMovie(-1, -1);
         fail("Method should throw an error.");
      } catch (Exception e) {
         assertTrue("Method should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      // Testing round set assumptions
      Set<Integer> set = r.getRounds();
      assertEquals("Set should be of size zero.", 0, set.size());
   }

   /**
    * Test method for {@link AbstractRobot#AbstractRobot(IRobot)}.
    */
   @Test
   public void testAbstractRobotIRobot() {
      String name = "Name";
      AbstractRobot r = new AbstractRobot(name) {
      };

      RobotSnapshot s1 = new RobotSnapshot(name, 0, 0, 0, 0, 0, 1, 0);
      RobotSnapshot s4 = new RobotSnapshot(name, 0, 0, 0, 0, 0, 4, 0);
      RobotSnapshot s2 = new RobotSnapshot(name, 0, 0, 0, 0, 0, 2, 1);
      RobotSnapshot s5 = new RobotSnapshot(name, 0, 0, 0, 0, 0, 5, 1);
      RobotSnapshot s3 = new RobotSnapshot(name, 0, 0, 0, 0, 0, 3, 2);

      r.add(s1);
      r.add(s2);
      r.add(s5);
      r.add(s4);
      r.add(s3);

      AbstractRobot copy = new AbstractRobot(r) {
      };

      assertEquals("The name of the robot should be " + name + ".", name, r.getName());

      assertEquals("Snapshot at time=0 in round 0 should be s1", s1, copy.getSnapshot(0, 0));
      assertEquals("Snapshot at time=1 in round 0 should be s1", s1, copy.getSnapshot(1, 0));
      assertEquals("Snapshot at time=2 in round 0 should be s1", s1, copy.getSnapshot(2, 0));
      assertEquals("Snapshot at time=3 in round 0 should be s1", s1, copy.getSnapshot(3, 0));
      assertEquals("Snapshot at time=4 in round 0 should be s4", s4, copy.getSnapshot(4, 0));
      assertEquals("Snapshot at time=5 in round 0 should be s4", s4, copy.getSnapshot(5, 0));
      assertEquals("Snapshot at time=6 in round 0 should be s4", s4, copy.getSnapshot(6, 0));

      assertEquals("Snapshot at time=0 in round 1 should be s2", s2, copy.getSnapshot(0, 1));
      assertEquals("Snapshot at time=1 in round 1 should be s2", s2, copy.getSnapshot(1, 1));
      assertEquals("Snapshot at time=2 in round 1 should be s2", s2, copy.getSnapshot(2, 1));
      assertEquals("Snapshot at time=3 in round 1 should be s2", s2, copy.getSnapshot(3, 1));
      assertEquals("Snapshot at time=4 in round 1 should be s2", s2, copy.getSnapshot(4, 1));
      assertEquals("Snapshot at time=5 in round 1 should be s5", s5, copy.getSnapshot(5, 1));
      assertEquals("Snapshot at time=6 in round 1 should be s5", s5, copy.getSnapshot(6, 1));

      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, copy.getSnapshot(0, 2));
      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, copy.getSnapshot(1, 2));
      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, copy.getSnapshot(2, 2));
      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, copy.getSnapshot(3, 2));
      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, copy.getSnapshot(4, 2));
      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, copy.getSnapshot(5, 2));
      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, copy.getSnapshot(6, 2));

      assertNotNull("Snapshot at time=0 in round 3 should not be null", copy.getSnapshot(0, 3));
      assertEquals("Snapshot at time=0 in round 3 should be blank", "", copy.getSnapshot(0, 3).getName());
      assertTrue("Snapshot at time=0 in round 3 should be blank", copy.getSnapshot(0, 3).getEnergy() < 0);

      assertNotNull("Snapshot at time=5 in round 3 should not be null", copy.getSnapshot(5, 3));
      assertEquals("Snapshot at time=5 in round 3 should be blank", "", copy.getSnapshot(5, 3).getName());
      assertTrue("Snapshot at time=5 in round 3 should be blank", copy.getSnapshot(5, 3).getEnergy() < 0);
   }

   /**
    * Test method for {@link AbstractRobot#getName()}.
    */
   @Test
   public void testGetName() {
      AbstractRobot r = new AbstractRobot("Name") {
      };

      assertEquals("The name of the robot should be Name.", "Name", r.getName());
   }

   /**
    * Test method for {@link AbstractRobot#add(IRobotSnapshot)}.
    */
   @Test
   public void testAdd() {
      RobotSnapshot s1 = new RobotSnapshot("", 0, 0, 0, 0, 0, 1, 0);
      RobotSnapshot s2 = new RobotSnapshot("", 0, 0, 0, 0, 0, 2, 0);
      RobotSnapshot s4 = new RobotSnapshot("", 0, 0, 0, 0, 0, 4, 0);
      RobotSnapshot s5 = new RobotSnapshot("", 0, 0, 0, 0, 0, 5, 0);
      RobotSnapshot s8 = new RobotSnapshot("", 0, 0, 0, 0, 0, 8, 0);

      AbstractRobot r = new AbstractRobot() {
      };
      r.add(s4);
      r.add(s1);
      r.add(s8);
      r.add(s2);
      r.add(s5);

      ListIterator<IRobotSnapshot> iter = r.getMovie();
      assertTrue("Movie should contain 5 elements.", iter.hasNext());
      assertEquals("First element should be s1.", s1, iter.next());
      assertTrue("Movie should contain 5 elements.", iter.hasNext());
      assertEquals("Second element should be s2.", s2, iter.next());
      assertTrue("Movie should contain 5 elements.", iter.hasNext());
      assertEquals("Third element should be s4.", s4, iter.next());
      assertTrue("Movie should contain 5 elements.", iter.hasNext());
      assertEquals("Fourth element should be s5.", s5, iter.next());
      assertTrue("Movie should contain 5 elements.", iter.hasNext());
      assertEquals("Fifth element should be s8.", s8, iter.next());

      assertFalse("Adding the same snapshot again should fail.", r.add(s1));
      assertFalse("Adding the same snapshot again should fail.", r.add(s2));
      assertFalse("Adding the same snapshot again should fail.", r.add(s4));
      assertFalse("Adding the same snapshot again should fail.", r.add(s5));
      assertFalse("Adding the same snapshot again should fail.", r.add(s8));

      RobotSnapshot s_other = new RobotSnapshot("Name", 0, 0, 0, 0, 0, 8, 0);

      try {
         r.add(s_other);
         fail("Add should throw an error.");
      } catch (Exception e) {
         assertTrue("Add should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }

      try {
         r.add(null);
         fail("Add should throw an error.");
      } catch (Exception e) {
         assertTrue("Add should throw an NullPointerException.", e instanceof NullPointerException);
      }
   }

   /**
    * Test method for {@link AbstractRobot#getSnapshot()}.
    */
   @Test
   public void testGetSnapshot() {
      RobotSnapshot s1 = new RobotSnapshot("", 0, 0, 0, 0, 0, 1, 0);
      RobotSnapshot s2 = new RobotSnapshot("", 0, 0, 0, 0, 0, 2, 0);
      RobotSnapshot s4 = new RobotSnapshot("", 0, 0, 0, 0, 0, 4, 0);
      RobotSnapshot s5 = new RobotSnapshot("", 0, 0, 0, 0, 0, 5, 0);
      RobotSnapshot s8 = new RobotSnapshot("", 0, 0, 0, 0, 0, 8, 0);

      AbstractRobot r = new AbstractRobot() {
      };

      r.add(s4);
      assertEquals("Most recent snapshot should be s4.", s4, r.getSnapshot());

      r.add(s1);
      assertEquals("Most recent snapshot should be s4.", s4, r.getSnapshot());

      r.add(s5);
      assertEquals("Most recent snapshot should be s5.", s5, r.getSnapshot());

      r.add(s2);
      assertEquals("Most recent snapshot should be s5.", s5, r.getSnapshot());

      r.add(s8);
      assertEquals("Most recent snapshot should be s8.", s8, r.getSnapshot());
   }

   /**
    * Test method for {@link AbstractRobot#getSnapshot(long)}.
    */
   @Test
   public void testGetSnapshotLong() {
      RobotSnapshot s1 = new RobotSnapshot("", 0, 0, 0, 0, 0, 1, 0);
      RobotSnapshot s2 = new RobotSnapshot("", 0, 0, 0, 0, 0, 2, 0);
      RobotSnapshot s4 = new RobotSnapshot("", 0, 0, 0, 0, 0, 4, 0);
      RobotSnapshot s5 = new RobotSnapshot("", 0, 0, 0, 0, 0, 5, 0);
      RobotSnapshot s8 = new RobotSnapshot("", 0, 0, 0, 0, 0, 8, 0);

      AbstractRobot r = new AbstractRobot() {
      };
      r.add(s1);
      r.add(s2);
      r.add(s4);
      r.add(s5);
      r.add(s8);

      assertEquals("Snapshot at time=0 should be s1", s1, r.getSnapshot(0));
      assertEquals("Snapshot at time=1 should be s1", s1, r.getSnapshot(1));
      assertEquals("Snapshot at time=2 should be s2", s2, r.getSnapshot(2));
      assertEquals("Snapshot at time=3 should be s2", s2, r.getSnapshot(3));
      assertEquals("Snapshot at time=4 should be s4", s4, r.getSnapshot(4));
      assertEquals("Snapshot at time=5 should be s5", s5, r.getSnapshot(5));
      assertEquals("Snapshot at time=6 should be s5", s5, r.getSnapshot(6));
      assertEquals("Snapshot at time=7 should be s5", s5, r.getSnapshot(7));
      assertEquals("Snapshot at time=8 should be s8", s8, r.getSnapshot(8));
      assertEquals("Snapshot at time=9 should be s8", s8, r.getSnapshot(9));
      assertEquals("Snapshot at time=10 should be s8", s8, r.getSnapshot(10));
   }

   /**
    * Test method for {@link AbstractRobot#getSnapshot(long, int)}.
    */
   @Test
   public void testGetSnapshotLongInt() {
      RobotSnapshot s1 = new RobotSnapshot("", 0, 0, 0, 0, 0, 1, 0);
      RobotSnapshot s4 = new RobotSnapshot("", 0, 0, 0, 0, 0, 4, 0);

      RobotSnapshot s2 = new RobotSnapshot("", 0, 0, 0, 0, 0, 2, 1);
      RobotSnapshot s5 = new RobotSnapshot("", 0, 0, 0, 0, 0, 5, 1);

      RobotSnapshot s3 = new RobotSnapshot("", 0, 0, 0, 0, 0, 3, 2);

      AbstractRobot r = new AbstractRobot() {
      };
      r.add(s1);
      r.add(s2);
      r.add(s5);
      r.add(s4);
      r.add(s3);

      assertEquals("Snapshot at time=0 in round 0 should be s1", s1, r.getSnapshot(0, 0));
      assertEquals("Snapshot at time=1 in round 0 should be s1", s1, r.getSnapshot(1, 0));
      assertEquals("Snapshot at time=2 in round 0 should be s1", s1, r.getSnapshot(2, 0));
      assertEquals("Snapshot at time=3 in round 0 should be s1", s1, r.getSnapshot(3, 0));
      assertEquals("Snapshot at time=4 in round 0 should be s4", s4, r.getSnapshot(4, 0));
      assertEquals("Snapshot at time=5 in round 0 should be s4", s4, r.getSnapshot(5, 0));
      assertEquals("Snapshot at time=6 in round 0 should be s4", s4, r.getSnapshot(6, 0));

      assertEquals("Snapshot at time=0 in round 1 should be s2", s2, r.getSnapshot(0, 1));
      assertEquals("Snapshot at time=1 in round 1 should be s2", s2, r.getSnapshot(1, 1));
      assertEquals("Snapshot at time=2 in round 1 should be s2", s2, r.getSnapshot(2, 1));
      assertEquals("Snapshot at time=3 in round 1 should be s2", s2, r.getSnapshot(3, 1));
      assertEquals("Snapshot at time=4 in round 1 should be s2", s2, r.getSnapshot(4, 1));
      assertEquals("Snapshot at time=5 in round 1 should be s5", s5, r.getSnapshot(5, 1));
      assertEquals("Snapshot at time=6 in round 1 should be s5", s5, r.getSnapshot(6, 1));

      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, r.getSnapshot(0, 2));
      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, r.getSnapshot(1, 2));
      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, r.getSnapshot(2, 2));
      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, r.getSnapshot(3, 2));
      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, r.getSnapshot(4, 2));
      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, r.getSnapshot(5, 2));
      assertEquals("Snapshot at time=0 in round 2 should be s3", s3, r.getSnapshot(6, 2));

      assertNotNull("Snapshot at time=0 in round 3 should not be null", r.getSnapshot(0, 3));
      assertEquals("Snapshot at time=0 in round 3 should be blank", "", r.getSnapshot(0, 3).getName());
      assertTrue("Snapshot at time=0 in round 3 should be blank", r.getSnapshot(0, 3).getEnergy() < 0);

      assertNotNull("Snapshot at time=5 in round 3 should not be null", r.getSnapshot(5, 3));
      assertEquals("Snapshot at time=5 in round 3 should be blank", "", r.getSnapshot(5, 3).getName());
      assertTrue("Snapshot at time=5 in round 3 should be blank", r.getSnapshot(5, 3).getEnergy() < 0);
   }

   /**
    * Test method for {@link AbstractRobot#getMovie()}.
    */
   @Test
   public void testGetMovie() {
      RobotSnapshot s1 = new RobotSnapshot("", 0, 0, 0, 0, 0, 1, 0);
      RobotSnapshot s2 = new RobotSnapshot("", 0, 0, 0, 0, 0, 2, 0);
      RobotSnapshot s4 = new RobotSnapshot("", 0, 0, 0, 0, 0, 4, 0);
      RobotSnapshot s5 = new RobotSnapshot("", 0, 0, 0, 0, 0, 5, 0);
      RobotSnapshot s8 = new RobotSnapshot("", 0, 0, 0, 0, 0, 8, 0);

      AbstractRobot r = new AbstractRobot() {
      };

      r.add(s1);
      r.add(s2);
      r.add(s4);
      r.add(s5);
      r.add(s8);

      ListIterator<IRobotSnapshot> iter = r.getMovie();
      assertTrue("Movie should contain 5 elements.", iter.hasNext());
      assertEquals("First element should be s1.", s1, iter.next());
      assertTrue("Movie should contain 5 elements.", iter.hasNext());
      assertEquals("Second element should be s2.", s2, iter.next());
      assertTrue("Movie should contain 5 elements.", iter.hasNext());
      assertEquals("Third element should be s4.", s4, iter.next());
      assertTrue("Movie should contain 5 elements.", iter.hasNext());
      assertEquals("Fourth element should be s5.", s5, iter.next());
      assertTrue("Movie should contain 5 elements.", iter.hasNext());
      assertEquals("Fifth element should be s8.", s8, iter.next());
   }

   /**
    * Test method for {@link AbstractRobot#getMovie(long)}.
    */
   @Test
   public void testGetMovieLong() {
      RobotSnapshot s1 = new RobotSnapshot("", 0, 0, 0, 0, 0, 1, 0);
      RobotSnapshot s2 = new RobotSnapshot("", 0, 0, 0, 0, 0, 2, 0);
      RobotSnapshot s4 = new RobotSnapshot("", 0, 0, 0, 0, 0, 4, 0);
      RobotSnapshot s5 = new RobotSnapshot("", 0, 0, 0, 0, 0, 5, 0);

      AbstractRobot r = new AbstractRobot() {
      };

      r.add(s1);
      r.add(s2);
      r.add(s4);
      r.add(s5);

      ListIterator<IRobotSnapshot> iter;

      iter = r.getMovie(0);
      assertTrue("Movie should contain 4 elements after start.", iter.hasNext());
      assertEquals("First element after start should be s1.", s1, iter.next());
      assertTrue("Movie should contain 4 elements after start.", iter.hasNext());
      assertEquals("Second element after start should be s2.", s2, iter.next());
      assertTrue("Movie should contain 4 elements after start.", iter.hasNext());
      assertEquals("Third element after start should be s4.", s4, iter.next());
      assertTrue("Movie should contain 4 elements after start.", iter.hasNext());
      assertEquals("Fourth element after start should be s5.", s5, iter.next());

      iter = r.getMovie(0);
      assertFalse("Movie should contain no elements before start.", iter.hasPrevious());

      iter = r.getMovie(1);
      assertTrue("Movie should contain 4 elements after start.", iter.hasNext());
      assertEquals("First element after start should be s1.", s1, iter.next());
      assertTrue("Movie should contain 4 elements after start.", iter.hasNext());
      assertEquals("Second element after start should be s2.", s2, iter.next());
      assertTrue("Movie should contain 4 elements after start.", iter.hasNext());
      assertEquals("Third element after start should be s4.", s4, iter.next());
      assertTrue("Movie should contain 4 elements after start.", iter.hasNext());
      assertEquals("Fourth element after start should be s5.", s5, iter.next());

      iter = r.getMovie(1);
      assertFalse("Movie should contain no elements before start.", iter.hasPrevious());

      iter = r.getMovie(2);
      assertTrue("Movie should contain 3 elements after start.", iter.hasNext());
      assertEquals("First element after start should be s2.", s2, iter.next());
      assertTrue("Movie should contain 3 elements after start.", iter.hasNext());
      assertEquals("Second element after start should be s4.", s4, iter.next());
      assertTrue("Movie should contain 3 elements after start.", iter.hasNext());
      assertEquals("Third element after start should be s5.", s5, iter.next());

      iter = r.getMovie(2);
      assertTrue("Movie should contain 1 elements before start.", iter.hasPrevious());
      assertEquals("First element before start should be s1.", s1, iter.previous());

      iter = r.getMovie(3);
      assertTrue("Movie should contain 2 elements after start.", iter.hasNext());
      assertEquals("First element after start should be s4.", s4, iter.next());
      assertTrue("Movie should contain 2 elements after start.", iter.hasNext());
      assertEquals("Second element after start should be s5.", s5, iter.next());

      iter = r.getMovie(3);
      assertTrue("Movie should contain 2 elements before start.", iter.hasPrevious());
      assertEquals("First element before start should be s2.", s2, iter.previous());
      assertTrue("Movie should contain 2 elements before start.", iter.hasPrevious());
      assertEquals("Second element before start should be s1.", s1, iter.previous());

      iter = r.getMovie(4);
      assertTrue("Movie should contain 2 elements after start.", iter.hasNext());
      assertEquals("First element after start should be s4.", s4, iter.next());
      assertTrue("Movie should contain 2 elements after start.", iter.hasNext());
      assertEquals("Second element after start should be s5.", s5, iter.next());

      iter = r.getMovie(4);
      assertTrue("Movie should contain 2 elements before start.", iter.hasPrevious());
      assertEquals("First element before start should be s2.", s2, iter.previous());
      assertTrue("Movie should contain 2 elements before start.", iter.hasPrevious());
      assertEquals("Second element before start should be s1.", s1, iter.previous());

      iter = r.getMovie(5);
      assertTrue("Movie should contain 1 elements after start.", iter.hasNext());
      assertEquals("First element after start should be s5.", s5, iter.next());

      iter = r.getMovie(5);
      assertTrue("Movie should contain 3 elements before start.", iter.hasPrevious());
      assertEquals("First element before start should be s4.", s4, iter.previous());
      assertTrue("Movie should contain 3 elements before start.", iter.hasPrevious());
      assertEquals("Second element before start should be s2.", s2, iter.previous());
      assertTrue("Movie should contain 3 elements before start.", iter.hasPrevious());
      assertEquals("Third element before start should be s1.", s1, iter.previous());

      iter = r.getMovie(6);
      assertFalse("Movie should contain no elements after start.", iter.hasNext());

      iter = r.getMovie(6);
      assertTrue("Movie should contain 4 elements before start.", iter.hasPrevious());
      assertEquals("First element before start should be s5.", s5, iter.previous());
      assertTrue("Movie should contain 4 elements before start.", iter.hasPrevious());
      assertEquals("Second element before start should be s4.", s4, iter.previous());
      assertTrue("Movie should contain 4 elements before start.", iter.hasPrevious());
      assertEquals("Third element before start should be s2.", s2, iter.previous());
      assertTrue("Movie should contain 4 elements before start.", iter.hasPrevious());
      assertEquals("Fourth element before start should be s1.", s1, iter.previous());
   }

   /**
    * Test method for {@link AbstractRobot#getMovie(long, int)}.
    */
   @Test
   public void testGetMovieLongInt() {
      RobotSnapshot s1 = new RobotSnapshot("", 0, 0, 0, 0, 0, 1, 0);
      RobotSnapshot s3 = new RobotSnapshot("", 0, 0, 0, 0, 0, 3, 0);

      RobotSnapshot s2 = new RobotSnapshot("", 0, 0, 0, 0, 0, 2, 1);
      RobotSnapshot s4 = new RobotSnapshot("", 0, 0, 0, 0, 0, 4, 1);

      AbstractRobot r = new AbstractRobot() {
      };

      r.add(s1);
      r.add(s3);
      r.add(s2);
      r.add(s4);

      ListIterator<IRobotSnapshot> iter;

      // Test round 0
      iter = r.getMovie(0, 0);
      assertTrue("Movie for round 0 should contain 2 elements after start.", iter.hasNext());
      assertEquals("First element for round 0 after start should be s1.", s1, iter.next());
      assertTrue("Movie for round 0 should contain 2 elements after start.", iter.hasNext());
      assertEquals("Second element for round 0 after start should be s3.", s3, iter.next());

      iter = r.getMovie(0, 0);
      assertFalse("Movie for round 0 should contain no elements before start.", iter.hasPrevious());

      iter = r.getMovie(1, 0);
      assertTrue("Movie for round 0 should contain 2 elements after start.", iter.hasNext());
      assertEquals("First element for round 0 after start should be s1.", s1, iter.next());
      assertTrue("Movie for round 0 should contain 2 elements after start.", iter.hasNext());
      assertEquals("Second element for round 0 after start should be s3.", s3, iter.next());

      iter = r.getMovie(1, 0);
      assertFalse("Movie for round 0 should contain no elements before start.", iter.hasPrevious());

      iter = r.getMovie(2, 0);
      assertTrue("Movie for round 0 should contain 1 elements after start.", iter.hasNext());
      assertEquals("First element for round 0 after start should be s3.", s3, iter.next());

      iter = r.getMovie(2, 0);
      assertTrue("Movie for round 0 should contain 1 elements before start.", iter.hasPrevious());
      assertEquals("First element for round 0 before start should be s1.", s1, iter.previous());

      iter = r.getMovie(3, 0);
      assertTrue("Movie for round 0 should contain 1 elements after start.", iter.hasNext());
      assertEquals("First element for round 0 after start should be s3.", s3, iter.next());

      iter = r.getMovie(3, 0);
      assertTrue("Movie for round 0 should contain 1 elements before start.", iter.hasPrevious());
      assertEquals("First element for round 0 before start should be s1.", s1, iter.previous());

      iter = r.getMovie(4, 0);
      assertFalse("Movie for round 0 should contain no elements after start.", iter.hasNext());

      iter = r.getMovie(4, 0);
      assertTrue("Movie for round 0 should contain 2 elements before start.", iter.hasPrevious());
      assertEquals("First element for round 0 before start should be s3.", s3, iter.previous());
      assertTrue("Movie for round 0 should contain 2 elements before start.", iter.hasPrevious());
      assertEquals("Second element for round 0 before start should be s1.", s1, iter.previous());

      iter = r.getMovie(5, 0);
      assertFalse("Movie for round 0 should contain no elements after start.", iter.hasNext());

      iter = r.getMovie(5, 0);
      assertTrue("Movie for round 0 should contain 2 elements before start.", iter.hasPrevious());
      assertEquals("First element for round 0 before start should be s3.", s3, iter.previous());
      assertTrue("Movie for round 0 should contain 2 elements before start.", iter.hasPrevious());
      assertEquals("Second element for round 0 before start should be s1.", s1, iter.previous());

      // Test round 1
      iter = r.getMovie(0, 1);
      assertTrue("Movie for round 1 should contain 2 elements after start.", iter.hasNext());
      assertEquals("First element for round 1 after start should be s2.", s2, iter.next());
      assertTrue("Movie for round 1 should contain 2 elements after start.", iter.hasNext());
      assertEquals("Second element for round 1 after start should be s4.", s4, iter.next());

      iter = r.getMovie(0, 1);
      assertFalse("Movie for round 1 should contain no elements before start.", iter.hasPrevious());

      iter = r.getMovie(1, 1);
      assertTrue("Movie for round 1 should contain 2 elements after start.", iter.hasNext());
      assertEquals("First element for round 1 after start should be s2.", s2, iter.next());
      assertTrue("Movie for round 1 should contain 2 elements after start.", iter.hasNext());
      assertEquals("Second element for round 1 after start should be s4.", s4, iter.next());

      iter = r.getMovie(1, 1);
      assertFalse("Movie for round 1 should contain no elements before start.", iter.hasPrevious());

      iter = r.getMovie(2, 1);
      assertTrue("Movie for round 1 should contain 2 elements after start.", iter.hasNext());
      assertEquals("First element for round 1 after start should be s2.", s2, iter.next());
      assertTrue("Movie for round 1 should contain 2 elements after start.", iter.hasNext());
      assertEquals("Second element for round 1 after start should be s4.", s4, iter.next());

      iter = r.getMovie(2, 1);
      assertFalse("Movie for round 1 should contain no elements before start.", iter.hasPrevious());

      iter = r.getMovie(3, 1);
      assertTrue("Movie for round 1 should contain 1 elements after start.", iter.hasNext());
      assertEquals("First element for round 1 after start should be s4.", s4, iter.next());

      iter = r.getMovie(3, 1);
      assertTrue("Movie for round 1 should contain 1 elements before start.", iter.hasPrevious());
      assertEquals("First element for round 1 before start should be s2.", s2, iter.previous());

      iter = r.getMovie(4, 1);
      assertTrue("Movie for round 1 should contain 1 elements after start.", iter.hasNext());
      assertEquals("First element for round 1 after start should be s4.", s4, iter.next());

      iter = r.getMovie(4, 1);
      assertTrue("Movie for round 1 should contain 1 elements before start.", iter.hasPrevious());
      assertEquals("First element for round 1 before start should be s2.", s2, iter.previous());

      iter = r.getMovie(5, 1);
      assertFalse("Movie for round 1 should contain no elements after start.", iter.hasNext());

      iter = r.getMovie(5, 1);
      assertTrue("Movie for round 1 should contain 2 elements before start.", iter.hasPrevious());
      assertEquals("First element for round 1 before start should be s4.", s4, iter.previous());
      assertTrue("Movie for round 1 should contain 2 elements before start.", iter.hasPrevious());
      assertEquals("Second element for round 1 before start should be s2.", s2, iter.previous());

      // Test round 2
      iter = r.getMovie(0, 2);
      assertNotNull("Movie for round 2 should not be null.", iter);
      assertFalse("Movie for round 2 should not have next.", iter.hasNext());
      assertFalse("Movie for round 2 should not have previous.", iter.hasPrevious());

      iter = r.getMovie(3, 2);
      assertNotNull("Movie for round 2 should not be null.", iter);
      assertFalse("Movie for round 2 should not have next.", iter.hasNext());
      assertFalse("Movie for round 2 should not have previous.", iter.hasPrevious());
   }

   /**
    * Test method for {@link AbstractRobot#getRounds()}.
    */
   @Test
   public void testGetRounds() {
      RobotSnapshot s1 = new RobotSnapshot("", 0, 0, 0, 0, 0, 1, 0);
      RobotSnapshot s3 = new RobotSnapshot("", 0, 0, 0, 0, 0, 3, 0);

      RobotSnapshot s2 = new RobotSnapshot("", 0, 0, 0, 0, 0, 2, 1);
      RobotSnapshot s4 = new RobotSnapshot("", 0, 0, 0, 0, 0, 4, 1);

      RobotSnapshot s5 = new RobotSnapshot("", 0, 0, 0, 0, 0, 5, 3);

      AbstractRobot r = new AbstractRobot() {
      };

      r.add(s1);

      Set<Integer> set = r.getRounds();
      assertEquals("Set size for robot should be 1.", 1, set.size());
      assertTrue("Set should contain round 0.", set.contains(0));
      assertFalse("Set should not contain round 1.", set.contains(1));
      assertFalse("Set should not contain round 3.", set.contains(3));
      assertFalse("Set should not contain round -1.", set.contains(-1));

      r.add(s2);

      set = r.getRounds();
      assertEquals("Set size for robot should be 2.", 2, set.size());
      assertTrue("Set should contain round 0.", set.contains(0));
      assertTrue("Set should contain round 1.", set.contains(1));
      assertFalse("Set should not contain round 3.", set.contains(3));
      assertFalse("Set should not contain round -1.", set.contains(-1));

      r.add(s3);

      set = r.getRounds();
      assertEquals("Set size for robot should be 2.", 2, set.size());
      assertTrue("Set should contain round 0.", set.contains(0));
      assertTrue("Set should contain round 1.", set.contains(1));
      assertFalse("Set should not contain round 3.", set.contains(3));
      assertFalse("Set should not contain round -1.", set.contains(-1));

      r.add(s4);

      set = r.getRounds();
      assertEquals("Set size for robot should be 2.", 2, set.size());
      assertTrue("Set should contain round 0.", set.contains(0));
      assertTrue("Set should contain round 1.", set.contains(1));
      assertFalse("Set should not contain round 3.", set.contains(3));
      assertFalse("Set should not contain round -1.", set.contains(-1));

      r.add(s5);

      set = r.getRounds();
      assertEquals("Set size for robot should be 3.", 3, set.size());
      assertTrue("Set should contain round 0.", set.contains(0));
      assertTrue("Set should contain round 1.", set.contains(1));
      assertTrue("Set should contain round 3.", set.contains(3));
      assertFalse("Set should not contain round -1.", set.contains(-1));
   }

   /**
    * Test method for {@link AbstractRobot#getIndex(java.util.List, long)}.
    */
   @Test
   public void testGetIndex() {
      LinkedList<IRobotSnapshot> list = new LinkedList<IRobotSnapshot>();
      list.add(new RobotSnapshot("", 0, 0, 0, 0, 0, 1, 0));
      list.add(new RobotSnapshot("", 0, 0, 0, 0, 0, 2, 0));
      list.add(new RobotSnapshot("", 0, 0, 0, 0, 0, 4, 0));
      list.add(new RobotSnapshot("", 0, 0, 0, 0, 0, 5, 0));
      list.add(new RobotSnapshot("", 0, 0, 0, 0, 0, 8, 0));

      int[] indexes = new int[11];
      for (int i = 0; i < indexes.length; i++) {
         indexes[i] = AbstractRobot.getIndex(list, i);
      }

      assertEquals("Index returned for time=0 should be -1.", -1, indexes[0]);
      assertEquals("Index returned for time=1 should be 0.", 0, indexes[1]);
      assertEquals("Index returned for time=2 should be 1.", 1, indexes[2]);
      assertEquals("Index returned for time=3 should be 1.", 1, indexes[3]);
      assertEquals("Index returned for time=4 should be 2.", 2, indexes[4]);
      assertEquals("Index returned for time=5 should be 3.", 3, indexes[5]);
      assertEquals("Index returned for time=6 should be 3.", 3, indexes[6]);
      assertEquals("Index returned for time=7 should be 3.", 3, indexes[7]);
      assertEquals("Index returned for time=8 should be 4.", 4, indexes[8]);
      assertEquals("Index returned for time=9 should be 4.", 4, indexes[9]);
      assertEquals("Index returned for time=10 should be 4.", 4, indexes[10]);

      try {
         AbstractRobot.getIndex(null, 0);
         fail("getIndex should throw an error.");
      } catch (Exception e) {
         assertTrue("getIndex should throw an NullPointerException.", e instanceof NullPointerException);
      }

      try {
         AbstractRobot.getIndex(null, -1);
         fail("getIndex should throw an error.");
      } catch (Exception e) {
         assertTrue("getIndex should throw an NullPointerException.", e instanceof NullPointerException);
      }

      try {
         AbstractRobot.getIndex(list, -1);
         fail("getIndex should throw an error.");
      } catch (Exception e) {
         assertTrue("getIndex should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }
   }

   /**
    * Test method for {@link AbstractRobot#getMovie(java.util.List, long)}.
    */
   @Test
   public void testGetMovieListOfTLong() {
      RobotSnapshot s1 = new RobotSnapshot("", 0, 0, 0, 0, 0, 1, 0);
      RobotSnapshot s2 = new RobotSnapshot("", 0, 0, 0, 0, 0, 2, 0);
      RobotSnapshot s4 = new RobotSnapshot("", 0, 0, 0, 0, 0, 4, 0);
      RobotSnapshot s5 = new RobotSnapshot("", 0, 0, 0, 0, 0, 5, 0);
      RobotSnapshot s8 = new RobotSnapshot("", 0, 0, 0, 0, 0, 8, 0);

      LinkedList<IRobotSnapshot> list = new LinkedList<IRobotSnapshot>();
      list.add(s1);
      list.add(s2);
      list.add(s4);
      list.add(s5);
      list.add(s8);

      @SuppressWarnings("rawtypes")
      ListIterator[] iters = new ListIterator[11];
      for (int i = 0; i < iters.length; i++) {
         iters[i] = AbstractRobot.getMovie(list, i);
      }

      assertEquals("ListIterator returned for time=0 should start at s1.", s1, iters[0].next());
      assertEquals("ListIterator returned for time=1 should start at s1.", s1, iters[1].next());
      assertEquals("ListIterator returned for time=2 should start at s2.", s2, iters[2].next());
      assertEquals("ListIterator returned for time=3 should start at s4.", s4, iters[3].next());
      assertEquals("ListIterator returned for time=4 should start at s4.", s4, iters[4].next());
      assertEquals("ListIterator returned for time=5 should start at s5.", s5, iters[5].next());
      assertEquals("ListIterator returned for time=6 should start at s8.", s8, iters[6].next());
      assertEquals("ListIterator returned for time=7 should start at s8.", s8, iters[7].next());
      assertEquals("ListIterator returned for time=8 should start at s8.", s8, iters[8].next());
      assertFalse("ListIterator returned for time=9 should start at the end.", iters[9].hasNext());
      assertFalse("ListIterator returned for time=10 should start at the end.", iters[10].hasNext());

      try {
         AbstractRobot.getMovie(null, -1);
         fail("getMovie should throw an error.");
      } catch (Exception e) {
         assertTrue("getMovie should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }
   }

   /**
    * Test method for {@link AbstractRobot#getSnapshot(java.util.List, long)}.
    */
   @Test
   public void testGetSnapshotListOfTLong() {
      RobotSnapshot s1 = new RobotSnapshot("", 0, 0, 0, 0, 0, 1, 0);
      RobotSnapshot s2 = new RobotSnapshot("", 0, 0, 0, 0, 0, 2, 0);
      RobotSnapshot s4 = new RobotSnapshot("", 0, 0, 0, 0, 0, 4, 0);
      RobotSnapshot s5 = new RobotSnapshot("", 0, 0, 0, 0, 0, 5, 0);
      RobotSnapshot s8 = new RobotSnapshot("", 0, 0, 0, 0, 0, 8, 0);

      LinkedList<IRobotSnapshot> list = new LinkedList<IRobotSnapshot>();
      list.add(s1);
      list.add(s2);
      list.add(s4);
      list.add(s5);
      list.add(s8);

      IRobotSnapshot[] snaps = new RobotSnapshot[11];
      for (int i = 0; i < snaps.length; i++) {
         snaps[i] = AbstractRobot.getSnapshot(list, i);
      }

      assertEquals("Snapshot returned for time=0 should be s1.", s1, snaps[0]);
      assertEquals("Snapshot returned for time=1 should be s1.", s1, snaps[1]);
      assertEquals("Snapshot returned for time=2 should be s2.", s2, snaps[2]);
      assertEquals("Snapshot returned for time=3 should be s2.", s2, snaps[3]);
      assertEquals("Snapshot returned for time=4 should be s4.", s4, snaps[4]);
      assertEquals("Snapshot returned for time=5 should be s5.", s5, snaps[5]);
      assertEquals("Snapshot returned for time=6 should be s5.", s5, snaps[6]);
      assertEquals("Snapshot returned for time=7 should be s5.", s5, snaps[7]);
      assertEquals("Snapshot returned for time=8 should be s8.", s8, snaps[8]);
      assertEquals("Snapshot returned for time=9 should be s8.", s8, snaps[9]);
      assertEquals("Snapshot returned for time=10 should be s8.", s8, snaps[10]);

      try {
         AbstractRobot.getSnapshot(null, -1);
         fail("getSnapshot should throw an error.");
      } catch (Exception e) {
         assertTrue("getSnapshot should throw an IllegalArgumentException.", e instanceof IllegalArgumentException);
      }
   }
}
