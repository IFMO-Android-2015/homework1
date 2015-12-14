package com.example.pav.homework1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pav.homework1.save.Container;
import com.example.pav.homework1.save.SaveFragment;

import java.util.ArrayDeque;
import java.util.Queue;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView stack, active, memory_indicator;
    Button mc, mr, ms, m_plus, m_minus;
    Button back, ce, c, plus_minus, sqrt;
    Button seven, eight, nine, divide, mod;
    Button four, five, six, multiply, x_inverse;
    Button one, two, three, subtract, equal;
    Button zero, triple_zero, dot, add;

    private SaveFragment saveFragment;
    private Container container;
    private float memory;
    private boolean dot_control;
    private String stack_info;
    private Queue<Float> numbers;
    private Queue<Character> operations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stack = (TextView) findViewById(R.id.stack_text);
        active = (TextView) findViewById(R.id.active_text);
        memory_indicator = (TextView) findViewById(R.id.memory_indicator);
        memory_indicator.setVisibility(View.INVISIBLE);

        mc = (Button) findViewById(R.id.mc_button);
        mr = (Button) findViewById(R.id.mr_button);
        ms = (Button) findViewById(R.id.ms_button);
        m_plus = (Button) findViewById(R.id.m_plus_button);
        m_minus = (Button) findViewById(R.id.m_minus_button);

        back = (Button) findViewById(R.id.back_button);
        ce = (Button) findViewById(R.id.ce_button);
        c = (Button) findViewById(R.id.c_button);
        plus_minus = (Button) findViewById(R.id.plus_minus_button);
        sqrt = (Button) findViewById(R.id.sqrt_button);

        seven = (Button) findViewById(R.id.seven_button);
        eight = (Button) findViewById(R.id.eight_button);
        nine = (Button) findViewById(R.id.nine_button);
        divide = (Button) findViewById(R.id.divide_button);
        mod = (Button) findViewById(R.id.mod_button);

        four = (Button) findViewById(R.id.four_button);
        five = (Button) findViewById(R.id.five_button);
        six = (Button) findViewById(R.id.six_button);
        multiply = (Button) findViewById(R.id.multiply_button);
        x_inverse = (Button) findViewById(R.id.x_inverse_button);

        one = (Button) findViewById(R.id.one_button);
        two = (Button) findViewById(R.id.two_button);
        three = (Button) findViewById(R.id.three_button);
        subtract = (Button) findViewById(R.id.subtract_button);
        equal = (Button) findViewById(R.id.equal_button);

        zero = (Button) findViewById(R.id.zero_button);
        triple_zero = (Button) findViewById(R.id.triple_zero_button);
        dot = (Button) findViewById(R.id.dot_button);
        add = (Button) findViewById(R.id.add_button);
        /**
         * setOnClickListener
         */
        mc.setOnClickListener(this);
        mr.setOnClickListener(this);
        ms.setOnClickListener(this);
        m_plus.setOnClickListener(this);
        m_minus.setOnClickListener(this);

        back.setOnClickListener(this);
        ce.setOnClickListener(this);
        c.setOnClickListener(this);
        plus_minus.setOnClickListener(this);
        sqrt.setOnClickListener(this);

        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        divide.setOnClickListener(this);
        mod.setOnClickListener(this);

        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        multiply.setOnClickListener(this);
        x_inverse.setOnClickListener(this);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        subtract.setOnClickListener(this);
        equal.setOnClickListener(this);

        zero.setOnClickListener(this);
        triple_zero.setOnClickListener(this);
        dot.setOnClickListener(this);
        add.setOnClickListener(this);

        numbers = new ArrayDeque<>();
        operations = new ArrayDeque<>();
        numbers.clear();
        operations.clear();

        /**
         * Востанавливаем состояние после поворота экрана.
         */
        saveFragment = (SaveFragment) getFragmentManager().findFragmentByTag("SAVE_FRAGMENT");
        if (saveFragment != null) {
            container = saveFragment.getModel();
            memory = container.memory;
            dot_control = container.dot_control;
            stack_info = container.stack_info;
            numbers = container.numbers;
            operations = container.operations;
            memory_visible();
        } else {
            saveFragment = new SaveFragment();
            getFragmentManager().beginTransaction().add(saveFragment, "SAVE_FRAGMENT")
                    .commit();
            memory = 0;
            dot_control = true;
            stack_info = "";
        }
    }

    /**
     * Сохраняем состояние экрана при разрушении активити.
     */
    @Override
    protected void onPause() {
        String active_text = active.getText().toString();
        String stack_text = stack.getText().toString();
        container = new Container(memory, dot_control, stack_info, numbers, operations,
                active_text, stack_text);
        saveFragment.setModel(container);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mc_button:
                memory = 0;
                memory_visible();
                break;
            case R.id.mr_button:
                setText(memory);
                memory_visible();
                break;
            case R.id.ms_button:
                String gt = getText(active);
                if (!gt.isEmpty())
                    memory = Float.parseFloat(gt);
                memory_visible();
                break;
            case R.id.m_plus_button:
                if (!active.getText().toString().isEmpty())
                    memory += Float.parseFloat(getText(active));
                memory_visible();
                break;
            case R.id.m_minus_button:
                if (!active.getText().toString().isEmpty())
                    memory -= Float.parseFloat(getText(active));
                memory_visible();
                break;

            case R.id.back_button:
                backspace();
                break;
            case R.id.ce_button:
                active.setText("");
                break;
            case R.id.c_button:
                active.setText("");
                stack.setText("");
                break;
            case R.id.plus_minus_button:
                setText(-Float.parseFloat(getText(active)));
                break;
            case R.id.sqrt_button:
                unar_op('s');
                break;

            case R.id.seven_button:
                add_to_active('7');
                break;
            case R.id.eight_button:
                add_to_active('8');
                break;
            case R.id.nine_button:
                add_to_active('9');
                break;
            case R.id.divide_button:
                operations('/');
                break;
            case R.id.mod_button:
                operations('%');
                break;

            case R.id.four_button:
                add_to_active('4');
                break;
            case R.id.five_button:
                add_to_active('5');
                break;
            case R.id.six_button:
                add_to_active('6');
                break;
            case R.id.multiply_button:
                operations('*');
                break;
            case R.id.x_inverse_button:
                unar_op('x');
                break;

            case R.id.one_button:
                add_to_active('1');
                break;
            case R.id.two_button:
                add_to_active('2');
                break;
            case R.id.three_button:
                add_to_active('3');
                break;
            case R.id.subtract_button:
                operations('-');
                break;
            case R.id.equal_button:
                calculation();
                break;

            case R.id.zero_button:
                add_to_active('0');
                break;
            case R.id.triple_zero_button:
                add_to_active('0');
                add_to_active('0');
                add_to_active('0');
                break;
            case R.id.dot_button:
                if (dot_control) {
                    dot_control = false;
                    add_to_active('.');
                }
                break;
            case R.id.add_button:
                operations('+');
                break;
        }
    }

    public void add_to_active(char symbol) {
        String active_text = active.getText().toString();
        if (active_text.equals("Infinity"))
            active_text = "";
        if (active_text.isEmpty() && symbol != '0') {
            if (symbol == '.')
                active.setText("0.");
            else
                active.setText(symbol + "");
        } else
            active.setText(active_text + symbol);
    }

    public void add_to_stack(String active_text, char op) {
        dot_control = true;
        String stack_text = stack.getText().toString();
        if (!active_text.isEmpty())
            stack.setText(stack_text + " " + active_text + " " + op);
        else {
            int length = stack_text.length();
            if (length > 0)
                stack.setText(stack_text.substring(0, length - 1) + op);
        }
    }

    public void backspace() {
        String active_text;
        active_text = active.getText().toString();
        int length = active_text.length();
        if (length > 0) {
            String last = active_text.substring(length - 1, length);
            String sub_str = active_text.substring(0, length - 1);
            if (last.equals("."))
                dot_control = true;
            if (last.equals("y")) {
                sub_str = "";
                dot_control = true;
            }
            if (sub_str.equals("0"))
                active.setText("");
            else
                active.setText(sub_str);
        }
    }

    public void memory_visible() {
        memory_indicator.setVisibility(View.VISIBLE);
        if (memory == 0)
            memory_indicator.setVisibility(View.INVISIBLE);
    }

    public void setText(Float text) {
        if (text == 0) {
            active.setText("");
            dot_control = true;
            return;
        }
        if (Math.abs(text - text.intValue()) > 0) {
            active.setText(text.toString());
            dot_control = false;
        } else {
            active.setText(text.intValue() + "");
            dot_control = true;
        }
    }

    public String getText(TextView vault) {
        String buf = vault.getText().toString();
        vault.setText("");
        int length = buf.length();
        if (length > 1) {
            if (buf.substring(length - 1, length).equals("."))
                return buf.substring(0, length - 1);
            else
                return buf;
        } else if (buf.equals(".") || buf.isEmpty())
            return 0 + "";
        else
            return buf;
    }

    public void unar_op(char op) {
        float num = Float.parseFloat(getText(active));
        if (op == 's') {
            if (stack_info.isEmpty())
                stack_info = "sqrt(" + num + ")";
            else
                stack_info = "sqrt(" + stack_info + ")";
            num = (float) Math.sqrt(num);
        }
        if (op == 'x') {
            if (stack_info.isEmpty())
                stack_info = "1/" + num;
            else
                stack_info = "1/" + stack_info;
            num = 1 / num;
        }
        setText(num);
    }

    public void operations(char op) {
        String num = getText(active);
        if (num.equals(""))
            num = "0";
        numbers.add(Float.parseFloat(num));
        operations.add(op);
        if (!stack_info.isEmpty()) {
            num = stack_info;
            stack_info = "";
        }
        add_to_stack(num, op);
    }

    public void calculation() {
        operations('=');
        stack.setText("");
        float result = numbers.remove();
        while (!numbers.isEmpty()) {
            float next_num = numbers.remove();
            Log.d("div", result + " " + next_num);
            switch (operations.remove()) {
                case '+':
                    result += next_num;
                    break;
                case '-':
                    result -= next_num;
                    break;
                case '*':
                    result *= next_num;
                    break;
                case '/':
                    result /= next_num;
                    break;
                case '%':
                    result %= next_num;
                    break;
            }
        }
        setText(result);
        numbers.clear();
        operations.clear();
    }
}