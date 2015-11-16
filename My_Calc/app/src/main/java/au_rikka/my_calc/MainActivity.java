package au_rikka.my_calc;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.text.NumberFormat;

import static java.lang.Math.max;
import static java.lang.Math.pow;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView arg1_display, arg2_display, sign_display;

    private class CalcTask {
        private double arg1 = 0;
        private double arg2 = 0;
        private int after_point = 0;
        private boolean is_arg1 = true;
        private boolean is_arg2 = false;
        private char sign = ' ';
    }

    private CalcTask curCalcTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arg1_display = (TextView) findViewById(R.id.arg1_display);
        arg2_display = (TextView) findViewById(R.id.arg2_display);
        sign_display = (TextView) findViewById(R.id.sign_display);

        findViewById(R.id.d0).setOnClickListener(this);
        findViewById(R.id.d1).setOnClickListener(this);
        findViewById(R.id.d2).setOnClickListener(this);
        findViewById(R.id.d3).setOnClickListener(this);
        findViewById(R.id.d4).setOnClickListener(this);
        findViewById(R.id.d5).setOnClickListener(this);
        findViewById(R.id.d6).setOnClickListener(this);
        findViewById(R.id.d7).setOnClickListener(this);
        findViewById(R.id.d8).setOnClickListener(this);
        findViewById(R.id.d9).setOnClickListener(this);

        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.sub).setOnClickListener(this);
        findViewById(R.id.mul).setOnClickListener(this);
        findViewById(R.id.div).setOnClickListener(this);

        findViewById(R.id.get_ans).setOnClickListener(this);

        findViewById(R.id.point).setOnClickListener(this);

        findViewById(R.id.del).setOnClickListener(this);

        curCalcTask = (CalcTask) getLastCustomNonConfigurationInstance();
        if (curCalcTask == null) {
            curCalcTask = new CalcTask();
        }
        refresh_display();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return curCalcTask;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.d0: change_args(0);
                break;
            case R.id.d1: change_args(1);
                break;
            case R.id.d2: change_args(2);
                break;
            case R.id.d3: change_args(3);
                break;
            case R.id.d4: change_args(4);
                break;
            case R.id.d5: change_args(5);
                break;
            case R.id.d6: change_args(6);
                break;
            case R.id.d7: change_args(7);
                break;
            case R.id.d8: change_args(8);
                break;
            case R.id.d9: change_args(9);
                break;

            case R.id.add: do_operation('+');
                break;
            case R.id.sub: do_operation('-');
                break;
            case R.id.mul: do_operation('*');
                break;
            case R.id.div: do_operation('/');
                break;
            case R.id.get_ans: do_operation('=');
                break;

            case R.id.point: make_point();
                break;

            case R.id.del: delete();
                break;

            default: break;
        }
    }

    public void change_args(int x) {
        if (curCalcTask.sign == '=') {
            curCalcTask.sign = ' ';
            curCalcTask.is_arg1 = true;
            curCalcTask.arg1 = 0;
        }

        if (curCalcTask.is_arg1) {
            if (curCalcTask.after_point == 0) {
                curCalcTask.arg1 = curCalcTask.arg1 * 10 + x;
            } else {
                curCalcTask.arg1 = curCalcTask.arg1 + pow(10, -curCalcTask.after_point) * x;
                curCalcTask.after_point++;
            }
        } else {
            if (!curCalcTask.is_arg1) {
                curCalcTask.is_arg2 = true;
            }
            if (curCalcTask.after_point == 0) {
                curCalcTask.arg2 = curCalcTask.arg2 * 10 + x;
            } else {
                curCalcTask.arg2 = curCalcTask.arg2 + pow(10, -curCalcTask.after_point) * x;
                curCalcTask.after_point++;
            }
        }

        refresh_display();
    }

    void refresh_display() {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(0);
        formatter.setMaximumFractionDigits(12);
        formatter.setMinimumIntegerDigits(1);
        String s = formatter.format(curCalcTask.arg1);
        if (curCalcTask.is_arg1 && curCalcTask.after_point == 1) {
            s += ",";
        }
        arg1_display.setText(s);

        if (!curCalcTask.is_arg2) {
            arg2_display.setText("");
        } else {
            formatter.setMinimumFractionDigits(max(0, curCalcTask.after_point - 1));
            formatter.setMaximumFractionDigits(12);
            formatter.setMinimumIntegerDigits(1);
            s = formatter.format(curCalcTask.arg2);
            if (curCalcTask.after_point == 1) {
                s += ",";
            }
            arg2_display.setText(s);
        }

        sign_display.setText(Character.toString(curCalcTask.sign));
    }


    void do_operation(char ch) {
        if (!curCalcTask.is_arg2) {
            curCalcTask.sign = ch;
            if (curCalcTask.is_arg1) {
                curCalcTask.is_arg1 = false;
            }
            curCalcTask.after_point = 0;
            refresh_display();
        } else {
            evaluate(curCalcTask.sign);
            curCalcTask.sign = ch;
            curCalcTask.is_arg1 = false;
            curCalcTask.is_arg2 = false;
            curCalcTask.after_point = 0;
            refresh_display();
        }
    }

    void evaluate(char sign) {
        switch (sign) {
            case '+': curCalcTask.arg1 += curCalcTask.arg2;
                break;
            case '-': curCalcTask.arg1 -= curCalcTask.arg2;
                break;
            case '*': curCalcTask.arg1 *= curCalcTask.arg2;
                break;
            case '/': curCalcTask.arg1 /= curCalcTask.arg2;
                break;
            case '=': curCalcTask.arg1 = curCalcTask.arg2;
                break;

            default: break;
        }

        curCalcTask.arg2 = 0;
    }

    void make_point() {
        if (curCalcTask.after_point == 0) {
            curCalcTask.after_point = 1;
            if (!curCalcTask.is_arg1) {
                curCalcTask.is_arg2 = true;
            }
            refresh_display();
        }
    }

    void delete() {
        if (!curCalcTask.is_arg2) {
            curCalcTask.is_arg1 = true;
            curCalcTask.arg1 = 0;
            curCalcTask.arg2 = 0;
            curCalcTask.sign = ' ';
            refresh_display();
        } else {
            curCalcTask.is_arg2 = false;
            curCalcTask.arg2 = 0;
            refresh_display();
        }
    }
}
