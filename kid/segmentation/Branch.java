package kid.segmentation;

import java.io.*;
import java.util.LinkedList;

import kid.RobocodeGraphicsDrawer;
import kid.data.*;
import robocode.RobocodeFileOutputStream;

// TODO  documentation: class (10% complete)
// BORED code: cloneable?

public class Branch<E extends Segmentable, F extends Data, G extends Data> implements Cloneable, Serializable, Printable, Drawable {

   /**
    * Determines if a de-serialized file is compatible with this class.<BR>
    * <BR>
    * Maintainers must change this value if and only if the new version of this class is not compatible with old
    * versions.
    */
   private static final long serialVersionUID = -5620003549784629496L;

   private TreeNode<E, F, G>[] treeNodes;
   private Segmenter<E, F, G> segmenter;

   public Branch() {
      init();
   }

   private void init() {
      this.treeNodes = null;
      this.segmenter = null;
   }

   protected void add(E data, F view, G reference) {
      segmenter.getTreeNode(treeNodes, view, reference).add(data, view, reference);
   }

   protected void clear() {
      for (TreeNode<E, F, G> t : treeNodes)
         t.clear();
   }

   protected static <E extends Segmentable, F extends Data, G extends Data> boolean splitable(LinkedList<E> dataList,
                                                                                              Segmenter<E, F, G>[] possibleSegmenters) {
      // boolean canSplit = false;
      // if (dataList != null && possibleSegmenters != null && dataList.size() > 0 &&
      // possibleSegmenters.length > 0)
      // for (Segmenter s : possibleSegmenters)
      // canSplit = canSplit && s.canSplit();
      return false;
   }

   // FIXME code: create splitting method
   protected void split(Leaf<E, F, G> parent) {
      if (parent == null || parent.getSegmenters() != null || parent.getDataLog() != null || parent.getDataLog().size() == 0)
         return;

      Segmenter<E, F, G>[] segmenters = parent.getSegmenters();

      for (int i = 0; i < segmenters.length; i++) {

      }
   }


   // TODO code: method
   public void print(PrintStream console) {
   }

   // TODO code: method
   public void print(RobocodeFileOutputStream output) {
   }

   // TODO code: method
   public void draw(RobocodeGraphicsDrawer grid, String commandString) {
   }

   // TODO code: method
   public void debug(PrintStream console) {
   }

   // TODO code: method
   public void debug(RobocodeFileOutputStream output) {
   }

   @Override
   protected void finalize() throws Throwable {
      clear();
      treeNodes = null;
      segmenter = null;
      super.finalize();
   }

}
