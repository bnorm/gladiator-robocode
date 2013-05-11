package kid.data.gravity;

import java.util.ArrayList;
import java.util.List;

import kid.utils.Vector;
import robocode.Robot;

// TODO documentation: kid.data.gravity.GravityEngine (0% complete)
// TODO code: kid.data.gravity.GravityEngine - com.bnorm.pathfinder.core.test class for errors, movement on old doesn't seem right
public class GravityEngine {

   protected static double WALL_FORCE = -25.0;

   protected static double BATTLEFIELD_WIDTH;
   protected static double BATTLEFIELD_HEIGHT;

   protected List<GravityObject> gravityPoints = new ArrayList<GravityObject>();
   protected Vector force;

   public GravityEngine() {
      init(0, 0);
   }

   public GravityEngine(double width, double height) {
      init(width, height);
   }


   private void init(double width, double height) {
      GravityEngine.BATTLEFIELD_WIDTH = width;
      GravityEngine.BATTLEFIELD_HEIGHT = height;
   }


   public double getWallForce() {
      return WALL_FORCE;
   }

   public void setWallForce(double wf) {
      GravityEngine.WALL_FORCE = wf;
   }


   public long size() {
      return gravityPoints.size();
   }


   public void add(GravityObject g) {
      gravityPoints.add(g);
   }

   public boolean remove(GravityObject g) {
      return gravityPoints.remove(g);
   }

   public void reset() {
      gravityPoints = new ArrayList<GravityObject>();
   }


   public void update(Robot MyRobot) {
      update(MyRobot.getX(), MyRobot.getY(), MyRobot.getTime());
   }

   public void update(double x, double y, long time) {
      force = new Vector();
      for (GravityObject g : gravityPoints) {
         if (g.active(time)) {
            Vector r = new Vector(g.deltaX(x), g.deltaY(y));
            force.add(Vector.scale(g.getStrength() / r.magnitudeSq(), Vector.unit(r)));
         }
      }

      Vector wall = new Vector(-x);
      force.add(Vector.scale(WALL_FORCE / wall.magnitudeSq(), Vector.unit(wall)));
      wall = new Vector(BATTLEFIELD_WIDTH - x);
      force.add(Vector.scale(WALL_FORCE / wall.magnitudeSq(), Vector.unit(wall)));
      wall = new Vector(0.0, BATTLEFIELD_HEIGHT - y);
      force.add(Vector.scale(WALL_FORCE / wall.magnitudeSq(), Vector.unit(wall)));
      wall = new Vector(0.0, -y);
      force.add(Vector.scale(WALL_FORCE / wall.magnitudeSq(), Vector.unit(wall)));
   }


   public Vector getForceVector(double x, double y, long time) {
      update(x, y, time);
      return force;
   }

   public Vector getForceVector() {
      return force;
   }


}
