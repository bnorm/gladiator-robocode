package kid.segmentation;

import kid.Utils;
import kid.data.Data;

// TODO documentation: class (0% complete)

public abstract class Segmenter<E extends Segmentable, F extends Data, G extends Data> {

   protected double minValue;
   protected double midValue;
   protected double maxValue;

   protected abstract double getSegmentValue(F view, G reference);

   public TreeNode<E, F, G> getTreeNode(TreeNode<E, F, G>[] treeNodes, F view, G reference) {
      TreeNode<E, F, G> tree = null;
      if (treeNodes.length == 2) {
         double distanceSq = getSegmentValue(view, reference);
         if (distanceSq == 0.0D)
            tree = new TreeNode<E, F, G>();
         else if (midValue > distanceSq)
            tree = treeNodes[0];
         else
            tree = treeNodes[1];
      }
      return tree;
   }

   public final int getIndex(F view, G reference, int numOfBins) {
      double factor = (getSegmentValue(view, reference) - minValue) / (maxValue - minValue);
      return Utils.round((numOfBins - 1.0D) * (factor));
   }

   public abstract Segmenter<E, F, G>[] getChildSegmenters(int[] spliters);

   public boolean canSplit() {
      return canSplit(2);
   }

   public abstract boolean canSplit(int numOfBranches);

}
