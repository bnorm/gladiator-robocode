package dev.data;

import robocode.Event;

public interface EventHandler {

   public void inEvent(Event e);

   public void inEvents(Iterable<Event> events);

}
