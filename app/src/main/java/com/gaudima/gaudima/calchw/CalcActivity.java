package com.gaudima.gaudima.calchw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class CalcActivity extends AppCompatActivity {
    private ArrayList<String> operands = new ArrayList<String>();
    private ArrayList<String> operators = new ArrayList<String>();

    private StringBuilder currentNumber = new StringBuilder();
    StringBuilder sb = new StringBuilder();
    private boolean currentNumberHasDot = false;
    TextView resultView;
    TextView formulaView;

    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if(id == R.id.buttonClearEverything) {
                clearEverything();
            } else if(id == R.id.buttonClear) {
                clearLast();
            } else if(id == R.id.buttonEquals) {
                String expr = String.valueOf(computeExpression());
                clearEverything();
                operands.set(0, expr);
                currentNumber.delete(0, currentNumber.length());
                currentNumber.append(expr);
                currentNumberHasDot = true;

            } else {
                    processInput(((Button) view).getText().charAt(0));
            }
            updateViews();
        }
    };

    private void clearLast() {
        if (operands.size() > 1) {
            if (operators.size() == operands.size()) {
                operators.remove(operators.size() - 1);
            } else {
                operands.remove(operands.size() - 1);
            }
        } else {
            if (operators.size() == operands.size()) {
                operators.remove(operators.size() - 1);
            } else {
                operands.set(0, "0");
            }
        }
        currentNumber.delete(0, currentNumber.length());
        currentNumberHasDot = false;
    }

    private void clearEverything() {
        operands.clear();
        operands.add("0");
        operators.clear();
        currentNumber.delete(0, currentNumber.length());
        currentNumberHasDot = false;
    }

    private void processInput(Character input) {
        if(Character.isDigit(input)) {
            currentNumber.append(input);
            if(operands.size() == operators.size()) {
                operands.add(currentNumber.toString());
            } else {
                operands.set(operands.size() - 1, currentNumber.toString());
            }

        } else if(input == '.') {
            if(!currentNumberHasDot) {
                if (currentNumber.length() == 0) {
                    currentNumber.append("0");
                }
                currentNumber.append(input);
                currentNumberHasDot = true;
                if (operands.size() == operators.size()) {
                    operands.add(currentNumber.toString());
                } else {
                    operands.set(operands.size() - 1, currentNumber.toString());
                }
            }
        } else {
            if(operands.size() == operators.size()) {
                operators.set(operators.size() - 1, String.valueOf(input));
            } else {
                operators.add(String.valueOf(input));
            }
            currentNumber.delete(0, currentNumber.length());
            currentNumberHasDot = false;
        }
    }

    private double computeExpression() {
        double result = Double.parseDouble(operands.get(0));
        for(int i = 1; i < operands.size(); i++) {
            result = processOperator(result, operators.get(i-1), Double.parseDouble(operands.get(i)));
        }
        return result;
    }

    private void updateViews() {
        for(int i = 0; i < operators.size(); i++) {
            sb.append(operands.get(i));
            sb.append(" ");
            sb.append(operators.get(i));
            sb.append(" ");
        }
        if(operands.size() > operators.size()) {
            sb.append(operands.get(operands.size() - 1));
        }
        formulaView.setText(sb.toString());
        sb.delete(0, sb.length());
        resultView.setText(String.valueOf(computeExpression()));
    }

    private double processOperator(double left, String operator, double right) {
        switch (operator) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                return left / right;
        }
        return right;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        operands.add("0");
        resultView = (TextView) findViewById(R.id.resultView);
        formulaView = (TextView) findViewById(R.id.formulaView);
        TableLayout buttonTable = (TableLayout) findViewById(R.id.buttonTable);
        for(int i = 0; i < buttonTable.getChildCount(); i++) {
            TableRow buttonRow = (TableRow) buttonTable.getChildAt(i);
            for(int j = 0; j < buttonRow.getChildCount(); j++) {
                Button button = (Button) buttonRow.getChildAt(j);
                button.setOnClickListener(buttonClick);
            }
        }
        updateViews();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        state.putStringArrayList("operators", operators);
        state.putStringArrayList("operands", operands);
        state.putString("currentNumber", currentNumber.toString());
        state.putBoolean("currentNumberHasDot", currentNumberHasDot);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        operators = state.getStringArrayList("operators");
        operands = state.getStringArrayList("operands");
        currentNumber.append(state.getString("currentNumber"));
        currentNumberHasDot = state.getBoolean("currentNumberHasDot");
        updateViews();
    }
}
