package com.example.n.homework1.parser;

public abstract class GenericNumber<T extends GenericNumber<T>> {
    protected abstract Number value();

    protected abstract T add(T second);

    protected abstract T divide(T second);

    protected abstract T getObject();

    protected abstract T negate();

    protected abstract T multiply(T second);

    protected abstract T parse(String s);

    protected abstract T abs();

    protected T square() {
        return multiply(getObject());
    }

    protected T mod(T second) {
        return this.subtract(second.multiply(this.divide(second)));
    }

    protected T subtract(T second) {
        return add(second.negate());
    }

    protected abstract T newGeneric(int value);
}
