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
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.course.CourseService;
import sportcoursesbot.service.user.UserService;
import sportcoursesbot.shared.entity.Course;
import sportcoursesbot.shared.entity.User;
import sportcoursesbot.shared.entity.security.Feature;
import sportcoursesbot.shared.exception.PermissionDeniedException;
import sportcoursesbot.shared.tool.SecurityUtil;

public class EditCourseCommand implements Command {
    public static final String UPDATE_COURSE_TITLE = "Please update course title";
    public static final String UPDATE_COURSE_DESCRIPTION = "Please update course description";
    public static final String UPDATE_COURSE_START_DATE = "Please update course start date in YYYY-MM-DD format";
    private CourseService courseService = ServiceFactory.getCourseService();
    private UserService userService = ServiceFactory.getUserService();
    private Integer count = 0;

    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        Long chatId = ChatUtil.readChatId(update);
        UserSession session = SessionManager.getSession(chatId);
        User user = userService.getUser(chatId);
        if (!SecurityUtil.hasFeature(user, Feature.EDIT_COURSE)) {
            throw new PermissionDeniedException();
        }
        Course course;
        if (session.getNewCourse() == null) {
            course = new Course();
            session.setNewCourse(course);
        } else {
            course = session.getNewCourse();
            Command nextCommand = TaskManager.getCommand(CommandNames.EDIT_COURSE_1);
            if (count == 0) {
                ChatUtil.sendMessage(UPDATE_COURSE_TITLE, update, source);
                session.setNextCommand(nextCommand);
                count = 1;
            } else if (count == 1) {
                ChatUtil.sendMessage(UPDATE_COURSE_DESCRIPTION, update, source);
                session.setNextCommand(nextCommand);
                count = 2;
            } else if (count == 2) {
                ChatUtil.sendMessage(UPDATE_COURSE_START_DATE, update, source);
                session.setNextCommand(nextCommand);
                count = 3;
            } else {
                courseService.editCourse(course);
                session.setNewCourse(null);
                session.setNextCommand(null);
                count = 0;
                ChatUtil.sendMessage("Course was updated successfully!", chatId, source);
            }
        }
    }
}
