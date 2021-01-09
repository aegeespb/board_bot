package org.aegee.board.bot.examples;

import com.google.inject.Inject;
import org.aegee.board.bot.SenderProxy;
import org.aegee.board.bot.Settings;

@Deprecated()
public class WorkWithPicturesExample {

  private final SenderProxy mySender;
  private final Settings mySettings;

  @Inject
  public WorkWithPicturesExample(Settings settings,
                                 SenderProxy sender) {
    mySettings = settings;
    mySender = sender;
  }
//
//  public void execute(Long chatId) {
//    if (isPictureCached()) {
//      sendPhotoFromCache(chatId, question, state.getPassedNumber());
//    } else {
//      Message message = sendPhotoFromDisk(chatId, question, state.getPassedNumber());
//      addPhotoToCacheIfPossible(question, message);
//    }
//  }
//
//  private Message sendPhotoFromDisk(Long chatId, Question question, int questionNumber) {
//    SendPhoto photo = new SendPhoto().setPhoto("jb quiz", question.myQuestionPicture)
//            .setChatId(chatId)
//            .setCaption(questionNumber +". " + question.myQuestionText)
//            .setReplyMarkup(KeyboardsFactory.getKeyboardForExample(question));
//    return mySender.sendPhoto(photo);
//  }
//
//  private void addPhotoToCacheIfPossible(Question question, Message message) {
//    if (message.getPhoto().size() > 0) {
//      question.questionPictureFileId = message.getPhoto().get(0).getFileId();
//    }
//  }
//
//  private void sendPhotoFromCache(Long chatId, Question question, int questionNumber) {
//    SendPhoto photo = new SendPhoto().setPhoto(question.questionPictureFileId)
//            .setChatId(chatId)
//            .setCaption(questionNumber +". " + question.myQuestionText)
//            .setReplyMarkup(KeyboardsFactory.getKeyboardForExample(question));
//    mySender.sendPhoto(photo);
//  }

//  private boolean isPictureCached(Question question) {
//    return question.questionPictureFileId != null;
//  }
}
