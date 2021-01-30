package org.aegee.board.bot;

import com.google.inject.Inject;
import org.aegee.board.bot.commands.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Arrays;

public class BoardBot extends TelegramLongPollingBot {
    private final WhoAmICommand myWhoAmICommand;
    private final HelpCommand myHelpCommand;
    private final StartCommand myStartCommand;
    private final ChangeLanguageCommand myChangeLanguageCommand;
    private final AboutCommand myAboutCommand;
    private final UserHolder myUserHolder;
    private final SenderProxy mySenderProxy;
    private final EventsCommand myEventsCommand;

    @Inject
    BoardBot(SenderProxy senderProxy,
             WhoAmICommand whoAmICommand,
             HelpCommand helpCommand,
             StartCommand startCommand,
             ChangeLanguageCommand changeLanguageCommand,
             AboutCommand aboutCommand,
             EventsCommand eventsCommand,
             UserHolder userHolder,
             Migration migration) {
        myWhoAmICommand = whoAmICommand;
        myHelpCommand = helpCommand;
        myStartCommand = startCommand;
        myChangeLanguageCommand = changeLanguageCommand;
        myAboutCommand = aboutCommand;
        myUserHolder = userHolder;
        myEventsCommand = eventsCommand;
        senderProxy.registerSender(this);
        mySenderProxy = senderProxy;
        System.out.println("bot started");

        migration.execute();
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
        String command = null;
        Long chatId = null;
        if (isCallback(update)) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            if (callbackQuery.getData().startsWith("/")) {
                command = callbackQuery.getData();
                chatId = callbackQuery.getMessage().getChatId();
            } else {
                handleCallback(callbackQuery);
                return;
            }
        }
        if (chatId == null) {
            chatId = update.getMessage().getChatId();
        }
        if (command == null) {
            command = update.getMessage().getText();
        }
        if (command.startsWith("/event_")) {
            myEventsCommand.handleEvent(chatId, command);
            return;
        }
        switch (command) {
            case "/start":
                createUpdateUser(chatId, update.getMessage().getFrom());
                myStartCommand.execute(chatId);
                break;
            case "/menu":
                myStartCommand.execute(chatId);
                break;
            case "/whoAmI":
                myWhoAmICommand.execute(update.getMessage().getFrom(), chatId);
                break;
            case "/listUsers":
                mySenderProxy.sendMessage(chatId.toString(), "New: " + Arrays.toString(myUserHolder.getAllUsers().toArray(new UserInfo[0])));
                break;
            case "/changeLanguage":
                myChangeLanguageCommand.execute(chatId);
                break;
            case "/help":
                myHelpCommand.execute(chatId);
                break;
            case "/faq":
            case "/about":
                myAboutCommand.execute(chatId);
                break;
            case "/version":
                mySenderProxy.sendMessage(chatId.toString(), "0.0.4");
                break;
            case "/events":
                myEventsCommand.execute(chatId);
                break;
        }
    }

    private void createUpdateUser(Long chatId, User user) {
        if (myUserHolder.getUser(chatId) == null) {
            myUserHolder.addUser(chatId, user);
        }
    }

    private void handleCallback(CallbackQuery callbackQuery) {
        if (Constants.BACK_TO_ABOUT_CALLBACK.equals(callbackQuery.getData())) {
            myAboutCommand.execute(callbackQuery.getMessage().getChatId());
        } else if (Language.ENGLISH.toString().equals(callbackQuery.getData()) ||
                Language.RUSSIAN.toString().equals(callbackQuery.getData())) {
            myChangeLanguageCommand.handleCallback(callbackQuery);
        } else if (callbackQuery.getData().startsWith("event_")) {
            myEventsCommand.addReminder(callbackQuery);
        } else {
            myAboutCommand.handleCallback(callbackQuery);
        }
    }

    private boolean isCallback(Update update) {
        return update.getCallbackQuery() != null;
    }
}