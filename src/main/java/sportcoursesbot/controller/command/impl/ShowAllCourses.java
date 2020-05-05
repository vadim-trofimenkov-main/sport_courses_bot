package sportcoursesbot.controller.command.impl;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.TaskManager;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.command.tool.ChatUtil;
import sportcoursesbot.controller.command.tool.UiEntityUtil;
import sportcoursesbot.controller.constant.CommandNames;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.course.CourseService;
import sportcoursesbot.shared.entity.Course;

import java.util.List;

public class ShowAllCourses implements Command {
    private CourseService service = ServiceFactory.getCourseService();

    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        List<Course> allActualCourses = service.getAllActualCourses();
        String courses = UiEntityUtil.coursesToShortString(allActualCourses);
        ChatUtil.sendMessage(courses, update, source);
        UserSession session = SessionManager.getSession(update);
        session.setAllCourses(allActualCourses);
        session.setNextCommand(TaskManager.getCommand(CommandNames.SHOW_FULL_COURSE));
    }
}
