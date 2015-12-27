package com.example.n.homework1.parser;

public class Multiply<T extends GenericNumber<T>> extends BinaryOperation<T> {
    protected Multiply(GenericExpression<T> first, GenericExpression<T> second) {
        super(first, second);
    }

    protected T operation(T first, T second) {
        try {
            return first.multiply(second);
        } catch (Exception e) {
            return null;
        }
    }
}