package com.example.n.homework1.parser;

public abstract class UnaryOperation<T extends GenericNumber<T>> extends GenericExpression<T> {

    private final GenericExpression<T> arg;

    protected UnaryOperation(GenericExpression<T> arg) {
        this.arg = arg;
    }

    protected abstract T operation(T arg);

    public T evaluate(T x, T y, T z) {
        return operation(arg.evaluate(x, y, z));
    }

}
