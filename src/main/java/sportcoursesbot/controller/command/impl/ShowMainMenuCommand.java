package sportcoursesbot.controller.command.impl;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.command.Command;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.controller.constant.CommandNames;
import sportcoursesbot.shared.entity.User;
import sportcoursesbot.shared.entity.security.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowMainMenuCommand implements Command {
    private static final String ALL_COURSES = "All courses";
    private static final String MY_COURSES = "My courses";
    private static final String MANAGE_USERS = "Manage users";
    private static final String ALL_COACHES = "Our Coaches";

    @Override
    public void execute(SportCoursesBot source, Update update) throws TelegramApiException {

        List<InlineKeyboardButton> buttons1 = Arrays.asList(
                new InlineKeyboardButton(MY_COURSES).setCallbackData(CommandNames.SHOW_MY_COURSES)
        );
        List<InlineKeyboardButton> buttons2 = Arrays.asList(
                new InlineKeyboardButton(ALL_COURSES).setCallbackData(CommandNames.SHOW_ALL_COURSES)
        );
        List<InlineKeyboardButton> buttons3 = Arrays.asList(
                new InlineKeyboardButton(ALL_COACHES).setCallbackData(CommandNames.SHOW_ALL_COACHES)
        );
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttons1);
        keyboard.add(buttons2);
        keyboard.add(buttons3);

        Long chatId = ChatUtil.readChatId(update);
        UserSession session = SessionManager.getSession(chatId);
        User user = session.getUser();
        String status = user.getStatus();
        Role role = Role.getByName(status);
        if (Role.ADMIN.equals(role)) {
            List<InlineKeyboardButton> buttons10 = Arrays.asList(
                    new InlineKeyboardButton(MANAGE_USERS).setCallbackData(CommandNames.SHOW_USERS_FOR_ADMIN));
            keyboard.add(buttons10);
        }

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(keyboard);

        String message = "Hello, " + user.getUsername();
        ChatUtil.sendMessageWithMarkup(message, update, source, markup);
    }
}
