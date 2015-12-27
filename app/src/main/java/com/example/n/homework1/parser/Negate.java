package com.example.n.homework1.parser;

public class Negate<T extends GenericNumber<T>> extends UnaryOperation<T> {
    protected Negate(GenericExpression<T> arg) {
        super(arg);
    }

    protected T operation(T arg) {
        try {
            return arg.negate();
        } catch (Exception e) {
            return null;
        }
    }
}