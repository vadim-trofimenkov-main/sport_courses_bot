package sportcoursesbot.controller.base;


import org.telegram.telegrambots.meta.api.objects.Update;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.shared.entity.User;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Map<Long, UserSession> USER_SESSION = new HashMap<>();

    public static UserSession putSession(Long chatId, User user) {
        UserSession newSession = new UserSession(user);
        USER_SESSION.put(chatId, newSession);
        return newSession;
    }

    public static UserSession getSession(Long chatId) {
        return USER_SESSION.get(chatId);
    }

    public static UserSession getSession(Update update) {
        Long chatId = ChatUtil.readChatId(update);
        return USER_SESSION.get(chatId);
    }

    public static boolean userOk(Update update) {
        UserSession session = getSession(update);
        return userOk(session);
    }

    public static boolean userOk(UserSession session) {
        User user = session.getUser();
        return user != null && user.getId() != null;
    }

    public static boolean sessionOk(Update update) {
        UserSession session = getSession(update);
        boolean init = session != null && userOk(session);
        return init;
    }

}
