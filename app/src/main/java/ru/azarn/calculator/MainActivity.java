package ru.azarn.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;


public class MainActivity extends Activity {
    private static final String TAG = "MAIN_ACTIVITY";

    private TextView tvResult;
    private GestureDetector gs;
    private Calculator calc;
    private StringBuilder buf;
    enum STATE_ENUM {
        NONE, ONLY_DIGIT, NEED_RESET, ERROR
    }

    private STATE_ENUM CurrentState;
    private char Operation;

    void setResult() {
        if (CurrentState == STATE_ENUM.ERROR)
            tvResult.setText("ОЧЕНЬ ЖАЛЬ");
        else
            tvResult.setText(buf.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tvResult);
        gs = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffY) > SWIPE_THRESHOLD || Math.abs(diffX) > SWIPE_THRESHOLD) {
                    CurrentState = STATE_ENUM.NONE;
                    Operation = '\0';
                    buf = new StringBuilder("0");
                    calc.clear();
                    setResult();
                }
                return true;
            }
        });

        tvResult.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gs.onTouchEvent(event);
            }
        });

        if (savedInstanceState == null) {
            buf = new StringBuilder("0");
            calc = new Calculator();
            CurrentState = STATE_ENUM.NONE;
            Operation = '\0';
        } else {
            buf = new StringBuilder(savedInstanceState.getString("BUF"));
            calc = (Calculator)savedInstanceState.getSerializable("CALC");
            CurrentState = (STATE_ENUM)savedInstanceState.getSerializable("CURRENT_STATE");
            Operation = savedInstanceState.getChar("OPERATION");
        }

        setResult();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putChar("OPERATION", Operation);
        outState.putString("BUF", buf.toString());
        outState.putSerializable("CURRENT_STATE", CurrentState);
        outState.putSerializable("CALC", calc);
    }

    public void ButtonClicked(View v) {
        Button btn = (Button) v;
        char btn_type = btn.getText().charAt(0);

        if (CurrentState == STATE_ENUM.ERROR) {
            return;
        } else if (btn_type == '.' && !buf.toString().contains(".")) {
            CurrentState = STATE_ENUM.ONLY_DIGIT;
            buf.append(".");
        } else if (Character.isDigit(btn_type)) {
            if (CurrentState == STATE_ENUM.ONLY_DIGIT)
                CurrentState = STATE_ENUM.NONE;
            else if (CurrentState == STATE_ENUM.NEED_RESET || buf.toString().equals("0")) {
                CurrentState = STATE_ENUM.NONE;
                buf = new StringBuilder();
            }
            buf.append(btn_type);
        } else if (CurrentState != STATE_ENUM.ONLY_DIGIT) {
            BigDecimal arg = new BigDecimal(buf.toString());
            if (Operation != '\0' && CurrentState != STATE_ENUM.NEED_RESET) {
                switch (Operation) {
                    case '+':
                        calc.add(arg);
                        break;
                    case '-':
                        calc.subtract(arg);
                        break;
                    case '*':
                        calc.multiply(arg);
                        break;
                    case '/':
                        if (arg.compareTo(BigDecimal.ZERO) == 0) {
                            CurrentState = STATE_ENUM.ERROR;
                            setResult();                // OPPA GOVNOKOD
                            return;
                        } else {
                            calc.divide(arg);
                        }
                        break;
                }
                buf = new StringBuilder(calc.get_result().toPlainString());
            } else {
                calc.set(arg);
            }

            if (btn_type != '=') {
                Operation = btn_type;
            } else {
                calc.clear();
                Operation = '\0';
            }
            CurrentState = STATE_ENUM.NEED_RESET;
        }

        setResult();
    }

    public boolean onTouchEvent(MotionEvent me){
        gs.onTouchEvent(me);
        return true;
    }
}
