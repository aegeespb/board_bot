package org.aegee.board.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardsFactory {
    public static ReplyKeyboard getLanguageKeyboard() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> buttons = new ArrayList<>();

        InlineKeyboardButton enButton = new InlineKeyboardButton();
        enButton.setText("EN");
        enButton.setCallbackData("ENGLISH");

        InlineKeyboardButton ruButton = new InlineKeyboardButton();
        ruButton.setText("RU");
        ruButton.setCallbackData("RUSSIAN");

        buttons.add(enButton);
        buttons.add(ruButton);
        rowsInline.add(buttons);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }
}
