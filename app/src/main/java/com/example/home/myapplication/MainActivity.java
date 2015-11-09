package com.example.home.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {
    BigDecimal result = new BigDecimal(0);
    StringBuilder buffer = new StringBuilder("0");
    String action = "+";
    boolean validbuff = true;
    boolean error = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void completeaction(View view) {
        Button button = (Button) view;
        if (validbuff)
            switch (action) {
                case (""):
                    break;
                case ("+"): {
                    result = result.add(new BigDecimal(buffer.toString()));
                    break;
                }
                case ("-"): {
                    result = result.subtract(new BigDecimal(buffer.toString()));
                    break;
                }
                case ("*"): {
                    result = result.multiply(new BigDecimal(buffer.toString())).setScale(7, BigDecimal.ROUND_HALF_EVEN);
                    break;
                }
                case ("/"): {
                    StringBuilder temp = new StringBuilder(buffer);
                    if (temp.charAt(0) == '-')
                        temp.delete(0, 1);
                    if (temp.charAt(temp.length() - 1) == '.')
                        temp.delete(temp.length() - 1, temp.length());
                    if (temp.toString().compareTo("0") == 0) {
                        error = true;
                        ((TextView) findViewById(R.id.textTempBuffer)).setText("Divide by zero");
                        return;
                    }
                    result = result.divide(new BigDecimal(buffer.toString()), 9, BigDecimal.ROUND_HALF_UP);
                    break;
                }
            }
        shownumber(false);
        if (String.valueOf(button.getText()).compareTo("=") != 0)
            action = button.getText().toString();
        System.out.println(action);
        validbuff = false;
    }

    public void clearbuff() {
        buffer = new StringBuilder("0");
        validbuff = true;
    }

    public void updatebuff(View v) {
        Button button = (Button) v;
        if (!validbuff) {
            clearbuff();
        }
        validbuff = true;
        if (buffer.toString().compareTo("0") == 0 || buffer.toString().compareTo("-0") == 0) {
            if (String.valueOf(button.getText()).compareTo(".") == 0) {
                buffer.append(".");
            } else
                buffer = new StringBuilder(button.getText());
        } else if (!(buffer.toString().contains(".") && (button.getText() == ".")))
            buffer.append(button.getText());
        shownumber(true);
    }

    public void shownumber(boolean isbuff) {
        StringBuilder i = (isbuff ? buffer : new StringBuilder(result.toString()));
        if (error)
            return;
        if (i.toString().contains(".") && !isbuff) {
            while (i.charAt(i.length() - 1) == '0')
                i.delete(i.length() - 1, i.length());
            if (i.charAt(i.length() - 1) == '.')
                i.delete(i.length() - 1, i.length());
        }
        ((TextView) findViewById(R.id.textTempBuffer)).setText(i);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("result", result.toString());
        outState.putString("buffer", buffer.toString());
        outState.putString("action", action);
        outState.putBoolean("validbuff", validbuff);
        outState.putBoolean("error", error);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        result = new BigDecimal(savedInstanceState.getString("result"));
        buffer = new StringBuilder(savedInstanceState.getString("buffer"));
        action = savedInstanceState.getString("action");
        validbuff = savedInstanceState.getBoolean("validbuff");
        error = savedInstanceState.getBoolean("error");
        shownumber(false);
    }

    public void reset(View v) {
        result = BigDecimal.ZERO;
        error = false;
        clearbuff();
        action = "+";
        shownumber(true);
    }

    public void resetbuff(View v) {
        if (error) {
            result = BigDecimal.ZERO;
            error = false;
        }
        clearbuff();
        shownumber(true);
    }

    public void changesign(View v) {
        if (validbuff) {
            if (buffer.charAt(0) == '-')
                buffer.delete(0, 1);
            else
                buffer.insert(0, "-");
            shownumber(true);
        } else {
            result = result.negate();
            shownumber(false);
        }
    }

    public void equals(View view) {
        validbuff = true;
        completeaction(view);
    }
}