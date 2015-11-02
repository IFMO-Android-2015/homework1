package expression;

import expression.types.MyNumber;

/**
 * Created by daniil on 29.03.15.
 */
public class CheckedNegate<T extends MyNumber<T>> extends AbstractUnaryOperation<T> {
    public CheckedNegate(Expression<T> right) {
        super(right);
    }

    public T count(T a) {
        return a.negate();
    }
}
