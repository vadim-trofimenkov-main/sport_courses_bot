package sportcoursesbot.controller.command.impl;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.controller.tool.UiEntityUtil;
import sportcoursesbot.controller.constant.CommandNames;
import sportcoursesbot.controller.tool.keyboard.ButtonConfig;
import sportcoursesbot.controller.tool.keyboard.KeyboardUtil;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.course.CourseService;
import sportcoursesbot.service.user.UserService;
import sportcoursesbot.shared.entity.Course;
import sportcoursesbot.shared.entity.User;
import sportcoursesbot.shared.entity.security.Role;

import java.util.List;

public class ShowAllCourses implements Command {
    private CourseService service = ServiceFactory.getCourseService();
    private UserService userService = ServiceFactory.getUserService();

    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        List<Course> allCourses;
        Long chatId = ChatUtil.readChatId(update);
        User user = userService.getUser(chatId);
        String status = user.getStatus();
        Role role = Role.getByName(status);
        if (Role.ADMIN.equals(role)) {
            allCourses = service.getAllCourses();
        } else {
            allCourses = service.getAllActualCourses();
        }
        List<ButtonConfig> configs = KeyboardUtil.buildConfigs(allCourses, CommandNames.SHOW_FULL_COURSE,
                Course::getId, UiEntityUtil::courseToShortString);

        InlineKeyboardMarkup markup = KeyboardUtil.buildMarkup(configs);
        ChatUtil.sendMessageWithMarkup("All actual courses:", update, source, markup);
    }
}
