package ru.ifmo.android_2015.activitylifecycle.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import ru.ifmo.android_2015.activitylifecycle.R;

public final class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    //private static final int MAX_CLICK_COUNT = 3;
    private static final String SAVED_EXPRESSION = "expr";

    private TextView resTextView;
    private boolean wasSignBtnPressed;
    //private View loginButton;

    //int clickCount = 0;

    Expression expr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_main);
        resTextView = (TextView)findViewById(R.id.text_view);
        resTextView.setText("0");
        expr = new Expression();
        wasSignBtnPressed = true;
        /*
        error = (TextView)findViewById(R.id.error);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;
                updateUI();
            }
        });

        if (savedInstanceState != null) {
            clickCount = savedInstanceState.getInt(EXTRA_CLICK_COUNT);
            updateUI();
        }
        */
    }

    private void updateUI() {
        /*
        boolean disabled = clickCount >= MAX_CLICK_COUNT;
        if (disabled) {
            error.setVisibility(View.VISIBLE);
            error.setText(MAX_CLICK_COUNT + " reached");
        }
        else {
            error.setVisibility(View.INVISIBLE);
        }

        loginButton.setEnabled(!disabled);
        */
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState");

        //outState.putInt(EXTRA_CLICK_COUNT, clickCount);
        //outState.putInt("SIGN", expr.currentSign);
    }

    public void onDigitBtnClick(View view) {
        char newDigit = '0';

        switch (view.getId()) {
            case R.id.digit_btn_0:
                newDigit = '0';
                break;
            case R.id.digit_btn_1:
                newDigit = '1';
                break;
            case R.id.digit_btn_2:
                newDigit = '2';
                break;
            case R.id.digit_btn_3:
                newDigit = '3';
                break;
            case R.id.digit_btn_4:
                newDigit = '4';
                break;
            case R.id.digit_btn_5:
                newDigit = '5';
                break;
            case R.id.digit_btn_6:
                newDigit = '6';
                break;
            case R.id.digit_btn_7:
                newDigit = '7';
                break;
            case R.id.digit_btn_8:
                newDigit = '8';
                break;
            case R.id.digit_btn_9:
                newDigit = '9';
                break;
        }

        if (wasSignBtnPressed) resTextView.setText("" + newDigit);
        else resTextView.setText(resTextView.getText().toString() + newDigit);

        wasSignBtnPressed = false;
        expr.addDigit(newDigit);

        updateUI();
    }

    public void onSignBtnClick(View view) {

        switch (view.getId()) {
            case R.id.sign_btn_div:
                expr.addSign(Expression.Sign.DIV);
                break;
            case R.id.sign_btn_mul:
                expr.addSign(Expression.Sign.MUL);
                break;
            case R.id.sign_btn_plus:
                expr.addSign(Expression.Sign.PLUS);
                break;
            case R.id.sign_btn_sub:
                expr.addSign(Expression.Sign.SUB);
                break;
        }

        wasSignBtnPressed = true;
    }

    public void onCancelBtnClick(View view) {
        resTextView.setText("0");
        wasSignBtnPressed = true;
        expr.reset();
    }

    public void onEqualBtnClick(View view) {
        resTextView.setText("Hello");
        wasSignBtnPressed = true;
    }
}
