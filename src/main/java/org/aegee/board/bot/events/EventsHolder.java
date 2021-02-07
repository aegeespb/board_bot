package org.aegee.board.bot.events;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.aegee.board.bot.Settings;

import java.util.*;

@Singleton
public class EventsHolder {

    private final Map<Integer, Event> myEvents = new HashMap<>();
    private final Settings mySettings;

    @Inject
    EventsHolder(Settings settings) {
        mySettings = settings;

        myEvents.put(new Event1().getId(), new Event1());
        myEvents.put(new Event2().getId(), new Event2());
        myEvents.put(new Event3().getId(), new Event3());
        myEvents.put(new Event4().getId(), new Event4());
        myEvents.put(new Event5().getId(), new Event5());

        DatabaseReference eventsRef = mySettings.getDatabaseReference("events");
        eventsRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, HashMap<String, Object>> eventsHolder = (Map<String, HashMap<String, Object>>) dataSnapshot.getValue();
                        for (Map.Entry<String, HashMap<String, Object>> eventId2dbEventMap : eventsHolder.entrySet()) {
                            HashMap<String, Object> dbEventAsMap = eventId2dbEventMap.getValue();
                            List<Long> subscribers = (List<Long>) dbEventAsMap.getOrDefault("subscribers", Collections.emptyList());
                            Event eventToUpdate = getById(Integer.parseInt(eventId2dbEventMap.getKey().substring(1)));
                            for (Long subscriber : subscribers) {
                                eventToUpdate.addSubscriber(subscriber);
                            }
                        }

                        System.out.println("Events subscribers imported");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Ann error occurred " + databaseError.getMessage());
                    }
                });
    }

    public void updateSubscribers(Event event) {
        DatabaseReference eventsRef = mySettings.getDatabaseReference("events");
        DatabaseReference eventRef = eventsRef.child("e" + event.getId());
        DbEvent dbEvent = new DbEvent();
        dbEvent.setSubscribers(event.getSubscribers());
        mySettings.update(eventRef, "events" + event.getId(), dbEvent);
    }

    public Collection<Event> getUpcomingEvents() {
        List<Event> result = new ArrayList<>();
        for (Event event : myEvents.values()) {
            if (event.getStartDate().before(Calendar.getInstance().getTime())) continue;
            result.add(event);
        }

        return result;
    }

    public Event getById(Integer id) {
        return myEvents.get(id);
    }

    private static class DbEvent {
        private List<Long> subscribers = new ArrayList<>();

        public List<Long> getSubscribers() {
            return subscribers;
        }

        public void setSubscribers(List<Long> chatsToRemind) {
            this.subscribers = chatsToRemind;
        }
    }
}

