package sportcoursesbot.controller.command.impl;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.course.CourseService;
import sportcoursesbot.shared.entity.Course;


public class DeleteCourseCommand implements Command {
    private CourseService service = ServiceFactory.getCourseService();

    @Override
    public void execute(SportCoursesBot source, Update update) {
        Long chatId = ChatUtil.readChatId(update);
        UserSession session = SessionManager.getSession(chatId);
        try {
            Course course = session.getNewCourse();
            if (course == null){
                throw new RuntimeException("No course");
            }
            int id = course.getId();
            service.deleteCourse(id);
            ChatUtil.sendMessage("Course was deleted successfully!", chatId, source);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
