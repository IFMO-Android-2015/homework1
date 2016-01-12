package ru.ifmo.android_2015.calculator;

/**
 * @author Andreikapolin
 * @date 12.01.16
 */
public class State {
    private static Double value = null;
    private static OpCode code = null;

    public static Double getValue() {
        return value;
    }

    public static void setValue(Double value) {
        State.value = value;
    }

    public static OpCode getCode() {
        return code;
    }

    public static void setCode(OpCode code) {
        State.code = code;
    }
}
