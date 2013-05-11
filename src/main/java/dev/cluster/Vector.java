package dev.cluster;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import dev.robots.RobotData;

public class Vector<E> {

   private E                      data_;
   private RobotData              view_;
   private RobotData              reference_;
   private HashMap<Scale, Double> components_;

   public Vector(Collection<Scale> scales, RobotData view, RobotData reference, E data) {
      data_ = data;
      view_ = view;
      reference_ = reference;
      components_ = new HashMap<Scale, Double>(scales.size());
      for (Scale s : scales)
         components_.put(s, s.value(view, reference));
   }

   protected Vector() {
   }

   public E getData() {
      return data_;
   }

   public RobotData getView() {
      return view_;
   }

   public RobotData getReference() {
      return reference_;
   }

   protected double getComponent(Scale s) {
      return components_.get(s);
   }

   @Override
   public String toString() {
      String str = "(";
      Iterator<Double> iter = components_.values().iterator();
      while (iter.hasNext()) {
         str += iter.next();
         if (iter.hasNext())
            str += ", ";
      }
      return str + ")";
   }

}
