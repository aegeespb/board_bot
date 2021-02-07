package org.aegee.board.bot.events;

import java.util.*;

public abstract class Event {
    private final int myId;
    private final String myShortDescription;
    private final String myLongDescription;
    private final String myRegistrationLink;
    private final Date myStartDate;
    private final Date myFinishDate;
    private final Set<Long> subscribers = new HashSet<>();

    public Event(int id,
                 String shortDescription,
                 String longDescription,
                 String registrationLink,
                 Date startDate,
                 Date finishDate) {
        myId = id;
        myShortDescription = shortDescription;
        myRegistrationLink = registrationLink;
        if (longDescription == null) {
            myLongDescription = "⭐️ Описание события '" + myShortDescription + "' появится позже ⭐️";
        } else {
            myLongDescription = longDescription;
        }
        myStartDate = startDate;
        myFinishDate = finishDate;

    }

    public Date getFinishDate() {
        return myFinishDate;
    }

    public Date getStartDate() {
        return myStartDate;
    }

    public String getRegistrationLink() {
        return myRegistrationLink;
    }

    public String getLongDescription() {
        return myLongDescription;
    }

    public String getShortDescription() {
        return myShortDescription;
    }

    public int getId() {
        return myId;
    }

    public List<Long> getSubscribers() {
        return new ArrayList<>(subscribers);
    }

    public void addSubscriber(Long chatId) {
        subscribers.add(chatId);
    }

    public void removeSubscriber(Long chatId) {
        subscribers.remove(chatId);
    }
}
