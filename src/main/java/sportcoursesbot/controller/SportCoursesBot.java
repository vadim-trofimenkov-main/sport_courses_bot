package sportcoursesbot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.base.SessionManager;
import sportcoursesbot.controller.base.UserSession;
import sportcoursesbot.controller.exception.UnknownCommandException;
import sportcoursesbot.controller.tool.chat.ChatUtil;
import sportcoursesbot.controller.tool.keyboard.ButtonCallback;
import sportcoursesbot.controller.constant.CommandNames;
import sportcoursesbot.controller.constant.Messages;
import sportcoursesbot.shared.entity.User;
import sportcoursesbot.shared.entity.security.Role;
import sportcoursesbot.shared.exception.UserFriendlyException;

public class SportCoursesBot extends TelegramLongPollingBot {
    public static final String SLASH = "/";
    private final TaskManager TASK_MANAGER = new TaskManager();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        ApiContextInitializer.init();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (initUser(update)) {
                if (update.hasCallbackQuery()) {
                    implCallback(update);
                } else {
                    implInput(update);
                }
            }
        } catch (UserFriendlyException e) {
            e.printStackTrace();
            ChatUtil.sendMessage(e.getMessage(), update, this);
        } catch (Exception e) {
            e.printStackTrace();
            ChatUtil.sendMessage("Server error", update, this);
        }
    }

    private void implCallback(Update update) throws TelegramApiException, JsonProcessingException {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String data = callbackQuery.getData();
        if (data.startsWith(SLASH)) {
            TaskManager.executeByName(data, this, update);
        } else {
            ButtonCallback<?> buttonCallback = objectMapper.readValue(data, new TypeReference<ButtonCallback<?>>() {
            });
            String command = buttonCallback.getCommand();
            TaskManager.executeByName(command, this, update);
        }
    }

    private void implInput(Update update) throws TelegramApiException {
        String input = getInput(update);
        TASK_MANAGER.impl(input, update, this);
    }

    private boolean initUser(Update update) throws TelegramApiException {
        UserSession session = SessionManager.getSession(update);
        if (session != null) {
            if (!checkUser(update, session)) {
                return false;
            } else {
                TaskManager.executeByName(CommandNames.REFRESH_USER_STATUS, this, update);
                return !checkBlocked(update, session);
            }
        } else {
            TaskManager.executeByName(CommandNames.INITIALIZE_USER_SESSION, this, update);
            return SessionManager.userOk(update);
        }
    }

    private boolean checkBlocked(Update update, UserSession session) throws TelegramApiException {
        User user = session.getUser();
        if (Role.BLOCKED.equals(user.getRole())) {
            ChatUtil.sendMessage(Messages.YOU_ARE_BLOCKED, update, this);
            return true;
        }
        return false;
    }

    private boolean checkUser(Update update, UserSession session) throws TelegramApiException {
        if (!SessionManager.userOk(session)) {
            TaskManager.executeByName(CommandNames.NEW_USER, this, update);
            TaskManager.executeByName(CommandNames.INITIALIZE_USER_SESSION, this, update);
            return false;
        }
        return true;
    }


    private String getInput(Update update) {
        String input;
        if (update.hasMessage()) {
            input = update.getMessage().getText();
        } else {
            throw new UnknownCommandException();
        }
        return input;
    }

    @Override
    public String getBotUsername() {
        return "SportCourseBot";
    }

    @Override
    public String getBotToken() {
        return "1162262582:AAE3xoL7T5tGuu-2ccKa7ORLaiQGCjcp9L8";
    }

    public static void main(String[] args) {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            SportCoursesBot sportCoursesBot = new SportCoursesBot();
            telegramBotsApi.registerBot(sportCoursesBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
