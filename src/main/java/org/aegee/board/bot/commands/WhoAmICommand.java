package org.aegee.board.bot.commands;

import com.google.inject.Inject;
import org.aegee.board.bot.SenderProxy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

public class WhoAmICommand {

  private final SenderProxy mySender;

  @Inject
  public WhoAmICommand(SenderProxy sender) {
    mySender = sender;
  }

  public void execute(User user, Long chatId) {
    SendMessage msg = new SendMessage();
    msg.setText(user.getFirstName() + " " + user.getLastName() +
                    " (id=`" + user.getId() + "`; " +
                    "username=`" + user.getUserName() + "`)");
    msg.setChatId(chatId.toString());
    msg.enableMarkdown(true);
    mySender.execute(msg);
  }
}
