package sportcoursesbot.shared.tool.validate;

import lombok.Data;

public class ValidatorWrapper<T> {
    private static final String INCORRECT_DATA = "Incorrect data";
    private Validator<T> validator;
    private String failMessage;

    public ValidatorWrapper(Validator<T> validator, String failMessage) {
        this.validator = validator;
        this.failMessage = failMessage;
    }

    public ValidatorWrapper(Validator<T> validator) {
        this.validator = validator;
        this.failMessage = INCORRECT_DATA;
    }

    public void validateWithThrow(T toValidate) {
        if (!validator.validate(toValidate)) {
            throw new ValidationException(failMessage);
        }
    }

    public ValidationResult validateWithResult(T toValidate) {
        return validator.validate(toValidate) ? new ValidationResult()
                : new ValidationResult(new ValidationException(failMessage));
    }


    @Data
    public static class ValidationResult {
        private boolean success;
        private ValidationException exception;

        public ValidationResult(ValidationException exception) {
            this.exception = exception;
            success = false;
        }

        public ValidationResult() {
            success = true;
        }
    }
}
