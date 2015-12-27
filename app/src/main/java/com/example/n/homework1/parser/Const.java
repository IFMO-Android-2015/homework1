package com.example.n.homework1.parser;

public class Const<T extends GenericNumber<T>> extends GenericExpression<T> {

    private final T Const;


    public Const(T Const) {
        this.Const = Const;
    }

    public T evaluate(T x, T y, T z) {
        return Const;
    }

}