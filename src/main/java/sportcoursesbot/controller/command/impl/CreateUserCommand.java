package sportcoursesbot.controller.command.impl;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.TaskManager;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.controller.constant.CommandNames;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.user.UserService;
import sportcoursesbot.shared.entity.User;

public class CreateUserCommand implements Command {
    private UserService userService = ServiceFactory.getUserService();


    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        String text = update.getMessage().getText();
        Long chatId = ChatUtil.readChatId(update);
        User newUser = new User(chatId, text, "simple");
        userService.createUser(newUser);
        UserSession session = SessionManager.getSession(chatId);
        if (session != null) {
            session.setUser(newUser);
        } else {
            SessionManager.putSession(chatId, newUser);
        }
        ChatUtil.sendMessage("Nice to meet you!", chatId, source);
        Command showMenu = TaskManager.getCommand(CommandNames.SHOW_MAIN_MENU);
        showMenu.execute(source, update);
    }
}