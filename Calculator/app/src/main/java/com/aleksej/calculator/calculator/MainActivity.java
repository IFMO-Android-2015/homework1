package com.aleksej.calculator.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean flage_go=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null)
        {
            TextView txt=(TextView) findViewById(R.id.txt);
            flage_go=savedInstanceState.getBoolean("flage_go");
            txt.setText(savedInstanceState.getString("txt"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putBoolean("flage_go", flage_go);
        TextView txt=(TextView) findViewById(R.id.txt);
        outState.putString("txt", txt.getText().toString());
    }

    public void onClick_number(View v)
    {
        TextView Vtxt=(TextView)findViewById(R.id.txt);
        CharSequence txt=Vtxt.getText();
        switch (v.getId())
        {
            case R.id.btn0:
                txt=putNum(txt,"0");
                break;
            case R.id.btn1:
                txt=putNum(txt,"1");
                break;
            case R.id.btn2:
                txt=putNum(txt,"2");
                break;
            case R.id.btn3:
                txt=putNum(txt,"3");
                break;
            case R.id.btn4:
                txt=putNum(txt,"4");
                break;
            case R.id.btn5:
                txt=putNum(txt,"5");
                break;
            case R.id.btn6:
                txt=putNum(txt,"6");
                break;
            case R.id.btn7:
                txt=putNum(txt,"7");
                break;
            case R.id.btn8:
                txt=putNum(txt,"8");
                break;
            case R.id.btn9:
                txt=putNum(txt,"9");
                break;
            case R.id.btn_add:
                txt=txt+"+";
                break;
            case R.id.btn_sub:
                txt=txt+"-";
                break;
            case R.id.btn_mul:
                txt=txt+"*";
                break;
            case R.id.btn_div:
                txt=txt+"/";
                break;
            default:
                txt=putNum(txt,".");
                break;
        }
        flage_go=false;
        Vtxt.setText(txt);
    }

    public void  onClick_C(View v)
    {
        TextView Vtxt=(TextView)findViewById(R.id.txt);
        Vtxt.setText("");
    }

    public void onClick_go(View v)
    {
        flage_go=true;
        TextView Vtxt=(TextView)findViewById(R.id.txt);
        String str=Vtxt.getText().toString();
        Parser p=new Parser();
        try {
            Double res=p.parse(str);
            Vtxt.setText(res.toString());
        }
        catch (Exception e)
        {
            Log.w(LOG,e.getStackTrace().toString());
            Vtxt.setText("Error");
        }
    }
    private CharSequence putNum(CharSequence txt, String num)
    {
        if(flage_go)
            return num;
        else
            return txt+num;
    }
    private final String LOG="Parser";
}
