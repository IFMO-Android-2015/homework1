package ru.breedpmnr.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView mCurr;
    private TextView mLast;
    private String mCurrOp;
    private String mSecondOp;
    private String mOp;
    private String mSecondString;
    private Double dLastResult;
    private Boolean mDotApplied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDotApplied = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        mCurr = (TextView) findViewById(R.id.curr);
        mLast = (TextView) findViewById(R.id.lastop);
        mCurrOp = "0";
        dLastResult = 0.0;
        mOp = "";
        mSecondString = "";
        mSecondOp = "";
        updateGUI();
    }

    private void updateGUI() {
        mLast.setText(mSecondString);
        mCurr.setText((mCurrOp.equals("")) ? ("") : (mCurrOp));
    }

    private void showResult(Double result) {
        if (result.isNaN() || result.isInfinite()) {
            clear(null);
        }
        if (Math.floor(result) == result && !result.isNaN()) {

            Integer temp = result.intValue();
            mCurrOp = temp.toString();
            mDotApplied = false;
        } else mCurrOp = result.toString();
        mSecondOp = "";
        mOp = "";
        updateGUI();
    }

    public void process(View v) {

        Double result;
        if (mSecondOp.equals("")) {
            result = dLastResult;
            showResult(result);
            return;
        }
        if (mCurrOp.equals("-")) mCurrOp = "0";
        mSecondString = mSecondOp + " " + mOp + " " + mCurrOp;
        Double first = Double.parseDouble(mSecondOp);
        Double second = Double.parseDouble(mCurrOp);
        if (mOp.equals("+")) result = first + second;
        else if (mOp.equals("X")) result = first * second;
        else if (mOp.equals("-")) result = first - second;
        else {
            if (second == 0.0) {
                Toasty("Cannot divide by zero");
                result = 0.0;
                clear(v);
                return;
            }
            result = first / second;
        }
        dLastResult = result;
        showResult(result);

    }

    private void Toasty(CharSequence output) {
        Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();
    }

    public void plusminus(View v) {
        if (mCurrOp.equals("0")) {
            mCurrOp = "-";
        } else if (mCurrOp.equals("-")) mCurrOp = "0";
        else {
            if (mCurrOp.substring(0, 1).equals("-")) {
                mCurrOp = mCurrOp.substring(1);
            } else mCurrOp = "-" + mCurrOp;
        }
        updateGUI();
    }

    public void op_pressed(View v) {
        if (!mSecondOp.equals("")) {
            Toasty("Only binary operations implemented");
            return;
        }
        if (mCurrOp.equals("-")) mCurrOp = "0";
        Button pressed = (Button) v;
        mOp = pressed.getText().toString();
        mSecondOp = mCurrOp;
        mSecondString = mSecondOp + " " + mOp;
        mCurrOp = "0";
        mDotApplied = false;
        updateGUI();

    }

    public void clearLast(View v) {
        if (mCurrOp.charAt(mCurrOp.length() - 1) == '.') {
            mDotApplied = false;
        }
        mCurrOp = mCurrOp.substring(0, mCurrOp.length() - 1);
        if (mCurrOp.equals("")) {
            if (mSecondOp.equals("")) {
                mCurrOp = "0";
            } else {
                mCurrOp = mSecondOp;
                mDotApplied = mCurrOp.contains(".");
                mOp = "";
                mSecondOp = "";
                mSecondString = "";
            }
        }
        updateGUI();
    }

    public void dot(View v) {
        if (!Character.isDigit(mCurrOp.charAt(mCurrOp.length() - 1))) {
            return;
        }
        mCurrOp = mCurrOp.concat((mDotApplied) ? ("") : ("."));
        mDotApplied = true;
        updateGUI();
    }

    public void num_clicked(View v) {
        Button butt = (Button) v;
        if (!mCurrOp.equals("0")) mCurrOp = mCurrOp.concat(butt.getText().toString());
        else mCurrOp = butt.getText().toString();
        updateGUI();
    }

    public void clear(View v) {
        mCurrOp = "0";
        mSecondString = "";
        mSecondOp = mOp = "";
        mDotApplied = false;
        updateGUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CurrOp", mCurrOp);
        outState.putString("SecondOp", mSecondOp);
        outState.putString("Op", mOp);
        outState.putBoolean("Dot", mDotApplied);
        outState.putDouble("LastResult", dLastResult);
        outState.putString("SecondString", mSecondString);
        //outState.putInt("count", cnt);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrOp = savedInstanceState.getString("CurrOp");
        mSecondOp = savedInstanceState.getString("SecondOp");
        mOp = savedInstanceState.getString("Op");
        mDotApplied = savedInstanceState.getBoolean("Dot");
        dLastResult = savedInstanceState.getDouble("LastResult");
        mSecondString = savedInstanceState.getString("SecondString");
        updateGUI();
        //cnt = savedInstanceState.getInt("count");
    }
}
