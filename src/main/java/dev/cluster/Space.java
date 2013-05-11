package dev.cluster;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import dev.robots.RobotData;
import dev.utils.Utils;

public class Space<E> {

   public void print() {
      for (Dimension<E> d : this.dimensions) {
         d.print();
      }
   }



   private LinkedList<Dimension<E>> dimensions;
   private Collection<Scale>        scales;

   public Space(Collection<Scale> scales) {
      this.scales = scales;
      this.dimensions = new LinkedList<Dimension<E>>();
      for (Scale s : scales)
         this.dimensions.add(new Dimension<E>(s));
   }

   public Space(Scale[] scales) {
      this(Arrays.asList(scales));
   }


   public void add(RobotData view, RobotData reference, E data) {
      Vector<E> v = new Vector<E>(this.scales, view.copy(), reference.copy(),
            data);
      for (Dimension<E> d : this.dimensions) {
         d.add(v);
      }
   }

   public LinkedList<E> getClustor(RobotData view, RobotData reference, int size) {
      Vector<E> center = new Vector<E>(this.scales, view, reference, null);
      LinkedList<SortVector<E>> sorted = new LinkedList<SortVector<E>>();
      for (Dimension<E> d : this.dimensions) {
         for (Vector<E> v : d.getCluster(center, size)) {
            if (!sorted.contains(new SortVector<E>(v, 0.0))) {

               // TODO code: make so that one scale does not have precedence
               // over another
               double sort = 0.0;
               for (Scale s : this.scales)
                  sort += Utils.sqr(s.compareNormalized(center, v));

               // binary search
               int first = 0;
               int last = sorted.size();
               int mid = (last + first) / 2;
               while (first < last) {
                  mid = (last + first) / 2;
                  SortVector<E> m = sorted.get(mid);
                  int sign_m = Utils.signum(m.sort - sort);
                  // smaller values at the beginning
                  if (sign_m == 0) {
                     first = last = mid;
                  } else if (sign_m == -1) {
                     first = ++mid;
                  } else {
                     last = mid;
                  }
               }

               sorted.add(mid, new SortVector<E>(v, sort));

               // remove any vectors that make the cluster to large
               if (sorted.size() > size) {
                  SortVector<E> v1 = sorted.get(size);
                  SortVector<E> v2 = sorted.get(size - 1);

                  // only remove vectors that don't tie for the last position
                  if (v1.sort != v2.sort) {
                     while (sorted.size() > size) {
                        sorted.removeLast();
                     }
                  }
               }
            }
         }
      }

      LinkedList<E> cluster = new LinkedList<E>();
      for (Vector<E> sv : sorted)
         cluster.add(sv.getData());
      
      String str = "CLUSTER:\n";
      for (Vector<E> v : sorted) {
         str += v;
      }
      System.out.println(str);
      
      return cluster;
   }


   private static class SortVector<E> extends Vector<E> {

      private Vector<E> vector;
      private double    sort;

      public SortVector(Vector<E> v, double sort) {
         this.vector = v;
         this.sort = sort;
      }

      @Override
      protected double getComponent(Scale s) {
         return this.vector.getComponent(s);
      }

      @Override
      public E getData() {
         return this.vector.getData();
      }

      @Override
      public RobotData getView() {
         return this.vector.getView();
      }

      @Override
      public RobotData getReference() {
         return this.vector.getReference();
      }

      @Override
      public boolean equals(Object obj) {
         if (obj instanceof SortVector<?>) {
            SortVector<?> sortVec = (SortVector<?>) obj;
            return this.vector.equals(sortVec.vector);
         } else if (obj instanceof Vector<?>) {
            return this.vector.equals(obj);
         } else {
            return super.equals(obj);
         }
      }

   }

}
