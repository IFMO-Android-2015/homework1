package com.example.alexey.hw1_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.Button;

import java.math.BigDecimal;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView val;
    private String x;
    private int op;
    private boolean flag;
    private static final int BIG_DECIMAL_SCALE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setterOfAllClickListeners(findViewById(R.id.mainLayout));
        val = (TextView) findViewById(R.id.text);
        clear(true);

        if (savedInstanceState != null) {
            x = savedInstanceState.getString("x");
            val.setText(savedInstanceState.getString("y"));
            op = savedInstanceState.getInt("op");
            flag = savedInstanceState.getBoolean("flag");
        }
    }

    protected void setterOfAllClickListeners(View currentView) {
        if (currentView instanceof Button)
            currentView.setOnClickListener(this);
        else if (currentView instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) currentView;
            View v;
            for (int i = 0; i < group.getChildCount(); i++) {
                v = group.getChildAt(i);
                setterOfAllClickListeners(v);
            }
        }
    }

    @Override
    public void onClick(View currentView) {
        try {
            switch (currentView.getId()) {
                case R.id.dot:
                    if (!val.getText().toString().contains(".")) print(currentView);
                    break;
                case R.id.add:
                case R.id.sub:
                case R.id.mul:
                case R.id.div:
                    operation(currentView);
                    break;
                case R.id.equ:
                    count();
                    break;
                case R.id.clear:
                    clear(true);
                    break;
                case R.id.del:
                    String tmp = val.getText().toString();
                    val.setText(tmp.length() <= 1 ? "0" : tmp.substring(0, tmp.length() - 1));
                    break;
                default:
                    print(currentView);
                    break;
            }
        }
        catch (Exception e) {
            Log.d("Error", e.getMessage());
            val.setText("Smth went wrong, try again pls");
            flag = true;
        }
    }

    private void print(View currentView) {
        if (flag) clear(false);
        String tmp = val.getText().toString();
        Button button = (Button) currentView;
        val.setText(tmp.equals("0") ? button.getText().toString() : tmp + button.getText().toString());
        scrollDown();
    }

    private void operation(View currentView) {
        if (!x.equals("0") && !val.getText().toString().equals("0")) count();
        x = val.getText().toString();
        clear(false);
        switch (currentView.getId()) {
            case R.id.add: op = 1; break;
            case R.id.sub: op = 2; break;
            case R.id.mul: op = 3; break;
            case R.id.div: op = 4; break;
        }
    }

    private void clear(boolean full) {
        val.setText("0");
        flag = false;
        if (full) {
            op = 1;
            x = "0";
        }
    }

    private void scrollDown() {
        ScrollView SV = (ScrollView) findViewById(R.id.scrollView);
        SV.fullScroll(ScrollView.FOCUS_DOWN);
    }

    private void count() {
        String y = val.getText().toString(), res;
        BigDecimal a = new BigDecimal(x), b = new BigDecimal(y);
        switch (op) {
            case 1: res = a.add(b).toString(); break;
            case 2: res = a.subtract(b).toString(); break;
            case 3: res = a.multiply(b).toString(); break;
            default:
                try { res = a.divide(b, BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP).toString(); }
                catch (ArithmeticException e) { res = "Dont try to divide by zero pls"; }
                while (res.contains(".") && res.length() >= 1 && ("0".charAt(0) == res.charAt(res.length() - 1)) || ".".charAt(0) == res.charAt(res.length() - 1))
                    res = res.substring(0, res.length() - 1);
                break;
        }
        clear(true);
        val.setText(res);
        flag = true;
        scrollDown();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("x", x);
        outState.putString("y", val.getText().toString());
        outState.putInt("op", op);
        outState.putBoolean("flag", flag);
    }
}