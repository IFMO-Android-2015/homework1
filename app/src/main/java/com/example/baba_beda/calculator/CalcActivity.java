package com.example.baba_beda.calculator;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity {
    final String PLUS = "+";
    final String MINUS = "−";
    final String MULT = "×";
    final String DIVIDE = "÷";
    final String DELETE = "del";
    final String CLEAR = "C";
    final String POINT = ".";
    final String DIVISIONBYZERO = "Division by zero!";

    final String OPERAND1 = "OPERAND1";
    final String OPERAND2 = "OPERAND2";
    final String OPERATOR = "OPERATOR";

    boolean wasPoint = false;
    String operand1 = "0";
    String operand2 = "";
    String operator = "";
    TextView field;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        field = (TextView) findViewById(R.id.textCalc);
        field.setText("0");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(OPERAND1, operand1);
        outState.putString(OPERAND2, operand2);
        outState.putString(OPERATOR, operator);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        operand1 = savedInstanceState.getString(OPERAND1);
        operand2 = savedInstanceState.getString(OPERAND2);
        operator = savedInstanceState.getString(OPERATOR);
        field.setText(operand1 + operator + operand2);
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onClick(View view) {
        String action = ((Button)view).getText().toString();
        if (action.equals(CLEAR)) {
            operand1 = "0";
            operand2 = "";
            operator = "";
            field.setText(operand1);
        }
        if (field.getText().equals("0") || field.getText().equals(DIVISIONBYZERO)) {
            if (action.equals(POINT)) {
                field.setText(operand1 + action);
                wasPoint = true;
            }
            else if (isOperator(action)) {
                operator = action;
                field.setText(operand1 + operator);
            }
            else if (action.equals(DELETE)) {
                field.setText(operand1);
            }
            else if (Character.isDigit(action.charAt(0))) {
                if (field.getText().equals(DIVISIONBYZERO)) {
                    operand1 = "0";
                    field.setText(action);
                }
                else if (field.getText().equals("0") && !action.equals("0")) {
                    operand1 = action;
                    field.setText(action);
                }
            }
        }
        else {
            if (action.equals(POINT) && !wasPoint) {
                if (!operator.isEmpty()) {
                    operand2 += POINT;
                }
                else {
                    operand1 += POINT;
                }
                field.setText(operand1 + operator + operand2);
                wasPoint = true;
            }
            else if (action.equals(DELETE)) {
                if (!operand2.isEmpty()) {
                    operand2 = operand2.substring(0, operand2.length() - 1);
                }
                else if (!operator.isEmpty()) {
                    operator = "";
                }
                else if (operand1.length() > 1) {
                    operand1 = operand1.substring(0, operand1.length() - 1);
                }
                else if (operand1.length() == 1) {
                    operand1 = "0";
                }
                field.setText(operand1 + operator + operand2);
            }
            else if (isOperator(action) && !operator.isEmpty()) {
                if (operand2.isEmpty()) {
                    operator = action;
                    field.setText(operand1 + action);
                }
                else {
                    String res = performOperation(Double.parseDouble(operand1), Double.parseDouble(operand2));
                    if (res.equals(DIVISIONBYZERO)) {
                        operand1 = "0";
                        operand2 = "";
                        operator = "";
                        field.setText(res);
                    }
                    else {
                        if (res.charAt(res.length() - 1) == '0') {
                            res = res.substring(0, res.length() - 2);
                        }

                        wasPoint = false;
                        operand1 = res;
                        operator = action;
                        operand2 = "";
                        field.setText(operand1 + operator);
                    }
                }
            }

            else if (action.equals("=") && !operator.isEmpty()) {
                if (operand2.isEmpty()) {
                    field.setText(operand1);
                }
                else {
                    String res = performOperation(Double.parseDouble(operand1), Double.parseDouble(operand2));

                    if (res.equals(DIVISIONBYZERO)) {
                        operand1 = "0";
                    }
                    else {
                        if (res.charAt(res.length() - 1) == '0') {
                            res = res.substring(0, res.length() - 2);
                        }
                        wasPoint = res.contains(POINT);
                        operand1 = res;
                    }
                    operand2 = "";
                    operator = "";
                    field.setText(res);
                }
            }
            else if (Character.isDigit(action.charAt(0))) {
                if (operator.isEmpty()) {
                    operand1 += action;
                }
                else {
                    operand2 += action;
                }
                field.setText(operand1 + operator + operand2);
            }
            else if (isOperator(action)) {
                operator = action;
                field.setText(operand1 + operator);
                wasPoint = false;
            }
        }
    }

    String performOperation(double op1, double op2) {
        String res;
        switch (operator) {
            case PLUS:
                res = Double.toString(op1 + op2);
                break;
            case MINUS:
                res = Double.toString(op1 - op2);
                break;
            case MULT:
                res = Double.toString(op1 * op2);
                break;
            default:
                if (operand2.equals("0")) {
                    res = DIVISIONBYZERO;
                } else {
                    res = Double.toString(op1 / op2);
                }
                break;
        }
        return res;
    }
    boolean isOperator(String str) {
        return str.equals(PLUS) || str.equals(MULT) || str.equals(MINUS) || str.equals(DIVIDE);
    }

}