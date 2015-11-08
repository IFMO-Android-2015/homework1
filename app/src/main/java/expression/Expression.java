package expression;

import expression.types.MyNumber;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */

public interface Expression<T extends MyNumber<T>> {
    T evaluate();
}