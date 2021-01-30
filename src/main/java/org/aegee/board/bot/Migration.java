package org.aegee.board.bot;

import com.google.inject.Inject;
import org.aegee.board.bot.commands.StartCommand;

public class Migration {
    private final SenderProxy mySenderProxy;
    private final UserHolder myUserHolder;
    private final StartCommand myStartCommand;

    @Inject
    public Migration(SenderProxy senderProxy,
                     UserHolder userHolder,
                     StartCommand startCommand) {
        mySenderProxy = senderProxy;
        myUserHolder = userHolder;
        myStartCommand = startCommand;
    }

    public void execute() {
//        System.out.println("Start waiting user holder");
//        while (!myUserHolder.isReady) {
//            try {
//                Thread.sleep(400);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("Migration started");
//        for (UserInfo boardMembersId : myUserHolder.getBoardMembers()) {
//            if (boardMembersId.getChatId() == 20660787L)
//                myStartCommand.execute(boardMembersId.getChatId());
//        }
    }
}
