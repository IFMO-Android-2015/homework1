package ru.ifmo.android_2015.easycalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    BigDecimal firstArgument = null;
    private TextView textView;
    private Operation operation = Operation.INV;
    private boolean needToClear = false;
    private boolean decimalUsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (firstArgument != null) {
            outState.putString("firstArgument", firstArgument.toString());
        }
        outState.putBoolean("needToClear", needToClear);
        outState.putBoolean("decimalUsed", decimalUsed);
        outState.putInt("operation", operation.ordinal());
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getString("firstArgument") != null) {
            firstArgument = new BigDecimal(savedInstanceState.getString("firstArgument"));
        }
        needToClear = savedInstanceState.getBoolean("needToClear");
        decimalUsed = savedInstanceState.getBoolean("decimalUsed");
        operation = Operation.values()[savedInstanceState.getInt("operation")];
    }

    public void onClickDigit(View v) {
        StringBuffer s = new StringBuffer(textView.getText());
        String digit = ((Button) v).getText().toString();
        Log.i("onClickDigit", " " + firstArgument);
        if (needToClear) {
            s.setLength(0);
            decimalUsed = false;
        }
        if (check(s, digit)) {
            if (needToClear) {
                needToClear = false;
            }
            if (operation == Operation.ERR) {
                operation = Operation.INV;
            }
            s.append(digit);
            textView.setText(s.toString());
        }
    }

    private boolean check(StringBuffer s, String digit) {
        switch (digit) {
            case ".":
                if (decimalUsed || operation == Operation.POW || s.length() == 0) {
                    return false;
                } else {
                    return decimalUsed = true;
                }
            case "0":
                return (s.length() == 0 || s.charAt(0) != '0' || decimalUsed);
            case "00":
                return (s.length() > 0 && s.charAt(0) != '0' || decimalUsed);
            default:
                return !(s.length() == 1 && s.charAt(0) == '0');
        }
    }

    public void onClickOperation(View v) {
        StringBuffer s = new StringBuffer(textView.getText().toString());
        Log.i("onClickOperation", operation.toString() + " " + firstArgument);
        if (R.id.buttonAllClear == v.getId()) {
            firstArgument = null;
            operation = Operation.INV;
            decimalUsed = false;
            s.setLength(0);
            textView.setText(s);
            return;
        }
        if (firstArgument == null && s.length() == 0 || operation == Operation.ERR) {
            return;
        }

        switch (v.getId()) {
            case R.id.buttonCos:
                operation = Operation.COS;
                evaluate();
                break;
            case R.id.buttonSin:
                operation = Operation.SIN;
                evaluate();
                break;
            case R.id.buttonFact:
                operation = Operation.FACT;
                evaluate();
                break;
            case R.id.buttonSquare:
                operation = Operation.SQR;
                evaluate();
                break;
            case R.id.buttonPower:
                operation = Operation.POW;
                break;
            case R.id.buttonAdd:
                operation = Operation.ADD;
                break;
            case R.id.buttonSubtract:
                operation = Operation.SUB;
                break;
            case R.id.buttonMultiply:
                operation = Operation.MUL;
                break;
            case R.id.buttonDivision:
                operation = Operation.DIV;
                break;
            case R.id.buttonChangeSign:
                if (s.length() != 0) {
                    if (s.charAt(0) == '-') {
                        s.deleteCharAt(0);
                    } else {
                        s.insert(0, '-');
                    }
                    textView.setText(s);
                }
                break;
            case R.id.buttonEvaluate:
                evaluate();
                break;
            case R.id.buttonMod:
                operation = Operation.MOD;
                break;
        }
        if (operation != Operation.ERR && (firstArgument == null || needToClear)) {
            firstArgument = new BigDecimal(s.toString());
        }
        needToClear = true;
    }

    private void evaluate() {
        String s = textView.getText().toString();
        if (firstArgument == null || needToClear) {
            firstArgument = new BigDecimal(s.toString());
        }

        switch (operation) {
            case MUL:
                firstArgument = firstArgument.multiply(new BigDecimal(s));
                break;
            case DIV:
                if (s.equals("0")) {
                    firstArgument = null;
                } else {
                    firstArgument = firstArgument.divide(new BigDecimal(s), 6, BigDecimal.ROUND_CEILING);
                }
                break;
            case ADD:
                firstArgument = firstArgument.add(new BigDecimal(s));
                break;
            case SUB:
                firstArgument = firstArgument.subtract(new BigDecimal(s));
                break;
            case MOD:
                firstArgument = firstArgument.divideAndRemainder(new BigDecimal(s))[1];
                break;
            case POW:
                firstArgument = firstArgument.pow(new Integer(s).intValue());
                break;
            case SQR:
                firstArgument = firstArgument.pow(2);
                break;
            case SIN:
                firstArgument = new BigDecimal(Math.sin(firstArgument.doubleValue() / 180 * Math.PI));
                firstArgument = firstArgument.setScale(6, BigDecimal.ROUND_HALF_UP);
                break;
            case COS:
                firstArgument = new BigDecimal(Math.cos(firstArgument.doubleValue() / 180 * Math.PI));
                firstArgument = firstArgument.setScale(6, BigDecimal.ROUND_HALF_UP);
                break;
            case FACT:
                if (firstArgument.compareTo(new BigDecimal(31)) == -1) {
                    int p = firstArgument.intValue();
                    firstArgument = new BigDecimal(1);
                    for (int i = 1; i <= p; i++) {
                        firstArgument = firstArgument.multiply(new BigDecimal(i));
                    }
                } else {
                    firstArgument = null;
                }
                break;
        }
        if (firstArgument == null) {
            textView.setText(getResources().getString(R.string.error));
            operation = Operation.ERR;
        } else {
            textView.setText(firstArgument.toString());
        }
    }

    enum Operation {
        MUL, DIV, ADD, SUB, MOD, INV, POW, SQR, SIN, COS, FACT, ERR
    }
}
