package expression;

import expression.types.MyNumber;

/**
 * Created by daniil on 25.03.15.
 */

public abstract class AbstractBinOperation<T extends MyNumber<T>> implements Expression<T> {
    private Expression<T> left, right;

    public AbstractBinOperation(Expression<T> left, Expression<T> right) {
        this.left = left;
        this.right = right;
    }

    public abstract T count(T a, T b);

    public T evaluate() {
        T a = left.evaluate();
        T b = right.evaluate();
        return count(a, b);
    }
}