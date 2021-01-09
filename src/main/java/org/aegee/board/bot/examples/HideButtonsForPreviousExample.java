package org.aegee.board.bot.examples;

import com.google.inject.Inject;
import org.aegee.board.bot.SenderProxy;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;

@Deprecated
public class HideButtonsForPreviousExample {
  private final SenderProxy mySender;

  @Inject
  public HideButtonsForPreviousExample(SenderProxy sender) {
    mySender = sender;
  }


  private void hideButtonsForPrevious(Long chatId, Message message) {
    EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup()
            .setChatId(chatId)
            .setMessageId(message.getMessageId())
            .setReplyMarkup(null);
    mySender.execute(editMarkup);
  }
}
