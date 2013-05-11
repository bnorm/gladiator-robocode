package kid.cluster;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;

import kid.data.Data;
import kid.data.Printable;
import kid.segmentation.Segmentable;
import robocode.RobocodeFileOutputStream;

public class Space<E extends Data, F extends Segmentable, G extends Segmentable> implements Printable {

   private ArrayList<Dimension<E, F, G>> dimensions;

   public Space(Comparison[] comparisons) {
      dimensions = new ArrayList<Dimension<E, F, G>>(comparisons.length);
      for (int i = 0; i < comparisons.length; i++)
         dimensions.add(new Dimension<E, F, G>(comparisons[i]));
   }

   public void add(E data, F view, G reference) {
      Vector<E, F, G> v = new Vector<E, F, G>(data, view, reference);
      for (Dimension<E, F, G> d : dimensions)
         d.add(v);
   }

   public Collection<E> getCluster(F view, G reference, int size) {
      Cluster<E> cluster = new Cluster<E>(dimensions, view, reference, size);
      return cluster.getCluster();
   }

   @Override
   public void print(PrintStream console) {
      for (Dimension<E, F, G> d : dimensions) {
         d.print(console);
      }
      console.println();
   }

   @Override
   public void print(RobocodeFileOutputStream output) {
      PrintStream out = new PrintStream(output);
      for (Dimension<E, F, G> d : dimensions) {
         d.print(output);
      }
      out.println();
   }

}
