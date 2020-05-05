package sportcoursesbot.shared.exception;

public class PermissionDeniedException extends UserFriendlyException {
    public PermissionDeniedException() {
        super("Permission denied");
    }
}
