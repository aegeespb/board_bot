package org.aegee.board.bot;

import com.google.inject.Singleton;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Singleton
public class SenderProxy {
  private DefaultAbsSender mySender;

  public void execute(SendMessage setChatId) {
    try {
      mySender.execute(setChatId);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  public void sendMessage(String chatId, String s) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(s);
    execute(sendMessage);
  }

  public void execute(EditMessageReplyMarkup editMarkup) {
    try {
      mySender.execute(editMarkup);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  public void registerSender(DefaultAbsSender bot) {
    mySender = bot;
  }
}
