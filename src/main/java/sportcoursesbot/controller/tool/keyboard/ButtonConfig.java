package sportcoursesbot.controller.tool.keyboard;

import lombok.Data;

@Data
public class ButtonConfig {
    private String message;
    private ButtonCallback<?> callback;
    private boolean lastInRow = false;

    public ButtonConfig(String message, ButtonCallback<?> callback) {
        this.message = message;
        this.callback = callback;
    }

    public ButtonConfig(String message, ButtonCallback<?> callback, boolean lastInRow) {
        this.message = message;
        this.callback = callback;
        this.lastInRow = lastInRow;
    }
}
