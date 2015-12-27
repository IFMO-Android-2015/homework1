package com.example.n.homework1.parser;

public abstract class BinaryOperation<T extends GenericNumber<T>> extends GenericExpression<T> {

    private final GenericExpression<T> first;
    private final GenericExpression<T> second;

    protected BinaryOperation(GenericExpression<T> first, GenericExpression<T> second) {
        this.first = first;
        this.second = second;
    }

    protected abstract T operation(T first, T second);

    public T evaluate(T x, T y, T z) {
        return operation(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}