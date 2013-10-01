package bnorm.virtual;

import org.junit.Assert;
import org.junit.Test;

import bnorm.utils.Trig;

/**
 * A group of unit tests for {@link Vector}s.
 *
 * @author Brian Norman
 */
public class VectorTest {

   /**
    * A test for the {@link Vector#getDeltaX()} method.
    */
   @Test
   public void testGetDeltaX() {
      final double EPSILON = 1.0E-14;
      final double SQRT2 = Math.sqrt(2.0);
      final double EIGHTH_CIRCLE = Trig.CIRCLE / 8.0;

      Assert.assertEquals(6.0, new Vector(6.0, 0.0).getDeltaX(), EPSILON);
      Assert.assertEquals(-3.33, new Vector(-3.33, 0.0).getDeltaX(), EPSILON);
      Assert.assertEquals(3.14159, new Vector(3.14159, 0.0).getDeltaX(), EPSILON);
      Assert.assertEquals(-365.25, new Vector(-365.25, 0.0).getDeltaX(), EPSILON);
      Assert.assertEquals(42.0, new Vector(42.0, 0.0).getDeltaX(), EPSILON);
      Assert.assertEquals(-0.0, new Vector(-0.0, 0.0).getDeltaX(), EPSILON);

      Assert.assertEquals(0.0, new Vector(0.0, 0.0, 0.0 * EIGHTH_CIRCLE, 4.0).getDeltaX(), EPSILON);
      Assert.assertEquals(0.0, new Vector(0.0, 0.0, 0.0 * EIGHTH_CIRCLE, 9.0).getDeltaX(), EPSILON);
      Assert.assertEquals(4.0 / SQRT2, new Vector(1.0, 1.0, 1.0 * EIGHTH_CIRCLE, 4.0).getDeltaX(), EPSILON);
      Assert.assertEquals(9.0 / SQRT2, new Vector(1.0, 1.0, 1.0 * EIGHTH_CIRCLE, 9.0).getDeltaX(), EPSILON);
      Assert.assertEquals(4.0, new Vector(2.0, 2.0, 2.0 * EIGHTH_CIRCLE, 4.0).getDeltaX(), EPSILON);
      Assert.assertEquals(9.0, new Vector(2.0, 2.0, 2.0 * EIGHTH_CIRCLE, 9.0).getDeltaX(), EPSILON);
      Assert.assertEquals(4.0 / SQRT2, new Vector(3.0, 3.0, 3.0 * EIGHTH_CIRCLE, 4.0).getDeltaX(), EPSILON);
      Assert.assertEquals(9.0 / SQRT2, new Vector(3.0, 3.0, 3.0 * EIGHTH_CIRCLE, 9.0).getDeltaX(), EPSILON);
      Assert.assertEquals(0.0, new Vector(-0.0, -0.0, 4.0 * EIGHTH_CIRCLE, 4.0).getDeltaX(), EPSILON);
      Assert.assertEquals(0.0, new Vector(-0.0, -0.0, 4.0 * EIGHTH_CIRCLE, 9.0).getDeltaX(), EPSILON);
      Assert.assertEquals(-4.0 / SQRT2, new Vector(-1.0, -1.0, 5.0 * EIGHTH_CIRCLE, 4.0).getDeltaX(), EPSILON);
      Assert.assertEquals(-9.0 / SQRT2, new Vector(-1.0, -1.0, 5.0 * EIGHTH_CIRCLE, 9.0).getDeltaX(), EPSILON);
      Assert.assertEquals(-4.0, new Vector(-2.0, -2.0, 6.0 * EIGHTH_CIRCLE, 4.0).getDeltaX(), EPSILON);
      Assert.assertEquals(-9.0, new Vector(-2.0, -2.0, 6.0 * EIGHTH_CIRCLE, 9.0).getDeltaX(), EPSILON);
      Assert.assertEquals(-4.0 / SQRT2, new Vector(-3.0, -3.0, 7.0 * EIGHTH_CIRCLE, 4.0).getDeltaX(), EPSILON);
      Assert.assertEquals(-9.0 / SQRT2, new Vector(-3.0, -3.0, 7.0 * EIGHTH_CIRCLE, 9.0).getDeltaX(), EPSILON);
      Assert.assertEquals(0.0, new Vector(0.0, -0.0, 8.0 * EIGHTH_CIRCLE, 4.0).getDeltaX(), EPSILON);
      Assert.assertEquals(0.0, new Vector(-1.0, 1.0, 8.0 * EIGHTH_CIRCLE, 9.0).getDeltaX(), EPSILON);
   }

   /**
    * A test for the {@link Vector#getDeltaY()} method.
    */
   @Test
   public void testGetDeltaY() {
      final double EPSILON = 1.0E-14;
      final double SQRT2 = Math.sqrt(2.0);
      final double EIGHTH_CIRCLE = Trig.CIRCLE / 8.0;

      Assert.assertEquals(6.0, new Vector(0.0, 6.0).getDeltaY(), 0.0);
      Assert.assertEquals(-3.33, new Vector(0.0, -3.33).getDeltaY(), 0.0);
      Assert.assertEquals(3.14159, new Vector(0.0, 3.14159).getDeltaY(), 0.0);
      Assert.assertEquals(-365.25, new Vector(0.0, -365.25).getDeltaY(), 0.0);
      Assert.assertEquals(42.0, new Vector(0.0, 42.0).getDeltaY(), 0.0);
      Assert.assertEquals(-0.0, new Vector(0.0, -0.0).getDeltaY(), 0.0);

      Assert.assertEquals(4.0, new Vector(0.0, 0.0, 0.0 * EIGHTH_CIRCLE, 4.0).getDeltaY(), EPSILON);
      Assert.assertEquals(9.0, new Vector(0.0, 0.0, 0.0 * EIGHTH_CIRCLE, 9.0).getDeltaY(), EPSILON);
      Assert.assertEquals(4.0 / SQRT2, new Vector(1.0, 1.0, 1.0 * EIGHTH_CIRCLE, 4.0).getDeltaY(), EPSILON);
      Assert.assertEquals(9.0 / SQRT2, new Vector(1.0, 1.0, 1.0 * EIGHTH_CIRCLE, 9.0).getDeltaY(), EPSILON);
      Assert.assertEquals(0.0, new Vector(2.0, 2.0, 2.0 * EIGHTH_CIRCLE, 4.0).getDeltaY(), EPSILON);
      Assert.assertEquals(0.0, new Vector(2.0, 2.0, 2.0 * EIGHTH_CIRCLE, 9.0).getDeltaY(), EPSILON);
      Assert.assertEquals(-4.0 / SQRT2, new Vector(3.0, 3.0, 3.0 * EIGHTH_CIRCLE, 4.0).getDeltaY(), EPSILON);
      Assert.assertEquals(-9.0 / SQRT2, new Vector(3.0, 3.0, 3.0 * EIGHTH_CIRCLE, 9.0).getDeltaY(), EPSILON);
      Assert.assertEquals(-4.0, new Vector(-0.0, -0.0, 4.0 * EIGHTH_CIRCLE, 4.0).getDeltaY(), EPSILON);
      Assert.assertEquals(-9.0, new Vector(-0.0, -0.0, 4.0 * EIGHTH_CIRCLE, 9.0).getDeltaY(), EPSILON);
      Assert.assertEquals(-4.0 / SQRT2, new Vector(-1.0, -1.0, 5.0 * EIGHTH_CIRCLE, 4.0).getDeltaY(), EPSILON);
      Assert.assertEquals(-9.0 / SQRT2, new Vector(-1.0, -1.0, 5.0 * EIGHTH_CIRCLE, 9.0).getDeltaY(), EPSILON);
      Assert.assertEquals(0.0, new Vector(-2.0, -2.0, 6.0 * EIGHTH_CIRCLE, 4.0).getDeltaY(), EPSILON);
      Assert.assertEquals(0.0, new Vector(-2.0, -2.0, 6.0 * EIGHTH_CIRCLE, 9.0).getDeltaY(), EPSILON);
      Assert.assertEquals(4.0 / SQRT2, new Vector(-3.0, -3.0, 7.0 * EIGHTH_CIRCLE, 4.0).getDeltaY(), EPSILON);
      Assert.assertEquals(9.0 / SQRT2, new Vector(-3.0, -3.0, 7.0 * EIGHTH_CIRCLE, 9.0).getDeltaY(), EPSILON);
      Assert.assertEquals(4.0, new Vector(0.0, -0.0, 8.0 * EIGHTH_CIRCLE, 4.0).getDeltaY(), EPSILON);
      Assert.assertEquals(9.0, new Vector(-1.0, 1.0, 8.0 * EIGHTH_CIRCLE, 9.0).getDeltaY(), EPSILON);
   }

   /**
    * A test for the {@link Vector#getHeading()} method.
    */
   @Test
   public void testGetHeading() {
      final double EPSILON = 1.0E-14;
      final double EIGHTH_CIRCLE = Trig.CIRCLE / 8.0;

      Assert.assertEquals(0.0, new Vector(0.0, 0.0, 0.0, 0.0).getHeading(), 0.0);
      Assert.assertEquals(-3.33, new Vector(0.0, 0.0, -3.33, 1.0).getHeading(), 0.0);
      Assert.assertEquals(3.14159, new Vector(0.0, 0.0, 3.14159, -1.0).getHeading(), 0.0);
      Assert.assertEquals(-365.25, new Vector(0.0, 0.0, -365.25, -0.0).getHeading(), 0.0);
      Assert.assertEquals(42.0, new Vector(0.0, 0.0, 42.0, 2.0).getHeading(), 0.0);
      Assert.assertEquals(-0.0, new Vector(0.0, 0.0, -0.0, -2.0).getHeading(), 0.0);

      Assert.assertEquals(0.0 * EIGHTH_CIRCLE, new Vector(0.0, 1.0).getHeading(), EPSILON);
      Assert.assertEquals(1.0 * EIGHTH_CIRCLE, new Vector(1.0, 1.0).getHeading(), EPSILON);
      Assert.assertEquals(2.0 * EIGHTH_CIRCLE, new Vector(1.0, 0.0).getHeading(), EPSILON);
      Assert.assertEquals(3.0 * EIGHTH_CIRCLE, new Vector(1.0, -1.0).getHeading(), EPSILON);
      Assert.assertEquals(4.0 * EIGHTH_CIRCLE, new Vector(0.0, -1.0).getHeading(), EPSILON);
      Assert.assertEquals(-3.0 * EIGHTH_CIRCLE, new Vector(-1.0, -1.0).getHeading(), EPSILON);
      Assert.assertEquals(-2.0 * EIGHTH_CIRCLE, new Vector(-1.0, 0.0).getHeading(), EPSILON);
      Assert.assertEquals(-1.0 * EIGHTH_CIRCLE, new Vector(-1.0, 1.0).getHeading(), EPSILON);
      Assert.assertEquals(0.0 * EIGHTH_CIRCLE, new Vector(0.0, 1.0).getHeading(), EPSILON);
   }

   /**
    * A test for the {@link Vector#getVelocity()} method.
    */
   @Test
   public void testGetVelocity() {
      final double EPSILON = 1.0E-14;
      final double SQRT2 = Math.sqrt(2.0);

      Assert.assertEquals(0.0, new Vector(0.0, 0.0, 0.0, 0.0).getVelocity(), 0.0);
      Assert.assertEquals(-3.33, new Vector(0.0, 0.0, 1.0, -3.33).getVelocity(), 0.0);
      Assert.assertEquals(3.14159, new Vector(0.0, 0.0, -1.0, 3.14159).getVelocity(), 0.0);
      Assert.assertEquals(-365.25, new Vector(0.0, 0.0, -0.0, -365.25).getVelocity(), 0.0);
      Assert.assertEquals(42.0, new Vector(0.0, 0.0, 2.0, 42.0).getVelocity(), 0.0);
      Assert.assertEquals(-0.0, new Vector(0.0, 0.0, -2.0, -0.0).getVelocity(), 0.0);

      Assert.assertEquals(1.0, new Vector(0.0, 1.0).getVelocity(), EPSILON);
      Assert.assertEquals(1.0 * SQRT2, new Vector(1.0, 1.0).getVelocity(), EPSILON);
      Assert.assertEquals(2.0, new Vector(2.0, 0.0).getVelocity(), EPSILON);
      Assert.assertEquals(2.0 * SQRT2, new Vector(2.0, -2.0).getVelocity(), EPSILON);
      Assert.assertEquals(42.0, new Vector(0.0, -42.0).getVelocity(), EPSILON);
      Assert.assertEquals(42.0 * SQRT2, new Vector(-42.0, -42.0).getVelocity(), EPSILON);
      Assert.assertEquals(3.33, new Vector(-3.33, 0.0).getVelocity(), EPSILON);
      Assert.assertEquals(3.33 * SQRT2, new Vector(-3.33, 3.33).getVelocity(), EPSILON);
      Assert.assertEquals(365.25, new Vector(0.0, 365.25).getVelocity(), EPSILON);
      Assert.assertEquals(365.25 * SQRT2, new Vector(365.25, 365.25).getVelocity(), EPSILON);
   }

   /**
    * A test for the {@link Vector#toString()} method.
    */
   @Test
   public void testToString() {
      Assert.assertEquals("Vector[x=0.0, y=0.0, dx=0.0, dy=0.0]", new Vector(0.0, 0.0).toString());
      Assert.assertEquals("Vector[x=0.0, y=0.0, dx=1.0, dy=-1.0]", new Vector(1.0, -1.0).toString());
      Assert.assertEquals("Vector[x=0.0, y=0.0, dx=-42.0, dy=3.33]", new Vector(-42.0, 3.33).toString());
      Assert.assertEquals("Vector[x=0.0, y=0.0, dx=3.14159, dy=-365.25]", new Vector(3.14159, -365.25).toString());

      Assert.assertEquals("Vector[x=0.0, y=0.0, h=0.0, v=0.0]", new Vector(0.0, 0.0, 0.0, 0.0).toString());
      Assert.assertEquals("Vector[x=1.0, y=-1.0, h=1.0, v=-1.0]", new Vector(1.0, -1.0, 1.0, -1.0).toString());
      Assert.assertEquals("Vector[x=3.14159, y=-365.25, h=-42.0, v=3.33]",
                          new Vector(3.14159, -365.25, -42.0, 3.33).toString());

      Vector vector = new Vector(1.0, 42.0);
      vector.getHeading();
      vector.getVelocity();
      Assert.assertEquals("Vector[x=0.0, y=0.0, h=0.023805026185069942, v=42.01190307520001]", vector.toString());
   }

   /**
    * A test for the {@link Vector#equals(Object)} method.
    */
   @Test
   public void testEquals() {
      final double SQRT2 = Math.sqrt(2.0);
      final double EIGHTH_CIRCLE = Trig.CIRCLE / 8.0;

      Assert.assertEquals(0.0, new Vector(0.0, 0.0, 0.0, 0.0).getHeading(), 0.0);
      Assert.assertEquals(-3.33, new Vector(0.0, 0.0, -3.33, 1.0).getHeading(), 0.0);
      Assert.assertEquals(3.14159, new Vector(0.0, 0.0, 3.14159, -1.0).getHeading(), 0.0);
      Assert.assertEquals(-365.25, new Vector(0.0, 0.0, -365.25, -0.0).getHeading(), 0.0);
      Assert.assertEquals(42.0, new Vector(0.0, 0.0, 42.0, 2.0).getHeading(), 0.0);
      Assert.assertEquals(-0.0, new Vector(0.0, 0.0, -0.0, -2.0).getHeading(), 0.0);

      Assert.assertEquals(new Vector(0.0, 1.0), new Vector(0.0, 0.0, 0.0 * EIGHTH_CIRCLE, 1.0));
      Assert.assertEquals(new Vector(1.0, 1.0), new Vector(0.0, 0.0, 1.0 * EIGHTH_CIRCLE, 1.0 * SQRT2));
      Assert.assertEquals(new Vector(2.0, 0.0), new Vector(0.0, 0.0, 2.0 * EIGHTH_CIRCLE, 2.0));
      Assert.assertEquals(new Vector(2.0, -2.0), new Vector(0.0, 0.0, 3.0 * EIGHTH_CIRCLE, 2.0 * SQRT2));

      Assert.assertEquals(new Vector(0.0, 0.0, 4.0 * EIGHTH_CIRCLE, 42.0), new Vector(0.0, -42.0));
      Assert.assertEquals(new Vector(0.0, 0.0, 5.0 * EIGHTH_CIRCLE, 42.0 * SQRT2), new Vector(-42.0, -42.0));
      Assert.assertEquals(new Vector(0.0, 0.0, 6.0 * EIGHTH_CIRCLE, 3.33), new Vector(-3.33, 0.0));
      Assert.assertEquals(new Vector(0.0, 0.0, 7.0 * EIGHTH_CIRCLE, 3.33 * SQRT2), new Vector(-3.33, 3.33));

      Assert.assertFalse(new Vector(0.0, -42.0).equals(null));
      Assert.assertFalse(new Vector(0.0, 0.0, 8.0 * EIGHTH_CIRCLE, 3.14159).equals("string"));
      Assert.assertFalse(new Vector(365.0, 0.0).equals(2.0));
   }
}
