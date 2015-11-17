package com.vantuz.calculator;

import android.view.View;

public class OperationListener implements View.OnClickListener {
    private final Operation operation;
    private final MainActivity ma;

    public OperationListener(MainActivity ma, Operation operation) {
        this.operation = operation;
        this.ma = ma;
    }

    @Override
    public void onClick(View v) {
        ma.doCalc();
        MainActivity.op = operation;
    }
}
