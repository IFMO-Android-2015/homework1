package com.example.n.homework1.parser;


public class GenericDouble extends GenericNumber<GenericDouble> {
    private Double first;

    public GenericDouble(Double value) {
        this.first = value;
    }

    public Double value() {
        return first;
    }

    protected GenericDouble add(GenericDouble second) {
        if (this.value() == null || second.value() == null) {
            return new GenericDouble(null);
        }
        return new GenericDouble(first + second.value());
    }

    protected GenericDouble divide(GenericDouble second) {
        if (this.value() == null || second.value() == null) {
            return new GenericDouble(null);
        }
        return new GenericDouble(first / second.value());

    }

    protected GenericDouble multiply(GenericDouble second) {
        if (this.value() == null || second.value() == null) {
            return new GenericDouble(null);
        }
        return new GenericDouble(first * second.value());
    }

    protected GenericDouble negate() {
        if (this.value() == null) {
            return new GenericDouble(null);
        }
        return new GenericDouble(-first);
    }

    protected GenericDouble parse(String s) {
        return new GenericDouble(Double.parseDouble(s));
    }

    protected GenericDouble getObject() {
        return this;
    }

    protected GenericDouble abs() {
        if (this.value() == null) {
            return new GenericDouble(null);
        }
        if (first < 0) {
            return negate();
        }
        return this;
    }

    @Override
    protected GenericDouble newGeneric(int value) {
        return new GenericDouble((double) value);
    }
}
