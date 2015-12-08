package com.example.kirill.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Operation {
        NOTHING,
        MULTIPLICATION,
        ADDITION,
        DIVISION,
        SUBTRACTION
    }

    private EditText editText;
    private double first = 0;
    private double second = 0;
    private Operation curOperation = Operation.NOTHING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        editText.setEnabled(false);
    }

    private double toDouble() {
        String numb = editText.getText().toString();
        double operand = 0;
        if(numb.length() > 0) {
            operand = Double.parseDouble(editText.getText().toString());
        }
        editText.setText("");
        return operand;
    }

    // worst programming below
    private void add() {
        if(curOperation == Operation.NOTHING) {
            try {
                first = toDouble();
                curOperation = Operation.ADDITION;
            } catch (Throwable exp) {
                first = 0;
                curOperation = Operation.NOTHING;
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Wrong number!", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else if(curOperation != Operation.ADDITION) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Addition now", Toast.LENGTH_SHORT);
            toast.show();
            curOperation = Operation.ADDITION;
        }
    }

    private void subtract() {
        if(curOperation == Operation.NOTHING) {
            try {
                first = toDouble();
                curOperation = Operation.SUBTRACTION;
            } catch (Throwable exp) {
                first = 0;
                curOperation = Operation.NOTHING;
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Wrong number!", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else if(curOperation != Operation.SUBTRACTION) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Subtraction now", Toast.LENGTH_SHORT);
            toast.show();
            curOperation = Operation.SUBTRACTION;
        }

    }

    private void multiply() {
        if(curOperation == Operation.NOTHING) {
            try {
                first = toDouble();
                curOperation = Operation.MULTIPLICATION;
            } catch (Throwable exp) {
                first = 0;
                curOperation = Operation.NOTHING;
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Wrong number!", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else if(curOperation != Operation.MULTIPLICATION) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Multiplication now", Toast.LENGTH_SHORT);
            toast.show();
            curOperation = Operation.MULTIPLICATION;
        }
    }

    private void divide() {
        if(curOperation == Operation.NOTHING) {
            try {
                first = toDouble();
                curOperation = Operation.DIVISION;
            } catch (Throwable exp) {
                first = 0;
                curOperation = Operation.NOTHING;
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Wrong number!", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else if(curOperation != Operation.DIVISION) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Division now", Toast.LENGTH_SHORT);
            toast.show();
            curOperation = Operation.DIVISION;
        }
    }

    private void calculation() {
        if(curOperation != Operation.NOTHING) {
            try {
                second = toDouble();
            } catch (Error exp) {
                second = 0;
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Wrong number!", Toast.LENGTH_SHORT);
                toast.show();
            }

            if (curOperation == Operation.ADDITION) {
                first += second;
            } else if (curOperation == Operation.SUBTRACTION) {
                first -= second;
            } else if (curOperation == Operation.MULTIPLICATION) {
                first *= second;
            } else if (curOperation == Operation.DIVISION) {
                if (second == 0.0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Division by zero", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    first /= second;
                }
            }

            editText.setText(String.valueOf(first));
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Nothing happened", Toast.LENGTH_SHORT);
            toast.show();
        }

        curOperation = Operation.NOTHING;
    }

    private void deleting() {
        String cur = editText.getText().toString();
        if(cur.length() != 0) {
            String next = cur.substring(0, cur.length() - 1);
            editText.setText(next);
        }
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.button0: editText.append("0"); break;
            case R.id.button1: editText.append("1"); break;
            case R.id.button2: editText.append("2"); break;
            case R.id.button3: editText.append("3"); break;
            case R.id.button4: editText.append("4"); break;
            case R.id.button5: editText.append("5"); break;
            case R.id.button6: editText.append("6"); break;
            case R.id.button7: editText.append("7"); break;
            case R.id.button8: editText.append("8"); break;
            case R.id.button9: editText.append("9"); break;
            case R.id.button10: add(); break;
            case R.id.button11: subtract(); break;
            case R.id.button12: multiply(); break;
            case R.id.button13: divide(); break;
            case R.id.button14: calculation(); break;
            case R.id.button15: deleting(); break;
        }
    }
}
