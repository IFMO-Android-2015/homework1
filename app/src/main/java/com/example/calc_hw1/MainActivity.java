package com.example.calc_hw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    TextView visualExpr;
    boolean ISFIRST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        visualExpr = (TextView) findViewById(R.id.textView);
        ISFIRST = true;

        if(savedInstanceState != null) {
            visualExpr = (TextView)((Pair<Object, Object>) getLastCustomNonConfigurationInstance()).first;
            ISFIRST = (boolean)((Pair<Object, Object>) getLastCustomNonConfigurationInstance()).second;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public Object onRetainCustomNonConfigurationInstance() {
        return new Pair<>(visualExpr, ISFIRST);
    }

    public void buttonClick(View v) {
        Button button = (Button) v;
        String s = button.getText().toString();
        if(ISFIRST || s.charAt(0) == 'i') {
            this.visualExpr.setText("");
            this.ISFIRST = false;
        }
        switch (s) {
            case "=":
                try {
                    new Expression().eval(this.visualExpr.getText().toString());
                } catch (Exception e) {
                    visualExpr.setText("incorrectExpr");
                }
                break;
            case "C":
                if(this.visualExpr.getText().length() == 0)
                    break;
                this.visualExpr.setText(this.visualExpr.getText().subSequence(0, this.visualExpr.getText().length() - 1));
                if(this.visualExpr.getText().length() == 0) {
                    this.visualExpr.setText("0");
                    this.ISFIRST = true;
                }
                break;
            case "DEL":
                this.visualExpr.setText("0");
                this.ISFIRST = true;
                break;
            default:
                if(this.visualExpr.getText().toString().contains("i") || this.visualExpr.getText().toString().contains("N") || this.visualExpr.getText().toString().equals("0"))
                    this.visualExpr.setText("");
                this.visualExpr.setText(this.visualExpr.getText().toString() + s);
                break;
        }
    }

    private class Expression {
        private LinkedList<String> expr;
        private Stack<Character> operands;
        private boolean BREAK;


        public void eval(String s) {
            expr = new LinkedList<>();
            operands = new Stack<>();
            char[] e = s.toCharArray();
            for(int i = 0; i < e.length;) {
                if(Character.isDigit(e[i]) || e[i] == '.') {
                    if(e[i] == '.' && i == 0) {
                        visualExpr.setText("incorrect point");
                        return;
                    }
                    float c = Integer.parseInt(Character.toString(e[i++]));
                    int k = 0;
                    boolean point = false;
                    if(i < e.length)
                    while(Character.isDigit(e[i]) || e[i] == '.') {
                        if(Character.isDigit(e[i])) {
                            if(point) k++;
                            c = c*10 + Integer.parseInt(Character.toString(e[i++]));
                        } else {
                            i++;
                            point = true;

                            while(Math.round(c) != c) {
                                c = c*10;
                                k++;
                            }
                        }
                    }
                    while (k > 0) {
                        k--;
                        c = c/10;
                    }
                    expr.push(Float.toString(c));
                } else {
                    if(i == 0) {
                        visualExpr.setText("incorrect expr");
                        return;
                    }
                    addOperand(e[i++]);
                    if(BREAK) return;
                }
            }

            while(!operands.isEmpty()) {
                expr.push(operands.pop().toString());
            }

            Stack<Float> res = new Stack<>();
            while (!expr.isEmpty()) {
                String c = expr.removeLast();
                if(Character.isDigit(c.charAt(0))) {
                    res.push(Float.parseFloat(c));
                } else {
                    if(res.size() < 2) {
                        visualExpr.setText("incorrect expr");
                        return;
                    }
                    float a = res.pop();
                    float b = res.pop();
                    switch (c) {
                        case "+":
                            res.push(a + b);
                            break;
                        case "-":
                            res.push(b - a);
                            break;
                        case "/":
                            res.push(b/a);
                            break;
                        case "*":
                            res.push(b * a);
                            break;
                    }
                }
            }
            visualExpr.setText(Float.toString(res.pop()));
        }

        private void addOperand(char o) {
            if(getPriority(o) == -1) {
                visualExpr.setText("incorrect symbol");
                BREAK = true;
                return;
            }
            while(!operands.empty() && getPriority(o) <= getPriority(operands.peek())) {
                expr.push(operands.pop().toString());
            }
            operands.push(o);
        }

        private int getPriority(char o) {
            switch (o) {
                case '*':case '/': return 2;
                case '+':case '-': return 1;
                default: return -1;
            }
        }
    }
}
