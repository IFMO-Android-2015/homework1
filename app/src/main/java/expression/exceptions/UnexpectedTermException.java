package expression.exceptions;

/**
 * Created by daniil on 30.03.15.
 */
public class UnexpectedTermException extends ParserException {
    public UnexpectedTermException(String message) {
        super(message);
    }
}
