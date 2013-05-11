package kid.data.pattern;

import java.util.HashMap;
import java.util.ListIterator;

import kid.data.Drawable;
import kid.graphics.Colors;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.management.RobotManager;
import kid.robot.RobotData;
import kid.utils.Utils;
import robocode.DeathEvent;
import robocode.Event;
import robocode.Robot;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

// FIXME code: update for skipped scans

public class RobotRecorder<E extends Pattern> implements Drawable {

   private Robot robot;
   private RobotManager robots;

   private PatternFactory<E> factory;

   private HashMap<String, FoldedLinkedList<E>> movies = null;


   public RobotRecorder(Robot myRobot, PatternFactory<E> factory) {
      this.robot = myRobot;
      this.factory = factory;
      this.robots = new RobotManager(myRobot);
      if (movies == null)
         movies = new HashMap<String, FoldedLinkedList<E>>(robot.getOthers());
   }

   public FoldedLinkedList<E> get(String name) {
      return movies.get(name);
   }

   public void inEvent(Event e) {
      robots.inEvent(e);
      if (e instanceof ScannedRobotEvent)
         handleScannedRobot((ScannedRobotEvent) e);
      if (e instanceof RobotDeathEvent)
         handleRobotDeath((RobotDeathEvent) e);
      if (e instanceof DeathEvent)
         handleDeath((DeathEvent) e);
   }

   private void handleScannedRobot(ScannedRobotEvent event) {
      // FIXME code: fix skipped turns
      if (!movies.containsKey(event.getName()))
         movies.put(event.getName(), new FoldedLinkedList<E>());
      RobotData r = robots.getRobot(event.getName());
      movies.get(r.getName()).add(factory.getPattern(r, robot));
   }

   private void handleRobotDeath(RobotDeathEvent event) {
      if (movies.containsKey(event.getName()))
         movies.get(event.getName()).add(factory.getNullPattern());
   }

   private void handleDeath(DeathEvent event) {
      for (RobotData r : robots.getRobots())
         movies.get(r.getName()).add(factory.getNullPattern());
   }

   public void draw(RGraphics grid) {
      if (!DrawMenu.getValue("Recorder", "Movie"))
         return;
      grid.setColor(Colors.BLUE);
      for (RobotData r : robots.getRobots()) {
         FoldedLinkedList<E> list = movies.get(r.getName());
         if (!r.isDead() && list != null && list.size() > 0 && list.getFirst() instanceof Polar) {
            ListIterator<E> iterator = list.listIterator(list.size());
            double x = r.getX(), y = r.getY(), h = r.getHeading();
            Polar p;
            for (int i = 0; iterator.hasPrevious() && (p = (Polar) iterator.previous()) != Polar.DUMMY_PATTERN
                  && i < 100; i++) {
               x -= p.getVelocity() * Utils.sin(h);
               y -= p.getVelocity() * Utils.cos(h);
               h -= p.getDeltaHeading();
               grid.fillOvalCenter(x, y, 2.0D, 2.0D);
            }
         }
      }
   }

}
