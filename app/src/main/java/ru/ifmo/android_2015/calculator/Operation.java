package ru.ifmo.android_2015.calculator;

import android.support.annotation.NonNull;

/**
 * @author creed
 * @date 02.12.15
 */
public class Operation implements OperationInterface {

    private OperationCode code;

    private Operation(@NonNull OperationCode code) {
        this.code = code;
    }

    public static Operation get(@NonNull OperationCode code) {
        return new Operation(code);
    }

    @Override
    public Double perform(Double a, Double b) throws UnsupportedOperationException {
        if (code == OperationCode.SUM) {
            return a + b;
        } else if (code == OperationCode.SUBTRACT) {
            return a - b;
        } else if (code == OperationCode.MULTIPLY) {
            return a * b;
        } else if (code == OperationCode.DIVIDE) {
            return a / b;
        }
        throw new UnsupportedOperationException("UnsupportedOperation");
    }
}
