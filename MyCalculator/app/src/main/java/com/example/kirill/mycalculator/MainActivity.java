package com.example.kirill.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    enum Operation {
        NOTHING,
        MULTIPLICATION ,
        ADDITION,
        DIVISION,
        SUBSTRACTION
    }

    private EditText editText;
    private double first = 0;
    private double second = 0;
    private Operation curOperation = Operation.NOTHING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        editText.setEnabled(false);
    }

    private void toDuble(double operand) {
        String numb = editText.getText().toString();
        if(numb.length() > 0) {
            operand = Double.parseDouble(editText.getText().toString());
        }
    }

    private void add() {
        if(curOperation == Operation.NOTHING) {
            curOperation = Operation.ADDITION;
        }
    }

    private void substract() {
        if(curOperation == Operation.NOTHING) {
            curOperation = Operation.SUBSTRACTION;
        }
    }

    private void multiplicate() {
        if(curOperation == Operation.NOTHING) {
            curOperation = Operation.MULTIPLICATION;
        }
    }

    private void divide() {
        if(curOperation == Operation.NOTHING) {
            curOperation = Operation.DIVISION;
        }
    }

    private void culculation() {

    }

    private void deleting() {
        String cur = editText.getText().toString();
        if(cur.length() != 0) {
            String next = cur.substring(0, cur.length() - 1);
            editText.setText(next);
        }
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.button0: editText.append("0"); break;
            case R.id.button1: editText.append("1"); break;
            case R.id.button2: editText.append("2"); break;
            case R.id.button3: editText.append("3"); break;
            case R.id.button4: editText.append("4"); break;
            case R.id.button5: editText.append("5"); break;
            case R.id.button6: editText.append("6"); break;
            case R.id.button7: editText.append("7"); break;
            case R.id.button8: editText.append("8"); break;
            case R.id.button9: editText.append("9"); break;
            case R.id.button10: add(); break;
            case R.id.button11: substract(); break;
            case R.id.button12: multiplicate(); break;
            case R.id.button13: divide(); break;
            case R.id.button14: culculation(); break;
            case R.id.button15: deleting(); break;
        }
    }
}
