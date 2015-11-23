package ru.ifmo.android_2015.activitylifecycle.ui;

import java.math.BigDecimal;

/*
 * Класс сделан для того, чтобы отделить подсчет выражения от view
 */

public class Expression {
    String op1, op2;
    Sign currentSign;

    public enum Sign { PLUS, SUB, MUL, DIV }

    Expression() {
        reset();
    }

    void reset() {
        op1 = "0";
        op2 = "";
        currentSign = Sign.PLUS;
    }

    // возвращает BigDecimal - что если вместо знака =, мы нажали
    // какой-то другой? нам нужно отобразить получившиеся значение
    BigDecimal addSign(Sign sign) {
        currentSign = sign;
        return evaluate();
    }

    // добавить очередную цифру к выражению
    void addDigit(char digit) {
        // если мы начали вводить очередное число после того, как
        // нажали =, нам нужно сбросить все выражение
        if (op1 != "") reset();
        op2 += digit;
    }

    // добавить запятую к выражению
    void addComma() {
        if (op2 == "") op2 = "0,";
        else op2 += ',';
    }

    // вычислить выражение
    BigDecimal evaluate() {
        BigDecimal res = null;
        BigDecimal bigDecimalOp1 = new BigDecimal(op1);
        BigDecimal bigDecimalOp2 = new BigDecimal(op2);

        switch (currentSign) {
            case DIV:
                res = bigDecimalOp1.divide(bigDecimalOp2);
                break;
            case MUL:
                res = bigDecimalOp1.multiply(bigDecimalOp2);
                break;
            case PLUS:
                res = bigDecimalOp1.add(bigDecimalOp2);
                break;
            case SUB:
                res = bigDecimalOp1.subtract(bigDecimalOp2);
                break;
        }

        op1 = res.toString();
        return res;
    }
}
