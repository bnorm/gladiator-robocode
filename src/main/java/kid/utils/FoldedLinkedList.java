package kid.utils;

import java.util.LinkedList;

public class FoldedLinkedList<E extends Object, F extends Object> {

   private LinkedList<E> list;
   private LinkedList<E>[] folds;

   public FoldedLinkedList(final int numOfFolds) {
      init(numOfFolds);
   }

   @SuppressWarnings("unchecked")
   private void init(final int numOfFolds) {
      folds = new LinkedList[numOfFolds];
      for (int i = 0; i < numOfFolds; i++)
         folds[i] = new LinkedList<E>();
   }

   public void add(final E object, final int fold) {
      list.add(object);
      if (Utils.contains(folds, fold))
         folds[fold].add(object);
   }

   public LinkedList<E> getList() {
      return list;
   }

   public LinkedList<E> getFold(final int fold) {
      if (Utils.contains(folds, fold))
         return folds[fold];
      return new LinkedList<E>();
   }

}
