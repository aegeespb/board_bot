package org.aegee.board.bot.commands;

import com.google.inject.Inject;
import org.aegee.board.bot.KeyboardsFactory;
import org.aegee.board.bot.SenderProxy;
import org.aegee.board.bot.UserHolder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class AboutCommand {
    private final SenderProxy mySenderProxy;
    private final UserHolder myUserHolder;

    @Inject
    public AboutCommand(SenderProxy sender,
                        UserHolder userHolder) {
        mySenderProxy = sender;
        myUserHolder = userHolder;
    }

    public void execute(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setText("Association des Etats Généraux des Etudiants de l’Europe (AEGEE, Европейский студенческий форум) — это одна из старейших и крупнейших молодежных некоммерческих негосударственных неполитических интердисциплинарных организаций Европы, зародившаяся во Франции (впрочем, это стало понятно уже из названия, верно?) в далеком 1985 году.");
        sendMessage.setReplyMarkup(KeyboardsFactory.getAboutKeyboard());
        mySenderProxy.execute(sendMessage);
    }

    public void handleCallback(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
        editMarkup.setChatId(chatId.toString());
        editMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
        editMarkup.setReplyMarkup(null);
        mySenderProxy.execute(editMarkup);

        String choose = callbackQuery.getData();
        AboutChapters chapter = AboutChapters.valueOf(choose);
        String text = "";
        if (chapter == AboutChapters.WHAT_TO_DO) {
            text = "AEGEE полностью управляется молодежью, поэтому преследуемые организацией цели близки и понятны" +
                    " каждому из нас. Самая, пожалуй, главная — это раскрытие потенциала молодых людей разных возрастов," +
                    " специальностей и национальностей. Основными инструментами для достижения целей являются:\n" +
                    "Тренинги, воркшопы и дискуссионные форумы;\n" +
                    "Тематические проекты;\n" +
                    "Международные обмены. \n\n" +
                    "AEGEE можно по праву считать площадкой для личностного роста, развития и кооперации:" +
                    " организация содействует осуществлению социально значимых проектов," +
                    " позволяя молодежи получить необходимые навыки и ресурсы.";
        } else if (chapter == AboutChapters.WHERE_IS_AEGEE) {
            text = "Национального разделения в AEGEE нет: все подразделения — кстати, мы называем их антеннами — функционируют исключительно на локальном уровне. В отличие от многих других молодежных организаций, AEGEE-антенны не привязаны к конкретным ВУЗам и открыты для студентов любых университетов, ровно как и для молодых людей, которые студентами не являются.\n\n" +
                    "Иными словами, в городе может быть создана только одна AEGEE-антенна — AEGEE-Sankt-Peterburg, например.\n\n" +
                    "Помимо Санкт-Петербурга AEGEE в Росии также представлена в Москве, Воронеже, Рязани, Самаре, Тюмени и Ростове-на-Дону. Итого чуть больше 160 подразделений в 40 странах Европы: так и получается, что в организации состоит почти 13 тысяч человек!";
        } else if (chapter == AboutChapters.MANAGEMENT_IN_AEGEE) {
            text = "Согласитесь, что за работой настолько большой организации не способен следить всего один человек: это просто-напросто невозможно! Поэтому организационная структура AEGEE довольно сложна — но сложностями нас не испугать, поэтому давайте разбираться.\n" +
                    "Локальный уровень\n" +
                    "За функционирование конкретного подразделения отвечает Борд (с англ. Board — правление), куда входят три обязательные позиции: Президент, Казначей и Секретарь. Прочие позиции обязательными не являются, а потому от срока к сроку могут разниться — например, в этом году в Борде AEGEE-Sankt-Peterburg есть еще IT, HR, PR и Советник. Кандидаты в Борд представляют свои программы на общем собрании членов подразделения (которое называется Локальной Агорой от греч. Agora — собрание), а голосуют за них тайно — настоящая демократия в действии!\n" +
                    "\n" +
                    "Согласно уставу AEGEE-Sankt-Peterburg, Борд избирается сроком на 1 год. Выбранные кандидаты руководят работой антенны в рамках своих позиций: PR наводит красоту в социальных сетях, IT помогает настраивать принтеры и проекторы занимается реализацией технических проектов, HR заботится о текущих членах антенны и ищет способы привлечь новых...\n" +
                    "Работа Борда — это непростой волонтерский труд, однако опыт, приобретаемый на этих позициях, бесценен и зачастую куда более обширен, чем на аналогичных коммерческих позициях." +
                    "\n" +
                    "Борду посильно помогают так называемые Рабочие Группы (англ. Working Groups) — объединения по критически важным для антенны направлениям. В настоящий момент в AEGEE-Sankt-Peterburg их пять:\n" +
                    "Локальные Мероприятия (англ. Local Events);\n" +
                    "Международные Мероприятия (англ. International Events);\n" +
                    "PR;\n" +
                    "IT;\n" +
                    "Фандрайзинг (англ. Fundraising).\n" +
                    "Вступить в рабочую группу (или даже в несколько рабочих групп) может любой желающий и в любое время: достаточно только стремиться к развитию в выбранном направлении и иметь несколько свободных часов в неделю!";
        } else if (chapter == AboutChapters.HOW_TO_JOIN) {
            text = "Стать членом семьи AEGEE-Sankt-Peterburg очень просто: достаточно только прийти на любое из интересующих локальных мероприятий, чтобы познакомиться с нами!\n" +
                    "Однако для участия в международных мероприятиях (обменах, тренинговых интенсивах, проекте \"Летние Университеты\" или других развлекательных мероприятиях) необходимо быть официальным членом организации.";

        } else if (chapter == AboutChapters.EVENTS_IN_AEGEE) {
            text = "Мы постоянно стремимся к расширению \"ассортимента\" наших мероприятий и к улучшению их качества, поэтому будем рады услышать ваши идеи, предложения и впечатления.\n" +
                    "Локальные мероприятия:\n" +
                    "Международные мероприятия:\n" +
                    "\n" +
                    "Международные мероприятия в Петербурге проводятся минимум трижды в год. Их количество куда меньше, чем количество локальных мероприятий, но это и понятно: размах у международных мероприятий куда серьезнее!\n" +
                    "Обмены — обмены с другими антеннами AEGEE;\n" +
                    "Летние Университеты (Летники) — о том, что это такое, узнать можно в нашей статье;\n" +
                    "Новогодние ивенты — совсем как Летники, только зимой... И даже называются сокращенно по аналогии — \"Зимники\".\n" +
                    "\n" +
                    "А список мероприятий в Европе можно найти тут.\n" +
                    "В каждом международном мероприятии принимает участие от 15 до 800 иностранцев — не будет лишним упомянуть, что это отличная возможность улучшить свой разговорный английский!";
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(KeyboardsFactory.getBackToAbout());
        mySenderProxy.execute(sendMessage);
    }

    public enum AboutChapters {
        WHAT_TO_DO,
        WHERE_IS_AEGEE,
        MANAGEMENT_IN_AEGEE,
        HOW_TO_JOIN,
        EVENTS_IN_AEGEE
    }
}
