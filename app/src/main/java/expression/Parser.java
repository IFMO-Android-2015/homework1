package expression;

import expression.types.MyNumber;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser<T extends MyNumber<T>> {
    Expression<T> parse(String expression) throws Exception;
}
