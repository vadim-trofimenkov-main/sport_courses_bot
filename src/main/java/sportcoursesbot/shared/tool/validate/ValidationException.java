package sportcoursesbot.shared.tool.validate;
import sportcoursesbot.shared.exception.UserFriendlyException;

public class ValidationException extends UserFriendlyException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
