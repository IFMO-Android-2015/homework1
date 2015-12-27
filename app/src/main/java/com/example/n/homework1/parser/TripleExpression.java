package com.example.n.homework1.parser;

public interface TripleExpression<T extends GenericNumber> {
    T evaluate(T x, T y, T z);
}