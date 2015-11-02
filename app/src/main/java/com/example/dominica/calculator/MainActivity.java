package com.example.dominica.calculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.*;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    Button one, two, three, four, five, six, seven, eight, nine, zero;
    Button dot;
    Button exp, pi;
    Button clear, reset, equal;
    Button plus, subtract, divide, multiply;
    TextView textOut;

    boolean clearDisplay = true;
    boolean doubleOperation = false;

    BigDecimal number1 = BigDecimal.ZERO;
    BigDecimal number2 = BigDecimal.ZERO;
    String answer = "";

    Button currentOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen);
        textOut = (TextView) findViewById(R.id.textOut);
        setNumberButtons();
        setOperationButtons();
        setClosingButtons();
        currentOperation = equal;
    }

    OnClickListener clickOnOperation = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = textOut.getText().toString();
            if ((!doubleOperation || currentOperation == equal) && !text.equals("") && !text.equals("-")
                    &&  (!text.equals("ERROR")) && !(text.charAt(text.length() - 1) == '.')) {

                number2 = new BigDecimal(textOut.getText().toString());
                executeOperation(currentOperation);
                currentOperation = (Button) findViewById(v.getId());
                clearDisplay = true;
                doubleOperation = true;
            }
        }
    };
    private void clearAll () {
        number1 = BigDecimal.ZERO;
        number2 = BigDecimal.ZERO;
        doubleOperation = true;
        currentOperation = equal;
        clearDisplay = true;
    }
    OnClickListener clickOnNumber = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Button number = (Button) findViewById(v.getId());
            if (clearDisplay) {
                textOut.setText("");
                clearDisplay = false;
            }
            doubleOperation = currentOperation == equal;
            switch (number.getText().toString()) {
                case "pi":
                    textOut.setText("3.1415926");
                    break;
                case "e":
                    textOut.setText("2.7182818");
                    break;
                default:
                    textOut.append(number.getText());
                    break;
            }


        }
    };
    protected void setOperationButtons() {
        plus = (Button) findViewById(R.id.plus);
        plus.setOnClickListener(clickOnOperation);
        subtract = (Button) findViewById(R.id.subtract);
        subtract.setOnClickListener(clickOnOperation);
        multiply = (Button) findViewById(R.id.multiply);
        multiply.setOnClickListener(clickOnOperation);
        divide = (Button) findViewById(R.id.divide);
        divide.setOnClickListener(clickOnOperation);
        equal = (Button) findViewById(R.id.equal);
        equal.setOnClickListener(clickOnOperation);
    }

    void executeOperation(View v) {
        switch (v.getId()) {
            case R.id.plus:
                answer = number1.add(number2).toString();
                break;
            case R.id.subtract:
                answer = number1.subtract(number2).toString();
                break;
            case R.id.multiply:
                answer = number1.multiply(number2).toString();
                break;
            case R.id.divide:
                if (number2.equals(BigDecimal.ZERO)) {
                    answer = "ERROR";
                    clearAll();
                } else {
                    answer = number1.divide(number2, 8, BigDecimal.ROUND_HALF_EVEN).toString();
                }
                break;
            case R.id.equal:
                answer = number2.toString();
                number1 = number2;
                clearDisplay = true;
                break;
            default:
                break;
        }
        while (answer.length() > 1 && answer.charAt(answer.length() - 1) == '0' || answer.charAt(answer.length() - 1) == '.') {
            answer = answer.substring(0, answer.length() - 1);
        }
        textOut.setText(answer);
        number2 = BigDecimal.ZERO;
    }
    OnClickListener clickOnClose = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.reset:
                    clearAll();
                    textOut.setText("");
                    break;
                case R.id.clear:
                    String buffer = textOut.getText().toString();
                    if (buffer.length() > 0) {
                        textOut.setText(buffer.substring(0, buffer.length() - 1));
                    }
                    currentOperation = equal;
            }
        }
    };
    protected void setClosingButtons() {
        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(clickOnClose);
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(clickOnClose);
    }

    protected void setNumberButtons() {
        one = (Button) findViewById(R.id.one);
        one.setOnClickListener(clickOnNumber);
        two = (Button) findViewById(R.id.two);
        two.setOnClickListener(clickOnNumber);
        three = (Button) findViewById(R.id.three);
        three.setOnClickListener(clickOnNumber);
        four = (Button) findViewById(R.id.four);
        four.setOnClickListener(clickOnNumber);
        five = (Button) findViewById(R.id.five);
        five.setOnClickListener(clickOnNumber);
        six = (Button) findViewById(R.id.six);
        six.setOnClickListener(clickOnNumber);
        seven = (Button) findViewById(R.id.seven);
        seven.setOnClickListener(clickOnNumber);
        eight = (Button) findViewById(R.id.eight);
        eight.setOnClickListener(clickOnNumber);
        nine = (Button) findViewById(R.id.nine);
        nine.setOnClickListener(clickOnNumber);
        zero = (Button) findViewById(R.id.zero);
        zero.setOnClickListener(clickOnNumber);
        dot = (Button) findViewById(R.id.dot);
        dot.setOnClickListener(clickOnNumber);
        exp = (Button) findViewById(R.id.exp);
        exp.setOnClickListener(clickOnNumber);
        pi = (Button) findViewById(R.id.pi);
        pi.setOnClickListener(clickOnNumber);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        number1 = new BigDecimal(savedInstanceState.getString("number1"));
        number2 = new BigDecimal(savedInstanceState.getString("number2"));
        currentOperation = (Button) findViewById(savedInstanceState.getInt("currentOperation"));
        textOut = (TextView) findViewById(savedInstanceState.getInt("textOut"));
        textOut.setText(savedInstanceState.getString("screenValue"));
        doubleOperation = savedInstanceState.getBoolean("doubleOperation");
        clearDisplay = savedInstanceState.getBoolean("clearDisplay");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("number1", number1.toString());
        outState.putString("number2", number2.toString());
        outState.putInt("currentOperation", currentOperation.getId());
        outState.putInt("textOut", textOut.getId());
        outState.putString("screenValue", textOut.getText().toString());
        outState.putBoolean("doubleOperation", doubleOperation);
        outState.putBoolean("clearDisplay", clearDisplay);


    }
}