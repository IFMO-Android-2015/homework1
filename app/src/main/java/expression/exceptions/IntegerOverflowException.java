package expression.exceptions;

/**
 * Created by daniil on 30.03.15.
 */
public class IntegerOverflowException extends EvaluationException {
    public IntegerOverflowException(String message) {
        super(message);
    }
}
