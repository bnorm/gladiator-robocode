package kid.management;

import java.util.HashMap;
import java.util.Set;

import kid.cluster.Comparison;
import kid.data.Drawable;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.robot.EnemyData;
import kid.robot.EnemyProfile;
import kid.robot.RobotData;
import robocode.AdvancedRobot;
import robocode.Bullet;
import robocode.Event;
import robocode.ScannedRobotEvent;

public class ProfileManager implements Drawable {

   private AdvancedRobot robot;
   private RobotManager robots;

   private static HashMap<String, EnemyProfile> profiles;
   private Comparison[] comparisons;

   public ProfileManager(AdvancedRobot myRobot, Comparison[] comparisons) {
      init(myRobot, comparisons);
   }

   private void init(AdvancedRobot r, Comparison[] c) {
      robot = r;
      robots = new RobotManager(r);
      comparisons = c;

      if (profiles == null)
         profiles = new HashMap<String, EnemyProfile>(r.getOthers());
      else {
         Set<String> keys = profiles.keySet();
         for (String s : keys)
            profiles.get(s).update(r);
      }
   }

   public EnemyProfile getProfile(String name) {
      if (profiles.containsKey(name))
         return profiles.get(name);
      return null;
   }

   public void fire(EnemyData target, Bullet b) {
      if (b != null)
         fire(target, b.getPower());
   }

   public void fire(EnemyData target, double power) {
      profiles.get(target.getName()).fire(power);
   }

   public void inEvent(final Event e) {
      robots.inEvent(e);
      if (e instanceof ScannedRobotEvent)
         handleScannedRobot((ScannedRobotEvent) e);
   }

   private void handleScannedRobot(final ScannedRobotEvent event) {
      if (!profiles.containsKey(event.getName()))
         profiles.put(event.getName(), new EnemyProfile(robot, robots.getEnemy(event.getName()), comparisons));
   }

   public void print() {
      for (RobotData r : robots.getRobots()) {
         System.out.println(r.getName());
         profiles.get(r.getName()).print();
      }
   }

   @Override
   public void draw(RGraphics grid) {
      if (DrawMenu.getValue("Waves", "Targeting")) {
         for (RobotData r : robots.getRobots())
            profiles.get(r.getName()).draw(grid);
      }
   }

}
