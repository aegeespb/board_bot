package org.aegee.board.bot;

import com.google.inject.Singleton;
//import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Singleton
public class SenderProxy {
//  private MessageSender mySender;

//  void registerSender(MessageSender sender) {
//    mySender = sender;
//  }

  public Message sendPhoto(SendPhoto photo) {
//    try {
//      return mySender.sendPhoto(photo);
//    } catch (TelegramApiException e) {
//      e.printStackTrace();
//    }

    return null;
  }

  public void execute(SendMessage setChatId) {
//    try {
//      mySender.execute(setChatId);
//    } catch (TelegramApiException e) {
//      e.printStackTrace();
//    }
  }

  public void execute(EditMessageReplyMarkup editMarkup) {
//    try {
//      mySender.execute(editMarkup);
//    } catch (TelegramApiException e) {
//      e.printStackTrace();
//    }
  }
}
