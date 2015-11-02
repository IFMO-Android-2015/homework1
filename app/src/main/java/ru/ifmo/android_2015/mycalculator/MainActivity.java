package ru.ifmo.android_2015.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private TextView text;

    private StringBuilder string;
    private BigDecimal accumulator;

    private enum Operation {ERROR, NONE, SUM, SUB, MUL, DIV}
    private Operation operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.screen);
        text.setVisibility(View.VISIBLE);

        if (savedInstanceState == null) {
            operation = Operation.NONE;
            accumulator = new BigDecimal(0);
            string = new StringBuilder("0");
            text.setText(string);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("TEXT", text.getText().toString());
        outState.putString("STRING", string.toString());
        outState.putString("ACC", accumulator.toString());
        outState.putSerializable("OPERATION", operation);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        text.setText(savedInstanceState.getString("TEXT"));
        string = new StringBuilder(savedInstanceState.getString("STRING"));
        accumulator = new BigDecimal(savedInstanceState.getString("ACC"));
        operation = (Operation) savedInstanceState.get("OPERATION");
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void appendDigit(View v) {
        Button button = (Button) v;
        if (string.toString().equals("0") || operation == Operation.ERROR) {
            string = new StringBuilder(button.getText());
            if (operation == Operation.ERROR)
                operation = Operation.NONE;
        } else {
            string.append(button.getText());
        }
        text.setText(string);
    }

    public void appendPoint(View v) {
        if (!string.toString().contains(".")) {
            string.append('.');
            if (operation == Operation.ERROR)
                operation = Operation.NONE;
            text.setText(string);
        }
    }

    private void calc(View v) {
        if (operation == Operation.NONE) {
            accumulator = new BigDecimal(string.toString());
            string = new StringBuilder("0");
            return;
        }
        BigDecimal tmp = new BigDecimal(string.toString());
        switch (operation) {
            case DIV:
                try {
                    accumulator = accumulator.divide(tmp, 20, RoundingMode.HALF_EVEN);
                    string = new StringBuilder(accumulator.toString());
                    operation = Operation.NONE;
                } catch (Exception e) {
                    string = new StringBuilder("0");
                    accumulator = new BigDecimal(0);
                    text.setText("Invalid arguments.\nCurrent value: 0");
                    operation = Operation.ERROR;
                }
                break;
            case MUL:
                accumulator = accumulator.multiply(tmp);
                string = new StringBuilder(accumulator.toString());
                operation = Operation.NONE;
                break;
            case SUB:
                accumulator = accumulator.subtract(tmp);
                string = new StringBuilder(accumulator.toString());
                operation = Operation.NONE;
                break;
            case SUM:
                accumulator = accumulator.add(tmp);
                string = new StringBuilder(accumulator.toString());
                operation = Operation.NONE;
                break;
        }
    }

    public void eval(View v) {
        Button button = (Button) v;
        String name = (String) button.getText();

        if (operation == Operation.ERROR && !name.equals("AC"))
            return;

        switch (name) {
            case "AC":
                string = new StringBuilder("0");
                operation = Operation.NONE;
                accumulator = new BigDecimal(0);
                break;
            case "C":
                if (string.length() > 1) {
                    string = string.deleteCharAt(string.length() - 1);
                } else {
                    string = new StringBuilder("0");
                }
                break;
            case "/":
                calc(v);
                operation = Operation.DIV;
                break;
            case "*":
                calc(v);
                operation = Operation.MUL;
                break;
            case "-":
                calc(v);
                operation = Operation.SUB;
                break;
            case "+":
                calc(v);
                operation = Operation.SUM;
                break;
            case "=":
                if (operation != Operation.NONE) {
                    calc(v);
                }
                break;
            case "%":
                calc(v);
                accumulator = accumulator.divide(new BigDecimal(100));
                string = new StringBuilder(accumulator.toString());
                break;
            case "+/-":
                if (!string.toString().equals("0")) {
                    if (string.charAt(0) == '-') {
                        string = string.deleteCharAt(0);
                    } else {
                        string = string.reverse();
                        string = string.append('-');
                        string = string.reverse();
                    }
                }
        }
        if (operation != Operation.ERROR)
            text.setText(string);
    }
}
