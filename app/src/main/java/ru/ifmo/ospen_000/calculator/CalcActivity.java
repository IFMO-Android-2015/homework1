package ru.ifmo.ospen_000.calculator;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity  {
    Button one, two, three, four, five, six, seven, eight, nine, minus, plus, mul, div, equal, clear;
    TextView screen;
    double result = 0;
    int next = 0;
    boolean hadDot = false;
    char operation = 'N';


    private boolean previosIsZero(){
        if (screen.getText().equals(new String("0"))){
            return true;
        } else {
            return false;
        }
    }

    private String numToString(double res){
        if ((res % 1) == 0){
            return String.valueOf((int)res);
        } else {
            return String.valueOf(res);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        Log.d("MyLogs", "Create");
        screen = (TextView) findViewById(R.id.textView);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five= (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        plus = (Button) findViewById(R.id.plus);
        div= (Button) findViewById(R.id.div);
        mul = (Button) findViewById(R.id.mul);
        minus = (Button) findViewById(R.id.minus);

    }

    public void onClick(View v) {
        if (previosIsZero()){
            screen.setText("");
        }
        if (screen.getText().length() < 20) {
            switch (v.getId()) {
                case R.id.one:
                    screen.append("1");
                    break;
                case R.id.two:
                    screen.append("2");
                    break;
                case R.id.three:
                    screen.append("3");
                    break;
                case R.id.four:
                    screen.append("4");
                    break;
                case R.id.five:
                    screen.append("5");
                    break;
                case R.id.six:
                    screen.append("6");
                    break;
                case R.id.seven:
                    screen.append("7");
                    break;
                case R.id.eight:
                    screen.append("8");
                    break;
                case R.id.nine:
                    screen.append("9");
                    break;
                case R.id.zero:
                    screen.append("0");
                    break;
                case R.id.coma:
                    if (!hadDot){
                        screen.append(".");
                        hadDot = true;
                    }
                    break;
            }
        }
    }

    public void onClickOperation(View v){
        if (screen.getText().length() < 19) {
            if (operation != 'N') {
                onClickEqual(v);
            }
            result = Double.parseDouble(screen.getText().toString());
            next = screen.getText().length();
            Log.d("MyLogs", String.valueOf(next));
            switch (v.getId()) {
                case R.id.plus:
                    operation = '+';
                    break;
                case R.id.minus:
                    operation = '-';
                    break;
                case R.id.mul:
                    operation = '*';
                    break;
                case R.id.div:
                    operation = '/';
                    break;
            }
            screen.append(String.valueOf(operation));
        }
    }

    public void onClickEqual(View V){
        Log.d("equalLog", String.valueOf(result) + String.valueOf(operation));
        try {
            switch (operation) {
                case '+':
                    result += Double.parseDouble(screen.getText().toString().substring(next + 1 ));
                    break;
                case '-':
                    result += Double.parseDouble(screen.getText().toString().substring(next));
                    break;
                case '*':
                    result *= Double.parseDouble(screen.getText().toString().substring(next + 1));
                    break;
                case '/':
                    result /= Double.parseDouble(screen.getText().toString().substring(next + 1));
                    break;
                case 'N':
                    result = Double.parseDouble(screen.getText().toString().substring(next));
                    break;
            }
        } catch (Exception e){
            screen.setText(e.getMessage());
        }
        Log.d("equalLog", String.valueOf(result) + String.valueOf(operation));
        operation = 'N';
        hadDot = false;
        next = 0;

        screen.setText(numToString(result));

    }

    public void onClickClear(View v) {
        screen.setText("0");

      //  screen.append("0");
        Log.d("MyLogs", String.valueOf(previosIsZero()));
        result = 0;
        next = 0;
        operation = 'N';
        hadDot = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putChar("operation", operation);
        outState.putBoolean("hadDot", hadDot);
        outState.putDouble("result", result);
        outState.putString("text", screen.getText().toString());
        outState.putInt("next", next);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        operation = savedInstanceState.getChar("operation");
        hadDot = savedInstanceState.getBoolean("hadDot");
        result = savedInstanceState.getDouble("result");
        screen.setText(savedInstanceState.getString("next"));
        next = savedInstanceState.getInt("text");
    }
}
