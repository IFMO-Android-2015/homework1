package com.example.ilnarsabirzyanov.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.EventListener;

public class MainActivity extends AppCompatActivity {

    String token = "";
    View lastButton = null;
    Double last = null;
    String operation = "calc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View[] DIGITS, OPERATIONS;
        DIGITS = new View[]{findViewById(R.id.zero), findViewById(R.id.one), findViewById(R.id.two), findViewById(R.id.three), findViewById(R.id.four),
                findViewById(R.id.five), findViewById(R.id.six), findViewById(R.id.seven), findViewById(R.id.eight),
                findViewById(R.id.nine), findViewById(R.id.dot)
        };
        OPERATIONS = new View[]{findViewById(R.id.add), findViewById(R.id.subtract),
                findViewById(R.id.multiply), findViewById(R.id.divide), findViewById(R.id.calc)
        };
        for (final View button : DIGITS) {
            if (button == null) {
                continue;
            }
            button.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!Arrays.asList(DIGITS).contains(lastButton)) {
                                if (!token.equals(""))
                                    last = Double.valueOf(token);
                                token = "";
                            }
                            token = token + ((Button) button).getText().toString();
                            ((TextView) findViewById(R.id.token)).setText(token);
                            lastButton = button;
                        }
                    }
            );
        }
        for (final View button : OPERATIONS) {
            button.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Arrays.asList(DIGITS).contains(lastButton) && !token.equals("")) {
                                Double result = null;
                                switch (operation) {
                                    case "add":
                                        //token = Double.toString(last + Double.valueOf(token));
                                        result = last + Double.valueOf(token);
                                        break;
                                    case "subtract":
                                        //token = Double.toString(last - Double.valueOf(token));
                                        result = last - Double.valueOf(token);
                                        break;
                                    case "multiply":
                                        //token = Double.toString(last * Double.valueOf(token));
                                        result = last * Double.valueOf(token);
                                        break;
                                    case "divide":
                                        //token = Double.toString(last / Double.valueOf(token));
                                        result = last / Double.valueOf(token);
                                        break;
                                }
                                if (result != null) {
                                    if (result.longValue() == result) {
                                        token = Long.toString(result.longValue());
                                    } else {
                                        token = Double.toString(result);
                                    }
                                }
                                ((TextView) findViewById(R.id.token)).setText(token);
                            }
                            operation = getResources().getResourceEntryName(v.getId());
                            lastButton = button;
                        }
                    }
            );
        }
        findViewById(R.id.clear).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (token.length() > 0) {
                            token = token.substring(0, token.length() - 1);
                            ((TextView) findViewById(R.id.token)).setText(token);
                        }
                    }
                }
        );
        if (savedInstanceState != null) {
            token = savedInstanceState.getString("token");
            ((TextView) findViewById(R.id.token)).setText(token);
            if (savedInstanceState.getInt("lastButtonID") != 0) {
                lastButton = findViewById(savedInstanceState.getInt("lastButtonID"));
            }
            last = savedInstanceState.getDouble("last");
            operation = savedInstanceState.getString("operation");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("token", token);
        if (lastButton != null)
            outState.putInt("lastButtonID", lastButton.getId());
        if (last != null) {
            outState.putDouble("last", last);
        }
        outState.putString("operation", operation);
    }
}
