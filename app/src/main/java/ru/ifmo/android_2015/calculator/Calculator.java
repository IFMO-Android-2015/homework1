package ru.ifmo.android_2015.calculator;

import android.support.annotation.NonNull;

/**
 * @author creed
 * @date 02.12.15
 */
public class Calculator {

    public static void doSum(@NonNull Double value) throws UnsupportedOperationException,
            IllegalStateException {
        Double state = State.getValue();
        if (state == null) throw new IllegalStateException("State is null");
        Double result = Operation.get(OperationCode.SUM).perform(state, value);
        State.setValue(result);
    }

    public static void doSubtract(@NonNull Double value) throws UnsupportedOperationException,
            IllegalStateException {
        Double state = State.getValue();
        if (state == null) throw new IllegalStateException("State is null");
        Double result = Operation.get(OperationCode.SUBTRACT).perform(state, value);
        State.setValue(result);
    }

    public static void doMultiply(@NonNull Double value) throws UnsupportedOperationException,
            IllegalStateException {
        Double state = State.getValue();
        if (state == null) throw new IllegalStateException("State is null");
        Double result = Operation.get(OperationCode.MULTIPLY).perform(state, value);
        State.setValue(result);
    }

    public static void doDivide(@NonNull Double value) throws UnsupportedOperationException,
            IllegalStateException {
        Double state = State.getValue();
        if (state == null) throw new IllegalStateException("State is null");
        Double result = Operation.get(OperationCode.DIVIDE).perform(state, value);
        State.setValue(result);
    }
}
