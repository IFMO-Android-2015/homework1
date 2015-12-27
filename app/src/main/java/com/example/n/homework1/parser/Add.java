package com.example.n.homework1.parser;

public class Add<T extends GenericNumber<T>> extends BinaryOperation<T> {
    protected Add(GenericExpression<T> first, GenericExpression<T> second) {
        super(first, second);
    }

    protected T operation(T first, T second) {
        try {
            return first.add(second);
        } catch (Exception e) {
            return null;
        }
    }
}
