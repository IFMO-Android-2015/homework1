package com.example.homework1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainActivity extends ActionBarActivity {

    private TextView display;

    private StringBuilder showing_string;
    private BigDecimal digit;

    private enum Operation {ADD, SUB, MUL, DIV, ERR, NUM}

    private Operation lastOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (TextView) findViewById(R.id.display);

        if (savedInstanceState == null) {
            showing_string = new StringBuilder("0");
            digit = new BigDecimal("0");
            lastOperation = Operation.NUM;
            display.setText(showing_string);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("DISPLAY", display.getText().toString());
        outState.putString("DIGIT", digit.toString());
        outState.putString("STRING", showing_string.toString());
        outState.putSerializable("OPERATION", lastOperation);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        display.setText(savedInstanceState.getString("DISPLAY"));
        digit = new BigDecimal(savedInstanceState.getString("DIGIT"));
        showing_string = new StringBuilder(savedInstanceState.getString("STRING"));
        lastOperation = (Operation) savedInstanceState.getSerializable("OPERATION");
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void digitButton(View view) {
        if (lastOperation == Operation.ERR) return;

        Button button = (Button) view;
        if (showing_string.charAt(0) == '0' && showing_string.length() == 1) {
            showing_string.setCharAt(0, button.getText().equals("00") ? '0' : button.getText().charAt(0));
        } else {
            showing_string.append(button.getText());
        }

        display.setText(showing_string);
    }

    public void allCleanButton(View view) {
        showing_string = new StringBuilder("0");
        digit = new BigDecimal("0");
        lastOperation = Operation.NUM;
        display.setText(showing_string);
    }

    public void deleteLastSymbolButton(View view) {
        if (lastOperation == Operation.ERR || showing_string.length() <= 1)
            return;

        showing_string.deleteCharAt(showing_string.length() - 1);
        if (showing_string.length() == 0) {
            showing_string.append("0");
        }
        display.setText(showing_string);
    }

    public void dotButton(View view) {
        if (lastOperation == Operation.ERR) return;

        if (!showing_string.toString().contains(".")) {
            showing_string.append(".");
        }
        display.setText(showing_string);
    }

    public void changeSignButton(View view) {
        if (lastOperation == Operation.ERR) return;

        if (showing_string.charAt(0) == '-') {
            showing_string.deleteCharAt(0);
        } else {
            if (!showing_string.toString().equals("0")) {
                showing_string.reverse();
                showing_string.append("-");
                showing_string.reverse();
            }
        }
        display.setText(showing_string);
    }

    private String makeRound(String withScale) {
        StringBuilder string = new StringBuilder(withScale);
        if (withScale.contains("0E")) {
            return "0";
        }
        if (withScale.contains(".")) {
            int i = string.length() - 1;
            while (string.charAt(i) == '0') {
                string.deleteCharAt(i);
                --i;
            }
            if (string.charAt(string.length() - 1) == '.') {
                string.deleteCharAt(string.length() - 1);
            }
        }
        return string.toString();
    }

    public void evaluate(View view) {
        BigDecimal tmp = new BigDecimal(showing_string.toString());
        String string;
        switch (lastOperation) {
            case ADD:
                digit = digit.add(tmp);
                string = makeRound(digit.toString());
                digit = new BigDecimal(string);
                showing_string = new StringBuilder(string);
                lastOperation = Operation.NUM;
                break;
            case SUB:
                digit = digit.subtract(tmp);
                string = makeRound(digit.toString());
                digit = new BigDecimal(string);
                showing_string = new StringBuilder(string);
                lastOperation = Operation.NUM;
                break;
            case DIV:
                try {
                    digit = digit.divide(tmp, 10, BigDecimal.ROUND_HALF_EVEN);
                    string = makeRound(digit.toString());
                    digit = new BigDecimal(string);
                    showing_string = new StringBuilder(string);
                    lastOperation = Operation.NUM;
                } catch (Exception e) {
                    showing_string = new StringBuilder("ERROR");
                    lastOperation = Operation.ERR;
                }
                break;
            case MUL:
                digit = digit.multiply(tmp);
                digit = digit.setScale(10, BigDecimal.ROUND_HALF_EVEN);
                string = makeRound(digit.toString());
                digit = new BigDecimal(string);
                showing_string = new StringBuilder(string);
                lastOperation = Operation.NUM;
                break;
            case NUM:
                digit = new BigDecimal(showing_string.toString());
                break;
        }
    }

    public void operationButton(View view) {
        if (lastOperation == Operation.ERR) return;

        switch (view.getId()) {
            case R.id.addButton:
                evaluate(view);
                lastOperation = Operation.ADD;
                break;
            case R.id.subtractButton:
                evaluate(view);
                lastOperation = Operation.SUB;
                break;
            case R.id.multiplyButton:
                evaluate(view);
                lastOperation = Operation.MUL;
                break;
            case R.id.divideButton:
                evaluate(view);
                if (lastOperation != Operation.ERR) {
                    lastOperation = Operation.DIV;
                }
                break;
            case R.id.evaluateButton:
                if (lastOperation != Operation.NUM) {
                    evaluate(view);
                }
                break;
        }
        display.setText(showing_string.toString());
        if (lastOperation != Operation.NUM && lastOperation != Operation.ERR) {
            showing_string = new StringBuilder("0");
        }
    }
}