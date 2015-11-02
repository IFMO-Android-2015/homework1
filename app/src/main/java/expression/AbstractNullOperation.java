package expression;

import expression.types.MyNumber;

/**
 * Created by daniil on 25.03.15.
 */
public abstract class AbstractNullOperation<T extends MyNumber<T>> implements Expression<T> {
    public T evaluate(int x, int y, int z) {
        return this.evaluate(x, y, z);
    }
}
