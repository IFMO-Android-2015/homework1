package expression.exceptions;

/**
 * Created by daniil on 31.03.15.
 */
public class UnexpectedEndOfExpressionException extends ParserException {
    public UnexpectedEndOfExpressionException(String message) {
        super(message);
    }
}