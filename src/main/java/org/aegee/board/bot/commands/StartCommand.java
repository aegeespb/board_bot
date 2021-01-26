package org.aegee.board.bot.commands;

import com.google.inject.Inject;
import org.aegee.board.bot.*;
import org.telegram.telegrambots.meta.api.objects.User;

public class StartCommand {
    private final UserHolder myUserHolder;
    private AboutCommand myAboutCommand;

    @Inject
    public StartCommand(UserHolder userHolder,
                        AboutCommand aboutCommand) {
        myUserHolder = userHolder;
        myAboutCommand = aboutCommand;
    }

    public void execute(Long chatId, User user) {
        createUpdateUser(chatId, user);
        myAboutCommand.execute(chatId);
    }

    private void createUpdateUser(Long chatId, User user) {
        if (myUserHolder.getUser(chatId) == null) {
            myUserHolder.addUser(chatId, user);
        }
    }
}
