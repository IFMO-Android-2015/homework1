package com.example.clitcommander;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;

public class MainActivity extends Activity{

    private Button btnPoint;
    private Button btnDigit0;
    private Button btnDigit1;
    private Button btnDigit2;
    private Button btnDigit3;
    private Button btnDigit4;
    private Button btnDigit5;
    private Button btnDigit6;
    private Button btnDigit7;
    private Button btnDigit8;
    private Button btnDigit9;

    private Button btnPlus;
    private Button btnMinus;
    private Button btnMultiply;
    private Button btnDivide;

    private Button btnSolve;
    private Button btnClean;

    private boolean wasError = false;
    private boolean wasCorrectAnswer = false;

    private TextView textView;

    private Double solve(String expression) throws ParseException, NumberFormatException {
        Integer opIndex = null;
        for (int i = 0, amount = 0; i < expression.length(); i++) {
            if((expression.charAt(i) == '+' || expression.charAt(i) == '-' ||
                    expression.charAt(i) == '*' || expression.charAt(i) == '/') &&
                    i != 0) {
                opIndex = i;
                break;
            }
        }
        if (opIndex == null) {
            return Double.parseDouble(expression);
        }
        Double first = Double.parseDouble(expression.substring(0, opIndex));
        Double second = Double.parseDouble(expression.substring(opIndex + 1));
        switch (expression.charAt(opIndex)) {
            case '+':
                return first + second;
            case '-':
                return first - second;
            case '*':
                return first * second;
            case '/':
                return first / second;
        }
        return null;
    }

    private View.OnClickListener opListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            wasCorrectAnswer = false;
            if (wasError) {
                textView.setText("");
                wasError = false;
            }
            switch (v.getId()) {
                case com.example.clitcommander.R.id.btnClean:
                    textView.setText("");
                    break;
                case com.example.clitcommander.R.id.btnSolve:
                    if (textView.length() != 0) {
                        try {
                            textView.setText(Double.toString(solve(textView.getText().toString())));
                            wasCorrectAnswer = true;
                        } catch (Exception e) {
                            textView.setText("Error");
                            wasError = true;
                        }
                    }
                    break;
                case com.example.clitcommander.R.id.btnPlus:
                    textView.append("+");
                    break;
                case com.example.clitcommander.R.id.btnMinus:
                    textView.append("-");
                    break;
                case com.example.clitcommander.R.id.btnMultiply:
                    textView.append("*");
                    break;
                case com.example.clitcommander.R.id.btnDivide:
                    textView.append("/");
                    break;
            }
        }
    };

    private View.OnClickListener digitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (wasError) {
                textView.setText("");
                wasError = false;
            }
            if (wasCorrectAnswer) {
                textView.setText("");
                wasCorrectAnswer = false;
            }
            switch (v.getId()) {
                case com.example.clitcommander.R.id.btnDigit0:
                    textView.append("0");
                    break;
                case com.example.clitcommander.R.id.btnPoint:
                    textView.append(".");
                    break;
                case com.example.clitcommander.R.id.btnDigit1:
                    textView.append("1");
                    break;
                case com.example.clitcommander.R.id.btnDigit2:
                    textView.append("2");
                    break;
                case com.example.clitcommander.R.id.btnDigit3:
                    textView.append("3");
                    break;
                case com.example.clitcommander.R.id.btnDigit4:
                    textView.append("4");
                    break;
                case com.example.clitcommander.R.id.btnDigit5:
                    textView.append("5");
                    break;
                case com.example.clitcommander.R.id.btnDigit6:
                    textView.append("6");
                    break;
                case com.example.clitcommander.R.id.btnDigit7:
                    textView.append("7");
                    break;
                case com.example.clitcommander.R.id.btnDigit8:
                    textView.append("8");
                    break;
                case com.example.clitcommander.R.id.btnDigit9:
                    textView.append("9");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.clitcommander.R.layout.activity_main);

        btnDigit0 = (Button) findViewById(com.example.clitcommander.R.id.btnDigit0);
        btnPoint = (Button) findViewById(com.example.clitcommander.R.id.btnPoint);
        btnDigit1 = (Button) findViewById(com.example.clitcommander.R.id.btnDigit1);
        btnDigit2 = (Button) findViewById(com.example.clitcommander.R.id.btnDigit2);
        btnDigit3 = (Button) findViewById(com.example.clitcommander.R.id.btnDigit3);
        btnDigit4 = (Button) findViewById(com.example.clitcommander.R.id.btnDigit4);
        btnDigit5 = (Button) findViewById(com.example.clitcommander.R.id.btnDigit5);
        btnDigit6 = (Button) findViewById(com.example.clitcommander.R.id.btnDigit6);
        btnDigit7 = (Button) findViewById(com.example.clitcommander.R.id.btnDigit7);
        btnDigit8 = (Button) findViewById(com.example.clitcommander.R.id.btnDigit8);
        btnDigit9 = (Button) findViewById(com.example.clitcommander.R.id.btnDigit9);

        btnDigit0.setOnClickListener(digitListener);
        btnPoint.setOnClickListener(digitListener);
        btnDigit1.setOnClickListener(digitListener);
        btnDigit2.setOnClickListener(digitListener);
        btnDigit3.setOnClickListener(digitListener);
        btnDigit4.setOnClickListener(digitListener);
        btnDigit5.setOnClickListener(digitListener);
        btnDigit6.setOnClickListener(digitListener);
        btnDigit7.setOnClickListener(digitListener);
        btnDigit8.setOnClickListener(digitListener);
        btnDigit9.setOnClickListener(digitListener);

        btnPlus = (Button) findViewById(com.example.clitcommander.R.id.btnPlus);
        btnMinus = (Button) findViewById(com.example.clitcommander.R.id.btnMinus);
        btnMultiply = (Button) findViewById(com.example.clitcommander.R.id.btnMultiply);
        btnDivide = (Button) findViewById(com.example.clitcommander.R.id.btnDivide);

        btnPlus.setOnClickListener(opListener);
        btnMinus.setOnClickListener(opListener);
        btnMultiply.setOnClickListener(opListener);
        btnDivide.setOnClickListener(opListener);


        btnSolve = (Button) findViewById(com.example.clitcommander.R.id.btnSolve);
        btnClean = (Button) findViewById(com.example.clitcommander.R.id.btnClean);

        btnSolve.setOnClickListener(opListener);
        btnClean.setOnClickListener(opListener);
        textView = (TextView) findViewById(com.example.clitcommander.R.id.expression);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("textView", textView.getText());
        outState.putBoolean("wasError", wasError);
        outState.putBoolean("wasCorrectAnswer", wasCorrectAnswer);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText(savedInstanceState.getCharSequence("textView", ""));
        wasError = savedInstanceState.getBoolean("wasError", false);
        wasCorrectAnswer = savedInstanceState.getBoolean("wasCorrectAnswer", false);
    }
}