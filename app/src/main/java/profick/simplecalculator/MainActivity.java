package profick.simplecalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    TextView tvResult;
    private StringBuilder stringNumber;
    private double number;
    private boolean isFirst;
    private char operation;


    private void toScreen(String str) {
        if (str.length() > 2) {
            if (str.charAt(str.length() - 2) == '.' && str.charAt(str.length() - 1) == '0') {
                str = str.substring(0, str.length() - 2);
            }
        }


        tvResult.setText(str);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myscreen);

        if (savedInstanceState == null) {
            stringNumber = new StringBuilder();
            clearStringNumber();
            number = 0;
            isFirst = true;
            operation = 0;
        }


        findViewById(R.id.buttonZero).setOnClickListener(this);
        findViewById(R.id.buttonOne).setOnClickListener(this);
        findViewById(R.id.buttonTwo).setOnClickListener(this);
        findViewById(R.id.buttonThree).setOnClickListener(this);
        findViewById(R.id.buttonFour).setOnClickListener(this);
        findViewById(R.id.buttonFive).setOnClickListener(this);
        findViewById(R.id.buttonSix).setOnClickListener(this);
        findViewById(R.id.buttonSeven).setOnClickListener(this);
        findViewById(R.id.buttonEight).setOnClickListener(this);
        findViewById(R.id.buttonNine).setOnClickListener(this);
        findViewById(R.id.buttonPoint).setOnClickListener(this);
        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonSub).setOnClickListener(this);
        findViewById(R.id.buttonMul).setOnClickListener(this);
        findViewById(R.id.buttonDiv).setOnClickListener(this);
        findViewById(R.id.buttonAC).setOnClickListener(this);
        findViewById(R.id.buttonRNG).setOnClickListener(this);
        findViewById(R.id.buttonEqual).setOnClickListener(this);
        findViewById(R.id.buttonSign).setOnClickListener(this);

        tvResult = (TextView) findViewById(R.id.textViewResult);
        tvResult.setText(stringNumber);

    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("stringNumber", stringNumber.toString());
        outState.putChar("operation", operation);
        outState.putDouble("number", number);
        outState.putBoolean("isFirst", isFirst);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        stringNumber = new StringBuilder(savedInstanceState.getString("stringNumber"));
        operation = savedInstanceState.getChar("operation");
        getButton(operation).setBackgroundColor(Color.GREEN);
        number = savedInstanceState.getDouble("number");
        isFirst = savedInstanceState.getBoolean("isFirst");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonZero: pushDigit('0'); break;
            case R.id.buttonOne: pushDigit('1'); break;
            case R.id.buttonTwo: pushDigit('2'); break;
            case R.id.buttonThree: pushDigit('3'); break;
            case R.id.buttonFour: pushDigit('4'); break;
            case R.id.buttonFive: pushDigit('5'); break;
            case R.id.buttonSix: pushDigit('6'); break;
            case R.id.buttonSeven: pushDigit('7'); break;
            case R.id.buttonEight: pushDigit('8'); break;
            case R.id.buttonNine: pushDigit('9'); break;

            case R.id.buttonAdd: pushOperation('+'); break;
            case R.id.buttonSub: pushOperation('-'); break;
            case R.id.buttonMul: pushOperation('*'); break;
            case R.id.buttonDiv: pushOperation('/'); break;

            case R.id.buttonSign: changeSign(); break;
            case R.id.buttonAC: pushAC(); break;
            case R.id.buttonRNG: pushRNG(); break;
            case R.id.buttonEqual: pushEqual(); break;
            case R.id.buttonPoint: pushPoint(); break;



        }
    }

    private void pushDigit(char digit) {
        if (stringNumber.length() == 1 && stringNumber.charAt(0) == '0') {
            if (digit == '0') {
                return;
            }
            stringNumber.setLength(0);

        }
        stringNumber.append(digit);
        toScreen(stringNumber.toString());
    }

    private void pushOperation(char oper) {
        if (isFirst) {
            number = Double.parseDouble(stringNumber.toString());
            isFirst = false;
            stringNumber.setLength(0);
            stringNumber.append('0');
        }

        if (operation != 0) {
            getButton(operation).setBackgroundColor(new ResourcesCompat().getColor(getResources(), R.color.orange, null));
        }
        getButton(oper).setBackgroundColor(Color.GREEN);

        operation = oper;
//        toScreen(String.valueOf(number));
    }

    private void pushPoint() {
        if (!stringNumber.toString().contains(".")) {
            stringNumber.append('.');
        }
        toScreen(stringNumber.toString());
    }

    private void changeSign() {
        if (stringNumber.charAt(0) == '-') {
            stringNumber.delete(0, 1);
        } else {
            stringNumber.insert(0, "-");
        }
        toScreen(stringNumber.toString());
    }

    private void pushAC() {
        number = 0;
        clearStringNumber();
        isFirst = true;
        getButton(operation).setBackgroundColor(new ResourcesCompat().getColor(getResources(), R.color.orange, null));
        operation = 0;
        toScreen(stringNumber.toString());
    }

    private void pushRNG() {
        stringNumber = new StringBuilder(Integer.toString(new Random().nextInt(100)));
        toScreen(stringNumber.toString());
    }

    private void pushEqual() {
        getButton(operation).setBackgroundColor(new ResourcesCompat().getColor(getResources(), R.color.orange, null));
        switch (operation) {
            case '+':
                number += Double.parseDouble(stringNumber.toString());
                clearStringNumber();
                break;
            case '-':
                number -= Double.parseDouble(stringNumber.toString());
                clearStringNumber();
                break;
            case '*':
                number *= Double.parseDouble(stringNumber.toString());
                clearStringNumber();
                break;
            case '/':
                number /= Double.parseDouble(stringNumber.toString());
                clearStringNumber();
                break;
        }
        toScreen(String.valueOf(number));
    }

    private void clearStringNumber() {
        stringNumber.setLength(0);
        stringNumber.append('0');
    }

    private Button getButton(char ch) {
            switch (ch) {
                case '+': return (Button)findViewById(R.id.buttonAdd);
                case '-': return (Button)findViewById(R.id.buttonSub);
                case '*': return (Button)findViewById(R.id.buttonMul);
                default: return (Button)findViewById(R.id.buttonDiv);
            }
    }

}
