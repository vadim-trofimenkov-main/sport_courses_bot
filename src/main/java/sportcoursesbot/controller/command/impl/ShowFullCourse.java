package sportcoursesbot.controller.command.impl;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.command.tool.ChatUtil;
import sportcoursesbot.controller.command.tool.UiEntityUtil;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.course.CourseService;
import sportcoursesbot.shared.entity.Course;

import java.util.List;

public class ShowFullCourse implements Command {
    private CourseService service = ServiceFactory.getCourseService();

    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        Long chatId = ChatUtil.readChatId(update);
        UserSession session = SessionManager.getSession(chatId);
        List<Course> allCourses = session.getAllCourses();
        if (allCourses == null) {
            allCourses = service.getAllActualCourses();
        }
        String input = update.getMessage().getText();
        input = input.substring(1);
        int courseNumber = Integer.parseInt(input);
        Course course = allCourses.get(courseNumber - 1);
        Course fullCourse = service.getFullCourse(course.getId());
        String fullCourseMsg = UiEntityUtil.coursesToString(fullCourse);

        ChatUtil.sendMessage(fullCourseMsg, chatId, source);

    }
}
