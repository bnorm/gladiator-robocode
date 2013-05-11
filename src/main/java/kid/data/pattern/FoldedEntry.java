package kid.data.pattern;


public class FoldedEntry<E extends Foldable> implements Foldable {

   public FoldedEntry(E item, int index, FoldedEntry<E> previous, FoldedEntry<E> previousFold) {
      this.item = item;
      this.index = index;

      this.previous = previous;
      if (previous != null)
         previous.next = this;

      this.previousFold = previousFold;
      if (previousFold != null)
         previousFold.nextFold = this;
   }

   E item;
   int index;
   FoldedEntry<E> next;
   FoldedEntry<E> nextFold;
   FoldedEntry<E> previous;
   FoldedEntry<E> previousFold;

   public E item() {
      return item;
   }

   public FoldedEntry<E> next() {
      return next;
   }

   @Override
   public char getFold() {
      return item.getFold();
   }

}
