package dev.cluster;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.TreeMap;

import dev.utils.Utils;

public class Dimension<E> {

   public void print() {
      String str = this.scale.getClass().getName() + ": ";
      for (Vector<E> v : tree_.values()) {
         str += v.getComponent(this.scale) + " ";
      }
      System.out.println(str);
   }



   private Scale                         scale;
   private LinkedList<Vector<E>>         vectors;

   private TreeMap<Vector<E>, Vector<E>> tree_;
   private Comparator<Vector<E>>         treeCompare_;

   public Dimension(Scale s) {
      this.scale = s;
      this.vectors = new LinkedList<Vector<E>>();

      treeCompare_ = new Comparator<Vector<E>>() {
         @Override
         public int compare(Vector<E> o1, Vector<E> o2) {
            return (int) (o1.getComponent(scale) - o2.getComponent(scale));
         }
      };

      tree_ = new TreeMap<Vector<E>, Vector<E>>(treeCompare_);
   }

   public Scale getScale() {
      return this.scale;
   }

   public void add(Vector<E> v) {
      if (v != null)
         this.vectors.add(this.binarySearch(v), v);
      if (v != null) {
         tree_.put(v, v);
      }
   }

   public LinkedList<Vector<E>> getCluster(Vector<E> center, int size) {
      LinkedList<Vector<E>> cluster = new LinkedList<Vector<E>>();

      int index = this.binarySearch(center);
      ListIterator<Vector<E>> iterN = this.vectors.listIterator(index);
      ListIterator<Vector<E>> iterP = this.vectors.listIterator(index);

      if (iterN.hasNext()) {
         if (iterP.hasPrevious()) {
            Vector<E> vN = iterN.next();
            Vector<E> vP = iterP.previous();
            double cN = Utils.abs(this.scale.compare(center, vN));
            double cP = Utils.abs(this.scale.compare(center, vP));

            do {
               if (vN == null) {
                  // fill with iterP
                  cluster.add(vP);
                  while (cluster.size() < size && iterP.hasPrevious()) {
                     cluster.add(iterP.previous());
                  }
               } else if (vP == null) {
                  // fill with iterN
                  cluster.add(vN);
                  while (cluster.size() < size && iterN.hasNext()) {
                     cluster.add(iterN.next());
                  }
               } else {
                  if (cN < cP) {
                     // vN is closer then vP
                     cluster.add(vN);
                     if (iterN.hasNext()) {
                        vN = iterN.next();
                        cN = Utils.abs(this.scale.compare(center, vN));
                     } else {
                        vN = null;
                        cN = Double.NaN;
                     }
                  } else if (cN > cP) {
                     // vP is closer then vN
                     cluster.add(vP);
                     if (iterP.hasPrevious()) {
                        vP = iterP.previous();
                        cP = Utils.abs(this.scale.compare(center, vP));
                     } else {
                        vP = null;
                        cP = Double.NaN;
                     }
                  } else if (cN == cP) {
                     // vN is equal to vP
                     cluster.add(vN);
                     if (iterN.hasNext()) {
                        vN = iterN.next();
                        cN = Utils.abs(this.scale.compare(center, vN));
                     } else {
                        vN = null;
                        cN = Double.NaN;
                     }

                     cluster.add(vP);
                     if (iterP.hasPrevious()) {
                        vP = iterP.previous();
                        cP = Utils.abs(this.scale.compare(center, vP));
                     } else {
                        vP = null;
                        cP = Double.NaN;
                     }
                  }
               }
            } while ((cluster.size() < size || cN == cP) && (iterN.hasNext() || iterP.hasPrevious()));
         } else {
            // fill with iterN
            do {
               cluster.add(iterN.next());
            } while (cluster.size() < size && iterN.hasNext());
         }
      } else if (iterP.hasPrevious()) {
         // fill with iterP
         do {
            cluster.add(iterP.previous());
         } while (cluster.size() < size && iterP.hasPrevious());
      } // else leave empty
      return cluster;
   }


   /**
    * Returns the index of the element that the {@link Vector} should be put before.
    * 
    * @param v
    * @param vectors
    * @return
    */
   private int binarySearch(Vector<E> v) {
      int first = 0;
      int last = this.vectors.size();
      int mid = (last + first) / 2;
      while (first < last) {
         mid = (last + first) / 2;
         Vector<E> m = this.vectors.get(mid);
         int sign_m = Utils.signum(this.scale.compare(v, m));
         if (sign_m == 0) {
            first = last = mid;
         } else if (sign_m == 1) {
            last = mid;
         } else {
            first = ++mid;
         }
      }
      return mid;
   }
}
