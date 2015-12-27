package com.example.n.homework1.parser;

public class Mod<T extends GenericNumber<T>> extends BinaryOperation<T> {
    protected Mod(GenericExpression<T> first, GenericExpression<T> second) {
        super(first, second);
    }

    protected T operation(T first, T second) {
        try {
            return first.mod(second);
        } catch (Exception e) {
            return null;
        }
    }
}
