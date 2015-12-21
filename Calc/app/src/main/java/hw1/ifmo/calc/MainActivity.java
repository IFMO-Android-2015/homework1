package hw1.ifmo.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BidiFormatter;
import android.view.View;
import android.widget.Button;;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView exp;
    TextView ans;

    Button add;
    Button eval;
    Button sub;
    Button mul;
    Button divide;
    Button dot;
    Button del;

    Button one;
    Button two;
    Button three;
    Button four;
    Button five;
    Button six;
    Button seven;
    Button eight;
    Button nine;
    Button zero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exp = (TextView) findViewById(R.id.exp);
        exp.setOnClickListener(this);
        ans = (TextView) findViewById(R.id.ans);
        ans.setOnClickListener(this);

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        eval = (Button) findViewById(R.id.eval);
        eval.setOnClickListener(this);
        sub = (Button) findViewById(R.id.sub);
        sub.setOnClickListener(this);
        mul = (Button) findViewById(R.id.mul);
        mul.setOnClickListener(this);
        divide = (Button) findViewById(R.id.div);
        divide.setOnClickListener(this);
        dot = (Button) findViewById(R.id.dot);
        dot.setOnClickListener(this);
        del = (Button) findViewById(R.id.del);
        del.setOnClickListener(this);

        one = (Button) findViewById(R.id.one);
        one.setOnClickListener(this);
        two = (Button) findViewById(R.id.two);
        two.setOnClickListener(this);
        three = (Button) findViewById(R.id.three);
        three.setOnClickListener(this);
        four = (Button) findViewById(R.id.four);
        four.setOnClickListener(this);
        five = (Button) findViewById(R.id.five);
        five.setOnClickListener(this);
        six = (Button) findViewById(R.id.six);
        six.setOnClickListener(this);
        seven = (Button) findViewById(R.id.seven);
        seven.setOnClickListener(this);
        eight = (Button) findViewById(R.id.eight);
        eight.setOnClickListener(this);
        nine = (Button) findViewById(R.id.nine);
        nine.setOnClickListener(this);
        zero = (Button) findViewById(R.id.zero);
        zero.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dot:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append(".");
                break;
            case R.id.add:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append(" + ");
                break;
            case R.id.sub:
                String tempst = exp.getText().toString();
                if (tempst.equals("0")) {
                    exp.setText("");
                }
                if (checkUnary())
                    exp.append("-");
                else
                    exp.append(" - ");
                break;
            case R.id.div:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append(" / ");
                break;
            case R.id.mul:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append(" * ");
                break;

            case R.id.eval:
                ans.setText(calcString(exp.getText().toString()));
                exp.setText("0");

                break;
            case R.id.del:
                exp.setText("0");
                break;

            case R.id.one:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append("1");
                break;
            case R.id.two:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append("2");
                break;
            case R.id.three:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append("3");
                break;
            case R.id.four:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append("4");
                break;
            case R.id.five:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append("5");
                break;
            case R.id.six:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append("6");
                break;
            case R.id.seven:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append("7");
                break;
            case R.id.eight:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append("8");
                break;
            case R.id.nine:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append("9");
                break;
            case R.id.zero:
                if (exp.getText().toString().equals("0")) {
                    exp.setText("");
                }
                exp.append("0");
                break;
        }
    }

    public String calcString(String str) {
        boolean error = false;
        String[] temp = str.trim().split(" ");
        ArrayList<String> exp = new ArrayList<>(Arrays.asList(temp));
        try {
            while (exp.contains("*")) {
                int i = exp.indexOf("*");
                double num = Double.parseDouble(exp.get(i - 1)) * Double.parseDouble(exp.get(i + 1));
                ;
                exp.remove(i + 1);
                exp.remove(i);
                exp.remove(i - 1);
                exp.add(i - 1, String.valueOf(num));
            }
            while (exp.contains("/")) {
                int i = exp.indexOf("/");
                if (!exp.get(i + 1).equals("0")) {
                    double num = Double.parseDouble(exp.get(i - 1)) / Double.parseDouble(exp.get(i + 1));
                    exp.remove(i + 1);
                    exp.remove(i);
                    exp.remove(i - 1);
                    exp.add(i - 1, String.valueOf(num));
                } else {
                    error = true;
                    break;
                }

            }
            while (exp.contains("+")) {
                if (error)
                    break;
                int i = exp.indexOf("+");
                double num = Double.parseDouble(exp.get(i - 1)) + Double.parseDouble(exp.get(i + 1));
                exp.remove(i + 1);
                exp.remove(i);
                exp.remove(i - 1);
                exp.add(i - 1, String.valueOf(num));
            }
            while (exp.contains("-")) {
                if (error)
                    break;
                int i = exp.indexOf("-");
                double num = Double.parseDouble(exp.get(i - 1)) - Double.parseDouble(exp.get(i + 1));
                exp.remove(i + 1);
                exp.remove(i);
                exp.remove(i - 1);
                exp.add(i - 1, String.valueOf(num));
            }

        } catch (Exception e) {
            error = true;
        }
        String t;
        if (error)
            t = "Error";
        else
            t = new BigDecimal("" + Double.parseDouble(exp.get(0))).setScale(1, BigDecimal.ROUND_HALF_UP).toString();
        return t;
    }

    public boolean checkUnary() {
        String[] temp = exp.getText().toString().trim().split(" ");
        boolean flag = false;
        if (temp[temp.length - 1].equals("+")
                || temp[temp.length - 1].equals("/")
                || temp[temp.length - 1].equals("*")
                || temp[temp.length - 1].equals("-")) {
            flag = true;
        }
        return flag;
    }


}
