package sportcoursesbot.controller.command.impl;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.dao.DaoFactory;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.course.CourseService;
import sportcoursesbot.service.user.UserService;
import sportcoursesbot.shared.entity.Course;
import sportcoursesbot.shared.entity.User;
import sportcoursesbot.shared.entity.security.Feature;
import sportcoursesbot.shared.exception.PermissionDeniedException;
import sportcoursesbot.shared.tool.SecurityUtil;


public class DeleteCourseCommand implements Command {
    private CourseService service = ServiceFactory.getCourseService();
    private UserService userService = ServiceFactory.getUserService();

    @Override
    public void execute(SportCoursesBot source, Update update) {
        Long chatId = ChatUtil.readChatId(update);
        UserSession session = SessionManager.getSession(chatId);
        User user = userService.getUser(chatId);
        try {
            Course course = session.getNewCourse();
            if (course == null){
                throw new RuntimeException("No course");
            }
            int id = course.getId();
            if (!SecurityUtil.hasFeature(user, Feature.DELETE_COURSE)) {
                throw new PermissionDeniedException();
            }
            service.deleteCourse(id);
            ChatUtil.sendMessage("Course was deleted successfully!", chatId, source);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } finally {
            session.setNewCourse(null);
        }
    }
}
