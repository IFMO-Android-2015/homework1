package com.bigz.calc;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Stack;

public class MainActivity extends Activity {

    private TextView wind;
    private Stack st = new Stack();
    private String cur, op;
    private HashMap<Integer, String> num = new HashMap<>();
    private HashMap<Integer, String> bop = new HashMap<>();
    private HashMap<Integer, String> uop = new HashMap<>();
    private double t;
    private boolean bad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wind = (TextView) findViewById(R.id.textView);
        num.put(R.id.button0, "0");
        num.put(R.id.button1, "1");
        num.put(R.id.button2, "2");
        num.put(R.id.button3, "3");
        num.put(R.id.button4, "4");
        num.put(R.id.button5, "5");
        num.put(R.id.button6, "6");
        num.put(R.id.button7, "7");
        num.put(R.id.button8, "8");
        num.put(R.id.button9, "9");
        num.put(R.id.button00, "00");
        bop.put(R.id.buttonDiv, "/");
        bop.put(R.id.buttonMul, "*");
        bop.put(R.id.buttonAdd, "+");
        bop.put(R.id.buttonSub, "-");
        uop.put(R.id.buttonSQRT, "sqrt");
        uop.put(R.id.buttonUm, "±");
        cur = "0";
        op = "";
        bad = false;
        wind.setText(cur);
        t = 0;
    }

    public void clickNum(View v) {
        try {
            t = Double.parseDouble(cur);
        } catch (Exception e) {
            if (cur.length() != 0) bad = true;
        }
        if (cur.equals("0")) cur = "";
        if (num.containsKey(v.getId()) && !bad) {
            if ((v.getId() == R.id.button00) && (cur.length() == 0)) {
                cur = "0";
            } else cur += num.get(v.getId());
        } else if (v.getId() == R.id.buttonPoint && !bad) {
            if (!cur.contains(".")) {
                if (cur.length() == 0) {
                    cur += "0";
                }
                cur += ".";
            }
        } else if (v.getId() == R.id.buttonC) {
            cur = "0";
            st.clear();
            bad = false;
            t = 0;
        }
        wind.setText(cur);
    }

    public void clickOp(View v) {
        if (cur.length() != 0) {
            try {
                t = Double.parseDouble(cur);
            } catch (Exception e) {
                wind.setText("Очень жаль");
                Toast.makeText(this, "Очень жаль", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (v.getId() != R.id.buttonE) cur = "";
        if (v.getId() == R.id.buttonE && !bad) {
            if (st.size() == 1) {
                st.push(t);
                binOp();
                cur = print((double) st.peek());
                cur = (cur.equals("NaN") ? "Не число" : (cur.equals("Infinity") ? "Ошибка" : cur));
                wind.setText(cur);
            }

        } else if (bop.containsKey(v.getId()) && !bad) {
            st.push(t);
            if (st.size() == 2) {
                binOp();
            }
            op = bop.get(v.getId());
        } else if (uop.containsKey(v.getId()) && !bad) {
            t = unOp(uop.get(v.getId()));
            cur = print(t);
            wind.setText(cur = (cur.equals("NaN") ? "Не число" : cur));
        }
    }

    private double unOp(String o) {
        switch (o) {
            case "sqrt": {
                return Math.sqrt(t);
            }
            case "±": {
                return ((Math.abs(t - 0) < 0.00000000001) ? 0 : t * (-1));
            }
        }
        return t;
    }

    private void binOp() {
        double t1 = (double) st.pop();
        double t2 = (double) st.pop();
        switch (op) {
            case "+": {
                st.push(t1 + t2);
                break;
            }
            case "-": {
                st.push(t2 - t1);
                break;
            }
            case "*": {
                st.push(t2 * t1);
                break;
            }
            case "/": {
                if (t1 == 0) {
                    bad = true;
                }
                st.push(t2 / t1);
                break;
            }
        }
        if (bad) {
            cur = (Double.toString((double) st.peek()).equals("NaN") ? "Не число" : "Ошибка");
            wind.setText(cur);
            return;
        }

        wind.setText(print((double) st.peek()));
    }

    private String print(double n) {
        String ret;
        if (n % 1 == 0) {
            ret = BigInteger.valueOf((long) n).toString();
        } else {
            ret = Double.toString(n);
        }
        return ret;
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        t = savedInstanceState.getDouble("last");
        cur = savedInstanceState.getString("curr");
        op = savedInstanceState.getString("oper");
        st = (Stack) savedInstanceState.get("stack");
        wind.setText(savedInstanceState.getString("disp"));
        bad = savedInstanceState.getBoolean("flag");
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putDouble("last", t);
        outState.putString("curr", cur);
        outState.putString("oper", op);
        outState.putSerializable("stack", st);
        outState.putString("disp", wind.getText().toString());
        outState.putSerializable("flag", bad);
        super.onSaveInstanceState(outState);
    }
}
