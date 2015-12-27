package ru.ifmo.android_2015.activitylifecycle.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ru.ifmo.android_2015.activitylifecycle.R;

public final class MainActivity extends Activity {
    private enum Operation {
        ADD, SUB, MUL, DIV, EQ, NONE;
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView calcRes;
    private BigDecimal leftOperand = null;
    private BigDecimal rightOperand = null;
    private Operation currentOperation = Operation.NONE;

    public void onDigitBtnClick(View view) {
        Button b = (Button)view;
        int currentDigit = Integer.parseInt(b.getText().toString());
        if (rightOperand == null) {
            rightOperand = new BigDecimal(0);
        }
        if (rightOperand.scale() > 0) {
            int oldScale = rightOperand.scale();
            Log.d(TAG, "scale: " + String.valueOf(oldScale));
            int inc;
            if (calcRes.getText().toString().endsWith(","))
                inc = 0;
            else
                inc = 1;
            rightOperand = rightOperand.setScale(oldScale + inc);
            rightOperand = rightOperand.add(new BigDecimal(currentDigit).movePointLeft(oldScale + inc));
        } else {
            rightOperand = rightOperand.multiply(BigDecimal.TEN).add(new BigDecimal(currentDigit));
        }
        calcRes.setText(rightOperand.toPlainString());
        Log.d(TAG, rightOperand.toPlainString());
    }

    public void onCancelBtnClick(View view) {
        calcRes.setText("0");
        leftOperand = null;
        rightOperand = null;
        currentOperation = Operation.NONE;
    }

    public void onDotBtnClick(View view) {
        if (rightOperand == null) {
            rightOperand = new BigDecimal(0);
        }
        if (rightOperand.scale() > 0) {
            calcRes.setText(rightOperand.toPlainString());
        } else {
            String tmp = String.format("%s,", rightOperand.toPlainString());
            calcRes.setText(tmp);
            rightOperand = rightOperand.multiply(BigDecimal.TEN).movePointLeft(1);
        }
        Log.d(TAG, "scale: " + String.valueOf(rightOperand.scale()));
        Log.d(TAG, rightOperand.toPlainString());
    }

    private BigDecimal copy(BigDecimal other) {
        return new BigDecimal(other.toString());
    }

    private void eval(Operation nextOperation) {
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
                if (rightOperand != null) {
                    leftOperand = copy(rightOperand);
                }
                rightOperand = null;
            } else {
                if (leftOperand != null) {
                    calcRes.setText(leftOperand.toPlainString());
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
                    calcRes.setText(leftOperand.toPlainString());
                }
            }
        }
    }

    public void onSignBtnClick(View view) {
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
            case "÷":
                nextOp = Operation.DIV;
                break;
        }
        eval(nextOp);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        calcRes = (TextView)findViewById(R.id.text_view);
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