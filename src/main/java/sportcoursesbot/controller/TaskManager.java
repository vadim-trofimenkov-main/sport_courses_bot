package sportcoursesbot.controller;


import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.command.exception.UnknownCommandException;
import sportcoursesbot.controller.command.impl.*;
import sportcoursesbot.controller.constant.CommandNames;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private static final Map<String, Command> COMMANDS = new HashMap<>();

    TaskManager() {
        COMMANDS.put(CommandNames.START, new StartCommand());
        COMMANDS.put(CommandNames.NEW_USER, new CreateUserCommand());
        COMMANDS.put(CommandNames.SHOW_MAIN_MENU, new ShowMainMenuCommand());
        COMMANDS.put(CommandNames.SHOW_ALL_COURSES, new ShowAllCourses());
        COMMANDS.put(CommandNames.SHOW_FULL_COURSE, new ShowFullCourse());
        COMMANDS.put(CommandNames.SHOW_USERS_FOR_ADMIN, new ShowUsersForAdmin());
    }

    public void impl(String commandName, Update update, SportCoursesBot source) throws TelegramApiException {
        Command command = COMMANDS.get(commandName);
        UserSession session = SessionManager.getSession(update);
        if (command != null) {
            if (session != null) {
                session.clearNextCommandIfExists();
            }
            command.execute(source, update);
        } else {
            Command nextCommand = session.getNextCommand();
            if (nextCommand != null) {
                nextCommand.execute(source, update);
            } else {
                throw new UnknownCommandException();
            }
        }
    }

    public static Command getCommand(String name) {
        return COMMANDS.get(name);
    }
}
