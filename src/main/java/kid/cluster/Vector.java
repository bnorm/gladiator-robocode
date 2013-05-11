package kid.cluster;

import kid.data.Data;
import kid.segmentation.Segmentable;

public class Vector<E extends Data, F extends Segmentable, G extends Segmentable> {

   private E data;
   private F view;
   private G reference;

   public Vector(E d, F v, G r) {
      data = d;
      view = v;
      reference = r;
   }

   public E getData() {
      return data;
   }

   public F getView() {
      return view;
   }

   public G getReference() {
      return reference;
   }

}
