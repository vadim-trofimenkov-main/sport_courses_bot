package sportcoursesbot.controller.tool.keyboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import sportcoursesbot.shared.tool.ExceptionHandler;

import java.util.Objects;
import java.util.function.Function;

public class ButtonWithObject<T extends ButtonCallback<?>> extends InlineKeyboardButton {
    private Function<T, String> serializer;
    private T callback;

    public ButtonWithObject() {
        serializer = new JsonSerializer<>();
    }

    public ButtonWithObject(String text) {
        super(text);
        serializer = new JsonSerializer<>();
    }

    public ButtonWithObject(Function<T, String> serializer) {
        setSerializerOrDefault(serializer);
    }

    public ButtonWithObject(String text, Function<T, String> serializer) {
        super(text);
        setSerializerOrDefault(serializer);
    }

    private void setSerializerOrDefault(Function<T, String> serializer) {
        if (Objects.nonNull(serializer)) {
            this.serializer = serializer;
        } else {
            this.serializer = new JsonSerializer<>();
        }
    }

    public ButtonWithObject<T> setCallbackData(T callback) {
        String callbackData = serializer.apply(callback);
        return (ButtonWithObject<T>) super.setCallbackData(callbackData);
    }

    private static class JsonSerializer<T extends ButtonCallback<?>> implements Function<T, String> {
        private static ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String apply(T buttonCallback) {
            try {
                String callbackData = objectMapper.writeValueAsString(buttonCallback);
                return callbackData;
            } catch (JsonProcessingException e) {
                ExceptionHandler.printAndThrowRuntime(e);
            }
            return null;
        }
    }
}
