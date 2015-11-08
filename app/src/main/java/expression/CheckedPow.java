package expression;

import expression.types.MyNumber;

public class CheckedPow<T extends MyNumber<T>> extends AbstractBinOperation<T> {
    public CheckedPow(Expression<T> left, Expression<T> right) {
        super(left, right);
    }

    public T count(T a, T b) {
        return a.pow(b);
    }
}