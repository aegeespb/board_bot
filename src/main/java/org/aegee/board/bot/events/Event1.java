package org.aegee.board.bot.events;

import java.util.GregorianCalendar;

public class Event1 extends Event {
    public Event1() {
        super(1,
                "Обмен с Тарту",
                null,
                "https://www.google.com/",
                new GregorianCalendar(2021,  2 - 1, 6).getTime(),
                null);
    }
}
