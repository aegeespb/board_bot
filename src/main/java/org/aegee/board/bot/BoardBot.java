package org.aegee.board.bot;

import com.google.inject.Inject;
import org.aegee.board.bot.commands.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BoardBot extends TelegramLongPollingBot {
  private final Settings mySettings;
  private final WhoAmICommand myWhoAmICommand;

  @Inject
  BoardBot(Settings settings,
           SenderProxy senderProxy,
           WhoAmICommand whoAmICommand) {
    mySettings = settings;
    myWhoAmICommand = whoAmICommand;
//    mySettings.init(db);
//    senderProxy.registerSender(sender);
    System.out.println("bot started");
  }


//  @SuppressWarnings("unused")
//  public Ability startQuiz() {
//    return Ability
//            .builder()
//            .name("start")
//            .info("Start the bot")
//            .locality(ALL)
//            .privacy(PUBLIC)
//            .action(ctx -> {
//              if (ctx == null) {
//                return;
//              }
//              try {
//                sender.execute(new SendMessage()
//                        .setText("started")
//                        .enableMarkdown(true)
//                        .setChatId(ctx.chatId()));
//              } catch (TelegramApiException e) {
//                e.printStackTrace();
//              }
//            })
//            .build();
//  }
//
//  @SuppressWarnings("unused")
//  public Ability whoAmI() {
//    return Ability
//            .builder()
//            .name("whoami")
//            .info("Who am I")
//            .locality(ALL)
//            .privacy(PUBLIC)
//            .action(ctx -> {
//              if (ctx == null) {
//                return;
//              }
//              myWhoAmICommand.execute(ctx.user(), ctx.chatId());
//            }).build();
//  }



//  @SuppressWarnings("unused")
//  public Reply replyToButtons() {
//    ButtonReplier buttonReplier = EntryPoint.getInstance(ButtonReplier.class);
//    return Reply.of(buttonReplier::execute, Flag.CALLBACK_QUERY);
//  }

//  @Override
//  public int creatorId() {
//    return mySettings.getCreatorId();
//  }

    @Override
    public String getBotUsername() {
        return "null";
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
            sendMsg(update.getMessage().getChatId().toString(), "Hello World");
        }
    }

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
//            log.log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    private boolean shouldFilterOut(Update update) {
        return !update.hasMessage() ||
                !update.getMessage().isUserMessage() ||
                !update.getMessage().hasText() ||
                update.getMessage().getText().isEmpty();
    }
}
