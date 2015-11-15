package com.example.khorinanton.calculator;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView result;
    boolean error = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
    }

    public void onButtonClick(View button) {
        if (!error) {
            String res = (String) result.getText() + ((Button) button).getText();
            result.setText(res);
        }
    }

    public void onCEClick(View button) {
        result.setText("");
        error = false;
    }

    public void onDeleteClick(View button) {
        if (!error) {
            CharSequence res = result.getText();
            if (res.length() > 0) {
                res = res.subSequence(0, res.length() - 1);
                result.setText(res);
            }
        }
    }

    public void onEqualsClick(View button) {
        if (!error) {
            String res = (String) result.getText();
            if (!res.isEmpty()) {
                try {
                    res = ((Double) Double.parseDouble(res)).toString();
                    result.setText(res);
                } catch (Exception e) {
                    int p = res.indexOf("×");
                    if (p == -1) {
                        p = res.indexOf("÷");
                    }
                    if (p == -1) {
                        p = res.indexOf("+", 1);
                        int p2 = res.indexOf("-", 1);
                        if (p != -1 && p2 != -1) {
                            p = Math.min(p, p2);
                        } else {
                            p = Math.max(p, p2);
                        }
                    }
                    if (p == -1) {
                        result.setText("Wrong expression");
                        error = true;
                    } else {
                        double d1;
                        double d2;
                        try {
                            d1 = Double.parseDouble(res.substring(0, p));
                            d2 = Double.parseDouble(res.substring(p + 1));
                        } catch (Exception ex) {
                            result.setText("Wrong expression");
                            error = true;
                            return;
                        }
                        switch (res.charAt(p)) {
                            case '+':
                                result.setText(((Double) (d1 + d2)).toString());
                                break;
                            case '-':
                                result.setText(((Double) (d1 - d2)).toString());
                                break;
                            case '×':
                                result.setText(((Double) (d1 * d2)).toString());
                                break;
                            case '÷':
                                if (d2 == 0) {
                                    result.setText("Division by zero");
                                    error = true;
                                    return;
                                }
                                result.setText(((Double) (d1 / d2)).toString());
                                break;

                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        result.setText(savedInstanceState.getString("result"));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("result", (String)result.getText());
    }
}