package ru.ifmo.android_2015.calculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Button sumButton = (Button)findViewById(R.id.button_sum);
        Button subtractButton = (Button)findViewById(R.id.button_subtract);
        Button multiplyButton = (Button)findViewById(R.id.button_multiply);
        Button divideButton = (Button)findViewById(R.id.button_divide);
        Button equalButton = (Button)findViewById(R.id.button_equal);
        Button clearButton = (Button)findViewById(R.id.button_clear);

        sumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State.setCode(OperationCode.SUM);
                saveValue();
            }
        });
        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State.setCode(OperationCode.SUBTRACT);
                saveValue();
            }
        });
        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State.setCode(OperationCode.MULTIPLY);
                saveValue();
            }
        });
        divideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State.setCode(OperationCode.DIVIDE);
                saveValue();
            }
        });

        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (State.getCode() != null && State.getValue() != null) {
                    Double value = getValue();
                    if (value == null) return;
                    if (State.getCode() == OperationCode.SUM) {
                        Calculator.doSum(value);
                    } else if (State.getCode() == OperationCode.SUBTRACT) {
                        Calculator.doSubtract(value);
                    } else if (State.getCode() == OperationCode.MULTIPLY) {
                        Calculator.doMultiply(value);
                    } else if (State.getCode() == OperationCode.DIVIDE) {
                        if (value == (double) 0) {
                            Toast.makeText(context, R.string.divide_by_zero_message, Toast.LENGTH_SHORT).show();
                        } else {
                            Calculator.doDivide(value);
                        }
                    }
                    updateUI();
                } else {
                    if (State.getCode() == null) {
                        Log.e("Calculator", "code is null");
                    }
                    if (State.getValue() == null) {
                        Log.e("Calculator", "value is null");
                    }
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearValue();
                State.setValue(null);
                State.setCode(null);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        Button sumButton = (Button)findViewById(R.id.button_sum);
        Button subtractButton = (Button)findViewById(R.id.button_subtract);
        Button multiplyButton = (Button)findViewById(R.id.button_multiply);
        Button divideButton = (Button)findViewById(R.id.button_divide);
        Button equalButton = (Button)findViewById(R.id.button_equal);
        Button clearButton = (Button)findViewById(R.id.button_clear);

        sumButton.setOnClickListener(null);
        subtractButton.setOnClickListener(null);
        multiplyButton.setOnClickListener(null);
        divideButton.setOnClickListener(null);
        equalButton.setOnClickListener(null);
        clearButton.setOnClickListener(null);
    }

    private Double getValue() {
        EditText editText = (EditText)findViewById(R.id.editText);
        String value = editText.getText().toString();
        if (value.isEmpty()) {
            return null;
        }
        return Double.valueOf(value);
    }

    private void saveValue() {
        Double value = getValue();
        if (value != null) {
            State.setValue(value);
            clearValue();
        }
    }

    private void clearValue() {
        EditText editText = (EditText)findViewById(R.id.editText);
        editText.setText("");
    }

    private void updateUI() {
        EditText editText = (EditText)findViewById(R.id.editText);
        if (State.getValue() == null) {
            clearValue();
        } else {
            int intValue = State.getValue().intValue();
            double doubleValue = State.getValue().doubleValue();
            if (intValue == doubleValue) {
                editText.setText(String.valueOf(intValue));
            } else {
                editText.setText(State.getValue().toString());
            }
            State.setCode(null);
        }

    }
}
