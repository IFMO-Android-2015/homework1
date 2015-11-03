package com.ifmo.kurkin.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView display;
    private boolean isPointInNumber;
    private boolean expectedNum;
    private Double acc;
    private int curOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        display = (TextView) findViewById(R.id.display);
        isPointInNumber = false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backspase:
                if (display.getText().equals("")) {
                    return;
                } else {
                    String num = display.getText().toString();
                    if (num.charAt(num.length() - 1) == '.') {
                        num = num.substring(0, num.length() - 1);
                        isPointInNumber = false;
                    } else if (num.length() == 1) {
                        num = "";
                    } else if (num.length() == 2 & num.charAt(0) == '-') {
                        num = "";
                    } else {
                        num = num.substring(0, num.length() - 1);
                    }
                    display.setText(num);
                }
                break;
            case R.id.minus:
                if (display.getText().equals("")) {
                    return;
                } else {
                    String num = display.getText().toString();
                    if (num.charAt(0) == '-') {
                        display.setText(num.subSequence(1, num.length()));
                    } else {
                        display.setText("-"+num);
                    }
                }
                break;
            case R.id.point:
                if (isPointInNumber) {
                    return;
                } else {
                    if (expectedNum) {
                        display.setText("0.");
                        expectedNum = !expectedNum;
                    } else {
                        if (display.getText().equals("")) {
                            display.setText("0.");
                        } else {
                            display.setText(display.getText().toString() + ".");
                        }
                        isPointInNumber = true;
                    }
                }
                break;
            case R.id.zero:
            case R.id.one:
            case R.id.two:
            case R.id.three:
            case R.id.four:
            case R.id.five:
            case R.id.six:
            case R.id.seven:
            case R.id.eight:
            case R.id.nine:
                if (expectedNum) {
                    display.setText("");
                    expectedNum = !expectedNum;
                }
                display.setText(display.getText().toString() + ((Button) v).getText().toString());
                break;
            case R.id.add:
            case R.id.sub:
            case R.id.mul:
            case R.id.div:
                if (display.getText().toString().equals("")) {
                    if (acc != null) curOperation = v.getId();
                    return;
                } else {
                    if (expectedNum) {
                        curOperation = v.getId();
                        return;
                    }
                    if (acc == null) {
                        acc = Double.parseDouble(display.getText().toString());
                        curOperation = v.getId();
                        display.setText("");
                    } else {
                        double temp = Double.parseDouble(display.getText().toString());
                        switch (curOperation) {
                            case R.id.add:
                                acc = acc + temp;
                                break;
                            case R.id.sub:
                                acc = acc - temp;
                                break;
                            case R.id.div:
                                acc = acc / temp;
                                break;
                            case R.id.mul:
                                acc = acc * temp;
                                break;
                        }
                        display.setText(acc.toString());
                        curOperation = v.getId();
                        expectedNum = true;
                    }
                }
                break;
            case R.id.clear:
                display.setText("");
                isPointInNumber = false;
                display.setTextSize(50);
                curOperation = 0;
                acc = null;
                expectedNum = false;
                break;
            case R.id.equals:
                if (display.getText().equals("")) {
                    return;
                }
                double temp = Double.parseDouble(display.getText().toString());
                switch (curOperation) {
                    case R.id.add:
                        acc = acc + temp;
                        break;
                    case R.id.sub:
                        acc = acc - temp;
                        break;
                    case R.id.div:
                        acc = acc / temp;
                        break;
                    case R.id.mul:
                        acc = acc * temp;
                        break;
                    default:
                        acc = temp;
                        break;
                }
                display.setText(acc.toString());
                acc = null;
                curOperation = 0;
                expectedNum = true;
                break;
        }
    }
}
