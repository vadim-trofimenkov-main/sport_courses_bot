package sportcoursesbot.shared.tool;

public class ExceptionHandler {
    public static void printAndThrowRuntime(Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
    }
}
