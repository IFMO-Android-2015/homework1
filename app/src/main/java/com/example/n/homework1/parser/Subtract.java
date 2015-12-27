package com.example.n.homework1.parser;

public class Subtract<T extends GenericNumber<T>> extends BinaryOperation<T> {
    protected Subtract(GenericExpression<T> first, GenericExpression<T> second) {
        super(first, second);
    }

    protected T operation(T first, T second) {
        try {
            return first.subtract(second);
        } catch (Exception e) {
            return null;
        }
    }
}
