package heat_wave.calculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class CalculatorActivity extends AppCompatActivity {

    private String temp;
    private TextView inputField;
    private Operation operation;

    public static final String INPUT_FIELD = "inputField";
    public static final String TEMP = "temp";
    public static final String OPERATION = "operation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        inputField = (TextView)findViewById(R.id.input);
        inputField.setText("0");
        operation = Operation.NOP;
        temp = "0";
    }

    @Override
    protected void onSaveInstanceState(Bundle savingState) {
        savingState.putString(INPUT_FIELD, inputField.getText().toString());
        savingState.putString(TEMP, temp);
        savingState.putSerializable(OPERATION, operation);
        super.onSaveInstanceState(savingState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            inputField.setText(savedInstanceState.getString(INPUT_FIELD));
            temp = savedInstanceState.getString(TEMP);
            operation = (Operation)savedInstanceState.getSerializable(OPERATION);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonClick(View btn) {
        char c = ((Button)btn).getText().charAt(0);
        if (c == 'C') {
            inputField.setText("0");
        }
        if (Character.isDigit(c)) {
            if (inputField.getText().toString().equals("0"))
                inputField.setText("" + c);
            else
                inputField.setText(inputField.getText().toString() + c);
        }
        else if (c == '.'){
            String number = inputField.getText().toString();
            if (number.length() == 0)
                number = "0";
            if (number.indexOf('.') != -1)
                return;
            number += '.';
            inputField.setText(number);
        }
        else if (inputField.getText().length() > 0) {
            if (operation != Operation.NOP)
                calculate();
            switch (c) {
                case '+':
                    operation = Operation.PLUS;
                    temp = inputField.getText().toString();
                    inputField.setText("");
                    break;
                case '\u2212':
                    operation = Operation.MINUS;
                    temp = inputField.getText().toString();
                    inputField.setText("");
                    break;
                case 'x':
                    operation = Operation.MULTIPLY;
                    temp = inputField.getText().toString();
                    inputField.setText("");
                    break;
                case '/':
                    operation = Operation.DIVIDE;
                    temp = inputField.getText().toString();
                    inputField.setText("");
                    break;
                case '=':
                    operation = Operation.NOP;
                    break;
            }
        }
        else {
            switch (c) {
                case '+':
                    operation = Operation.PLUS;
                    break;
                case '\u2212':
                    operation = Operation.MINUS;
                    break;
                case 'x':
                    operation = Operation.MULTIPLY;
                    break;
                case '/':
                    operation = Operation.DIVIDE;
                    break;
                case '=':
                    operation = Operation.NOP;
                    inputField.setText(temp);
                    break;
            }
        }
    }

    private void calculate() {
        double res = 0;
        switch (operation) {
            case PLUS:
                res = Double.parseDouble(inputField.getText().toString()) +
                        Double.parseDouble(temp);
                break;
            case MINUS:
                res = Double.parseDouble(temp) -
                        Double.parseDouble(inputField.getText().toString());

                break;
            case MULTIPLY:
                res = Double.parseDouble(inputField.getText().toString()) *
                        Double.parseDouble(temp);
                break;
            case DIVIDE:
                res = Double.parseDouble(temp) /
                        Double.parseDouble(inputField.getText().toString());
                break;
            default:
                operation = Operation.NOP;
                break;
        }
        if (res % 1 == 0) {
            inputField.setText(String.format("%8d", (long) res));
        } else {
            inputField.setText(String.format("%8.3f", res));
        }
    }

    private enum Operation{PLUS, MINUS, MULTIPLY, DIVIDE, NOP}
}
