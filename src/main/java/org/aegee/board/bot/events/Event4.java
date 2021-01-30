package org.aegee.board.bot.events;

import java.util.GregorianCalendar;

public class Event4 extends Event {
    public Event4() {
        super(4,
                "Совместный выезд в Псков с Aegee-Moskva",
                null,
                "https://www.google.com/",
                new GregorianCalendar(2021,  2 - 1, 21).getTime(),
                new GregorianCalendar(2021,  2 - 1, 23).getTime());
    }
}
