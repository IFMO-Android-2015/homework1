package com.hw.anna.hw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;


public class Calculator extends AppCompatActivity implements View.OnClickListener {

    Button one, two, three, four, five, six, seven, eight, nine, zero, point, plus, minus, C, AC, mul, div, eq;
    TextView res;
    String expr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        one = (Button) findViewById(R.id.but1);
        two = (Button) findViewById(R.id.but2);
        three = (Button) findViewById(R.id.but3);
        four = (Button) findViewById(R.id.but4);
        five = (Button) findViewById(R.id.but5);
        six = (Button) findViewById(R.id.but6);
        seven = (Button) findViewById(R.id.but7);
        eight = (Button) findViewById(R.id.but8);
        nine = (Button) findViewById(R.id.but9);
        zero = (Button) findViewById(R.id.but0);
        point = (Button) findViewById(R.id.butPoint);
        div = (Button) findViewById(R.id.butDiv);
        mul = (Button) findViewById(R.id.butMul);
        minus = (Button) findViewById(R.id.butMinus);
        plus = (Button) findViewById(R.id.butPlus);
        AC = (Button) findViewById(R.id.butAC);
        C = (Button) findViewById(R.id.butC);
        eq = (Button) findViewById(R.id.butEq);

        res = (TextView) findViewById(R.id.text);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        point.setOnClickListener(this);
        div.setOnClickListener(this);
        mul.setOnClickListener(this);
        minus.setOnClickListener(this);
        plus.setOnClickListener(this);
        AC.setOnClickListener(this);
        C.setOnClickListener(this);
        eq.setOnClickListener(this);
    }

    private boolean checkLen(String str) {
        if (str.length() >  14) {
            return false;
        }
        return true;
    }

    int p = 0;
    private double parsePriorityFirst() {
        double first = parsePrioritySecond();
        while (p < expr.length()) {
            char tmp = expr.charAt(p);
            switch(tmp) {
                case '+':
                    p++;
                    first += parsePriorityThird();
                    break;
                case '-':
                    p++;
                    first -= parsePriorityThird();
                    break;
                default:
                    return first;
            }
        }
        return first;
    }

    private double parsePrioritySecond() {
        double first = parsePriorityThird();
        while (p < expr.length()) {
            char tmp = expr.charAt(p);
            switch(tmp) {
                case '*':
                    p++;
                    first *= parsePriorityThird();
                    break;
                case '/':
                    p++;
                    first /= parsePriorityThird();
                    break;
                default:
                    return first;
            }
        }
        return first;
    }

    private String getSubStr() {
        int p0 = p;
        String s = "";
        while (p < expr.length()) {
            if (Character.isLetterOrDigit(expr.charAt(p)) || (expr.charAt(p) == '.')) {
                p++;
            } else {
                break;
            }
        }
        if (p > p0) {
            s = expr.substring(p0, p);
        }
        return s;
    }

    private double parsePriorityThird() {
        double answ = 0;
        boolean neg = false;
        if (expr.charAt(p) == '-') {
            neg = true;
            p++;
        }
        String s = getSubStr();
        if (neg) {
            s = "-" + s;
        }
        answ = Double.parseDouble(s);
        return answ;
    }

    private boolean isBinOperation(char c) {
        if ((c == '+') || (c == '*') || (c == '/') || (c == '-')) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int ID = v.getId();
        if ((checkLen(expr)) || (ID == R.id.butC) || (ID == R.id.butAC) || (ID == R.id.butEq)) {
            switch (ID) {
                case R.id.but1:
                    expr += "1";
                    res.setText(expr);
                    break;
                case R.id.but2:
                    expr += "2";
                    res.setText(expr);
                    break;
                case R.id.but3:
                    expr += "3";
                    res.setText(expr);
                    break;
                case R.id.but4:
                    expr += "4";
                    res.setText(expr);
                    break;
                case R.id.but5:
                    expr += "5";
                    res.setText(expr);
                    break;
                case R.id.but6:
                    expr += "6";
                    res.setText(expr);
                    break;
                case R.id.but7:
                    expr += "7";
                    res.setText(expr);
                    break;
                case R.id.but8:
                    expr += "8";
                    res.setText(expr);
                    break;
                case R.id.but9:
                    expr += "9";
                    res.setText(expr);
                    break;
                case R.id.but0:
                    expr += "0";
                    res.setText(expr);
                    break;
                case R.id.butPoint:
                    expr += ".";
                    res.setText(expr);
                    break;
                case R.id.butPlus:
                    if (isBinOperation(expr.charAt(expr.length() - 1))) {
                        expr = expr.substring(0, expr.length() - 1);
                    }
                    expr += "+";
                    res.setText(expr);
                    break;
                case R.id.butMinus:
                    if (isBinOperation(expr.charAt(expr.length() - 1))) {
                        expr = expr.substring(0, expr.length() - 1);
                    }
                    expr += "-";
                    res.setText(expr);
                    break;
                case R.id.butMul:
                    if (isBinOperation(expr.charAt(expr.length() - 1))) {
                        expr = expr.substring(0, expr.length() - 1);
                    }
                    expr += "*";
                    res.setText(expr);
                    break;
                case R.id.butDiv:
                    if (isBinOperation(expr.charAt(expr.length() - 1))) {
                        expr = expr.substring(0, expr.length() - 1);
                    }
                    expr += "/";
                    res.setText(expr);
                    break;
                case R.id.butC:
                    expr = expr.substring(0, expr.length() - 1);
                    res.setText(expr);
                    break;
                case R.id.butAC:
                    expr = "";
                    res.setText(expr);
                    break;
                case R.id.butEq:
                    p = 0;
                    String exAn = "";
                    try {
                        double answ = parsePriorityFirst();
                        exAn = Double.toString(answ);
                    } catch (Exception e) {
                        exAn = "Error";
                    }
                    expr = "";
                    res.setText(exAn);
                    break;
            }
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("expression", expr);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        expr = savedInstanceState.getString("expression");
    }
}
