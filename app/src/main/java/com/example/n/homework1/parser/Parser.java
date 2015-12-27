package com.example.n.homework1.parser;

public interface Parser<T extends GenericNumber<T>> {
    GenericExpression<T> parse(String expression, T value) throws Exception;
}