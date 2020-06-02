package sportcoursesbot.controller.command.impl;


import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.TaskManager;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.constant.CommandNames;

public class StartCommand implements Command {
    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        if (SessionManager.sessionOk(update)) {
            TaskManager.executeByName(CommandNames.SHOW_MAIN_MENU, source, update);
        } else {
            TaskManager.executeByName(CommandNames.INITIALIZE_USER_SESSION, source, update);
        }
    }
}

