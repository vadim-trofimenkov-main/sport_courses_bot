package sportcoursesbot.controller.command.impl;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.security.SecurityService;
import sportcoursesbot.shared.entity.User;
import sportcoursesbot.shared.entity.security.Role;

public class RefreshUserStatus implements Command {
    private SecurityService securityService = ServiceFactory.getSecurityService();

    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        UserSession session = SessionManager.getSession(update);
        User user = session.getUser();
        Integer id = user.getId();
        Role userRole = securityService.getUserRole(id);
        user.setRole(userRole);
    }
}
