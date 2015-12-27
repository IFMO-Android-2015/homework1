package com.example.n.homework1.parser;

public class Variable<T extends GenericNumber<T>> extends GenericExpression<T> {

    private final String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    public T evaluate(T x, T y, T z) {
        switch (variable) {
            case "x": {
                return x;
            }
            case "y": {
                return y;
            }
            default: {
                return z;
            }
        }
    }

}