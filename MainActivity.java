package com.example.admin.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    private enum State {NONE, PLUS, MINUS, MUL, DIV}

    TextView tv;
    double operand1, operand2;
    StringBuilder curNumber;
    State state;
    Button btOne, btTwo, btThree, btFour, btFive;
    Button btSix, btSeven, btEight, btNine, btZero;
    Button btPoint, btClear, btEqual, btPlus, btMinus, btMulti, btDiv;
    int[] bt_ids;
    Button[] bt_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_screen);

        tv = (TextView) findViewById(R.id.tv);
        bt_ids = new int[]{R.id.btOne, R.id.btTwo, R.id.btThree, R.id.btFour, R.id.btFive,

                R.id.btSix, R.id.btSeven, R.id.btEight, R.id.btNine, R.id.btZero,

                R.id.btPlus, R.id.btMinus, R.id.btMulti, R.id.btDiv, R.id.btEqual,

                R.id.btClear, R.id.btPoint};

        bt_array = new Button[]{btOne, btTwo, btThree, btFour, btFive, btSix, btSeven,

                btEight, btNine, btZero, btPlus, btMinus, btMulti, btDiv, btEqual,

                btClear, btPoint};

        for (int i = 0; i < bt_array.length; i++) {

            bt_array[i] = (Button) findViewById(bt_ids[i]);

            bt_array[i].setOnClickListener(this);

        }

        operand1 = 0;
        operand2 = 0;
        state = State.NONE;
        curNumber = new StringBuilder();
        tv.setText(curNumber);
    }

    private void appendSymbol(char symbol) {
        curNumber.append(symbol);
        tv.setText(curNumber);
        if (symbol != '.') {
            if (state == State.NONE) {
                try {
                    operand1 = Double.parseDouble(curNumber.toString());
                } catch (Exception e) {
                    Toast.makeText(this, "Invalid number", Toast.LENGTH_LONG).show();
                    curNumber = new StringBuilder();
                    tv.setText("");
                }
            } else {
                try {
                    operand2 = Double.parseDouble(curNumber.toString());
                } catch (Exception e) {
                    Toast.makeText(this, "Invalid number", Toast.LENGTH_LONG).show();
                    curNumber = new StringBuilder();
                    tv.setText("");
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btOne:
                appendSymbol('1');
                break;

            case R.id.btTwo:
                appendSymbol('2');
                break;

            case R.id.btThree:
                appendSymbol('3');
                break;

            case R.id.btFour:
                appendSymbol('4');
                break;

            case R.id.btFive:
                appendSymbol('5');
                break;

            case R.id.btSix:
                appendSymbol('6');
                break;

            case R.id.btSeven:
                appendSymbol('7');
                break;

            case R.id.btEight:
                appendSymbol('8');
                break;

            case R.id.btNine:
                appendSymbol('9');
                break;

            case R.id.btZero:
                appendSymbol('0');
                break;

            case R.id.btPoint:
                appendSymbol('.');
                break;

            case R.id.btPlus:
                if (state == State.NONE) state = State.PLUS;
                curNumber = new StringBuilder();
                break;

            case R.id.btMinus:
                if (state == State.NONE) state = State.MINUS;
                curNumber = new StringBuilder();
                break;

            case R.id.btMulti:
                if (state == State.NONE) state = State.MUL;
                curNumber = new StringBuilder();
                break;

            case R.id.btDiv:
                if (state == State.NONE) state = State.DIV;
                curNumber = new StringBuilder();
                break;

            case R.id.btClear:
                operand1 = 0;
                operand2 = 0;
                tv.setText("");
                curNumber = new StringBuilder();
                break;

            case R.id.btEqual:
                switch (state) {
                    case NONE:
                        break;
                    case PLUS:
                        curNumber = new StringBuilder();
                        tv.setText("");
                        operand1 += operand2;
                        break;
                    case MINUS:
                        curNumber = new StringBuilder();
                        tv.setText("");
                        operand1 -= operand2;
                        break;
                    case MUL:
                        curNumber = new StringBuilder();
                        tv.setText("");
                        operand1 *= operand2;
                        break;
                    case DIV:
                        curNumber = new StringBuilder();
                        tv.setText("");
                        operand1 /= operand2;
                        break;
                }
                tv.setText(Double.toString(operand1));
                operand2 = 0;
                state = State.NONE;
                break;


        }
    }


}
