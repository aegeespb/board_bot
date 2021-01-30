package org.aegee.board.bot.events;

import java.util.GregorianCalendar;

public class Event5 extends Event {
    public Event5() {
        super(5,
                "Social Meeting",
                null,
                "https://www.google.com/", new GregorianCalendar(2021,  2 - 1, 27).getTime(),
                null);
    }
}
