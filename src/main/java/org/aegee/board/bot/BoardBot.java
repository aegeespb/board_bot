package org.aegee.board.bot;

import com.google.inject.Inject;
import org.aegee.board.bot.commands.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

public class BoardBot extends TelegramLongPollingBot {
    private final Settings mySettings;
    private final WhoAmICommand myWhoAmICommand;
    private final HelpCommand myHelpCommand;
    private final StartCommand myStartCommand;
    private final ChangeLanguageCommand myChangeLanguageCommand;
    private final AboutCommand myAboutCommand;
    private final SenderProxy mySenderProxy;

    @Inject
    BoardBot(Settings settings,
             SenderProxy senderProxy,
             WhoAmICommand whoAmICommand,
             HelpCommand helpCommand,
             StartCommand startCommand,
             ChangeLanguageCommand changeLanguageCommand,
             AboutCommand aboutCommand) {
        mySettings = settings;
        myWhoAmICommand = whoAmICommand;
        myHelpCommand = helpCommand;
        myStartCommand = startCommand;
        myChangeLanguageCommand = changeLanguageCommand;
        myAboutCommand = aboutCommand;
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
        if (isCallback(update)) handleCallback(update.getCallbackQuery());
        if (shouldFilterOut(update)) return;
        Long chatId = update.getMessage().getChatId();
        String command = update.getMessage().getText();
        switch (command) {
            case "/start":
                myStartCommand.execute(chatId, update.getMessage().getFrom());
                break;
            case "/whoAmI":
                myWhoAmICommand.execute(update.getMessage().getFrom(), chatId);
                break;
            case "/listUsers":
                mySenderProxy.sendMessage(chatId.toString(), Arrays.toString(mySettings.getAllListeners().toArray()));
                break;
            case "/changeLanguage":
                myChangeLanguageCommand.execute(chatId);
                break;
            case "/help":
                myHelpCommand.execute(chatId);
                break;
            case "/about":
                myAboutCommand.execute(chatId);
                break;
        }
    }

    private void handleCallback(CallbackQuery callbackQuery) {
        if (Constants.BACK_TO_ABOUT_CALLBACK.equals(callbackQuery.getData())) {
            myAboutCommand.execute(callbackQuery.getMessage().getChatId());
        } else if (Language.ENGLISH.toString().equals(callbackQuery.getData()) ||
                Language.RUSSIAN.toString().equals(callbackQuery.getData())) {
            myChangeLanguageCommand.handleCallback(callbackQuery);
        } else {
            myAboutCommand.handleCallback(callbackQuery);
        }
    }

    private boolean isCallback(Update update) {
        return update.getCallbackQuery() != null;
    }

    private boolean shouldFilterOut(Update update) {
        return !update.hasMessage() ||
                !update.getMessage().isUserMessage() ||
                !update.getMessage().hasText() ||
                update.getMessage().getText().isEmpty();
    }
}