package sportcoursesbot.controller.command.impl;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.TaskManager;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.constant.CommandNames;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.dao.tool.EntityDaoUtil;
import sportcoursesbot.shared.entity.Course;

import java.sql.Timestamp;

public class EditCourse1 implements Command {
    private Integer count = 0;
    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        Long chatId = ChatUtil.readChatId(update);
        UserSession session = SessionManager.getSession(chatId);
        Course course = session.getNewCourse();

        String updateText = update.getMessage().getText();
        if (count == 0) {
            course.setTitle(updateText);
            count = 1;
        } else if (count == 1) {
            course.setDescription(updateText);
            count = 2;
        } else if (count == 2) {
            Timestamp date = EntityDaoUtil.dateConverter(updateText);
            course.setStartDate(date);
            count = 3;
        }
        TaskManager.executeByName(CommandNames.EDIT_COURSE, source, update);
    }
}

