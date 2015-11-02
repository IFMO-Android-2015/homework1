package expression.exceptions;

/**
 * Created by daniil on 30.03.15.
 */
public abstract class ParserException extends Exception {
    public ParserException(String message) {
        super(message);
    }
}
