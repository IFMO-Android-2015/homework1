package com.ifmo.android_2015.ivan_pavlov.homework1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CalculatorBrain brain = new CalculatorBrain();
    TextView resultTextView;
    boolean userIsInTheMiddleOfTypingANumber = false;
    boolean isError = false;

    int [] digitButtons = new int[] {
            R.id.num0Button,
            R.id.num1Button,
            R.id.num2Button,
            R.id.num3Button,
            R.id.num4Button,
            R.id.num5Button,
            R.id.num6Button,
            R.id.num7Button,
            R.id.num8Button,
            R.id.num9Button,
    };

    int [] operationButtons = new int[] {
            R.id.ChangeSignButton,
            R.id.PercentageButton,
            R.id.evaluateButton,
            R.id.addButton,
            R.id.subtractButton,
            R.id.multiplicationButton,
            R.id.divideButton,
    };

    int [] controlButtons = new int[] {
            R.id.evaluateButton,
            R.id.ACButton
    };

    View.OnClickListener digitsListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            if(isError)
                return;
            if(userIsInTheMiddleOfTypingANumber) {
                resultTextView.setText(resultTextView.getText().toString().concat(btn.getText().toString()));
            } else {
                resultTextView.setText(btn.getText().toString());
                userIsInTheMiddleOfTypingANumber = true;
            }
        }
    };

    View.OnClickListener dotOnCLickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if(isError)
                return;
            int dotCount = 0;
            String text = resultTextView.getText().toString();
            if(!text.contains(".")) {
                resultTextView.setText(resultTextView.getText().toString().concat("."));
                userIsInTheMiddleOfTypingANumber = true;
            }
        }
    };

    View.OnClickListener operationOnCLickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if(isError)
                return;
            Button btn = (Button)view;
            String op = btn.getText().toString();
            double value = 0;
            try {
                value = Double.valueOf(resultTextView.getText().toString());
            }catch (Exception e) {
                userIsInTheMiddleOfTypingANumber = false;
                resultTextView.setText("error");
                isError = true;
                return;
            }
            if(brain.latest == null) {
                brain.latest = value;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        resultTextView = (TextView)findViewById(R.id.resultsTextView);
        for(int btnId : this.digitButtons)
            findViewById(btnId).setOnClickListener(this.digitsListener);
        findViewById(R.id.dotButton).setOnClickListener(this.dotOnCLickListener);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("userIsInTheMiddleOfTypingANumber", this.userIsInTheMiddleOfTypingANumber);
        savedInstanceState.putCharSequence("textViewData", this.resultTextView.getText());
        savedInstanceState.putSerializable("brain", this.brain);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.userIsInTheMiddleOfTypingANumber = savedInstanceState.getBoolean("userIsInTheMiddleOfTypingANumber");
        this.resultTextView.setText(savedInstanceState.getCharSequence("textViewData"));
        this.brain = (CalculatorBrain)savedInstanceState.getSerializable("brain");
    }
}
