package org.aegee.board.bot;

import java.util.*;

public class UserInfo {
    private String firstName;
    private String lastName;
    private Long chatId;
    private Language language;
    private AegeePosition position;
    private Collection<Subscriptions> subscriptions;

    public static UserInfo fromMap(HashMap<String, Object> userInfoAsMap) {
        UserInfo userInfo = new UserInfo();
        userInfo.setChatId((Long) userInfoAsMap.get("chatId"));
        userInfo.setFirstName((String) userInfoAsMap.get("firstName"));
        userInfo.setLastName((String) userInfoAsMap.get("lastName"));
        userInfo.setLanguage(Language.valueOf((String) userInfoAsMap.get("language")));
        userInfo.setPosition(AegeePosition.valueOf((String) userInfoAsMap.get("position")));
        @SuppressWarnings("unchecked") List<String> subscriptionAsStr = (List<String>) userInfoAsMap.get("subscriptions");
        Set<Subscriptions> subscriptions = new HashSet<>();
        for (String sub : subscriptionAsStr) {
            subscriptions.add(Subscriptions.valueOf(sub));
        }
        userInfo.setSubscriptions(subscriptions);
        return userInfo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public AegeePosition getPosition() {
        return position;
    }

    public void setPosition(AegeePosition position) {
        this.position = position;
    }

    public List<Subscriptions> getSubscriptions() {
        return new ArrayList<>(subscriptions);
    }

    public void setSubscriptions(Collection<Subscriptions> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "UserInfo [" +
                firstName + " " + lastName + " :: " + chatId + " :: position: " + position +
                " :: " + " language: " + language + "]";
    }
}

