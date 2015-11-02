package com.example.artem.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by artem on 21.10.15.
 */
public final class MainActivity extends Activity {
    private enum Operation {
        ADD, SUB, MUL, DIV, EQ, PERC, CHANGE_SIGN, NONE;

        public boolean isUnary() {
            return this == Operation.PERC || this == Operation.CHANGE_SIGN;
        }
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView calcRes;
    private ScrollView scroll;
    private BigDecimal leftOperand = null;
    private BigDecimal rightOperand = null;
    private Operation currentOperation = Operation.NONE;

    /**
     * prints calculation result without leading zeros
     * @param number
     */
    private void printOnScreen(String number) {
        Pattern reg = Pattern.compile("^\\-?[0-9]*(\\.(0*[1-9]+)+)?");
        Matcher m = reg.matcher(number);
        String tmp = "";
        if (m.find())
            tmp = m.group();
        calcRes.setText(tmp);
    }

    /**
     * calls while clicking on digit button
     * @param view
     */
    public void onDigitButtonClick(View view) {
        Button b = (Button)view;
        int currentDigit = Integer.parseInt(b.getText().toString());
        if (rightOperand == null) {
            rightOperand = new BigDecimal(0);
        }
        if (rightOperand.scale() > 0) {
            int oldScale = rightOperand.scale();
            Log.d(TAG, "scale: " + String.valueOf(oldScale));
            int inc;
            if (calcRes.getText().toString().endsWith("."))
                inc = 0;
            else
                inc = 1;
            rightOperand = rightOperand.setScale(oldScale + inc);
            rightOperand = rightOperand.add(new BigDecimal(currentDigit).movePointLeft(oldScale + inc));
        } else {
            rightOperand = rightOperand.multiply(BigDecimal.TEN).add(new BigDecimal(currentDigit));
        }
        calcRes.setText(rightOperand.toPlainString());
        scroll.fullScroll(View.FOCUS_DOWN);
        Log.d(TAG, rightOperand.toPlainString());
    }

    /**
     * calls while clicking on clear button
     * clears left and right operand and forget last operation
     * @param view
     */
    public void onClearButtonClick(View view) {
        printOnScreen("0");
        leftOperand = null;
        rightOperand = null;
        currentOperation = Operation.NONE;
    }

    /**
     * calls while clicking on dot button
     * adds 1 scale to right operand if it's equals to zero earlier
     * nothing happens else
     * @param view
     */
    public void onDotButtonClick(View view) {
        if (rightOperand == null) {
            rightOperand = new BigDecimal(0);
        }
        if (rightOperand.scale() > 0) {
            printOnScreen(rightOperand.toPlainString());
        } else {
            String tmp = String.format("%s.", rightOperand.toPlainString());
            calcRes.setText(tmp);
            rightOperand = rightOperand.multiply(BigDecimal.TEN).movePointLeft(1);
        }
        Log.d(TAG, "scale: " + String.valueOf(rightOperand.scale()));
        Log.d(TAG, rightOperand.toPlainString());
    }

    /**
     * returns a copy of BigDecimal
     * @param other
     * @return a copy of other
     */
    private BigDecimal copy(BigDecimal other) {
        return new BigDecimal(other.toString());
    }

    /**
     * evaluates last operation if it haven't been evaluated earlier
     * current operation in this case cannot be equal
     * @param nextOperation next operation, that can't be none
     */
    private void eval(Operation nextOperation) {
        // at first trying to evaluate unary operations (percent and negate)
        if (nextOperation.isUnary()) {
            switch (nextOperation) {
                case PERC:
                    // define operand that need to be evaluated
                    if (currentOperation == Operation.NONE && rightOperand == null) {
                        if (leftOperand != null) {
                            leftOperand = leftOperand.movePointLeft(2);
                            printOnScreen(leftOperand.toPlainString());
                        }
                    } else {
                        if (rightOperand != null) {
                            rightOperand = rightOperand.movePointLeft(2);
                            printOnScreen(rightOperand.toPlainString());
                        }
                    }
                    break;
                case CHANGE_SIGN:
                    // define operand that need to be evaluated
                    if (currentOperation == Operation.NONE && rightOperand == null) {
                        Log.d(TAG, "i was reached");
                        if (leftOperand != null) {
                            leftOperand = leftOperand.negate();
                            printOnScreen(leftOperand.toPlainString());
                        }
                    } else {
                        if (rightOperand != null) {
                            rightOperand = rightOperand.negate();
                            printOnScreen(rightOperand.toPlainString());
                        }
                    }
                    break;
                default:
                    break;
            }
            return;
        }
        // trying to evaluate last operation (currentOperation)
        if (currentOperation != Operation.NONE && rightOperand != null && leftOperand != null) {
            Log.d(TAG, currentOperation.toString());
            switch (currentOperation) {
                case ADD:
                    leftOperand = leftOperand.add(rightOperand);
                    break;
                case SUB:
                    leftOperand = leftOperand.subtract(rightOperand);
                    break;
                case MUL:
                    leftOperand = leftOperand.multiply(rightOperand);
                    break;
                case DIV:
                    int scale = leftOperand.scale() + rightOperand.scale() + 4;
                    if (scale == 4) {
                        scale = 9;
                    }
                    if (rightOperand.equals(BigDecimal.ZERO)) {
                        rightOperand = null;
                        currentOperation = Operation.NONE;
                        leftOperand = null;
                        calcRes.setText("error: division by zero");
                    } else {
                            leftOperand = leftOperand.divide(rightOperand, scale, RoundingMode.CEILING);
                    }
                    break;
            }
            rightOperand = new BigDecimal(0);
        }
        if (nextOperation != Operation.EQ) {
            // if next operation is not equal
            // moving rightOperand to leftOperand
            if (currentOperation == Operation.NONE) {
                if (rightOperand != null && !rightOperand.equals(BigDecimal.ZERO)) {
                    leftOperand = copy(rightOperand);
                }
                rightOperand = null;
            } else {
                if (leftOperand != null) {
                    printOnScreen(leftOperand.toPlainString());
                }
            }
            // updating current operation
            currentOperation = nextOperation;

        } else {
            // if next operation is equal then current must become none
            if (currentOperation == Operation.NONE && rightOperand != null && !rightOperand.equals(BigDecimal.ZERO)) {
                calcRes.setText(rightOperand.toPlainString());
            } else {
                currentOperation = Operation.NONE;
                rightOperand = null;
                if (leftOperand != null) {
                    printOnScreen(leftOperand.toPlainString());
                }
            }
        }
    }

    /**
     * calls while clicking on binary or unary operation button
     * @param view clicked button
     */
    public void onOperationButtonClick(View view) {
        Button op = (Button)view;
        Log.d(TAG, "getText() returns: " + op.getText().toString());
        Log.d(TAG, "leftOperand: " + (leftOperand == null ? "null" : leftOperand.toPlainString()));
        Log.d(TAG, "rightOperand: " + (rightOperand == null ? "null" : rightOperand.toPlainString()));
        Operation nextOp = null;
        switch (op.getText().toString()) {
            case "=":
                nextOp = Operation.EQ;
                break;
            case "+":
                nextOp = Operation.ADD;
                break;
            case "-":
                nextOp = Operation.SUB;
                break;
            case "*":
                nextOp = Operation.MUL;
                break;
            case "/":
                nextOp = Operation.DIV;
                break;
            case "%":
                nextOp = Operation.PERC;
                break;
            case "+/-":
                nextOp = Operation.CHANGE_SIGN;
                break;
        }
        eval(nextOp);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        calcRes = (TextView)findViewById(R.id.calc_res);
        scroll = (ScrollView)findViewById(R.id.scroll);
        if (savedInstanceState != null) {
            String savedOperand = savedInstanceState.getString("rightOperand");
            if (savedOperand != null) {
                rightOperand = new BigDecimal(savedOperand);
            }
            savedOperand = savedInstanceState.getString("leftOperand");
            if (savedOperand != null) {
                leftOperand = new BigDecimal(savedOperand);
            }
            currentOperation = Operation.valueOf(savedInstanceState.getString("currentOperation"));
            calcRes.setText(savedInstanceState.getCharSequence("displayedValue"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    protected void onSaveInstanceState(Bundle savingBundle) {
        super.onSaveInstanceState(savingBundle);
        if (rightOperand != null) {
            savingBundle.putString("rightOperand", rightOperand.toPlainString());
        }
        if (leftOperand != null) {
            savingBundle.putString("leftOperand", leftOperand.toPlainString());
        }
        savingBundle.putString("currentOperation", currentOperation.toString());
        savingBundle.putString("displayedValue", calcRes.getText().toString());
    }
}