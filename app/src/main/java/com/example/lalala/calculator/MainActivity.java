package com.example.lalala.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button digits[], operations[], lastButton;
    String token = "", operation = "calc";
    Double res = null;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);

        digits = new Button[] {
                (Button)findViewById(R.id.zero), (Button)findViewById(R.id.one),
                (Button)findViewById(R.id.two), (Button)findViewById(R.id.three),
                (Button)findViewById(R.id.four), (Button)findViewById(R.id.five),
                (Button)findViewById(R.id.six), (Button)findViewById(R.id.seven),
                (Button)findViewById(R.id.eight), (Button)findViewById(R.id.nine),
                (Button)findViewById(R.id.point)
        };
        operations = new Button[] {
                (Button)findViewById(R.id.add), (Button)findViewById(R.id.subtract),
                (Button)findViewById(R.id.multiply), (Button)findViewById(R.id.divide),
                (Button)findViewById(R.id.calc)
        };

        for (final Button button : digits) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Arrays.asList(digits).contains(lastButton)) {
                        if (!token.equals("")) {
                            res = Double.valueOf(token);
                        }
                        token = "";
                    }
                    token = token + button.getText();
                    textView.setText(token);
                    lastButton = button;
                }
            });
        }

        for (final Button button : operations) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Arrays.asList(digits).contains(lastButton) && !token.equals("")) {
                        Double result = null;
                        switch (operation) {
                            case "add":
                                result = res + Double.valueOf(token);
                                break;
                            case "subtract":
                                result = res - Double.valueOf(token);
                                break;
                            case "multiply":
                                result  = res * Double.valueOf(token);
                                break;
                            case "divide":
                                result = res / Double.valueOf(token);
                                break;
                        }
                        if (result != null) {
                            if (result.longValue() == result) {
                                token = Long.toString(result.longValue());
                            } else {
                                token = Double.toString(result);
                            }
                            textView.setText(token);
                        }
                        operation = getResources().getResourceEntryName(button.getId());
                        lastButton  = button;
                    }
                }
            });
        }

        ((Button)findViewById(R.id.clear)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (token.length() > 0) {
                    token = token.substring(0, token.length() - 1);
                    textView.setText(token);
                }
            }
        });

        final Button button = (Button)findViewById(R.id.point);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Arrays.asList(digits).contains(lastButton)) {
                    if (!token.equals("")) {
                        res = Double.valueOf(token);
                    }
                    token = "";
                }
                if (token.contains(button.getText())) return;
                if (token.equals("")) token = "0";
                token = token + button.getText();
                textView.setText(token);
                lastButton = button;
            }
        });

        if (savedInstanceState != null) {
            token = savedInstanceState.getString("token");
            operation = savedInstanceState.getString("operation");
            if (savedInstanceState.getInt("lastButton") != 0) {
                lastButton = (Button)findViewById(savedInstanceState.getInt("lastButton"));
            }
            res = savedInstanceState.getDouble("res");
            textView.setText(token);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("token", token);
        outState.putString("operation", operation);
        if (lastButton != null) {
            outState.putInt("lastButton", lastButton.getId());
        }
        if (res != null) {
            outState.putDouble("res", res);
        }
    }
}
