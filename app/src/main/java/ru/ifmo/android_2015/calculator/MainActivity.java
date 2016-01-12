package ru.ifmo.android_2015.calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private Button sumButton;
    private Button subtractButton;
    private Button multiplyButton;
    private Button divideButton;
    private Button equalButton;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        sumButton = (Button)findViewById(R.id.button_sum);
        subtractButton = (Button)findViewById(R.id.button_subtract);
        multiplyButton = (Button)findViewById(R.id.button_multiply);
        divideButton = (Button)findViewById(R.id.button_divide);
        equalButton = (Button)findViewById(R.id.button_equal);
        clearButton = (Button)findViewById(R.id.button_clear);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State.setCode(OpCode.SUM);
                saveValue();
            }
        });
        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State.setCode(OpCode.SUBTRACT);
                saveValue();
            }
        });
        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State.setCode(OpCode.MULTIPLY);
                saveValue();
            }
        });
        divideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State.setCode(OpCode.DIVIDE);
                saveValue();
            }
        });

        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (State.getCode() != null && State.getValue() != null) {
                    Double value = getValue();
                    if (value == null) return;
                    calculate(State.getCode(), value);
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

    /* Private methods */

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
            editText.setText(State.getValue().toString());
            State.setCode(null);
        }

    }

    private void calculate(OpCode code, @NonNull Double value) {
        Double state = State.getValue();
        if (state == null) {
            return;
        }
        try {
            double result = Operation.create(code).execute(state, value);
            State.setValue(result);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                Toast.makeText(context, R.string.divide_by_zero, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
