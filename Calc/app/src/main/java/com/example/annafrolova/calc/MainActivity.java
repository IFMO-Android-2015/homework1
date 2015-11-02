package com.example.annafrolova.calc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
    private static final String NUM_1 = "num1";
    private static final String NUM_2 = "num2";
    private static final String CURRENT_NUMBER = "currentNumber";
    private static final String OPER = "oper";
    private String currentNumber = "0";
    private TextView tvLCD;
    private double num1, num2 = 0.0;
    private char oper = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLCD = (TextView) findViewById(R.id.tvLCD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvLCD.setText(currentNumber);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        num1 = savedInstanceState.getDouble(NUM_1);
        num2 = savedInstanceState.getDouble(NUM_2);
        currentNumber = savedInstanceState.getString(CURRENT_NUMBER);
        oper = savedInstanceState.getChar(OPER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void btnClick(View view) {
        if (!(view instanceof Button))
            return ;
        Button button = (Button)view;
        String text = button.getText().toString();
        if (text.charAt(0) <= '9' && text.charAt(0) >= '0') {
            if (currentNumber.equals("0")) {
                currentNumber = text.charAt(0)+"";
            } else {
                currentNumber += text.charAt(0);
            }
        } else if (text.equals("+/-")) {
            if (currentNumber.charAt(0) == '-') {
                currentNumber = currentNumber.substring(1);
            } else if (!currentNumber.equals("0")) {
                currentNumber = "-" + currentNumber;
            }
        } else if (text.equals(".")) {
            if (!currentNumber.contains(".")) {
                currentNumber += ".";
            }
        } else if (text.equals("AC")) {
            currentNumber = "0";
            num1 = 0.0;
            num2 = 0.0;
        } else if (text.equals("+")){
            num1 = Double.parseDouble(currentNumber);
            currentNumber = "0";
            oper = '+';
        } else if (text.equals("-")) {
            num1 = Double.parseDouble(currentNumber);
            currentNumber = "0";
            oper = '-';
        } else if (text.equals("/")) {
            num1 = Double.parseDouble(currentNumber);
            currentNumber = "0";
            oper = '/';
        } else if (text.equals("*")) {
            num1 = Double.parseDouble(currentNumber);
            currentNumber = "0";
            oper = '*';
        } else if (text.equals("%")) {
            num1 = Double.parseDouble(currentNumber);
            num2 = num1 / 100.0;
            currentNumber = /*new DecimalFormat("#.########").format(num2); */String.valueOf(num2);
        } else if (text.equals("=")) {
            num2 = Double.parseDouble(currentNumber);
            switch (oper) {
                case '+':
                    num2 = num1 + num2;
                    break;
                case '-':
                    num2 = num1 - num2;
                    break;
                case '*':
                    num2 *= num1;
                    break;
                case '/':
                    num2 = num1 / num2;
                    break;
            }
            currentNumber = /*new DecimalFormat("#.########").format(num2); */String.valueOf(num2);
        }
        tvLCD.setText(currentNumber);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putDouble(NUM_1, num1);
        outState.putDouble(NUM_2, num2);
        outState.putChar(OPER, oper);
        outState.putString(CURRENT_NUMBER, currentNumber);
        super.onSaveInstanceState(outState);
    }
}
