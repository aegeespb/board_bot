package org.aegee.board.bot.events;

import java.util.Date;

public abstract class Event {
    private final int myId;
    private final String myShortDescription;
    private final String myLongDescription;
    private final String myRegistrationLink;
    private final Date myStartDate;
    private final Date myFinishDate;

    public Event(int id, String shortDescription, String longDescription, String registrationLink, Date startDate, Date finishDate) {
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
}
