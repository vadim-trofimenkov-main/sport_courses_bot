package sportcoursesbot.controller.tool.keyboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ButtonCallback<T> {
    private String command;
    private T value;

    private ButtonCallback() {
    }
}
