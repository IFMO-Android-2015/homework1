package com.example.alice.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.text.DecimalFormat;

public strictfp class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView result;
    String res, currRes;
    enum Operation{DIV, MUL, SUBT, ADD, EQ}
    Operation currOperation;
    //Type of operation: 1 - 'curRes' is clear, 'res' has the value; 0 - 'curRes' and 'res' have values;
    int typeOfOperationFlag;
    boolean tooLongNumber;

    static final String CURR_RES = "Current Value";
    static final String RES = "Value";
    static final String CURR_OPERATION = "Operation";
    static final String TYPE_OF_OPERATION = "Opr";
    static final String TEXT = "Text";
    static final String TOO_LONG_NUMBER_FLAG = "Flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView)findViewById(R.id.resText);
        findViewById(R.id.oneButton).setOnClickListener(this);
        findViewById(R.id.twoButton).setOnClickListener(this);
        findViewById(R.id.threeButton).setOnClickListener(this);
        findViewById(R.id.fourButton).setOnClickListener(this);
        findViewById(R.id.fiveButton).setOnClickListener(this);
        findViewById(R.id.sixButton).setOnClickListener(this);
        findViewById(R.id.sevenButton).setOnClickListener(this);
        findViewById(R.id.eightButton).setOnClickListener(this);
        findViewById(R.id.nineButton).setOnClickListener(this);
        findViewById(R.id.nulButton).setOnClickListener(this);
        findViewById(R.id.allClearButton).setOnClickListener(this);
        findViewById(R.id.delLastButton).setOnClickListener(this);
        findViewById(R.id.changeSignButton).setOnClickListener(this);
        findViewById(R.id.divButton).setOnClickListener(this);
        findViewById(R.id.addButton).setOnClickListener(this);
        findViewById(R.id.mulButton).setOnClickListener(this);
        findViewById(R.id.subtButton).setOnClickListener(this);
        findViewById(R.id.eqButton).setOnClickListener(this);
        findViewById(R.id.comButton).setOnClickListener(this);

        if (savedInstanceState != null) {
            result.setText(savedInstanceState.getString(TEXT));
            currRes = savedInstanceState.getString(CURR_RES);
            res = savedInstanceState.getString(RES);
            currOperation = (Operation) savedInstanceState.get(CURR_OPERATION);
            typeOfOperationFlag = savedInstanceState.getInt(TYPE_OF_OPERATION);
            tooLongNumber = savedInstanceState.getBoolean(TOO_LONG_NUMBER_FLAG);
        } else {
            newProcess();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.oneButton):
                clickButton("1");
                break;
            case (R.id.twoButton):
                clickButton("2");
                break;
            case (R.id.threeButton):
                clickButton("3");
                break;
            case (R.id.fourButton):
                clickButton("4");
                break;
            case (R.id.fiveButton):
                clickButton("5");
                break;
            case (R.id.sixButton):
                clickButton("6");
                break;
            case (R.id.sevenButton):
                clickButton("7");
                break;
            case (R.id.eightButton):
                clickButton("8");
                break;
            case (R.id.nineButton):
                clickButton("9");
                break;
            case (R.id.nulButton):
                clickButton("0");
                break;
            case (R.id.allClearButton):
                newProcess();
                break;
            case (R.id.delLastButton):
                if (tooLongNumber)
                    break;
                if (typeOfOperationFlag == 1) {
                    String tmp;
                    if (res.length() <= 1) {
                        tmp = "0";
                        currRes = "";
                    }
                    else {
                        tmp = res.substring(0, res.length() - 1);
                        currRes = tmp;
                    }
                    typeOfOperationFlag = 0;
                    res = "0";
                    currOperation = Operation.ADD;
                    result.setText(tmp);
                } else if (typeOfOperationFlag == 0) {
                    String tmp;
                    if (currRes.length() <= 1) {
                        tmp = "0";
                        currRes = "";
                    }
                    else {
                        tmp = currRes.substring(0, currRes.length() - 1);
                        currRes = tmp;
                    }
                    result.setText(tmp);
                }

                break;
            case (R.id.changeSignButton):
                if (tooLongNumber)
                    break;
                if (typeOfOperationFlag == 1) {
                    double changeCurr = -Double.parseDouble(res);
                    String tmp = makeLongRes(changeCurr);
                    res = "0";
                    currOperation = Operation.ADD;
                    typeOfOperationFlag = 0;
                    currRes = fmt(changeCurr);
                    result.setText(tmp);
                } else if (typeOfOperationFlag == 0) {
                    double changeCurr = -Double.parseDouble(currRes);
                    String tmp = makeLongRes(changeCurr);
                    currRes = fmt(changeCurr);
                    result.setText(tmp);
                }
                break;
            case (R.id.divButton):
                calc();
                currOperation = Operation.DIV;
                break;
            case (R.id.mulButton):
                calc();
                currOperation = Operation.MUL;
                break;
            case (R.id.subtButton):
                calc();
                currOperation = Operation.SUBT;
                break;
            case (R.id.addButton):
                calc();
                currOperation = Operation.ADD;
                break;
            case (R.id.eqButton):
                calc();
                currOperation = Operation.EQ;
                break;
            case (R.id.comButton):
                if (tooLongNumber)
                    break;
                if (currRes.length() < 9 && !currRes.contains(".")) {
                    if (typeOfOperationFlag == 1) {
                        currRes = res + ".";
                        res = "0";
                        typeOfOperationFlag = 0;
                        result.setText(currRes);
                    } else if (typeOfOperationFlag == 0) {
                        currRes = currRes + ".";
                        result.setText(currRes);
                    }
                }
                break;
        }
    }

    void newProcess(){
        currRes = "";
        res = "0";
        currOperation = Operation.ADD;
        result.setText("0");
        typeOfOperationFlag = 1;
        tooLongNumber = false;
    }

    void calc() {
        if (typeOfOperationFlag == 1 || tooLongNumber)
            return;
        double resDouble = Double.parseDouble(res);
        double currResDouble = Double.parseDouble(currRes);
        switch (currOperation) {
            case DIV: {
                double cur = resDouble / currResDouble;
                String tmp = makeLongRes(cur);
                currRes = "";
                typeOfOperationFlag = 1;
                result.setText(tmp);
                res = fmt(cur);
                break;
            }
            case MUL: {
                double cur = resDouble * currResDouble;
                String tmp = makeLongRes(cur);
                currRes = "";
                typeOfOperationFlag = 1;
                result.setText(tmp);
                res = fmt(cur);
                break;
            }
            case SUBT: {
                double cur = resDouble - currResDouble;
                String tmp = makeLongRes(cur);
                currRes = "";
                typeOfOperationFlag = 1;
                result.setText(tmp);
                res = fmt(cur);
                break;
            }
            case ADD: {
                double cur = resDouble + currResDouble;
                String tmp = makeLongRes(cur);
                currRes = "";
                typeOfOperationFlag = 1;
                result.setText(tmp);
                res = fmt(cur);
                break;
            }
            case EQ: {
                if (typeOfOperationFlag == 0) {
                    res = currRes;
                    currRes = "";
                    typeOfOperationFlag = 1;
                    result.setText(res);
                }
                break;
            }

        }
    }

    void clickButton(String num) {
        if (currRes.equals("0") || tooLongNumber)
            return;
        if (checkLength(currRes)) {
            currRes += num;
            result.setText(currRes);
            typeOfOperationFlag = 0;
        }
    }


    boolean checkLength(String op){
        if (op.length() < 9 && !op.contains("."))
            return true;
        if (op.length() < 10 && op.contains("."))
            return true;
        return false;
    }

    String makeLongRes(double d) {
        if (Math.abs(d) < 0.00000001)
            d = 0;
        long doubleToLong = Math.abs((long)d);
        int longLenght = Long.toString(doubleToLong).length();
        String tmp;
        if (longLenght > 9) {
            if (longLenght > 18) {
                tmp = "Ошибка";
                tooLongNumber = true;
            }
            else
                tmp = String.format("%.7e", (double) d);
        } else {
            longLenght = (int)Math.pow(10,9 - longLenght);
            d *= longLenght;
            long i = (long) Math.round(d);
            d = (double) i / longLenght;
            tmp = fmt(d);
        }
        return tmp;

    }

    private static String fmt(double num) {
        if ((long) num == num) {
            return String.format("%d", (long)num);
        }
        else {
            long doubleToLong = Math.abs((long)num);
            int longLenght = Long.toString(doubleToLong).length();
            int fractionLen;
            if (longLenght < 9) {
                fractionLen = 9 - longLenght;
                String pattern = "###.#########";
                DecimalFormat nf = new DecimalFormat(pattern);
                nf.setMaximumFractionDigits(fractionLen);
                String tmp = String.valueOf(nf.format(num));
                return tmp.replace(',','.');
            }
            return String.format("%s", num).replace(',','.');
        }
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString(CURR_RES, currRes);
        state.putString(RES, res);
        state.putInt(TYPE_OF_OPERATION, typeOfOperationFlag);
        state.putBoolean(TOO_LONG_NUMBER_FLAG, tooLongNumber);
        state.putSerializable(CURR_OPERATION, currOperation);
        state.putString(TEXT, result.getText().toString());
    }
}
