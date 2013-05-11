package kid.utils;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * A {@code class} that represents a mathematical vector.
 * 
 * @author Brian Norman
 * @version 0.2.9.1
 */
public class Vector implements Cloneable, Serializable {

   /**
    * Determines if a deserialized file is compatible with {@code this class}. <br>
    * <br>
    * Maintainers must change this value if and only if the new version of {@code this class} is not
    * compatible with old versions.
    */
   private static final long serialVersionUID = -735805461823980984L;

   /**
    * Decimal formating for the components of {@code this} {@link Vector}
    */
   private static final DecimalFormat vectorFormat = new DecimalFormat("0.0##;-0.0##");


   /**
    * The magnitude of the {@code x} component of {@code this} {@link Vector}.
    */
   private double i;

   /**
    * The magnitude of the {@code y} component of {@code this} {@link Vector}.
    */
   private double j;

   /**
    * The magnitude of the {@code z} component of {@code this} {@link Vector}.
    */
   private double k;


   /**
    * Creates a new {@link Vector} with the specified components.
    * 
    * @param x
    *           the {@code x} component
    * @param y
    *           the {@code y} component
    * @param z
    *           the {@code z} component
    */
   public Vector(double x, double y, double z) {
      set(x, y, z);
   }

   /**
    * Creates a new {@link Vector} with the specified components.
    * 
    * @param x
    *           the {@code x} component
    * @param y
    *           the {@code y} component
    */
   public Vector(double x, double y) {
      this(x, y, 0.0);
   }

   /**
    * Creates a new {@link Vector} with the specified component.
    * 
    * @param x
    *           the {@code x} component
    */
   public Vector(double x) {
      this(x, 0.0, 0.0);
   }

   /**
    * Creates a new zero {@link Vector}. That is, a {@link Vector} with all components set to zero.
    */
   public Vector() {
      this(0.0, 0.0, 0.0);
   }


   /**
    * Factory constructor that returns the unit {@link Vector} in the i direction or along the
    * {@code x}-axis.
    */
   public static final Vector UNIT_I() {
      return new Vector(1.0);
   }

   /**
    * Factory constructor that returns the unit {@link Vector} in the j direction or along the
    * {@code y}-axis.
    */
   public static final Vector UNIT_J() {
      return new Vector(0.0, 1.0);
   }

   /**
    * Factory constructor that returns the unit {@link Vector} in the k direction or along the
    * {@code z}-axis.
    */
   public static final Vector UNIT_K() {
      return new Vector(0.0, 0.0, 1.0);
   }


   // BORED document: Vector#createFromAglMag(double, double, double)
   public Vector createFromAngMag(double magnitude, double theta, double phi) {
      return new Vector(magnitude * Math.sin(phi) * Math.cos(theta), magnitude * Math.sin(phi) * Math.sin(theta),
            magnitude * Math.cos(phi));
   }

   // BORED document: Vector#createFromAngMag(double, double)
   public Vector createFromAngMag(double magnitude, double theta) {
      return new Vector(magnitude * Math.sin(theta), magnitude * Math.cos(theta));
   }

   // BORED document: Vector#createFromPoints(Point, Point)
   public Vector createFromPoints(Point2D tail, Point2D tip) {
      return new Vector(tip.getX() - tail.getX(), tip.getY() - tail.getY());
   }


   /**
    * Sets the current {@link Vector} to be a copy of the passed in {@link Vector}.
    * 
    * @param v
    *           the {@link Vector} to be copied
    * @return {@code this} {@link Vector}
    */
   public Vector set(Vector v) {
      set(v.x(), v.y(), v.z());
      return this;
   }

   /**
    * Sets the components of {@code this} {@link Vector} to be the passed in components.
    * 
    * @param x
    *           the {@code x} component
    * @param y
    *           the {@code y} component
    * @param z
    *           the {@code z} component
    * @return {@code this} {@link Vector}
    */
   public Vector set(double x, double y, double z) {
      i = x;
      j = y;
      k = z;
      return this;
   }


   /**
    * Returns the magnitude of the {@code x} component of {@code this} {@link Vector} .
    * 
    * @return the {@code x} component
    * @see #i
    */
   public double x() {
      return i;
   }

   /**
    * Returns the magnitude of the {@code y} component of {@code this} {@link Vector} .
    * 
    * @return the {@code y} component
    * @see #j
    */
   public double y() {
      return j;
   }

   /**
    * Returns the magnitude of the {@code z} component of {@code this} {@link Vector} .
    * 
    * @return the {@code z} component
    * @see #k
    */
   public double z() {
      return k;
   }


   /**
    * Calculates the magnitude of {@code this} {@link Vector} .
    * 
    * @return magnitude of {@code this} {@link Vector}
    */
   public double magnitude() {
      return Vector.magnitude(this);
   }

   /**
    * Calculates the magnitude of the {@link Vector}.
    * 
    * @param v
    *           the {@link Vector} that will have its magnitude calculated
    * @return the magnitude of the {@link Vector}
    */
   public static final double magnitude(Vector v) {
      return Math.sqrt(Vector.magnitudeSq(v));
   }


   /**
    * Calculates the magnitude squared of {@code this} {@link Vector} .
    * 
    * @return the magnitude squared of {@code this} {@link Vector}
    */
   public double magnitudeSq() {
      return Vector.magnitudeSq(this);
   }

   /**
    * Calculates the magnitude squared of the {@link Vector}.
    * 
    * @param v
    *           the {@link Vector} that will have its squared magnitude calculated
    * @return the magnitude squared of the {@link Vector}
    */
   public static final double magnitudeSq(Vector v) {
      return (v.x() * v.x() + v.y() * v.y() + v.z() * v.z());
   }


   /**
    * Calculates the angle of {@code this} {@link Vector} as projected onto the{@code xy}-plane.
    * This is the angle from the positive {@code x} axis in the counter-clockwise direction.
    * 
    * @return the angle measured off the {@code x} axis through the {@code xy}-plane
    */
   public double angleTheta() {
      return Vector.angleTheta(this);
   }

   /**
    * Calculates the angle of the {@link Vector} as projected onto the xy-plane. This is the angle
    * from the positive {@code x} axis in the counter-clockwise direction.
    * 
    * @param v
    *           {@link Vector} to measure
    * @return the angle measured from the positive {@code x} axis through the {@code xy}-plane
    */
   public static final double angleTheta(Vector v) {
      return Math.atan2(v.y(), v.x());
   }


   /**
    * Calculates the angle of {@code this} {@link Vector} measured from the positive {@code z} axis.
    * 
    * @return the angle measured from the positive {@code z} axis
    */
   public double anglePhi() {
      return Vector.anglePhi(this);
   }

   /**
    * Calculates the angle of the {@link Vector} measured from the positive {@code z} axis.
    * 
    * @param v
    *           {@link Vector} to measure
    * @return the angle measured from the positive {@code z} axis
    */
   public static final double anglePhi(Vector v) {
      return Math.acos(v.z() / Vector.magnitude(v));
   }


   /**
    * Scales {@code this} {@link Vector} by a constant.
    * 
    * @param a
    *           constant by which {@code this} {@link Vector} will be scaled
    * @return {@code this} {@link Vector}
    */
   public Vector scale(double a) {
      set(Vector.scale(a, this));
      return this;
   }

   /**
    * Returns a new {@link Vector} that is a copy of the {@link Vector} scaled by a constant.
    * 
    * @param a
    *           constant by which the {@link Vector} will be scaled
    * @param v
    *           {@link Vector} to be copied and scaled
    * @return new {@link Vector} that has been copied and scaled
    */
   public static final Vector scale(double a, Vector v) {
      return new Vector(a * v.x(), a * v.y(), a * v.z());
   }


   /**
    * Reverses the direction of {@code this} {@link Vector}.
    * 
    * @return {@code this} {@link Vector}
    */
   public Vector negate() {
      set(Vector.negate(this));
      return this;
   }

   /**
    * Returns a new {@link Vector} that is a copy of the {@link Vector} but points in the opposite
    * direction.
    * 
    * @param v
    *           {@link Vector} to be copied and reversed
    * @return new {@link Vector} that is the reverse of the old {@link Vector}
    */
   public static final Vector negate(Vector v) {
      return Vector.scale(-1.0, v);
   }


   /**
    * Returns {@code this} {@link Vector} scaled to be a unit {@link Vector}.
    * 
    * @return {@code this} {@link Vector}
    */
   public Vector unit() {
      set(Vector.unit(this));
      return this;
   }

   /**
    * Returns a new {@link Vector} that is the unit {@link Vector} of the passed in {@link Vector}.
    * 
    * @param v
    *           reference {@link Vector} for the unit {@link Vector}
    * @return unit {@link Vector} that points in the same direction as the reference {@link Vector}
    */
   public static final Vector unit(Vector v) {
      return Vector.scale(1.0 / Vector.magnitude(v), v);
   }


   /**
    * Adds a {@link Vector} to {@code this} {@link Vector} .
    * 
    * @param v
    *           {@link Vector} to be added
    * @return {@code this} {@link Vector}
    */
   public Vector add(Vector v) {
      set(Vector.add(this, v));
      return this;
   }

   /**
    * Adds the two {@link Vector}s together and returns a new {@link Vector}.
    * 
    * @param v1
    *           base {@link Vector}
    * @param v2
    *           {@link Vector} to be added
    * @return new {@link Vector} that is a copy of the combined {@link Vector}s
    */
   public static final Vector add(Vector v1, Vector v2) {
      return new Vector(v1.x() + v2.x(), v1.y() + v2.y(), v1.z() + v2.z());
   }


   /**
    * Subtracts a {@link Vector} from {@code this} {@link Vector} .
    * 
    * @param v
    *           {@link Vector} to be subtracted
    * @return {@code this} {@link Vector}
    */
   public Vector subtract(Vector v) {
      set(Vector.subtract(this, v));
      return this;
   }

   /**
    * Subtracts a second {@link Vector} from the base {@link Vector} and returns that difference as
    * a new {@link Vector}.
    * 
    * @param v1
    *           base {@link Vector}
    * @param v2
    *           {@link Vector} to be subtracted
    * @return new {@link Vector} that is a copy of the difference of the two {@link Vector}s
    */
   public static final Vector subtract(Vector v1, Vector v2) {
      return new Vector(v1.x() - v2.x(), v1.y() - v2.y(), v1.z() - v2.z());
   }


   /**
    * Calculates the cross product of {@code this} {@link Vector} and the {@link Vector} and returns
    * it as a new {@link Vector}.
    * 
    * @param v
    *           second {@link Vector} of the cross product
    * @return cross product of {@code this} {@link Vector} and the {@link Vector}
    */
   public Vector cross(Vector v) {
      return Vector.cross(this, v);
   }

   /**
    * Calculates the cross product of the two {@link Vector}s in the order {@code v1} cross {@code
    * v2}.
    * 
    * @param v1
    *           first {@link Vector} of the cross product
    * @param v2
    *           second {@link Vector} of the cross product
    * @return cross product, {@code v1} cross {@code v2}
    */
   public static final Vector cross(Vector v1, Vector v2) {
      return new Vector(v1.y() * v2.z() - v2.y() * v1.z(), v2.x() * v1.z() - v1.x() * v2.z(), v1.x() * v2.y() - v2.x()
            * v1.y());
   }


   /**
    * Calculates the dot product of {@code this} {@link Vector} and a {@link Vector}.
    * 
    * @param v
    *           the {@link Vector} to be dotted with
    * @return {@code this} {@link Vector}
    */
   public double dot(Vector v) {
      return Vector.dot(this, v);
   }

   /**
    * Calculates the dot product of the two {@link Vector}s.
    * 
    * @param v1
    *           first {@link Vector} of the dot product
    * @param v2
    *           second {@link Vector} of the dot product
    * @return dot product of {@link Vector}s
    */
   public static final double dot(Vector v1, Vector v2) {
      return (v1.x() * v2.x() + v1.y() * v2.y() + v1.z() * v2.z());
   }


   /**
    * Calculates the angle between {@code this} {@link Vector} and another {@link Vector}.
    * 
    * @param v
    *           other {@link Vector}
    * @return angle between the two {@link Vector}s
    */
   public double angle(Vector v) {
      return Vector.angle(this, v);
   }

   /**
    * Calculates the angle between two {@link Vector}s.
    * 
    * @param v1
    *           first {@link Vector}
    * @param v2
    *           second {@link Vector}
    * @return angle between the two {@link Vector}s
    */
   public static final double angle(Vector v1, Vector v2) {
      return Math.acos(Vector.dot(v1, v2) / Math.sqrt(Vector.magnitudeSq(v1) * Vector.magnitudeSq(v2)));
   }


   /**
    * Rotates {@code this} {@link Vector} {@code 180} degrees or {@code 2pi} radians around the
    * given {@link Vector} .
    * 
    * @param v
    *           axis of rotation
    * @return {@code this} {@link Vector} reflected across axis
    */
   public Vector reflect(Vector v) {
      set(Vector.reflect(this, v));
      return this;
   }

   /**
    * Rotates {@link Vector} {@code v1 180} degrees or {@code 2pi} radians around {@link Vector}
    * {@code v2}.
    * 
    * @param v1
    *           {@link Vector} to reflect
    * @param v2
    *           {@link Vector} representing axis of rotation
    * @return {@code Vector v1} reflected across {@code Vector v2}
    */
   public static final Vector reflect(Vector v1, Vector v2) {
      return Vector.subtract(Vector.scale(2.0 * Vector.dot(v1, v2) / Vector.magnitudeSq(v2), v2), v1);
   }


   /**
    * Returns a copy of {@code this} {@link Vector} .
    * 
    * @return copy of {@code this} {@link Vector}
    */
   public Vector copy() {
      return new Vector(x(), y(), z());
   }


   /**
    * Returns a {@link String} with {@code this} {@link Vector} formated as it would appear in a
    * physics text book.
    * 
    * @return formated {@link Vector}
    */
   public String physicsFormat() {
      String str = vectorFormat.format(x()) + "i";
      if (y() != 0.0)
         str += (y() < 0.0 ? " - " : " + ") + vectorFormat.format(Math.abs(y())) + "j";
      if (z() != 0.0)
         str += (z() < 0.0 ? " - " : " + ") + vectorFormat.format(Math.abs(z())) + "k";
      return str;
   }


   /**
    * Returns the {@code String} representation of {@code this} {@link Vector} .
    * 
    * @return {@code this} {@link Vector} as a {@code String}
    */
   @Override
   public String toString() {
      return "Vector: [" + vectorFormat.format(x()) + (y() != 0.0 || z() != 0.0 ? "  " + vectorFormat.format(y()) : "")
            + (z() != 0.0 ? "  " + vectorFormat.format(z()) : "") + "]";
   }

   /**
    * Returns a clone of {@code this} {@link Vector} .
    * 
    * @return clone of {@code this} {@link Vector}
    */
   @Override
   public Object clone() {
      return copy();
   }

   /**
    * Returns {@code true} if some other object is equal to this {@link Vector}, {@code false}
    * otherwise.
    * 
    * @return {@code true} is {@code this} {@link Vector} is equal to the other object
    */
   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Vector) {
         Vector vec = (Vector) obj;
         return x() == vec.x() && y() == vec.y() && z() == vec.z();
      }
      return super.equals(obj);
   }

}
