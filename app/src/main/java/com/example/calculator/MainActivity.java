package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private enum State {
        InputFirstNum,
        InputAction,
        InputSecondNum,
        ShowResult
    };
    private State state = State.InputFirstNum;
    private String input = "0";
    private Double firstNum = .0;
    private Double secondNum = .0;
    private char action = '='; //no action
    private TextView ansText;
    private final int MAX_LENGTH = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            state = State.values()[savedInstanceState.getInt("state")];
            input = savedInstanceState.getString("input");
            firstNum = savedInstanceState.getDouble("firstNum");
            secondNum = savedInstanceState.getDouble("secondNum");
            action = savedInstanceState.getChar("action");
        }

        ansText = (TextView)findViewById(R.id.ansText);
        ansText.setText(input);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("state", state.ordinal());
        savedInstanceState.putString("input", input);
        savedInstanceState.putDouble("firstNum", firstNum);
        savedInstanceState.putDouble("secondNum", secondNum);
        savedInstanceState.putChar("action", action);
    }

    public void onClickDotButton(View v) {
        if (v.getId() == R.id.btnDot) {
            if (state == State.InputAction) {
                state = State.InputSecondNum;
                input = "0";
            }
            if (state == State.ShowResult) {
                state = State.InputFirstNum;
                input = "0";
            }
            if (input.contains(".") || input.length() >= MAX_LENGTH - 1) {
                return;
            }
            else {
                input += ".";
                ansText.setText(input);
            }
        }
    }

    public void onClickNumButton(View v) {
        Button btn = (Button) v;
        if (state == State.InputAction) {
            state = State.InputSecondNum;
            input = "0";
        }
        if (state == State.ShowResult) {
            state = State.InputFirstNum;
            input = "0";
        }
        if (input.equals("0")) {
            input = "";
        }
        if (input.length() < MAX_LENGTH) {
            input += btn.getText();
        }
        ansText.setText(input);
    }

    public void onClickDelButton(View v) {
        if (state == State.ShowResult) {
            state = State.InputFirstNum;
            input = "0";
            ansText.setText(input);
            return;
        }
        if (state == State.InputAction) {
            state = State.InputFirstNum;
        }
        if (input.length() >= 2) {
            input = input.substring(0, input.length() - 1);
        }
        else
            input = "0";
        ansText.setText(input);
    }

    public void onClickActButton(View v) {
        Button btn = (Button) v;
        switch (state) {
            case InputFirstNum:
            case InputAction:
                if (v.getId() != R.id.btnExe) {
                    firstNum = Double.parseDouble(input);
                    action = btn.getText().charAt(0);
                    state = State.InputAction;
                }
                break;
            case InputSecondNum:
                secondNum = Double.parseDouble(input);
                switch (action) {
                    case '+':
                        firstNum += secondNum;
                        break;
                    case '-':
                        firstNum -= secondNum;
                        break;
                    case '*':
                        firstNum *= secondNum;
                        break;
                    case '/':
                        firstNum /= secondNum;
                        break;
                }
                if (v.getId() == R.id.btnExe) {
                    state = State.ShowResult;
                } else {
                    state = State.InputAction;
                    action = btn.getText().charAt(0);
                }
                input = toFormatString(firstNum);
                ansText.setText(input);
                break;
            case ShowResult:
                if (v.getId() == R.id.btnExe) {
                    switch (action) {
                        case '+':
                            firstNum += secondNum;
                            break;
                        case '-':
                            firstNum -= secondNum;
                            break;
                        case '*':
                            firstNum *= secondNum;
                            break;
                        case '/':
                            firstNum /= secondNum;
                            break;
                    }
                    input = toFormatString(firstNum);
                    ansText.setText(input);
                } else {
                    if (!input.equals("Infinity")) {
                        state = State.InputAction;
                        action = btn.getText().charAt(0);
                    }
                }
        }
    }

    private String toFormatString(Double num) {
        boolean negate = (num < 0);
        num = negate ? -num : num;
        String res = Long.toString(num.longValue());
        num %= 1;
        if (res.length() > MAX_LENGTH) {
            Toast.makeText(this, "Слишком большое число!", Toast.LENGTH_SHORT).show();
            state = State.ShowResult;
            return "Infinity";
        }
        res += ".";
        while (res.length() <= MAX_LENGTH) {
            num *= 10;
            res += Integer.toString(num.intValue());
            num %= 1;
        }
        if (res.length() > MAX_LENGTH) {
            res = res.substring(0, MAX_LENGTH);
        }
        while (res.substring(res.length() - 1, res.length()).equals("0")) {
            res = res.substring(0, res.length() - 1);
        }
        if (res.endsWith(".")) {
            res = res.substring(0, res.length() - 1);
        }
        if (negate) {
            res = "-" + res;
        }
        return res;
    }
}

