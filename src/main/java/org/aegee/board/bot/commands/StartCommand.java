package org.aegee.board.bot.commands;

import com.google.inject.Inject;
import org.aegee.board.bot.*;
import org.telegram.telegrambots.meta.api.objects.User;

public class StartCommand {
    private final SenderProxy mySenderProxy;
    private final UserHolder myUserHolder;

    @Inject
    public StartCommand(SenderProxy sender,
                        UserHolder userHolder) {
        mySenderProxy = sender;
        myUserHolder = userHolder;
    }

    public void execute(Long chatId, User user) {

        mySenderProxy.sendMessage(chatId.toString(), "Since the moment you will receive notifications about group events :yey:");
        createUpdateUser(chatId, user);
    }

    private void createUpdateUser(Long chatId, User user) {
        myUserHolder.addUser(chatId, user);
    }
}
