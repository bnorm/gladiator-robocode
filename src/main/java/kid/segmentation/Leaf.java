package kid.segmentation;

import java.util.ArrayList;
import java.util.List;

public class Leaf<E extends Segmentable> {

   private List<E> bin;

   public Leaf() {
      this(new ArrayList<E>());
   }

   public Leaf(List<E> data) {
      bin = data;
   }
   
   public List<E> getData() {
      return bin;
   }

}
