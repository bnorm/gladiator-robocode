package kid.data.gravity;

// TODO document class

public interface GravityObject {

   public double dist(double x, double y);

   public double distSq(double x, double y);

   public double deltaX(double x);

   public double deltaY(double y);

   public double angle(double x, double y);

   public double getStrength();

   public boolean active(long time);

}
