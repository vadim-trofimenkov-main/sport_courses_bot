package sportcoursesbot.shared.tool.validate;

public interface Validator<T> {
    boolean validate(T parameter);
}
