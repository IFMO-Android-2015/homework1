package ru.npoperechnyi.homework1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {
    AutoResizeTextView et;
    private double val;
    private int opr;
    private double currentVal;

    private enum operations {MUL, DIV, ADD, SUBTR, EQ}

    Button clearButton;
    operations currentOperation = operations.ADD;
    static final String CURRENT_VALUE = "Current Value";
    static final String VALUE = "Value";
    static final String OPERATION = "Operation";
    static final String OPR = "Opr";
    static final String TEXT = "Text";
    static final String CLRBUTTON = "ACC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        et = (AutoResizeTextView) findViewById(R.id.outText);

        findViewById(R.id.commaButton).setOnClickListener(this);
        findViewById(R.id.zeroButton).setOnClickListener(this);
        findViewById(R.id.oneButton).setOnClickListener(this);
        findViewById(R.id.twoButton).setOnClickListener(this);
        findViewById(R.id.threeButton).setOnClickListener(this);
        findViewById(R.id.fourButton).setOnClickListener(this);
        findViewById(R.id.fiveButton).setOnClickListener(this);
        findViewById(R.id.sixButton).setOnClickListener(this);
        findViewById(R.id.sevenButton).setOnClickListener(this);
        findViewById(R.id.eightButton).setOnClickListener(this);
        findViewById(R.id.nineButton).setOnClickListener(this);
        findViewById(R.id.plusButton).setOnClickListener(this);
        findViewById(R.id.minusButton).setOnClickListener(this);
        findViewById(R.id.mulButton).setOnClickListener(this);
        findViewById(R.id.divButton).setOnClickListener(this);
        findViewById(R.id.equalButton).setOnClickListener(this);
        findViewById(R.id.signButton).setOnClickListener(this);
        findViewById(R.id.sqrtButton).setOnClickListener(this);
        findViewById(R.id.backButton).setOnClickListener(this);
        clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(this);
        if (savedInstanceState != null) {
            et.setText(savedInstanceState.getString(TEXT));
            val = savedInstanceState.getDouble(VALUE);
            currentVal = savedInstanceState.getDouble(CURRENT_VALUE);
            currentOperation = (operations) savedInstanceState.get(OPERATION);
            opr = savedInstanceState.getInt(OPR);
            clearButton.setTag(savedInstanceState.getInt(CLRBUTTON));
        } else {
            clearButton.setTag(1);
            Init(true);

        }
    }

    @Override
    public void onClick(View v) {
        et.setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
        switch (v.getId()) {
            case R.id.oneButton: {
                updateText("1");
                break;
            }
            case R.id.twoButton: {
                updateText("2");
                break;
            }
            case R.id.threeButton: {
                updateText("3");
                break;

            }
            case R.id.fourButton: {
                updateText("4");
                break;

            }
            case R.id.fiveButton: {
                updateText("5");
                break;
            }
            case R.id.sixButton: {
                updateText("6");
                break;
            }
            case R.id.sevenButton: {
                updateText("7");
                break;
            }
            case R.id.eightButton: {
                updateText("8");
                break;
            }
            case R.id.nineButton: {
                updateText("9");
                break;
            }
            case R.id.zeroButton: {
                updateText("0");
                break;
            }
            case R.id.commaButton: {
                if (opr > 0) {
                    et.setText("0.");
                } else {
                    if (!(et.getText() + "").contains(".")) {
                        et.append(".");
                    }
                }
                opr = 0;
                break;
            }
            case R.id.mulButton: {
                perform();
                currentOperation = operations.MUL;
                break;
            }
            case R.id.divButton: {
                perform();
                currentOperation = operations.DIV;
                break;
            }
            case R.id.plusButton: {
                perform();
                currentOperation = operations.ADD;
                break;
            }
            case R.id.minusButton: {
                perform();
                currentOperation = operations.SUBTR;
                break;
            }
            case R.id.equalButton: {
                opr = 2;
                perform();
                currentOperation = operations.EQ;
                opr = 2;
                break;
            }
            case R.id.signButton: {
                currentVal = -Double.parseDouble(et.getText().toString());
                et.setText(fmt(currentVal));
                break;
            }
            case R.id.sqrtButton: {
                currentVal = Math.sqrt(Double.parseDouble(et.getText() + ""));
                et.setText(fmt(currentVal));
                opr = 2;
                break;
            }
            case R.id.clearButton: {
                Init((Integer) clearButton.getTag() == 1);
                break;
            }
            case R.id.backButton: {
                String tempS = et.getText().subSequence(0, et.getText().length() - 1) + "";
                if (tempS.contains("fin") || tempS.contains("N") || tempS.contains("E")) {
                    break;
                }
                currentVal = !tempS.equals("") ? Double.valueOf(tempS) : 0;
                et.setText(String.valueOf(fmt(currentVal)));
                break;
            }
        }
    }

    private void perform() {
        if (opr == 1) {
            return;
        }
        switch (currentOperation) {
            case ADD: {
                val += currentVal;
                et.setText(fmt(val));
                break;
            }
            case MUL: {
                val *= currentVal;
                et.setText(fmt(val));
                break;
            }
            case DIV: {
                val /= currentVal;
                et.setText(fmt(val));
                break;
            }
            case SUBTR: {
                val -= currentVal;
                et.setText(fmt(val));
                break;
            }
        }
        opr = 1;
    }

    void updateText(String number) {
        clearButton.setTag(0);
        clearButton.setText(R.string.clearButton);
        if (opr >= 1) {
            val = Double.parseDouble(et.getText() + "");
            et.setText(number);
            opr = 0;
            currentVal = Double.parseDouble(number);
            return;
        }
        if ((et.getText() + "").equals("0")) {
            et.setText(number);
        } else {
            et.append(number);
        }
        currentVal = Double.parseDouble(et.getText() + "");
    }

    protected static String fmt(double d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }

    void Init(boolean full) {
        clearButton.setTag(1);
        clearButton.setText(R.string.allclearButton);
        if (full) {
            opr = 0;
            val = currentVal = 0;
            currentOperation = operations.ADD;
            et.setText("0");
            return;
        }
        if (opr == 0 || opr == 2) {
            et.setText("0");
            currentVal = 0;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putDouble(CURRENT_VALUE, currentVal);
        state.putDouble(VALUE, val);
        state.putInt(OPR, opr);
        state.putSerializable(OPERATION, currentOperation);
        state.putString(TEXT, et.getText().toString());
        state.putInt(CLRBUTTON, (Integer) clearButton.getTag());
    }

}
