package org.aegee.board.bot;

import org.aegee.board.bot.events.Event;
import org.aegee.board.bot.events.EventsHolder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimerTask;

public class SubscribersSupporter extends TimerTask {
    private final EventsHolder myEventsHolder;
    private final SenderProxy mySenderProxy;

    @Inject
    SubscribersSupporter(EventsHolder eventsHolder,
                         SenderProxy senderProxy) {
        myEventsHolder = eventsHolder;
        mySenderProxy = senderProxy;
    }

    @Override
    public void run() {
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime().toString() + ":: run subscribers supporter");
        c.add(Calendar.HOUR, 4);
        Date hours4 = c.getTime();
        c.add(Calendar.HOUR, 1);
        Date hours5 = c.getTime();

        Collection<Event> events = myEventsHolder.getUpcomingEvents();
        for (Event event : events) {
            if (event.getStartDate().before(hours5) && event.getStartDate().after(hours4)) {
                notify(event);
            }
        }
    }

    private void notify(Event event) {
        for (Long subscriber : event.getSubscribers()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(subscriber.toString());
            sendMessage.setText("Завтра состоиться мероприятие '" + event.getShortDescription() + "' /event_" + event.getId());
            mySenderProxy.execute(sendMessage);
        }
    }
}
