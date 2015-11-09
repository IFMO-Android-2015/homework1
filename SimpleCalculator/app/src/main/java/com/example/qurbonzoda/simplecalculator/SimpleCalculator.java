package com.example.qurbonzoda.simplecalculator;

/**
 * Created by qurbonzoda on 16.10.15.
 */

class SimpleCalculator implements Calculator {
    private int pos, length;
    private char curChar;
    private String expression;

    @Override
    public double calculate(final String expression) {
        this.expression = expression.replace(" ", "").toLowerCase();
        length = this.expression.length();
        pos = 0;
        readNextChar();
        return calcExpression();
    }

    private void readNextChar() {
        curChar = (pos >= length ? '\0' : expression.charAt(pos++));
    }

    private double calcExpression() {
        double result = calcSummand();
        Operator operator = Operator.buildOperator(curChar);
        while (operator != null && operator.isOperatorEquals(Operator.ADD, Operator.SUB)) {
            readNextChar();
            result = operator.apply(result, calcSummand());
            operator = Operator.buildOperator(curChar);
        }
        return result;
    }

    private double calcSummand() {
        double result = calcFactor();
        Operator operator = Operator.buildOperator(curChar);
        while (operator != null && operator.isOperatorEquals(Operator.MUL, Operator.DIV)) {
            readNextChar();
            result = operator.apply(result, calcFactor());
            operator = Operator.buildOperator(curChar);
        }
        return result;
    }

    private double calcFactor() {
        double result = calcUnary();
        Operator operator = Operator.buildOperator(curChar);
        while (operator != null && operator.isOperatorEquals(Operator.POW)) {
            readNextChar();
            result = operator.apply(result, calcFactor());
            operator = Operator.buildOperator(curChar);
        }
        return result;
    }

    private double calcUnary() {
        double result = 0.0;
        if (Character.isDigit(curChar)) {
            result = readNumber();
            readNextChar();
        } else if (isSign(curChar)) {
            boolean isNegative = (curChar == '-');
            readNextChar();
            result = (isNegative ? -1 : 1) * calcUnary();
        }
        return result;
    }

    private double readNumber() {
        int leftNumBorder = pos - 1;
        int rightNumBorder = -1;
        for (int i = leftNumBorder; i < length; i++) {
            if (expression.charAt(i) != '.' && !Character.isDigit(expression.charAt(i))) {
                rightNumBorder = i;
                break;
            }
        }
        if (rightNumBorder == -1) {
            rightNumBorder = length;
        }
        pos = rightNumBorder;
        return Double.parseDouble(expression.substring(leftNumBorder, rightNumBorder));
    }

    private boolean isSign(final char c) {
        return (c == '+' || c == '-');
    }
}
