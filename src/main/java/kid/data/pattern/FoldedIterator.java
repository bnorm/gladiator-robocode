package kid.data.pattern;

import java.util.List;
import java.util.ListIterator;

// FIXME code: GAAAAAAA!!!!!!
public class FoldedIterator<E extends Foldable> implements ListIterator<E> {

   private List<FoldedEntry<E>> list;

   private FoldedEntry<E> next;
   private FoldedEntry<E> previous;
   private FoldedEntry<E> foldnext;
   private FoldedEntry<E> foldprevious;

   public FoldedIterator(List<FoldedEntry<E>> list) {
      init(list, 0);
   }

   public FoldedIterator(List<FoldedEntry<E>> list, int startIndex) {
      init(list, startIndex);
   }

   private void init(List<FoldedEntry<E>> list, int index) {
      this.list = list;
      jump(index);
   }

   // FIXME code: correctly set fold jumping
   public void jump(int index) {
      next = null;
      foldnext = null;
      foldprevious = null;
      previous = null;

      if (index < list.size() && index >= 0)
         next = list.get(index);
      if (index <= list.size() && index > -1)
         previous = list.get(index - 1);
   }

   public int size() {
      return list.size();
   }

   public void add(E o) {
   }

   public boolean hasNext() {
      return next != null;
   }

   public boolean hasNextFold() {
      return foldnext != null;
   }

   public boolean hasPrevious() {
      return previous != null;
   }

   public boolean hasPreviousFold() {
      return foldprevious != null;
   }

   public E next() {
      E i = next.item;

      previous = next;
      foldprevious = next;
      foldnext = next.nextFold;
      next = next.next;

      return i;
   }

   public E nextFold() {
      E i = foldnext.item;

      previous = foldnext;
      foldprevious = foldnext;
      next = foldnext.next;
      foldnext = foldnext.nextFold;

      return i;
   }

   public int nextIndex() {
      return next.index;
   }

   public int nextIndexFold() {
      return foldnext.index;
   }

   public E previous() {
      E i = previous.item;

      next = previous;
      foldnext = previous;
      foldprevious = previous.previousFold;
      previous = previous.previous;

      return i;
   }

   public E previousFold() {
      E i = foldprevious.item;

      next = foldprevious;
      foldnext = foldprevious;
      previous = foldprevious.previous;
      foldprevious = foldprevious.previousFold;

      return i;
   }

   public int previousIndex() {
      return previous.index;
   }

   public int previousIndexFold() {
      return foldprevious.index;
   }

   public void remove() {
   }

   public void set(E arg0) {
   }

}
