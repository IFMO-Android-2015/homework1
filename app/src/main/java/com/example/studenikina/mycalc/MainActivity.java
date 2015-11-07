package com.example.studenikina.mycalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import java.lang.String;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etNum1;

    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;
    Button btnComm;
    Button btnAC;
    Button btnEq;
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;

    TextView tvResult;

    String oper = "";
    String st1 = "";

    float num1 = 0;
    float num2 = 0;
    float result = 0;

    boolean old = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // находим элементы ?
        etNum1 = (EditText) findViewById(R.id.etNum1);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnComm = (Button) findViewById(R.id.btnComm);
        btnEq = (Button) findViewById(R.id.btnEq);
        btnAC = (Button) findViewById(R.id.btnAC);

        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);


        tvResult = (TextView) findViewById(R.id.tvResult);

        // прописываем обработчик
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener((View.OnClickListener)this);
        btnMult.setOnClickListener((View.OnClickListener) this);
        btnDiv.setOnClickListener((View.OnClickListener) this);

        btnComm.setOnClickListener((View.OnClickListener) this);
        btnEq.setOnClickListener((View.OnClickListener) this);
        btnAC.setOnClickListener((View.OnClickListener) this);

        btn0.setOnClickListener((View.OnClickListener) this);
        btn1.setOnClickListener((View.OnClickListener) this);
        btn2.setOnClickListener((View.OnClickListener) this);
        btn3.setOnClickListener((View.OnClickListener) this);
        btn4.setOnClickListener((View.OnClickListener) this);
        btn5.setOnClickListener((View.OnClickListener) this);
        btn6.setOnClickListener((View.OnClickListener) this);
        btn7.setOnClickListener((View.OnClickListener) this);
        btn8.setOnClickListener((View.OnClickListener) this);
        btn9.setOnClickListener((View.OnClickListener) this);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("number1", num1);
        outState.putFloat("number2", num2);
        outState.putFloat("result", result);
        outState.putString("operation", oper);
        outState.putString("string", st1);
        outState.putBoolean("age", old);
        outState.putString("text", (String) tvResult.getText());
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        num1 = savedInstanceState.getFloat("number1");
        num2 = savedInstanceState.getFloat("number2");
        result = savedInstanceState.getFloat("result");
        oper = savedInstanceState.getString("operation");
        st1 = savedInstanceState.getString("string");
        old = savedInstanceState.getBoolean("age");
        tvResult.setText(savedInstanceState.getString("text"));
    }

    public void onClick(View v) {

        // определяем нажатую кнопку и выполняем соответствующую операцию
        // в oper пишем операцию, потом будем использовать в выводе
        switch (v.getId()) {
            case R.id.btn0:st1+="0";
                etNum1.setText(st1);
                break;
            case R.id.btn1:st1+="1";
                etNum1.setText(st1);
                break;
            case R.id.btn2:st1+="2";
                etNum1.setText(st1);
                break;
            case R.id.btn3:st1+="3";
                etNum1.setText(st1);
                break;
            case R.id.btn4:st1+="4";
                etNum1.setText(st1);
                break;
            case R.id.btn5:st1+="5";
                etNum1.setText(st1);
                break;
            case R.id.btn6:st1+="6";
                etNum1.setText(st1);
                break;
            case R.id.btn7:st1+="7";
                etNum1.setText(st1);
                break;
            case R.id.btn8:st1+="8";
                etNum1.setText(st1);
                break;
            case R.id.btn9:st1+="9";
                etNum1.setText(st1);
                break;
            // ----
            case R.id.btnComm:st1+=".";
                etNum1.setText(st1);
                break;
            case R.id.btnAdd:
                oper = "+";
                if (TextUtils.isEmpty(st1)){
                    tvResult.setText("Error. try again");
                    return;
                }
                num1 =  Float.parseFloat(etNum1.getText().toString());
                etNum1.setText("");
                st1 = "";
                break;
            case R.id.btnSub:
                oper = "-";
                if (TextUtils.isEmpty(st1)) {
                    tvResult.setText("Error. try again");
                    return;
                }
                num1 =  Float.parseFloat(etNum1.getText().toString());
                etNum1.setText("");
                st1 = "";
                break;
            case R.id.btnMult:
                oper = "*";
                if (TextUtils.isEmpty(st1)){
                    tvResult.setText("Error. try again");
                    return;
                }
                num1 =  Float.parseFloat(etNum1.getText().toString());
                etNum1.setText("");
                st1 = "";
                break;
            case R.id.btnDiv:
                oper = "/";
                if (TextUtils.isEmpty(st1)){
                    tvResult.setText("Error. try again");
                    return;
                }
                num1 =  Float.parseFloat(etNum1.getText().toString());
                etNum1.setText("");
                st1 = "";
                break;
            //------
            case R.id.btnAC:
                oper = "AC";
                old = false;
                result = 0;
                num1 = num2 = 0;
                st1 = "";
                etNum1.setText("");
                tvResult.setText("");
                break;
            case R.id.btnEq:
                if (TextUtils.isEmpty(st1)) {
                    tvResult.setText("Error. try again");
                    return;
                }
                num2 = Float.parseFloat(st1);
                if(old == true)
                    num1 = result;
                switch (oper) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 != 0) {
                            result = num1  / num2;
                        }
                        else {
                            tvResult.setText("Error. You divide by zero :'(");
                            return;
                        }
                        break;
                    default:
                        break;
                }
                old = true;
                tvResult.setText(num1 + " " + oper + " " + num2 + " = " + result);
                break;
            default:
                break;
        }
    }
}
