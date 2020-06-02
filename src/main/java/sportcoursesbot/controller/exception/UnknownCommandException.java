package sportcoursesbot.controller.exception;

import sportcoursesbot.shared.exception.UserFriendlyException;

public class UnknownCommandException extends UserFriendlyException {
    public UnknownCommandException() {
        super("Unknown Command.");
    }
}
