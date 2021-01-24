package org.aegee.board.bot;

import com.google.inject.Inject;
import org.aegee.board.bot.commands.ChangeLanguageCommand;
import org.aegee.board.bot.commands.HelpCommand;
import org.aegee.board.bot.commands.StartCommand;
import org.aegee.board.bot.commands.WhoAmICommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

public class BoardBot extends TelegramLongPollingBot {
    private final Settings mySettings;
    private final WhoAmICommand myWhoAmICommand;
    private final HelpCommand myHelpCommand;
    private final StartCommand myStartCommand;
    private final ChangeLanguageCommand myChangeLanguageCommand;
    private final SenderProxy mySenderProxy;

    @Inject
    BoardBot(Settings settings,
             SenderProxy senderProxy,
             WhoAmICommand whoAmICommand,
             HelpCommand helpCommand,
             StartCommand startCommand,
             ChangeLanguageCommand changeLanguageCommand) {
        mySettings = settings;
        myWhoAmICommand = whoAmICommand;
        myHelpCommand = helpCommand;
        myStartCommand = startCommand;
        myChangeLanguageCommand = changeLanguageCommand;
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
        if (isCallback(update)) myChangeLanguageCommand.handleCallback(update.getCallbackQuery());
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