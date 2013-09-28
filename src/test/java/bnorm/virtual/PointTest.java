package bnorm.virtual;

import org.junit.Assert;
import org.junit.Test;

/**
 * A group of unit tests for {@link Point}s.
 *
 * @author Brian Norman
 */
public class PointTest {

   /**
    * A test for the {@link Point#getX()} method.
    */
   @Test
   public void testGetX() {
      Assert.assertEquals(6.0, new Point(6.0, 0.0).getX(), 0.0);
      Assert.assertEquals(-3.33, new Point(-3.33, 0.0).getX(), 0.0);
      Assert.assertEquals(3.14159, new Point(3.14159, 0.0).getX(), 0.0);
      Assert.assertEquals(-365.25, new Point(-365.25, 0.0).getX(), 0.0);
      Assert.assertEquals(42.0, new Point(42.0, 0.0).getX(), 0.0);
      Assert.assertEquals(-0.0, new Point(-0.0, 0.0).getX(), 0.0);
   }

   /**
    * A test for the {@link Point#getY()} method.
    */
   @Test
   public void testGetY() {
      Assert.assertEquals(6.0, new Point(0.0, 6.0).getY(), 0.0);
      Assert.assertEquals(-3.33, new Point(0.0, -3.33).getY(), 0.0);
      Assert.assertEquals(3.14159, new Point(0.0, 3.14159).getY(), 0.0);
      Assert.assertEquals(-365.25, new Point(0.0, -365.25).getY(), 0.0);
      Assert.assertEquals(42.0, new Point(0.0, 42.0).getY(), 0.0);
      Assert.assertEquals(-0.0, new Point(0.0, -0.0).getY(), 0.0);
   }

   /**
    * A test for the {@link Point#toString()} method.
    */
   @Test
   public void testToString() {
      Assert.assertEquals("Point[0.0, 0.0]", new Point(0.0, 0.0).toString());
      Assert.assertEquals("Point[3.33, -0.0]", new Point(3.33, -0.0).toString());
      Assert.assertEquals("Point[-42.0, 365.0]", new Point(-42.0, 365.0).toString());
      Assert.assertEquals("Point[365.25, -3.14159]", new Point(365.25, -3.14159).toString());
   }

   /**
    * A test for the {@link Point#equals(Object)} method.
    */
   @Test
   public void testEquals() {
      Assert.assertEquals(new Point(6.0, 6.0), new Point(6.0, 6.0));
      Assert.assertEquals(new Point(-3.0, 3.0), new Point(-3.0, 3.0));
      Assert.assertEquals(new Point(3.0, -3.0), new Point(3.0, -3.0));
      Assert.assertEquals(new Point(365.0, 0.0), new Point(365.0, 0.0));
      Assert.assertEquals(new Point(0.0, 365.0), new Point(0.0, 365.0));

      Assert.assertFalse(new Point(0.0, 0.0).equals(new Point(6.0, 6.0)));
      Assert.assertFalse(new Point(-3.0, 3.0).equals(new Point(3.0, -3.0)));
      Assert.assertFalse(new Point(365.0, 0.0).equals(new Point(0.0, 365.0)));

      Assert.assertFalse(new Point(0.0, 0.0).equals(null));
      Assert.assertFalse(new Point(-3.0, 3.0).equals("string"));
      Assert.assertFalse(new Point(365.0, 0.0).equals(2.0));
   }
}
