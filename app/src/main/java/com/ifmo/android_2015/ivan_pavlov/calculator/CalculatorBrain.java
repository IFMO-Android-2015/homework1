package com.ifmo.android_2015.ivan_pavlov.calculator;

import android.util.Log;

import java.io.Serializable;
import java.util.Stack;

/**
 * Created by ivan_pavlov on 22.10.15.
 */

public class CalculatorBrain implements Serializable {

    enum valueType {
        Operand, Operation;
    }

    interface Stackable {
        valueType type();
    }

    class Operand implements Stackable {
        double value;
        Operand(double value) {
            this.value = value;
        }

        @Override
        public valueType type() {
            return valueType.Operand;
        }
    }

    class Operation implements Stackable {
        char operation;
        Operation(char operation) {
            this.operation = operation;
        }

        @Override
        public valueType type() {
            return valueType.Operation;
        }
    }

    Stack<Stackable> data = new Stack<>();

    double _evaluate() {
        if(this.data.size() > 0) {
            Stackable top = this.data.pop();
            switch (top.type()) {
                case Operand:
                    Operand operand = (Operand) top;
                    return operand.value;
                case Operation:
                    Operation operation = (Operation) top;
                    if(operation.operation == '±')
                        return -evaluate();
                    double a = evaluate();
                    double b = evaluate();
                    switch (operation.operation) {
                        case '+':
                            return b + a;
                        case '-':
                            return b - a;
                        case '×':
                            return b * a;
                        case '/':
                            return b / a;
                        case '%':
                            return b / a;
                    }
            }
        }
        Log.e("Calculator Brain error", "Empty stack");
        return 0;
    }

    public double evaluate() {
        return this._evaluate();
    }

    public void clear() {
        data.clear();
    }

    public void pushOperation(char op) {
        data.push(new Operation(op));
    }

    public void pushOperand(Double value){
        data.push(new Operand(value));
    }
}
