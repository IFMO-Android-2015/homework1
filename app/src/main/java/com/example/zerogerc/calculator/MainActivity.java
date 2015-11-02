package com.example.zerogerc.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "MainActivity";
    private final String INF = "Infinity";
    private final String MINF = "-Infinity";
    private final String NAN = "NaN";

    static final String DISPLAY = "display";
    static final String CURRENT_TEXT = "currentText";
    static final String RESULT = "result";
    static final String CURRENT_OPERATION = "currentOperation";
    static final String HAS_COMMA = "hasComma";

    private TextView display;
    private StringBuilder currentText;
    private String result;
    private String currentOperation;
    private boolean hasComma;

    private String add(String first, String second) {
        if (first.equals("-")) {
            first = "0";
        }
        if (second.equals("-")) {
            second = "0";
        }
        if (first.equals(NAN)) {
            return NAN;
        }
        if (first.equals(INF) || first.equals(MINF)) {
            return first;
        }
        return (new BigDecimal(first).add(new BigDecimal(second))).toString();
    }

    private String subtract(String first, String second) {
        if (first.equals("-")) {
            first = "0";
        }
        if (second.equals("-")) {
            second = "0";
        }
        if (first.equals(NAN)) {
            return NAN;
        }
        if (first.equals(INF) || first.equals(MINF)) {
            return first;
        }
        return (new BigDecimal(first).subtract(new BigDecimal(second))).toString();
    }

    private String multiply(String first, String second) {
        if (first.equals("-")) {
            first = "0";
        }
        if (second.equals("-")) {
            second = "0";
        }
        if (first.equals(NAN)) {
            return NAN;
        }
        if (first.equals(INF)) {
            if (new BigDecimal(second).equals(new BigDecimal(0))) {
                return NAN;
            } else if (new BigDecimal(second).compareTo(new BigDecimal(0)) > 0) {
                return INF;
            } else {
                return MINF;
            }
        }
        if (first.equals(MINF)) {
            if (new BigDecimal(second).equals(new BigDecimal(0))) {
                return NAN;
            } else if (new BigDecimal(second).compareTo(new BigDecimal(0)) > 0) {
                return MINF;
            } else {
                return INF;
            }
        }
        return (new BigDecimal(first).multiply(new BigDecimal(second))).toString();
    }

    private String divide(String first, String second) {
        if (first.equals("-")) {
            first = "0";
        }
        if (second.equals("-")) {
            second = "0";
        }
        if (first.equals(NAN)) {
            return NAN;
        }
        if (first.equals(INF)) {
            if (new BigDecimal(second).compareTo(new BigDecimal(0)) >= 0) {
                return INF;
            } else {
                return MINF;
            }
        }
        if (first.equals(MINF)) {
            if (new BigDecimal(second).compareTo(new BigDecimal(0)) >= 0) {
                return MINF;
            } else {
                return INF;
            }
        }
        if (new BigDecimal(second).equals(new BigDecimal(0))) {
            return INF;
        }
        return new BigDecimal(first).divide(new BigDecimal(second), 10, RoundingMode.CEILING).toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.display = (TextView)findViewById(R.id.display);
        this.display.setText("0");

        this.resetFields();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(DISPLAY, this.display.getText().toString());
        savedInstanceState.putString(CURRENT_TEXT, this.currentText.toString());
        savedInstanceState.putString(RESULT, this.result);
        savedInstanceState.putString(CURRENT_OPERATION, this.currentOperation);
        savedInstanceState.putBoolean(HAS_COMMA, this.hasComma);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.display.setText(savedInstanceState.getString(DISPLAY));
        this.currentText = new StringBuilder();
        this.currentText.append(savedInstanceState.getString(CURRENT_TEXT));
        this.result = (savedInstanceState.getString(RESULT));
        this.currentOperation = savedInstanceState.getString(CURRENT_OPERATION);
        this.hasComma = savedInstanceState.getBoolean(HAS_COMMA);
    }

    public void addDigit(View v) {
        if (this.currentText.toString().equals(INF) || this.currentText.toString().equals(MINF) || this.currentText.toString().equals(NAN)) {
            return;
        }
        if (this.currentText.toString().equals("0")) {
            this.currentText = new StringBuilder();
        }
        Button currentButton = (Button)v;
        if ((this.currentText.length() == 0) && currentButton.getText().toString().equals("0")) {
            return;
        }
        this.currentText.append(currentButton.getText());
        this.display.setText(this.currentText.toString().toCharArray(), 0, this.currentText.length());
    }

    public void addComma(View v) {
        if (this.currentText.toString().equals(INF) || this.currentText.toString().equals(MINF) || this.currentText.toString().equals(NAN)) {
            return;
        }
        if (!this.hasComma) {
            this.hasComma = true;
            if (this.currentText.length() == 0) {
                this.currentText.append('0');
            }
            this.currentText.append('.');
            this.display.setText(this.currentText.toString().toCharArray(), 0, this.currentText.length());
        }
    }

    public void deleteOne(View v) {
        if (this.currentText.toString().equals(INF) || this.currentText.toString().equals(MINF) || this.currentText.toString().equals(NAN)) {
            return;
        }
        if (this.currentText.length() == 0) {
            return;
        }
        this.currentText.deleteCharAt(this.currentText.length() - 1);
        this.display.setText(this.currentText.toString().toCharArray(), 0, this.currentText.length());
    }

    private void resetFields() {
        this.currentText = new StringBuilder("");
        this.result = "0";
        this.currentOperation = "=";
        this.hasComma = false;
    }

    public void delete(View v) {
        this.display.setText("0");
        this.resetFields();
    }

    public void applyOperation(View v) {
        Button currentButton = (Button)v;

        if (currentButton.getText().toString().equals("-") && this.currentText.length() == 0) {
            this.currentText.append('-');
            this.display.setText(this.currentText.toString().toCharArray(), 0, this.currentText.length());
            return;
        }

        if (this.currentText.length() == 0) {
            this.currentText.append('0');
        }

        if (this.currentOperation.equals("=")) {
            String temp = new String(this.currentText);
            resetFields();
            this.currentOperation = currentButton.getText().toString();
            this.result = temp;
            this.display.setText("0".toCharArray(), 0, 1);
        } else {
            switch (this.currentOperation) {
                case "+":
                    this.result = this.add(this.result, this.currentText.toString());
                    break;
                case "-":
                    this.result = this.subtract(this.result, this.currentText.toString());
                    break;
                case "x":
                    this.result = this.multiply(this.result, this.currentText.toString());
                    break;
                case "/":
                    this.result = this.divide(this.result, this.currentText.toString());
                    break;
            }

            this.hasComma = true;
            this.display.setText(this.result.toCharArray(), 0, this.result.length());
            this.currentText = new StringBuilder();
            this.currentOperation = currentButton.getText().toString();
        }
    }

    public void printResult(View v) {
        this.applyOperation(v);
        String temp = this.result;
        this.display.setText(temp.toCharArray(), 0, temp.length());
        this.currentText = new StringBuilder(temp);
    }

    public void swapSign(View v) {
        if (this.currentText.toString().equals(NAN)) {
            return;
        }
        if (this.currentText.toString().equals("-")) {
            this.currentText = new StringBuilder();
            this.currentText.append("0");

        } else if (this.currentText.length() == 0) {
            this.currentText.append('-');
        } else {
            StringBuilder temp = new StringBuilder(this.currentText.toString());

            if (temp.charAt(0) == '-') {
                this.currentText = new StringBuilder();
                this.currentText.append(temp.substring(1));
            } else {
                this.currentText = new StringBuilder();
                this.currentText.append('-');
                this.currentText.append(temp);
            }
        }

        this.display.setText(this.currentText.toString().toCharArray(), 0, this.currentText.length());
    }
}
