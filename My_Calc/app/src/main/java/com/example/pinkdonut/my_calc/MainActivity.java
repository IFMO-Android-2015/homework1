package com.example.pinkdonut.my_calc;

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

public class MainActivity extends AppCompatActivity {
    private TextView scrn;
    private float number;
    private String operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrn = (TextView) findViewById(R.id.textView);
        int Buttons[] = {R.id.buttonC, R.id.buttonEq, R.id.buttonDiv, R.id.buttonMul, R.id.buttonPlus, R.id.buttonMinus, R.id.button1, R.id.button2,
                R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button0, R.id.buttonDot};

        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonC:
                        scrn.setText("0");
                        number = 0;
                        operation = "";
                        break;
                    case R.id.buttonDiv:
                        calc("/");
                        break;
                    case R.id.buttonMul:
                        calc("*");
                        break;
                    case R.id.buttonPlus:
                        calc("+");
                        break;
                    case R.id.buttonMinus:
                        calc("-");
                        break;
                    case R.id.buttonEq:
                        getResult();
                        break;
                    default:
                        String number1 = ((Button) v).getText().toString();
                        getKeyboard(number1);
                        break;
                }
            }
        };

        for(int btn:Buttons) {
            View v = (View) findViewById(btn);
            v.setOnClickListener(click);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void calc(String s) {
        number = Float.parseFloat(scrn.getText().toString());
        operation = s;
        scrn.setText("0");
    }

    private void getKeyboard(String s) {
        String curs = scrn.getText().toString();
        if (!(curs.contains(".") && s.equals("."))) {
            if (curs.equals("0") && !s.equals(".")) curs = s;
            else curs += s;
            scrn.setText(curs);
        }
    }

    private void getResult() {
        float f = Float.parseFloat(scrn.getText().toString());
        float res = 0;
        switch (operation) {
            case "/":
                res = number / f;
                break;
            case "*":
                res = number * f;
                break;
            case "+":
                res = number + f;
                break;
            case "-":
                res = number - f;
                break;
        }
     /*   if (operation.equals("/")) {
            res = number / f;
        } else if (operation.equals("*")) {
            res = number * f;
        } else if (operation.equals("+")) {
            res = number + f;
        } else if (operation.equals("-")) {
            res = number - f;
        }*/
        scrn.setText(String.valueOf(res));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
