package ru.nobird.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView result;
    private Container data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        findViewById(R.id.d0).setOnClickListener(this);
        findViewById(R.id.d1).setOnClickListener(this);
        findViewById(R.id.d2).setOnClickListener(this);
        findViewById(R.id.d3).setOnClickListener(this);
        findViewById(R.id.d4).setOnClickListener(this);
        findViewById(R.id.d5).setOnClickListener(this);
        findViewById(R.id.d6).setOnClickListener(this);
        findViewById(R.id.d7).setOnClickListener(this);
        findViewById(R.id.d8).setOnClickListener(this);
        findViewById(R.id.d9).setOnClickListener(this);
        findViewById(R.id.div).setOnClickListener(this);
        findViewById(R.id.mul).setOnClickListener(this);
        findViewById(R.id.sub).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.eqv).setOnClickListener(this);
        findViewById(R.id.clear).setOnClickListener(this);

        result = (TextView) findViewById(R.id.result);
        if (savedInstanceState != null) {
            data = (Container) onRetainCustomNonConfigurationInstance();
        }

        if (data == null) data = new Container();

        update();
    }

    public Object getLastCustomNonConfigurationInstance() {
        return data;
    }

    private class Container {
        private double a = 0, b = 0;
        private boolean isFirst = true;
        private int operation;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.d0: add(0); break;
            case R.id.d1: add(1); break;
            case R.id.d2: add(2); break;
            case R.id.d3: add(3); break;
            case R.id.d4: add(4); break;
            case R.id.d5: add(5); break;
            case R.id.d6: add(6); break;
            case R.id.d7: add(7); break;
            case R.id.d8: add(8); break;
            case R.id.d9: add(9); break;
            case R.id.clear: clear(); break;

            case R.id.eqv: equality(); break;
            default: pre(v.getId()); break;

        }

        update();
    }

    private void update() {
        result.setText(Double.toString(data.isFirst ? data.a : data.b));
    }

    private void equality() {
        if (data.isFirst) return;

        data.a = operation(data.a, data.b, data.operation);
        data.isFirst = true;
        data.b = 0;

    }

    private void pre (int operation) {
        equality();
        data.isFirst = false;
        switch (operation) {
            case R.id.add: data.operation = 0; break;
            case R.id.sub: data.operation = 1; break;
            case R.id.mul: data.operation = 2; break;
            case R.id.div: data.operation = 3; break;
        }
    }

    private double operation(double a, double b, int type) {
        switch (type) {
            case 0: return a + b;
            case 1: return a - b;
            case 2: return a * b;
            case 3: return a / b;
        }

        return a;
    }

    private void clear() {
        data.isFirst = true;
        data.a = 0;
        data.b = 0;
    }
    private void add(final int i) {
        if (data.isFirst) {
            data.a *= 10;
            data.a += i;
        } else {
            data.b *= 10;
            data.b += i;
        }
    }

}
