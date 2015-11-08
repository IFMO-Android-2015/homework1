package expression.types;

/**
 * Created by daniil on 12.04.15.
 */
public abstract class MyNumber<T> {
    public abstract T add(T b);

    public abstract T divide(T b);

    public abstract T multiply(T b);

    public abstract T negate();

    public abstract T subtract(T b);

    public abstract T abs();

    public abstract T pow(T b);

    public abstract T square();

    public abstract T newNumber(String s);

    public abstract String toString();
}
