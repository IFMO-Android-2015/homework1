package com.example.kirill.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        editText.setEnabled(false);
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.button1: editText.append("1"); break;
            case R.id.button2: editText.append("2"); break;
            case R.id.button3: editText.append("3"); break;
            case R.id.button4: editText.append("4"); break;
            case R.id.button5: editText.append("5"); break;
            case R.id.button6: editText.append("6"); break;
            case R.id.button7: editText.append("7"); break;
            case R.id.button8: editText.append("8"); break;
            case R.id.button9: editText.append("9"); break;
        }
    }
}
