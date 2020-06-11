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

public class NewCourseCommand implements Command {

    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        Long chatId = ChatUtil.readChatId(update);
        UserSession session = SessionManager.getSession(chatId);
        Course course = session.getNewCourse();
        String text = update.getMessage().getText();
        if (course.getTitle() == null) {
            course.setTitle(text);
        } else if (course.getDescription() == null) {
            course.setDescription(text);
        } else if (course.getStartDate() == null) {
            Timestamp date = EntityDaoUtil.dateConverter(text);
            course.setStartDate(date);
        }
        TaskManager.executeByName(CommandNames.CREATE_NEW_COURSE, source,update);
    }
}