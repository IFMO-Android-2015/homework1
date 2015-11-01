package ru.ifmo.android_2015.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends Activity {
    enum Operation {
        NOTHING, ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    double precision = 1e-10;

    TextView textField;
    TextView oldField;
    TextView memoryLabel;
    ScrollView sv;

    String textString;
    boolean eraseField;
    boolean hasDot;
    double firstOperand;
    Operation currentOperation;
    double memory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        textField = (TextView) findViewById(R.id.textView);
        oldField = (TextView) findViewById(R.id.oldView);
        sv = (ScrollView) findViewById(R.id.scrollView);
        memoryLabel = (TextView) findViewById(R.id.memoryLabel);

        int[] digitButtonsId = {
                R.id.button0, R.id.button1, R.id.button2,
                R.id.button3, R.id.button4, R.id.button5,
                R.id.button6, R.id.button7, R.id.button8,
                R.id.button9};

        Button[] digitButtons;
        digitButtons = new Button[10];
        for (int i = 0; i < 10; i++) {
            digitButtons[i] = (Button) findViewById(digitButtonsId[i]);
        }

        for (int i = 0; i < 10; i++) {
            digitButtons[i].setOnClickListener(makeDigitListener(i));
        }

        Button dotButton = (Button) findViewById(R.id.dotButton);
        dotButton.setOnClickListener(dotClickListener);

        int[] operationButtonsId =
                {R.id.pButton, R.id.mButton, R.id.sButton, R.id.dButton};
        Operation[] operationButtonsMeaning =
                {Operation.ADD, Operation.MULTIPLY, Operation.SUBTRACT, Operation.DIVIDE};

        Button[] operationButtons = new Button[4];
        for (int i = 0; i < 4; i++) {
            operationButtons[i] = (Button) findViewById(operationButtonsId[i]);
            operationButtons[i].setOnClickListener(makeOperationListener(operationButtonsMeaning[i]));
        }

        Button eqButton = (Button) findViewById(R.id.equalButton);
        eqButton.setOnClickListener(equalButtonListener);

        Button cButton = (Button) findViewById(R.id.clearButton);
        cButton.setOnClickListener(clearButtonListener);

        Button pmButton = (Button) findViewById(R.id.pmButton);
        pmButton.setOnClickListener(pmButtonListener);

        Button inverseButton = (Button) findViewById(R.id.inverseButton);
        inverseButton.setOnClickListener(inverseButtonListener);

        Button eraseButton = (Button) findViewById(R.id.eraseButton);
        eraseButton.setOnClickListener(eraseButtonListener);

        Button memcButton = (Button) findViewById(R.id.memcButton);
        memcButton.setOnClickListener(memcButtonListener);

        Button memrButton = (Button) findViewById(R.id.memrButton);
        memrButton.setOnClickListener(memrButtonListener);

        Button mempButton = (Button) findViewById(R.id.mempButton);
        mempButton.setOnClickListener(mempButtonListener);

                memoryLabel.setVisibility(View.INVISIBLE);
        if (savedInstanceState != null) {
            eraseField = savedInstanceState.getBoolean("erase");
            hasDot = savedInstanceState.getBoolean("dot");
            firstOperand = savedInstanceState.getDouble("operand");
            currentOperation = (Operation) savedInstanceState.getSerializable("op");
            setTextField(savedInstanceState.getString("text"));
            memory = savedInstanceState.getDouble("memory");
            if (Math.abs(memory) > precision) {
                memoryLabel.setVisibility(View.VISIBLE);
            }
        } else {
            clear();
            memory = 0;
        }
        sv.setSmoothScrollingEnabled(false);
        scrollToBottom();
        sv.setSmoothScrollingEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("operand", firstOperand);
        outState.putDouble("memory", memory);
        outState.putBoolean("erase", eraseField);
        outState.putBoolean("dot", hasDot);
        outState.putSerializable("op", currentOperation);
        outState.putString("text", textString);
    }

    private View.OnClickListener makeDigitListener(final int c) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                scrollToBottom();
                if (eraseField || textString.equals("0")) {
                    textString = "";
                    eraseField = false;
                    hasDot = false;
                }

                textString += c;
                textField.setText(textString);
            }
        };
    }

    View.OnClickListener dotClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            scrollToBottom();
            if (hasDot) {
                return;
            }
            if (eraseField) {
                setTextField("0.");
                eraseField = false;
            } else {
                addToTextField(".");
            }
            hasDot = true;
        }
    };

    private View.OnClickListener makeOperationListener(final Operation operation) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opnd;
                if (currentOperation != Operation.NOTHING) {
                    opnd = doOperation();
                } else {
                    opnd = textString;
                    addToOldField(textString);
                }
                if (opnd.equals("!")) {
                    currentOperation = Operation.NOTHING;
                } else {
                    currentOperation = operation;
                    firstOperand = Double.parseDouble(opnd);
                    addToOldField(((Button) v).getText().toString());

                    setZero();
                }
                scrollToBottom();
            }
        };
    }

    String doOperation() {
        addToOldField(textString);
        addToOldField("=");
        double secondOperand = Double.parseDouble(textString);
        double res = 0;
        switch (currentOperation) {
            case NOTHING:
                res = secondOperand;
                break;
            case ADD:
                res = firstOperand + secondOperand;
                break;
            case SUBTRACT:
                res = firstOperand - secondOperand;
                break;
            case MULTIPLY:
                res = firstOperand * secondOperand;
                break;
            case DIVIDE:
                if (Math.abs(secondOperand) < precision) {
                    divideByZero();
                    return "!";
                }
                res = firstOperand / secondOperand;
                break;
        }
        String result = formatDouble(res);
        addToOldField(result);
        return result;
    }

    View.OnClickListener equalButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String result = doOperation();
            currentOperation = Operation.NOTHING;
            if (!result.equals("!")) {
                setTextField(result);
                eraseField = true;
                hasDot = result.contains(".");
            }
            scrollToBottom();
        }
    };

    View.OnClickListener clearButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clear();
        }
    };

    View.OnClickListener pmButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (textString.equals("0")) {
                return;
            }
            if (textString.charAt(0) == '-') {
                setTextField(textString.substring(1));
            } else {
                setTextField("-" + textString);
            }
        }
    };

    View.OnClickListener inverseButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            double operand = Double.parseDouble(textString);
            if (Math.abs(operand) < precision) {
                divideByZero();
                return;
            }
            double res = 1 / operand;
            setTextField(formatDouble(res));
            hasDot = textString.contains(".");
        }
    };

    View.OnClickListener eraseButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (textString.equals("0")) {
                return;
            }
            if (textString.length() == 1) {
                setZero();
                return;
            }
            if (textString.length() == 2 && textString.charAt(0) == '-') {
                setZero();
                return;
            }
            setTextField(textString.substring(0, textString.length() - 1));
            hasDot = textString.contains(".");
        }
    };

    View.OnClickListener memcButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            memory = 0;
            memoryLabel.setVisibility(View.INVISIBLE);
        }
    };

    View.OnClickListener memrButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setTextField(formatDouble(memory));
            eraseField = false;
            hasDot = textString.contains(".");
        }
    };

    View.OnClickListener mempButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            double op = Double.parseDouble(textString);
            memory += op;
            if (Math.abs(memory)>precision) {
                memoryLabel.setVisibility(View.VISIBLE);
            } else {
                memoryLabel.setVisibility(View.INVISIBLE);
            }
        }
    };

    private void clear() {
        setZero();
        setOldField("");
        currentOperation = Operation.NOTHING;
        firstOperand = 0;
    }

    String formatDouble(double res) {
        if (Math.abs(res - Math.round(res)) < precision) {
            return new DecimalFormat("#").format(res);
        } else {
            return Double.toString(res);
        }
    }

    private void setTextField(String s) {
        textString = s;
        textField.setText(s);
    }

    private void divideByZero() {
        addToOldField("Не могу делить на 0");
        setZero();
    }

    private void setZero() {
        setTextField("0");
        eraseField = true;
        hasDot = false;
    }

    private void addToTextField(String s) {
        setTextField(textString + s);
    }

    private void setOldField(String s) {
        oldField.setText(s);
    }

    private void addToOldField(String s) {
        oldField.append("\n" + s);
    }

    void scrollToBottom() {
        sv.post(new Runnable() {
            @Override
            public void run() {
                sv.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
