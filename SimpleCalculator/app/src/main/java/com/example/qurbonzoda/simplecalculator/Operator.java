package com.example.qurbonzoda.simplecalculator;

/**
 * Created by qurbonzoda on 26.10.15.
 */
public enum Operator {
    ADD('+') {
        @Override
        public double apply(final double leftOperand, final double rightOperand) {
            return leftOperand + rightOperand;
        }
    },
    SUB('-') {
        @Override
        public double apply(final double leftOperand, final double rightOperand) {
            return leftOperand - rightOperand;
        }
    },
    MUL('*') {
        @Override
        public double apply(final double leftOperand, final double rightOperand) {
            return leftOperand * rightOperand;
        }
    },
    DIV('/') {
        @Override
        public double apply(final double leftOperand, final double rightOperand) {
            return leftOperand / rightOperand;
        }
    },
    POW('^') {
        @Override
        public double apply(final double leftOperand, final double rightOperand) {
            return (leftOperand < 0 ? -1 : 1) * Math.pow(Math.abs(leftOperand), rightOperand);
        }
    };
    private final char code;

    private Operator(final char code) {
        this.code = code;
    }

    public static Operator buildOperator(final char code) {
        for (Operator operator: Operator.values()) {
            if (code == operator.code) {
                return operator;
            }
        }
        return null;
    }

    public boolean isOperatorEquals(final Operator... operators) {
        for (Operator operator: operators) {
            if (this.equals(operator)) {
                return true;
            }
        }
        return false;
    }

    public abstract double apply(final double leftOperand, final double rightOperand);
}