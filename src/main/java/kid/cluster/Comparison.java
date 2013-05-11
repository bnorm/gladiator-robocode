package kid.cluster;

import kid.data.Data;
import kid.segmentation.Segmentable;

public abstract class Comparison {

   /**
    * Returns negative if v1 is some how smaller then v2, zero if they are the same and positive if v1 is greater then
    * v2 (i.e. v1 - v2).
    * 
    * @param <E>
    * @param <F>
    * @param <G>
    * @param v1
    * @param v2
    * @return
    */
   public <E extends Data, F extends Segmentable, G extends Segmentable> double compare(Vector<E, F, G> v1, Vector<E, F, G> v2) {
      return value(v1) - value(v2);
   }

   public abstract <E extends Data, F extends Segmentable, G extends Segmentable> double value(Vector<E, F, G> v);

}
