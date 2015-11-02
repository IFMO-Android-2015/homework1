package expression;

import expression.types.MyNumber;

/**
 * Created by daniil on 25.03.15.
 */
public class CheckedSquare<T extends MyNumber<T>> extends AbstractUnaryOperation<T> {
    public CheckedSquare(Expression<T> right) {
        super(right);
    }

    public T count(T a) {
        return a.square();
    }
}
