package kid.data.pattern;

import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

// TODO documentation: class
// TODO code: perfect

public class FoldedLinkedList<E extends Foldable> extends AbstractSequentialList<E> implements List<E>, Queue<E>,
      Cloneable, Serializable {

   private static final long serialVersionUID = -239556682937205295L;

   private LinkedList<FoldedEntry<E>> list;
   private HashMap<Character, LinkedList<FoldedEntry<E>>> foldedList;

   public FoldedLinkedList() {
      list = new LinkedList<FoldedEntry<E>>();
      foldedList = new HashMap<Character, LinkedList<FoldedEntry<E>>>();
   }

   @Override
   public boolean add(final E o) {
      char fold = o.getFold();
      FoldedEntry<E> prev = null;
      FoldedEntry<E> prevFold = null;
      if (list.size() > 0) {
         prev = list.getLast();
      }
      if (!foldedList.containsKey(fold)) {
         foldedList.put(fold, new LinkedList<FoldedEntry<E>>());
         // System.out.println("Adding first element in fold " + fold);
      } else {
         prevFold = foldedList.get(fold).getLast();
      }
      FoldedEntry<E> entry = new FoldedEntry<E>(o, list.size(), prev, prevFold);
      return list.add(entry) && foldedList.get(fold).add(entry);
   }

   @Override
   public boolean addAll(final Collection<? extends E> c) {
      boolean answer = true;
      for (E e : c)
         answer = answer && add(e);
      return answer;
   }

   @Override
   public void clear() {
      list.clear();
      for (LinkedList<FoldedEntry<E>> l : foldedList.values())
         l.clear();
   }

   @Override
   public boolean contains(final Object o) {
      return list.contains(o);
   }

   public E element() {
      return list.element().item;
   }

   public E element(final char fold) {
      element();
      return foldedList.get(fold).element().item;
   }

   @Override
   public E get(final int index) {
      return list.get(index).item;
   }

   public LinkedList<FoldedEntry<E>> getFold(final char fold) {
      return foldedList.get(fold);
   }

   public HashMap<Character, LinkedList<FoldedEntry<E>>> getFolds() {
      return foldedList;
   }

   public E get(final int index, final char fold) {
      return foldedList.get(fold).get(index).item;
   }

   public E getFirst() {
      return list.getFirst().item;
   }

   public E getFirst(final char fold) {
      return foldedList.get(fold).getFirst().item;
   }

   public E getLast() {
      return list.getLast().item;
   }

   public E getLast(final char fold) {
      return foldedList.get(fold).getLast().item;
   }


   @Override
   public int size() {
      return list.size();
   }

   public int size(final char fold) {
      return foldedList.get(fold).size();
   }

   // TODO code: method - offer(E)
   public boolean offer(final E o) {
      return false;
   }

   // TODO code: method - offer(E, int)
   public boolean offer(final E o, final char fold) {
      return false;
   }

   public E peek() {
      return list.peek().item;
   }

   public E peek(final char fold) {
      peek();
      return foldedList.get(fold).peek().item;
   }

   public E poll() {
      return foldedList.get(list.poll().item.getFold()).poll().item;
   }

   public E poll(final char fold) {
      remove(foldedList.get(fold).poll().item);
      return foldedList.get(fold).poll().item;
   }

   public E remove() {
      return list.remove().item;
   }

   @Override
   public E remove(final int index) {
      return null;
   }

   @Override
   public ListIterator<E> listIterator(int index) {
      return new ListItr(index);
   }



   public void print() {
      String str = "";
      for (FoldedEntry<E> i : list) {
         str += (int) i.item.getFold() + " - ";
      }
      str += "\n\n";

      for (char fold : foldedList.keySet()) {
         LinkedList<FoldedEntry<E>> list = foldedList.get(fold);
         str += (int) fold + ": ";
         for (FoldedEntry<E> i : list) {
            str += i.index + " - ";
         }
         str += "\n";
      }
      str += "\n";
      System.out.print(str);
   }



   private class ListItr implements ListIterator<E> {
      private ListIterator<FoldedEntry<E>> iter;

      ListItr(int index) {
         iter = list.listIterator(index);
      }

      public boolean hasNext() {
         return iter.hasNext();
      }

      public E next() {
         return iter.next().item;
      }

      public boolean hasPrevious() {
         return iter.hasPrevious();
      }

      public E previous() {
         return iter.previous().item;
      }

      public int nextIndex() {
         return iter.nextIndex();
      }

      public int previousIndex() {
         return iter.previousIndex();
      }

      public void remove() {
         iter.remove();
      }

      public void set(E e) {
         // iter.set(e);
      }

      public void add(E e) {
         // iter.add(e);
      }
   }

}
