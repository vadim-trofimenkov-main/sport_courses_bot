package sportcoursesbot.controller.tool.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KeyboardUtil {
    public static InlineKeyboardMarkup buildMarkup(List<ButtonConfig> configs) {
        List<List<InlineKeyboardButton>> panel = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        for (ButtonConfig config : configs) {
            String message = config.getMessage();
            ButtonCallback<?> callback = config.getCallback();

            ButtonWithObject<ButtonCallback<?>> button = new ButtonWithObject<>(message).setCallbackData(callback);
            buttons.add(button);
            if (config.isLastInRow()) {
                panel.add(buttons);
                buttons = new ArrayList<>();
            }
        }
        if (!buttons.isEmpty()) {
            panel.add(buttons);
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(panel);
        return markup;
    }

    public static <T> List<ButtonConfig> buildConfigs(Collection<T> values, String commandName, Function<? super T, ?> dataConverter,
                                                      Function<? super T, String> messageConverter) {
        List<ButtonConfig> collect = values.stream()
                .map(n -> {
                    Object value = dataConverter.apply(n);
                    ButtonCallback<Object> callback = new ButtonCallback<>(commandName, value);
                    String message = messageConverter.apply(n);
                    ButtonConfig config = new ButtonConfig(message, callback, true);
                    return config;
                })
                .collect(Collectors.toList());
        return collect;
    }
}
