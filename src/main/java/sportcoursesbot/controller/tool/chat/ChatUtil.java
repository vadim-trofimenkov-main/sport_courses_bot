package sportcoursesbot.controller.tool.chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sportcoursesbot.controller.SportCoursesBot;
import sportcoursesbot.shared.exception.UserFriendlyException;

import java.util.Scanner;

public class ChatUtil {
    public static Long readChatId(Update update) {
        Long chatId = null;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }
        if (chatId == null) {
            throw new RuntimeException();
        }
        return chatId;
    }
    public static String readChatText(Update update)  {
        String chatText;
        CallbackQuery callbackQuery = update.getCallbackQuery();
        chatText = callbackQuery.getData();
        if (chatText.isEmpty()) {
            throw new UserFriendlyException("Нет текста");
        }
        return chatText;
    }

    public static void sendMessage(String text, Long chatId, SportCoursesBot source) throws TelegramApiException {
        SendMessage message = new SendMessage(chatId, text);
        source.execute(message);
    }

    public static void sendMessage(String text, Update update, SportCoursesBot source) throws TelegramApiException {
        Long chatId = readChatId(update);
        sendMessage(text, chatId, source);
    }

    public static void sendMessageWithMarkup(String text, Update update, SportCoursesBot source, InlineKeyboardMarkup markup) throws TelegramApiException {
        Long chatId = readChatId(update);
        SendMessage sendMessage = new SendMessage(chatId, text).setReplyMarkup(markup);
        source.execute(sendMessage);
    }
}
