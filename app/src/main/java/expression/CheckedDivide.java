package expression;

/**
 * Created by daniil on 25.03.15.
 */

import expression.types.MyNumber;

public class CheckedDivide<T extends MyNumber<T>> extends AbstractBinOperation<T> {
    public CheckedDivide(Expression<T> left, Expression<T> right) {
        super(left, right);
    }

    public T count(T a, T b) {
        return a.divide(b);
    }
}