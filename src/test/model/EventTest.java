package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

// reconfigured from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event event;
    private Date date;

    // NOTE: these tests might fail if time at which line (2) below is executed
    // is different from time that line (1) is executed. Lines (1) and (2) must
    // run in same millisecond for this test to make sense and pass.

    @BeforeEach
    void runBefore() {
        event = new Event("Player added to user team's roster."); // (1)
        date = Calendar.getInstance().getTime(); // (2)
    }

    @Test
    void testEvent() {
        assertEquals("Player added to user team's roster.", event.getDescription());
        assertEquals(date, event.getDate());
    }

    @Test
    void testToString() {
        assertEquals(date.toString() + "\n" + "Player added to user team's roster.", event.toString());
    }

    @Test
    void testEquals() {
        Event e2 = null;
        assertFalse(event.equals(e2));

        String s = "Player added to user team's roster.";
        assertFalse(event.equals(s));
    }

    @Test
    void testHashCode() {
        assertEquals(13 * date.hashCode() + "Player added to user team's roster.".hashCode(), event.hashCode());
    }
}
