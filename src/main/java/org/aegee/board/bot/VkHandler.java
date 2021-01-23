package org.aegee.board.bot;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vk.api.sdk.callback.longpoll.CallbackApiLongPoll;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.callback.GroupJoin;
import com.vk.api.sdk.objects.callback.GroupLeave;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import com.vk.api.sdk.objects.wall.Wallpost;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Singleton
public class VkHandler {
    private final SenderProxy mySenderProxy;
    private final Settings mySettings;
    private final VkApiClient vkApiClient;
    @Inject
    public VkHandler(SenderProxy senderProxy, Settings settings) throws ClientException, ApiException {
        mySenderProxy = senderProxy;
        mySettings = settings;
        GroupActor groupActor = createGroupActor();
        HttpTransportClient httpClient = HttpTransportClient.getInstance();
        vkApiClient = new VkApiClient(httpClient);
        vkApiClient.groups().setLongPollSettings(groupActor, groupActor.getGroupId()).enabled(true)
                .groupJoin(true)
                .groupLeave(true)
                .messageNew(true)
                .wallPostNew(true)
                .apiVersion("5.126")
                .execute();

        new Thread(() -> {
            try {
                CallbackApiLongPollHandler handler = new CallbackApiLongPollHandler(vkApiClient, groupActor);
                handler.run();
            } catch(ApiException | ClientException v) {
                v.printStackTrace();
            }
        }).start();
    }

    public class CallbackApiLongPollHandler extends CallbackApiLongPoll {
        private static final String CALLBACK_EVENT_CONFIRMATION = "confirmation";

        @Override
        public boolean parse(JsonObject json) {
            String type = json.get("type").getAsString();
            if (type.equalsIgnoreCase(CALLBACK_EVENT_CONFIRMATION)) {
                super.parse(json);
            }

            if (type.equals("message_new")) {
                String text = json.getAsJsonObject("object").getAsJsonObject("message").get("text").toString();
                String fromId = json.getAsJsonObject("object").getAsJsonObject("message").get("from_id").toString();
                notifyNewMessage(Integer.parseInt(fromId), text);
            }
            return super.parse(json);
        }

        public CallbackApiLongPollHandler(VkApiClient client, GroupActor actor) {
            super(client, actor);
        }

        @Override
        public void groupLeave(Integer groupId, GroupLeave message) {
            for (Long userId : mySettings.getAllListeners()) {
                notifyLeaveJoin(userId, message.getUserId(), false);
            }
        }

        @Override
        public void groupJoin(Integer groupId, GroupJoin message) {
            for (Long userId : mySettings.getAllListeners()) {
                notifyLeaveJoin(userId, message.getUserId(), true);
            }
        }

        @Override
        public void wallPostNew(Integer groupId, Wallpost message) {
            try {
                Integer vkUserId = message.getFromId();
                String link = "https://vk.com/club" + getVkGroupId() + "?w=wall-" + getVkGroupId() + "_" + message.getId();
                List<GetResponse> execute = vkApiClient.users().get(createGroupActor()).userIds(String.valueOf(vkUserId)).execute();
                String msg = "New [post](" + link + ") from [" + execute.get(0).getFirstName() + " " + execute.get(0).getLastName() + "](" + "https://vk.com/id" + vkUserId + ")";

                for (Long tgUserIdToNotify : mySettings.getAllListeners()) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(tgUserIdToNotify.toString());
                    sendMessage.enableMarkdown(true);
                    sendMessage.setText(msg);
                    mySenderProxy.execute(sendMessage);
                }
            } catch (ApiException | ClientException e) {
                e.printStackTrace();
            }
            super.wallPostNew(groupId, message);
        }
    }

    private void notifyNewMessage(Integer vkUserId, String message) {
        try {
            for (Long tgUserIdToNotify : mySettings.getAllListeners()) {
                List<GetResponse> execute = vkApiClient.users().get(createGroupActor()).userIds(String.valueOf(vkUserId)).execute();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(tgUserIdToNotify.toString());
                sendMessage.enableMarkdown(true);
                String msg = "New message from [" + execute.get(0).getFirstName() + " " + execute.get(0).getLastName() + "](" + "https://vk.com/id" + vkUserId + ") received: " + message;
                sendMessage.setText(msg);
                mySenderProxy.execute(sendMessage);
            }
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
    private void notifyLeaveJoin(Long tgUserIdToNotify, Integer vkUserId, boolean isJoin) {
        try {
            List<GetResponse> execute = vkApiClient.users().get(createGroupActor()).userIds(String.valueOf(vkUserId)).execute();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(tgUserIdToNotify.toString());
            sendMessage.enableMarkdown(true);
            String msg = "[" + execute.get(0).getFirstName() + " " + execute.get(0).getLastName() + "](" + "https://vk.com/id" + vkUserId + ") "
                    + (isJoin ? "has joined" : "has left");
            sendMessage.setText(msg);
            mySenderProxy.execute(sendMessage);
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    private static GroupActor createGroupActor() {
        return new GroupActor(getVkGroupId(), getVkAccessToken());
    }

    private static Integer getVkGroupId() {
        final String vkGroupId = System.getenv(Constants.VK_GROUP_ID_ENV_NAME);
        if (vkGroupId == null) {
            throw new IllegalStateException("You should specify vk group id as environment variable " + Constants.VK_GROUP_ID_ENV_NAME);
        }

        return Integer.parseInt(vkGroupId);
    }

    private static String getVkAccessToken() {
        final String vkGroupAccessToken = System.getenv(Constants.VK_GROUP_TOKEN_ENV_NAME);
        if (vkGroupAccessToken == null) {
            throw new IllegalStateException("You should specify vk group access token as environment variable " + Constants.VK_GROUP_TOKEN_ENV_NAME);
        }

        return vkGroupAccessToken;
    }
}
