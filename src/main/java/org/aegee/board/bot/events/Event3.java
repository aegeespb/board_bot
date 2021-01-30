package org.aegee.board.bot.events;

import java.util.GregorianCalendar;

public class Event3 extends Event {
    public Event3() {
        super(3,
                "Speaking Club: Writing",
                null,
                "https://www.google.com/",
                new GregorianCalendar(2021,  2 - 1, 18).getTime(),
                null);
    }
}
