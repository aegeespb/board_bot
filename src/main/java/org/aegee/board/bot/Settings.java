package org.aegee.board.bot;

import com.google.inject.Singleton;
import org.telegram.abilitybots.api.db.DBContext;

import java.util.Set;

@Singleton
public class Settings {
  private volatile Integer myCreatorId;
  private Set<Long> myDbStartedUserIds;
  private Set<String> myDbSubscribeEmails;

  public int getCreatorId() {
    if (myCreatorId != null) {
      return myCreatorId;
    }

    synchronized (this) {
      if (myCreatorId == null) {
        myCreatorId = getCreatorIdFromEnv();
      }
      return myCreatorId;
    }
  }

  private static int getCreatorIdFromEnv() {
    final String creatorId = System.getenv(Constants.BOT_CREATOR_ID_ENV_NAME);
    if (creatorId == null) {
      throw new IllegalStateException("You should specify bot superuser as environment variable " + Constants.BOT_CREATOR_ID_ENV_NAME);
    }
    return Integer.parseInt(creatorId);
  }

  void init(DBContext db) {
    myDbStartedUserIds = db.getSet("user.ids");
    myDbSubscribeEmails = db.getSet("subscribe.emails");
  }
}
