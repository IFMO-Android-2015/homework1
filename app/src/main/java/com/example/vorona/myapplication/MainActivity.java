package com.example.vorona.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Double.doubleToLongBits;
import static java.lang.Double.parseDouble;
import static java.lang.Double.toString;

public class MainActivity extends AppCompatActivity {

    public TextView txt;
    public double cur = 0, mem = 0;
    boolean nw = true;
    private String operation = "+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            txt = (TextView) findViewById(R.id.Result);
            txt.setText(savedInstanceState.getCharSequence("res").toString());
            operation = String.valueOf(savedInstanceState.getChar("oper"));
            mem = savedInstanceState.getDouble("mem");
            nw = savedInstanceState.getBoolean("new");
        }
    }

    public void onNumberClicked(View view) {
        txt = (TextView) findViewById(R.id.Result);
        Button b = (Button) view;
        String str = txt.getText().toString();
        if (operation.equals("=")) {
            operation = "+";
            cur = mem = 0;
        }
        if (str.equals("0") || nw) {
            str = "";
        }
        str += b.getText().toString();
        txt.setText(str);
        nw = false;
    }

    public void onClicked(View view) {
        txt = (TextView) findViewById(R.id.Result);
        operation = "+";
        cur = mem = 0;
        txt.setText(String.valueOf(0));
    }

    public void onOperClicked(View view) {
        txt = (TextView) findViewById(R.id.Result);
        Button b = (Button) view;
        cur = parseDouble(txt.getText().toString());
        String op = b.getText().toString();
        boolean f = false;
        switch (operation) {
            case "+":
                mem += cur;
                break;
            case "-":
                mem = (double)Math.round((mem - cur) * 10000) / 10000;
                break;
            case "X":
                mem *= cur;
                break;
            case "/":
                mem /= cur;
                break;
            case "%":
                mem = mem / 100 * cur;
                break;
            case "=":
                f = true;
                break;
            default:
                mem = cur;
                break;
        }
        nw = true;
        operation = op;
        if (mem * 10 % 10 == 0)
            txt.setText(String.valueOf((int) mem));
        else
            txt.setText(String.valueOf(mem));
    }

    public void onSignClicked(View view) {
        txt = (TextView) findViewById(R.id.Result);
        String s;
        if (txt.getText().charAt(0) != '-')
            s = "-" + txt.getText().toString();
        else {
            s = txt.getText().toString();
            s = s.substring(1);
        }
        txt.setText(s);
    }

    public void onCommaClicked(View view) {
        txt = (TextView) findViewById(R.id.Result);
        String str = txt.getText().toString();
        if (!str.contains(".")) {
            String s = txt.getText().toString() + '.';
            txt.setText(s);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        txt = (TextView) findViewById(R.id.Result);
        outState.putCharSequence("res", txt.getText());
        outState.putDouble("mem", mem);
        outState.putChar("oper", operation.charAt(0));
        outState.putBoolean("new", nw);
    }

}
