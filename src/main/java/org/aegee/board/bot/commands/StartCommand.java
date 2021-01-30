package org.aegee.board.bot.commands;

import com.google.inject.Inject;
import org.aegee.board.bot.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class StartCommand {
    private final SenderProxy mySenderProxy;

    @Inject
    public StartCommand(SenderProxy senderProxy) {
        mySenderProxy = senderProxy;
    }

    public void execute(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("Welcome to AEGEE-Sankt-Peterburg");
        sendMessage.setReplyMarkup(getInlineKeyboardMarkup());
        mySenderProxy.execute(sendMessage);
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        InlineKeyboardButton events = new InlineKeyboardButton();
        events.setText("Upcoming Events");
        events.setCallbackData("/events");

        InlineKeyboardButton faq = new InlineKeyboardButton();
        faq.setText("FAQ");
        faq.setCallbackData("/about");

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(faq);
        buttons.add(events);
        rowsInline.add(buttons);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }
}
