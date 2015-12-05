package ru.ifmo.android_2015.calculator2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends Activity {

    private TextView screen;
    int state; //0 - first number, 1 - operation, 2 - second number
    StringBuilder firstNumber;
    String operation;
    StringBuilder secondNumber;
    boolean firstZero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_calculator);

        Button deleteButton = (Button) findViewById(R.id.delete_button);
        deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongDeleteClick(v);
                return true;
            }

        });

        screen = (TextView) findViewById(R.id.screen);

        if(savedInstanceState == null) {
            firstNumber = new StringBuilder("0.0");
            secondNumber = new StringBuilder();
            firstZero = true;
            state = 0;
        } else {
            firstNumber = new StringBuilder(savedInstanceState.getString("first_number"));
            secondNumber = new StringBuilder(savedInstanceState.getString("second_number"));
            operation = savedInstanceState.getString("operation");
            firstZero = savedInstanceState.getBoolean("first_zero");
            state = savedInstanceState.getInt("state");
            switch(state) {
                case 0:
                    screen.setText(firstNumber.toString());
                    break;
                case 1:
                    screen.setText(operation);
                    break;
                case 2:
                    screen.setText(secondNumber.toString());
                    break;
            }
        }
    }

    public void onOperationClick(View v) {
        CharSequence text = ((Button) v).getText();
        if(state != 2) {
            state = 1;
            operation = text.toString();
            screen.setText(operation);
        }
    }

    boolean containsDot(StringBuilder b) {
        for(int i = 0; i < b.length(); i++) {
            if(b.charAt(i) == '.') {
                return true;
            }
        }
        return false;
    }

    public void onDigitClick(View v) {

        CharSequence text = ((Button) v).getText();

        switch(state) {
            case 0:
                if(firstZero) {
                    firstNumber.delete(0, firstNumber.length());
                    firstZero = false;
                }
                if(text.charAt(0) != '.' || !containsDot(firstNumber)) {
                    firstNumber.append(text);
                    screen.setText(firstNumber.toString());
                }
                break;
            case 1:
                state = 2;
                secondNumber.delete(0, secondNumber.length());
                secondNumber.append(text);
                screen.setText(secondNumber.toString());
                break;
            case 2:
                if(text.charAt(0) != '.' || !containsDot(secondNumber)) {
                    secondNumber.append(text);
                    screen.setText(secondNumber.toString());
                }
                break;
        }
    }

    public void onEqualsClick(View v) {
        if(state == 2) {
            state = 0;
            firstZero = true;
            double first = Double.parseDouble(firstNumber.toString());
            double second = Double.parseDouble(secondNumber.toString());
            double result = 0.0;
            switch(operation) {
                case "+":
                    result = first + second;
                    break;
                case "-":
                    result = first - second;
                    break;
                case "*":
                    result = first * second;
                    break;
                case "/":
                    result = first / second;
                    break;
            }
            firstNumber.delete(0, firstNumber.length());
            firstNumber.append(String.valueOf(result));
            screen.setText(firstNumber.toString());
        }
    }

    public void onDeleteClick(View v) {

    }

    public void onLongDeleteClick(View v) {
        state = 0;
        firstZero = true;
        firstNumber.delete(0, firstNumber.length());
        firstNumber.append("0.0");
        screen.setText("0.0");
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("state", state);
        bundle.putString("first_number", firstNumber.toString());
        bundle.putString("second_number", secondNumber.toString());
        bundle.putString("operation", operation);
        bundle.putBoolean("first_zero", firstZero);
    }

}
