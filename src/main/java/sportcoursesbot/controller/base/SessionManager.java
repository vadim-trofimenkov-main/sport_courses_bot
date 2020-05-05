package sportcoursesbot.controller.base;


import org.telegram.telegrambots.meta.api.objects.Update;
import sportcoursesbot.controller.command.tool.ChatUtil;
import sportcoursesbot.shared.entity.User;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Map<Long, UserSession> USER_SESSION = new HashMap<>();

    public static UserSession putSession(Long chatId, User user){
        UserSession newSession = new UserSession(user);
        USER_SESSION.put(chatId, newSession);
//        UserSession session = USER_SESSION.put(chatId, newSession);
//        if(session != null){
//            throw new RuntimeException("Session is already defined");
//        }
        return newSession;
    }
    public static UserSession getSession(Long chatId){
        return USER_SESSION.get(chatId);
    }

    public static UserSession getSession(Update update){
        Long chatId = ChatUtil.readChatId(update);
        return USER_SESSION.get(chatId);
    }
}
