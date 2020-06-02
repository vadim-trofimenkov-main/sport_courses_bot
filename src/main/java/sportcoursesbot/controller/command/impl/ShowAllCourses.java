package sportcoursesbot.controller.command.impl;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.controller.tool.UiEntityUtil;
import sportcoursesbot.controller.constant.CommandNames;
import sportcoursesbot.controller.tool.keyboard.ButtonConfig;
import sportcoursesbot.controller.tool.keyboard.KeyboardUtil;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.course.CourseService;
import sportcoursesbot.shared.entity.Course;

import java.util.List;

public class ShowAllCourses implements Command {
    private CourseService service = ServiceFactory.getCourseService();

    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        List<Course> allActualCourses = service.getAllActualCourses();

        List<ButtonConfig> configs = KeyboardUtil.buildConfigs(allActualCourses, CommandNames.SHOW_FULL_COURSE,
                Course::getId, UiEntityUtil::courseToShortString);

        InlineKeyboardMarkup markup = KeyboardUtil.buildMarkup(configs);
        ChatUtil.sendMessageWithMarkup("Here is all our actual courses", update, source, markup);
    }
}
