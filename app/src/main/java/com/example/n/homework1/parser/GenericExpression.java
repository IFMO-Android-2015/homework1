package com.example.n.homework1.parser;

public abstract class GenericExpression<T extends GenericNumber<T>> implements TripleExpression<T> {
    public abstract T evaluate(T x, T y, T z);
}
