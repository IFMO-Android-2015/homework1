package com.example.n.homework1.parser;

public class Divide<T extends GenericNumber<T>> extends BinaryOperation<T> {
    protected Divide(GenericExpression<T> first, GenericExpression<T> second) {
        super(first, second);
    }

    protected T operation(T first, T second) {
        try {
            return first.divide(second);
        } catch (Exception e) {
            return null;
        }
    }
}