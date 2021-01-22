package org.aegee.board.bot;

import com.google.inject.Inject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public class BoardBot extends TelegramLongPollingBot {
    private final Settings mySettings;
    private final SenderProxy mySenderProxy;

    @Inject
    BoardBot(Settings settings,
             SenderProxy senderProxy) {
        mySettings = settings;
        senderProxy.registerSender(this);
        mySenderProxy = senderProxy;
        System.out.println("bot started");
    }

    @Override
    public String getBotUsername() {
        return "Board Bot";
    }

    @Override
    public String getBotToken() {
        final String token = System.getenv(Constants.BOT_TOKEN_ENV_NAME);
        if (token == null) {
            throw new IllegalStateException("You should specify bot token as environment variable " + Constants.BOT_TOKEN_ENV_NAME);
        }

        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (shouldFilterOut(update)) return;

        String command = update.getMessage().getText();
        if (command.equals("/start")) {
            mySenderProxy.sendMessage(update.getMessage().getChatId().toString(), "Since the moment you will receive notifications about group events :yey:");
            try {
                mySettings.addListener(update.getMessage().getChatId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean shouldFilterOut(Update update) {
        return !update.hasMessage() ||
                !update.getMessage().isUserMessage() ||
                !update.getMessage().hasText() ||
                update.getMessage().getText().isEmpty();
    }
}