package org.aegee.board.bot;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class EntryPoint {
  private static Injector myInjector;
  private final BoardBotRouter myRouter;

  @Inject
  public EntryPoint(BoardBotRouter router) {
    myRouter = router;
  }

  public static void main(String[] args) {
    myInjector = Guice.createInjector(new GuiceConfig());
    EntryPoint entryPoint = myInjector.getInstance(EntryPoint.class);
    entryPoint.start();
  }

  static <T> T getInstance(Class<T> type) {
    return myInjector.getInstance(type);
  }

  private void start() {
    try {
      ApiContextInitializer.init();
      new TelegramBotsApi().registerBot(myRouter);
    } catch (TelegramApiRequestException e) {
      e.printStackTrace();
    }
  }
}
