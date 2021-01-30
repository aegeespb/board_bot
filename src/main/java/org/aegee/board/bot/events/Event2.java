package org.aegee.board.bot.events;

import java.util.GregorianCalendar;

public class Event2 extends Event {
    public Event2() {
        super(2,
                "Праздновануем 14 феврал",
                null,
                "https://www.google.com/",
                new GregorianCalendar(2021,  2 - 1, 14).getTime(),
                null);
    }
}
