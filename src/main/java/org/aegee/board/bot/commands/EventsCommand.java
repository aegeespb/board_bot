package org.aegee.board.bot.commands;

import com.google.inject.Inject;
import org.aegee.board.bot.SenderProxy;
import org.aegee.board.bot.UserHolder;
import org.aegee.board.bot.events.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
    private final UserHolder myUserHolder;
    private static final Map<Integer, Event> events = new HashMap<>();
    private final DateFormat df = new SimpleDateFormat("dd.MM");

    static {
        events.put(new Event1().getId(), new Event1());
        events.put(new Event2().getId(), new Event2());
        events.put(new Event3().getId(), new Event3());
        events.put(new Event4().getId(), new Event4());
        events.put(new Event5().getId(), new Event5());
    }

    @Inject
    public EventsCommand(SenderProxy sender,
                         UserHolder userHolder) {
        mySenderProxy = sender;
        myUserHolder = userHolder;
    }

    public void execute(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        StringBuilder sb = new StringBuilder();
        for (Event event : events.values()) {
            if (event.getStartDate().before(Calendar.getInstance().getTime())) continue;
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
            Event event = events.get(id);
            if (event != null) {
                sendMessage.setText(event.getLongDescription());
                InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

                InlineKeyboardButton subscribe = new InlineKeyboardButton();
                subscribe.setText("Remind");
                subscribe.setCallbackData("event_" + id + "_remind");

                InlineKeyboardButton registration = new InlineKeyboardButton();
                registration.setText("Registration");
                registration.setUrl(event.getRegistrationLink());

                List<InlineKeyboardButton> buttons = new ArrayList<>();
                buttons.add(registration);
                buttons.add(subscribe);
                rowsInline.add(buttons);
                inlineKeyboard.setKeyboard(rowsInline);
                sendMessage.setReplyMarkup(inlineKeyboard);
            }
        }

        mySenderProxy.execute(sendMessage);
    }

    public void addReminder(CallbackQuery callbackQuery) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        sendMessage.setText("This functionality will be implemented soon ;)");
        mySenderProxy.execute(sendMessage);
    }
}
