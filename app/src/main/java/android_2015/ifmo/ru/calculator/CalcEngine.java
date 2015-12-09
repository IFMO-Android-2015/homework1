package android_2015.ifmo.ru.calculator;

import android.os.Parcel;
import android.os.Parcelable;

public class CalcEngine implements Parcelable {
    double num1 = 0;
    String num2 = "";
    OperationType lastOperator = null;

    public static final Creator<CalcEngine> CREATOR = new Creator<CalcEngine>() {
        @Override
        public CalcEngine createFromParcel(Parcel in) {
            double num1 = in.readDouble();
            String num2 = in.readString();
            OperationType lastOperator = (OperationType) in.readSerializable();
            return new CalcEngine(num1, num2, lastOperator);
        }

        @Override
        public CalcEngine[] newArray(int size) {
            return new CalcEngine[size];
        }
    };

    private CalcEngine(double num1, String num2, OperationType lastOperator) {
        this.num1 = num1;
        this.num2 = num2;
        this.lastOperator = lastOperator;
    }

    public CalcEngine() {

    }

    public String getNum1() {
        return Double.toString(num1);
    }

    public String getNum2() {
        return num2;
    }

    public void addChar(Character c) {
        if (c == '.') {
            if (num2.isEmpty()) num2 = "0.";
            if (!num2.contains(".")) num2 = num2.concat(".");
        } else {
            num2 = num2.concat(c.toString());
        }
    }

    public void doOperation(OperationType op) {

        eval();
        if (op != OperationType.Eval) {
            lastOperator = op;
        }
    }

    public void clear() {
        num1 = 0;
        num2 = "";
        lastOperator = null;
    }

    public double callOperator(OperationType op, double a, double b) {
        switch (op) {
            case Minus:
                return a - b;
            case Plus:
                return a + b;
            case Div:
                return a / b;
            case Mul:
                return a * b;
        }
        return 0;
    }

    public void eval() {

        if (lastOperator != null) {
            num1 = callOperator(lastOperator, num1, Double.parseDouble(num2));
            lastOperator = null;
        } else {
            if (!num2.isEmpty())
                num1 = Double.parseDouble(num2);
        }
        num2 = "";

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(num1);
        dest.writeString(num2);
        dest.writeSerializable(lastOperator);
    }

    public void changeSign() {
        if (num2.length() == 0) {
            return;
        }
        if (num2.charAt(0) == '-') {
            num2 = num2.substring(1);
        } else {
            num2 = "-".concat(num2);
        }
    }

}
