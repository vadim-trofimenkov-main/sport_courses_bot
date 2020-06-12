package sportcoursesbot.controller.command.impl;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.shared.exception.UserFriendlyException;

public class MyCoursesCommand implements Command {
    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        throw new UserFriendlyException("Feature is in development");
    }
}
