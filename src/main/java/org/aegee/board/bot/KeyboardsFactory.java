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
        whatToDoButton.setText("Зачем вступать в AEGEE?");
        whatToDoButton.setCallbackData(AboutChapters.WHAT_TO_DO.toString());

        InlineKeyboardButton whereIsAegee = new InlineKeyboardButton();
        whereIsAegee.setText("Миссия AEGEE");
        whereIsAegee.setCallbackData(AboutChapters.AEGEE_MISSION.toString());

        InlineKeyboardButton managementInAegee = new InlineKeyboardButton();
        managementInAegee.setText("Как управляется AEGEE?");
        managementInAegee.setCallbackData(AboutChapters.MANAGEMENT_IN_AEGEE.toString());

        InlineKeyboardButton howToJoin = new InlineKeyboardButton();
        howToJoin.setText("Как присоединиться к AEGEE-Sankt-Peterburg?");
        howToJoin.setCallbackData(AboutChapters.HOW_TO_JOIN.toString());

        InlineKeyboardButton eventInAegee = new InlineKeyboardButton();
        eventInAegee.setText("Какие мероприятия проводятся в AEGEE?");
        eventInAegee.setCallbackData(AboutChapters.EVENTS_IN_AEGEE.toString());

        InlineKeyboardButton aboutSummerUniversity = new InlineKeyboardButton();
        aboutSummerUniversity.setText("Что такое Summer University?");
        aboutSummerUniversity.setCallbackData(AboutChapters.ABOUT_SUMMER_UNIVERSITY.toString());

        rowsInline.add(Collections.singletonList(whatToDoButton));
        rowsInline.add(Collections.singletonList(whereIsAegee));
        rowsInline.add(Collections.singletonList(managementInAegee));
        rowsInline.add(Collections.singletonList(howToJoin));
        rowsInline.add(Collections.singletonList(eventInAegee));
        rowsInline.add(Collections.singletonList(aboutSummerUniversity));
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

    public static ReplyKeyboard getEuropeanManagementAndBack() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        InlineKeyboardButton europeanLevel = new InlineKeyboardButton();
        europeanLevel.setText("Европейский уровень");
        europeanLevel.setCallbackData(AboutChapters.EUROPEAN_MANAGEMENT_LEVEL.toString());

        InlineKeyboardButton backToAbout = new InlineKeyboardButton();
        backToAbout.setText("<< Вернуться к оглавлению");
        backToAbout.setCallbackData(BACK_TO_ABOUT_CALLBACK);

        rowsInline.add(Collections.singletonList(europeanLevel));
        rowsInline.add(Collections.singletonList(backToAbout));
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static ReplyKeyboard getTypesCostSUAndBack() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        InlineKeyboardButton typesSummerUniversity = new InlineKeyboardButton();
        typesSummerUniversity.setText("Какие бывают типы SU?");
        typesSummerUniversity.setCallbackData(AboutChapters.TYPES_SUMMER_UNIVERSITY.toString());

        InlineKeyboardButton costSummerUniversity = new InlineKeyboardButton();
        costSummerUniversity.setText("Сколько это стоит?");
        costSummerUniversity.setCallbackData(AboutChapters.COST_SUMMER_UNIVERSITY.toString());

        InlineKeyboardButton backToAbout = new InlineKeyboardButton();
        backToAbout.setText("<< Вернуться к оглавлению");
        backToAbout.setCallbackData(BACK_TO_ABOUT_CALLBACK);

        rowsInline.add(Collections.singletonList(typesSummerUniversity));
        rowsInline.add(Collections.singletonList(costSummerUniversity));
        rowsInline.add(Collections.singletonList(backToAbout));
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }
}


