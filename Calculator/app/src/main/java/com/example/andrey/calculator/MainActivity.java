package com.example.andrey.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.andrey.calculator.R;

import java.util.Arrays;

public class MainActivity extends Activity {
    String operation = "none";
    Double f = null;
    String s = "0";
    Boolean isSecond = false;
    Boolean lOp = false;
    Boolean isComma = false;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvOut = (TextView) findViewById(R.id.editText);
        Button clear = (Button) findViewById(R.id.clear);
        Button point = (Button) findViewById(R.id.point);


        final View[] DIGITS, OPERATIONS;
        DIGITS = new View[]{findViewById(R.id.but0), findViewById(R.id.but1), findViewById(R.id.but2), findViewById(R.id.but3), findViewById(R.id.but4),
                findViewById(R.id.but5), findViewById(R.id.but6), findViewById(R.id.but7), findViewById(R.id.but8), findViewById(R.id.but9)  };
        OPERATIONS = new View[]{findViewById(R.id.add), findViewById(R.id.sub),
                findViewById(R.id.multiply), findViewById(R.id.divide), findViewById(R.id.equals)
        };
        for (final View button : DIGITS) {
            if (button == null) {
                continue;
            }
            button.setOnClickListener( new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                    if (operation.equals("equals") && lOp == true) {
                        tvOut.setText("0");
                        operation = "none";
                        f = 0.0;
                        isComma = false;
                        isSecond = false;
                    } else if (!operation.equals("none") && lOp == true) {
                        isSecond = true;
                        isComma = false;
                        tvOut.setText("0");
                    }
                    if (tvOut.getText().toString().equals("0")) {
                        tvOut.setText(((Button) button).getText().toString());
                    } else {
                        tvOut.setText(tvOut.getText() + ((Button) button).getText().toString());
                    }
                      lOp = false;
                }
            }
            );


        }

        for (final View button : OPERATIONS) {
            button.setOnClickListener( new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                    s = tvOut.getText().toString();
                    try {
                        calculate();
                        tvOut.setText(Double.toString(f));
                        operation = getResources().getResourceEntryName(v.getId());

                        lOp = true;
                    } catch (Exception e) {
                        tvOut.setText("Ошибка");
                        operation = "equals";
                        lOp = true;
                    }


                    }
            });
        }
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOut.setText("0");
                f = 0.0;
                operation = "none";
                isComma = false;
            }
        });
        point.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                if (!(isComma || operation.equals("equals"))) {
                    tvOut.setText(tvOut.getText() + ".");
                    isComma = true;
                }

            }
        });




    }

    private void calculate() throws ArithmeticException {

        switch (operation) {
            case "add": {
                f = f + Double.valueOf(s);
                return;
            }
            case "sub": {
                f = f - Double.valueOf(s);
                return;
            }
            case "multiply": {
                f = f * Double.valueOf(s);
                return;
            }
            case "divide": {
                if (Double.valueOf(s).equals(0.0)) {
                    throw new ArithmeticException("Error");
                }
                f = f / Double.valueOf(s);
                return;
            }
            case "none": {
                f = Double.valueOf(s);
                return;
            }
            default: {
                return;
            }
        }

    }

    protected  void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("a",operation);
        outState.putDouble("b",f);
        outState.putSerializable("c",isComma);
        outState.putSerializable("d",isSecond);
        outState.putSerializable("e",lOp);
        outState.putString("f",s);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        operation = savedInstanceState.getString("a");
        f = savedInstanceState.getDouble("b");
        isComma = (Boolean) savedInstanceState.get("c");
        isSecond = (Boolean) savedInstanceState.get("d");
        lOp = (Boolean) savedInstanceState.get("e");
        s = savedInstanceState.getString("f");
    }
}
