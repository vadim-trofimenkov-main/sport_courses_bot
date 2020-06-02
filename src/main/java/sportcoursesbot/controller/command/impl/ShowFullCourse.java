package sportcoursesbot.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.controller.tool.UiEntityUtil;
import sportcoursesbot.controller.tool.keyboard.ButtonCallback;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.course.CourseService;
import sportcoursesbot.shared.entity.Course;
import sportcoursesbot.shared.tool.ExceptionHandler;

import java.util.List;

public class ShowFullCourse implements Command {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private CourseService service = ServiceFactory.getCourseService();

    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String data = callbackQuery.getData();
        try {
            ButtonCallback<Integer> callback = OBJECT_MAPPER.readValue(data, new TypeReference<ButtonCallback<Integer>>() {});
            Course fullCourse = service.getFullCourse(callback.getValue());
            String fullCourseString = UiEntityUtil.courseToFullString(fullCourse);
            ChatUtil.sendMessage(fullCourseString, update, source);
        } catch (JsonProcessingException e) {
            ExceptionHandler.printAndThrowRuntime(e);
        }
    }
}
