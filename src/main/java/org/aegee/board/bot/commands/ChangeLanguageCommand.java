package org.aegee.board.bot.commands;

import com.google.inject.Inject;
import org.aegee.board.bot.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class ChangeLanguageCommand {
    private final SenderProxy mySenderProxy;
    private final UserHolder myUserHolder;

    @Inject
    public ChangeLanguageCommand(SenderProxy sender,
                                 UserHolder userHolder) {
        mySenderProxy = sender;
        myUserHolder = userHolder;
    }

    public void execute(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(Constants.PREFERABLE_LANGUAGE);
        sendMessage.setReplyMarkup(KeyboardsFactory.getLanguageKeyboard());
        mySenderProxy.execute(sendMessage);
    }

    public void handleCallback(CallbackQuery callbackQuery) {
        Language language = Language.valueOf(callbackQuery.getData());
        Long chatId = callbackQuery.getMessage().getChatId();
        myUserHolder.addUser(chatId, callbackQuery.getFrom(), language);

        EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
        editMarkup.setChatId(chatId.toString());
        editMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
        editMarkup.setReplyMarkup(null);
        mySenderProxy.execute(editMarkup);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("Language was successfully changed!");
        mySenderProxy.execute(sendMessage);
    }
}
