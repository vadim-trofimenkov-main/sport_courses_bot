package sportcoursesbot.controller.command;


import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;

public interface Command {
    void execute(SportCoursesBot source, Update update) throws TelegramApiException;
}
