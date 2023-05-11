package kid.segmentation;

import java.io.*;
import java.util.LinkedList;

import kid.RobocodeGraphicsDrawer;
import kid.data.*;
import robocode.RobocodeFileOutputStream;

// TODO documentation: class (10% complete)
// BORED code: cloneable?

public class TreeNode<E extends Segmentable, F extends Data, G extends Data> implements Cloneable, Serializable, Printable, Drawable {

   /**
    * Determines if a de-serialized file is compatible with this class.<BR>
    * <BR>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = -6162072213421568109L;

   private Leaf<E, F, G> leaf;
   private Branch<E, F, G> branch;

   private Segmenter<E, F, G>[] segments;

   public TreeNode() {
      init(null);
   }

   public TreeNode(Segmenter<E, F, G>[] segmenters) {
      init(segmenters);
   }

   private void init(Segmenter<E, F, G>[] segmenters) {
      this.leaf = new Leaf<E, F, G>(segmenters);
      this.branch = null;
      this.segments = segmenters;
   }

   public void add(E data, F view, G reference) {
      leaf.add(data, view, reference);
      if (branch != null)
         branch.add(data, view, reference);

      // FIXME code:
      if (Branch.splitable(leaf.getDataLog(), segments)) {
         branch = new Branch<E, F, G>();
         branch.split(leaf);
      }
   }

   // TODO code: method
   public LinkedList<E> get(F view, G reference) {
      return leaf.getDataLog();
   }

   protected void addFromSplit(E data, F view, G reference) {
      leaf.add(data, view, reference);
   }

   protected void clear() {
      leaf.clear();
      if (branch != null)
         branch.clear();
   }

   // TODO code: method
   public void print(PrintStream console) {
   }

   // TODO code: method
   public void print(RobocodeFileOutputStream output) {
   }

   // TODO code: method
   public void draw(RobocodeGraphicsDrawer grid, String commandString) {
      leaf.draw(grid, commandString);
   }

   @Override
   protected void finalize() throws Throwable {
      this.clear();
      leaf = null;
      branch = null;
      super.finalize();
   }
}