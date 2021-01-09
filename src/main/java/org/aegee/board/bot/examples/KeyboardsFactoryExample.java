package org.aegee.board.bot.examples;

import org.aegee.board.bot.Constants;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Deprecated
public class KeyboardsFactoryExample {
  public static ReplyKeyboard getKeyboardForExample() {
    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
    List<InlineKeyboardButton> rowInline = Collections.singletonList(
            new InlineKeyboardButton()
                    .setText("example of text")
                    .setCallbackData("callback text"));
    rowsInline.add(rowInline);
    inlineKeyboard.setKeyboard(rowsInline);
    return inlineKeyboard;
  }

  public static ReplyKeyboard getKeyboardStartAgain() {
    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
    inlineKeyboard.setKeyboard(Collections.singletonList(Collections.singletonList(
            new InlineKeyboardButton()
                    .setText("example")
                    .setCallbackData("Callback example"))));
    return inlineKeyboard;
  }

  public static ReplyKeyboard getKeyboardFollowUs() {
    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
    inlineKeyboard.setKeyboard(Collections.singletonList(Collections.singletonList(
            new InlineKeyboardButton()
                    .setText("subscribe example")
                    .setCallbackData(Constants.SUBSCRIBE_ACTION))));
    return inlineKeyboard;
  }
}
