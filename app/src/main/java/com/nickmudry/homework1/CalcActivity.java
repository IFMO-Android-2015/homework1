package com.nickmudry.homework1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity {

    String number;
    double result;
    char operation;
    TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        number = new String();
        operation = 0;
        res = (TextView) findViewById(R.id.textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDigitPressed(View view) {
        String digit = ((Button) view).getText().toString();
        res.append(digit);
        number += digit;
    }

    public void onEvaluatePressed(View view) {
        try {
            String func = ((Button) view).getText().toString();
            switch (operation) {
                case '+': {
                    result += Double.valueOf(number);
                }
                break;
                case '-': {
                    result -= Double.valueOf(number);
                }
                break;
                case '*': {
                    result *= Double.valueOf(number);
                }
                break;
                case '/': {
                    result /= Double.valueOf(number);
                }
                break;
                case '=': {
                }
                break;
                default: {
                    result = Double.valueOf(number);
                }
            }
            res.setText(Double.toString(result) + ((func.equals("=")) ? "" : (" " + func + " ")));
            number = "";
            operation = (char) func.charAt(0);
        } catch (Throwable e) {
        }
    }

    public void onClearPressed(View view) {
        String op = ((Button) view).getText().toString();
        if (op.equals("clear")) {
            number = new String();
            operation = 0;
            result = 0;
            res.setText("");
        } else {
            if (!(number.length() == 0)) {
                number = number.substring(0, number.length() - 1);
                res.setText(res.getText().toString().substring(0, res.getText().length() - 1));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("textview", res.getText().toString());
        outState.putString("number", number);
        outState.putDouble("res", result);
        outState.putChar("operation", operation);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        number = savedInstanceState.getString("number");
        result = savedInstanceState.getDouble("res");
        res.setText(savedInstanceState.getString("textview"));
        operation = savedInstanceState.getChar("operation");
    }
}
