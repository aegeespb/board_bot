package org.aegee.board.bot;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class EntryPoint {
  private static Injector myInjector;
  private final BoardBot myRouter;

  @Inject
  public EntryPoint(BoardBot router,
                    VkHandler vkHandler) {
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
      new TelegramBotsApi(DefaultBotSession.class).registerBot(myRouter);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
}
