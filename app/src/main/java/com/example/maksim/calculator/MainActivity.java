package com.example.maksim.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {
    enum State {
        FIRST, OPERATION, SECOND, RESULT
    }

    private TextView text;
    private State state;
    private char operation;
    private StringBuffer result;
    private Double leftOperand, rightOperand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.screen);
        if (savedInstanceState == null) {
            result = new StringBuffer("0");
            leftOperand = new Double(0);
            rightOperand = new Double(0);
            state = State.FIRST;
            operation = '\0';
        } else {
            result = new StringBuffer(savedInstanceState.getString("result"));
            leftOperand = new Double(savedInstanceState.getDouble("leftOperand"));
            rightOperand = new Double(savedInstanceState.getDouble("rightOperand"));
            state = (State) savedInstanceState.getSerializable("state");
            operation = savedInstanceState.getChar("operation");
        }
        text.setText(result.toString());
    }

    public void onDigitClick(View view) {
        if (state == State.OPERATION) {
            state = State.SECOND;
            leftOperand = Double.parseDouble(result.toString());
            result = new StringBuffer("0");
        }
        if (state == State.RESULT)
            this.onClearClick(view);

        String digit = ((TextView) view).getText().toString();

        if (state == State.FIRST || state == State.SECOND) {
            result.append(digit);
            if (result.charAt(0) == '0' && result.charAt(1) != '.') {
                result.deleteCharAt(0);
            }
        }
        text.setText(result.toString());
    }

    public void onDotClick(View view) {
        if (state == State.RESULT) {
            result = new StringBuffer("0.");
            state = State.FIRST;
            text.setText(result.toString());
            return;
        }
        if (state == State.OPERATION) {
            state = State.SECOND;
            leftOperand = Double.parseDouble(result.toString());
            result = new StringBuffer("0.");
            text.setText(result.toString());
            return;
        }
        if (result.toString().contains("."))
            return;
        result.append('.');
        text.setText(result.toString());
    }

    public void onClearClick(View view) {
        state = State.FIRST;
        leftOperand = 0.0;
        rightOperand = 0.0;
        result = new StringBuffer("0");
        text.setText(result.toString());
    }

    public void onChangeSignClick(View view) {
        if (state == State.OPERATION)
            return;
        if (Double.valueOf(result.toString()) == 0.0)
            return;
        if (result.charAt(0) != '-')
            result.insert(0, '-');
        else
            result.deleteCharAt(0);
        text.setText(result.toString());
    }

    public void onDeleteClick(View view) {
        if (state == State.OPERATION) {
            operation = '\0';
            state = State.FIRST;
        }
        if (result.length() > 1) {
            result.deleteCharAt(result.length() - 1);
        } else {
            result = new StringBuffer("0");
        }
        text.setText(result.toString());
    }

    public void onResultClick(View view) {
        if (state == State.FIRST)
            return;
        if (state == State.SECOND || state == State.OPERATION) {
            rightOperand = Double.parseDouble(result.toString());
        }
        switch (operation) {
            case '\0':
                break;
            case '+':
                leftOperand += rightOperand;
                break;
            case '–':
                leftOperand -= rightOperand;
                break;
            case '×':
                leftOperand *= rightOperand;
                break;
            case '÷':
                leftOperand /= rightOperand;
                break;
            default:
                break;
        }
        leftOperand = new BigDecimal(leftOperand).setScale(5, RoundingMode.UP).doubleValue();

        result = new StringBuffer(leftOperand.toString());
        state = State.RESULT;
        text.setText(result.toString());
    }

    public void onOperationClick(View view) {
        if (state == State.FIRST) {
            leftOperand = Double.parseDouble(result.toString());
        }
        if (state == State.SECOND) {
            onResultClick(view);
        }
        state = State.OPERATION;
        operation = ((TextView) view).getText().toString().charAt(0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("result", result.toString());
        outState.putChar("operation", operation);
        outState.putSerializable("state", state);
        outState.putDouble("leftOperand", leftOperand.doubleValue());
        outState.putDouble("rightOperand", rightOperand.doubleValue());
    }
}