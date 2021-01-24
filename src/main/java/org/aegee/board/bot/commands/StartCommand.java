package org.aegee.board.bot.commands;

import com.google.inject.Inject;
import org.aegee.board.bot.*;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashSet;
import java.util.Set;

public class StartCommand {
  private final SenderProxy mySenderProxy;
  private final UserHolder myUserHolder;

  @Inject
  public StartCommand(SenderProxy sender,
                      UserHolder userHolder) {
    mySenderProxy = sender;
    myUserHolder = userHolder;
  }

  public void execute(Long chatId, User user) {
    mySenderProxy.sendMessage(chatId.toString(), "Since the moment you will receive notifications about group events :yey:");

    UserInfo userInfo = new UserInfo();
    userInfo.setChatId(chatId);
    userInfo.setLanguage(Language.ENGLISH);
    userInfo.setPosition(AegeePosition.BOARD);
    userInfo.setFirstName(user.getFirstName());
    userInfo.setLastName(user.getLastName());

    Set<Subscriptions> subscriptions = new HashSet<>();
    subscriptions.add(Subscriptions.JOIN_LEAVE);
    subscriptions.add(Subscriptions.NEW_POST);
    userInfo.setSubscriptions(subscriptions);
    try {
      myUserHolder.addUser(userInfo);
    } catch (Exception err) {
      err.printStackTrace();
    }
  }
}
