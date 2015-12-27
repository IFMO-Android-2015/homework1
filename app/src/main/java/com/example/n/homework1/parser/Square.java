package com.example.n.homework1.parser;

public class Square<T extends GenericNumber<T>> extends UnaryOperation<T> {
    protected Square(GenericExpression<T> arg) {
        super(arg);
    }

    protected T operation(T arg) {
        try {
            return arg.square();
        } catch (Exception e) {
            return null;
        }
    }
}
