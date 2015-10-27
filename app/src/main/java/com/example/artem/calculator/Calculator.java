package com.example.artem.calculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class Calculator extends AppCompatActivity {

    private BigDecimal firstNum;
    private Functions.Function function;

    private StringBuilder secondNumStr;
    private boolean isDotEntered;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        textView = (TextView) findViewById(R.id.text);
        onClearClicked(null);

        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    private void clearSecondNum() {
        secondNumStr = new StringBuilder();
        isDotEntered = false;

        renderSecondNum();
    }

    private void renderSecondNum() {
        textView.setText(secondNumStr.toString());
    }


    public void onClearClicked(View view) {
        firstNum = null;
        function = new Functions.FlipConst();

        clearSecondNum();
    }


    public void onDigitClicked(View view) {
        Button button = (Button) view;
        secondNumStr.append(button.getText());
        renderSecondNum();
    }

    public void onDotClicked(View view) {
        if (secondNumStr.length() > 0 && !isDotEntered) {
            isDotEntered = true;
            secondNumStr.append('.');
            renderSecondNum();
        }
    }

    public void onSignClicked(View view) {
        if (secondNumStr.length() > 0) {
            if (secondNumStr.charAt(0) != '-') {
                secondNumStr.insert(0, '-');
            } else {
                secondNumStr.delete(0, 1);
            }
            renderSecondNum();
        }
    }
}
