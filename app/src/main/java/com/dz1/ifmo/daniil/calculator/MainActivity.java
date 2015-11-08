package com.dz1.ifmo.daniil.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import expression.CheckedParser;
import expression.types.CheckedBigDecimal;

public class MainActivity extends AppCompatActivity {
    private TextView expression;
    private TextView memoryStatus;
    private String memory;
    private boolean finishCalc;
    private boolean writeInMem;

    private enum  ResultStatus {COMPLETE, OK, ERROR};
    private ResultStatus resultStatus = ResultStatus.COMPLETE;


    private void evaluateExpression(String expr) {
        String result;
        try {
            result = new CheckedParser<>(new CheckedBigDecimal("0")).parse(expr).evaluate().toString();
        } catch (Exception e) {
            result = "Error";
        }
        if (writeInMem) {
            memory = result;
        } else {
            expression.setText(result);
            if (result.equals("Error")) {
                resultStatus = ResultStatus.ERROR;
            } else {
                resultStatus = ResultStatus.OK;
            }
        }
        finishCalc = true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expression = (TextView) findViewById(R.id.expr);
        memoryStatus = (TextView) findViewById(R.id.mexpr);

        if (savedInstanceState != null) {
            expression.setText(savedInstanceState.getString("EXPRESSION"));
            memoryStatus.setText(savedInstanceState.getString("MEMORY_STATUS"));
            memory = savedInstanceState.getString("MEMORY");
            resultStatus = ResultStatus.values()[savedInstanceState.getInt("RESULT_STATUS")];
            finishCalc = savedInstanceState.getBoolean("FINISH_CALC");
            writeInMem = savedInstanceState.getBoolean("WRITE_IN_MEMORY");
        } else {
            memory ="";
            resultStatus = ResultStatus.COMPLETE;
            finishCalc = true;
            writeInMem = true;
        }
    }

    private void addOperationToExp(String x) {
        if (expression.length() != 0) {
            expression.append(x);
        }
    }

    private boolean toClear(View v) {
        int  id = v.getId();
        return  ((id == R.id.Button0) || (id == R.id.Button1) || (id == R.id.Button2)
                || (id == R.id.Button3) || (id == R.id.Button4) || (id == R.id.Button5)
                || (id == R.id.Button6) || (id == R.id.Button7) || (id == R.id.Button8)
                || (id == R.id.Button9) || (id == R.id.dotButton) || (id == R.id.leftBracketButton)
                || (id == R.id.rightBracketButton) || (id == R.id.piButton) || (id == R.id.eButton));
    }

    public void onClick(View v) {
        if (!finishCalc) {
            return;
        }

        if (resultStatus.equals(ResultStatus.ERROR)) {
            expression.setText("");
            resultStatus = ResultStatus.COMPLETE;
        }

        if (resultStatus.equals(ResultStatus.OK)) {
            if (toClear(v)) {
                expression.setText("");
            }
            resultStatus = ResultStatus.COMPLETE;
        }

        switch (v.getId()) {
            case R.id.leftBracketButton :
                expression.append("(");
                break;
            case R.id.rightBracketButton :
                expression.append(")");
                break;
            case R.id.x2Button :
                expression.append("^2");
                break;
            case R.id.x3Button :
                expression.append("^3");
                break;
            case R.id.piButton :
                expression.append("pi");
                break;
            case R.id.eButton :
                expression.append("e");
                break;
            case R.id.mMButton :
                if (expression.length() != 0) {
                    memoryStatus.setText("M");
                    finishCalc = false;
                    writeInMem = true;
                    evaluateExpression(memory + "-" + expression.getText().toString());
                }
                break;
            case R.id.mPButton :
                if (expression.length() != 0) {
                    memoryStatus.setText("M");
                    if (memory.length() != 0) {
                        memory = memory + "+";
                    }
                    finishCalc = false;
                    writeInMem = true;
                    evaluateExpression(memory + expression.getText().toString());
                }
                break;
            case R.id.mRButton :
                if (memory.length() != 0) {
                    if (memory.equals("Error")) {
                        resultStatus = ResultStatus.ERROR;
                    }
                    expression.append(memory);
                }
                break;
            case R.id.mCButton :
                memoryStatus.setText("");
                memory = "";
                break;
            case R.id.Button0 :
                expression.append("0");
                break;
            case R.id.Button1 :
                expression.append("1");
                break;
            case R.id.Button2 :
                expression.append("2");
                break;
            case R.id.Button3 :
                expression.append("3");
                break;
            case R.id.Button4 :
                expression.append("4");
                break;
            case R.id.Button5 :
                expression.append("5");
                break;
            case R.id.Button6 :
                expression.append("6");
                break;
            case R.id.Button7 :
                expression.append("7");
                break;
            case R.id.Button8 :
                expression.append("8");
                break;
            case R.id.Button9 :
                expression.append("9");
                break;
            case R.id.deleteButton :
                if (expression.length() != 0) {
                    expression.setText(expression.getText().subSequence(0, expression.length() - 1));
                }
                break;
            case R.id.clearButton :
                expression.setText("");
                break;
            case R.id.dotButton :
                expression.append(".");
                break;
            case R.id.plusButton :
                addOperationToExp("+");
                break;
            case R.id.minusButton :
                expression.append("-");
                break;
            case R.id.divButton :
                addOperationToExp("/");
                break;
            case R.id.mulButton :
                addOperationToExp("*");
                break;
            case R.id.equalButton :
                finishCalc = false;
                writeInMem = false;
                evaluateExpression(expression.getText().toString());
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("EXPRESSION", expression.getText().toString());
        outState.putString("MEMORY_STATUS", memoryStatus.getText().toString());
        outState.putString("MEMORY", memory);
        outState.putInt("RESULT_STATUS", resultStatus.ordinal());
        outState.putBoolean("FINISH_CALC", finishCalc);
        outState.putBoolean("WRITE_IN_MEMORY", writeInMem);
    }
}
