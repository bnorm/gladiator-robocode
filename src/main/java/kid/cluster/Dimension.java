package kid.cluster;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.ListIterator;

import kid.data.Data;
import kid.data.Printable;
import kid.segmentation.Segmentable;
import robocode.RobocodeFileOutputStream;

public class Dimension<E extends Data, F extends Segmentable, G extends Segmentable> implements Printable {

   private static final double percent = .05;
   private static double epsilon = .13;

   private LinkedList<Item> data;
   private Comparison comparison;
   private double maxValue;
   private double scale;

   public Dimension(Comparison c) {
      comparison = c;
      data = new LinkedList<Item>();
      maxValue = 0.0;
      scale = 100.0;
   }

   public void add(E d, F v, G r) {
      Vector<E, F, G> vector = new Vector<E, F, G>(d, v, r);
      add(vector);
   }

   public void add(Vector<E, F, G> vector) {
      boolean added = false;
      double value = comparison.value(vector);
      maxValue = Math.max(maxValue, Math.abs(value));
      if (data.size() == 0) {
         data.add(new Item(vector, value));
      } else if (value - data.getFirst().value < data.getLast().value - value) {
         ListIterator<Item> iter = data.listIterator();
         while (iter.hasNext() && !added) {
            Item i = iter.next();
            if (value <= i.value) {
               iter.previous();
               iter.add(new Item(vector, value));
               added = true;
            }
         }
         if (!added)
            iter.add(new Item(vector, value));
      } else {
         ListIterator<Item> iter = data.listIterator(data.size());
         while (iter.hasPrevious() && !added) {
            Item i = iter.previous();
            if (value >= i.value) {
               iter.next();
               iter.add(new Item(vector, value));
               added = true;
            }
         }
         if (!added)
            iter.add(new Item(vector, value));
      }

      boolean DO_SCALE = false;
      if (DO_SCALE) { // Weigh the dimension.
         // System.out.println("start scaling...");
         Data d = vector.getData();
         double max = d.max();

         ListIterator<Item> iter = data.listIterator();
         while (iter.hasNext() && iter.next().vector != vector)
            ;

         int addIndex = iter.previousIndex();
         int indexWidth = (int) (percent * data.size());

         int start = Math.max(addIndex - indexWidth, 0);
         int end = Math.min(addIndex + indexWidth, data.size() - 1);

         while (iter.hasPrevious() && iter.previousIndex() > start)
            iter.previous();

         while (iter.hasNext() && iter.nextIndex() < end) {
            if (iter.nextIndex() != addIndex) {
               double dif = epsilon - Math.abs(d.compare(iter.next().vector.getData()) / max);
               scale += dif * 5.0;
            }
         }
         if (scale < 0.0)
            scale = 0.0;
         if (scale < 50.0)
            epsilon += .005;
         else if (scale > 200.0)
            epsilon -= .01;
         // System.out.println("...scaling ended");
      }
   }

   public Comparison getComparison() {
      return comparison;
   }

   public double getMaxValue() {
      return maxValue;
   }

   public double getScale() {
      return scale;
   }

   public int size() {
      return data.size();
   }

   public double dist(Vector<E, F, G> v1, Vector<E, F, G> v2) {
      return comparison.compare(v1, v2) / getMaxValue() * getScale();
   }

   public LinkedList<Vector<E, F, G>> getList(F view, G reference, int total) {
      ListIterator<Item> first = data.listIterator();
      ListIterator<Item> last = data.listIterator(data.size());

      if (data.size() > total) {

         Vector<E, F, G> v = new Vector<E, F, G>(null, view, reference);
         double value = comparison.value(v);

         while (last.previousIndex() - first.nextIndex() > total) {
            if (Math.abs(first.next().value - value) > Math.abs(last.previous().value - value)) {
               last.next();
            } else {
               first.previous();
            }
         }
      }

      LinkedList<Vector<E, F, G>> list = new LinkedList<Vector<E, F, G>>();
      while (first.nextIndex() <= last.previousIndex())
         list.add(first.next().vector);

      return list;
   }

   @Override
   public void print(PrintStream console) {
      console.println(comparison.toString() + ": " + scale + ", " + data.size());
   }

   @Override
   public void print(RobocodeFileOutputStream output) {
      // BORED Auto-generated method stub
   }

   private class Item {
      protected Vector<E, F, G> vector;
      protected double value;

      public Item(Vector<E, F, G> vec, double val) {
         vector = vec;
         value = val;
      }
   }

}
