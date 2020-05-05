package sportcoursesbot.controller.command.impl;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.TaskManager;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.command.tool.ChatUtil;
import sportcoursesbot.controller.constant.CommandNames;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.user.UserService;
import sportcoursesbot.shared.entity.User;

public class StartCommand implements Command {
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
                SessionManager.putSession(chatId, user);
            }
            Command showMenu = TaskManager.getCommand((CommandNames.SHOW_MAIN_MENU));
            showMenu.execute(source, update);
        } else {
            ChatUtil.sendMessage("Hi. Enter your username.", chatId, source);
            Command newUseCommand = TaskManager.getCommand(CommandNames.NEW_USER);
            session = SessionManager.putSession(chatId, new User(chatId, null, "not registered"));
            session.setNextCommand(newUseCommand);
        }
    }
}

