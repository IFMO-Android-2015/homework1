package emaxxbrowser.com.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int[] digitIds = new int[] {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9 };
    public static final int[] otherButtonIds = new int[] {R.id.buttonDecimalMark, R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonClear, R.id.buttonEquals, R.id.buttonBackSpace};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstFieldView = (TextView) findViewById(R.id.firstOperandField);
        secondFieldView = (TextView) findViewById(R.id.secondOperandField);
        signFieldView = (TextView) findViewById(R.id.signField);

        for (int digitId : digitIds) {
            findViewById(digitId).setOnClickListener(this);
        }
        for (int buttonId : otherButtonIds) {
            findViewById(buttonId).setOnClickListener(this);
        }

        MainActivity lastActivity = (MainActivity) getLastCustomNonConfigurationInstance();

        if (lastActivity == null) {
            firstField = new NumberBuilder();
            secondField = new NumberBuilder();
            currendField = firstField;
            currentSign = "";
        } else {
            firstField = lastActivity.firstField;
            secondField = lastActivity.secondField;
            currendField = lastActivity.currendField;
            currentSign = lastActivity.currentSign;
        }

        setText();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return this;
    }

    private TextView firstFieldView;
    private TextView secondFieldView;
    private TextView signFieldView;
    private NumberBuilder firstField;
    private NumberBuilder secondField;
    private NumberBuilder currendField;
    private String currentSign;

    private void setText() {
        firstFieldView.setText(firstField.getString().replace('.', DECIMAL_MARK));
        secondFieldView.setText(secondField.getString().replace('.', DECIMAL_MARK));
        signFieldView.setText(currentSign);
    }

    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final char TIMES = '×';
    private static final char OBELUS = '÷';
    private static final char DECIMAL_MARK = ',';
    private static final char EQUALS_SIGN = '=';
    private static final char BACK_SPACE = ' ';
    private static final char CLEAR = 'C';

    private char getCharacterById(int id) {
        switch (id) {
            case R.id.buttonAdd:
                return PLUS;
            case R.id.buttonSubtract:
                return MINUS;
            case R.id.buttonMultiply:
                return TIMES;
            case R.id.buttonDivide:
                return OBELUS;
            case R.id.buttonDecimalMark:
                return DECIMAL_MARK;
            case  R.id.buttonBackSpace:
                return BACK_SPACE;
            case R.id.buttonEquals:
                return EQUALS_SIGN;
            case R.id.buttonClear:
                return CLEAR;
            default:
                for (int i = 0; i < digitIds.length; i++) {
                    if (id == digitIds[i]) {
                        return (char) ('0' + i);
                    }
                }
                throw new AssertionError();
        }
    }

    public void onClick(View v) {
        processCharacter(getCharacterById(v.getId()));
        setText();
    }

    private void processCharacter(char x) {
        if (Character.isDigit(x) || x == DECIMAL_MARK) {
            currendField.append(x);
            return;
        }
        if (x == PLUS || x == MINUS || x == TIMES || x == OBELUS) {
            if (currendField == secondField) {
                calculate();
            }
            currendField = secondField;
            currentSign = x + "";
            return;
        }
        if (x == BACK_SPACE) {
            if (currendField == firstField) {
                currendField.deleteLast();
            } else {
                if (secondField.isEmpty()) {
                    currentSign = "";
                    currendField = firstField;
                } else {
                    currendField.deleteLast();
                }
            }
            return;
        }
        if (x == CLEAR) {
            if (currendField == firstField) {
                currendField.clear();
            } else {
                if (secondField.isEmpty()) {
                    currentSign = "";
                    currendField = firstField;
                } else {
                    secondField.clear();
                }
            }
            return;
        }
        if (x == EQUALS_SIGN) {
            if (currendField == secondField) {
                if (secondField.isEmpty()) {
                    currendField = firstField;
                    currentSign = "";
                } else {
                    calculate();
                }
            }
            return;
        }
        throw new AssertionError();
    }

    private void calculate() {
        double a = firstField.getNumber();
        double b = secondField.getNumber();
        double result;
        switch (currentSign.charAt(0)) {
            case PLUS:
                result = a + b;
                break;
            case MINUS:
                result = a - b;
                break;
            case TIMES:
                result = a * b;
                break;
            case OBELUS:
                result = a / b;
                break;
            default:
                throw new AssertionError();
        }
        if (Double.isInfinite(result) || Double.isNaN(result)) {
            firstField.setError();
        } else {
            firstField.setNumber(result);
        }
        currentSign = "";
        currendField = firstField;
        secondField.clear();
    }
}
