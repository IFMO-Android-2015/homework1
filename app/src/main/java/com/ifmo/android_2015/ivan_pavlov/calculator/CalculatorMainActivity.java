package com.ifmo.android_2015.ivan_pavlov.calculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class CalculatorMainActivity extends ActionBarActivity implements View.OnClickListener {

    TextView resultTextView;
    CalculatorBrain brain = new CalculatorBrain();
    boolean userIsInTheMiddleOfTypingANumber = false;

    int [] operationButtons = new int[] {
        R.id.changeSignButton,
        R.id.percentageButton,
        R.id.evaluateButton,
        R.id.addButton,
        R.id.subtractButton,
        R.id.multiplicationButton,
        R.id.divideButton,
    };

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

    int [] controlButtons = new int[] {
        R.id.enterButton,
        R.id.evaluateButton,
        R.id.ACButton
    };

    void pushOperand() {
        double value = 0;
        try {
            value = Double.valueOf(resultTextView.getText().toString());
        }catch (Exception e) {
            //пусть вводит заново
            userIsInTheMiddleOfTypingANumber = false;
            resultTextView.setText("0");
            return;
        }
        brain.pushOperand(value);
        resultTextView.setText("0");
        userIsInTheMiddleOfTypingANumber = false;
    }

    View.OnClickListener operationsListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            if(userIsInTheMiddleOfTypingANumber)
                pushOperand();
            brain.pushOperation(btn.getText().charAt(0));
            resultTextView.setText("0");
            userIsInTheMiddleOfTypingANumber = false;
        }
    };
    View.OnClickListener digitsListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            if(btn.getText().toString().equals("0") && resultTextView.getText().equals("0"))
                return;
            if(userIsInTheMiddleOfTypingANumber)
                resultTextView.setText(resultTextView.getText().toString().concat(btn.getText().toString()));
            else {
                resultTextView.setText(btn.getText().toString());
                userIsInTheMiddleOfTypingANumber = true;
            }
        }
    };

    View.OnClickListener dotOnCLickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String text = resultTextView.getText().toString();
            if(!text.contains(".")) {
                resultTextView.setText(resultTextView.getText().toString().concat("."));
                userIsInTheMiddleOfTypingANumber = true;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_main);
        resultTextView = (TextView)findViewById(R.id.resultsTextView);
        findViewById(R.id.dotButton).setOnClickListener(this.dotOnCLickListener);
        for(int btnId : this.operationButtons)
            findViewById(btnId).setOnClickListener(this.operationsListener);
        for(int btnId : this.digitButtons)
            findViewById(btnId).setOnClickListener(this.digitsListener);
        for(int btnId : this.controlButtons)
            findViewById(btnId).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluateButton:
                resultTextView.setText(String.format(Locale.ENGLISH, "%f", this.brain.evaluate()));
                break;
            case R.id.ACButton:
                brain.clear();
                resultTextView.setText("0");
                userIsInTheMiddleOfTypingANumber = false;
                break;
            case R.id.enterButton:
                pushOperand();
                break;
        }
        userIsInTheMiddleOfTypingANumber = false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("userIsInTheMiddleOfTypingANumber", this.userIsInTheMiddleOfTypingANumber);
        savedInstanceState.putCharSequence("textViewData", this.resultTextView.getText());
        savedInstanceState.putParcelable("brain", this.brain);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.userIsInTheMiddleOfTypingANumber = savedInstanceState.getBoolean("userIsInTheMiddleOfTypingANumber");
        this.resultTextView.setText(savedInstanceState.getCharSequence("textViewData"));
        this.brain = (CalculatorBrain)savedInstanceState.getParcelable("brain");
    }
}
