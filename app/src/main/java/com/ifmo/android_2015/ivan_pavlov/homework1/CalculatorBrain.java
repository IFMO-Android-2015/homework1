package com.ifmo.android_2015.ivan_pavlov.homework1;

import java.io.Serializable;

/**
 * Created by ivan_pavlov on 03.11.15.
 */
public class CalculatorBrain implements Serializable {

    Double latest = null;
    String op = null;

    Double evaluate(Double a, Double b, String opSymbol) {
        switch (opSymbol) {
            case "÷":
                return a / b;
            case "+":
                return a + b;
            case "%":
                return a % b;
            case "-":
                return a - b;
            case "×":
                return a * b;
        }
        return null;
    }
}
