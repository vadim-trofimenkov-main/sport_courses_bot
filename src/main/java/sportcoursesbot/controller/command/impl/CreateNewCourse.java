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
import sportcoursesbot.shared.entity.Course;

public class CreateNewCourse implements Command {
    public static final String ENTER_COURSE_TITLE = "Please enter course title";
    public static final String ENTER_COURSE_DESCRIPTION = "Please enter course description";
    //    public static final String ENTER_COURSE_START_DATE = "Please enter course start date in YYYY-MM-DD format";
    private CourseService courseService = ServiceFactory.getCourseService();

    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        Long chatId = ChatUtil.readChatId(update);
        UserSession session = SessionManager.getSession(chatId);
        Course course;
        if (session.getNewCourse() == null) {
            course = new Course();
            session.setNewCourse(course);
        } else {
            course = session.getNewCourse();
            Command newCourse = TaskManager.getCommand(CommandNames.NEW_COURSE);
            if (course.getTitle() == null) {
                ChatUtil.sendMessage(ENTER_COURSE_TITLE, update, source);
                session.setNextCommand(newCourse);
            } else if (course.getDescription() == null) {
                ChatUtil.sendMessage(ENTER_COURSE_DESCRIPTION, update, source);
                session.setNextCommand(newCourse);
//        } else if(course.getStartDate() == null){
//            ChatUtil.sendMessage(ENTER_COURSE_START_DATE,update,source);
//            session.setNextCommand(newCourse);
            } else {
                courseService.createCourse(course);
                session.setNewCourse(null);
                session.setNextCommand(null);
                ChatUtil.sendMessage("Course was added successfully!:", chatId, source);
            }
        }
    }
}