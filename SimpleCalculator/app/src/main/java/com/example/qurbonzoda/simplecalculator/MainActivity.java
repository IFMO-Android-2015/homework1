package com.example.qurbonzoda.simplecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
        int[] idList = {R.id.plus, R.id.minus, R.id.multiply, R.id.divide};

        for (int id : idList) {
            View view = findViewById(id);
            view.setOnClickListener(this);
        }
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("num1", num1.getText().toString());
        state.putString("num2", num2.getText().toString());
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        num1.setText(state.getString("num1"));
        num2.setText(state.getString("num2"));
    }

    public void onClick(View view) {
        try {
            double a = Double.parseDouble(num1.getText().toString());
            double b = Double.parseDouble(num2.getText().toString());
            double res;
            switch (view.getId()) {
                case R.id.plus:
                    res = a + b;
                    break;
                case R.id.minus:
                    res = a - b;
                    break;
                case R.id.divide:
                    res = a / b;
                    break;
                default:
                    res = a * b;
            }
            num1.setText(Double.toString(res));
            num2.setText("");
        } catch (Exception e) {
            num1.setText("");
            num2.setText("");
            Toast.makeText(getApplicationContext(), "Error occured!", Toast.LENGTH_SHORT).show();
        }
    }
}
