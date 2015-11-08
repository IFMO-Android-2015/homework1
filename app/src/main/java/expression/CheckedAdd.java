package expression;

import expression.types.MyNumber;

/**
 * Created by daniil on 25.03.15.
 */
public class CheckedAdd<T extends MyNumber<T>> extends AbstractBinOperation<T> {
    public CheckedAdd(Expression<T> left, Expression<T> right) {
        super(left, right);
    }

    public T count(T a, T b) {
        return a.add(b);
    }
}