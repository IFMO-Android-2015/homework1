package com.example.qurbonzoda.simplecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String POW = "^";
    private static final String CANNOT_CALCULATE = "Cannot calculate";
    private EditText screen;
    StringBuilder expression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expression = new StringBuilder();
        screen = (EditText) findViewById(R.id.screen);
        int[] idList = {R.id.button_add, R.id.button_sub, R.id.button_mul, R.id.button_div, R.id.button_pow,
                R.id.button_mod, R.id.button_zero, R.id.button_one, R.id.button_two, R.id.button_three,
                R.id.button_four, R.id.button_five, R.id.button_six, R.id.button_seven, R.id.button_eight,
                R.id.button_nine, R.id.button_equals, R.id.button_dot, R.id.button_del, R.id.button_clear
        };

        for (int id : idList) {
            View view = findViewById(id);
            view.setOnClickListener(this);
        }
    }

    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        boolean crashed = false;
        switch (view.getId()) {
            case R.id.button_pow:
                expression.append(POW);
                break;
            case R.id.button_clear:
                expression = new StringBuilder();
                break;
            case R.id.button_del:
                if (expression.length() != 0) {
                    expression.deleteCharAt(expression.length() - 1);
                }
                break;
            case R.id.button_equals:
                try {
                    Calculator calculator = new SimpleCalculator();
                    expression = new StringBuilder(Double.toString(calculator.calculate(expression.toString())));
                } catch (Exception e) {
                    crashed = true;
                    screen.setText(CANNOT_CALCULATE);
                    expression = new StringBuilder();
                }
                break;
            default:
                expression.append(buttonText);
        }
        if (!crashed) {
            screen.setText(expression);
            screen.setSelection(screen.getText().length());
        }
    }
}
