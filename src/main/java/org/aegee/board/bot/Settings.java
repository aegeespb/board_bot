package org.aegee.board.bot;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.inject.Singleton;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

@Singleton
public class Settings {
    private final FirebaseDatabase firebaseDatabase;
    private final String VK_GROUP_LISTENERS_KEY = "vk_group_listeners";
    @Deprecated
    private final Set<Long> myListeners = new HashSet<>();

    public Settings() throws IOException {
        String dbUrl = getFirebaseDbUrl();

        InputStream stream = new ByteArrayInputStream(getFirebaseDbServiceAccountKeyContent().getBytes(StandardCharsets.UTF_8));
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(stream))
                .setDatabaseUrl(dbUrl)
                .build();

        FirebaseApp.initializeApp(options);

        firebaseDatabase = FirebaseDatabase.getInstance(dbUrl);
        DatabaseReference vkGroupListenersRef = firebaseDatabase.getReference(VK_GROUP_LISTENERS_KEY);
        vkGroupListenersRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<Long> ids = (ArrayList<Long>) dataSnapshot.getValue();
                        myListeners.addAll(ids);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Ann error occurred " + databaseError.getMessage());
                    }
                });
    }

    public DatabaseReference getDatabaseReference(String key) {
       return firebaseDatabase.getReference(key);
    }

    public void update(String key, Object value) {
        try {
            DatabaseReference ref = firebaseDatabase.getReference(key);
            final CountDownLatch latch = new CountDownLatch(1);
            ref.setValue(value, (databaseError, databaseReference) -> {
                if (databaseError != null) {
                    System.out.println(key + " data could not be saved " + databaseError.getMessage());
                } else {
                    System.out.println(key + " data saved successfully.");
                }
                latch.countDown();
            });
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public Set<Long> getAllListeners() {
        return myListeners;
    }

    private static String getFirebaseDbUrl() {
        final String firebaseDbUrl = System.getenv(Constants.FIREBASE_DB_URL_ENV_NAME);
        if (firebaseDbUrl == null) {
            throw new IllegalStateException("You should specify firebase db url as environment variable " + Constants.FIREBASE_DB_URL_ENV_NAME);
        }

        return firebaseDbUrl;
    }

    private static String getFirebaseDbServiceAccountKeyContent() {
        final String firebaseDbAccountInfo = System.getenv(Constants.FIREBASE_DB_SERVICE_ACCOUNT_KEY_CONTENT);
        if (firebaseDbAccountInfo == null) {
            throw new IllegalStateException("You should specify firebase db service account key content as environment variable " + Constants.FIREBASE_DB_SERVICE_ACCOUNT_KEY_CONTENT);
        }

        return firebaseDbAccountInfo;
    }
}
