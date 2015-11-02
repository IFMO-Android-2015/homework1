package expression;

/**
 * Created by daniil on 25.03.15.
 */

import expression.types.MyNumber;

public class CheckedABS<T extends MyNumber<T>> extends AbstractUnaryOperation<T> {
    public CheckedABS(Expression<T> right) {
        super(right);
    }

    @Override
    public T count(T a) {
        return a.abs();
    }
}
