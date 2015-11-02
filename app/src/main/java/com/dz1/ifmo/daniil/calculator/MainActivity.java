package com.dz1.ifmo.daniil.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView expression;
    public TextView memoryStatus;
    public String memory;
    public int flag;
    public boolean finishCalc;
    public boolean writeInMem;
    public CalcTask ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUG", "Create new activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expression = (TextView) findViewById(R.id.expr);
        memoryStatus = (TextView) findViewById(R.id.mexpr);

        ct = (CalcTask) getLastCustomNonConfigurationInstance();
        if (ct != null) {
            ct.attachActivity(this);
        }

        if (savedInstanceState != null) {
            expression.setText(savedInstanceState.getString("EXPRESSION"));
            memoryStatus.setText(savedInstanceState.getString("MEMORY_STATUS"));
            memory = savedInstanceState.getString("MEMORY");
            flag = savedInstanceState.getInt("FLAG");
            finishCalc = savedInstanceState.getBoolean("FINISH_CALC");
            writeInMem = savedInstanceState.getBoolean("WRITE_IN_MEMORY");
        } else {
            memory ="";
            flag = 0;
            finishCalc = true;
            writeInMem = true;
        }
    }

    public boolean toClear(View v) {
        return  ((v.getId() == R.id.Button0) || (v.getId() == R.id.Button1) || (v.getId() == R.id.Button2)
                || (v.getId() == R.id.Button3) || (v.getId() == R.id.Button4) || (v.getId() == R.id.Button5)
                || (v.getId() == R.id.Button6) || (v.getId() == R.id.Button7) || (v.getId() == R.id.Button8)
                || (v.getId() == R.id.Button9) || (v.getId() == R.id.dotButton) || (v.getId() == R.id.lBButton)
                || (v.getId() == R.id.rBButton) || (v.getId() == R.id.piButton) || (v.getId() == R.id.eButton));
    }

    public void onClick(View v) {
        if (!finishCalc) {
            return;
        }

        if (flag == 2) {
            expression.setText("");
            flag = 0;
        }

        if (flag == 1) {
            if (toClear(v)) {
                expression.setText("");
            }
            flag = 0;
        }

        switch (v.getId()) {
            case R.id.lBButton :
                expression.append("(");
                break;
            case R.id.rBButton :
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
                    ct = new CalcTask();
                    ct.attachActivity(this);
                    ct.execute(memory + "-" + expression.getText().toString());
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
                    ct = new CalcTask();
                    ct.attachActivity(this);
                    ct.execute(memory + expression.getText().toString());
                }
                break;
            case R.id.mRButton :
                if (memory.length() != 0) {
                    if (memory.equals("Error")) {
                        flag = 2;
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
                if (expression.length() != 0) {
                    expression.append("+");
                }
                break;
            case R.id.minusButton :
                expression.append("-");
                break;
            case R.id.divButton :
                if (expression.length() != 0) {
                    expression.append("/");
                }
                break;
            case R.id.mulButton :
                if (expression.length() != 0) {
                    expression.append("*");
                }
                break;
            case R.id.equalButton :
                finishCalc = false;
                writeInMem = false;
                ct = new CalcTask();
                ct.attachActivity(this);
                ct.execute(expression.getText().toString());
                break;
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return ct;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("EXPRESSION", expression.getText().toString());
        outState.putString("MEMORY_STATUS", memoryStatus.getText().toString());
        outState.putString("MEMORY", memory);
        outState.putInt("FLAG", flag);
        outState.putBoolean("FINISH_CALC", finishCalc);
        outState.putBoolean("WRITE_IN_MEMORY", writeInMem);
    }
}
