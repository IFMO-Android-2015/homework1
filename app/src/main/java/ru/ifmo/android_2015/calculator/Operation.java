package ru.ifmo.android_2015.calculator;

import android.support.annotation.NonNull;

/**
 * @author Andreikapolin
 * @date 12.01.16
 */
public class Operation implements OperationInterface {

    private OpCode code;

    private Operation(@NonNull OpCode code) {
        this.code = code;
    }

    public static Operation create(@NonNull OpCode code) {
        return new Operation(code);
    }

    @Override
    public double execute(double a, double b)
            throws UnsupportedOperationException, IllegalArgumentException {
        if (code == OpCode.SUM) {
            return a + b;
        } else if (code == OpCode.SUBTRACT) {
            return a - b;
        } else if (code == OpCode.MULTIPLY) {
            return a * b;
        } else if (code == OpCode.DIVIDE) {
            if (b == 0) {
                throw new IllegalArgumentException("Second argument is equal to 0");
            }
            return a / b;
        }
        throw new UnsupportedOperationException("UnsupportedOperation");
    }
}
