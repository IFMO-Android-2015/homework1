package com.example.CustomCalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;

public class MyActivity extends Activity {
    private StringBuffer exp, errExp;
    int pos;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (savedInstanceState != null) {
            exp = (StringBuffer) getLastNonConfigurationInstance();
            ((TextView) findViewById(R.id.workField)).setText(exp);
        } else {
            exp = new StringBuffer();
        }
        errExp = new StringBuffer("INVALID EXPRESSION");
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        return exp;
    }
    public void onClickSumb(View v) {
        if (exp == errExp) {
            exp = new StringBuffer();
        }
        switch (v.getId()) {
            case R.id.delBtn:
                if (exp.length() != 0) {
                    exp.deleteCharAt(exp.length() - 1);
                }
                break;

            case R.id.acBtn:
                exp = new StringBuffer("");
                break;

            case R.id.eqBtn:
                try {
                    double save = additionalLayer();

                    exp = new StringBuffer((BigDecimal.valueOf(save)).setScale(5, BigDecimal.ROUND_HALF_EVEN).toString());
                } catch (Exception e) {
                    exp = errExp;
                }

                break;
            default:
                exp.append(((Button) v).getText());
        }
        ((TextView) findViewById(R.id.workField)).setText(exp);
    }

    double  additionalLayer() {
        exp.append(')');
        pos = 0;
        double buf = parse();
        exp.deleteCharAt(exp.length() - 1);
        return buf;
    }

    double parse() {
        return thirdPrior();
    }

    double thirdPrior() {
        double buf = secondPrior();
        char c = exp.charAt(pos);
        while (c == '+' || c == '-') {
            pos++;
            if (c == '+') {
                buf += secondPrior();
            } else {
                buf -= secondPrior();
            }
            c = exp.charAt(pos);
        }
        return buf;
    }

    double secondPrior() {
        double buf = firstPrior();
        char c = exp.charAt(pos);
        while (c == '*' || c == '/') {
            pos++;
            if (c == '*') {
                buf *= firstPrior();
            } else {
                buf /= firstPrior();
            }
            c = exp.charAt(pos);
        }
        return buf;
    }

    double firstPrior() {
        StringBuffer buf = new StringBuffer();
        char c = exp.charAt(pos);
        if (c == '(') {
            pos++;
            double save = parse();
            pos++;
            return save;
        }
        while ((c >= '0' && c <= '9') || c == '.' || (c == '-' && buf.length() == 0)) {
            buf.append(c);
            pos++;
            c = exp.charAt(pos);
        }
        return Double.parseDouble(buf.toString());
    }
}
