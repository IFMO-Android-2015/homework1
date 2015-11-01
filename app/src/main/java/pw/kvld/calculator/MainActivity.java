package pw.kvld.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    enum Operation {ADD, DIV, MUL, SUB}

    Button buttonClear;
    Button buttonChangeSign;
    Button buttonComma;
    Button buttonEquals;
    Button buttonsNumbers[] = new Button[10];
    Map<Button, Operation> buttonsMathOperations = new HashMap<>();
    EditText operationsField;

    Double firstOperand;
    Double secondOperand;
    Operation currentOperation;
    Boolean isError = false;
    Boolean isSecond = false;
    Boolean isCommaTyped = false;
    Boolean isResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operationsField = (EditText) findViewById(R.id.operations);
        buttonsMathOperations.put((Button) findViewById(R.id.buttonAdd), Operation.ADD);
        buttonsMathOperations.put((Button) findViewById(R.id.buttonDivide), Operation.DIV);
        buttonsMathOperations.put((Button) findViewById(R.id.buttonMultiply), Operation.MUL);
        buttonsMathOperations.put((Button) findViewById(R.id.buttonSubtract), Operation.SUB);
        buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonChangeSign = (Button) findViewById(R.id.buttonChangeSgn);
        buttonComma = (Button) findViewById(R.id.buttonComma);
        buttonEquals = (Button) findViewById(R.id.buttonEquals);

        // numbers
        for (int i = 0; i < 10; i++) {
            String name = "buttonNum" + String.valueOf(i);
            buttonsNumbers[i] = (Button) findViewById(getResources().getIdentifier(name, "id", getPackageName()));
        }

        for (int i = 0; i < 10; i++) {
            buttonsNumbers[i].setOnClickListener(new OnNumClickListener(i) {
                @Override
                public void onClick(View v) {
                    String curNum = operationsField.getText().toString();
                    if (isError || (isResult && currentOperation == null)) {
                        curNum = "";
                        reset();
                    }
                    if (curNum.equals("0") || (currentOperation != null && !isSecond)) {
                        curNum = "";
                        isCommaTyped = false;
                    }
                    if (currentOperation != null) {
                        isSecond = true;
                    }
                    operationsField.setText(curNum + String.valueOf(number));
                }
            });
        }

        // math operations
        for (Map.Entry<Button, Operation> entry : buttonsMathOperations.entrySet()) {
            entry.getKey().setOnClickListener(new OnOpClickListener(entry.getValue()) {
                @Override
                public void onClick(View v) {
                    doOperation((Operation) operation, (Button) v);
                }
            });
        }


        // clear
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationsField.setText("0");
                reset();
            }
        });

        // a -> -a
        buttonChangeSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isError) {
                    String curNum = operationsField.getText().toString();
                    if (curNum.charAt(0) == '-') {
                        operationsField.setText(curNum.subSequence(1, curNum.length()));
                    } else if (!curNum.equals("0")) {
                        operationsField.setText("-" + curNum);
                    }
                }
            }
        });

        // decimal mark
        buttonComma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isError) {
                    if (!isCommaTyped) {
                        isCommaTyped = true;
                        operationsField.setText(operationsField.getText().toString() + '.');
                    }
                }
            }
        });

        // Equals 
        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentOperation == null || isError) {
                    return;
                }
                secondOperand = Double.parseDouble(operationsField.getText().toString());
                Double result = .0;
                try {
                    result = calculate();
                } catch (Exception e) {
                    isError = true;
                    operationsField.setText("Ошибка");
                    return;
                }

                // 14.0 -> 14
                if (result % 1 == 0) {
                    operationsField.setText(String.valueOf(result.longValue()));
                } else {
                    operationsField.setText(String.valueOf(result));
                }
                reset();
                isResult = true;
            }
        });

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        firstOperand = (Double) savedInstanceState.get("firstOperand");
        currentOperation = (Operation) savedInstanceState.get("currentOperation");
        isError = (Boolean) savedInstanceState.get("isError");
        isSecond = (Boolean) savedInstanceState.get("isSecond");
        isCommaTyped = (Boolean) savedInstanceState.get("isCommaTyped");
        isResult = (Boolean) savedInstanceState.get("isResult");

        // restore border on button
        for (Map.Entry<Button, Operation> entry : buttonsMathOperations.entrySet()) {
            if (currentOperation != null && currentOperation.equals(entry.getValue())) {
                entry.getKey().setBackgroundResource(R.drawable.btn_bg_border);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("firstOperand", firstOperand);
        outState.putSerializable("currentOperation", currentOperation);
        outState.putSerializable("isError", isError);
        outState.putSerializable("isSecond", isSecond);
        outState.putSerializable("isCommaTyped", isCommaTyped);
        outState.putSerializable("isResult", isResult);
    }

    private double calculate() throws ArithmeticException {
        switch (currentOperation) {
            case ADD:
                return firstOperand + secondOperand;
            case SUB:
                return firstOperand - secondOperand;
            case DIV:
                if (secondOperand.equals(0.0)) {
                    throw new ArithmeticException("Division by zero!");
                }
                return firstOperand / secondOperand;
            case MUL:
                return firstOperand * secondOperand;
            default:
                return 0;
        }
    }

    private void reset() {
        firstOperand = secondOperand = null;
        currentOperation = null;
        isError = isSecond = isCommaTyped = isResult = false;

        // reset border on button
        for (Map.Entry<Button, Operation> entry : buttonsMathOperations.entrySet()) {
            entry.getKey().setBackgroundColor(0x00000000);
        }
    }

    private void doOperation(Operation operation, Button opButton) {
        if (!isError) {
            buttonEquals.performClick();
            if (!isError) {
                String curNum = operationsField.getText().toString();
                firstOperand = Double.parseDouble(curNum);
                currentOperation = operation;

                // set border for entered operation
                opButton.setBackgroundResource(R.drawable.btn_bg_border);
            }
        }
    }
}
