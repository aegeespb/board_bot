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

//  @SuppressWarnings("StringBufferReplaceableByString")
//  public void execute(Long chatId) {
//    StringBuilder helpMessageBuilder = new StringBuilder("*Available commands:* \n");
//    helpMessageBuilder.append("`/start` - \n");
//    helpMessageBuilder.append("`/score` - ");
//
//    mySender.execute(new SendMessage()
//                             .setText(helpMessageBuilder.toString())
//                             .enableMarkdown(true)
//                             .setChatId(chatId));
//  }
}
