package bnorm.events;

import robocode.Event;

/**
 * The {@link EventHandler} interface provides a simple way to require certain
 * methods pertaining to Robocode events.
 * 
 * @author Brian Norman
 * @version 1.0
 */
public interface EventHandler {

   /**
    * Handles the event that was specified. If the event handler does not need
    * this event than it is ignored.
    * 
    * @param event
    *           the event for the handler.
    */
   public void inEvent(Event event);

   /**
    * Handles the events that are specified. If the event handler does not need
    * one of the events specified than it is ignored. The easiest way to
    * implement this method is to call {@link #inEvent(Event)} for every event
    * in the collection.
    *
    * @param events
    *           the collection of events for the handler.
    */
   public void inEvents(Iterable<? extends Event> events);

}
