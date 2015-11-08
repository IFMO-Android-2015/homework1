package expression;

import expression.types.MyNumber;

/**
 * Created by daniil on 25.03.15.
 */
public abstract class AbstractUnaryOperation<T extends MyNumber<T>> implements Expression<T> {
    private Expression<T> right;

    public AbstractUnaryOperation(Expression<T> right) {
        this.right = right;
    }

    public abstract T count(T a);

    public T evaluate() {
        T a = right.evaluate();
        return count(a);
    }
}
