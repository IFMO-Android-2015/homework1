package ru.ifmo.android_2015.thakohov.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {

    private static final String RESULT_KEY = "RESULT";
    private static final String DISPLAY_KEY = "DISPLAY";
    private static final String OPERATION_KEY = "OPERATION";
    private static final String TYPING_KEY = "TYPING";
    private static final String ERROR_KEY = "ERROR";

    TextView displayTextView;

    private StringBuilder displayText = new StringBuilder("0");

    private boolean isTyping = false;
    private  boolean errorState = false;
    private double result = 0.0;
    private String operation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        displayTextView = (TextView) findViewById(R.id.expressionText);

        if (savedInstanceState != null) {
            displayText = new StringBuilder(savedInstanceState.getString(DISPLAY_KEY));
            isTyping = savedInstanceState.getBoolean(TYPING_KEY);
            errorState = savedInstanceState.getBoolean(ERROR_KEY);
            result = savedInstanceState.getDouble(RESULT_KEY);
            operation = savedInstanceState.getString(OPERATION_KEY);

            updateUI();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putDouble(RESULT_KEY, result);
        savedInstanceState.putString(DISPLAY_KEY, displayText.toString());
        savedInstanceState.putString(OPERATION_KEY, operation);
        savedInstanceState.putBoolean(TYPING_KEY, isTyping);
        savedInstanceState.putBoolean(ERROR_KEY, errorState);
    }

    public void onDigitClick(View view) {
        Button clickedButton = (Button) view;
        String digit = clickedButton.getText().toString();

        if (displayText.toString().equals("0")) {
            if (digit.startsWith("0")) {
                return;
            }
            displayText.setLength(0);
        }

        if (isTyping) {
            displayText.append(digit);
        } else {
            displayText = new StringBuilder(digit);
            isTyping = true;
        }

        isTyping = true;
        errorState = false;

        updateUI();
    }

    public void onClearClick(View view) {
        if (errorState) {
            displayText = new StringBuilder("0");
        }

        if (displayText.length() != 0) {
            displayText.deleteCharAt(displayText.length() - 1);
        }

        updateUI();
    }

    public void onClearAllClick(View view) {
        result = 0.0;
        displayText = new StringBuilder("0");
        isTyping = false;
        errorState = false;
        operation = "";
        updateUI();
    }

    public void onSeparatorClick(View view) {
        isTyping = true;

        if (displayText.toString().indexOf('.') == -1) {
            displayText.append('.');
        }

        updateUI();
    }

    public void onUnaryOperationClick(View view) {
        if (errorState) {
            //TODO: show notification
            return;
        }

        Button clickedButton = (Button) view;
        String operation = clickedButton.getText().toString();

        double displayValue = Double.parseDouble(displayText.toString());
        boolean error = false;

        if (operation.equals("sin")) {
            displayValue = Math.sin(displayValue);
        } else if (operation.equals("cos")) {
            displayValue = Math.cos(displayValue);
        } else if (operation.equals(String.valueOf('\u221a'))) {
            if (displayValue >= 0) {
                displayValue = Math.sqrt(displayValue);
            } else {
                Log.e(TAG, "sqrt from a negative value");
                error = true;
            }
        } else if (operation.equals("1/x")) {
            try {
                displayValue = 1 / displayValue;
            } catch (ArithmeticException e) {
                Log.e(TAG, "divizion by zero: " + e.toString());
                error = true;
            }
        } else if (operation.equals("x" + String.valueOf('\u00b2'))) {
            displayValue = displayValue * displayValue;
        } else if (operation.equals("-x")) {
            displayValue = -displayValue;
        } else {
            Log.e(TAG, "Unsopported operation: " + operation);
            error = true;
        }

        if (error) {
            isTyping = false;
            errorState = true;
            displayText = new StringBuilder("Error");
        } else {
            displayText = new StringBuilder(removeTail(String.valueOf(displayValue)));
        }
        updateUI();
    }

    public void onBinaryOperationClick(View view) {
        if (errorState || !isTyping) {
            return;
        }

        Button clickedButton = (Button) view;
        String operation = clickedButton.getText().toString();

        boolean error = calculate();
        isTyping = false;
        if (error) {
            displayText = new StringBuilder("Error");
            errorState = true;
        }
        updateUI();
        this.operation = operation;
    }

    public void onEqualsClick(View view) {
        if (errorState || !isTyping) {
            return;
        }

        boolean error = calculate();
        if (error) {
            displayText = new StringBuilder("Error");
            errorState = true;
        }

        isTyping = true;
        displayText = new StringBuilder(removeTail(String.valueOf(result)));
        result = 0;
        updateUI();

        operation = "";
    }

    private boolean calculate() {
        double displayValue = Double.parseDouble(displayText.toString());
        boolean error = false;

        if (operation.isEmpty()) {
            result = displayValue;
            return false;
        }

        if (operation.equals("+")) {
            result += displayValue;
        } else if (operation.equals("-")) {
            result -= displayValue;
        } else if (operation.equals(String.valueOf('\u00d7'))) {
            result *= displayValue;
        } else if (operation.equals(String.valueOf('\u00f7'))) {
            if (displayValue == 0) {
                Log.e(TAG, "divizion by zero");
                error = true;
            } else {
                result /= displayValue;
            }
        } else {
            Log.e(TAG, "Unsupported operation: " + operation);
            error = true;
        }

        return error;
    }

    private void updateUI() {
        if (isTyping || errorState) {
            displayTextView.setText(displayText.toString());
        } else {
            displayTextView.setText(removeTail(String.valueOf(result)));
        }
    }

    /**
     * If the string looks like "xxxx.0", the ".0" tail is removed
     * @param s     the input string
     * @return      the resulting string
     */

    private String removeTail(String s) {
        if (s.endsWith(".0")) {
            return s.substring(0, s.length() - 2);
        } else {
            return s;
        }
    }

private static final String TAG = "CalculatorAcitivity";
}

