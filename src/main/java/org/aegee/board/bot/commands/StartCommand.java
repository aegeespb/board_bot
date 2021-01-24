package org.aegee.board.bot.commands;

import com.google.inject.Inject;
import org.aegee.board.bot.*;
import org.telegram.telegrambots.meta.api.objects.User;

public class StartCommand {
    private final SenderProxy mySenderProxy;
    private final UserHolder myUserHolder;
    private AboutCommand myAboutCommand;

    @Inject
    public StartCommand(SenderProxy sender,
                        UserHolder userHolder,
                        AboutCommand aboutCommand) {
        mySenderProxy = sender;
        myUserHolder = userHolder;
        myAboutCommand = aboutCommand;
    }

    public void execute(Long chatId, User user) {
        createUpdateUser(chatId, user);
        myAboutCommand.execute(chatId);
    }

    private void createUpdateUser(Long chatId, User user) {
        myUserHolder.addUser(chatId, user);
    }
}
