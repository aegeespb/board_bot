package org.aegee.board.bot;

import com.google.inject.Inject;
import org.aegee.board.bot.commands.*;
import org.jetbrains.annotations.NotNull;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class BoardBotRouter extends AbilityBot {
  @NotNull private final Settings mySettings;
  private final WhoAmICommand myWhoAmICommand;

  @Inject
  BoardBotRouter(Settings settings,
                 SenderProxy senderProxy,
                 WhoAmICommand whoAmICommand) {
    super(getToken(), String.valueOf(settings.getCreatorId()));
    mySettings = settings;
    myWhoAmICommand = whoAmICommand;
    mySettings.init(db);
    senderProxy.registerSender(sender);
    System.out.println("bot started");
  }

  private static String getToken() {
    final String token = System.getenv(Constants.BOT_TOKEN_ENV_NAME);
    if (token == null) {
      throw new IllegalStateException("You should specify bot token as environment variable " + Constants.BOT_TOKEN_ENV_NAME);
    }

    return token;
  }


  @SuppressWarnings("unused")
  public Ability startQuiz() {
    return Ability
            .builder()
            .name("start")
            .info("Start the bot")
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx -> {
              if (ctx == null) {
                return;
              }
//              myStartQuizCommand.execute(ctx.chatId());
            })
            .build();
  }

  @SuppressWarnings("unused")
  public Ability whoAmI() {
    return Ability
            .builder()
            .name("whoami")
            .info("Who am I")
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx -> {
              if (ctx == null) {
                return;
              }
              myWhoAmICommand.execute(ctx.user(), ctx.chatId());
            }).build();
  }



//  @SuppressWarnings("unused")
//  public Reply replyToButtons() {
//    ButtonReplier buttonReplier = EntryPoint.getInstance(ButtonReplier.class);
//    return Reply.of(buttonReplier::execute, Flag.CALLBACK_QUERY);
//  }

  @Override
  public int creatorId() {
    return mySettings.getCreatorId();
  }
}
