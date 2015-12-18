package ru.kozlov_ea.my_calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bAC, bC, bAns;

    TextView Ans;
    String inStr = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // определяем элементы

        bAC = (Button) findViewById(R.id.bAC);
        bC = (Button) findViewById(R.id.bC);
        bAns = (Button) findViewById(R.id.bAns);

        Ans = (TextView) findViewById(R.id.textView);

        int[] digits = new int[] {R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9, R.id.b0, R.id.bdot};
        int[] operations = new int[] {R.id.bplus, R.id.bminus, R.id.bmul, R.id.bdiv};

        for(int number: digits) {
            findViewById(number).setOnClickListener(digitsListener);
        }

        for(int op: operations) {
            findViewById(op).setOnClickListener(operationsListener);
        }

        bAC.setOnClickListener(this);
        bC.setOnClickListener(this);
        bAns.setOnClickListener(this);
    }

    private boolean isBinaryOperation (char ch) {
        return (ch == '+') || (ch == '-') || (ch == '*') || (ch == '/');
    }

    View.OnClickListener digitsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button inBtn = (Button) view;
            inStr += inBtn.getText();
            Ans.setText(inStr);
        }
    };

    View.OnClickListener operationsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button inBtn = (Button) view;
            if (inStr.length() > 0) {
                int curLen = inStr.length() - 1;
                char cur = inStr.charAt(curLen);
                if (isBinaryOperation(cur)) {
                    inStr = inStr.substring(0, curLen);
                }
            }

            inStr += inBtn.getText();
            Ans.setText(inStr);
        }
    };


    // нажатая пользователем кнопка определяет текущую операцию
    public void onClick(View v) {
        try {
            switch (v.getId()) {

                case R.id.bAC:
                    inStr = "";
                    Ans.setText(inStr);
                    break;
                case R.id.bC:
                    inStr = inStr.substring(0, inStr.length() - 1);
                    Ans.setText(inStr);
                    break;

                case R.id.bAns:
                    double t = new ParserModel(inStr).Third();
                    String s = Double.toString(t);

                    Ans.setText(s);
                    inStr = s;
                    break;
            }
        } catch (Exception e) {
            Ans.setText("Invalid input");
            inStr = "";
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Input data", inStr);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        inStr = savedInstanceState.getString("Input data");
        Ans.setText(inStr);
    }
}
