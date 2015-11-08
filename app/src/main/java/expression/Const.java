package expression;

import expression.types.MyNumber;

/**
 * Created by daniil on 25.03.15.
 */
public class Const<T extends MyNumber<T>> extends AbstractNullOperation<T> {
    T value;

    public Const(T x) {
        value = x;
    }

    @Override
    public T evaluate() {
        return value;
    }
}