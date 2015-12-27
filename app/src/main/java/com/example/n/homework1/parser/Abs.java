package com.example.n.homework1.parser;

public class Abs<T extends GenericNumber<T>> extends UnaryOperation<T> {
    protected Abs(GenericExpression<T> arg) {
        super(arg);
    }

    protected T operation(T arg) {
        try {
            return arg.abs();
        } catch (Exception e) {
            return null;
        }
    }
}