package com.example.hw1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends Activity {

    String st = "";
    double first = 0.0;
    double second = 0.0;
    String mark = "n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen);

        final TextView text = (TextView) findViewById(R.id.myText);
        final Button[] but = new Button[10];
        but[1] = (Button) findViewById(R.id.button1);
        but[2] = (Button) findViewById(R.id.button2);
        but[3] = (Button) findViewById(R.id.button3);
        but[4] = (Button) findViewById(R.id.button4);
        but[5] = (Button) findViewById(R.id.button5);
        but[6] = (Button) findViewById(R.id.button6);
        but[7] = (Button) findViewById(R.id.button7);
        but[8] = (Button) findViewById(R.id.button8);
        but[9] = (Button) findViewById(R.id.button9);
        but[0] = (Button) findViewById(R.id.button0);
        Button buttonCE = (Button) findViewById(R.id.buttonCE);
        final Button buttonC = (Button) findViewById(R.id.buttonC);
        Button buttonArrow = (Button) findViewById(R.id.buttonArrow);
        final Button buttonSlash = (Button) findViewById(R.id.buttonSlash);
        final Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        final Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        Button buttonPM = (Button) findViewById(R.id.buttonPM);
        Button buttonPoint = (Button) findViewById(R.id.buttonPoint);
        Button buttonEq = (Button) findViewById(R.id.buttonEq);
        final Button buttonAst = (Button) findViewById(R.id.buttonAst);

        String past = st;
        text.setText(past);
        text.setText(past);

        View.OnClickListener onClicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    if (v.getId() == but[i].getId()) {
                        if (st.length() < 25)
                            st += Integer.toString(i);
                        text.setText(st);
                        break;
                    }
                }
                switch (v.getId()) {
                    case R.id.buttonArrow:
                        //delete last symbol
                        if (st.length() > 0) {
                            st = st.substring(0, st.length() - 1);
                        }
                        if (st.equals("-")) {
                            st = "";
                        }
                        text.setText(st);
                        break;
                    case R.id.buttonCE:
                        //delete one number
                        st = "";
                        text.setText(st);
                        break;
                    case R.id.buttonC:
                        //delete two numbers
                        st = "";
                        first = 0.0;
                        mark = "n";
                        text.setText(st);
                        break;
                    case R.id.buttonPoint:
                        if (st.length() == 0) {
                            st = "0.";
                        }
                        if (!st.contains(".")) {
                            st += ".";
                        }
                        text.setText(st);
                        break;
                    case R.id.buttonPM:
                        if (st.length() > 0) {
                            if (st.charAt(0) == '-') {
                                st = st.substring(1, st.length());
                            } else {
                                st = "-" + st;
                            }
                        }
                        text.setText(st);
                        break;
                    case R.id.buttonPlus:
                    case R.id.buttonMinus:
                    case R.id.buttonAst:
                    case R.id.buttonSlash:
                        if (!mark.equals("n") && st.length() > 0) {
                            second = Double.parseDouble(st);
                            makeOperation();
                            if (Double.toString(first).contains("Inf") || Double.toString(first).contains("NaN")) {
                                st = "Error";
                                text.setText(st);
                                st = "";
                            }
                            second = 0.0;
                        }
                        if (mark.equals("n") && st.length() > 0) {
                            first = Double.parseDouble(st);
                        }
                        Button b = (Button) v;
                        mark = b.getText().toString();
                        if (text.getText().equals("Error")) {
                            first = 0.0;
                            mark = "n";
                        } else {
                            st = "";
                            text.setText(st);
                        }
                        break;
                    case R.id.buttonEq:
                        if (mark.equals("n")) {
                            if (st.length() > 0 && st.charAt(st.length() - 1) == '.') {
                                st = st.substring(0, st.length() - 1);
                            }
                            if (st.equals("-0")) {
                                st = "0";
                            }
                            if (!st.isEmpty()) {
                                st = "Answer is " + st;
                            }
                            text.setText(st);
                            st = "";
                        } else {
                            if (st.length() > 0) {
                                second = Double.parseDouble(st);
                            }
                            makeOperation();
                            if (!Double.toString(first).contains("Inf") && !Double.toString(first).contains("NaN")) {
                                first = new BigDecimal(first).setScale(6, RoundingMode.HALF_UP).doubleValue();
                            }
                            st = Double.toString(first);
                            if (st.length() > 2) {
                                if (st.substring(st.length() - 2, st.length()).equals(".0")) {
                                    st = st.substring(0, st.length() - 2);
                                }
                            }
                            if (st.equals("NaN") || st.equals("Infinity") || st.equals("-Infinity")) {
                                st = "Error";
                            } else {
                                if (st.equals("-0")) {
                                    st = "0";
                                }
                                st = "Answer is " + st;
                            }
                            text.setText(st);
                            first = second = 0.0;
                            st = "";
                            mark = "n";
                        }
                        break;
                }
            }
        };
        but[0].setOnClickListener(onClicker);
        but[1].setOnClickListener(onClicker);
        but[2].setOnClickListener(onClicker);
        but[3].setOnClickListener(onClicker);
        but[4].setOnClickListener(onClicker);
        but[5].setOnClickListener(onClicker);
        but[6].setOnClickListener(onClicker);
        but[7].setOnClickListener(onClicker);
        but[8].setOnClickListener(onClicker);
        but[9].setOnClickListener(onClicker);
        buttonArrow.setOnClickListener(onClicker);
        buttonC.setOnClickListener(onClicker);
        buttonCE.setOnClickListener(onClicker);
        buttonPoint.setOnClickListener(onClicker);
        buttonPM.setOnClickListener(onClicker);
        buttonPlus.setOnClickListener(onClicker);
        buttonEq.setOnClickListener(onClicker);
        buttonAst.setOnClickListener(onClicker);
        buttonSlash.setOnClickListener(onClicker);
        buttonMinus.setOnClickListener(onClicker);
    }

    private void makeOperation() {
        switch (mark) {
            case "+":
                first += second;
                break;
            case "-":
                first -= second;
                break;
            case "*":
                first *= second;
                break;
            case "/":
                first /= second;
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        first = savedInstanceState.getDouble("f");
        second = savedInstanceState.getDouble("s");
        mark = savedInstanceState.getString("m");
        st = savedInstanceState.getString("str");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("f", first);
        outState.putDouble("s", second);
        outState.putString("m", mark);
        outState.putString("str", st);
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
