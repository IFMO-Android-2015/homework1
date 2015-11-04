package com.ifmo.android_2015.ivan_pavlov.calculator;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by ivan_pavlov on 22.10.15.
 */

public class CalculatorBrain implements Parcelable {

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
        @Override public String toString() {
            return Double.toString(value);
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

        @Override public String toString() {
            return Character.toString(operation);
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

    protected CalculatorBrain () {
        //empty init.
    }

    //Parcelable implementation.
    protected CalculatorBrain(Parcel in) {
        ArrayList<String> brain = new ArrayList<>();
        this.data.clear();
        in.readStringList(brain);
        for(String s: brain) {
            try {
                data.add(new Operand(Double.parseDouble(s)));
            } catch (Exception e) {
                data.add(new Operation(s.charAt(0)));
            }
        }
    }

    public static final Creator<CalculatorBrain> CREATOR = new Creator<CalculatorBrain>() {
        @Override
        public CalculatorBrain createFromParcel(Parcel in) {
            return new CalculatorBrain(in);
        }

        @Override
        public CalculatorBrain[] newArray(int size) {
            return new CalculatorBrain[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ArrayList<String> brain = new ArrayList<>();
        for(Stackable op : this.data)
            brain.add(op.toString());
        dest.writeStringList(brain);
    }
}
