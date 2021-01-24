package org.aegee.board.bot;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class UserHolder {

    private final Settings mySettings;
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
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Ann error occurred " + databaseError.getMessage());
                    }
                });
    }

    public void addUser(UserInfo userInfo) {
        myUserInfoMap.put(userInfo.getChatId().toString(), userInfo);
        mySettings.update("user_holder", myUserInfoMap);
    }

    public void getUser(Long chatId) {
        myUserInfoMap.get(chatId.toString());
    }

    public Collection<UserInfo> getAllUsers() {
        return myUserInfoMap.values();
    }
}