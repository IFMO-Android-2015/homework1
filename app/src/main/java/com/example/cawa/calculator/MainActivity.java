package com.example.cawa.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    String lastValue = "";
    String value = "";
    String operation = "";
    boolean valueOnFirst = true;
    boolean isComma = false;
    boolean isExp = false;
    private TextView chosenLabel;
    private int size = 0;
    private int MAX_NUMBER_SIZE = 25;

    public void onClick(View view) {
        switch  (view.getId()) {
            case R.id.oneButton :
                addDigit("1");
                break;
            case R.id.twoButton :
                addDigit("2");
                break;
            case R.id.threeButton :
                addDigit("3");
                break;
            case R.id.fourButton :
                addDigit("4");
                break;
            case R.id.fiveButton :
                addDigit("5");
                break;
            case R.id.sixButton :
                addDigit("6");
                break;
            case R.id.sevenButton :
                addDigit("7");
                break;
            case R.id.eightButton :
                addDigit("8");
                break;
            case R.id.nineButton :
                addDigit("9");
                break;
            case R.id.zeroButton :
                addDigit("0");
                break;
            case R.id.commaButton :
                if (!isComma && (size != 0) && !isExp) {
                    addDigit(".");
                    isComma = true;
                }
                break;
            case R.id.expButton :
                if (!isExp && (size != 0) && (value.charAt(value.length() - 1) != '.')) {
                    addDigit("E");
                    isExp = true;
                }
                break;
            case R.id.signButton :
                changeSign(value);
                break;
            case R.id.plusButton :
                makeOp("+");
                break;
            case R.id.minusButton :
                makeOp("-");
                break;
            case R.id.mulButton :
                makeOp("*");
                break;
            case R.id.divButton :
                makeOp("/");
                break;
            case R.id.equalButton :
                if (!operation.equals("") && size != 0) {
                    calc();
                }
                break;
            case R.id.clearButton :
                nextNumber("", false);
                nextNumber("", true);
                break;
            case R.id.deleteButton :
                del();
                break;
        }

    }
    void del() {
        if (valueOnFirst) {
            if (size != 0) {
                if (!isInf()) {
                    nextNumber(value.substring(0, value.length() - 1), true);
                } else {
                    nextNumber("", true);
                }
            }
        } else {
            if (size != 0) {
                nextNumber(value.substring(0, value.length() - 1), false);
            } else {
                nextNumber(lastValue.substring(0, lastValue.length()), true);
            }
        }
    }
    void addDigit(String s) {
        if (size < MAX_NUMBER_SIZE && !isInf()) {
            refreshValue(value + s);
            size++;
        }
    }
    boolean isInf () {
        return (value.equals("Infinity") || value.equals("-Infinity") || value.equals("NaN"));
    }
    void makeOp(String s) {
        if (size != 0) {
            if (valueOnFirst) {
                operation = s;
                lastValue = value;
                ((TextView) findViewById(R.id.operationLabel)).setText(s);
                nextNumber("", false);
            } else {
                calc();
                makeOp(s);
            }
        }
    }
    void nextNumber(String s, boolean first) {
        if (first) {
            valueOnFirst = true;
            ((TextView) findViewById(R.id.operationLabel)).setText("");
            operation = "";
            chosenLabel = (TextView) findViewById(R.id.firstLabel);
            lastValue = "";
        } else {
            valueOnFirst = false;
            chosenLabel = (TextView) findViewById(R.id.secondLabel);
        }

        value = s;
        isComma = false;
        isExp = false;
        size = s.length();
        if (size != 0) {
            s = checkEnd(s);
            size = s.length();
        }
        if (size != 0) {
            if (s.charAt(0) == '-') {
                size--;
            }

            if (s.contains(".")) {
                size--;
                isComma = true;
            }
            if (s.contains("E")) {
                size--;
                isExp = true;
            }
        }
        if (size == 0) {
            value = "";
            isComma = false;
            isExp = false;
        }
        chosenLabel.setText(value);
    }
    String checkEnd(String s) {
        if ((s.charAt(s.length() - 1) == 'E') || (s.charAt(s.length() - 1) == ',') || (s.charAt(s.length() - 1) == '-')) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
    void calc() {
        Double a = Double.parseDouble(checkEnd(lastValue));
        Double b = Double.parseDouble(checkEnd(value));
        Double ans = 0.d;
        switch (operation) {
            case "+" :
                ans = a + b;
                break;
            case "-" :
                ans = a - b;
                break;
            case "*" :
                ans = a * b;
                break;
            case "/" :
                ans = a / b;
                break;
        }
        value = "";
        lastValue = "";
        operation = "";
        ((TextView) findViewById(R.id.firstLabel)).setText("");
        ((TextView) findViewById(R.id.operationLabel)).setText("");
        ((TextView) findViewById(R.id.secondLabel)).setText("");
        nextNumber(ans.toString(), true);
    }
    void changeSign(String s) {
        if(s.length() != 0) {
            char c = s.charAt(0);
            if (c == '-') {
                s = s.substring(1);
            } else {
                s = "-" + s;
            }
            refreshValue(s);
        }
    }
    void refreshValue(String s) {
        value = s;
        chosenLabel.setText(value);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        chosenLabel = (TextView)findViewById(R.id.firstLabel);
        chosenLabel.setText(value);

        if (savedInstanceState != null) {
            if (!savedInstanceState.getBoolean("keyValueOnFirst")) {
                nextNumber(savedInstanceState.getString("keyLastValue"), true);
                makeOp(savedInstanceState.getString("keyOperation"));
                nextNumber(savedInstanceState.getString("keyValue"),false);
            } else {
                nextNumber(savedInstanceState.getString("keyValue"),true);
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState");

        outState.putString("keyValue", value);
        outState.putString("keyLastValue", lastValue);
        outState.putString("keyOperation", operation);
        outState.putBoolean("keyValueOnFirst", valueOnFirst);
    }

}
