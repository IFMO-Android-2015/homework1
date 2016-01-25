package ru.ifmo.android_2015.homework1;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView result;
    private TextView previous;
    private final char emptyOp = ' ';
    private char operator = emptyOp;
    private Button operatorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_main_land);
        }
        result = (TextView)findViewById(R.id.resultView);
        previous = (TextView)findViewById(R.id.previousView);
        if (savedInstanceState != null) {
            operator = savedInstanceState.getChar("operator", emptyOp);
            switch (operator) {
                case '+':
                    operatorButton = (Button) findViewById(R.id.BAdd);
                    break;
                case '-':
                    operatorButton = (Button) findViewById(R.id.BMin);
                    break;
                case '*':
                    operatorButton = (Button) findViewById(R.id.BMul);
                    break;
                case '/':
                    operatorButton = (Button) findViewById(R.id.BDiv);
                    break;
                case '^':
                    operatorButton = (Button) findViewById(R.id.BPow);
                    break;
                default:
                    operatorButton = null;
            }
            setOp(operatorButton, operator);
            result.setText(savedInstanceState.getString("result", ""));
            result.setHint(savedInstanceState.getString("hint", "0"));
            previous.setText(savedInstanceState.getString("previous", ""));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("previous", previous.getText().toString());
        savedInstanceState.putString("result", result.getText().toString());
        savedInstanceState.putString("hint", result.getHint().toString());
        savedInstanceState.putChar("operator", operator);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onNumberClick(View view) {
        result.append(((Button) view).getText());
    }

    private void setOp(Button view, char op) {
        operator = op;
        if (operatorButton != null) {
            operatorButton.setTextColor(getResources().getColor(R.color.colorOperandDefault));
        }
        if (op != emptyOp) {
            view.setTextColor(getResources().getColor(R.color.colorOperandAccent));
            operatorButton = view;
        } else {
            operatorButton = null;
        }
    }

    public void onOpClick(View view) {
        char op = ((Button)view).getText().charAt(0);
        if (op == '-') {
            if ((operator == emptyOp) || (result.length() == 0)) {
                if (operator == '-')
                    setOp((Button)view, emptyOp);
                else
                    setOp((Button)view, '-');
            } else {
                if ((result.getText().charAt(0) == '-')) {
                    //Log.d("result-length", Integer.toString(result.length()));
                    result.setText(result.getText().subSequence(1, result.length()));
                } else {
                    result.setText('-' + result.getText().toString());
                }
            }
        } else {
            if (operator == op) {
                setOp((Button)view, emptyOp);
            } else {
                setOp((Button)view, op);
            }
        }
        if (previous.length() == 0) {
            previous.setText(result.getText());
            result.setText("");
        }
    }

    public void onResultClick(View view) {
        if ((operator != emptyOp) && (previous.length() != 0) && (result.length() != 0) && (!((result.length() == 1) && (result.getText() == "-")))) {
            final double left  = Double.parseDouble(previous.getText().toString());
            final double right = Double.parseDouble  (result.getText().toString());
            final double res;
            switch (operator) {
                case '+':
                    res = left + right;
                    break;
                case '-':
                    res = left - right;
                    break;
                case '*':
                    res = left * right;
                    break;
                case '/':
                    res = left / right;
                    break;
                case '^':
                    res = Math.pow(left, right);
                    break;
                default:
                    res = Double.NEGATIVE_INFINITY; // Unreachable, ofc.
            }
            previous.setText(Double.toString(res));
            result.setHint(Double.toString(res));
            result.setText("");
        }
    }

    public void onModClick(View view) {
        switch (view.getId()) {
            case R.id.BCE:
                previous.setText("");
                result.setHint("");
            case R.id.BC:
                result.setText("");
                break;
            case R.id.BBack:
                result.setText(result.getText().subSequence(0, result.length() - 1));
                break;
        }
    }
}
