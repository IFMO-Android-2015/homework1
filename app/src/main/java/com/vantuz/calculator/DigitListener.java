package com.vantuz.calculator;

import android.view.View;

public class DigitListener implements View.OnClickListener {
    private final String digit;
    private final MainActivity ma;

    public DigitListener(MainActivity ma, String digit) {
        this.digit = digit;
        this.ma = ma;
    }

    @Override
    public void onClick(View v) {
        if (MainActivity.dropValueFlag) {
            ma.clear();
        }
        if (MainActivity.zeroFlag) {
            MainActivity.valText.setLength(0);
        }
        MainActivity.zeroFlag = false;
        MainActivity.dropValueFlag = false;
        MainActivity.valText.append(digit);
        ma.updateValue(true);
    }
}
