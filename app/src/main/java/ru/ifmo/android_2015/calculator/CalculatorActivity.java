package ru.ifmo.android_2015.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CalculatorActivity extends AppCompatActivity {
    private enum Operation {
        NO_OPERATION, PLUS, MINUS, MULTIPLY, DIVIDE;
    }
    private enum State {
        FIRST_NUMBER_INPUT, WAITING_FOR_SECOND_NUMBER_INPUT, SECOND_NUMBER_INPUT, SHOW_RESULT, SHOW_ERROR
    }

    private static final String KEY_STATE = "state";
    private static final String KEY_NUMBER_INPUT = "numberInput";
    private static final String KEY_SELECTED_OPERATION = "selected_operation";
    private static final String KEY_OP_1 = "op1";
    private static final String KEY_OP_2 = "op2";
    private static final String KEY_RESULT = "result";
    private static final String KEY_TEXT_DISPLAY = "text_display";

    private DecimalFormat numberFormat;

    private State state;
    private TextView textDisplay;

    private Operation selectedOperation = Operation.NO_OPERATION;
    private String numberInput;
    private double op1;
    private double op2;
    private double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        textDisplay = (TextView) findViewById(R.id.textDisplay);
        configureDigitButton(R.id.number0, 0);state = State.FIRST_NUMBER_INPUT;
        configureDigitButton(R.id.number1, 1);
        configureDigitButton(R.id.number2, 2);
        configureDigitButton(R.id.number3, 3);
        configureDigitButton(R.id.number4, 4);
        configureDigitButton(R.id.number5, 5);
        configureDigitButton(R.id.number6, 6);
        configureDigitButton(R.id.number7, 7);
        configureDigitButton(R.id.number8, 8);
        configureDigitButton(R.id.number9, 9);
        configurePointButton(R.id.point);
        configureOperationButton(R.id.plus, Operation.PLUS);
        configureOperationButton(R.id.minus, Operation.MINUS);
        configureOperationButton(R.id.muliply, Operation.MULTIPLY);
        configureOperationButton(R.id.divide, Operation.DIVIDE);
        configureEqualityButton(R.id.equality);
        configureChangeSignButton(R.id.changeSign);
        configureResetButton(R.id.reset);

        numberFormat = (DecimalFormat) DecimalFormat.getInstance();
        numberFormat.setMinimumFractionDigits(0);
        numberFormat.setMaximumFractionDigits(8);

        switchState(State.FIRST_NUMBER_INPUT);

        if (savedInstanceState != null) {
            state = State.values()[savedInstanceState.getInt(KEY_STATE)];
            numberInput = savedInstanceState.getString(KEY_NUMBER_INPUT);
            selectedOperation = Operation.values()[savedInstanceState.getInt(KEY_SELECTED_OPERATION)];
            op1 = savedInstanceState.getDouble(KEY_OP_1);
            op2 = savedInstanceState.getDouble(KEY_OP_2);
            result = savedInstanceState.getDouble(KEY_RESULT);
            textDisplay.setText(savedInstanceState.getString(KEY_TEXT_DISPLAY));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(KEY_STATE, state.ordinal());
        bundle.putString(KEY_NUMBER_INPUT, numberInput);
        bundle.putInt(KEY_SELECTED_OPERATION, selectedOperation.ordinal());
        bundle.putDouble(KEY_OP_1, op1);
        bundle.putDouble(KEY_OP_2, op2);
        bundle.putDouble(KEY_RESULT, result);
        bundle.putString(KEY_TEXT_DISPLAY, textDisplay.getText().toString());
    }

    private void configureChangeSignButton(int resId) {
        ((Button)findViewById(resId)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (state) {
                    case FIRST_NUMBER_INPUT:
                    case SECOND_NUMBER_INPUT:
                        if (numberInput.startsWith("-"))
                            numberInput = numberInput.substring(1);
                        else
                            numberInput = "-" + numberInput;
                        textDisplay.setText(numberInput);
                        break;
                }
            }
        });
    }

    private void configureResetButton(final int resId) {
        ((Button)findViewById(resId)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op1 = 0;
                op2 = 0;
                result = 0;
                selectedOperation = Operation.NO_OPERATION;
                switchState(State.FIRST_NUMBER_INPUT);
            }
        });
    }

    private void configureDigitButton(int resId, final int digit) {
        ((Button)findViewById(resId)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (state) {
                    case WAITING_FOR_SECOND_NUMBER_INPUT:
                        switchState(State.SECOND_NUMBER_INPUT);
                    case FIRST_NUMBER_INPUT:
                    case SECOND_NUMBER_INPUT:
                        addDigitToInputNumber(digit);
                        break;
                }
            }
        });
    }

    private void configurePointButton(int resId) {
        ((Button)findViewById(resId)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (state) {
                    case WAITING_FOR_SECOND_NUMBER_INPUT:
                        switchState(State.SECOND_NUMBER_INPUT);
                    case FIRST_NUMBER_INPUT:
                    case SECOND_NUMBER_INPUT:
                        addPointToInputNumber();
                        break;
                }
            }
        });
    }

    private void addDigitToInputNumber(int digit) {
        if (!numberInput.equals("0")) {
            numberInput += digit;
        } else {
            if (digit != 0) {
                numberInput = Integer.toString(digit);
            }
        }
        textDisplay.setText(numberInput);
    }

    private void addPointToInputNumber() {
        if (!numberInput.contains(".")) {
            numberInput += ".";
        }
        textDisplay.setText(numberInput);
    }

    private void resetInputNumber() {
        numberInput = "0";
        textDisplay.setText(numberInput);
    }

    private void displayNumberOnTextDisplay(double number) {
        textDisplay.setText(numberFormat.format(number));
    }

    private void configureEqualityButton(int resId) {
        ((Button)findViewById(resId)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (state) {
                    case FIRST_NUMBER_INPUT:
                    case SECOND_NUMBER_INPUT:
                        finishNumberInput();
                        if (calculateOrShowError()) {
                            switchState(State.SHOW_RESULT);
                        }
                        break;

                }
            }
        });
    }

    private void configureOperationButton(final int resId, final Operation operation) {
        ((Button)findViewById(resId)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (state) {
                    case FIRST_NUMBER_INPUT:
                        finishNumberInput();
                        selectedOperation = operation;
                        switchState(State.WAITING_FOR_SECOND_NUMBER_INPUT);
                        break;
                    case WAITING_FOR_SECOND_NUMBER_INPUT:
                        selectedOperation = operation;
                        break;
                    case SECOND_NUMBER_INPUT:
                        finishNumberInput();
                        if (calculateOrShowError()) {
                            startOperationOnResult(operation);
                        }
                        break;
                    case SHOW_RESULT:
                        startOperationOnResult(operation);
                        break;
                    case SHOW_ERROR:
                        // Do nothing
                        break;
                }
            }
        });
    }

    private void finishNumberInput() {
        switch (state) {
            case FIRST_NUMBER_INPUT:
                op1 = Double.parseDouble(numberInput);
                break;
            case SECOND_NUMBER_INPUT:
                op2 = Double.parseDouble(numberInput);
                break;
        }
    }

    private void switchState(State newState) {
        this.state = newState;

        switch (newState) {
            case FIRST_NUMBER_INPUT:
            case SECOND_NUMBER_INPUT:
                resetInputNumber();
                break;
            case SHOW_RESULT:
                displayNumberOnTextDisplay(result);
                break;
            case SHOW_ERROR:
                textDisplay.setText("ERROR");
                break;
        }
    }

    private boolean calculateOrShowError() {
        double result;

        switch (selectedOperation) {
            case PLUS:
                result = op1 + op2;
                break;
            case MINUS:
                result = op1 - op2;
                break;
            case MULTIPLY:
                result = op1 * op2;
                break;
            case DIVIDE:
                result = op1 / op2;
                break;
            default:
                result = Double.NaN;
                break;
        }

        if (!Double.isInfinite(result) && !Double.isNaN(result)) {
            this.result = result;
            return true;
        } else {
            switchState(State.SHOW_ERROR);
            return false;
        }
    }

    private void startOperationOnResult(Operation operation) {
        op1 = result;
        selectedOperation = operation;
        displayNumberOnTextDisplay(result);
        switchState(State.WAITING_FOR_SECOND_NUMBER_INPUT);
    }
}
