package org.aegee.board.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.aegee.board.bot.Constants.BACK_TO_ABOUT_CALLBACK;
import static org.aegee.board.bot.commands.AboutCommand.*;

public class KeyboardsFactory {
    public static ReplyKeyboard getLanguageKeyboard() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> buttons = new ArrayList<>();

        InlineKeyboardButton enButton = new InlineKeyboardButton();
        enButton.setText("EN");
        enButton.setCallbackData(Language.ENGLISH.toString());

        InlineKeyboardButton ruButton = new InlineKeyboardButton();
        ruButton.setText("RU");
        ruButton.setCallbackData(Language.RUSSIAN.toString());

        buttons.add(enButton);
        buttons.add(ruButton);
        rowsInline.add(buttons);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static ReplyKeyboard getAboutKeyboard() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        InlineKeyboardButton whatToDoButton = new InlineKeyboardButton();
        whatToDoButton.setText("Чем занимаются в AEGEE?");
        whatToDoButton.setCallbackData(AboutChapters.WHAT_TO_DO.toString());

        InlineKeyboardButton whereIsAegee = new InlineKeyboardButton();
        whereIsAegee.setText("Где можно найти AEGEE?");
        whereIsAegee.setCallbackData(AboutChapters.WHERE_IS_AEGEE.toString());

        InlineKeyboardButton managementInAegee = new InlineKeyboardButton();
        managementInAegee.setText("Как управляется AEGEE?");
        managementInAegee.setCallbackData(AboutChapters.MANAGEMENT_IN_AEGEE.toString());

        InlineKeyboardButton howToJoin = new InlineKeyboardButton();
        howToJoin.setText("Как присоединиться к AEGEE-Sankt-Peterburg?");
        howToJoin.setCallbackData(AboutChapters.HOW_TO_JOIN.toString());

        InlineKeyboardButton eventInAegee = new InlineKeyboardButton();
        eventInAegee.setText("Какие мероприятия проводятся в AEGEE?");
        eventInAegee.setCallbackData(AboutChapters.EVENTS_IN_AEGEE.toString());

        rowsInline.add(Collections.singletonList(whatToDoButton));
        rowsInline.add(Collections.singletonList(whereIsAegee));
        rowsInline.add(Collections.singletonList(managementInAegee));
        rowsInline.add(Collections.singletonList(howToJoin));
        rowsInline.add(Collections.singletonList(eventInAegee));
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static ReplyKeyboard getBackToAbout() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        InlineKeyboardButton backToAbout = new InlineKeyboardButton();
        backToAbout.setText("<< Вернуться к оглавлению");
        backToAbout.setCallbackData(BACK_TO_ABOUT_CALLBACK);

        rowsInline.add(Collections.singletonList(backToAbout));
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }
}


