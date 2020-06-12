package sportcoursesbot.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.constant.CommandNames;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.controller.tool.UiEntityUtil;
import sportcoursesbot.controller.tool.keyboard.ButtonCallback;
import sportcoursesbot.controller.tool.keyboard.ButtonConfig;
import sportcoursesbot.controller.tool.keyboard.KeyboardUtil;
import sportcoursesbot.service.ServiceFactory;
import sportcoursesbot.service.course.CourseService;
import sportcoursesbot.shared.entity.Course;
import sportcoursesbot.shared.entity.User;
import sportcoursesbot.shared.entity.security.Role;
import sportcoursesbot.shared.tool.ExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowFullCourse implements Command {
    private static final String DELETE_COURSE = "Delete Course";
    private static final String Edit_COURSE = "Edit Course";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private CourseService service = ServiceFactory.getCourseService();

    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {
        Long chatId = ChatUtil.readChatId(update);
        UserSession session = SessionManager.getSession(chatId);
        User user = session.getUser();
        String status = user.getStatus();
        Role role = Role.getByName(status);
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String data = callbackQuery.getData();
        try {
            ButtonCallback<Integer> callback = OBJECT_MAPPER.readValue(data, new TypeReference<ButtonCallback<Integer>>() {
            });
            Course fullCourse = service.getFullCourse(callback.getValue());
            String fullCourseString = UiEntityUtil.courseToFullString(fullCourse);
            ChatUtil.sendMessage(fullCourseString, update, source);
            session.setNewCourse(fullCourse);
            if (Role.ADMIN.equals(role)) {
                List<InlineKeyboardButton> button1 = Arrays.asList(
                        new InlineKeyboardButton(DELETE_COURSE).setCallbackData(CommandNames.DELETE_COURSE)
                );
                List<InlineKeyboardButton> button2 = Arrays.asList(
                        new InlineKeyboardButton(Edit_COURSE).setCallbackData(CommandNames.EDIT_COURSE)
                );
                List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
                keyboard.add(button1);
                keyboard.add(button2);
                InlineKeyboardMarkup markup = new InlineKeyboardMarkup(keyboard);
                String message = "Course settings:";
                ChatUtil.sendMessageWithMarkup(message, update, source, markup);
            }
        } catch (JsonProcessingException e) {
            ExceptionHandler.printAndThrowRuntime(e);
        }
    }
}
