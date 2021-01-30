package org.aegee.board.bot;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class UserHolder {

    private final Settings mySettings;
    public volatile boolean isReady = false;
    private final Map<String, UserInfo> myUserInfoMap = new HashMap<>();

    @Inject
    UserHolder(Settings settings) {
        mySettings = settings;
        DatabaseReference dbReference = mySettings.getDatabaseReference("user_holder");
        dbReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, HashMap<String, Object>> userHolder = (Map<String, HashMap<String, Object>>) dataSnapshot.getValue();
                        for (Map.Entry<String, HashMap<String, Object>> userId2userMap : userHolder.entrySet()) {
                            HashMap<String, Object> userInfoAsMap = userId2userMap.getValue();
                            UserInfo userInfo = UserInfo.fromMap(userInfoAsMap);

                            myUserInfoMap.put(userId2userMap.getKey(), userInfo);
                        }

                        isReady = true;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Ann error occurred " + databaseError.getMessage());
                    }
                });
    }

    public void addUser(Long chatId, User user) {
        addUser(chatId, user, user.getLanguageCode().equals("ru") ? Language.RUSSIAN : Language.ENGLISH);
    }

    public void addUser(Long chatId, User user, Language language) {
        UserInfo userInfo = new UserInfo();
        userInfo.setChatId(chatId);
        userInfo.setLanguage(language);
        userInfo.setPosition(AegeePosition.CURIOUS);
        userInfo.setFirstName(user.getFirstName());
        userInfo.setLastName(user.getLastName());

        Set<Subscriptions> subscriptions = new HashSet<>();
        subscriptions.add(Subscriptions.JOIN_LEAVE);
        subscriptions.add(Subscriptions.NEW_POST);
        userInfo.setSubscriptions(subscriptions);

        addUser(userInfo);
    }

    public void addUser(UserInfo userInfo) {
        myUserInfoMap.put(userInfo.getChatId().toString(), userInfo);
        mySettings.update("user_holder", myUserInfoMap);
    }

    public UserInfo getUser(Long chatId) {
        return myUserInfoMap.get(chatId.toString());
    }

    public Collection<UserInfo> getBoardMembers() {
        List<UserInfo> boardMembers = new ArrayList<>();
        for (Map.Entry<String, UserInfo> chatId2UserInfo : myUserInfoMap.entrySet()) {
            UserInfo userInfo = chatId2UserInfo.getValue();
            if (userInfo.getPosition() == AegeePosition.BOARD) {
                boardMembers.add(userInfo);
            }
        }
        return boardMembers;
    }

    public Collection<UserInfo> getAllUsers() {
        return myUserInfoMap.values();
    }
}