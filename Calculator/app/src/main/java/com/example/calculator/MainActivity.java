package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.calculator.R.string.*;

public class MainActivity extends AppCompatActivity {

    private final int DIGIT = 0;
    private final int OPERATION = 1;

    private final int NO_OP = 0;
    private final int ADD = 1;
    private final int SUB = 2;
    private final int MUL = 3;
    private final int DIV = 4;

    String current;
    int state;
    int operation;
    double currentRes;
    TextView expression;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expression = (TextView)findViewById(R.id.textView);
        current = "0";
        currentRes = 0;
        operation = NO_OP;
        state = OPERATION;
        if (expression != null) {
            expression.setText(current);
        }
    }

    public void onClickDigit(View view){
        Button button = (Button)view;
        if (state == OPERATION) {
            current = "";
        }
        current += button.getText().toString();
        expression.setText(current);
        state = DIGIT;
    }

    public void onClickOperation(View view){
        Button button = (Button)view;
        makePrevOperation();
        switch(button.getText().toString()) {
            case "+":
                operation = ADD;
                break;
            case "-":
                operation = SUB;
                break;
            case "*":
                operation = MUL;
                break;
            case "/":
                operation = DIV;
                break;
        }
        state = OPERATION;
    }

    public void onClickC(View view) {
        current = "0";
        currentRes = 0;
        operation = NO_OP;
        state = OPERATION;
        expression.setText(current);
    }

    public void onClickEquals(View view) {
        makePrevOperation();
        expression.setText(current);
        currentRes = 0;
        state = OPERATION;
        operation = NO_OP;
    }

    private void makePrevOperation() {
        double tmp = Double.parseDouble(current);
        switch(operation) {
            case ADD:
                currentRes += tmp;
                break;
            case SUB:
                currentRes -= tmp;
                break;
            case MUL:
                currentRes *= tmp;
                break;
            case DIV:
                currentRes /= tmp;
                break;
            case NO_OP:
                currentRes = tmp;
                break;
        }
        current = String.valueOf(currentRes);
        expression.setText(current);
    }
}
