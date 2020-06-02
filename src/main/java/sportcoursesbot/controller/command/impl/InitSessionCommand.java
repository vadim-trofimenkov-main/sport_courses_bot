package sportcoursesbot.controller.command.impl;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.user.UserService;
import sportcoursesbot.shared.entity.User;


public class InitSessionCommand implements Command {
    public static final String ENTER_YOU_USERNAME = "Hi. Enter you username.";
    private UserService userService = ServiceFactory.getUserService();

    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        Long chatId = ChatUtil.readChatId(update);
        User user = userService.getUser(chatId);
        UserSession session = SessionManager.getSession(chatId);
        if (user != null) {
            if (session != null) {
                session.setUser(user);
            } else {
                session = SessionManager.putSession(chatId, user);
            }
        } else {
            ChatUtil.sendMessage(ENTER_YOU_USERNAME, chatId, source);
            session = SessionManager.putSession(chatId, new User(chatId, null, "not registered"));
        }
    }
}
