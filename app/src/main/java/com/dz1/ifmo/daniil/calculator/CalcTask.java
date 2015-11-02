package com.dz1.ifmo.daniil.calculator;

import android.os.AsyncTask;

import expression.CheckedParser;
import expression.types.CheckedBigDecimal;

/**
 * Created by daniil on 29.10.15.
 */
public class CalcTask extends AsyncTask<String, Void, String> {
    private MainActivity activity;

    void attachActivity(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            if (params[0].length() != 0) {
                return new CheckedParser<>(new CheckedBigDecimal("0")).parse(params[0]).evaluate().toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            return "Error";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (activity.writeInMem == true) {
            activity.memory = result;
        } else {
            activity.expression.setText(result);
            if (result.equals("Error")) {
                activity.flag = 2;
            } else {
                activity.flag = 1;
            }
        }
        activity.finishCalc = true;
    }
}
