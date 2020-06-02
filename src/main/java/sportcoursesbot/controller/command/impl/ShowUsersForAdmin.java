package sportcoursesbot.controller.command.impl;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.controller.tool.UiEntityUtil;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.user.UserService;
import sportcoursesbot.shared.entity.User;

import java.util.List;

public class ShowUsersForAdmin implements Command {
    private UserService service = ServiceFactory.getUserService();

    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        UserSession session = SessionManager.getSession(update);
        Long chatId = ChatUtil.readChatId(update);
        List<User> allUsers = service.getAllUsers(chatId);

        session.setAllUsers(allUsers);
        String usersToString = UiEntityUtil.usersToString(allUsers);
        ChatUtil.sendMessage(usersToString, update, source);
    }
}
