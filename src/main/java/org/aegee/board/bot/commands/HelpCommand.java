package org.aegee.board.bot.commands;

import com.google.inject.Inject;
import org.aegee.board.bot.SenderProxy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class HelpCommand {
  private final SenderProxy mySender;

  @Inject
  public HelpCommand(SenderProxy sender) {
    mySender = sender;
  }

  public void execute(Long chatId) {
    SendMessage msg = new SendMessage();
    msg.setText("Available commands: \n" + "/whoAmI - help to get userId\n");
    msg.setChatId(chatId.toString());
    mySender.execute(msg);
  }
}
