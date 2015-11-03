package ru.ifmo.android_2015.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private final String double_key = "STACK";
    private final String state_key = "STATE";
    private final String screen_key = "SCREEN";
    private State state;
    private Stack<Double> stack;
    private TextView screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stack = new Stack<>();
        state = State.EMPTY;
        screen = (TextView) findViewById(R.id.screen);
    }

    public void digitClick(View v) {
        if (state == State.RESULT)
            this.clearClick(v);
        StringBuilder s = new StringBuilder(screen.getText().toString());
        String k = ((TextView) v).getText().toString();
        if (k.equals(".") && s.indexOf(".") != -1) return;
        if (s.length() >= 9) return;
        s.append(k);
        if (s.charAt(0) == '0' && s.length() > 1 && s.charAt(1) != '.') s.deleteCharAt(0);
        screen.setText(s.toString());
    }

    public void clearClick(View v) {
        screen.setText("0");
        stack.clear();
        state = State.EMPTY;
    }

    public void operationClick(View v) {
        Double cur = Double.parseDouble(screen.getText().toString());
        State oper = State.getOper(((TextView) v).getText().toString());
        if (!state.isOperation()) {
            stack.push(cur);
            state = oper;
        } else {
            stack.push(performOper(cur, stack.pop(), state));
            state = oper;
        }
        screen.setText("0");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (!stack.empty()) outState.putDouble(double_key, stack.peek());
        outState.putString(state_key, state.toString());
        outState.putString(screen_key, screen.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState == null) return;
        double d = savedInstanceState.getDouble(double_key, Double.NaN);
        if (!Double.isNaN(d)) stack.push(d);
        state = State.valueOf(savedInstanceState.getString(state_key));
        screen.setText(savedInstanceState.getString(screen_key));
    }

    public void resultClick(View v) {
        if (!state.isOperation()) return;
        Double res = Double.parseDouble(screen.getText().toString());
        res = performOper(res, stack.pop(), state);

        String ans = res.toString();
        if (ans.length() >= 9) { //Max digits in field
            if (res > 1) {
                ans = new DecimalFormat("#.#####E0", DecimalFormatSymbols.getInstance(Locale.US)).format(res);
            } else {
                ans = new DecimalFormat("0.######", DecimalFormatSymbols.getInstance(Locale.US)).format(res);
            }//OMFG
        } else if (Math.abs(res - res.intValue()) < 1e-5) {
            //Output as integer
            ans = Integer.toString(res.intValue());
        }
        screen.setText(ans);
        state = State.RESULT;
    }

    private Double performOper(Double a, Double b, State oper) {
        switch (oper) {
            case ADD:
                return a + b;
            case SUB:
                return b - a;
            case MUL:
                return a * b;
            case DIV:
                return b / a;
            default:
                return Double.NaN;
        }
    }

    private enum State {
        EMPTY, ADD, SUB, MUL, DIV, RESULT;

        public static State getOper(String lit) {
            switch (lit) {
                case "+":
                    return ADD;
                case "-":
                    return SUB;
                case "*":
                    return MUL;
                case "/":
                    return DIV;
                default:
                    return EMPTY;
            }
        }

        public boolean isOperation() {
            return this != EMPTY && this != RESULT;
        }
    }
}
