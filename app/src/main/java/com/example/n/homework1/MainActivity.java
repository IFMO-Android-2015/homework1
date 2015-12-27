package com.example.n.homework1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.n.homework1.parser.GenericDouble;
import com.example.n.homework1.parser.GenericExpression;
import com.example.n.homework1.parser.GenericParser;

public class MainActivity extends AppCompatActivity {
    private TextView calculationTextView;
    private GenericParser<GenericDouble> parser;
    private String resultString;
    private String messageString;
    private boolean dotPlaced;
    private boolean message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculationTextView = (TextView)findViewById(R.id.calculation_textView);
        if(savedInstanceState != null) {
            resultString = savedInstanceState.getString("resultString");
            dotPlaced = savedInstanceState.getBoolean("dotPlaced");
            messageString = savedInstanceState.getString("messageString");
            message = savedInstanceState.getBoolean("message");
        } else {
            resultString = "";
            messageString = "";
            dotPlaced = false;
            message = false;
        }
        parser = new GenericParser<>();
        updateUI();

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("resultString", resultString);
        outState.putString("messageString", messageString);
        outState.putBoolean("dotPlaced", dotPlaced);
        outState.putBoolean("message", message);
    }
    public void DigitAction(View view) {
        resultString += ((TextView) view).getText().toString();
        message = false;
        updateUI();
    }
    public void ACAction(View view) {
        resultString = "";
        dotPlaced = false;
        message = false;
        updateUI();
    }
    public void CEAction(View view) {
        if(resultString.length() > 0) {
            if(resultString.charAt(resultString.length() - 1) == '.') {
                dotPlaced = false;
            }
            resultString = resultString.substring(0, resultString.length() - 1);
        }
        message = false;
        updateUI();
    }
    public void ResultAction(View view) {
        if(resultString.isEmpty())
            return;
        try {
            while(!Character.isDigit(resultString.charAt(resultString.length() - 1))) {
                resultString = resultString.substring(0, resultString.length() - 1);
            }
            GenericExpression<GenericDouble> expr = parser.parse(resultString, new GenericDouble(0.0));
            GenericDouble answer = expr.evaluate(new GenericDouble(0.0), new GenericDouble(0.0), new GenericDouble(0.0));
            if(!Character.isDigit(answer.value().toString().charAt(answer.value().toString().length() - 1))) {
                messageString = answer.value().toString();
                messageString = messageString.replace("-", getString(R.string.minus));
                message = true;
            } else {
                resultString = answer.value().toString();
                resultString = resultString.replace("-", getString(R.string.minus));
                dotPlaced = true;
            }
        } catch(Exception e) {
            messageString = "ERROR";
            message = true;
        }
        updateUI();
    }
    public void DotAction(View view) {
        if(!resultString.isEmpty() && Character.isDigit(resultString.charAt(resultString.length() - 1)) && !dotPlaced) {
            resultString += ((TextView)view).getText();
            dotPlaced = true;
        } else if(resultString.isEmpty() || resultString.charAt(resultString.length() - 1) != '.' && !dotPlaced){
            resultString += getString(R.string.zero) + ((TextView)view).getText();
            dotPlaced = true;
        }
        message = false;
        updateUI();
    }
    public void OperationAction(View view) {
        if(((TextView)view).getText() == getString(R.string.minus)) {
            if (resultString.isEmpty())
                resultString += ((TextView)view).getText();
            else if (Character.isDigit(resultString.charAt(resultString.length() - 1)) || resultString.charAt(resultString.length() - 1) == '÷' || resultString.charAt(resultString.length() - 1) == '×') {
                resultString += ((TextView)view).getText();
            } else {
                resultString = resultString.substring(0, resultString.length() - 1) + ((TextView)view).getText();
            }
        } else {
            if (resultString.isEmpty())
                return;
            if (Character.isDigit(resultString.charAt(resultString.length() - 1))) {
                resultString += ((TextView)view).getText();
            } else if (resultString.length() > 1 && (Character.isDigit(resultString.charAt(resultString.length() - 2)) || resultString.charAt(resultString.length() - 2) == '.')) {
                resultString = resultString.substring(0, resultString.length() - 1) + ((TextView)view).getText();
            } else {
                resultString = resultString.substring(0, resultString.length() - 1);
            }
        }
            dotPlaced = false;
            message = false;
            updateUI();
    }

    private void updateUI() {
        if(message)
            calculationTextView.setText(messageString);
        else
            calculationTextView.setText(resultString);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
