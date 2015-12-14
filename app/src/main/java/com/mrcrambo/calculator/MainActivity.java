package com.mrcrambo.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    public static final String TOKEN = "TOKEN";
    public static final String OPERAND = "OPERAND";
    public static final String MEMORY = "MEMORY";
    public static final String OPERATOR = "OPERATOR";
    public static final String WAITING_OPERATOR = "WAITINGOPERATOR";
    public static TextView calculatorDisplay;
    private static final String DIGITS = "0123456789.";
    private Boolean userIsInTheMiddleOfTypingANumber = false;
    Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = new Calculator();
        calculatorDisplay = (TextView) findViewById(R.id.textView);

        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);

        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonSubtract).setOnClickListener(this);
        findViewById(R.id.buttonMultiply).setOnClickListener(this);
        findViewById(R.id.buttonDivide).setOnClickListener(this);
        findViewById(R.id.buttonChangeSign).setOnClickListener(this);
        findViewById(R.id.buttonDecimalPoint).setOnClickListener(this);
        findViewById(R.id.buttonEquals).setOnClickListener(this);
        findViewById(R.id.buttonClear).setOnClickListener(this);
        findViewById(R.id.buttonClearMemory).setOnClickListener(this);
        findViewById(R.id.buttonAddToMemory).setOnClickListener(this);
        findViewById(R.id.buttonSubtractFromMemory).setOnClickListener(this);
        findViewById(R.id.buttonRecallMemory).setOnClickListener(this);
    }

     @Override
    public void onClick(View view) {

        String buttonPressed = ((Button) view).getText().toString();

        if (DIGITS.contains(buttonPressed)) {
            // digit was pressed
            if (userIsInTheMiddleOfTypingANumber) {
                calculatorDisplay.append(buttonPressed);
            } else {
                calculatorDisplay.setText(buttonPressed);
                userIsInTheMiddleOfTypingANumber = true;
            }
        } else {
            // operation was pressed
            if (userIsInTheMiddleOfTypingANumber) {
                calculator.setOperand(Double.parseDouble(calculatorDisplay.getText().toString()));
                userIsInTheMiddleOfTypingANumber = false;
            }

            calculator.Operation(buttonPressed);
            if (calculator.getOperand().longValue() == calculator.getOperand()){
                calculatorDisplay.setText(String.valueOf(calculator.getOperand().longValue()));
            }else {
                calculatorDisplay.setText(String.valueOf(calculator.getOperand()));
            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save variables on screen orientation change
        outState.putString(TOKEN, calculatorDisplay.getText().toString());
        outState.putDouble(OPERAND, Double.valueOf(calculatorDisplay.getText().toString()));
        outState.putDouble(MEMORY, calculator.getCalcMemory());
        outState.putString(OPERATOR, calculator.getWaitOperator());
        outState.putDouble(WAITING_OPERATOR, calculator.getWaitingOperand());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore variables on screen orientation change
        calculator.setOperand(savedInstanceState.getDouble(OPERAND));
        calculator.setCalcMemory(savedInstanceState.getDouble(MEMORY));
        calculatorDisplay.setText(savedInstanceState.getString(TOKEN));
        calculator.setWaitOperator(savedInstanceState.getString(OPERATOR));
        calculator.setWaitingOperand(savedInstanceState.getDouble(WAITING_OPERATOR));
    }
}
