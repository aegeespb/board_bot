package org.aegee.board.bot.commands;

import com.google.inject.Inject;
import org.aegee.board.bot.SenderProxy;
import org.aegee.board.bot.events.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventsCommand {
    private final SenderProxy mySenderProxy;
    private final EventsHolder myEventsHolder;
    private final DateFormat df = new SimpleDateFormat("dd.MM");

    @Inject
    public EventsCommand(SenderProxy sender,
                         EventsHolder eventsHolder) {
        mySenderProxy = sender;
        myEventsHolder = eventsHolder;
    }

    public void execute(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        StringBuilder sb = new StringBuilder();
        for (Event event : myEventsHolder.getUpcomingEvents()) {
            sb.append(df.format(event.getStartDate()));
            sb.append(" - ");
            if (event.getFinishDate() != null) {
                sb.append(df.format(event.getFinishDate()));
                sb.append(" - ");
            }
            sb.append(event.getShortDescription());
            sb.append(" /event_").append(event.getId());
            sb.append("\n");
        }
        sendMessage.setText(sb.toString());
        mySenderProxy.execute(sendMessage);
    }

    private final Pattern myEventIdPattern = Pattern.compile("/event_(.*)");
    public void handleEvent(Long chatId, String command) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());

        Matcher eventIdMatcher = myEventIdPattern.matcher(command);
        if (eventIdMatcher.matches()) {
            String idAsString = eventIdMatcher.group(1);
            Integer id = Integer.parseInt(idAsString);
            Event event = myEventsHolder.getById(id);
            if (event != null) {
                sendMessage.setText(event.getLongDescription());
                sendMessage.setReplyMarkup(getInlineKeyboard(event, chatId));
            }
        }

        mySenderProxy.execute(sendMessage);
    }

    private InlineKeyboardMarkup getInlineKeyboard(Event event, Long chatId) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        InlineKeyboardButton subscribe = new InlineKeyboardButton();
        if (!event.getSubscribers().contains(chatId)) {
            subscribe.setText("Remind");
            subscribe.setCallbackData("event_" + event.getId() + "_remind");
        } else {
            subscribe.setText("Remove reminder");
            subscribe.setCallbackData("event_" + event.getId() + "_unremind");
        }
        InlineKeyboardButton registration = new InlineKeyboardButton();
        registration.setText("Registration");
        registration.setUrl(event.getRegistrationLink());

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(registration);
        buttons.add(subscribe);
        rowsInline.add(buttons);
        inlineKeyboard.setKeyboard(rowsInline);

        return inlineKeyboard;
    }

    private final Pattern remindPattern = Pattern.compile("event_(.*)_remind");
    private final Pattern unRemindPattern = Pattern.compile("event_(.*)_unremind");
    public void addReminder(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        Matcher reminderMatcher = remindPattern.matcher(data);
        Matcher unReminderMatcher = unRemindPattern.matcher(data);

        if (reminderMatcher.matches()) {
            Integer eventId = Integer.parseInt(reminderMatcher.group(1));
            Event event = myEventsHolder.getById(eventId);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
            if (!event.getSubscribers().contains(callbackQuery.getMessage().getChatId())) {
                event.addSubscriber(callbackQuery.getMessage().getChatId());
                myEventsHolder.updateSubscribers(event);
                sendMessage.setText("Reminder for event '" + event.getShortDescription() + "' added");
            } else {
                sendMessage.setText("Reminder already exists");
            }

            mySenderProxy.execute(sendMessage);
            updateInlineKeyboard(callbackQuery, event);
        } else if (unReminderMatcher.matches()){
            Integer eventId = Integer.parseInt(unReminderMatcher.group(1));
            Event event = myEventsHolder.getById(eventId);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
            if (event.getSubscribers().contains(callbackQuery.getMessage().getChatId())) {
                event.removeSubscriber(callbackQuery.getMessage().getChatId());
                myEventsHolder.updateSubscribers(event);
                sendMessage.setText("Reminder for event '" + event.getShortDescription() + "' removed");
            } else {
                sendMessage.setText("Reminder not exists");
            }

            mySenderProxy.execute(sendMessage);
            updateInlineKeyboard(callbackQuery, event);
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
            sendMessage.setText("Reminder not changed - received command " + data);
            mySenderProxy.execute(sendMessage);
        }
    }

    private void updateInlineKeyboard(CallbackQuery callbackQuery, Event event) {
        EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
        editMarkup.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
        editMarkup.setReplyMarkup(getInlineKeyboard(event, callbackQuery.getMessage().getChatId()));
        mySenderProxy.execute(editMarkup);
    }
}
