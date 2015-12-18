package com.example.user.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("opr", opr);
        outState.putFloat("op1", op1);
        outState.putFloat("op2", op2);
        outState.putString("str", str);
        TextView result = (TextView) findViewById(R.id.textView);
        outState.putString("str2", (String) result.getText());

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        op1 = savedInstanceState.getFloat("op1");
        op2 = savedInstanceState.getFloat("op1");
        opr = savedInstanceState.getInt("opr");
        str = savedInstanceState.getString("str");
        TextView result = (TextView) findViewById(R.id.textView);
        result.setText(savedInstanceState.getString("str2"));

    }

    public float op1 = 0;
    public float op2 = 0;
    public int opr = 0;

    public String str = "0";

    public void onClickdot(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        if (str.indexOf(".") == -1)
            str = str + ".";
        result.setText(str);
    }

    public void onClick1(View view) {
        TextView result = (TextView) findViewById(R.id.textView);

        if (str == "0") str = "";
        str = str + "1";
        result.setText(str);


    }

    public void onClick2(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        if (str == "0") str = "";
        str = str + "2";
        result.setText(str);


    }

    public void onClick3(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        if (str == "0") str = "";
        str = str + "3";
        result.setText(str);


    }

    public void onClick4(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        if (str == "0") str = "";
        str = str + "4";
        result.setText(str);


    }

    public void onClick5(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        if (str == "0") str = "";
        str = str + "5";
        result.setText(str);


    }

    public void onClick6(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        if (str == "0") str = "";
        str = str + "6";
        result.setText(str);


    }

    public void onClick7(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        if (str == "0") str = "";
        str = str + "7";
        result.setText(str);


    }

    public void onClick8(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        if (str == "0") str = "";
        str = str + "8";
        result.setText(str);


    }

    public void onClick9(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        if (str == "0") str = "";
        str = str + "9";
        result.setText(str);


    }

    public void onClick0(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        if (str == "0") str = "";
        str = str + "0";
        result.setText(str);


    }

    public void onClickplus(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        CharSequence s = result.getText();
        String s1 = s.toString();


        if (opr == 1) {
            op2 = Float.parseFloat(s1);
            op1 = op1 + op2;
            result.setText(Float.toString(op1));
            op2 = 0;
            str = "0";
        } else {
            opr = 1;
            op1 = Float.parseFloat(s1);
            str = "0";
        }
    }

    public void onClickminus(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        CharSequence s = result.getText();
        String s1 = s.toString();

        if (opr == 3) {
            op2 = Float.parseFloat(s1);
            op1 = op1 - op2;
            result.setText(Float.toString(op1));
            op2 = 0;
            str = "0";
        } else {
            opr = 3;
            op1 = Float.parseFloat(s1);
            str = "0";
        }

    }

    public void onClickmul(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        CharSequence s = result.getText();
        String s1 = s.toString();

        if (opr == 2) {
            op2 = Float.parseFloat(s1);
            op1 = op1 * op2;
            result.setText(Float.toString(op1));
            op2 = 0;
            str = "0";
        } else {
            opr = 2;
            op1 = Float.parseFloat(s1);
            str = "0";
        }

    }

    public void onClickmod(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        CharSequence s = result.getText();
        String s1 = s.toString();

        if (opr == 4) {
            op2 = Float.parseFloat(s1);
            if (op2 == 0)
                op1 = 0;
            else
                op1 = op1 / op2;
            result.setText(Float.toString(op1));
            op2 = 0;
            str = "0";
        } else {
            opr = 2;
            op1 = Float.parseFloat(s1);
            str = "0";
        }
    }

    public void onClicc(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        op1 = op2 = 0;
        opr = 0;
        str = "0";
        result.setText("0");


    }

    public void onClickrav(View view) {
        TextView result = (TextView) findViewById(R.id.textView);
        CharSequence s = result.getText();
        String s1 = s.toString();
        op2 = Float.parseFloat(s1);
        switch (opr) {
            case 0:
                break;
            case 1:
                op1 = op1 + op2;
                break;
            case 2:
                op1 = op1 * op2;
                break;
            case 3:
                op1 = op1 - op2;
                break;
            case 4:
                if (op2 != 0) {
                    op1 = op1 / op2;
                } else {
                    op1 = 0;
                }
                break;
        }
        op2 = 0;
        opr = 0;
        str = "0";


        s = Float.toString(op1);
        result.setText(s);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
