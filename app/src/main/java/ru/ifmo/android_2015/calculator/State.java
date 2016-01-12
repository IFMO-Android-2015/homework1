package ru.ifmo.android_2015.calculator;

/**
 * @author creed
 * @date 02.12.15
 */
public class State {
    private static Double value = null;
    private static OperationCode code = null;

    public static Double getValue() {
        return value;
    }

    public static void setValue(Double value) {
        State.value = value;
    }

    public static OperationCode getCode() {
        return code;
    }

    public static void setCode(OperationCode code) {
        State.code = code;
    }
}
