package kid.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

import kid.data.Data;
import kid.segmentation.Segmentable;
import kid.utils.Utils;

public class Cluster<E extends Data> {

   private Collection<E> cluster;

   private int size;


   public Cluster(int s) {
      cluster = new LinkedList<E>();
      size = s;
   }

   public <F extends Segmentable, G extends Segmentable> Cluster(ArrayList<Dimension<E, F, G>> dimensions, F view,
         G reference, int s) {
      this(s);
      buildCluster(dimensions, view, reference);
   }


   public Collection<E> getCluster() {
      return cluster;
   }


   public <F extends Segmentable, G extends Segmentable> void buildCluster(ArrayList<Dimension<E, F, G>> dimensions,
         F view, G reference) {
      cluster = createCluster(dimensions, view, reference, size);
   }


   public static <E extends Data, F extends Segmentable, G extends Segmentable> Collection<E> createCluster(
         ArrayList<Dimension<E, F, G>> dimensions, F view, G reference, int size) {
      Vector<E, F, G> match = new Vector<E, F, G>(null, view, reference);

      LinkedList<Vector<E, F, G>> nearestMatches = new LinkedList<Vector<E, F, G>>();
      for (Dimension<E, F, G> d : dimensions) {
         for (Vector<E, F, G> v : d.getList(view, reference, size)) {
            nearestMatches.add(v);
         }
      }

      LinkedList<Entry<E>> bestMatches = new LinkedList<Entry<E>>();
      Vector<E, F, G> vector;

      while ((vector = nearestMatches.pollFirst()) != null) {
         double value = 0;
         for (Dimension<E, F, G> d : dimensions)
            value += Utils.sqr(d.dist(match, vector));

         ListIterator<Entry<E>> iter = bestMatches.listIterator();
         boolean added = false;

         while (iter.hasNext() && !added) {
            Entry<E> v = iter.next();

            if (vector.getData() == v.data) {
               // has already been added
               added = true;
            } else if (value < v.value) {
               iter.previous();
               iter.add(new Entry<E>(vector.getData(), value));
               added = true;
            }
         }

         if (iter.nextIndex() < size && !added)
            iter.add(new Entry<E>(vector.getData(), value));

         if (bestMatches.size() > size)
            bestMatches.removeLast();
      }

      LinkedList<E> cluster = new LinkedList<E>();
      for (Entry<E> v : bestMatches)
         cluster.add(v.data);
      return cluster;
   }


   private static class Entry<E extends Data> {

      protected E data;

      protected double value;


      public Entry(E d, double v) {
         data = d;
         value = v;
      }

   }

}
