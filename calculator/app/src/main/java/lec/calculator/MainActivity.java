package lec.calculator;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private TextView inputView;

    private Calculator calculator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputView  = (TextView) findViewById(R.id.text_input);

        if (savedInstanceState != null) {
            calculator = (Calculator) getLastNonConfigurationInstance();
        } else {
            calculator = new Calculator();
        }

        updateView();
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

    @Override
    public Object onRetainNonConfigurationInstance() {
        return calculator;
    }

    public void updateView() {
        inputView.setText(calculator.toString());
    }

    // more copy-paste

    public void SymbClicked(View view) {
        calculator.pushSymb(((Button) view).getText().charAt(0));
        updateView();
    }

    public void PlusClicked(View view) {
        calculator.pushOp(Calculator.Operator.ADDITION);
        updateView();
    }

    public void MinusClicked(View view) {
        calculator.pushOp(Calculator.Operator.SUBTRACTION);
        updateView();
    }

    public void MulClicked(View view) {
        calculator.pushOp(Calculator.Operator.MULTIPLICATION);
        updateView();
    }

    public void DivClicked(View view) {
        calculator.pushOp(Calculator.Operator.DIVISION);
        updateView();
    }

    public void PercentClicked(View view) {
        calculator.pushOp(Calculator.Operator.PERCENTAGE);
        updateView();
    }

    public void SignChangeClicked(View view) {
        calculator.changeSign();
        updateView();
    }

    public void ACClicked(View view) {
        calculator.pop();
        updateView();
    }

    public void CommaClicked(View view) {
        calculator.pushComma();
        updateView();
    }

    public void EqualClicked(View view) {
        calculator.calculate();
        updateView();
    }

}
