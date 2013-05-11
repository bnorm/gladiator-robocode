package kid.data.pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import kid.graphics.Colors;
import kid.graphics.DrawMenu;
import kid.graphics.RGraphics;
import kid.management.RobotManager;
import kid.robot.RobotData;
import kid.utils.Utils;
import robocode.Event;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class PatternMatcher<E extends Pattern> {

   private RobotRecorder<E>                            recorder;
   private HashMap<String, LinkedList<FoldedEntry<E>>> robotMatches;

   private E                                           NULL_PATTERN;

   private static RobotManager                         robots;

   public PatternMatcher() {
   }

   public void start(Robot myRobot, PatternFactory<E> factory) {
      if (recorder == null) {
         recorder = new RobotRecorder<E>(myRobot, factory);
         robotMatches = new HashMap<String, LinkedList<FoldedEntry<E>>>();
         NULL_PATTERN = factory.getNullPattern();
         robots = new RobotManager(myRobot);
      }
   }

   public FoldedEntry<E> getMatch(String name) {
      LinkedList<FoldedEntry<E>> matches = robotMatches.get(name);
      if (matches != null) {
         return matches.getFirst();
         // return getBestMatch(matches.subList(0, Math.min(matches.size(),
         // 10)));
      }
      return null;
   }

   // TODO code: way to slow, got to speed it up some how
   public FoldedEntry<E> getBestMatch(List<FoldedEntry<E>> choices) {
      LinkedList<FoldedEntry<E>> best = new LinkedList<FoldedEntry<E>>(choices);
      LinkedList<FoldedEntry<E>> future = new LinkedList<FoldedEntry<E>>(best);
      int recersion = 0;
      while (best.size() > 1 && recersion < 35) {
         recersion++;
         LinkedList<FoldedEntry<E>> futureTemp = new LinkedList<FoldedEntry<E>>(future);
         future.clear();

         ArrayList<Character> folds = new ArrayList<Character>(futureTemp.size());
         int[] count = new int[futureTemp.size()];
         for (FoldedEntry<E> e : futureTemp) {
            if (goodNext(e)) {
               future.add(e.next);
               char fold = e.next.item.getFold();
               int index = folds.indexOf(fold);
               if (index >= 0) {
                  count[index]++;
               } else {
                  folds.add(fold);
               }
            }
         }
         int max = 0;
         int index = -1;
         for (int i = 0; i < folds.size(); i++) {
            int c = count[i];
            if (c > max) {
               max = c;
               index = i;
            }
         }

         if (index == -1) {
            return best.getFirst();
         }

         char fold = folds.get(index);
         ListIterator<FoldedEntry<E>> iterB = best.listIterator();
         ListIterator<FoldedEntry<E>> iterF = future.listIterator();
         while (iterB.hasNext() && iterF.hasNext()) {
            iterB.next();
            if (iterF.next().item.getFold() != fold) {
               iterB.remove();
               iterF.remove();
            }
         }
      }
      return best.getFirst();
   }

   public void inEvent(Event e) {
      recorder.inEvent(e);
      if (e instanceof ScannedRobotEvent) {
         handleScannedRobotEvent((ScannedRobotEvent) e);
      }
   }

   private void handleScannedRobotEvent(ScannedRobotEvent e) {
      if (!robotMatches.containsKey(e.getName()))
         robotMatches.put(e.getName(), new LinkedList<FoldedEntry<E>>());

      FoldedLinkedList<E> movie = recorder.get(e.getName());
      if (movie == null)
         return;
      E latest = movie.getLast();

      LinkedList<FoldedEntry<E>> matches = robotMatches.get(e.getName());
      LinkedList<FoldedEntry<E>> newMatches = new LinkedList<FoldedEntry<E>>();
      for (FoldedEntry<E> match : matches) {
         if (latest.equals(match.next.item) && goodNext(match.next)) {
            newMatches.add(match.next);
         }
      }

      int size = movie.size();

      LinkedList<FoldedEntry<E>> fold = movie.getFold(latest.getFold());
      if (newMatches.size() < 100 && fold != null) {
         for (FoldedEntry<E> item : fold) {
            if (size - item.index > 50)
               newMatches.add(item);
         }
         newMatches.addAll(fold);
      }
      robotMatches.put(e.getName(), newMatches);
   }

   private boolean goodNext(FoldedEntry<E> entry) {
      return entry != null && entry.next != null && entry.next.item != NULL_PATTERN;
   }

   public void draw(final RGraphics grid) {
      recorder.draw(grid);

      if (!DrawMenu.getValue("Matcher", "Movie"))
         return;
      grid.setColor(Colors.GREEN);
      for (String name : robotMatches.keySet()) {
         FoldedLinkedList<E> list = recorder.get(name);
         LinkedList<FoldedEntry<E>> matchList = robotMatches.get(name);
         RobotData r = robots.getRobot(name);
         FoldedEntry<E> match = getMatch(name);
         if (match.item instanceof Polar && !r.isDead() && list != null && matchList.size() > 0) {
            double x = r.getX(), y = r.getY(), h = r.getHeading();
            Polar p;

            for (int i = 0; goodNext(match) && i < 50; i++) {
               match = match.next;
               p = (Polar) match.item;
               h += p.getDeltaHeading();
               x += p.getVelocity() * Utils.sin(h);
               y += p.getVelocity() * Utils.cos(h);
               grid.fillOvalCenter(x, y, 2.0D, 2.0D);
               if (!goodNext(match)) {
                  match = getMatch(name);
               }
            }
         }
      }
   }
}
