package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// reconfigured from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

//Represents a log of Hockey Hero events using Singleton design pattern
public class EventLog implements Iterable<Event> {
    /** the only EventLog in the system (Singleton Design Pattern) */
    private static EventLog theLog;
    private Collection<Event> events;

    //EFFECTS: Singleton constructor for EventLog. intializes events
    private EventLog() {
        events = new ArrayList<Event>();
    }

    //MODIFIES: this
    //EFFECTS: returns EventLog . if EventLog doesn't already exist, create it then return it.
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    //MODIFIES: this
    //EFFECTS: adds event to events
    public void logEvent(Event e) {
        events.add(e);
    }

    //MODIFIES: this
    //EFFECTS: clears event log and logs the event as "Event log cleared."
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    //EFFECTS: returns events iterator
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
