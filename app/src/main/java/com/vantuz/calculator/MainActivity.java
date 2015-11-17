package com.vantuz.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainActivity extends Activity {
    public static boolean zeroFlag = true;
    public static boolean negativeFlag = false;
    public static boolean dropValueFlag = true;
    public static double acc = 0;
    public static StringBuilder valText = new StringBuilder("0");
    public static Operation op = null;
    public static boolean errorFlag = false;
    public static boolean comaFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListenersForDigits();
        findViewById(R.id.button228).setOnClickListener(new DigitListener(this, "228"));
        findViewById(R.id.button0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dropValueFlag) {
                    clear();
                    dropValueFlag = false;
                } else if (!zeroFlag) {
                    valText.append('0');
                }
                updateValue(true);
            }
        });
        findViewById(R.id.buttonC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op = null;
                clear();
                updateValue(false);
            }
        });
        findViewById(R.id.buttonCE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                updateValue(false);
            }
        });
        findViewById(R.id.buttonPlusMinus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!errorFlag) {
                    if (!zeroFlag) {
                        if (!negativeFlag) {
                            valText.insert(0, "-");
                        } else {
                            valText.deleteCharAt(0);
                        }
                        negativeFlag = !negativeFlag;
                        updateValue(false);
                    }
                }
            }
        });
        findViewById(R.id.buttonEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCalc();
                op = null;
            }
        });
        findViewById(R.id.buttonComa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!comaFlag && !dropValueFlag) {
                    comaFlag = true;
                    zeroFlag = false;
                    valText.append('.');
                    updateValue(true);
                }
            }
        });

        //operations
        findViewById(R.id.buttonPlus).setOnClickListener(new OperationListener(this, new Operation() {
            @Override
            public double calculate(double a, double b) {
                return a + b;
            }
        }));
        findViewById(R.id.buttonMinus).setOnClickListener(new OperationListener(this, new Operation() {
            @Override
            public double calculate(double a, double b) {
                return a - b;
            }
        }));
        findViewById(R.id.buttonMultiply).setOnClickListener(new OperationListener(this, new Operation() {
            @Override
            public double calculate(double a, double b) {
                return a * b;
            }
        }));
        findViewById(R.id.buttonDivide).setOnClickListener(new OperationListener(this, new Operation() {
            @Override
            public double calculate(double a, double b) {
                return a / b;
            }
        }));

        updateValue(true);
    }

    public void updateValue(final boolean dirRight) {
        final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        ((TextView) findViewById(R.id.resultTextView)).setText(valText);
        hsv.post(new Runnable() {

            @Override
            public void run() {
                if (dirRight) {
                    hsv.fullScroll(View.FOCUS_RIGHT);
                } else {
                    hsv.fullScroll(View.FOCUS_LEFT);
                }

            }
        });
    }

    public void processError() {
        errorFlag = true;
        dropValueFlag = true;
        op = null;
        valText.setLength(0);
        valText.append(getString(R.string.error_message));
        updateValue(false);
    }

    private void setListenersForDigits() {
        TableLayout table = (TableLayout) findViewById(R.id.table);
        for (int i = table.getChildCount() - 2; i >= 1; i--) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < row.getChildCount() - 1; j++) {
                int rowNum = 3 - i;
                int columnNum = j;
                int number = rowNum * 3 + columnNum + 1;
                row.getChildAt(j).setOnClickListener(new DigitListener(this, String.valueOf(number)));
            }
        }
    }

    public void processCalculatedValue(double val) {
        long valLonged = Math.round(val);
        valText.setLength(0);
        if (Double.isNaN(val)) {
            processError();
        } else {
            if (val == 0) {
                zeroFlag = true;
                negativeFlag = false;
            } else if (val < 0) {
                negativeFlag = true;
            } else {
                negativeFlag = false;
            }
            if (valLonged == val) {
                valText.append(String.valueOf(valLonged));
            } else {
                DecimalFormatSymbols dfSymbols = new DecimalFormatSymbols();
                dfSymbols.setDecimalSeparator('.');
                dfSymbols.setInfinity(getString(R.string.infinity));
                DecimalFormat df = new DecimalFormat("#.#########", dfSymbols);
                valText.append(df.format(val));
            }
            dropValueFlag = true;
            acc = val;
            updateValue(false);
        }
    }

    public void doCalc() {
        double curValue;
        try {
            String valTextString = valText.toString();
            if (valTextString.contains(getString(R.string.infinity))) {
                curValue = Double.POSITIVE_INFINITY * (negativeFlag ? -1 : 1);
            } else {
                curValue = Double.parseDouble(valTextString);
            }
            if (op != null) {
                curValue = MainActivity.op.calculate(MainActivity.acc, curValue);
            }
            processCalculatedValue(curValue);
        } catch (Exception e) {
            processError();
        }
    }

    public void clear() {
        zeroFlag = true;
        negativeFlag = false;
        dropValueFlag = true;
        errorFlag = false;
        comaFlag = false;
        valText.setLength(0);
        valText.append('0');
    }
}
