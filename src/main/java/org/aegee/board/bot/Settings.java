package org.aegee.board.bot;

import com.google.inject.Singleton;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class Settings {
  private final Set<Long> myListeners = new HashSet<>();
  private Set<String> myDbSubscribeEmails;

  public void addListener(Long listener) {
    myListeners.add(listener);
  }

  public Set<Long> getAllListeners() {
    return myListeners;
  }
//
//  void init(DBContext db) {
//    myDbStartedUserIds = db.getSet("user.ids");
//    myDbSubscribeEmails = db.getSet("subscribe.emails");
//  }
}
